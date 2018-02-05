package com.ots.dpel.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.global.dto.ExportDataDTO;
import com.ots.dpel.global.services.ExportService;
import com.ots.dpel.management.args.ElectionCenterArgs;
import com.ots.dpel.management.dto.ElectionCenterBasicDto;
import com.ots.dpel.management.dto.ElectionCenterDto;
import com.ots.dpel.management.dto.list.ElectionCenterListDto;
import com.ots.dpel.management.services.ElectionCenterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/mg/electioncenter")
public class ElectionCenterController {
    
    private static final Logger logger = LogManager.getLogger(ElectionCenterController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ElectionCenterService electionCenterService;
    
    @Autowired
    private ExportService exportService;
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public @ResponseBody Page<ElectionCenterListDto> getElectionCenterIndex(HttpServletRequest request, Pageable pageable) {
        
        String searchArgs = request.getParameter("searchString");
        
        ElectionCenterArgs args = new ElectionCenterArgs();
        
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, ElectionCenterArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return electionCenterService.getElectionCenterIndex(args, pageable);
    }
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "findall", method = RequestMethod.GET)
    public @ResponseBody List<ElectionCenterBasicDto> findAll() {
        return electionCenterService.findAllBasic();
    }
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "find/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ElectionCenterDto findElectionCenter(@PathVariable("id") Long id) {
        return electionCenterService.findElectionCenter(id);
    }
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody
    ElectionCenterDto saveElectionCenter(@RequestBody ElectionCenterDto electionCenterDto) {
        return electionCenterService.saveElectionCenter(electionCenterDto);
    }
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    void deleteElectionCenter(@PathVariable("id") Long id) {
        electionCenterService.deleteElectionCenter(id);
    }
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "findbasic/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ElectionCenterDto findElectionCenterBasic(@PathVariable("id") Long id) {
        return electionCenterService.findElectionCenterBasic(id);
    }
    
    @PreAuthorize("hasAuthority('mg.electioncenter')")
    @RequestMapping(value = "excel", method = RequestMethod.POST)
    public void exportIndexToExcel(@RequestBody ExportDataDTO exportParams, HttpServletRequest request, HttpServletResponse response) {
    
        exportParams.setTitle(request.getParameter("title"));
        exportParams.setSortColumn(request.getParameter("sidx"));
        exportParams.setSortOrder(request.getParameter("sord"));
        
        Pageable pageable = exportService.getExportPageable(exportParams);
    
        Page<ElectionCenterListDto> indexData = getElectionCenterIndex(request, pageable);
        
        try {
            
            ServletOutputStream outputStream = response.getOutputStream();
            
            exportService.listToExcelFile(exportParams.getTitle(), indexData.getContent(), exportParams.getModel(), outputStream);
            
        } catch (IOException e) {
            logger.error("Error while initializing Excel file creation", e);
        }
    }
}
