package com.ots.dpel.management.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.management.core.enums.ElectionRound;

import java.util.Date;

public class ElectionProcedureDto {
    
    private Long id;
    
    private String name;
    
    private Boolean current;
    
    private String round;
    
    private Date resultsLastCalcDateTime;
    
    private Date snapshotLastCalcDateTime;
    
    
    public ElectionProcedureDto() {
    }
    
    @QueryProjection
    public ElectionProcedureDto(Long id, String name, YesNoEnum current, ElectionRound round, Date resultsLastCalcDateTime, Date snapshotLastCalcDateTime) {
        this.id = id;
        this.name = name;
        this.current = current != null && current.equals(YesNoEnum.YES);
        this.round = round != null ? round.name() : null;
        this.resultsLastCalcDateTime = resultsLastCalcDateTime;
        this.snapshotLastCalcDateTime = snapshotLastCalcDateTime;
    }
    
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
    
    public Boolean getCurrent() {
        return current;
    }
    
    public void setCurrent(Boolean current) {
        this.current = current;
    }
    
    public String getRound() {
        return round;
    }
    
    public void setRound(String round) {
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
