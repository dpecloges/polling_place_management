package com.ots.dpel.config.scheduling.jobs;

import com.ots.dpel.results.services.ResultService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class CalculateResultsJob implements Job {
    
    private static final Logger logger = LogManager.getLogger(CalculateResultsJob.class);
    
    @Autowired
    private ResultService resultService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        try {
            resultService.calculateResults();
        } catch (SchedulerException e) {
            logger.error("Scheduler Error while Calculating Results", e);
        }
    }
}
