package com.ots.dpel.config.scheduling.jobs;

import com.ots.dpel.ep.services.SnapshotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class CalculateSnapshotsJob implements Job {
    
    private static final Logger logger = LogManager.getLogger(CalculateSnapshotsJob.class);
    
    @Autowired
    private SnapshotService snapshotService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        try {
            snapshotService.calculateSnapshots();
        } catch (SchedulerException e) {
            logger.error("Scheduler Error while Calculating Snapshots", e);
        }
    }
}
