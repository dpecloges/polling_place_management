package com.ots.dpel.system.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.config.beans.ApiConfigBean;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.global.utils.UserUtils;
import com.ots.dpel.system.args.ScheduledJobArgs;
import com.ots.dpel.system.core.domain.QScheduledJob;
import com.ots.dpel.system.core.domain.QScheduledJobCalculation;
import com.ots.dpel.system.core.domain.ScheduledJob;
import com.ots.dpel.system.core.domain.ScheduledJobCalculation;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;
import com.ots.dpel.system.dto.ScheduleJobDTO;
import com.ots.dpel.system.dto.ScheduledJobDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import com.ots.dpel.system.dto.list.QScheduledJobListDTO;
import com.ots.dpel.system.dto.list.ScheduledJobListDTO;
import com.ots.dpel.system.persistence.ScheduledJobCalculationRepository;
import com.ots.dpel.system.persistence.ScheduledJobRepository;
import com.ots.dpel.system.services.SchedulingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.ots.dpel.system.predicates.ScheduledJobPredicates.createScheduledJobsIndexPredicate;

/**
 * Υλοποίηση λειτουργιών που αφορούν διαδικασίες χρονοπρογραμματισμού της εφαρμογής
 */
@Service
public class SchedulingServiceImpl implements SchedulingService {
    
    private static final Logger logger = LogManager.getLogger(SchedulingServiceImpl.class);
    
    @Autowired
    private Scheduler scheduler;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Autowired
    private ApiConfigBean apiConfigBean;
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ScheduledJobRepository scheduledJobRepository;
    
    @Autowired
    private ScheduledJobCalculationRepository scheduledJobCalculationRepository;
    
    @Override
    public void startScheduler() throws SchedulerException {
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
    }
    
    @Override
    public void standbyScheduler() throws SchedulerException {
        if (!scheduler.isInStandbyMode()) {
            scheduler.standby();
        }
    }
    
