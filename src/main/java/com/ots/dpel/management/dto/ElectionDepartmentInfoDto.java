package com.ots.dpel.management.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ElectionDepartmentInfoDto {
    
    @Id
    private Long id;
    
    private String code;
    
    private String name;
    
    private Long electionCenterId;
    
    private String electionCenterCode;
    
    private String electionCenterName;
    
    private String foreignCountry;
    
    private String municipalityName;
    
    @Transient
    private String electionCenterDisplayName;
    
    @Transient
    private String electionDepartmentDisplayName;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }
    
    public String getElectionCenterCode() {
        return electionCenterCode;
    }
    
    public void setElectionCenterCode(String electionCenterCode) {
        this.electionCenterCode = electionCenterCode;
    }
    
    public String getElectionCenterName() {
        return electionCenterName;
    }
    
    public void setElectionCenterName(String electionCenterName) {
        this.electionCenterName = electionCenterName;
    }
    
    public String getForeignCountry() {
        return foreignCountry;
    }
    
    public void setForeignCountry(String foreignCountry) {
        this.foreignCountry = foreignCountry;
    }
    
    public String getMunicipalityName() {
        return municipalityName;
    }
    
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }
    
    public String getElectionCenterDisplayName() {
        String thisElectionCenterCode = (this.electionCenterCode == null) ? "" : this.electionCenterCode;
        Boolean thisForeignCountry = (this.foreignCountry != null && this.foreignCountry.equals("1"));
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipalityName = (this.municipalityName == null) ? "" : this.municipalityName;
        
        return thisElectionCenterCode + " - " + (thisForeignCountry ? thisElectionCenterName : thisMunicipalityName);
    }
    
    public void setElectionCenterDisplayName(String electionCenterDisplayName) {
        this.electionCenterDisplayName = electionCenterDisplayName;
    }
    
    public String getElectionDepartmentDisplayName() {
        String thisElectionDepartmentName = (this.name == null) ? "" : this.name;
        Boolean thisForeignCountry = (this.foreignCountry != null && this.foreignCountry.equals("1"));
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipalityName = (this.municipalityName == null) ? "" : this.municipalityName;
        
        return thisElectionDepartmentName + " - " + (thisForeignCountry ? thisElectionCenterName : thisMunicipalityName);
    }
    
    public void setElectionDepartmentDisplayName(String electionDepartmentDisplayName) {
        this.electionDepartmentDisplayName = electionDepartmentDisplayName;
    }
}
