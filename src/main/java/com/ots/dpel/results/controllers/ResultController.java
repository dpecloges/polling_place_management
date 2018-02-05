package com.ots.dpel.results.controllers;

import com.ots.dpel.results.args.ResultArgs;
import com.ots.dpel.results.dto.ResultsDto;
import com.ots.dpel.results.services.ResultService;
import com.ots.dpel.system.dto.ScheduleJobCronDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import com.ots.dpel.system.services.SchedulingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/result")
public class ResultController {
    
    private static final Logger logger = LogManager.getLogger(ResultController.class);
    
    @Autowired
    private ResultService resultService;
    
    @PreAuthorize("hasAuthority('rs.result')")
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public @ResponseBody ResultsDto searchResult(@RequestBody ResultArgs args) {
        return resultService.searchResult(args);
    }
    
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "schedule", method = RequestMethod.POST)
    public ScheduledJobTriggerDTO scheduleCalculateResultsJob(@RequestBody ScheduleJobCronDTO scheduleJobCronDTO) throws SchedulerException {
        
        resultService.scheduleCalculateResultsJob(scheduleJobCronDTO);
        return resultService.getCalculateResultsJobStatus();
    }
    
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "unschedule", method = RequestMethod.GET)
    public ScheduledJobTriggerDTO unscheduleJob() throws SchedulerException {
        
        resultService.unscheduleCalculateResultsJob();
        return new ScheduledJobTriggerDTO(SchedulingService.SCHEDULE_CALCULATE_RESULTS_GROUP, Boolean.FALSE);
    }
    
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "jobstatus", method = RequestMethod.GET)
    public ScheduledJobTriggerDTO getCalculateResultsJobStatus() throws SchedulerException {
        return resultService.getCalculateResultsJobStatus();
    }
}
