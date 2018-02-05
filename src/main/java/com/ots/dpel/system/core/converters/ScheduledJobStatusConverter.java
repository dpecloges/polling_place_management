package com.ots.dpel.system.core.converters;

import com.ots.dpel.system.core.enums.ScheduledJobStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScheduledJobStatusConverter implements AttributeConverter<ScheduledJobStatus, String> {
    
    @Override
    public String convertToDatabaseColumn(ScheduledJobStatus attribute) {
        
        if (attribute == null) return null;
        
        switch (attribute) {
            case SCHEDULED:
                return "SCHEDULED";
            
            case RUNNING:
                return "RUNNING";
            
            case COMPLETED:
                return "COMPLETED";
            
            case FAILED:
                return "FAILED";
            
            case ADDED:
                return "ADDED";
            
            default:
                break;
        }
        
        return null;
    }
    
    @Override
    public ScheduledJobStatus convertToEntityAttribute(String dbData) {
        
        if (dbData == null) return null;
        
        switch (dbData) {
            case "SCHEDULED":
                return ScheduledJobStatus.SCHEDULED;
            
            case "RUNNING":
                return ScheduledJobStatus.RUNNING;
            
            case "COMPLETED":
                return ScheduledJobStatus.COMPLETED;
            
            case "FAILED":
                return ScheduledJobStatus.FAILED;
            
            case "ADDED":
                return ScheduledJobStatus.ADDED;
            
            default:
                break;
        }
        
        return null;
    }
    
}
