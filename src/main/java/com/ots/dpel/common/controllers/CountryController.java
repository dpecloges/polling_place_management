package com.ots.dpel.common.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ots.dpel.common.core.dto.CountryDto;
import com.ots.dpel.common.services.CountryService;

@RestController
@RequestMapping("/common/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "findall", method = RequestMethod.GET)
    public @ResponseBody
    List<CountryDto> findAll() {
        return countryService.findAll();
    }
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "find/{isoCode}", method = RequestMethod.GET)
    public @ResponseBody
    CountryDto findByIsoCode(@PathVariable("isoCode") String isoCode) {
        return countryService.findByIsoCode(isoCode);
    }
}
