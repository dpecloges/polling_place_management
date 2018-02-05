package com.ots.dpel.management.dto.list;

import com.mysema.query.annotations.QueryProjection;

public class ElectionDepartmentListDto {
    
    private Long id;
    
    private Integer serialNo;
    
    private String code;
    
    private String name;
    
    private String comments;
    
    public ElectionDepartmentListDto() {
    }
    
    @QueryProjection
    public ElectionDepartmentListDto(Long id, Integer serialNo, String code, String name, String comments) {
        this.id = id;
        this.serialNo = serialNo;
        this.code = code;
        this.name = name;
        this.comments = comments;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getSerialNo() {
        return serialNo;
    }
    
    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
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
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
}
