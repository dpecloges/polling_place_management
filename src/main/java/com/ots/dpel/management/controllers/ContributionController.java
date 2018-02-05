package com.ots.dpel.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.management.services.ContributionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mg/contribution")
public class ContributionController {
    
    private static final Logger logger = LogManager.getLogger(ContributionController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ContributionService contributionService;
    
    @PreAuthorize("hasAuthority('ext.contribution')")
    @RequestMapping(value = "newrep", method = RequestMethod.POST)
    public ResponseEntity<Boolean> createCandidateRepresentativeContribution(
            @RequestParam Long volunteerId,
            @RequestParam Long electionDepartmentId,
            @RequestParam Long candidateId) {
        
        logger.info("External newrep contribution call with volunteerId [{}], electionDepartmentId [{}], candidateId [{}]", volunteerId, electionDepartmentId, candidateId);
        contributionService.createCandidateRepresentativeContribution(volunteerId, electionDepartmentId, candidateId);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
