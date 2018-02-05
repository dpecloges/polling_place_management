package com.ots.dpel.management.args;

import com.ots.dpel.common.args.SearchableArguments;

public class ElectionCenterArgs implements SearchableArguments {
    
    private String code;
    
    private String name;
    
    private String address;
    
    private Long geographicalUnitId;
    
    private Long decentralAdminId;
    
    private Long regionId;
    
    private Long regionalUnitId;
    
    private Long municipalityId;
    
    private Long municipalUnitId;
    
    private Long municipalCommunityId;
    
    private Boolean foreign;
    
    private String foreignCountryIsoCode;
    
    private String foreignCity;
    
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
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

    public String getForeignCountryIsoCode() {
        return foreignCountryIsoCode;
    }

    public void setForeignCountryIsoCode(String foreignCountryIsoCode) {
        this.foreignCountryIsoCode = foreignCountryIsoCode;
    }

    public String getForeignCity() {
        return foreignCity;
    }

    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
}
