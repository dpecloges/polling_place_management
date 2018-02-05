package com.ots.dpel.auth.services;

import com.ots.dpel.auth.dto.RoleDto;

import java.util.List;

public interface RoleService {
    
    List<RoleDto> getAll();
    
    Long getRoleIdByUser(Long userId);
    
    String getCode(Long id);
}
