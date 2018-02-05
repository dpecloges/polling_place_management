package com.ots.dpel.management.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

import javax.persistence.Entity;
import javax.persistence.Id;

public class ElectionDepartmentResultDto {
    
    private Long id;
    
    private Long electionCenterId;
    
    private Boolean submitted;
    
    private Boolean foreign;
    
    private String foreignCountryIsoCode;
    
    private String foreignCity;
    
    private Long geographicalUnitId;
    
    private Long decentralAdminId;
    
    private Long regionId;
    
    private Long regionalUnitId;
    
    private Long municipalityId;
    
    private Long municipalUnitId;
    
    private Integer totalVotes;
    
    private Integer whiteVotes;
    
    private Integer invalidVotes;
    
    private Integer validVotes;
    
    private Integer candidateOneVotes;
    
    private Integer candidateTwoVotes;
    
    private Integer candidateThreeVotes;
    
    private Integer candidateFourVotes;
    
    private Integer candidateFiveVotes;
    
    private Integer candidateSixVotes;
    
    private Integer candidateSevenVotes;
    
    private Integer candidateEightVotes;
    
    private Integer candidateNineVotes;
    
    private Integer candidateTenVotes;
    
    @QueryProjection
    public ElectionDepartmentResultDto(Long id, Long electionCenterId, YesNoEnum submitted, YesNoEnum foreign, String foreignCountryIsoCode,
                                       String foreignCity, Long geographicalUnitId, Long decentralAdminId, Long regionId, Long regionalUnitId,
                                       Long municipalityId, Long municipalUnitId, Integer totalVotes, Integer whiteVotes, Integer invalidVotes,
                                       Integer validVotes, Integer candidateOneVotes, Integer candidateTwoVotes, Integer candidateThreeVotes,
                                       Integer candidateFourVotes, Integer candidateFiveVotes, Integer candidateSixVotes, Integer candidateSevenVotes,
                                       Integer candidateEightVotes, Integer candidateNineVotes, Integer candidateTenVotes) {
        this.id = id;
        this.electionCenterId = electionCenterId;
        this.submitted = submitted != null && submitted.equals(YesNoEnum.YES);
        this.foreign = foreign != null && foreign.equals(YesNoEnum.YES);
        this.foreignCountryIsoCode = foreignCountryIsoCode;
        this.foreignCity = foreignCity;
        this.geographicalUnitId = geographicalUnitId;
        this.decentralAdminId = decentralAdminId;
        this.regionId = regionId;
        this.regionalUnitId = regionalUnitId;
        this.municipalityId = municipalityId;
        this.municipalUnitId = municipalUnitId;
        this.totalVotes = totalVotes == null ? 0 : totalVotes;
        this.whiteVotes = whiteVotes == null ? 0 : whiteVotes;
        this.invalidVotes = invalidVotes == null ? 0 : invalidVotes;
        this.validVotes = validVotes == null ? 0 : validVotes;
        this.candidateOneVotes = candidateOneVotes == null ? 0 : candidateOneVotes;
        this.candidateTwoVotes = candidateTwoVotes == null ? 0 : candidateTwoVotes;
        this.candidateThreeVotes = candidateThreeVotes == null ? 0 : candidateThreeVotes;
        this.candidateFourVotes = candidateFourVotes == null ? 0 : candidateFourVotes;
        this.candidateFiveVotes = candidateFiveVotes == null ? 0 : candidateFiveVotes;
        this.candidateSixVotes = candidateSixVotes == null ? 0 : candidateSixVotes;
        this.candidateSevenVotes = candidateSevenVotes == null ? 0 : candidateSevenVotes;
        this.candidateEightVotes = candidateEightVotes == null ? 0 : candidateEightVotes;
        this.candidateNineVotes = candidateNineVotes == null ? 0 : candidateNineVotes;
        this.candidateTenVotes = candidateTenVotes == null ? 0 : candidateTenVotes;
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
    
    public Integer getTotalVotes() {
        return totalVotes;
    }
    
    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }
    
    public Integer getWhiteVotes() {
        return whiteVotes;
    }
    
    public void setWhiteVotes(Integer whiteVotes) {
        this.whiteVotes = whiteVotes;
    }
    
    public Integer getInvalidVotes() {
        return invalidVotes;
    }
    
    public void setInvalidVotes(Integer invalidVotes) {
        this.invalidVotes = invalidVotes;
    }
    
    public Integer getValidVotes() {
        return validVotes;
    }
    
    public void setValidVotes(Integer validVotes) {
        this.validVotes = validVotes;
    }
    
    public Integer getCandidateOneVotes() {
        return candidateOneVotes;
    }
    
    public void setCandidateOneVotes(Integer candidateOneVotes) {
        this.candidateOneVotes = candidateOneVotes;
    }
    
    public Integer getCandidateTwoVotes() {
        return candidateTwoVotes;
    }
    
    public void setCandidateTwoVotes(Integer candidateTwoVotes) {
        this.candidateTwoVotes = candidateTwoVotes;
    }
    
    public Integer getCandidateThreeVotes() {
        return candidateThreeVotes;
    }
    
    public void setCandidateThreeVotes(Integer candidateThreeVotes) {
        this.candidateThreeVotes = candidateThreeVotes;
    }
    
    public Integer getCandidateFourVotes() {
        return candidateFourVotes;
    }
    
    public void setCandidateFourVotes(Integer candidateFourVotes) {
        this.candidateFourVotes = candidateFourVotes;
    }
    
    public Integer getCandidateFiveVotes() {
        return candidateFiveVotes;
    }
    
    public void setCandidateFiveVotes(Integer candidateFiveVotes) {
        this.candidateFiveVotes = candidateFiveVotes;
    }
    
    public Integer getCandidateSixVotes() {
        return candidateSixVotes;
    }
    
    public void setCandidateSixVotes(Integer candidateSixVotes) {
        this.candidateSixVotes = candidateSixVotes;
    }
    
    public Integer getCandidateSevenVotes() {
        return candidateSevenVotes;
    }
    
    public void setCandidateSevenVotes(Integer candidateSevenVotes) {
        this.candidateSevenVotes = candidateSevenVotes;
    }
    
    public Integer getCandidateEightVotes() {
        return candidateEightVotes;
    }
    
    public void setCandidateEightVotes(Integer candidateEightVotes) {
        this.candidateEightVotes = candidateEightVotes;
    }
    
    public Integer getCandidateNineVotes() {
        return candidateNineVotes;
    }
    
    public void setCandidateNineVotes(Integer candidateNineVotes) {
        this.candidateNineVotes = candidateNineVotes;
    }
    
    public Integer getCandidateTenVotes() {
        return candidateTenVotes;
    }
    
    public void setCandidateTenVotes(Integer candidateTenVotes) {
        this.candidateTenVotes = candidateTenVotes;
    }
}
