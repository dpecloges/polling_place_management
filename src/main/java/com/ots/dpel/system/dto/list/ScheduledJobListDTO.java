package com.ots.dpel.system.dto.list;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.global.utils.JsonDateTimeSecondSerializer;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;

import java.util.Date;

/**
 * Προγραμματισμένη Ενέργεια - DTO Ευρετηρίου
 */
public class ScheduledJobListDTO {
    
    private Long id;
    
    private String jobName;
    
    private String description;
    
    private String scheduledBy;
    
    @JsonSerialize(using = JsonDateTimeSecondSerializer.class)
    private Date scheduleDate;
    
    @JsonSerialize(using = JsonDateTimeSecondSerializer.class)
    private Date fireDate;
    
    private String status;
    
    private String statusDescription;
    
    @JsonSerialize(using = JsonDateTimeSecondSerializer.class)
    private Date startDate;
    
    @JsonSerialize(using = JsonDateTimeSecondSerializer.class)
    private Date endDate;
    
    private String errorId;
    
    private String errorMessage;
    
    private String fromJobName;
    
    public ScheduledJobListDTO() {
    }
    
    /**
     * Constructor για τη σελίδα του ευρετηρίου
     * @param id
     * @param jobName
     * @param description
     * @param scheduledBy
     * @param scheduleDate
     * @param fireDate
     * @param status
     * @param startDate
     * @param endDate
     * @param errorId
     * @param errorMessage
     * @param fromJobName
     */
    @QueryProjection
    public ScheduledJobListDTO(Long id, String jobName, String description, String scheduledBy,
                               Date scheduleDate, Date fireDate, ScheduledJobStatus status, Date startDate, Date endDate,
                               String errorId, String errorMessage, String fromJobName) {
        this.id = id;
        this.jobName = jobName;
        this.description = description;
        this.scheduledBy = scheduledBy;
        this.scheduleDate = scheduleDate;
        this.fireDate = fireDate;
        this.status = (status != null ? status.name() : null);
        this.statusDescription = (status != null ? status.toString() : null);
        this.startDate = startDate;
        this.endDate = endDate;
        this.errorId = errorId;
        this.errorMessage = errorMessage;
        this.fromJobName = fromJobName;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getScheduledBy() {
        return scheduledBy;
    }
    
    public void setScheduledBy(String scheduledBy) {
        this.scheduledBy = scheduledBy;
    }
    
    public Date getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public Date getFireDate() {
        return fireDate;
    }
    
    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusDescription() {
        return statusDescription;
    }
    
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getErrorId() {
        return errorId;
    }
    
    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getFromJobName() {
        return fromJobName;
    }
    
    public void setFromJobName(String fromJobName) {
        this.fromJobName = fromJobName;
    }
    
}
