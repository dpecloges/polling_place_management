package com.ots.dpel.ep.dto;

import com.ots.dpel.ep.core.enums.SnapshotType;
import com.ots.dpel.results.dto.ResultMapKey;

public class SnapshotMapKey {
    
    private SnapshotType type;
    
    private String argId;
    
    
    public SnapshotMapKey(SnapshotType type, String argId) {
        this.type = type;
        this.argId = argId;
    }
    
    public SnapshotType getType() {
        return type;
    }
    
    public void setType(SnapshotType type) {
        this.type = type;
    }
    
    public String getArgId() {
        return argId;
    }
    
    public void setArgId(String argId) {
        this.argId = argId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        SnapshotMapKey that = (SnapshotMapKey) o;
        
        if (type != that.type) return false;
        if (!argId.equals(that.argId)) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + argId.hashCode();
        return result;
    }
}
