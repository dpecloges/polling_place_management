package com.ots.dpel.ep.core.domain;

import com.ots.dpel.ep.core.enums.SnapshotType;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.management.core.enums.ElectionRound;

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
@Table(name = "snapshot", schema = "dp")
public class Snapshot {
    
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
    private SnapshotType type;
    
    @Column(name = "v_arg_id")
    private String argId;
    
    @Column(name = "v_name")
    private String name;
    
    @Column(name = "n_totaldepartmentcount")
    private Integer totalDepartmentCount = 0;
    
    @Column(name = "n_starteddepartmentcount")
    private Integer startedDepartmentCount = 0;
    
    @Column(name = "n_submitteddepartmentcount")
    private Integer submittedDepartmentCount = 0;
    
    @Column(name = "n_votercount")
    private Integer voterCount = 0;
    
    @Column(name = "n_undonevotercount")
    private Integer undoneVoterCount = 0;
    
    public Snapshot() {
    }
    
    public Snapshot(Date calculationDateTime, Long electionProcedureId, ElectionRound round, SnapshotType type, String argId) {
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
    
    public SnapshotType getType() {
        return type;
    }
    
    public void setType(SnapshotType type) {
        this.type = type;
    }
    
    public String getArgId() {
        return argId;
    }
    
    public void setArgId(String argId) {
        this.argId = argId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getTotalDepartmentCount() {
        return totalDepartmentCount;
    }
    
    public void setTotalDepartmentCount(Integer totalDepartmentCount) {
        this.totalDepartmentCount = totalDepartmentCount;
    }
    
    public Integer getStartedDepartmentCount() {
        return startedDepartmentCount;
    }
    
    public void setStartedDepartmentCount(Integer startedDepartmentCount) {
        this.startedDepartmentCount = startedDepartmentCount;
    }
    
    public Integer getSubmittedDepartmentCount() {
        return submittedDepartmentCount;
    }
    
    public void setSubmittedDepartmentCount(Integer submittedDepartmentCount) {
        this.submittedDepartmentCount = submittedDepartmentCount;
    }
    
    public Integer getVoterCount() {
        return voterCount;
    }
    
    public void setVoterCount(Integer voterCount) {
        this.voterCount = voterCount;
    }
    
    public Integer getUndoneVoterCount() {
        return undoneVoterCount;
    }
    
    public void setUndoneVoterCount(Integer undoneVoterCount) {
        this.undoneVoterCount = undoneVoterCount;
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
