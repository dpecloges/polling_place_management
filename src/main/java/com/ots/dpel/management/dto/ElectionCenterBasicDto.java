package com.ots.dpel.management.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class ElectionCenterBasicDto {
    
    private Long id;
    
    private Long electionProcedureId;
    
    private String code;
    
    private String name;
    
    private Long geographicalUnitId;
    
    private Long decentralAdminId;
    
    private Long regionId;
    
    private Long regionalUnitId;
    
    private Long municipalityId;
    
    private String municipalityName;
    
    private Long municipalUnitId;
    
    private Long municipalCommunityId;
    
    private Boolean foreign;
    
    private String foreignCountryIsoCode;
    
    private String foreignCity;
    
    private String displayName;
    
    public ElectionCenterBasicDto() {
    }
    
    @QueryProjection
    public ElectionCenterBasicDto(
            Long id, Long electionProcedureId, String code, String name, Long geographicalUnitId, Long decentralAdminId, Long regionId,
            Long regionalUnitId, Long municipalityId, String municipalityName, Long municipalUnitId, Long municipalCommunityId,
            YesNoEnum foreign, String foreignCountryIsoCode, String foreignCity) {
        this.id = id;
        this.electionProcedureId = electionProcedureId;
        this.code = (code == null) ? "" : code;
        this.name = (name == null) ? "" : name;
        this.geographicalUnitId = geographicalUnitId;
        this.decentralAdminId = decentralAdminId;
        this.regionId = regionId;
        this.regionalUnitId = regionalUnitId;
        this.municipalityId = municipalityId;
        this.municipalityName = (municipalityName == null) ? "" : municipalityName;
        this.municipalUnitId = municipalUnitId;
        this.municipalCommunityId = municipalCommunityId;
        this.foreign = YesNoEnum.booleanValue(foreign);
        this.foreignCountryIsoCode = foreignCountryIsoCode;
        this.foreignCity = foreignCity;
        this.displayName = this.code + " - " + (this.foreign ? this.name : this.municipalityName);
    }
    
    @QueryProjection
    public ElectionCenterBasicDto(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
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
    
    public Long getGeographicalUnitId() {
        return geographicalUnitId;
    }
    
    public void setGeographicalUnitId(Long geographicalUnitId) {
        this.geographicalUnitId = geographicalUnitId;
    }
    
    public Long getDecentralAdminId() {
        return decentralAdminId;
    }
    
    public void setDecentralAdminId(Long decentralAdminId) {
        this.decentralAdminId = decentralAdminId;
    }
    
    public Long getRegionId() {
        return regionId;
    }
    
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public Long getRegionalUnitId() {
        return regionalUnitId;
    }
    
    public void setRegionalUnitId(Long regionalUnitId) {
        this.regionalUnitId = regionalUnitId;
    }
    
    public Long getMunicipalityId() {
        return municipalityId;
    }
    
    public void setMunicipalityId(Long municipalityId) {
        this.municipalityId = municipalityId;
    }
    
    public Long getMunicipalUnitId() {
        return municipalUnitId;
    }
    
    public String getMunicipalityName() {
        return municipalityName;
    }
    
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }
    
    public void setMunicipalUnitId(Long municipalUnitId) {
        this.municipalUnitId = municipalUnitId;
    }
    
    public Long getMunicipalCommunityId() {
        return municipalCommunityId;
    }
    
    public void setMunicipalCommunityId(Long municipalCommunityId) {
        this.municipalCommunityId = municipalCommunityId;
    }
    
    public Boolean getForeign() {
        return foreign;
    }
    
    public void setForeign(Boolean foreign) {
        this.foreign = foreign;
    }
    
    public String getForeignCity() {
        return foreignCity;
    }
    
    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
    
    public String getForeignCountryIsoCode() {
        return foreignCountryIsoCode;
    }
    
    public void setForeignCountryIsoCode(String foreignCountryIsoCode) {
        this.foreignCountryIsoCode = foreignCountryIsoCode;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
