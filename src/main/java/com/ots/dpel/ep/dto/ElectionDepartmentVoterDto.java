package com.ots.dpel.ep.dto;

import com.mysema.query.annotations.QueryProjection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ElectionDepartmentVoterDto {
    
    @Id
    private Long electionDepartmentId;
    
    private Integer voterCount;
    
    public ElectionDepartmentVoterDto() {
    }
    
    @QueryProjection
    public ElectionDepartmentVoterDto(Long electionDepartmentId, Long voterCount) {
        this.electionDepartmentId = electionDepartmentId;
        this.voterCount = voterCount == null ? 0 : voterCount.intValue();
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public Integer getVoterCount() {
        return voterCount;
    }
    
    public void setVoterCount(Integer voterCount) {
        this.voterCount = voterCount;
    }
}
