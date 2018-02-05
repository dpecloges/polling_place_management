package com.ots.dpel.management.core.domain;

import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "contribution", schema = "dp")
public class Contribution {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "n_volunteer_id")
    private Long volunteerId;
    
    @ManyToOne
    @JoinColumn(name = "n_electiondepartment_id", referencedColumnName = "N_ID")
    private ElectionDepartment electionDepartment;
    
    @Column(name = "v_type")
    @Enumerated(EnumType.STRING)
    private ContributionType type;
    
    @Column(name = "v_round")
    @Enumerated(EnumType.STRING)
    private ElectionRound round;
    
    @Column(name = "v_identifier")
    private String identifier;
    
    @Column(name = "v_status")
    @Enumerated(EnumType.STRING)
    private ContributionStatus status;
    
    @Column(name = "dt_emailsentdate")
    private Date emailSentDate;
    
    @Column(name = "dt_registrationdate")
    private Date registrationDate;
    
    @Column(name = "n_candidate_id")
    private Long candidateId;
    
    
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
    
    public ElectionDepartment getElectionDepartment() {
        return electionDepartment;
    }
    
    public void setElectionDepartment(ElectionDepartment electionDepartment) {
        this.electionDepartment = electionDepartment;
    }
    
    public ContributionType getType() {
        return type;
    }
    
    public void setType(ContributionType type) {
        this.type = type;
    }
    
    public ElectionRound getRound() {
        return round;
    }
    
    public void setRound(ElectionRound round) {
        this.round = round;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public ContributionStatus getStatus() {
        return status;
    }
    
    public void setStatus(ContributionStatus status) {
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
    
    public Long getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
