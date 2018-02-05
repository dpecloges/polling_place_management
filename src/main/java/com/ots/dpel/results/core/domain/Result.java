package com.ots.dpel.results.core.domain;

import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.results.core.enums.ResultType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "result", schema = "dp")
public class Result {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "dt_calculationdatetime")
    private Date calculationDateTime;
    
    @Column(name = "dt_nextcalculationdatetime")
    private Date nextCalculationDateTime;
    
    @Column(name = "n_electionprocedure_id")
    private Long electionProcedureId;
    
    @Column(name = "v_round")
    @Enumerated(EnumType.STRING)
    private ElectionRound round;
    
    @Column(name = "v_type")
    @Enumerated(EnumType.STRING)
    private ResultType type;
    
    @Column(name = "v_arg_id")
    private String argId;
    
    @Column(name = "n_totalvotes")
    private Integer totalVotes = 0;
    
    @Column(name = "n_whitevotes")
    private Integer whiteVotes = 0;
    
    @Column(name = "n_invalidvotes")
    private Integer invalidVotes = 0;
    
    @Column(name = "n_validvotes")
    private Integer validVotes = 0;
    
    @Column(name = "n_candidateonevotes")
    private Integer candidateOneVotes = 0;
    
    @Column(name = "n_candidatetwovotes")
    private Integer candidateTwoVotes = 0;
    
    @Column(name = "n_candidatethreevotes")
    private Integer candidateThreeVotes = 0;
    
    @Column(name = "n_candidatefourvotes")
    private Integer candidateFourVotes = 0;
    
    @Column(name = "n_candidatefivevotes")
    private Integer candidateFiveVotes = 0;
    
    @Column(name = "n_candidatesixvotes")
    private Integer candidateSixVotes = 0;
    
    @Column(name = "n_candidatesevenvotes")
    private Integer candidateSevenVotes = 0;
    
    @Column(name = "n_candidateeightvotes")
    private Integer candidateEightVotes = 0;
    
    @Column(name = "n_candidateninevotes")
    private Integer candidateNineVotes = 0;
    
    @Column(name = "n_candidatetenvotes")
    private Integer candidateTenVotes = 0;
    
    @Column(name = "n_submitteddepartmentcount")
    private Integer submittedDepartmentCount = 0;
    
    @Column(name = "n_totaldepartmentcount")
    private Integer totalDepartmentCount = 0;
    
    public Result() {
    }
    
    public Result(Date calculationDateTime, Long electionProcedureId, ElectionRound round, ResultType type, String argId) {
        this.calculationDateTime = calculationDateTime;
        this.electionProcedureId = electionProcedureId;
        this.round = round;
        this.type = type;
        this.argId = argId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getCalculationDateTime() {
        return calculationDateTime;
    }
    
    public void setCalculationDateTime(Date calculationDateTime) {
        this.calculationDateTime = calculationDateTime;
    }
    
    public Date getNextCalculationDateTime() {
        return nextCalculationDateTime;
    }
    
    public void setNextCalculationDateTime(Date nextCalculationDateTime) {
        this.nextCalculationDateTime = nextCalculationDateTime;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public ElectionRound getRound() {
        return round;
    }
    
    public void setRound(ElectionRound round) {
        this.round = round;
    }
    
    public ResultType getType() {
        return type;
    }
    
    public void setType(ResultType type) {
        this.type = type;
    }
    
    public String getArgId() {
        return argId;
    }
    
    public void setArgId(String argId) {
        this.argId = argId;
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
    
    public Integer getSubmittedDepartmentCount() {
        return submittedDepartmentCount;
    }
    
    public void setSubmittedDepartmentCount(Integer submittedDepartmentCount) {
        this.submittedDepartmentCount = submittedDepartmentCount;
    }
    
    public Integer getTotalDepartmentCount() {
        return totalDepartmentCount;
    }
    
    public void setTotalDepartmentCount(Integer totalDepartmentCount) {
        this.totalDepartmentCount = totalDepartmentCount;
    }
    
    public String computeCacheSignature() {
        return (electionProcedureId != null ? electionProcedureId.toString() : "") + "_" +
                (round != null ? round.name() : "") + "_" +
                (calculationDateTime != null ? DpDateUtils.getDateTimeTillSecondsString(calculationDateTime) : "") + "_" +
                (type != null ? type.name() : "") + "_" +
                (argId != null ? argId : "");
    }
    
    public String computeCacheOlderSignature(Date olderCalculationDateTime) {
        return (electionProcedureId != null ? electionProcedureId.toString() : "") + "_" +
                (round != null ? round.name() : "") + "_" +
                (olderCalculationDateTime != null ? DpDateUtils.getDateTimeTillSecondsString(olderCalculationDateTime) : "") + "_" +
                (type != null ? type.name() : "") + "_" +
                (argId != null ? argId : "");
    }
}
