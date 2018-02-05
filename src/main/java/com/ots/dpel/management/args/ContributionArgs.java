package com.ots.dpel.management.args;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;

public class ContributionArgs implements SearchableArguments {

    
    private Long electionCenterId;
    
    private Long electionDepartmentId;
    
    private Long geographicalUnitId;
    
    private Long decentralAdminId;
    
    private Long regionId;
    
    private Long regionalUnitId;
    
    private Long municipalityId;
    
    private Long municipalUnitId;
    
    private Boolean foreign;
    
    private String foreignCountryIsoCode;
    
    private String foreignCity;
    
    /**
     * Είδος μέλους
     */
    private ContributionType contributionType;
    
    /**
     * Κατάσταση ενεργοποίησης χρήστη
     */
    private ContributionStatus contributionStatus;

    public Long getElectionCenterId() {
        return electionCenterId;
    }

    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }

    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }

    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
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

    public ContributionType getContributionType() {
        return contributionType;
    }

    public void setContributionType(ContributionType contributionType) {
        this.contributionType = contributionType;
    }

    public ContributionStatus getContributionStatus() {
        return contributionStatus;
    }

    public void setContributionStatus(ContributionStatus contributionStatus) {
        this.contributionStatus = contributionStatus;
    }
}
