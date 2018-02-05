package com.ots.dpel.ep.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.ep.core.enums.SnapshotType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SnapshotsDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String type;
    
    private String argId;
    
    private Date calculationDateTime;
    
    private Date nextCalculationDateTime;
    
    private String name;
    
    private Integer totalDepartmentCount;
    
    private Integer startedDepartmentCount;
    
    private Integer submittedDepartmentCount;
    
    private Integer voterCount;
    
    private Integer undoneVoterCount;
    
    private List<SnapshotsDto> descendantSnapshots = new ArrayList<>();
    
    public SnapshotsDto() {
    }
    
    @QueryProjection
    public SnapshotsDto(Long id, SnapshotType type, String argId, Date calculationDateTime, Date nextCalculationDateTime, String name, Integer totalDepartmentCount, Integer
            startedDepartmentCount, Integer submittedDepartmentCount, Integer voterCount, Integer undoneVoterCount) {
        this.id = id;
        this.type = type == null ? null : type.name();
        this.argId = argId;
        this.calculationDateTime = calculationDateTime;
        this.nextCalculationDateTime = nextCalculationDateTime;
        this.name = name;
        this.totalDepartmentCount = totalDepartmentCount == null ? 0 : totalDepartmentCount;
        this.startedDepartmentCount = startedDepartmentCount == null ? 0 : startedDepartmentCount;
        this.submittedDepartmentCount = submittedDepartmentCount == null? 0 : submittedDepartmentCount;
        this.voterCount = voterCount == null ? 0 : voterCount;
        this.undoneVoterCount = undoneVoterCount == null ? 0 : undoneVoterCount;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getArgId() {
        return argId;
    }
    
    public void setArgId(String argId) {
        this.argId = argId;
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
    
    public List<SnapshotsDto> getDescendantSnapshots() {
        return descendantSnapshots;
    }
    
    public void setDescendantSnapshots(List<SnapshotsDto> descendantSnapshots) {
        this.descendantSnapshots = descendantSnapshots;
    }
    
    public SnapshotsDto normalizeGeneralArgs() {
        
        if (this.type.equals(SnapshotType.ALL.name())) {
            this.type = null;
            this.argId = null;
        } else if (this.type.equals(SnapshotType.GREECE.name())) {
            this.type = "GENERAL";
            this.argId = SnapshotType.GREECE.name();
        } else if (this.type.equals(SnapshotType.ABROAD.name())) {
            this.type = "GENERAL";
            this.argId = SnapshotType.ABROAD.name();
        }
        
        return this;
    }
    
    public void orderDescendantSnapshotsByVoterCount() {
        this.descendantSnapshots.removeAll(Collections.singleton(null));
        Collections.sort(this.descendantSnapshots, new Comparator<SnapshotsDto>() {
            @Override
            public int compare(SnapshotsDto o1, SnapshotsDto o2) {
                return o2.getVoterCount().compareTo(o1.getVoterCount());
            }
        });
    
    }
}
