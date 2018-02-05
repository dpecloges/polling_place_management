package com.ots.dpel.management.core.domain;

import com.ots.dpel.common.core.enums.YesNoEnum;
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
@Table(name = "electionprocedure", schema = "dp")
public class ElectionProcedure {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "v_name")
    private String name;
    
    @Column(name = "n_current")
    private YesNoEnum current;
    
    @Column(name = "v_round")
    @Enumerated(EnumType.STRING)
    private ElectionRound round;
    
    @Column(name = "dt_resultlastcalcdatetime")
    private Date resultsLastCalcDateTime;
    
    @Column(name = "dt_snapshotlastcalcdatetime")
    private Date snapshotLastCalcDateTime;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public YesNoEnum getCurrent() {
        return current;
    }
    
    public void setCurrent(YesNoEnum current) {
        this.current = current;
    }
    
    public ElectionRound getRound() {
        return round;
    }
    
    public void setRound(ElectionRound round) {
        this.round = round;
    }
    
    public Date getResultsLastCalcDateTime() {
        return resultsLastCalcDateTime;
    }
    
    public void setResultsLastCalcDateTime(Date resultsLastCalcDateTime) {
        this.resultsLastCalcDateTime = resultsLastCalcDateTime;
    }
    
    public Date getSnapshotLastCalcDateTime() {
        return snapshotLastCalcDateTime;
    }
    
    public void setSnapshotLastCalcDateTime(Date snapshotLastCalcDateTime) {
        this.snapshotLastCalcDateTime = snapshotLastCalcDateTime;
    }
}
