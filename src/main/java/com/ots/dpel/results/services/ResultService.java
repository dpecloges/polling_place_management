package com.ots.dpel.results.services;

import com.ots.dpel.results.args.ResultArgs;
import com.ots.dpel.results.dto.ResultsDto;
import com.ots.dpel.system.dto.ScheduleJobCronDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import org.quartz.SchedulerException;

public interface ResultService {
    
    void calculateResults() throws SchedulerException;
    
    ResultsDto searchResult(ResultArgs args);
    
    void scheduleCalculateResultsJob(ScheduleJobCronDTO scheduleJobCronDTO) throws SchedulerException;
    
    void unscheduleCalculateResultsJob() throws SchedulerException;
    
    ScheduledJobTriggerDTO getCalculateResultsJobStatus() throws SchedulerException;
}
