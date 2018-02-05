package com.ots.dpel.us.dto.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ext.dto.list.VolunteerListDto;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;

public class UsVolunteerExportListDto extends VolunteerListDto {

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
    
    private String foreign;
    
    private String foreignCountry;
    
    private String foreignCity;
    
    private String geographicalUnit;
    
    private String decentralAdmin;
    
    private String region;
    
    private String regionalUnit;
    
    private String municipality;
    
    private String municipalUnit;

    public UsVolunteerExportListDto() {
    }
    
    public UsVolunteerExportListDto(UsVolunteerListDto volunteerDto) {
        BeanUtils.copyProperties(volunteerDto, this, new String[] {"contributionStatus", "contributionType", "foreign"});
        
        // Αντικατάσταση σταθεράς ContributionStatus με το λεκτικό της
        String contributionStatus = volunteerDto.getContributionStatus();
        try {
            this.contributionStatus = ContributionStatus.valueOf(contributionStatus).toString();
        } catch (Exception e) {
            // Σε περίπτωση μη αποδεκτής τιμής το αντίστοιχο κελί στο excel θα είναι κενό
            volunteerDto.setContributionStatus("");
        }
        
        // Αντικατάσταση σταθεράς ContributionType με το λεκτικό της
        String contributionType = volunteerDto.getContributionType();
        try {
            this.contributionType = ContributionType.valueOf(contributionType).toString();
        } catch (Exception e) {
            // Σε περίπτωση μη αποδεκτής τιμής το αντίστοιχο κελί στο excel θα είναι κενό
            volunteerDto.setContributionStatus("");
        }
        
        Boolean foreign = volunteerDto.getForeign();
        this.foreign = foreign != null? YesNoEnum.of(foreign).toString(): "";
    }
    
    public static List<UsVolunteerExportListDto> ofUsVolunteerListDtos(List<UsVolunteerListDto> volunteerDtos) {
        List<UsVolunteerExportListDto> list = new ArrayList<>();
        
        if (volunteerDtos == null) {
            return list;
        }
        
        for (UsVolunteerListDto volunteerDto: volunteerDtos) {
            list.add(new UsVolunteerExportListDto(volunteerDto));
        }
        
        return list;
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

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
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
}
