package com.ots.dpel.config.scheduling;

import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;
import com.ots.dpel.system.dto.ScheduledJobDTO;
import com.ots.dpel.system.services.SchedulingService;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Scheduler Listener to override Scheduler Events
 */
public class DpSchedulerListener extends SchedulerListenerSupport {
    
    @Autowired
    private SchedulingService schedulingService;
    
    @Override
    public void jobAdded(JobDetail jobDetail) {
        
        // Αναγνωριστικό χρονοπρογραμματισμένης εργασίας
        String jobName = jobDetail.getKey().getName();
        
        getLog().info("Job Added: " + jobName);
    }
    
    @Override
    public void jobScheduled(Trigger trigger) {
        
        // Αναγνωριστικό χρονοπρογραμματισμένης εργασίας
        String jobName = trigger.getJobKey().getName();
        
        getLog().info("job Scheduled: " + jobName);
    }
    
}
