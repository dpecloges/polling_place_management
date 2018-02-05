package com.ots.dpel.results.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.ots.dpel.common.core.dto.AdminUnitDto;
import com.ots.dpel.common.core.dto.CountryDto;
import com.ots.dpel.common.services.AdminUnitService;
import com.ots.dpel.common.services.CountryService;
import com.ots.dpel.config.scheduling.jobs.CalculateResultsJob;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.global.utils.UserUtils;
import com.ots.dpel.global.utils.UuidUtils;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionDepartmentResultDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionCenterService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;
import com.ots.dpel.results.args.ResultArgs;
import com.ots.dpel.results.core.domain.QResult;
import com.ots.dpel.results.core.domain.Result;
import com.ots.dpel.results.core.enums.ResultType;
import com.ots.dpel.results.dto.ResultMapKey;
import com.ots.dpel.results.dto.ResultsDto;
import com.ots.dpel.results.persistence.ResultRepository;
import com.ots.dpel.results.services.ResultService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ots.dpel.system.services.SchedulingService.KEEP_BATCHES_IN_CACHE;
import static com.ots.dpel.system.services.SchedulingService.SCHEDULE_CALCULATE_RESULTS_GROUP;

@Service
public class ResultServiceImpl implements ResultService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Autowired
    private ResultRepository resultRepository;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private ElectionCenterService electionCenterService;
    
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private AdminUnitService adminUnitService;
    
    @Autowired
    private SchedulingService schedulingService;
    
    @Autowired(required = false)
    private CacheManager cacheManager;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void calculateResults() throws SchedulerException {
    
        //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return;
        }
        
        //Ανάκτηση στοιχείων όλων των εκλογικών τμημάτων
        List<ElectionDepartmentResultDto> electionDepartmentResultDtos = electionDepartmentService.getAllElectionDepartmentResults();
        
        //Τρέχουσα ημερομηνία/ώρα
        Date calculationDateTime = DpDateUtils.getNewDateSecond();
    
        //Ημερομηνία επόμενης ενημέρωσης αποτελεσμάτων
        Date nextCalculationDateTime = schedulingService.getNextCalculationDateTime(SCHEDULE_CALCULATE_RESULTS_GROUP);
        
        //Map αποτελεσμάτων ανά ενότητα
        Map<ResultMapKey, Result> resultMap = new HashMap<ResultMapKey, Result>();
        
        //Για κάθε εκλογικό τμήμα που ανακτήθηκε
        for(ElectionDepartmentResultDto electionDepartmentResultDto: electionDepartmentResultDtos) {
            
            //Όλα
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, null, ResultType.ALL);
            
            
            //Εξωτερικό
            if(electionDepartmentResultDto.getForeign()) {
                manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, null, ResultType.ABROAD);
            }
            
            //Χώρα Εξωτερικού
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getForeignCountryIsoCode(), ResultType.FOREIGN_COUNTRY);
            
            //Πόλη Εξωτερικού
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getForeignCity(), ResultType.FOREIGN_CITY);
            
            
            //Ελλάδα
            if(!electionDepartmentResultDto.getForeign()) {
                manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, null, ResultType.GREECE);
            }
            
            //Γεωγραφική Ενότητα
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getGeographicalUnitId(), ResultType.GEOGRAPHICAL_UNIT);
            
            //Αποκεντρωμένη Διοίκηση
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getDecentralAdminId(), ResultType.DECENTRAL_ADMIN);
            
            //Περιφέρεια
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getRegionId(), ResultType.REGION);
            
            //Περιφερειακή Ενότητα
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getRegionalUnitId(), ResultType.REGIONAL_UNIT);
            
            //Δήμος
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getMunicipalityId(), ResultType.MUNICIPALITY);
            
            //Δημοτική Ενότητα
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getMunicipalUnitId(), ResultType.MUNICIPAL_UNIT);
            
            
            //Εκλογικά Κέντρα
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getElectionCenterId(), ResultType.ELECTION_CENTER);
            
            //Εκλογικά Τμήματα
            manageResultByType(currentElectionProcedureDto, electionDepartmentResultDto, resultMap, calculationDateTime, nextCalculationDateTime, electionDepartmentResultDto.getId(), ResultType.ELECTION_DEPARTMENT);
        }
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        /**
         * Δημιουργία αποτελεσμάτων για όσα αντικείμενα δεν υπάρχουν στο map
         */
        
        //Χώρες Εξωτερικού
        List<CountryDto> countryDtos = countryService.findAll();
        for(CountryDto countryDto: countryDtos) {
            ResultMapKey key = new ResultMapKey(ResultType.FOREIGN_COUNTRY, countryDto.getIsoCode());
            Result result = resultMap.get(key);
            
            if(result == null) {
                result = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key);
                resultMap.put(key, result);
            }
        }
        
        //Πόλεις Εξωτερικού
        List<String> foreignCities = electionCenterService.getCitiesUsedInElectionCenters();
        for(String foreignCity: foreignCities) {
            ResultMapKey key = new ResultMapKey(ResultType.FOREIGN_CITY, foreignCity);
            Result result = resultMap.get(key);
            
            if(result == null) {
                result = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key);
                resultMap.put(key, result);
            }
        }
        
        //Admin Units
        List<AdminUnitDto> adminUnitDtos = adminUnitService.getAll();
        for(AdminUnitDto adminUnitDto: adminUnitDtos) {
            
            ResultType resultType = adminUnitService.getResultType(adminUnitDto.getLevel());
            ResultMapKey key = new ResultMapKey(resultType, adminUnitDto.getId().toString());
            Result result = resultMap.get(key);
            
            if(result == null) {
                result = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key);
                resultMap.put(key, result);
            }
        }
        
        //Εκλογικά Κέντρα
        List<Long> electionCenterIds = electionCenterService.getElectionCenterIds();
        for(Long electionCenterId: electionCenterIds) {
            ResultMapKey key = new ResultMapKey(ResultType.ELECTION_CENTER, electionCenterId.toString());
            Result result = resultMap.get(key);
            
            if(result == null) {
                result = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key);
                resultMap.put(key, result);
            }
        }
        
        //Εξωτερικό
        ResultMapKey abroadKey = new ResultMapKey(ResultType.ABROAD, "0");
        Result abroadResult = resultMap.get(abroadKey);
        
        if(abroadResult == null) {
            abroadResult = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, abroadKey);
            resultMap.put(abroadKey, abroadResult);
        }
        
        //Ελλάδα
        ResultMapKey greeceKey = new ResultMapKey(ResultType.GREECE, "0");
        Result greeceResult = resultMap.get(greeceKey);
        
        if(greeceResult == null) {
            greeceResult = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, greeceKey);
            resultMap.put(greeceKey, greeceResult);
        }
        
        //Όλα
        ResultMapKey allKey = new ResultMapKey(ResultType.ALL, "0");
        Result allResult = resultMap.get(allKey);
        
        if(allResult == null) {
            allResult = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, allKey);
            resultMap.put(allKey, allResult);
        }
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        
        //Ενημέρωση του timestamp τελευταίου υπολογισμού αποτελεσμάτων στην τρέχουσα εκλογική διαδικασία
        electionProcedureService.updateResultsLastCalcDateTimeForCurrent(calculationDateTime);

        Cache resultsCache = null;
        Date olderCalculationDateTime = null;
        
        // Το cacheManager είναι null όταν δεν είναι ενεργοποιημένο το memcached profile
        if (cacheManager != null) {
            //Ανάκτηση cache διατήρησης αποτελεσμάτων
            resultsCache = cacheManager.getCache(CacheService.RESULTS_CACHE);
            if (resultsCache != null) {
                olderCalculationDateTime = schedulingService.getOlderCalculationDateTime(
                        currentElectionProcedureDto.getId(), SCHEDULE_CALCULATE_RESULTS_GROUP, KEEP_BATCHES_IN_CACHE);
            }
        }
        
        //Αποθήκευση των αποτελεσμάτων του map
        for(Result result: resultMap.values()) {
            resultRepository.save(result);
            if (resultsCache != null) {
                resultsCache.putIfAbsent(result.computeCacheSignature(), getResultsDto(result));
                if (olderCalculationDateTime != null) {
                    resultsCache.evict(result.computeCacheOlderSignature(olderCalculationDateTime));
                }
            }
        }
    
        //Αποθήκευση εγγραφής υπολογισμού στον πίνακα dp.scheduled_job_calc
        schedulingService.createAndSaveScheduledJobCalculation(currentElectionProcedureDto.getId(),
                calculationDateTime, SCHEDULE_CALCULATE_RESULTS_GROUP);
    }
    
    private ResultsDto getResultsDto(Result result) {
    
        ResultsDto resultsDto = new ResultsDto();
        BeanUtils.copyProperties(result, resultsDto);
    
        return resultsDto;
    }
    
    private void manageResultByType(ElectionProcedureDto currentElectionProcedureDto,
                                    ElectionDepartmentResultDto electionDepartmentResultDto,
                                    Map<ResultMapKey, Result> resultMap,
                                    Date calculationDateTime,
                                    Date nextCalculationDateTime,
                                    Object id,
                                    ResultType resultType) {
        
        if(id != null) {
            ResultMapKey key = new ResultMapKey(resultType, id.toString());
            Result result = resultMap.get(key);
        
            if(result == null) {
                result = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key);
                resultMap.put(key, result);
            }
            addVotesToResult(electionDepartmentResultDto, result);
        }
        else if(resultType.equals(ResultType.ALL) || resultType.equals(ResultType.ABROAD) || resultType.equals(ResultType.GREECE)) {
            ResultMapKey key = new ResultMapKey(resultType, "0");
            Result result = resultMap.get(key);
            
            if(result == null) {
                result = createNewResult(currentElectionProcedureDto, calculationDateTime, nextCalculationDateTime, key);
                resultMap.put(key, result);
            }
            addVotesToResult(electionDepartmentResultDto, result);
        }
    }
    
    private Result createNewResult(ElectionProcedureDto currentElectionProcedureDto, Date calculationDateTime, Date nextCalculationDateTime, ResultMapKey resultMapKey) {
        Result result = new Result();
        
        result.setCalculationDateTime(calculationDateTime);
        result.setNextCalculationDateTime(nextCalculationDateTime);
        result.setElectionProcedureId(currentElectionProcedureDto.getId());
        result.setRound(ElectionRound.valueOf(currentElectionProcedureDto.getRound()));
        result.setType(resultMapKey.getType());
        result.setArgId(resultMapKey.getArgId());
        
        return result;
    }
    
    private void addVotesToResult(ElectionDepartmentResultDto electionDepartmentResultDto, Result result) {
        
        //Αν το εκλογικό τμήμα έχει υποβάλει αποτελέσματα
        if(electionDepartmentResultDto.getSubmitted()) {
            //Πρόσθεση των ψήφων στα αθροίσματα
            result.setTotalVotes(result.getTotalVotes() + electionDepartmentResultDto.getTotalVotes());
            result.setWhiteVotes(result.getWhiteVotes() + electionDepartmentResultDto.getWhiteVotes());
            result.setInvalidVotes(result.getInvalidVotes() + electionDepartmentResultDto.getInvalidVotes());
            result.setValidVotes(result.getValidVotes() + electionDepartmentResultDto.getValidVotes());
            result.setCandidateOneVotes(result.getCandidateOneVotes() + electionDepartmentResultDto.getCandidateOneVotes());
            result.setCandidateTwoVotes(result.getCandidateTwoVotes() + electionDepartmentResultDto.getCandidateTwoVotes());
            result.setCandidateThreeVotes(result.getCandidateThreeVotes() + electionDepartmentResultDto.getCandidateThreeVotes());
            result.setCandidateFourVotes(result.getCandidateFourVotes() + electionDepartmentResultDto.getCandidateFourVotes());
            result.setCandidateFiveVotes(result.getCandidateFiveVotes() + electionDepartmentResultDto.getCandidateFiveVotes());
            result.setCandidateSixVotes(result.getCandidateSixVotes() + electionDepartmentResultDto.getCandidateSixVotes());
            result.setCandidateSevenVotes(result.getCandidateSevenVotes() + electionDepartmentResultDto.getCandidateSevenVotes());
            result.setCandidateEightVotes(result.getCandidateEightVotes() + electionDepartmentResultDto.getCandidateEightVotes());
            result.setCandidateNineVotes(result.getCandidateNineVotes() + electionDepartmentResultDto.getCandidateNineVotes());
            result.setCandidateTenVotes(result.getCandidateTenVotes() + electionDepartmentResultDto.getCandidateTenVotes());
            
            
            //Αύξηση κατά 1 του πλήθους των εκλογικών τμημάτων που υπέβαλλαν
            result.setSubmittedDepartmentCount(result.getSubmittedDepartmentCount() + 1);
        }
        
        //Αύξηση κατά 1 του συνολικού πλήθους των εκλογικών τμημάτων
        result.setTotalDepartmentCount(result.getTotalDepartmentCount() + 1);
    }
    
    @Override
    public ResultsDto searchResult(ResultArgs args) {
    
        // Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())
                || currentElectionProcedureDto.getResultsLastCalcDateTime() == null) {
            return null;
        }
    
        QResult result = QResult.result;
        JPQLQuery query = new JPAQuery(entityManager);
        BooleanBuilder predicate = new BooleanBuilder();
    
        // Τρέχουσα εκλογική διαδικασία και γύρος
        predicate.and(result.electionProcedureId.eq(currentElectionProcedureDto.getId()));
        predicate.and(result.round.eq(ElectionRound.valueOf(currentElectionProcedureDto.getRound())));
    
        // Ημερομηνία τελευταίου υπολογισμού αποτελεσμάτων
        predicate.and(result.calculationDateTime.eq(currentElectionProcedureDto.getResultsLastCalcDateTime()));
    
        // Διαχείριση των κριτηρίων για τις τρεις γενικές περιπτώσεις, ALL/ABROAD/GREECE
        normalizeGeneralArgs(args);
    
        // Αν δεν έχει δοθεί type, έρχονται τα συνολικά αποτελέσματα
        if (args == null || DpTextUtils.isEmpty(args.getType())) {
            predicate.and(result.type.eq(ResultType.ALL));
        } else {
            ResultType resultType = ResultType.valueOf(args.getType());
        
            // Αν δεν έχει δοθεί argId
            if (DpTextUtils.isEmpty(args.getArgId())) {
                if (resultType.equals(ResultType.ALL) || resultType.equals(ResultType.ABROAD) || resultType.equals(ResultType.GREECE)) {
                    // Αν το type είναι ALL/ABROAD/GREECE, βάζουμε στο argId το 0.
                    args.setArgId("0");
                } else {
                    // Αλλιώς δε φέρνει τίποτα
                    return null;
                }
            }
        
            predicate.and(result.type.eq(resultType));
            predicate.and(result.argId.eq(args.getArgId()));
        }
    
        // Έλεγχος εάν υπάρχει αποθηκευμένη η εγγραφή των αποτελεσμάτων στην Cache
        Result cacheResult = new Result(
            currentElectionProcedureDto.getResultsLastCalcDateTime(),
            currentElectionProcedureDto.getId(),
            ElectionRound.valueOf(currentElectionProcedureDto.getRound()),
            args != null && args.getType() != null ? ResultType.valueOf(args.getType()) : ResultType.ALL,
            args != null ? args.getArgId() : null);
    
        //  Το cacheManager είναι null όταν δεν είναι ενεργοποιημένο το memcached profile
        if (cacheManager != null) {
            Cache resultsCache = cacheManager.getCache(CacheService.RESULTS_CACHE);
            ResultsDto cachedResultsDto = resultsCache.get(cacheResult.computeCacheSignature(), ResultsDto.class);
        
            if (cachedResultsDto != null) {
                return cachedResultsDto;
            }
        }
    
        // Εάν δε βρεθεί στην Cache εκτελείται ερώτημα στη βάση
        query.from(result).where(predicate);
    
        Expression<ResultsDto> expression = getResultDtoConstructorExpression(result);
    
        return query.singleResult(expression);
    }
    
    /**
     * Μεταροπή των κριτηρίων αναζήτησης ως εξής:
     * (GENERAL / ALL) -> (ALL / 0)
     * (GENERAL / ABROAD) -> (ABROAD / 0)
     * (GENERAL / GREECE) -> (GREECE / 0)
     */
    private void normalizeGeneralArgs(ResultArgs args) {
        if(args != null && !DpTextUtils.isEmpty(args.getType()) && args.getType().equals("GENERAL")) {
            if(!DpTextUtils.isEmpty(args.getArgId())) {
                args.setType(args.getArgId());
                args.setArgId("0");
            }
        }
    }
    
    private Expression<ResultsDto> getResultDtoConstructorExpression(QResult result) {
        return ConstructorExpression.create(ResultsDto.class,
            result.id,
            result.totalVotes,
            result.whiteVotes,
            result.invalidVotes,
            result.validVotes,
            result.candidateOneVotes,
            result.candidateTwoVotes,
            result.candidateThreeVotes,
            result.candidateFourVotes,
            result.candidateFiveVotes,
            result.candidateSixVotes,
            result.candidateSevenVotes,
            result.candidateEightVotes,
            result.candidateNineVotes,
            result.candidateTenVotes,
            result.submittedDepartmentCount,
            result.totalDepartmentCount,
            result.calculationDateTime,
            result.nextCalculationDateTime);
    }
    
    @Override
    public void scheduleCalculateResultsJob(ScheduleJobCronDTO scheduleJobCronDTO) throws SchedulerException {
    
        if (DpTextUtils.isEmpty(scheduleJobCronDTO.getCronExpression())) {
            throw new DpValidationException("CRON_EXPRESSION_EMPTY", messageSourceProvider.getMessage("schedule.calculateResults.job.cronExpression.empty"));
        }
    
        if (!CronExpression.isValidExpression(scheduleJobCronDTO.getCronExpression())) {
            throw new DpValidationException("CRON_EXPRESSION_INVALID", messageSourceProvider.getMessage("schedule.calculateResults.job.cronExpression.invalid"));
        }
        
        JobDetail jobDetail = JobBuilder.newJob().ofType(CalculateResultsJob.class)
                .storeDurably()
                .withIdentity(UuidUtils.generateId(), SCHEDULE_CALCULATE_RESULTS_GROUP)
                .withDescription(SchedulingService.SCHEDULE_CALCULATE_RESULTS_JOB_DESCRIPTION)
                .build();
    
        String description = messageSourceProvider.getMessage("schedule.calculateResults.job", new Object[]{});
    
        jobDetail.getJobDataMap().put("scheduledByUser", UserUtils.getUser().getUsername());
        jobDetail.getJobDataMap().put("scheduleDate", new Date());
        jobDetail.getJobDataMap().put("description", description);
    
        // String cronExpression = CrDateUtils.convertDateToCronExpression(scheduleDate);
        String cronExpression = scheduleJobCronDTO.getCronExpression();
        
        CronTrigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(UuidUtils.generateId(), SCHEDULE_CALCULATE_RESULTS_GROUP)
                .withDescription(SchedulingService.SCHEDULE_CALCULATE_RESULTS_TRIGGER_DESCRIPTION)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    
        schedulingService.scheduleJob(jobDetail, trigger);
    }
    
    @Override
    public void unscheduleCalculateResultsJob() throws SchedulerException {
        schedulingService.unscheduleJob(SCHEDULE_CALCULATE_RESULTS_GROUP);
    }
    
    @Override
    public ScheduledJobTriggerDTO getCalculateResultsJobStatus() throws SchedulerException {
        return schedulingService.getJobStatus(SCHEDULE_CALCULATE_RESULTS_GROUP);
    }
}
