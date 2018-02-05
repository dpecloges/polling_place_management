package com.ots.dpel.system.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.global.dto.ExportDataDTO;
import com.ots.dpel.global.services.ExportService;
import com.ots.dpel.system.args.ScheduledJobArgs;
import com.ots.dpel.system.dto.SchedulingResponse;
import com.ots.dpel.system.dto.list.ScheduledJobListDTO;
import com.ots.dpel.system.services.SchedulingService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

/**
 * Controller για τις κλήσεις που αφορούν τις Προγραμματισμένες Εργασίες
 */
@Controller
@RequestMapping("/system/scheduling")
public class SchedulingController {
    
    private static final Logger logger = LogManager.getLogger(SchedulingController.class);
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private SchedulingService schedulingService;
    
    @Autowired
    private ExportService exportService;
    
    /**
     * Ευρετήριο προγραμματισμένων εργασιών
     */
    @PreAuthorize("hasPermission(#principal, 'sys.admin')")
    @RequestMapping(value = "jobs/index", method = RequestMethod.GET)
    public @ResponseBody
    Page<ScheduledJobListDTO> getScheduledJobsIndex(HttpServletRequest request, Pageable pageable) {
        
        String searchArgs = request.getParameter("searchString");
        
        ScheduledJobArgs args = new ScheduledJobArgs();
        
        if (searchArgs != null) {
            try {
                args = mapper.readValue(searchArgs, ScheduledJobArgs.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return schedulingService.getScheduledJobsIndex(args, pageable);
    }
    
    /**
     * Εξαγωγή ευρετηρίου σε Excel
     */
    @PreAuthorize("hasPermission(#principal, 'sys.admin')")
    @RequestMapping(value = "jobs/excel", method = RequestMethod.POST)
    public void exportIndexToExcel(@RequestBody ExportDataDTO exportParams, HttpServletRequest request, HttpServletResponse response) {
    
        exportParams.setTitle(request.getParameter("title"));
        exportParams.setSortColumn(request.getParameter("sidx"));
        exportParams.setSortOrder(request.getParameter("sord"));
        
        Pageable pageable = exportService.getExportPageable(exportParams);
        
        Page<ScheduledJobListDTO> indexData = getScheduledJobsIndex(request, pageable);
        
        try {
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            exportService.listToExcelFile(exportParams.getTitle(), indexData.getContent(), exportParams.getModel(), outputStream);
            
        } catch (IOException e) {
            logger.error("Error while initializing Excel file creation", e);
        }
    }
    
}
