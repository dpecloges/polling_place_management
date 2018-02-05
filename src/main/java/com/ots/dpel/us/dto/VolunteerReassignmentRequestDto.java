package com.ots.dpel.us.dto;

import java.io.Serializable;

public class VolunteerReassignmentRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long volunteerId;
    
    private Long fromElectionDepartmentId;
    
    private Long toElectionDepartmentId;
    
    private String round;
    
    public boolean checkValidity() {
        return (
            volunteerId != null && 
            fromElectionDepartmentId != null && 
            toElectionDepartmentId != null && 
            round != null);
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Long getFromElectionDepartmentId() {
        return fromElectionDepartmentId;
    }

    public void setFromElectionDepartmentId(Long fromElectionDepartmentId) {
        this.fromElectionDepartmentId = fromElectionDepartmentId;
    }

    public Long getToElectionDepartmentId() {
        return toElectionDepartmentId;
    }

    public void setToElectionDepartmentId(Long toElectionDepartmentId) {
        this.toElectionDepartmentId = toElectionDepartmentId;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return "VolunteerReassignmentRequestDto [volunteerId=" + volunteerId + ", fromElectionDepartmentId="
                + fromElectionDepartmentId + ", toElectionDepartmentId=" + toElectionDepartmentId + ", round=" + round
                + "]";
    }
}
