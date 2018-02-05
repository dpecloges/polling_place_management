package com.ots.dpel.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;

import java.util.Date;

public class ContributionDto {
    
    private Long id;
    
    private Long volunteerId;
    
    @JsonProperty("volunteer")
    private VolunteerDto volunteerDto;
    
    private String type;
    
    private String round;
    
    private Long electionDepartmentId;
    
    private String status;
    
    private Date emailSentDate;
    
    private Date registrationDate;
    
    private String identifier;
    
    private Long candidateId;
    
    public ContributionDto() {
    }
    
    @QueryProjection
    public ContributionDto(Long id, Long volunteerId, ContributionType type, ElectionRound round, Long electionDepartmentId,
                           ContributionStatus status, Date emailSentDate, Date registrationDate, String identifier, Long candidateId) {
        this.id = id;
        this.volunteerId = volunteerId;
        this.type = type != null ? type.name() : null;
        this.round = round != null ? round.name() : null;
        this.electionDepartmentId = electionDepartmentId;
        this.status = status != null ? status.name() : null;
        this.emailSentDate = emailSentDate;
        this.registrationDate = registrationDate;
        this.identifier = identifier;
        this.candidateId = candidateId;
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
    
    public VolunteerDto getVolunteerDto() {
        return volunteerDto;
    }
    
    public void setVolunteerDto(VolunteerDto volunteerDto) {
        this.volunteerDto = volunteerDto;
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
    
    public Long getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
