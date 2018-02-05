package com.ots.dpel.auth.controllers;

import com.ots.dpel.auth.dto.RegisterDto;
import com.ots.dpel.auth.dto.UserRegistrationDto;
import com.ots.dpel.auth.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/registration")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @RequestMapping(value = "find/{identifier}", method = RequestMethod.GET)
    public @ResponseBody
    UserRegistrationDto findUserRegistrationByIdentifier(@PathVariable("identifier") String identifier) {
        return registrationService.findUserRegistrationByIdentifier(identifier);
    }
    
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Boolean> register(@RequestBody RegisterDto registerDto) {
        registrationService.registerUser(registerDto);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
}
