package com.ots.dpel.system.core.domain;

import com.ots.dpel.system.core.enums.ScheduledJobStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Χρονοπρογραμματισμένες Εργασίες
 */
@Entity
@Table(name = "scheduled_job", schema = "dp")
public class ScheduledJob {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    /**
     * Αναγνωριστικό Όνομα Εργασίας
     */
    @Column(name = "V_JOBNAME")
    private String jobName;
    
    /**
     * Αναγνωριστικό Ομάδας Εργασιών
     */
    @Column(name = "V_JOBGROUP")
    private String jobGroup;
    
    /**
     * Περιγραφή
     */
    @Column(name = "V_DESCRIPTION")
    private String description;
    
    /**
     * Χρήστης Προγραμματισμού Εργασίας
     */
    @Column(name = "V_SCHEDULEDBY")
    private String scheduledBy;
    
    /**
     * Ημερομηνία Καταχώρησης Εργασίας
     */
    @Column(name = "DT_SCHEDULEDATE")
    private Date scheduleDate;
    
    /**
     * Ημερομηνία Προγραμματισμού Εργασίας
     */
    @Column(name = "DT_FIREDATE")
    private Date fireDate;
    
    /**
     * Κατάσταση
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "V_STATUS")
    private ScheduledJobStatus status;
    
    /**
     * Ημερομηνία Έναρξης
     */
    @Column(name = "DT_STARTDATE")
    private Date startDate;
    
    /**
     * Ημερομηνία Λήξης
     */
    @Column(name = "DT_ENDDATE")
    private Date endDate;
    
    /**
     * Αναγνωριστικό Σφάλματος
     */
    @Column(name = "V_ERRORID")
    private String errorId;
    
    /**
     * Περιγραφή Σφάλματος
     */
    @Column(name = "V_ERRORMESSAGE")
    private String errorMessage;
    
    /**
     * Αναγνωριστικό Όνομα Σχετικής Εργασίας
     */
    @Column(name = "V_FROMJOBNAME")
    private String fromJobName;
    
    public ScheduledJob() {
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
    
    public ScheduledJobStatus getStatus() {
        return status;
    }
    
    public void setStatus(ScheduledJobStatus status) {
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
