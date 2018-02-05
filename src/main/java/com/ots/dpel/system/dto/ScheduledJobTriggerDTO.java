package com.ots.dpel.system.dto;

import java.util.Date;

public class ScheduledJobTriggerDTO {
    
    private String jobGroup;
    
    private Boolean pending;
    
    private Date previousFireDate;
    
    private Date nextFireDate;
    
    public ScheduledJobTriggerDTO() {
    }
    
    public ScheduledJobTriggerDTO(String jobGroup, Boolean pending) {
        this.jobGroup = jobGroup;
        this.pending = pending;
    }
    
    public String getJobGroup() {
        return jobGroup;
    }
    
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    
    public Boolean getPending() {
        return pending;
    }
    
    public void setPending(Boolean pending) {
        this.pending = pending;
    }
    
    public Date getPreviousFireDate() {
        return previousFireDate;
    }
    
    public void setPreviousFireDate(Date previousFireDate) {
        this.previousFireDate = previousFireDate;
    }
    
    public Date getNextFireDate() {
        return nextFireDate;
    }
    
    public void setNextFireDate(Date nextFireDate) {
        this.nextFireDate = nextFireDate;
    }
}
