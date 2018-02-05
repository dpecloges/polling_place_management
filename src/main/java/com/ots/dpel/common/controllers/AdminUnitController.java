package com.ots.dpel.common.controllers;

import com.ots.dpel.common.core.dto.AdminUnitDto;
import com.ots.dpel.common.services.AdminUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/common/adminunits")
public class AdminUnitController {
    
    @Autowired
    private AdminUnitService adminUnitService;
    
    @PreAuthorize("hasAuthority('cm.general')")
    @RequestMapping(value = "findall", method = RequestMethod.GET)
    public @ResponseBody
    List<AdminUnitDto> getAll() {
        return adminUnitService.getAll();
    }
}
