package com.ots.dpel.results.controllers;

import com.ots.dpel.management.dto.list.CandidateListDto;
import com.ots.dpel.management.services.CandidateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rs/candidates")
public class CandidateController {
    
    private static final Logger logger = LogManager.getLogger(CandidateController.class);
    
    @Autowired
    private CandidateService candidateService;
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "findall", method = RequestMethod.GET)
    public @ResponseBody List<CandidateListDto> getAll() {
        return candidateService.getAll();
    }
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "findcurrent", method = RequestMethod.GET)
    public @ResponseBody List<CandidateListDto> getByCurrentElectionProcedure() {
        return candidateService.getByCurrentElectionProcedure();
    }
}
