package com.ots.dpel.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ots.dpel.global.utils.JsonDateTimeSerializer;

import java.util.Date;

/**
 * Στοιχεία Ενέργειας Cache Eviction - Αντικείμενο Response DTO
 */
public class CacheEvictionResponse {
    
    /**
     * Το όνομα της Cache που γίνεται evicted
     */
    private String cacheName;
    
    /**
     * Ημερομηνία και ώρα της ενέργειας
     */
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date date;
    
    /**
     * Ένδειξη επιτυχίας εκτέλεσης της ενέργειας
     */
    private Boolean evicted;
    
    /**
     * Μήνυμα
     */
    private String message;
    
    public CacheEvictionResponse() {
    }
    
    public CacheEvictionResponse(String cacheName, Date date, Boolean evicted, String message) {
        this.cacheName = cacheName;
        this.date = date;
        this.evicted = evicted;
        this.message = message;
    }
    
    public String getCacheName() {
        return cacheName;
    }
    
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Boolean getEvicted() {
        return evicted;
    }
    
    public void setEvicted(Boolean evicted) {
        this.evicted = evicted;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
