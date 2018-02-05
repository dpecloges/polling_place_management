package com.ots.dpel.common.services;

import com.ots.dpel.common.core.dto.AdminUnitDto;
import com.ots.dpel.ep.core.enums.SnapshotType;
import com.ots.dpel.results.core.enums.ResultType;

import java.util.List;

public interface AdminUnitService {
    
    List<AdminUnitDto> getAll();
    
    ResultType getResultType(Short level);
    
    SnapshotType getSnapshotType(Short level);
    
    List<AdminUnitDto> getByParentId(Long parentId);
    
    List<AdminUnitDto> getByType(String type);
}
