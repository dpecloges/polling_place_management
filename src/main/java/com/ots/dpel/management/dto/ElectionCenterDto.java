package com.ots.dpel.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.dto.CountryDto;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class ElectionCenterDto {
    
    private Long id;
    
    private Long electionProcedureId;
    
    private String code;
    
    private String name;
    
    private String address;
    
    private String postalCode;
    
    private String telephone;
    
    @JsonProperty("supervisorFirst")
    private ContributorDto supervisorFirstDto;
    
    @JsonProperty("supervisorSecond")
    private ContributorDto supervisorSecondDto;
    
    private String comments;
    
    private Long geographicalUnitId;
    
    private Long decentralAdminId;
    
    private Long regionId;
    
    private Long regionalUnitId;
    
    private Long municipalityId;
    
    private Long municipalUnitId;
    
    private Long municipalCommunityId;
    
    private Integer floorNumber;
    
    private Boolean disabledAccess;
    
    private Boolean foreign;
    
    @JsonProperty("foreignCountry")
    private CountryDto foreignCountryDto;
    
    private String foreignCity;
    
    private Integer ballotBoxes;
    
    private Integer estimatedBallotBoxes;
    
    private Integer voters2007;
    
    public ElectionCenterDto() {
    }
    
    @QueryProjection
    public ElectionCenterDto(
            Long id, Long electionProcedureId, String code, String name, String address, String postalCode, String telephone, 
            ContributorDto supervisorFirstDto, ContributorDto supervisorSecondDto, String comments, 
            Long geographicalUnitId, Long decentralAdminId, Long regionId, Long regionalUnitId, 
            Long municipalityId, Long municipalUnitId, Long municipalCommunityId, 
            Integer floorNumber, YesNoEnum disabledAccess,
            YesNoEnum foreign, CountryDto foreignCountryDto, String foreignCity,
            Integer ballotBoxes, Integer estimatedBallotBoxes, Integer voters2007) {
        
        this.id = id;
        this.electionProcedureId = electionProcedureId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.telephone = telephone;
        this.supervisorFirstDto = supervisorFirstDto.getId() == null ? null : supervisorFirstDto;
        this.supervisorSecondDto = supervisorSecondDto.getId() == null ? null : supervisorSecondDto;
        this.comments = comments;
        this.geographicalUnitId = geographicalUnitId;
        this.decentralAdminId = decentralAdminId;
        this.regionId = regionId;
        this.regionalUnitId = regionalUnitId;
        this.municipalityId = municipalityId;
        this.municipalUnitId = municipalUnitId;
        this.municipalCommunityId = municipalCommunityId;
        this.floorNumber = floorNumber;
        this.disabledAccess = YesNoEnum.booleanValue(disabledAccess);
        this.foreign = YesNoEnum.booleanValue(foreign);
        this.foreignCountryDto = foreignCountryDto.getIsoCode() == null ? null : foreignCountryDto;
        this.foreignCity = foreignCity;
        this.ballotBoxes = ballotBoxes;
        this.estimatedBallotBoxes = estimatedBallotBoxes;
        this.voters2007 = voters2007;
    }
    
    @QueryProjection
    public ElectionCenterDto(Long id, String code, String name) {
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public ContributorDto getSupervisorFirstDto() {
        return supervisorFirstDto;
    }
    
    public void setSupervisorFirstDto(ContributorDto supervisorFirstDto) {
        this.supervisorFirstDto = supervisorFirstDto;
    }
    
    public ContributorDto getSupervisorSecondDto() {
        return supervisorSecondDto;
    }
    
    public void setSupervisorSecondDto(ContributorDto supervisorSecondDto) {
        this.supervisorSecondDto = supervisorSecondDto;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
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

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Boolean getDisabledAccess() {
        return disabledAccess;
    }

    public void setDisabledAccess(Boolean disabledAccess) {
        this.disabledAccess = disabledAccess;
    }

    public Boolean getForeign() {
        return foreign;
    }

    public void setForeign(Boolean foreign) {
        this.foreign = foreign;
    }

    public CountryDto getForeignCountryDto() {
        return foreignCountryDto;
    }

    public void setForeignCountryDto(CountryDto foreignCountryDto) {
        this.foreignCountryDto = foreignCountryDto;
    }

    public String getForeignCity() {
        return foreignCity;
    }

    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
    
    public Integer getBallotBoxes() {
        return ballotBoxes;
    }
    
    public void setBallotBoxes(Integer ballotBoxes) {
        this.ballotBoxes = ballotBoxes;
    }
    
    public Integer getEstimatedBallotBoxes() {
        return estimatedBallotBoxes;
    }
    
    public void setEstimatedBallotBoxes(Integer estimatedBallotBoxes) {
        this.estimatedBallotBoxes = estimatedBallotBoxes;
    }
    
    public Integer getVoters2007() {
        return voters2007;
    }
    
    public void setVoters2007(Integer voters2007) {
        this.voters2007 = voters2007;
    }
}
