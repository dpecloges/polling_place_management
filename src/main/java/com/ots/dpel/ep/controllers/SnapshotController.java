package com.ots.dpel.ep.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.ep.args.SnapshotArgs;
import com.ots.dpel.ep.args.StatisticsArgs;
import com.ots.dpel.ep.dto.SnapshotsDto;
import com.ots.dpel.ep.services.SnapshotService;
import com.ots.dpel.global.dto.ExportDataDTO;
import com.ots.dpel.global.services.ExportService;
import com.ots.dpel.system.dto.ScheduleJobCronDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import com.ots.dpel.system.services.SchedulingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ep/snapshot")
public class SnapshotController {
    
    private static final Logger logger = LogManager.getLogger(SnapshotController.class);
    
    @Autowired
    private SnapshotService snapshotService;
    
    @Autowired
    private ExportService exportService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PreAuthorize("hasAuthority('ep.snapshot')")
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public @ResponseBody
    SnapshotsDto searchSnapshot(@RequestBody SnapshotArgs args) {
        return snapshotService.searchSnapshot(args);
    }
    
    @PreAuthorize("hasAuthority('ep.snapshot')")
    @RequestMapping(value = "findall", method = RequestMethod.POST)
    public @ResponseBody
    List<SnapshotsDto> findAllSnapshots(@RequestBody SnapshotArgs args) {
        return snapshotService.findAllSnapshots(args);
    }
    
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "schedule", method = RequestMethod.POST)
    public ScheduledJobTriggerDTO scheduleCalculateSnapshotsJob(@RequestBody ScheduleJobCronDTO scheduleJobCronDTO) throws SchedulerException {
        
        snapshotService.scheduleCalculateSnapshotsJob(scheduleJobCronDTO);
        return snapshotService.getCalculateSnapshotsJobStatus();
    }
    
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "unschedule", method = RequestMethod.GET)
    public ScheduledJobTriggerDTO unscheduleJob() throws SchedulerException {
        
        snapshotService.unscheduleCalculateSnapshotsJob();
        return new ScheduledJobTriggerDTO(SchedulingService.SCHEDULE_CALCULATE_SNAPSHOTS_GROUP, Boolean.FALSE);
    }
    
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(value = "jobstatus", method = RequestMethod.GET)
    public ScheduledJobTriggerDTO getCalculateSnapshotsJobStatus() throws SchedulerException {
        return snapshotService.getCalculateSnapshotsJobStatus();
    }
    
    @PreAuthorize("hasAuthority('ep.snapshot')")
    @RequestMapping(value = "statistics/index", method = RequestMethod.GET)
    public @ResponseBody
    Page<SnapshotsDto> getStatisticsIndex(HttpServletRequest request, Pageable pageable) {
        String searchArgs = request.getParameter("searchString");
        
        StatisticsArgs args = new StatisticsArgs();
        
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, StatisticsArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return snapshotService.getStatisticsIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('ep.snapshot')")
    @RequestMapping(value = "statistics/excel", method = RequestMethod.POST)
    public void exportIndexToExcel(@RequestBody ExportDataDTO exportParams, HttpServletRequest request, HttpServletResponse response) {
        
        exportParams.setTitle(request.getParameter("title"));
        exportParams.setSortColumn(request.getParameter("sidx"));
        exportParams.setSortOrder(request.getParameter("sord"));
        
        Pageable pageable = exportService.getExportPageable(exportParams);
        
        Page<SnapshotsDto> indexData = getStatisticsIndex(request, pageable);
        
        try {
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            exportService.listToExcelFile(exportParams.getTitle(), indexData.getContent(), exportParams.getModel(), outputStream);
            
        } catch (IOException e) {
            logger.error("Error while initializing Excel file creation", e);
        }
    }
}
