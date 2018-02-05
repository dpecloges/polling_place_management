package com.ots.dpel.config.scheduling;

import com.ots.dpel.global.utils.UuidUtils;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;
import com.ots.dpel.system.dto.ScheduledJobDTO;
import com.ots.dpel.system.services.SchedulingService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Scheduling Job Listener to override Scheduling Job Events
 */
public class DpJobListener extends JobListenerSupport {
    
    private static final Logger logger = LogManager.getLogger(DpJobListener.class);
    
    @Autowired
    private SchedulingService schedulingService;
    
    private String name;
    
    public DpJobListener() {
    }
    
    public DpJobListener(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        
        // Αναγνωριστικό χρονοπρογραμματισμένης εργασίας
        String jobName = context.getJobDetail().getKey().getName();
        
        getLog().info("Job to be Executed: " + jobName);
    }
    
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        
        // Αναγνωριστικό χρονοπρογραμματισμένης εργασίας
        String jobName = context.getJobDetail().getKey().getName();
        
        ScheduledJobDTO scheduledJobDTO = schedulingService.findScheduledJob(jobName);
        
        if (scheduledJobDTO != null && jobException != null) {
            
            // Ενημέρωση χρονοπρογραμματισμένης εργασίας ως "Απέτυχε"
            scheduledJobDTO.setStatus(ScheduledJobStatus.FAILED.name());
            scheduledJobDTO.setEndDate(new Date());
            
            // Δημιουργία αναγνωριστικού σφάλματος
            String errorId = UuidUtils.generateId();
            
            // Βασικό σφάλμα
            Throwable error = jobException.getCause() == null ? jobException :
                    (jobException.getCause().getCause() == null ? jobException.getCause() : jobException.getCause().getCause());
            String errorMessage = error.getMessage() == null ? error.toString() : error.getMessage();
            
            ThreadContext.put("errorId", errorId);
            ThreadContext.put("scheduledByUser", scheduledJobDTO.getScheduledBy());
            
            logger.error("Error Id: ".concat(errorId).concat(" - Message: ").concat(errorMessage), error);
            
            // Ενημέρωση στοιχείων σφάλματος χρονοπρογραμματισμένης εργασίας
            scheduledJobDTO.setErrorId(errorId);
            scheduledJobDTO.setErrorMessage(StringUtils.left(errorMessage, 250));
    
            ThreadContext.clearAll();
    
            schedulingService.saveScheduledJob(scheduledJobDTO);
        }
        
        getLog().info("Job was Executed: " + jobName);
    }
    
}
