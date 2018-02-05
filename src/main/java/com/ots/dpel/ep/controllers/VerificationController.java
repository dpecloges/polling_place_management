package com.ots.dpel.ep.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.args.VerificationArgs;
import com.ots.dpel.ep.dto.VerificationDto;
import com.ots.dpel.ep.dto.VoterDto;
import com.ots.dpel.ep.services.VerificationService;
import com.ots.dpel.ep.services.VoterService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ep/verification")
public class VerificationController {
    
    private static final Logger logger = LogManager.getLogger(VerificationController.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private VerificationService verificationService;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @PreAuthorize("hasAuthority('ep.verification')")
    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public @ResponseBody VerificationDto verify(@RequestBody VerificationArgs args) {
        return verificationService.verify(args);
    }
    
    @PreAuthorize("hasAuthority('ep.voter')")
    @RequestMapping(value = "savevoter", method = RequestMethod.POST)
    public VoterDto saveVoter(@RequestBody VoterDto voterDto) {
        return verificationService.saveVoter(voterDto);
    }
    
    @PreAuthorize("hasAuthority('ep.voter.undo')")
    @RequestMapping(value = "undovote", method = RequestMethod.POST)
    public ResponseEntity<Boolean> undoVote(@RequestParam Long voterId, @RequestParam(required = false) String undoReason) {
        voterService.undoVote(voterId, undoReason);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('ep.voter')")
    @RequestMapping(value = "votercount", method = RequestMethod.GET)
    public JSONObject getVoterCountByElectionDepartmentId(@RequestParam Long electionDepartmentId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", voterService.getVoterCountByElectionDepartmentId(electionDepartmentId, YesNoEnum.YES));
        jsonObject.put("allowsInconsistentSubmission", electionDepartmentService.allowsInconsistentSubmission(electionDepartmentId));
        return jsonObject;
    }
}
