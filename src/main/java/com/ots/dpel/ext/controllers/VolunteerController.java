package com.ots.dpel.ext.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.ext.args.VolunteerArgs;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.dto.list.VolunteerListDto;
import com.ots.dpel.ext.services.VolunteerService;

@RestController
@RequestMapping("/ext/volunteer")
public class VolunteerController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "find/{id}", method = RequestMethod.GET)
    public @ResponseBody VolunteerDto findVolunteer(@PathVariable("id") Long id) {
        return this.volunteerService.findVolunteer(id);
    }
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "findbasic/{id}", method = RequestMethod.GET)
    public @ResponseBody VolunteerDto findVolunteerBasic(@PathVariable("id") Long id) {
        return this.volunteerService.findVolunteerBasic(id);
    }
    
    @PreAuthorize("hasAuthority('ext.volunteer')")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public @ResponseBody Page<VolunteerListDto> getVolunteerIndex(HttpServletRequest request, Pageable pageable) {
        VolunteerArgs args = extractVolunteerArgs(request);
        return volunteerService.getVolunteerIndex(args, pageable);
    }
    
    private VolunteerArgs extractVolunteerArgs(HttpServletRequest request) {
        String searchArgs = request.getParameter("searchString");
        VolunteerArgs args = new VolunteerArgs();
        
        if (searchArgs != null) {
            try {
                args = objectMapper.readValue(searchArgs, VolunteerArgs.class);
            } catch (IOException e) {
                logger.error("Error reading index arguments", e);
            }
        }
        
        return args;
    }
}
