package com.ots.dpel.ep.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.dto.AdminUnitDto;
import com.ots.dpel.common.core.dto.CountryDto;
import com.ots.dpel.common.services.AdminUnitService;
import com.ots.dpel.common.services.CountryService;
import com.ots.dpel.config.scheduling.jobs.CalculateSnapshotsJob;
import com.ots.dpel.ep.args.SnapshotArgs;
import com.ots.dpel.ep.args.StatisticsArgs;
import com.ots.dpel.ep.core.domain.QSnapshot;
import com.ots.dpel.ep.core.domain.Snapshot;
import com.ots.dpel.ep.core.enums.SnapshotType;
import com.ots.dpel.ep.dto.QSnapshotsDto;
import com.ots.dpel.ep.dto.SnapshotMapKey;
import com.ots.dpel.ep.dto.SnapshotsDto;
import com.ots.dpel.ep.persistence.SnapshotRepository;
import com.ots.dpel.ep.services.SnapshotService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.global.utils.UserUtils;
import com.ots.dpel.global.utils.UuidUtils;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionCenterBasicDto;
import com.ots.dpel.management.dto.ElectionDepartmentSnapshotDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionCenterService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;
import com.ots.dpel.system.dto.ScheduleJobCronDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import com.ots.dpel.system.services.CacheService;
import com.ots.dpel.system.services.SchedulingService;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ots.dpel.system.services.SchedulingService.KEEP_BATCHES_IN_CACHE;
import static com.ots.dpel.system.services.SchedulingService.SCHEDULE_CALCULATE_SNAPSHOTS_GROUP;

@Service
public class SnapshotServiceImpl implements SnapshotService {
    
    public static final int MAX_EXTRACTED_SNAPSHOTS = 15;
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Autowired
    private SnapshotRepository snapshotRepository;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private ElectionCenterService electionCenterService;
    
    @Autowired
    private AdminUnitService adminUnitService;
    
    @Autowired
    private SchedulingService schedulingService;
    
    @Autowired(required = false)
    private CacheManager cacheManager;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void calculateSnapshots() throws SchedulerException {
        
        //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return;
        }
        
        //Ανάκτηση στοιχείων όλων των εκλογικών τμημάτων
        List<ElectionDepartmentSnapshotDto> electionDepartmentSnapshotDtos = electionDepartmentService.getAllElectionDepartmentSnapshots();
        
        //Τρέχουσα ημερομηνία/ώρα
        Date calculationDateTime = DpDateUtils.getNewDateSecond();
        
        //Ημερομηνία επόμενης ενημέρωσης αποτελεσμάτων
        Date nextCalculationDateTime = schedulingService.getNextCalculationDateTime(SCHEDULE_CALCULATE_SNAPSHOTS_GROUP);
        
        //Map αποτελεσμάτων ανά ενότητα
        Map<SnapshotMapKey, Snapshot> snapshotMap = new HashMap<SnapshotMapKey, Snapshot>();
        
