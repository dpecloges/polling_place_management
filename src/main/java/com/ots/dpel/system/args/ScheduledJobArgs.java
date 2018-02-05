package com.ots.dpel.system.args;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;

import java.util.Date;

/**
 * Φίλτρα ευρετηρίου Προγραμματισμένων Εργασιών
 */
public class ScheduledJobArgs implements SearchableArguments {
    
    private String description;
    
    private ScheduledJobStatus status;
    
    private Date scheduleDateFrom;
    
    private Date scheduleDateTo;
    
    private Date fireDateFrom;
    
    private Date fireDateTo;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ScheduledJobStatus getStatus() {
        return status;
    }
    
    public void setStatus(ScheduledJobStatus status) {
        this.status = status;
    }
    
    public Date getScheduleDateFrom() {
        return scheduleDateFrom;
    }
    
    public void setScheduleDateFrom(Date scheduleDateFrom) {
        this.scheduleDateFrom = scheduleDateFrom;
    }
    
    public Date getScheduleDateTo() {
        return scheduleDateTo;
    }
    
    public void setScheduleDateTo(Date scheduleDateTo) {
        this.scheduleDateTo = scheduleDateTo;
    }
    
    public Date getFireDateFrom() {
        return fireDateFrom;
    }
    
    public void setFireDateFrom(Date fireDateFrom) {
        this.fireDateFrom = fireDateFrom;
    }
    
    public Date getFireDateTo() {
        return fireDateTo;
    }
    
    public void setFireDateTo(Date fireDateTo) {
        this.fireDateTo = fireDateTo;
    }
    
}
