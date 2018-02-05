package com.ots.dpel.management.dto.list;

import java.util.Date;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;

public class ContributionListDto {
    
    private Long id;
    
    private Long volunteerId;
    
    private String type;
    
    private String round;

    private Long electionCenterId;
    
    private String electionCenterCode;
    
    private String electionCenterName;
    
    private Long electionDepartmentId;
    
    private String electionDepartmentCode;
    
    private String electionDepartmentName;
    
    private String status;
    
    private Date emailSentDate;
    
    private Date registrationDate;
    
    private String identifier;
    
    private Boolean foreign;
    
    private String foreignCountry;
    
    private String foreignCity;
    
    private Long geographicalUnitId;
    private String geographicalUnit;
    
    private Long decentralAdminId;
    private String decentralAdmin;
    
    private Long regionId;
    private String region;
    
    private Long regionalUnitId;
    private String regionalUnit;
    
    private Long municipalityId;
    private String municipality;
    
    private Long municipalUnitId;
    private String municipalUnit;
    
    public ContributionListDto() {
    }
    
    @QueryProjection
    public ContributionListDto(Long id, Long volunteerId, ContributionType type, ElectionRound round, Long electionDepartmentId,
                           ContributionStatus status, Date emailSentDate, Date registrationDate) {
        this(id, volunteerId, type, round, electionDepartmentId, status, emailSentDate, registrationDate, null);
    }
    
    @QueryProjection
    public ContributionListDto(Long id, Long volunteerId, ContributionType type, ElectionRound round, Long electionDepartmentId,
                           ContributionStatus status, Date emailSentDate, Date registrationDate, String identifier) {
        this.id = id;
        this.volunteerId = volunteerId;
        this.type = type != null ? type.name() : null;
        this.round = round != null ? round.name() : null;
        this.electionDepartmentId = electionDepartmentId;
        this.status = status != null ? status.name() : null;
        this.emailSentDate = emailSentDate;
        this.registrationDate = registrationDate;
        this.identifier = identifier;
    }
    
    @QueryProjection
    public ContributionListDto(Long id, Long volunteerId, ContributionType type, ElectionRound round, 
            Long electionCenterId, String electionCenterCode, String electionCenterName, Long electionDepartmentId,
            String electionDepartmentCode, String electionDepartmentName, ContributionStatus status, Date emailSentDate,
            Date registrationDate, String identifier, YesNoEnum foreign, String foreignCountry, String foreignCity,
            Long geographicalUnitId, String geographicalUnit, Long decentralAdminId, String decentralAdmin,
            Long regionId, String region, Long regionalUnitId, String regionalUnit, Long municipalityId,
            String municipality, Long municipalUnitId, String municipalUnit) {
        
        this.id = id;
        this.volunteerId = volunteerId;
        this.type = type != null ? type.name() : null;
        this.round = round != null ? round.name() : null;
        this.electionCenterId = electionCenterId;
        this.electionCenterCode = electionCenterCode;
        this.electionCenterName = electionCenterName;
        this.electionDepartmentId = electionDepartmentId;
        this.electionDepartmentCode = electionDepartmentCode;
        this.electionDepartmentName = electionDepartmentName;
        this.status = status != null ? status.name() : null;
        this.emailSentDate = emailSentDate;
        this.registrationDate = registrationDate;
        this.identifier = identifier;
        this.foreign = YesNoEnum.booleanValue(foreign);
        this.foreignCountry = foreignCountry;
        this.foreignCity = foreignCity;
        this.geographicalUnitId = geographicalUnitId;
        this.geographicalUnit = geographicalUnit;
        this.decentralAdminId = decentralAdminId;
        this.decentralAdmin = decentralAdmin;
        this.regionId = regionId;
        this.region = region;
        this.regionalUnitId = regionalUnitId;
        this.regionalUnit = regionalUnit;
        this.municipalityId = municipalityId;
        this.municipality = municipality;
        this.municipalUnitId = municipalUnitId;
        this.municipalUnit = municipalUnit;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVolunteerId() {
        return volunteerId;
    }
    
    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getRound() {
        return round;
    }
    
    public void setRound(String round) {
        this.round = round;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getEmailSentDate() {
        return emailSentDate;
    }
    
    public void setEmailSentDate(Date emailSentDate) {
        this.emailSentDate = emailSentDate;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean getForeign() {
        return foreign;
    }

    public void setForeign(Boolean foreign) {
        this.foreign = foreign;
    }

    public String getForeignCountry() {
        return foreignCountry;
    }

    public void setForeignCountry(String foreignCountry) {
        this.foreignCountry = foreignCountry;
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

    public String getGeographicalUnit() {
        return geographicalUnit;
    }

    public void setGeographicalUnit(String geographicalUnit) {
        this.geographicalUnit = geographicalUnit;
    }

    public Long getDecentralAdminId() {
        return decentralAdminId;
    }

    public void setDecentralAdminId(Long decentralAdminId) {
        this.decentralAdminId = decentralAdminId;
    }

    public String getDecentralAdmin() {
        return decentralAdmin;
    }

    public void setDecentralAdmin(String decentralAdmin) {
        this.decentralAdmin = decentralAdmin;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getRegionalUnitId() {
        return regionalUnitId;
    }

    public void setRegionalUnitId(Long regionalUnitId) {
        this.regionalUnitId = regionalUnitId;
    }

    public String getRegionalUnit() {
        return regionalUnit;
    }

    public void setRegionalUnit(String regionalUnit) {
        this.regionalUnit = regionalUnit;
    }

    public Long getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Long municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Long getMunicipalUnitId() {
        return municipalUnitId;
    }

    public void setMunicipalUnitId(Long municipalUnitId) {
        this.municipalUnitId = municipalUnitId;
    }

    public String getMunicipalUnit() {
        return municipalUnit;
    }

    public void setMunicipalUnit(String municipalUnit) {
        this.municipalUnit = municipalUnit;
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
}
