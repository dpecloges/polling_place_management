package com.ots.dpel.management.args;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.management.core.enums.ElectionRound;

public class ElectionDepartmentArgs implements SearchableArguments {
    
    private Long electionCenterId;
    
    private Long geographicalUnitId;
    
    private Long decentralAdminId;
    
    private Long regionId;
    
    private Long regionalUnitId;
    
    private Long municipalityId;
    
    private Long municipalUnitId;
    
    private Boolean foreign;
    
    private String foreignCountryIsoCode;
    
    private String foreignCity;
    
    private Boolean contribution;
    
    private YesNoEnum votersExist;
    
    private YesNoEnum submitted;
    
    private ElectionRound electionRound;
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
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
    
    public Boolean getContribution() {
        return contribution;
    }
    
    public void setContribution(Boolean contribution) {
        this.contribution = contribution;
    }
    
    public YesNoEnum getVotersExist() {
        return votersExist;
    }
    
    public void setVotersExist(YesNoEnum votersExist) {
        this.votersExist = votersExist;
    }
    
    public YesNoEnum getSubmitted() {
        return submitted;
    }
    
    public void setSubmitted(YesNoEnum submitted) {
        this.submitted = submitted;
    }
    
    public ElectionRound getElectionRound() {
        return electionRound;
    }
    
    public void setElectionRound(ElectionRound electionRound) {
        this.electionRound = electionRound;
    }
}
