package com.ots.dpel.ep.args;

import com.ots.dpel.common.args.SearchableArguments;

public class SnapshotArgs implements SearchableArguments {
    
    private String type;
    
    private String argId;
    
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getArgId() {
        return argId;
    }
    
    public void setArgId(String argId) {
        this.argId = argId;
    }
}
