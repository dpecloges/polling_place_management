package com.ots.dpel.us.dto.list;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.dto.list.VolunteerListDto;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.list.ContributionListDto;

public class UsVolunteerListDto extends VolunteerListDto {

    private Long id;
    
    private Long publicIdentifier;
    
    private String eklSpecialNo;
    
    private String lastName;
    
    private String firstName;
    
    private String email;

    private Long electionCenterId;
    
    private String electionCenterCode;
    
    private String electionCenterName;
    
    private Long electionDepartmentId;
    
    private String electionDepartmentCode;
    
    private String electionDepartmentName;
    
    private Long contributionId;
    
    // COMMITTEE_LEADER, COMMITTEE_MEMBER, ID_VERIFIER, TREASURER
    private String contributionType;
    
    private String contributionStatus;
    
    private String round;
    
    private Date emailSentDate;
    
    private Date registrationDate;
    
    private String username;
    
    private String userType;
    
    private Boolean foreign;
    
    private String foreignCountry;
    
    private String foreignCity;
    
    private String geographicalUnit;
    
    private String decentralAdmin;
    
    private String region;
    
    private String regionalUnit;
    
    private String municipality;
    
    private String municipalUnit;
    
    private String electionCenterDisplayName;
    
    private String electionDepartmentDisplayName;

    public UsVolunteerListDto() {
    }

    public UsVolunteerListDto(Long id, Long publicIdentifier, String eklSpecialNo, String lastName, String firstName,
            String email) {
        super(id, publicIdentifier, eklSpecialNo, lastName, firstName, email);
    }

    @QueryProjection
    public UsVolunteerListDto(Long id, Long publicIdentifier, String eklSpecialNo, String lastName, String firstName,
            String email, Long electionCenterId, String electionCenterCode, String electionCenterName,
            Long electionDepartmentId, String electionDepartmentCode, String electionDepartmentName,
            Long contributionId, ContributionType contributionType, ContributionStatus contributionStatus, 
            ElectionRound round, Date emailSentDate, Date registrationDate, String username, String userType, 
            YesNoEnum foreign, String foreignCountry, String foreignCity, 
            String geographicalUnit, String decentralAdmin, String region, 
            String regionalUnit, String municipality, String municipalUnit) {
        
        this.id = id;
        this.publicIdentifier = publicIdentifier;
        this.eklSpecialNo = eklSpecialNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.electionCenterId = electionCenterId;
        this.electionCenterCode = electionCenterCode;
        this.electionCenterName = electionCenterName;
        this.electionDepartmentId = electionDepartmentId;
        this.electionDepartmentCode = electionDepartmentCode;
        this.electionDepartmentName = electionDepartmentName;
        this.contributionId = contributionId;
        this.contributionType = contributionType != null? contributionType.name(): null;
        this.contributionStatus = contributionStatus != null? contributionStatus.name(): null;
        this.round = round != null? round.name(): null;
        this.emailSentDate = emailSentDate;
        this.registrationDate = registrationDate;
        this.username = username;
        this.userType = userType;
        
        this.foreign = YesNoEnum.booleanValue(foreign);
        this.foreignCountry = foreignCountry;
        this.foreignCity = foreignCity;
        this.geographicalUnit = geographicalUnit;
        this.decentralAdmin = decentralAdmin;
        this.region = region;
        this.regionalUnit = regionalUnit;
        this.municipality = municipality;
        this.municipalUnit = municipalUnit;
    }
    
    public UsVolunteerListDto(VolunteerDto volunteerDto) {
        BeanUtils.copyProperties(volunteerDto, this);
    }
    
    public void setContributionDto(ContributionListDto contributionDto) {
        this.contributionId = contributionDto.getId();
        this.contributionStatus = contributionDto.getStatus();
        this.contributionType = contributionDto.getType();
        this.round = contributionDto.getRound();
        
        this.electionCenterId = contributionDto.getElectionCenterId();
        this.electionCenterCode = contributionDto.getElectionCenterCode();
        this.electionCenterName = contributionDto.getElectionCenterName();
        this.electionDepartmentId = contributionDto.getElectionDepartmentId();
        this.electionDepartmentCode = contributionDto.getElectionDepartmentCode();
        this.electionDepartmentName = contributionDto.getElectionDepartmentName();
        
        this.geographicalUnit = contributionDto.getGeographicalUnit();
        this.decentralAdmin = contributionDto.getDecentralAdmin();
        this.region = contributionDto.getRegion();
        this.regionalUnit = contributionDto.getRegionalUnit();
        this.municipality = contributionDto.getMunicipality();
        this.municipalUnit = contributionDto.getMunicipalUnit();
        
        this.foreign = contributionDto.getForeign();
        this.foreignCountry = contributionDto.getForeignCountry();
        this.foreignCity = contributionDto.getForeignCity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(Long publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    public String getEklSpecialNo() {
        return eklSpecialNo;
    }

    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
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

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }

    public String getContributionType() {
        return contributionType;
    }

    public void setContributionType(String contributionType) {
        this.contributionType = contributionType;
    }

    public String getContributionStatus() {
        return contributionStatus;
    }

    public void setContributionStatus(String contributionStatus) {
        this.contributionStatus = contributionStatus;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getGeographicalUnit() {
        return geographicalUnit;
    }

    public void setGeographicalUnit(String geographicalUnit) {
        this.geographicalUnit = geographicalUnit;
    }

    public String getDecentralAdmin() {
        return decentralAdmin;
    }

    public void setDecentralAdmin(String decentralAdmin) {
        this.decentralAdmin = decentralAdmin;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionalUnit() {
        return regionalUnit;
    }

    public void setRegionalUnit(String regionalUnit) {
        this.regionalUnit = regionalUnit;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getMunicipalUnit() {
        return municipalUnit;
    }

    public void setMunicipalUnit(String municipalUnit) {
        this.municipalUnit = municipalUnit;
    }
    
    public String getElectionCenterDisplayName() {
        String thisElectionCenterCode = (this.electionCenterCode == null) ? "" : this.electionCenterCode;
        Boolean thisForeign = (this.foreign == null) ? false : this.foreign;
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipality = (this.municipality == null) ? "" : this.municipality;
        
        return thisElectionCenterCode + " - " + (thisForeign ? thisElectionCenterName : thisMunicipality);
    }
    
    public void setElectionCenterDisplayName(String electionCenterDisplayName) {
        this.electionCenterDisplayName = electionCenterDisplayName;
    }
    
    public String getElectionDepartmentDisplayName() {
        String thisElectionDepartmentName = (this.electionDepartmentName == null) ? "" : this.electionDepartmentName;
        Boolean thisForeign = (this.foreign == null) ? false : this.foreign;
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipality = (this.municipality == null) ? "" : this.municipality;
        
        return thisElectionDepartmentName + " - " + (thisForeign ? thisElectionCenterName : thisMunicipality);
    }
    
    public void setElectionDepartmentDisplayName(String electionDepartmentDisplayName) {
        this.electionDepartmentDisplayName = electionDepartmentDisplayName;
    }
}
