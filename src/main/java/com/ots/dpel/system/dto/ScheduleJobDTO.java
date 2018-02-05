package com.ots.dpel.system.dto;

import java.util.Date;

/**
 * Προγραμματισμός λειτουργίας - Αντικείμενο DTO
 */
public class ScheduleJobDTO {
    
    /**
     * Περιγραφή λειτουργίας προς προγραμματισμό
     */
    private String jobDescription;
    
    /**
     * Ένδειξη προγραμματισμού λειτουργίας
     */
    private Boolean scheduleJob;
    
    /**
     * Ημερομηνία και ώρα προγραμματισμένης λειτουργίας
     */
    private Date scheduleDate;
    
    public ScheduleJobDTO() {
    }
    
    public ScheduleJobDTO(String jobDescription, Boolean scheduleJob, Date scheduleDate) {
        this.jobDescription = jobDescription;
        this.scheduleJob = scheduleJob;
        this.scheduleDate = scheduleDate;
    }
    
    public String getJobDescription() {
        return jobDescription;
    }
    
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
    
    public Boolean getScheduleJob() {
        return scheduleJob;
    }
    
    public void setScheduleJob(Boolean scheduleJob) {
        this.scheduleJob = scheduleJob;
    }
    
    public Date getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
}
