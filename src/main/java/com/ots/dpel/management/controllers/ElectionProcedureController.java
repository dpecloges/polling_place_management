package com.ots.dpel.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionProcedureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mg/electionprocedure")
public class ElectionProcedureController {
    
    private static final Logger logger = LogManager.getLogger(ElectionProcedureController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "current", method = RequestMethod.GET)
    public @ResponseBody ElectionProcedureDto getCurrent() {
        return electionProcedureService.getCurrent();
    }
}
