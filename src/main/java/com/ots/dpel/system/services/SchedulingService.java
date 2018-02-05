package com.ots.dpel.system.services;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.system.core.domain.ScheduledJob;
import com.ots.dpel.system.dto.ScheduleJobDTO;
import com.ots.dpel.system.dto.ScheduledJobDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import com.ots.dpel.system.dto.list.ScheduledJobListDTO;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Interface λειτουργιών που αφορούν διαδικασίες χρονοπρογραμματισμού της εφαρμογής
 */
public interface SchedulingService {
    
    public static final int CALCULATE_DURATION_FROM_RECENTLY_JOBS = 5;
    public static final int CALCULATE_DURATION_HANDICAP_SECONDS = 10;
    public static final int KEEP_BATCHES_IN_CACHE = 3;
    
    public static final String SCHEDULE_CALCULATE_RESULTS_GROUP = "CALCULATE_RESULTS";
    public static final String SCHEDULE_CALCULATE_RESULTS_JOB_DESCRIPTION = "Calculate voting results";
    public static final String SCHEDULE_CALCULATE_RESULTS_TRIGGER_DESCRIPTION = "Trigger update";
    
    public static final String SCHEDULE_CALCULATE_SNAPSHOTS_GROUP = "CALCULATE_SNAPSHOTS";
    public static final String SCHEDULE_CALCULATE_SNAPSHOTS_JOB_DESCRIPTION = "Calculate voting snapshots";
    public static final String SCHEDULE_CALCULATE_SNAPSHOTS_TRIGGER_DESCRIPTION = "Trigger update";
    
    /**
     * Έναρξη Scheduler
     * Έναρξη εκτέλεσης προγραμματισμένων λειτουργιών
     */
    public void startScheduler() throws SchedulerException;
    
    /**
     * Παύση Scheduler
     * Παύση εκτέλεσης προγραμματισμένων λειτουργιών
     */
    public void standbyScheduler() throws SchedulerException;
    
    /**
     * Τερματισμός Scheduler
     * Τερματισμός εκτέλεσης προγραμματισμένων λειτουργιών
     */
    public void shutdownScheduler() throws SchedulerException;
    
    /**
     * Προγραμματισμός συγκεκριμένης λειτουργίας
     * @param jobDetail Λειτουργία
     * @param trigger   Ορισμός στοιχείων εκτέλεσης λειτουργίας
     */
    public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException;
    
    public void unscheduleJob(String jobGroup) throws SchedulerException;
    
    public ScheduledJobTriggerDTO getJobStatus(String jobGroup) throws SchedulerException;
    
    public Date getNextCalculationDateTime(String jobGroup) throws SchedulerException;
    
    /**
     * Έλεγχος ενεργοποίησης προγραμματισμού συγκεκριμένης λειτουργίας
     * @param scheduleJobDTO Στοιχεία προγραμματισμού
     * @return
     */
    public boolean jobShouldBeScheduled(ScheduleJobDTO scheduleJobDTO);
    
    /**
     * Αντιγραφή τιμών ιδιοτήτων από το αντικείμενο ScheduledJobDTO στο ScheduledJob entity
     * @param scheduledJobDto το αντικείμενο ScheduledJobDTO
     * @param scheduledJob    το entity ScheduledJob
     * @return
     */
    public ScheduledJob dtoToEntity(ScheduledJobDTO scheduledJobDto, ScheduledJob scheduledJob);
    
    /**
     * Αποθήκευση χρονοπρογραμματισμένης εργασίας
     * @param scheduledJobDto Το αντικείμενο της χρονοπρογραμματισμένης εργασίας
     * @return
     */
    public ScheduledJobDTO saveScheduledJob(ScheduledJobDTO scheduledJobDto);
    
    /**
     * Ανάκτηση αντικειμένου ScheduledJobDTO για μία χρονοπρογραμματισμένη εργασία
     * με βάση το αναγνωριστικό της
     * @param jobName το αναγνωριστικό της χρονοπρογραμματισμένης εργασίας
     * @return
     */
    public @NotNull(message = "{error.validation.sa.scheduled.job.isnull}")
    ScheduledJobDTO findScheduledJob(String jobName);
    
    /**
     * Ανάκτηση σελίδας προγραμματισμένων ενεργειών για το ευρετήριο
     */
    public Page<ScheduledJobListDTO> getScheduledJobsIndex(SearchableArguments arguments, Pageable pageable);
    
    /**
     * Ανάκτηση μεγαλύτερης χρονικής στιγμής εκτέλεσης προγραμματισμένης εργασίας
     * που βρίσκεται σε κατάσταση Προγραμματισμένη (SCHEDULED)
     * @param jobGroup Ομάδα προγραμματισμένων εργασιών
     * @return
     */
    public Date getMaxScheduledJobFireDateToRun(String jobGroup);
    
    /**
     * Ανάκτηση προτεινόμενης χρονικής εκτέλεσης προγραμματισμένης εργασίας
     * βάση κάποιων απλών κανόνων που ορίζονται στα διαστήματα στο αρχείο api.properties
     * 1. Εργάσιμες ημέρες εβδομάδας
     * 2. Μη εργάσιμες ημέρες εβδομάδας (σαββατοκύριακο)
     * @param jobGroup Ομάδα προγραμματισμένων εργασιών
     * @return
     */
    public Date getSuggestedScheduledJobNextFireDate(String jobGroup);
    
    public void createAndSaveScheduledJobCalculation(Long electionProcedureId, Date calculationDateTime, String jobGroup);

    public Date getOlderCalculationDateTime(Long electionProcedureId, String jobGroup, Integer interval);
}
