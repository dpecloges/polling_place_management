package com.ots.dpel.common.core.dto;

import com.mysema.query.annotations.QueryProjection;

import java.io.Serializable;

public class AdminUnitDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private Long parentId;
    
    private Short level;
    
    private String code;
    
    private String name;
    
    public AdminUnitDto() {
    }
    
    @QueryProjection
    public AdminUnitDto(Long id, Long parentId, Short level, String code, String name) {
        this.id = id;
        this.parentId = parentId;
        this.level = level;
        this.code = code;
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public Short getLevel() {
        return level;
    }
    
    public void setLevel(Short level) {
        this.level = level;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        AdminUnitDto that = (AdminUnitDto) o;
    
        return id != null ? id.equals(that.id) : that.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