        //Για κάθε εκλογικό τμήμα που ανακτήθηκε
        for (ElectionDepartmentSnapshotDto electionDepartmentSnapshotDto : electionDepartmentSnapshotDtos) {
            
            //Όλα
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, null, SnapshotType.ALL, SnapshotType.ALL.toString());
            
            
            //Εξωτερικό
            if (electionDepartmentSnapshotDto.getForeign()) {
                manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                        nextCalculationDateTime, null, SnapshotType.ABROAD, SnapshotType.ABROAD.toString());
            }
            
            //Χώρα Εξωτερικού
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getForeignCountryIsoCode(), SnapshotType.FOREIGN_COUNTRY, electionDepartmentSnapshotDto.getForeignCountryName());
            
            //Πόλη Εξωτερικού
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, DpTextUtils.normalizeForeignCity(electionDepartmentSnapshotDto.getForeignCity()), SnapshotType.FOREIGN_CITY, electionDepartmentSnapshotDto.getForeignCity());
            
            
            //Ελλάδα
            if (!electionDepartmentSnapshotDto.getForeign()) {
                manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                        nextCalculationDateTime, null, SnapshotType.GREECE, SnapshotType.GREECE.toString());
            }
            
            //Γεωγραφική Ενότητα
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getGeographicalUnitId(), SnapshotType.GEOGRAPHICAL_UNIT, electionDepartmentSnapshotDto.getGeographicalUnitName());
            
            //Αποκεντρωμένη Διοίκηση
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getDecentralAdminId(), SnapshotType.DECENTRAL_ADMIN, electionDepartmentSnapshotDto.getDecentralAdminName());
            
            //Περιφέρεια
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getRegionId(), SnapshotType.REGION, electionDepartmentSnapshotDto.getRegionName());
            
            //Περιφερειακή Ενότητα
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getRegionalUnitId(), SnapshotType.REGIONAL_UNIT, electionDepartmentSnapshotDto.getRegionalUnitName());
            
            //Δήμος
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getMunicipalityId(), SnapshotType.MUNICIPALITY, electionDepartmentSnapshotDto.getMunicipalityName());
            
            //Δημοτική Ενότητα
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getMunicipalUnitId(), SnapshotType.MUNICIPAL_UNIT, electionDepartmentSnapshotDto.getMunicipalUnitName());
            
            
            //Εκλογικά Κέντρα
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getElectionCenterId(), SnapshotType.ELECTION_CENTER, electionDepartmentSnapshotDto.getElectionCenterDisplayName());
            
            //Εκλογικά Τμήματα
            manageSnapshotByType(currentElectionProcedureDto, electionDepartmentSnapshotDto, snapshotMap, calculationDateTime,
                    nextCalculationDateTime, electionDepartmentSnapshotDto.getId(), SnapshotType.ELECTION_DEPARTMENT, electionDepartmentSnapshotDto.getElectionDepartmentDisplayName());
        }
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        /**
         * Δημιουργία αποτελεσμάτων για όσα αντικείμενα δεν υπάρχουν στο map
         */
        
        //Χώρες Εξωτερικού
        List<CountryDto> countryDtos = countryService.findAll();
        for (CountryDto countryDto : countryDtos) {
            SnapshotMapKey key = new SnapshotMapKey(SnapshotType.FOREIGN_COUNTRY, countryDto.getIsoCode());
            Snapshot snapshot = snapshotMap.get(key);
            
            if (snapshot == null) {
                snapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key, countryDto.getName());
                snapshotMap.put(key, snapshot);
            }
        }
        
        //Πόλεις Εξωτερικού
        List<String> foreignCities = electionCenterService.getCitiesUsedInElectionCenters();
        for (String foreignCity : foreignCities) {
            SnapshotMapKey key = new SnapshotMapKey(SnapshotType.FOREIGN_CITY, DpTextUtils.normalizeForeignCity(foreignCity));
            Snapshot snapshot = snapshotMap.get(key);
            
            if (snapshot == null) {
                snapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key, foreignCity);
                snapshotMap.put(key, snapshot);
            }
        }
        
        //Admin Units
        List<AdminUnitDto> adminUnitDtos = adminUnitService.getAll();
        for (AdminUnitDto adminUnitDto : adminUnitDtos) {
            
            SnapshotType snapshotType = adminUnitService.getSnapshotType(adminUnitDto.getLevel());
            SnapshotMapKey key = new SnapshotMapKey(snapshotType, adminUnitDto.getId().toString());
            Snapshot snapshot = snapshotMap.get(key);
            
            if (snapshot == null) {
                snapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key, adminUnitDto.getName());
                snapshotMap.put(key, snapshot);
            }
        }
        
        //Εκλογικά Κέντρα
        List<ElectionCenterBasicDto> electionCenterDtos = electionCenterService.getElectionCenterBasicForSnapshot();
        for (ElectionCenterBasicDto electionCenterDto : electionCenterDtos) {
            SnapshotMapKey key = new SnapshotMapKey(SnapshotType.ELECTION_CENTER, electionCenterDto.getId().toString());
            Snapshot snapshot = snapshotMap.get(key);
            
            if (snapshot == null) {
                snapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key, electionCenterDto.getName());
                snapshotMap.put(key, snapshot);
            }
        }
        
        //Εξωτερικό
        SnapshotMapKey abroadKey = new SnapshotMapKey(SnapshotType.ABROAD, "0");
        Snapshot abroadSnapshot = snapshotMap.get(abroadKey);
        
        if (abroadSnapshot == null) {
            abroadSnapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, abroadKey, SnapshotType.ABROAD.toString());
            snapshotMap.put(abroadKey, abroadSnapshot);
        }
        
        //Ελλάδα
        SnapshotMapKey greeceKey = new SnapshotMapKey(SnapshotType.GREECE, "0");
        Snapshot greeceSnapshot = snapshotMap.get(greeceKey);
        
        if (greeceSnapshot == null) {
            greeceSnapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, greeceKey, SnapshotType.GREECE.toString());
            snapshotMap.put(greeceKey, greeceSnapshot);
        }
        
        //Όλα
        SnapshotMapKey allKey = new SnapshotMapKey(SnapshotType.ALL, "0");
        Snapshot allSnapshot = snapshotMap.get(allKey);
        
        if (allSnapshot == null) {
            allSnapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, allKey, SnapshotType.ALL.toString());
            snapshotMap.put(allKey, allSnapshot);
        }
    
        //--------------------------------------------------------------------------------------------------------------------------------------------
    
        //Ενημέρωση του timestamp τελευταίου υπολογισμού στην τρέχουσα εκλογική διαδικασία
        electionProcedureService.updateSnapshotLastCalcDateTimeForCurrent(calculationDateTime);
    
        Cache snapshotsCache = null;
        Date olderCalculationDateTime = null;
        
        // Το cacheManager είναι null όταν δεν είναι ενεργοποιημένο το memcached profile
        if (cacheManager != null) {
            //Ανάκτηση cache διατήρησης αποτελεσμάτων
            snapshotsCache = cacheManager.getCache(CacheService.SNAPSHOTS_CACHE);
            if (snapshotsCache != null) {
                olderCalculationDateTime = schedulingService.getOlderCalculationDateTime(
                        currentElectionProcedureDto.getId(), SCHEDULE_CALCULATE_SNAPSHOTS_GROUP, KEEP_BATCHES_IN_CACHE);
            }
        }
        
        //Αποθήκευση των εγγραφών του map
        for(Snapshot snapshot: snapshotMap.values()) {
            snapshotRepository.save(snapshot);
            if (snapshotsCache != null) {
                snapshotsCache.putIfAbsent(snapshot.computeCacheSignature(), getSnapshotsDto(snapshot));
                if (olderCalculationDateTime != null) {
                    snapshotsCache.evict(snapshot.computeCacheOlderSignature(olderCalculationDateTime));
                }
            }
        }
    
        //Αποθήκευση εγγραφής υπολογισμού στον πίνακα dp.scheduled_job_calc
        schedulingService.createAndSaveScheduledJobCalculation(currentElectionProcedureDto.getId(),
                calculationDateTime, SCHEDULE_CALCULATE_SNAPSHOTS_GROUP);
    }
    
    private SnapshotsDto getSnapshotsDto(Snapshot snapshot) {
    
        SnapshotsDto snapshotsDto = new SnapshotsDto();
        BeanUtils.copyProperties(snapshot, snapshotsDto, "type");
        snapshotsDto.setType(snapshot.getType().name());
        
        return snapshotsDto;
    }
    
    private void manageSnapshotByType(ElectionProcedureDto currentElectionProcedureDto,
                                      ElectionDepartmentSnapshotDto electionDepartmentSnapshotDto,
                                      Map<SnapshotMapKey, Snapshot> snapshotMap,
                                      Date calculationDateTime,
                                      Date nextCalculationDateTime,
                                      Object id,
                                      SnapshotType snapshotType,
                                      String snapshotName) {
        
        if (id != null) {
            SnapshotMapKey key = new SnapshotMapKey(snapshotType, id.toString());
            Snapshot snapshot = snapshotMap.get(key);
            
            if (snapshot == null) {
                snapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key, snapshotName);
                snapshotMap.put(key, snapshot);
            }
            addVotesToSnapshot(electionDepartmentSnapshotDto, snapshot);
        } else if (snapshotType.equals(SnapshotType.ALL) || snapshotType.equals(SnapshotType.ABROAD) || snapshotType.equals(SnapshotType.GREECE)) {
            SnapshotMapKey key = new SnapshotMapKey(snapshotType, "0");
            Snapshot snapshot = snapshotMap.get(key);
            
            if (snapshot == null) {
                snapshot = createNewSnapshot(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key, snapshotName);
                snapshotMap.put(key, snapshot);
            }
            addVotesToSnapshot(electionDepartmentSnapshotDto, snapshot);
        }
    }
    
    private Snapshot createNewSnapshot(ElectionProcedureDto currentElectionProcedureDto, Date calculationDateTime, Date nextCalculationDateTime,
                                       SnapshotMapKey snapshotMapKey, String name) {
        Snapshot snapshot = new Snapshot();
        
        snapshot.setCalculationDateTime(calculationDateTime);
        snapshot.setNextCalculationDateTime(nextCalculationDateTime);
        snapshot.setElectionProcedureId(currentElectionProcedureDto.getId());
        snapshot.setRound(ElectionRound.valueOf(currentElectionProcedureDto.getRound()));
        snapshot.setType(snapshotMapKey.getType());
        snapshot.setArgId(snapshotMapKey.getArgId());
        snapshot.setName(name);
        
        return snapshot;
    }
    
    private void addVotesToSnapshot(ElectionDepartmentSnapshotDto electionDepartmentSnapshotDto, Snapshot snapshot) {
        
        //Αύξηση κατά 1 του συνολικού πλήθους των εκλογικών τμημάτων
        snapshot.setTotalDepartmentCount(snapshot.getTotalDepartmentCount() + 1);
        
        //Αν στο εκλογικό τμήμα υπάρχει έστω και μία διαπίστευση σημαίνει ότι έχει ξεκινήσει
        if (electionDepartmentSnapshotDto.getVoterCount() > 0) {
            snapshot.setStartedDepartmentCount(snapshot.getStartedDepartmentCount() + 1);
        }
        
        //Αν το εκλογικό τμήμα έχει υποβάλει αποτελέσματα
        if (electionDepartmentSnapshotDto.getSubmitted()) {
            //Αύξηση κατά 1 του πλήθους των εκλογικών τμημάτων που υπέβαλλαν
            snapshot.setSubmittedDepartmentCount(snapshot.getSubmittedDepartmentCount() + 1);
        }
        
        //Πλήθος διαπιστεύσεων και άρσεων διαπιστεύσεων
        snapshot.setVoterCount(snapshot.getVoterCount() + electionDepartmentSnapshotDto.getVoterCount());
        snapshot.setUndoneVoterCount(snapshot.getUndoneVoterCount() + electionDepartmentSnapshotDto.getUndoneVoterCount());
    }
    
    @Override
    public SnapshotsDto searchSnapshot(SnapshotArgs args) {
    
        // Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return null;
        }
        
        // Έλεγχος ότι υπάρχει ορισμένη ημερομηνία στον πίνακα της εκλογικής διαδικασίας
        if (currentElectionProcedureDto.getSnapshotLastCalcDateTime() == null) {
            throw new DpValidationException("SNAPSHOT_LAST_CALCULATION_DATE_NULL", messageSourceProvider.getMessage("snapshots.error.lastCalculationDateTime.null"));
        }
    
        QSnapshot snapshot = QSnapshot.snapshot;
        JPQLQuery query = new JPAQuery(entityManager);
        BooleanBuilder predicate = new BooleanBuilder();
    
        // Τρέχουσα εκλογική διαδικασία και γύρος
        predicate.and(snapshot.electionProcedureId.eq(currentElectionProcedureDto.getId()));
        predicate.and(snapshot.round.eq(ElectionRound.valueOf(currentElectionProcedureDto.getRound())));
    
        // Ημερομηνία τελευταίου υπολογισμού αποτελεσμάτων
        predicate.and(snapshot.calculationDateTime.eq(currentElectionProcedureDto.getSnapshotLastCalcDateTime()));
    
        // Διαχείριση των κριτηρίων για τις τρεις γενικές περιπτώσεις, ALL/ABROAD/GREECE
        normalizeGeneralArgs(args);
        
        // Αν δεν έχει δοθεί type, έρχονται τα συνολικά αποτελέσματα
        if (args == null || DpTextUtils.isEmpty(args.getType())) {
            predicate.and(snapshot.type.eq(SnapshotType.ALL));
        } else {
            SnapshotType snapshotType = SnapshotType.valueOf(args.getType());
        
            // Αν δεν έχει δοθεί argId
            if (DpTextUtils.isEmpty(args.getArgId())) {
                if (snapshotType.equals(SnapshotType.ALL) || snapshotType.equals(SnapshotType.ABROAD) || snapshotType.equals(SnapshotType.GREECE)) {
                    // Αν το type είναι ALL/ABROAD/GREECE, βάζουμε στο argId το 0.
                    args.setArgId("0");
                } else {
                    // Αλλιώς δε φέρνει τίποτα
                    return null;
                }
            }
    
            // Διαχείριση της πόλης εξωτερικού
            if (snapshotType.equals(SnapshotType.FOREIGN_CITY)) {
                args.setArgId(DpTextUtils.normalizeForeignCity(args.getArgId()));
            }
            
            predicate.and(snapshot.type.eq(snapshotType));
            predicate.and(snapshot.argId.eq(args.getArgId()));
        }
    
        // Έλεγχος εάν υπάρχει αποθηκευμένη η εγγραφή των αποτελεσμάτων στην Cache
        Snapshot cacheSnapshot = getSnapshot(currentElectionProcedureDto, args);
    
        // Το cacheManager είναι null όταν δεν είναι ενεργοποιημένο το memcached profile
        if (cacheManager != null) {
            Cache snapshotsCache = cacheManager.getCache(CacheService.SNAPSHOTS_CACHE);
            SnapshotsDto cachedSnapshotsDto = snapshotsCache.get(cacheSnapshot.computeCacheSignature(), SnapshotsDto.class);
            
            if (cachedSnapshotsDto != null) {
    
                if (cachedSnapshotsDto.getType().equals(SnapshotType.ALL.name()) || cachedSnapshotsDto.getType().equals(SnapshotType.GREECE.name()) ||
                        cachedSnapshotsDto.getType().equals(SnapshotType.ABROAD.name())) {
                    cachedSnapshotsDto = cachedSnapshotsDto.normalizeGeneralArgs();
                }
                
                // Ανάκτηση και των εγγραφών άμεσων απογόνων στην ιεραρχία από την cache
                cachedSnapshotsDto.setDescendantSnapshots(getDescendantSnapshotsFromCache(snapshotsCache, cacheSnapshot));
                cachedSnapshotsDto.orderDescendantSnapshotsByVoterCount();
                return cachedSnapshotsDto;
            }
        }
    
        // Εάν δε βρεθεί στην Cache εκτελείται ερώτημα στη βάση
        query.from(snapshot).where(predicate);
    
        Expression<SnapshotsDto> expression = getSnapshotDtoConstructorExpression(snapshot);
    
        SnapshotsDto snapshotsDto = query.singleResult(expression);
    
        if (snapshotsDto == null) {
            throw new DpValidationException("SNAPSHOT_DTO_NULL", messageSourceProvider.getMessage("snapshots.error.snapshotsDto.null"));
        }
        
        if (snapshotsDto.getType().equals(SnapshotType.ALL.name()) || snapshotsDto.getType().equals(SnapshotType.GREECE.name()) ||
                snapshotsDto.getType().equals(SnapshotType.ABROAD.name())) {
            snapshotsDto = snapshotsDto.normalizeGeneralArgs();
        }
        
        Snapshot dbSnapshot = getSnapshot(currentElectionProcedureDto, args);
        
        // Ανάκτηση και των εγγραφών άμεσων απογόνων στην ιεραρχία από τη βάση
        snapshotsDto.setDescendantSnapshots(getDescendantSnapshots(dbSnapshot));
        snapshotsDto.orderDescendantSnapshotsByVoterCount();
        
        return snapshotsDto;
    }
    
    private Snapshot getSnapshot(ElectionProcedureDto currentElectionProcedureDto, SnapshotArgs args) {
        return new Snapshot(
                currentElectionProcedureDto.getSnapshotLastCalcDateTime(),
                currentElectionProcedureDto.getId(),
                ElectionRound.valueOf(currentElectionProcedureDto.getRound()),
                args != null && args.getType() != null ? SnapshotType.valueOf(args.getType()) : SnapshotType.ALL,
                args != null ? args.getArgId() : null);
    }
    
    private String getDescendantSnapshotSignature(Snapshot snapshot, SnapshotType snapshotType, String argId) {
        
        Snapshot descendantSnapshot = new Snapshot(snapshot.getCalculationDateTime(), snapshot.getElectionProcedureId(), snapshot.getRound(), snapshotType, argId);
        return descendantSnapshot.computeCacheSignature();
    }
    
    private List<SnapshotsDto> getDescendantSnapshots(Snapshot snapshot) {
        
        List<SnapshotsDto> descendantSnapshots = new ArrayList<>();
        List<AdminUnitDto> descendantAdminUnits = null;
        List<ElectionCenterBasicDto> descendantElectionCenters = null;
        
        switch (snapshot.getType()) {
            case ALL:
                descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.GREECE, "0").normalizeGeneralArgs());
                descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.ABROAD, "0").normalizeGeneralArgs());
                break;
            case GREECE:
                descendantAdminUnits = adminUnitService.getByType("REGION");
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.REGION, adminUnitDto.getId().toString()));
                }
                break;
            case GEOGRAPHICAL_UNIT:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.DECENTRAL_ADMIN, adminUnitDto.getId().toString()));
                }
                break;
            case DECENTRAL_ADMIN:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.REGION, adminUnitDto.getId().toString()));
                }
                break;
            case REGION:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.REGIONAL_UNIT, adminUnitDto.getId().toString()));
                }
                break;
            case REGIONAL_UNIT:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.MUNICIPALITY, adminUnitDto.getId().toString()));
                }
                break;
            case MUNICIPALITY:
                descendantElectionCenters = electionCenterService.getElectionCenterBasicByMunicipalityId
                        (Long.parseLong(snapshot.getArgId()));
                for (ElectionCenterBasicDto electionCenterBasicDto : descendantElectionCenters) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.ELECTION_CENTER, electionCenterBasicDto.getId().toString()));
                }
                break;
            case MUNICIPAL_UNIT:
                break;
            case ELECTION_CENTER:
                List<Long> descendantElectionDepartments = electionDepartmentService.findIdsByElectionCenter(Long.parseLong(snapshot.getArgId()));
                for (Long electionDepartmentId : descendantElectionDepartments) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.ELECTION_DEPARTMENT, electionDepartmentId.toString()));
                }
                break;
            case ELECTION_DEPARTMENT:
                break;
            case ABROAD:
                List<CountryDto> descendantCountries = countryService.findAll();
                for (CountryDto countryDto : descendantCountries) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.FOREIGN_COUNTRY, countryDto.getIsoCode()));
                }
                break;
            case FOREIGN_COUNTRY:
                descendantElectionCenters = electionCenterService.getElectionCenterBasicByForeignCountry
                        (snapshot.getArgId());
                for (ElectionCenterBasicDto electionCenterBasicDto : descendantElectionCenters) {
                    descendantSnapshots.add(getDescendantSnapshot(snapshot, SnapshotType.ELECTION_CENTER, electionCenterBasicDto.getId().toString()));
                }
                break;
            case FOREIGN_CITY:
                break;
            default:
                break;
            
        }
        
        return descendantSnapshots;
    }
    
    private SnapshotsDto getDescendantSnapshot(Snapshot snapshot, SnapshotType snapshotType, String argId) {
    
        QSnapshot qSnapshot = QSnapshot.snapshot;
        JPQLQuery query = new JPAQuery(entityManager);
        BooleanBuilder predicate = new BooleanBuilder();
    
        predicate.and(qSnapshot.calculationDateTime.eq(snapshot.getCalculationDateTime()));
        predicate.and(qSnapshot.electionProcedureId.eq(snapshot.getElectionProcedureId()));
        predicate.and(qSnapshot.round.eq(snapshot.getRound()));
        predicate.and(qSnapshot.type.eq(snapshotType));
        predicate.and(qSnapshot.argId.eq(argId));
    
        query.from(qSnapshot).where(predicate);
    
        Expression<SnapshotsDto> expression = getSnapshotDtoConstructorExpression(qSnapshot);
    
        return query.singleResult(expression);
    }
    
    private List<SnapshotsDto> getDescendantSnapshotsFromCache(Cache snapshotsCache, Snapshot snapshot) {
    
        List<SnapshotsDto> descendantSnapshots = new ArrayList<>();
        List<AdminUnitDto> descendantAdminUnits = null;
        List<ElectionCenterBasicDto> descendantElectionCenters = null;
        
        switch (snapshot.getType()) {
            case ALL:
                descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(snapshot, SnapshotType.GREECE, "0"), SnapshotsDto.class).normalizeGeneralArgs());
                descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(snapshot, SnapshotType.ABROAD, "0"), SnapshotsDto.class).normalizeGeneralArgs());
                break;
            case GREECE:
                descendantAdminUnits = adminUnitService.getByType("REGION");
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.REGION, adminUnitDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case GEOGRAPHICAL_UNIT:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.DECENTRAL_ADMIN, adminUnitDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case DECENTRAL_ADMIN:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.REGION, adminUnitDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case REGION:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.REGIONAL_UNIT, adminUnitDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case REGIONAL_UNIT:
                descendantAdminUnits = adminUnitService.getByParentId(Long.parseLong(snapshot.getArgId()));
                for (AdminUnitDto adminUnitDto : descendantAdminUnits) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.MUNICIPALITY, adminUnitDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case MUNICIPALITY:
                descendantElectionCenters = electionCenterService.getElectionCenterBasicByMunicipalityId
                        (Long.parseLong(snapshot.getArgId()));
                for (ElectionCenterBasicDto electionCenterBasicDto : descendantElectionCenters) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.ELECTION_CENTER, electionCenterBasicDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case MUNICIPAL_UNIT:
                break;
            case ELECTION_CENTER:
                List<Long> descendantElectionDepartments = electionDepartmentService.findIdsByElectionCenter(Long.parseLong(snapshot.getArgId()));
                for (Long electionDepartmentId : descendantElectionDepartments) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.ELECTION_DEPARTMENT, electionDepartmentId.toString()), SnapshotsDto.class));
                }
                break;
            case ELECTION_DEPARTMENT:
                break;
            case ABROAD:
                List<CountryDto> descendantCountries = countryService.findAll();
                for (CountryDto countryDto : descendantCountries) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.FOREIGN_COUNTRY, countryDto.getIsoCode()), SnapshotsDto.class));
                }
                break;
            case FOREIGN_COUNTRY:
                descendantElectionCenters = electionCenterService.getElectionCenterBasicByForeignCountry
                        (snapshot.getArgId());
                for (ElectionCenterBasicDto electionCenterBasicDto : descendantElectionCenters) {
                    descendantSnapshots.add(snapshotsCache.get(getDescendantSnapshotSignature(
                            snapshot, SnapshotType.ELECTION_CENTER, electionCenterBasicDto.getId().toString()), SnapshotsDto.class));
                }
                break;
            case FOREIGN_CITY:
                break;
            default:
                break;
                
        }
        
        return descendantSnapshots;
    }
    
    /**
     * Μεταροπή των κριτηρίων αναζήτησης ως εξής:
     * (GENERAL / ALL) -> (ALL / 0)
     * (GENERAL / ABROAD) -> (ABROAD / 0)
     * (GENERAL / GREECE) -> (GREECE / 0)
     */
    private void normalizeGeneralArgs(SnapshotArgs args) {
        if(args != null && !DpTextUtils.isEmpty(args.getType()) && args.getType().equals("GENERAL")) {
            if(!DpTextUtils.isEmpty(args.getArgId())) {
                args.setType(args.getArgId());
                args.setArgId("0");
            }
        }
    }
    
    private Expression<SnapshotsDto> getSnapshotDtoConstructorExpression(QSnapshot snapshot) {
        return ConstructorExpression.create(SnapshotsDto.class,
                snapshot.id,
                snapshot.type,
                snapshot.argId,
                snapshot.calculationDateTime,
                snapshot.nextCalculationDateTime,
                snapshot.name,
                snapshot.totalDepartmentCount,
                snapshot.startedDepartmentCount,
                snapshot.submittedDepartmentCount,
                snapshot.voterCount,
                snapshot.undoneVoterCount);
    }
    
    @Override
    public void scheduleCalculateSnapshotsJob(ScheduleJobCronDTO scheduleJobCronDTO) throws SchedulerException {
        
        if (DpTextUtils.isEmpty(scheduleJobCronDTO.getCronExpression())) {
            throw new DpValidationException("CRON_EXPRESSION_EMPTY", messageSourceProvider.getMessage("schedule.calculateSnapshots.job" +
                    ".cronExpression.empty"));
        }
        
        if (!CronExpression.isValidExpression(scheduleJobCronDTO.getCronExpression())) {
            throw new DpValidationException("CRON_EXPRESSION_INVALID", messageSourceProvider.getMessage("schedule.calculateSnapshots.job" +
                    ".cronExpression.invalid"));
        }
        
        JobDetail jobDetail = JobBuilder.newJob().ofType(CalculateSnapshotsJob.class)
                .storeDurably()
                .withIdentity(UuidUtils.generateId(), SCHEDULE_CALCULATE_SNAPSHOTS_GROUP)
                .withDescription(SchedulingService.SCHEDULE_CALCULATE_SNAPSHOTS_JOB_DESCRIPTION)
                .build();
        
        String description = messageSourceProvider.getMessage("schedule.calculateSnapshots.job", new Object[]{});
        
        jobDetail.getJobDataMap().put("scheduledByUser", UserUtils.getUser().getUsername());
        jobDetail.getJobDataMap().put("scheduleDate", new Date());
        jobDetail.getJobDataMap().put("description", description);
        
        // String cronExpression = CrDateUtils.convertDateToCronExpression(scheduleDate);
        String cronExpression = scheduleJobCronDTO.getCronExpression();
        
        CronTrigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(UuidUtils.generateId(), SCHEDULE_CALCULATE_SNAPSHOTS_GROUP)
                .withDescription(SchedulingService.SCHEDULE_CALCULATE_SNAPSHOTS_TRIGGER_DESCRIPTION)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        
        schedulingService.scheduleJob(jobDetail, trigger);
    }
    
    @Override
    public void unscheduleCalculateSnapshotsJob() throws SchedulerException {
        schedulingService.unscheduleJob(SCHEDULE_CALCULATE_SNAPSHOTS_GROUP);
    }
    
    @Override
    public ScheduledJobTriggerDTO getCalculateSnapshotsJobStatus() throws SchedulerException {
        return schedulingService.getJobStatus(SCHEDULE_CALCULATE_SNAPSHOTS_GROUP);
    }
    
    @Override
    public List<SnapshotsDto> findAllSnapshots(SnapshotArgs args) {
    
        // Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())
                || currentElectionProcedureDto.getSnapshotLastCalcDateTime() == null) {
            return null;
        }
    
        if (args != null) {
            if (args.getType() == null) {
                args.setType("ALL");
                args.setArgId("0");
            } else if (args.getType().equals("GENERAL")) {
                args.setType(args.getArgId());
                args.setArgId("0");
            }
        }
        
        QSnapshot snapshot = QSnapshot.snapshot;
        JPQLQuery query = new JPAQuery(entityManager);
        BooleanBuilder predicate = new BooleanBuilder();
    
        predicate.and(snapshot.electionProcedureId.eq(currentElectionProcedureDto.getId()));
        predicate.and(snapshot.round.eq(ElectionRound.valueOf(currentElectionProcedureDto.getRound())));
    
        predicate.and(snapshot.type.eq(SnapshotType.valueOf(args.getType())));
        predicate.and(snapshot.argId.eq(args.getArgId()));
    
        query.from(snapshot).where(predicate).orderBy(snapshot.calculationDateTime.asc());
    
        List<SnapshotsDto> allSnapshots = snapshotRepository.findAll(query, (FactoryExpression<SnapshotsDto>) getSnapshotDtoConstructorExpression(snapshot));
        
        return extractFinalSnapshots(allSnapshots);
    }
    
    private List<SnapshotsDto> extractFinalSnapshots(List<SnapshotsDto> allSnapshots) {
        List<SnapshotsDto> finalSnapshots = new ArrayList<>();
        
        if (allSnapshots != null && allSnapshots.size() > MAX_EXTRACTED_SNAPSHOTS) {

            Date finalCalculationDateTime = allSnapshots.get(allSnapshots.size() - 1).getCalculationDateTime();
            Date initialCalculationDateTime = allSnapshots.get(0).getCalculationDateTime();

            Long secondsBetweenDates = DpDateUtils.getSecondsBetweenDates(initialCalculationDateTime, finalCalculationDateTime);

            int length = (int) ((Long) secondsBetweenDates / MAX_EXTRACTED_SNAPSHOTS);

            for (int i = 0; i < allSnapshots.size(); i++) {
                if (i == 0 || i == allSnapshots.size() - 1) {
                    finalSnapshots.add(allSnapshots.get(i));
                }
                else {
                    Date stepDate = DpDateUtils.addSecondsToDate(finalSnapshots.get(finalSnapshots.size() - 1).getCalculationDateTime(), length);
                    if (stepDate.before(allSnapshots.get(i).getCalculationDateTime()) || stepDate.equals(allSnapshots.get(i).getCalculationDateTime())) {
                        finalSnapshots.add(allSnapshots.get(i));
                    }
                }
            }
        }
        else {
            finalSnapshots = allSnapshots;
        }
        return finalSnapshots;
    }
    
    @Override
    public Page<SnapshotsDto> getStatisticsIndex(SearchableArguments arguments, Pageable pageable) {
        // Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())
            || currentElectionProcedureDto.getSnapshotLastCalcDateTime() == null) {
            return new PageImpl<>(Collections.<SnapshotsDto>emptyList(), pageable, 0);
        }
        
        StatisticsArgs args = (StatisticsArgs) arguments;
        
        QSnapshot snapshot = new QSnapshot("snapshot");
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = new BooleanBuilder()
            .and(snapshot.electionProcedureId.eq(currentElectionProcedureDto.getId()))
            .and(snapshot.round.eq(ElectionRound.valueOf(currentElectionProcedureDto.getRound())))
            .and(snapshot.calculationDateTime.eq(currentElectionProcedureDto.getSnapshotLastCalcDateTime()))
            .and(snapshot.type.eq(args.getType() != null ? args.getType() : SnapshotType.ALL));
        
        query.from(snapshot).where(predicate);
        
        FactoryExpression<SnapshotsDto> expression = (FactoryExpression<SnapshotsDto>) getSnapshotDtoConstructorExpression(snapshot);
        
        return snapshotRepository.findAll(query, expression, pageable);
    }
}
