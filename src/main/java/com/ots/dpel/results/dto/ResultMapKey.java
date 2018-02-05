package com.ots.dpel.results.dto;

import com.ots.dpel.results.core.enums.ResultType;

public class ResultMapKey {
    
    private ResultType type;
    
    private String argId;
    
    
    public ResultMapKey(ResultType type, String argId) {
        this.type = type;
        this.argId = argId;
    }
    
    public ResultType getType() {
        return type;
    }
    
    public void setType(ResultType type) {
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
        
        ResultMapKey that = (ResultMapKey) o;
        
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
