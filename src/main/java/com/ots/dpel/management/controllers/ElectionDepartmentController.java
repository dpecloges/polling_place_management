package com.ots.dpel.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.global.dto.ExportDataDTO;
import com.ots.dpel.global.services.ExportService;
import com.ots.dpel.management.args.ElectionDepartmentArgs;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.ElectionDepartmentDto;
import com.ots.dpel.management.dto.list.ElectionDepartmentIndexListDto;
import com.ots.dpel.management.dto.list.ElectionDepartmentListDto;
import com.ots.dpel.management.services.ContributionService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/mg/electiondepartment")
public class ElectionDepartmentController {
    
    private static final Logger logger = LogManager.getLogger(ElectionDepartmentController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private ContributionService contributionService;
    
    @Autowired
    private ExportService exportService;
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "find/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ElectionDepartmentDto findElectionDepartment(@PathVariable("id") Long id) {
        return electionDepartmentService.findElectionDepartment(id);
    }
    
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody
    ElectionDepartmentDto saveElectionDepartment(@RequestBody ElectionDepartmentDto electionDepartmentDto) {
        return electionDepartmentService.saveElectionDepartment(electionDepartmentDto);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    void deleteElectionDepartment(@PathVariable("id") Long id) {
        electionDepartmentService.deleteElectionDepartment(id);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public @ResponseBody
    Page<ElectionDepartmentListDto> getIndex(HttpServletRequest request, Pageable pageable) {
        String searchArgs = request.getParameter("searchString");
        ElectionDepartmentArgs args = new ElectionDepartmentArgs();
        
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, ElectionDepartmentArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return electionDepartmentService.getIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "generateserialnoandcode/{electionCenterId}", method = RequestMethod.GET)
    public @ResponseBody
    ElectionDepartmentDto generateSerialNoAndCode(@PathVariable("electionCenterId") Long electionCenterId) {
        return electionDepartmentService.generateSerialNoAndCode(electionCenterId);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "findall", method = RequestMethod.GET)
    public @ResponseBody List<ElectionDepartmentBasicDto> findAll() {
        return electionDepartmentService.findAllBasic();
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "find/electioncenter/{electionCenterId}", method = RequestMethod.GET)
    public @ResponseBody
    List<ElectionDepartmentListDto> getByElectionCenter(@PathVariable("electionCenterId") Long electionCenterId) {
        return electionDepartmentService.getByElectionCenter(electionCenterId);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "volunteer/{volunteerId}/round/{round}", method = RequestMethod.GET)
    public @ResponseBody List<ElectionDepartmentBasicDto> getByVolunteer(@PathVariable("volunteerId") Long volunteerId,
                                                    @PathVariable("round") ElectionRound round,
                                                    @RequestParam(name = "electionDepartmentId", required = false) Long electionDepartmentId) {
        return contributionService.getVolunteerElectionDepartments(volunteerId, round, electionDepartmentId);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "fullindex", method = RequestMethod.GET)
    public @ResponseBody Page<ElectionDepartmentIndexListDto> getElectionDepartmentIndex(HttpServletRequest request, Pageable pageable) {
        
        String searchArgs = request.getParameter("searchString");
        
        ElectionDepartmentArgs args = new ElectionDepartmentArgs();
        
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, ElectionDepartmentArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return electionDepartmentService.getElectionDepartmentIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('mg.electiondepartment')")
    @RequestMapping(value = "excel", method = RequestMethod.POST)
    public void exportIndexToExcel(@RequestBody ExportDataDTO exportParams, HttpServletRequest request, HttpServletResponse response) {
    
        exportParams.setTitle(request.getParameter("title"));
        exportParams.setSortColumn(request.getParameter("sidx"));
        exportParams.setSortOrder(request.getParameter("sord"));
        
        Pageable pageable = exportService.getExportPageable(exportParams);
        
        Page<ElectionDepartmentIndexListDto> indexData = getElectionDepartmentIndex(request, pageable);
        ElectionDepartmentIndexListDto.postProcessExcelReportData(indexData.getContent());
        
        try {
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            exportService.listToExcelFile(exportParams.getTitle(), indexData.getContent(), exportParams.getModel(), outputStream);
            
        } catch (IOException e) {
            logger.error("Error while initializing Excel file creation", e);
        }
    }
    
    @PreAuthorize("hasAuthority('rs.result')")
    @RequestMapping(value = "resultsindex", method = RequestMethod.GET)
    public @ResponseBody Page<ElectionDepartmentIndexListDto> getElectionDepartmentResultsIndex(HttpServletRequest request, Pageable pageable) {
        
        String searchArgs = request.getParameter("searchString");
        
        ElectionDepartmentArgs args = new ElectionDepartmentArgs();
        
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, ElectionDepartmentArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return electionDepartmentService.getElectionDepartmentResultsIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('rs.result')")
    @RequestMapping(value = "resultsexcel", method = RequestMethod.POST)
    public void exportResultsIndexToExcel(@RequestBody ExportDataDTO exportParams, HttpServletRequest request, HttpServletResponse response) {
    
        exportParams.setTitle(request.getParameter("title"));
        exportParams.setSortColumn(request.getParameter("sidx"));
        exportParams.setSortOrder(request.getParameter("sord"));
        
        Pageable pageable = exportService.getExportPageable(exportParams);
        
        Page<ElectionDepartmentIndexListDto> indexData = getElectionDepartmentResultsIndex(request, pageable);
        ElectionDepartmentIndexListDto.postProcessExcelReportData(indexData.getContent());
        
        try {
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            exportService.listToExcelFile(exportParams.getTitle(), indexData.getContent(), exportParams.getModel(), outputStream);
            
        } catch (IOException e) {
            logger.error("Error while initializing Excel file creation", e);
        }
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "{electionDepartmentId}/contribution/pending/{contributionId}/notify", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> notifyPendingContribution(
            @PathVariable("electionDepartmentId") Long electionDepartmentId,
            @PathVariable("contributionId") Long contributionId) {
        
        contributionService.notifyPendingContribution(electionDepartmentId, contributionId);
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "{electionDepartmentId}/contribution/pending/notify", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> notifyPendingContributionsByElectionDepartment(
            @PathVariable("electionDepartmentId") Long electionDepartmentId) {
        
        contributionService.notifyPendingContributionsByElectionDepartment(electionDepartmentId);
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "all/contribution/pending/notify", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> notifyAllPendingContributions() {
        
        contributionService.notifyAllPendingContributions();
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "{electionDepartmentId}/contribution/pending/{contributionId}/renotify", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> renotifyPendingContribution(
            @PathVariable("electionDepartmentId") Long electionDepartmentId,
            @PathVariable("contributionId") Long contributionId) {
        
        contributionService.renotifyPendingContribution(electionDepartmentId, contributionId);
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "{electionDepartmentId}/contribution/pending/renotify", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> renotifyPendingContributionsByElectionDepartment(
            @PathVariable("electionDepartmentId") Long electionDepartmentId) {
        
        contributionService.renotifyPendingContributionsByElectionDepartment(electionDepartmentId);
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('sa.email')")
    @RequestMapping(value = "all/contribution/pending/renotify", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> renotifyAllPendingContributions() {
        
        contributionService.renotifyAllPendingContributions();
        
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
