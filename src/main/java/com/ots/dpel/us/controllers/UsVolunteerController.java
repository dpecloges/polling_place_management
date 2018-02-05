package com.ots.dpel.us.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.global.dto.ExportDataDTO;
import com.ots.dpel.global.services.ExportService;
import com.ots.dpel.us.args.UsVolunteerArgs;
import com.ots.dpel.us.dto.ManualUserCreationRequest;
import com.ots.dpel.us.dto.VolunteerAssignmentRequestDto;
import com.ots.dpel.us.dto.VolunteerReassignmentRequestDto;
import com.ots.dpel.us.dto.list.UsVolunteerExportListDto;
import com.ots.dpel.us.dto.list.UsVolunteerListDto;
import com.ots.dpel.us.services.UsVolunteerService;

@RestController
@RequestMapping("/us/volunteer")
public class UsVolunteerController {
    
    private static final Logger logger = LogManager.getLogger(UsVolunteerController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UsVolunteerService usVolunteerService;
    
    @Autowired
    private ExportService exportService;
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public @ResponseBody Page<UsVolunteerListDto> index(HttpServletRequest request, Pageable pageable) {
        String searchArgs = request.getParameter("searchString");
        UsVolunteerArgs args = new UsVolunteerArgs();
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, UsVolunteerArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return usVolunteerService.getUsVolunteerIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "assign", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> assignVolunteer(
            @RequestBody VolunteerAssignmentRequestDto assignment) {
        
        this.usVolunteerService.assignVolunteer(assignment);

        return ResponseEntity.ok(true);
    }
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "unassign/{volunteerId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> unassignVolunteer(
            @PathVariable("volunteerId") Long volunteerId) {
        
        this.usVolunteerService.unassignVolunteer(volunteerId);

        return ResponseEntity.ok(true);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "notify/{volunteerId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> notifyVolunteer(
            @PathVariable("volunteerId") Long volunteerId) {
        
        this.usVolunteerService.notifyVolunteer(volunteerId);

        return ResponseEntity.ok(true);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "notifyall", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> notifyAllVolunteers(
            @RequestParam("pending") Boolean pending,
            @RequestParam("notified") Boolean notified) {
        
        this.usVolunteerService.notifyAll(pending, notified);
        return ResponseEntity.ok(true);
    }
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "excel", method = RequestMethod.POST)
    public void exportIndexToExcel(@RequestBody ExportDataDTO exportParams, HttpServletRequest request, HttpServletResponse response) {
    
        exportParams.setTitle(request.getParameter("title"));
        exportParams.setSortColumn(request.getParameter("sidx"));
        exportParams.setSortOrder(request.getParameter("sord"));
        
        Pageable pageable = exportService.getExportPageable(exportParams);
        
        Page<UsVolunteerListDto> indexData = index(request, pageable);
        List<UsVolunteerExportListDto> exportData = UsVolunteerExportListDto.ofUsVolunteerListDtos(indexData.getContent());
        
        try {
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            exportService.listToExcelFile(exportParams.getTitle(), exportData, exportParams.getModel(), outputStream);
            
        } catch (IOException e) {
            logger.error("Error while initializing Excel file creation", e);
        }
    }
    
    @PreAuthorize("hasAuthority('sa.user')")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> manuallyCreateUser(
            @RequestBody ManualUserCreationRequest userCreation) {
        
        this.usVolunteerService.manuallyCreateUser(userCreation);

        return ResponseEntity.ok(true);
    }
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "reassign", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> reassignVolunteer(
            @RequestBody VolunteerReassignmentRequestDto reassignment) {
        
        this.usVolunteerService.reassignVolunteer(reassignment);
        return ResponseEntity.ok(true);
    }
}
