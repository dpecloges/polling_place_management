package com.ots.dpel.system.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;

import java.util.Date;

/**
 * Χρονοπρογραμματισμένες Εργασίες - Αντικείμενο DTO
 */
public class ScheduledJobDTO {
    
    /**
     * Id Εγγραφής
     */
    private Long id;
    
    /**
     * Αναγνωριστικό Όνομα Εργασίας
     */
    private String jobName;
    
    /**
     * Αναγνωριστικό Ομάδας Εργασιών
     */
    private String jobGroup;
    
    /**
     * Περιγραφή
     */
    private String description;
    
    /**
     * Χρήστης Προγραμματισμού Εργασίας
     */
    private String scheduledBy;
    
    /**
     * Ημερομηνία Καταχώρησης Εργασίας
     */
    private Date scheduleDate;
    
    /**
     * Ημερομηνία Προγραμματισμού Εργασίας
     */
    private Date fireDate;
    
    /**
     * Κατάσταση
     */
    private String status;
    
    /**
     * Ημερομηνία Έναρξης
     */
    private Date startDate;
    
    /**
     * Ημερομηνία Λήξης
     */
    private Date endDate;
    
    /**
     * Αναγνωριστικό Σφάλματος
     */
    private String errorId;
    
    /**
     * Περιγραφή Σφάλματος
     */
    private String errorMessage;
    
    /**
     * Αναγνωριστικό Όνομα Σχετικής Εργασίας
     */
    private String fromJobName;
    
    public ScheduledJobDTO() {
    }
    
    @QueryProjection
    public ScheduledJobDTO(Long id, String jobName, String jobGroup, String description, String scheduledBy, Date scheduleDate, Date fireDate,
                           ScheduledJobStatus status, Date startDate, Date endDate, String errorId, String errorMessage, String fromJobName) {
        this.id = id;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
        this.scheduledBy = scheduledBy;
        this.scheduleDate = scheduleDate;
        this.fireDate = fireDate;
        this.status = status != null ? status.name() : null;
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
    
    public String getJobGroup() {
        return jobGroup;
    }
    
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
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
