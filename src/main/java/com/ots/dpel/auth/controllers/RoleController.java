package com.ots.dpel.auth.controllers;

import com.ots.dpel.auth.dto.RoleDto;
import com.ots.dpel.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @PreAuthorize("hasAuthority('sa.user')")
    @RequestMapping(value = "findall", method = RequestMethod.GET)
    public @ResponseBody List<RoleDto> getAll() {
        return roleService.getAll();
    }
}
