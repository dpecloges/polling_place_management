package com.ots.dpel.management.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.management.core.enums.ContributionType;

public class ElectionDepartmentBasicDto {
    
    private Long id;
    
    private Long electionCenterId;
    
    private Integer serialNo;
    
    private String code;
    
    private String name;
    
    private Boolean foreign;
    
    private String municipalityName;
    
    private String electionCenterName;
    
    private String displayName;
    
    private ContributionType contributionType;
    
    
    public ElectionDepartmentBasicDto() {
    }
    
    @QueryProjection
    public ElectionDepartmentBasicDto(Long id, Integer serialNo, String code, String name) {
        this.id = id;
        this.serialNo = serialNo;
        this.code = code;
        this.name = name;
    }
    
    @QueryProjection
    public ElectionDepartmentBasicDto(
            Long id,
            Long electionCenterId,
            Integer serialNo,
            String code,
            String name,
            YesNoEnum foreign,
            String municipalityName,
            String electionCenterName
            ) {
        this.id = id;
        this.electionCenterId = electionCenterId;
        this.serialNo = serialNo;
        this.code = code;
        this.name = (name == null) ? "" : name;
        this.foreign = YesNoEnum.booleanValue(foreign);
        this.municipalityName = (municipalityName == null) ? "" : municipalityName;
        this.electionCenterName = (electionCenterName == null) ? "" : electionCenterName;
        this.displayName = this.name + " - " + (this.foreign ? this.electionCenterName : this.municipalityName);
    }
    
    @QueryProjection
    public ElectionDepartmentBasicDto(
            Long id,
            Integer serialNo,
            String code,
            String name,
            YesNoEnum foreign,
            String municipalityName,
            String electionCenterName,
            ContributionType contributionType
    ) {
        this.id = id;
        this.serialNo = serialNo;
        this.code = code;
        this.name = name;
        this.foreign = YesNoEnum.booleanValue(foreign);
        this.municipalityName = (municipalityName == null) ? "" : municipalityName;
        this.electionCenterName = (electionCenterName == null) ? "" : electionCenterName;
        this.displayName = this.name + " - " + (this.foreign ? this.electionCenterName : this.municipalityName);
        this.contributionType = contributionType;
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
    
    public Boolean getForeign() {
        return foreign;
    }
    
    public void setForeign(Boolean foreign) {
        this.foreign = foreign;
    }
    
    public String getMunicipalityName() {
        return municipalityName;
    }
    
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }
    
    public String getElectionCenterName() {
        return electionCenterName;
    }
    
    public void setElectionCenterName(String electionCenterName) {
        this.electionCenterName = electionCenterName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public ContributionType getContributionType() {
        return contributionType;
    }
    
    public void setContributionType(ContributionType contributionType) {
        this.contributionType = contributionType;
    }
}