    @Override
    public void shutdownScheduler() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    
    @Override
    public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(jobDetail, trigger);
    }
    
    @Override
    public void unscheduleJob(String jobGroup) throws SchedulerException {
    
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(jobGroup));
    
        for (JobKey jobKey : jobKeys) {
            
            List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
    
            for (Trigger trigger : triggersOfJob) {
                scheduler.unscheduleJob(trigger.getKey());
            }
        }
    }
    
    @Override
    public ScheduledJobTriggerDTO getJobStatus(String jobGroup) throws SchedulerException {
    
        ScheduledJobTriggerDTO scheduledJobTriggerDTO = new ScheduledJobTriggerDTO(jobGroup, Boolean.FALSE);
        
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(jobGroup));
    
        for (JobKey jobKey : jobKeys) {
        
            List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
    
            if (triggersOfJob != null && triggersOfJob.size() > 0) {
                scheduledJobTriggerDTO.setPending(Boolean.TRUE);
                scheduledJobTriggerDTO.setPreviousFireDate(triggersOfJob.get(0).getPreviousFireTime());
                scheduledJobTriggerDTO.setNextFireDate(triggersOfJob.get(0).getNextFireTime());
                return scheduledJobTriggerDTO;
            }
        }
        
        return scheduledJobTriggerDTO;
    }
    
    @Override
    public Date getNextCalculationDateTime(String jobGroup) throws SchedulerException {
    
        Date nextCalculationDateTime = null;
        
        ScheduledJobTriggerDTO scheduledJobTriggerDTO = getJobStatus(jobGroup);
        
        if (scheduledJobTriggerDTO != null) {
            Date nextFireDate = scheduledJobTriggerDTO.getNextFireDate();
            // addSecondsToDate
            Long calculatedJobDuration = getRecentlyCalculatedJobDuration(jobGroup);
            if (nextFireDate != null && calculatedJobDuration != null && calculatedJobDuration > 0) {
                nextCalculationDateTime = DpDateUtils.addSecondsToDate(nextFireDate, calculatedJobDuration.intValue());
                if (nextCalculationDateTime != null) {
                    nextCalculationDateTime = DpDateUtils.addSecondsToDate(nextCalculationDateTime, CALCULATE_DURATION_HANDICAP_SECONDS);
                }
            }
        }
        
        return nextCalculationDateTime;
    }
    
    @Override
    public boolean jobShouldBeScheduled(ScheduleJobDTO scheduleJobDTO) {
        
        // Εάν δεν υπάρχουν στοιχεία ή δεν είναι ενεργοποιημένη η ένδειξη χρονοπρογραμματισμού
        // δε θα προγραμματιστεί η εκτέλεση της λειτουργίας
        if (scheduleJobDTO == null || scheduleJobDTO.getScheduleJob() == null || !scheduleJobDTO.getScheduleJob()) {
            return false;
        }
        
        // Έλεγχος ύπαρξης χρονικής στιγμής εκτέλεσης της λειτουργίας
        if (scheduleJobDTO.getScheduleDate() == null) {
            throw new DpValidationException("Schedule date of job is not defined.",
                    messageSourceProvider.getMessage("error.schedule.job.scheduleDate.notDefined"));
        }
        
        // Έλεγχος ότι η χρονική στιγμή εκτέλεσης της λειτουργίας βρίσκεται στο μέλλον
        if (DpDateUtils.dateInPast(scheduleJobDTO.getScheduleDate())) {
            throw new DpValidationException("Schedule date of job is in past.",
                    messageSourceProvider.getMessage("error.schedule.job.scheduleDate.inPast"));
        }
        
        return true;
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ScheduledJob dtoToEntity(ScheduledJobDTO scheduledJobDto, ScheduledJob scheduledJob) {
        
        if (scheduledJob == null) {
            scheduledJob = new ScheduledJob();
        }
        
        BeanUtils.copyProperties(scheduledJobDto, scheduledJob);
        
        scheduledJob.setStatus(scheduledJobDto.getStatus() != null ? ScheduledJobStatus.valueOf(scheduledJobDto.getStatus()) : null);
        
        return scheduledJob;
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ScheduledJobDTO saveScheduledJob(ScheduledJobDTO scheduledJobDto) {
        
        Boolean isScheduledJobNew = (scheduledJobDto.getId() == null);
        
        //Δημιουργία νέου ή ανάκτηση υπάρχουσας εγγραφής
        ScheduledJob scheduledJob = (isScheduledJobNew) ? new ScheduledJob() : scheduledJobRepository.findOne(scheduledJobDto.getId());
        
        //Μεταφορά των στοιχείων στο entity
        dtoToEntity(scheduledJobDto, scheduledJob);
        
        //Αποθήκευση
        scheduledJobRepository.save(scheduledJob);
        
        //Ορισμός των τιμών για επιπλέον πεδία
        setExtraFieldValues(scheduledJob, scheduledJobDto);
        
        //Επιστροφή του DTO
        return scheduledJobDto;
    }
    
    /**
     * Ορισμός (Μεταφορά) τιμών σε πεδία του ScheduledJobDTO από το ScheduledJob entity
     * @param scheduledJob
     * @param scheduledJobDTO
     */
    private void setExtraFieldValues(ScheduledJob scheduledJob, ScheduledJobDTO scheduledJobDTO) {
        
        scheduledJobDTO.setId(scheduledJob.getId());
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ScheduledJobDTO findScheduledJob(String jobName) {
        
        QScheduledJob scheduledJob = QScheduledJob.scheduledJob;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(scheduledJob).where(scheduledJob.jobName.eq(jobName).and(scheduledJob.status.eq(ScheduledJobStatus.RUNNING)));
        
        Expression<ScheduledJobDTO> expression = ConstructorExpression.create(ScheduledJobDTO.class, scheduledJob.id, scheduledJob.jobName,
                scheduledJob.jobGroup, scheduledJob.description, scheduledJob.scheduledBy, scheduledJob.scheduleDate, scheduledJob.fireDate,
                scheduledJob.status, scheduledJob.startDate, scheduledJob.endDate, scheduledJob.errorId, scheduledJob.errorMessage, scheduledJob
                        .fromJobName);
        
        return query.singleResult(expression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public Page<ScheduledJobListDTO> getScheduledJobsIndex(SearchableArguments arguments, Pageable pageable) {
        
        DpUserDetailsDTO userDetails = UserUtils.getUser();
        ScheduledJobArgs args = (ScheduledJobArgs) arguments;

        //Τα entities που θα χρησιμοποιηθούν για να δημιουργηθεί το ερώτημα
        QScheduledJob scheduledJob = QScheduledJob.scheduledJob;

        //Δημιουργία του αντικειμένου του ερωτήματος
        JPQLQuery query = new JPAQuery(entityManager);

        //Δημιουργία των παραμέτρων του where του ερωτήματος
        BooleanBuilder predicate = createScheduledJobsIndexPredicate(arguments);

        //Δημιουργία του ερωτήματος χρησιμοποιώντας QueryDsl
        query.from(scheduledJob).where(predicate);

        //Καθορισμός της μορφής των αποτελεσμάτων του ερωτήματος (στήλες που θα περιλαμβάνονται στο select)
        FactoryExpression<ScheduledJobListDTO> factoryExpression = new QScheduledJobListDTO(
                scheduledJob.id, scheduledJob.jobName, scheduledJob.description, scheduledJob.scheduledBy,
                scheduledJob.scheduleDate, scheduledJob.fireDate, scheduledJob.status, scheduledJob.startDate,
                scheduledJob.endDate, scheduledJob.errorId, scheduledJob.errorMessage, scheduledJob.fromJobName
        );

        //Ανάκτηση σελίδας αποτελεσμάτων
        Page<ScheduledJobListDTO> page = scheduledJobRepository.findAll(query, factoryExpression, pageable);
        
        return page;
    }
    
    @Override
    public Date getMaxScheduledJobFireDateToRun(String jobGroup) {
        
        QScheduledJob scheduledJob = QScheduledJob.scheduledJob;

        JPQLQuery query = new JPAQuery(entityManager);

        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(scheduledJob.jobGroup.eq(jobGroup));
        predicate.and(scheduledJob.status.eq(ScheduledJobStatus.SCHEDULED));
        predicate.and(scheduledJob.fireDate.after(new Date()));

        Date fireDate = query.from(scheduledJob).where(predicate).singleResult(scheduledJob.fireDate.max());

        return fireDate;
    }
    
    @Override
    public Date getSuggestedScheduledJobNextFireDate(String jobGroup) {
        
        // // Ανάκτηση μεγαλύτερης ημερομηνίας εκτέλεσης χρονοπρογραμματισμένης εργασίας
        // // που βρίσκεται σε κατάσταση Προγραμματισμένη
        // Date alreadyScheduledMaxFireDate = getMaxScheduledJobFireDateToRun(jobGroup);
        //
        // // Επιτρεπόμενα χρονικά διαστήματα
        // CronSequenceGenerator midweekSequenceGenerator = new CronSequenceGenerator(apiConfigBean.getSuggestNextFireDateMidweekRange());
        // CronSequenceGenerator weekendSequenceGenerator = new CronSequenceGenerator(apiConfigBean.getSuggestNextFireDateWeekendRange());
        //
        // // Προσθήκη παραμετρικού χρονικού interval
        // Date startDate = alreadyScheduledMaxFireDate == null ? new Date() : DateUtils.addSeconds(alreadyScheduledMaxFireDate, apiConfigBean
        // .getSuggestNextFireDateInterval());
        //
        // // Ανάκτηση επόμενων τιμών προς trigger
        // Date midweekNextFireDate = midweekSequenceGenerator.next(startDate);
        // Date weekendNextFireDate = weekendSequenceGenerator.next(startDate);
        //
        // logger.trace("Calculate suggested next fire date for job group {} - midweek: {} - weekend: {}",
        //         jobGroup, midweekNextFireDate, weekendNextFireDate);
        //
        // Date suggestedDate = null;
        //
        // if (midweekNextFireDate == null && weekendNextFireDate == null) {
        //     return suggestedDate;
        // }
        //
        // // Εάν δεν έχει υπολογιστεί τιμή στην ημερομηνία που προκύπτει για τις ημέρες εντός της εβδομάδας
        // // ή η ημερομηνία εντός της εβδομάδας είναι προγενέστερη εκείνης του σαββατοκύριακου
        // // επιστρέφεται η ημερομηνία εντός της εβδομάδας
        // // Σε αντίθετη περίπτωση επιστρέφεται η ημερομηνία του σαββατοκύριακου
        // if (midweekNextFireDate == null || weekendNextFireDate.before(midweekNextFireDate)) {
        //     suggestedDate = weekendNextFireDate;
        // }
        // else if (weekendNextFireDate == null || midweekNextFireDate.before(weekendNextFireDate)) {
        //     suggestedDate = midweekNextFireDate;
        // }
        //
        // return suggestedDate;
        
        return null;
    }
    
    private Long getRecentlyCalculatedJobDuration(String jobGroup) {
    
        //Τα entities που θα χρησιμοποιηθούν για να δημιουργηθεί το ερώτημα
        QScheduledJob scheduledJob = QScheduledJob.scheduledJob;
        
        //Δημιουργία του αντικειμένου του ερωτήματος
        JPQLQuery query = new JPAQuery(entityManager);
        
        //Δημιουργία των παραμέτρων του where του ερωτήματος
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(scheduledJob.jobGroup.eq(jobGroup));
        predicate.and(scheduledJob.status.eq(ScheduledJobStatus.COMPLETED));
        
        //Δημιουργία του ερωτήματος χρησιμοποιώντας QueryDsl
        query.from(scheduledJob).where(predicate).orderBy(scheduledJob.fireDate.desc());
        
        //Καθορισμός της μορφής των αποτελεσμάτων του ερωτήματος (στήλες που θα περιλαμβάνονται στο select)
        FactoryExpression<ScheduledJobListDTO> factoryExpression = new QScheduledJobListDTO(
                scheduledJob.id, scheduledJob.jobName, scheduledJob.description, scheduledJob.scheduledBy,
                scheduledJob.scheduleDate, scheduledJob.fireDate, scheduledJob.status, scheduledJob.startDate,
                scheduledJob.endDate, scheduledJob.errorId, scheduledJob.errorMessage, scheduledJob.fromJobName
        );
        
        //Ανάκτηση σελίδας αποτελεσμάτων
        Page<ScheduledJobListDTO> page = scheduledJobRepository.findAll(query, factoryExpression, new PageRequest(0, CALCULATE_DURATION_FROM_RECENTLY_JOBS));
        
        Long calculatedJobDuration = 0L;
    
        if (page.getContent() != null && page.getContent().size() > 0) {
            for (ScheduledJobListDTO scheduledJobListDTO : page.getContent()) {
                Long secondsBetweenDates = DpDateUtils.getSecondsBetweenDates(scheduledJobListDTO.getStartDate(), scheduledJobListDTO.getEndDate());
                if (secondsBetweenDates != null) {
                    calculatedJobDuration += secondsBetweenDates;
                }
            }
            calculatedJobDuration = calculatedJobDuration / page.getContent().size();
        }
        
        return calculatedJobDuration;
    }
    
    @Override
    public void createAndSaveScheduledJobCalculation(Long electionProcedureId, Date calculationDateTime, String jobGroup) {
    
        ScheduledJobCalculation scheduledJobCalculation = new ScheduledJobCalculation();
        
        scheduledJobCalculation.setElectionProcedureId(electionProcedureId);
        scheduledJobCalculation.setCalculationDateTime(calculationDateTime);
        scheduledJobCalculation.setJobGroup(jobGroup);
        scheduledJobCalculation.setOrder(getMaxScheduledJobCalculationCount(jobGroup) + 1);
    
        scheduledJobCalculationRepository.save(scheduledJobCalculation);
    }
    
    private Integer getMaxScheduledJobCalculationCount(String jobGroup) {
    
        QScheduledJobCalculation scheduledJobCalculation = QScheduledJobCalculation.scheduledJobCalculation;

        JPQLQuery query = new JPAQuery(entityManager);
    
        Integer maxOrder = query
                .from(scheduledJobCalculation)
                .where(scheduledJobCalculation.jobGroup.eq(jobGroup))
                .singleResult(scheduledJobCalculation.order.max());
        
        return maxOrder == null ? 0 : maxOrder;
    }
    
    @Override
    public Date getOlderCalculationDateTime(Long electionProcedureId, String jobGroup, Integer interval) {
    
        if (interval == null || interval <= 0) {
            return null;
        }
    
        Integer maxScheduledJobCalculationCount = getMaxScheduledJobCalculationCount(jobGroup);
        if (maxScheduledJobCalculationCount == null || maxScheduledJobCalculationCount <= 0) {
            return null;
        }
        
        Integer olderOrder = maxScheduledJobCalculationCount - interval;
        if (olderOrder == null || olderOrder <= 0) {
            return null;
        }
    
        QScheduledJobCalculation scheduledJobCalculation = QScheduledJobCalculation.scheduledJobCalculation;
    
        JPQLQuery query = new JPAQuery(entityManager);
    
        Date olderCalculationDateTime = query
                .from(scheduledJobCalculation)
                .where(scheduledJobCalculation.jobGroup.eq(jobGroup)
                        .and(scheduledJobCalculation.order.eq(olderOrder)))
                .singleResult(scheduledJobCalculation.calculationDateTime);
        
        return olderCalculationDateTime;
    }
}
