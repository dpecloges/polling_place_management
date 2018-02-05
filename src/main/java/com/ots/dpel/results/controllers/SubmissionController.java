package com.ots.dpel.results.controllers;

import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.results.core.enums.AttachmentType;
import com.ots.dpel.results.dto.ResultsDto;
import com.ots.dpel.results.services.SubmissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/rs/submission")
public class SubmissionController {
    
    private static final Logger logger = LogManager.getLogger(SubmissionController.class);
    
    @Autowired
    private SubmissionService submissionService;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @PreAuthorize("hasAuthority('rs.submission')")
    @RequestMapping(value = "find/results/{id}", method = RequestMethod.GET)
    public @ResponseBody ResultsDto findResults(@PathVariable("id") Long id) {
        return submissionService.findResults(id);
    }
    
    @PreAuthorize("hasAuthority('rs.submission')")
    @RequestMapping(value = "save/results", method = RequestMethod.POST)
    public @ResponseBody ResultsDto saveResults(@RequestBody ResultsDto resultsDto) {
        return submissionService.saveResults(resultsDto);
    }
    
    @PreAuthorize("hasAuthority('rs.submission')")
    @RequestMapping(value = "/upload/{electionDepartmentId}/{attachmentType}", method = RequestMethod.POST)
    public JSONObject uploadFile(@PathVariable("electionDepartmentId") Long electionDepartmentId,
                                 @PathVariable("attachmentType") AttachmentType attachmentType,
                                 MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        
        // Iterate for 1st file
        if (itr.hasNext()) {
            String uploadedFile = itr.next();
            MultipartFile file = request.getFile(uploadedFile);
    
            submissionService.uploadFile(electionDepartmentId, file, attachmentType);
        }
        
        Map<String, String> props = new HashMap<>();
        props.put("success", messageSourceProvider.getMessage("results.uploadFile.success"));
        
        return new JSONObject(props);
    }
    
    @PreAuthorize("hasAuthority('rs.submission')")
    @RequestMapping(value = "/download/{electionDepartmentId}/{attachmentType}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable("electionDepartmentId") Long electionDepartmentId,
                             @PathVariable("attachmentType") AttachmentType attachmentType,
                             HttpServletResponse response) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
    
            submissionService.downloadFile(electionDepartmentId, outputStream, attachmentType);
        } catch (IOException e) {
            logger.error("Error while initializing image file creation", e);
        }
    }
    
}
