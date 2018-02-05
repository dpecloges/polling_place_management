package com.ots.dpel.us.dto;

import java.io.Serializable;

import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;

public class VolunteerAssignmentRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long volunteerId;
    
    private Long electionDepartmentId;
    
    private ElectionRound round;
    
    private ContributionType contributionType;
    
    private Long contributionCandidateId;
    
    private String username;
    
    private String password;
    
    private String passwordConfirmation;
    
    private Boolean notify;

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }

    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }

    public ContributionType getContributionType() {
        return contributionType;
    }

    public void setContributionType(ContributionType contributionType) {
        this.contributionType = contributionType;
    }
    
    public Long getContributionCandidateId() {
        return contributionCandidateId;
    }
    
    public void setContributionCandidateId(Long contributionCandidateId) {
        this.contributionCandidateId = contributionCandidateId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public ElectionRound getRound() {
        return round;
    }

    public void setRound(ElectionRound round) {
        this.round = round;
    }

    public Boolean getNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }
}
