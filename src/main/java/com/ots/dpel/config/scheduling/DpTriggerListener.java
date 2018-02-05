package com.ots.dpel.config.scheduling;

import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;
import com.ots.dpel.system.dto.ScheduledJobDTO;
import com.ots.dpel.system.services.SchedulingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Scheduling Trigger Listener to override Scheduling Trigger Events
 */
public class DpTriggerListener extends TriggerListenerSupport {
    
    private static final Logger logger = LogManager.getLogger(DpTriggerListener.class);
    
    @Autowired
    private SchedulingService schedulingService;
    
    private String name;
    
    public DpTriggerListener() {
    }
    
    public DpTriggerListener(String name) {
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
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        
        JobDetail jobDetail = context.getJobDetail();
        
        String jobName = jobDetail.getKey().getName();
        String jobGroup = jobDetail.getKey().getGroup();
        Date triggerFiredDate = new Date();
        
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        
        String scheduledByUser = (String) jobDataMap.get("scheduledByUser");
        Date scheduleDate = (Date) jobDataMap.get("scheduleDate");
        String description = (String) jobDataMap.get("description");
        String fromJobName = (String) jobDataMap.get("fromJobName");
        
        // Δημιουργία χρονοπρογραμματισμένης εργασίας ως "Δημιουργήθηκε"
        ScheduledJobDTO scheduledJobDTO = new ScheduledJobDTO();
        scheduledJobDTO.setJobName(jobName);
        scheduledJobDTO.setJobGroup(jobGroup);
        scheduledJobDTO.setDescription(description.concat(": ").concat(DpDateUtils.getDateTimeTillSecondsPlainString(triggerFiredDate)));
        scheduledJobDTO.setScheduledBy(scheduledByUser);
        scheduledJobDTO.setScheduleDate(scheduleDate);
        scheduledJobDTO.setStatus(ScheduledJobStatus.RUNNING.name());
        scheduledJobDTO.setFireDate(triggerFiredDate);
        scheduledJobDTO.setStartDate(triggerFiredDate);
        scheduledJobDTO.setFromJobName(DpTextUtils.isEmpty(fromJobName) ? null : fromJobName);
        
        schedulingService.saveScheduledJob(scheduledJobDTO);
        
        getLog().info("Job Trigger Fired: " + context.getJobDetail().getKey().getName());
    }
    
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        
        JobDetail jobDetail = context.getJobDetail();
        
        String jobName = jobDetail.getKey().getName();
        String jobGroup = jobDetail.getKey().getGroup();
        
        ScheduledJobDTO scheduledJobDTO = schedulingService.findScheduledJob(jobName);
        
        if (scheduledJobDTO != null) {
            
            // Ενημέρωση χρονοπρογραμματισμένης εργασίας ως "Ολοκληρώθηκε"
            scheduledJobDTO.setStatus(ScheduledJobStatus.COMPLETED.name());
            scheduledJobDTO.setEndDate(new Date());
            
            schedulingService.saveScheduledJob(scheduledJobDTO);
        }
        
        getLog().info("Job Trigger Complete: " + context.getJobDetail().getKey().getName());
    }
}
