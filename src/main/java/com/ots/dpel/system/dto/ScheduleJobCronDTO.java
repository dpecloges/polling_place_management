package com.ots.dpel.system.dto;

public class ScheduleJobCronDTO {
    
    private String cronExpression;
    
    public ScheduleJobCronDTO() {
    }
    
    public String getCronExpression() {
        return cronExpression;
    }
    
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
