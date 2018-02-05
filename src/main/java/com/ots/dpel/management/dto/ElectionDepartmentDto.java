package com.ots.dpel.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysema.query.annotations.QueryProjection;

import java.util.ArrayList;
import java.util.List;

import com.ots.dpel.common.core.enums.YesNoEnum;

public class ElectionDepartmentDto {
    
    private Long id;
    
    private Long electionCenterId;
    
    private Integer serialNo;
    
    private String code;
    
    private String name;
    
    private String comments;
    
    private Boolean accessDifficulty;
    
    private Boolean allowInconsistentSubmission;
    
    @JsonProperty("contributions")
    private List<ContributionDto> contributionDtos = new ArrayList<>();
    
    
    public ElectionDepartmentDto() {
    }
    
    @QueryProjection
    public ElectionDepartmentDto(Long id, Long electionCenterId, Integer serialNo, String code, String name, String comments,
            YesNoEnum accessDifficulty, YesNoEnum allowInconsistentSubmission) {
        this.id = id;
        this.electionCenterId = electionCenterId;
        this.serialNo = serialNo;
        this.code = code;
        this.name = name;
        this.comments = comments;
        this.accessDifficulty = YesNoEnum.booleanValue(accessDifficulty);
        this.allowInconsistentSubmission = allowInconsistentSubmission != null && allowInconsistentSubmission.equals(YesNoEnum.YES);
    }
    
    @QueryProjection
    public ElectionDepartmentDto(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    
    @QueryProjection
    public ElectionDepartmentDto(Long id, Long electionCenterId, Integer serialNo, String code, String name, String comments) {
        this.id = id;
        this.electionCenterId = electionCenterId;
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
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
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
    
    public List<ContributionDto> getContributionDtos() {
        return contributionDtos;
    }
    
    public void setContributionDtos(List<ContributionDto> contributionDtos) {
        this.contributionDtos = contributionDtos;
    }

    public Boolean getAccessDifficulty() {
        return accessDifficulty;
    }

    public void setAccessDifficulty(Boolean accessDifficulty) {
        this.accessDifficulty = accessDifficulty;
    }
    
    public Boolean getAllowInconsistentSubmission() {
        return allowInconsistentSubmission;
    }
    
    public void setAllowInconsistentSubmission(Boolean allowInconsistentSubmission) {
        this.allowInconsistentSubmission = allowInconsistentSubmission;
    }
}
