package com.ots.dpel.management.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class ElectionDepartmentSnapshotDto {
    
    private Long id;
    
    private String code;
    
    private String name;
    
    private Long electionCenterId;
    
    private String electionCenterCode;
    
    private String electionCenterName;
    
    private Boolean submitted;
    
    private Boolean foreign;
    
    private String foreignCountryIsoCode;
    
    private String foreignCountryName;
    
    private String foreignCity;
    
    private Long geographicalUnitId;
    
    private String geographicalUnitName;
    
    private Long decentralAdminId;
    
    private String decentralAdminName;
    
    private Long regionId;
    
    private String regionName;
    
    private Long regionalUnitId;
    
    private String regionalUnitName;
    
    private Long municipalityId;
    
    private String municipalityName;
    
    private Long municipalUnitId;
    
    private String municipalUnitName;
    
    private Integer voterCount;
    
    private Integer undoneVoterCount;
    
    private String electionCenterDisplayName;
    
    private String electionDepartmentDisplayName;
    
    @QueryProjection
    public ElectionDepartmentSnapshotDto(Long id, String code, String name, Long electionCenterId, String electionCenterCode, String electionCenterName,
                                         YesNoEnum submitted, YesNoEnum foreign, String foreignCountryIsoCode,
                                         String foreignCountryName, String foreignCity, Long geographicalUnitId, String geographicalUnitName, Long decentralAdminId,
                                         String decentralAdminName, Long regionId, String regionName, Long regionalUnitId, String regionalUnitName,
                                         Long municipalityId, String municipalityName, Long municipalUnitId, String municipalUnitName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.electionCenterId = electionCenterId;
        this.electionCenterCode = electionCenterCode;
        this.electionCenterName = electionCenterName;
        this.submitted = submitted != null && submitted.equals(YesNoEnum.YES);
        this.foreign = foreign != null && foreign.equals(YesNoEnum.YES);
        this.foreignCountryIsoCode = foreignCountryIsoCode;
        this.foreignCountryName = foreignCountryName;
        this.foreignCity = foreignCity;
        this.geographicalUnitId = geographicalUnitId;
        this.geographicalUnitName = geographicalUnitName;
        this.decentralAdminId = decentralAdminId;
        this.decentralAdminName = decentralAdminName;
        this.regionId = regionId;
        this.regionName = regionName;
        this.regionalUnitId = regionalUnitId;
        this.regionalUnitName = regionalUnitName;
        this.municipalityId = municipalityId;
        this.municipalityName = municipalityName;
        this.municipalUnitId = municipalUnitId;
        this.municipalUnitName = municipalUnitName;
    }
    
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
    
    public Boolean getSubmitted() {
        return submitted;
    }
    
    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }
    
    public Boolean getForeign() {
        return foreign;
    }
    
    public void setForeign(Boolean foreign) {
        this.foreign = foreign;
    }
    
    public String getForeignCountryIsoCode() {
        return foreignCountryIsoCode;
    }
    
    public void setForeignCountryIsoCode(String foreignCountryIsoCode) {
        this.foreignCountryIsoCode = foreignCountryIsoCode;
    }
    
    public String getForeignCountryName() {
        return foreignCountryName;
    }
    
    public void setForeignCountryName(String foreignCountryName) {
        this.foreignCountryName = foreignCountryName;
    }
    
    public String getForeignCity() {
        return foreignCity;
    }
    
    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
    
    public Long getGeographicalUnitId() {
        return geographicalUnitId;
    }
    
    public void setGeographicalUnitId(Long geographicalUnitId) {
        this.geographicalUnitId = geographicalUnitId;
    }
    
    public String getGeographicalUnitName() {
        return geographicalUnitName;
    }
    
    public void setGeographicalUnitName(String geographicalUnitName) {
        this.geographicalUnitName = geographicalUnitName;
    }
    
    public Long getDecentralAdminId() {
        return decentralAdminId;
    }
    
    public void setDecentralAdminId(Long decentralAdminId) {
        this.decentralAdminId = decentralAdminId;
    }
    
    public String getDecentralAdminName() {
        return decentralAdminName;
    }
    
    public void setDecentralAdminName(String decentralAdminName) {
        this.decentralAdminName = decentralAdminName;
    }
    
    public Long getRegionId() {
        return regionId;
    }
    
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public String getRegionName() {
        return regionName;
    }
    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    public Long getRegionalUnitId() {
        return regionalUnitId;
    }
    
    public void setRegionalUnitId(Long regionalUnitId) {
        this.regionalUnitId = regionalUnitId;
    }
    
    public String getRegionalUnitName() {
        return regionalUnitName;
    }
    
    public void setRegionalUnitName(String regionalUnitName) {
        this.regionalUnitName = regionalUnitName;
    }
    
    public Long getMunicipalityId() {
        return municipalityId;
    }
    
    public void setMunicipalityId(Long municipalityId) {
        this.municipalityId = municipalityId;
    }
    
    public String getMunicipalityName() {
        return municipalityName;
    }
    
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }
    
    public Long getMunicipalUnitId() {
        return municipalUnitId;
    }
    
    public void setMunicipalUnitId(Long municipalUnitId) {
        this.municipalUnitId = municipalUnitId;
    }
    
    public String getMunicipalUnitName() {
        return municipalUnitName;
    }
    
    public void setMunicipalUnitName(String municipalUnitName) {
        this.municipalUnitName = municipalUnitName;
    }
    
    public Integer getVoterCount() {
        return voterCount;
    }
    
    public void setVoterCount(Integer voterCount) {
        this.voterCount = voterCount;
    }
    
    public Integer getUndoneVoterCount() {
        return undoneVoterCount;
    }
    
    public void setUndoneVoterCount(Integer undoneVoterCount) {
        this.undoneVoterCount = undoneVoterCount;
    }
    
    public String getElectionCenterDisplayName() {
        String thisElectionCenterCode = (this.electionCenterCode == null) ? "" : this.electionCenterCode;
        Boolean thisForeign = (this.foreign != null && this.foreign);
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipalityName = (this.municipalityName == null) ? "" : this.municipalityName;
        
        return "Ε.Κ. " + thisElectionCenterCode + " - " + (thisForeign ? thisElectionCenterName : thisMunicipalityName);
    }
    
    public void setElectionCenterDisplayName(String electionCenterDisplayName) {
        this.electionCenterDisplayName = electionCenterDisplayName;
    }
    
    public String getElectionDepartmentDisplayName() {
        String thisElectionDepartmentName = (this.name == null) ? "" : this.name;
        Boolean thisForeign = (this.foreign != null && this.foreign);
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipalityName = (this.municipalityName == null) ? "" : this.municipalityName;
        
        return "Ε.Τ. " + thisElectionDepartmentName + " - " + (thisForeign ? thisElectionCenterName : thisMunicipalityName);
    }
    
    public void setElectionDepartmentDisplayName(String electionDepartmentDisplayName) {
        this.electionDepartmentDisplayName = electionDepartmentDisplayName;
    }
}
