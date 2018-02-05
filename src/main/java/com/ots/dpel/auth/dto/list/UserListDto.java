package com.ots.dpel.auth.dto.list;

import com.mysema.query.annotations.QueryProjection;

/**
 * Created by lzagkaretos on 15/9/2016.
 */
public class UserListDto {
    
    private Long id;
    
    private String username;
    
    private String lastName;
    
    private String firstName;
    
    private String email;
    
    private String type;
    
    private Long electionCenterId;
    
    private String electionCenterCode;
    
    private String electionCenterName;
    
    private Long electionDepartmentId;
    
    private String electionDepartmentCode;
    
    private String electionDepartmentName;
    
    private String geographicalUnitName;
    
    private String decentralAdminName;
    
    private String regionName;
    
    private String regionalUnitName;
    
    private String municipalityName;
    
    private String municipalUnitName;
    
    public UserListDto() {
    }

    @QueryProjection
    public UserListDto(Long id, String username, String lastName, String firstName, String email, String electionCenterCode,
                       String electionCenterName, String electionDepartmentCode, String electionDepartmentName) {
        
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.electionCenterCode = electionCenterCode;
        this.electionCenterName = electionCenterName;
        this.electionDepartmentCode = electionDepartmentCode;
        this.electionDepartmentName = electionDepartmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }

    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }

    public String getElectionDepartmentCode() {
        return electionDepartmentCode;
    }

    public void setElectionDepartmentCode(String electionDepartmentCode) {
        this.electionDepartmentCode = electionDepartmentCode;
    }

    public String getElectionDepartmentName() {
        return electionDepartmentName;
    }

    public void setElectionDepartmentName(String electionDepartmentName) {
        this.electionDepartmentName = electionDepartmentName;
    }

    public String getGeographicalUnitName() {
        return geographicalUnitName;
    }

    public void setGeographicalUnitName(String geographicalUnitName) {
        this.geographicalUnitName = geographicalUnitName;
    }

    public String getDecentralAdminName() {
        return decentralAdminName;
    }

    public void setDecentralAdminName(String decentralAdminName) {
        this.decentralAdminName = decentralAdminName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionalUnitName() {
        return regionalUnitName;
    }

    public void setRegionalUnitName(String regionalUnitName) {
        this.regionalUnitName = regionalUnitName;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public String getMunicipalUnitName() {
        return municipalUnitName;
    }

    public void setMunicipalUnitName(String municipalUnitName) {
        this.municipalUnitName = municipalUnitName;
    }
}
