package com.ots.dpel.system.controllers;

import com.ots.dpel.system.services.TestDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/testdata")
public class TestDataController {
    
    private static final Logger logger = LogManager.getLogger(TestDataController.class);
    
    @Autowired
    private TestDataService testDataService;
    
    @PreAuthorize("hasPermission(#principal, 'sys.admin')")
    @RequestMapping(value = "createvoters", method = RequestMethod.POST)
    public ResponseEntity<Boolean> createVoters(@RequestParam("count") Integer count,
                                                @RequestParam(value = "chunksize", required = false) Integer chunksize) {
        testDataService.createVoters(count, chunksize);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    
    @PreAuthorize("hasPermission(#principal, 'sys.admin')")
    @RequestMapping(value = "undovotes", method = RequestMethod.POST)
    public ResponseEntity<Boolean> undoVotes(@RequestParam("count") Integer count,
                                             @RequestParam(value = "chunksize", required = false) Integer chunksize) {
        testDataService.undoVotes(count, chunksize);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
