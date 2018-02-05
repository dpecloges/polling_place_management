package com.ots.dpel.results.args;

import com.ots.dpel.common.args.SearchableArguments;

public class ResultArgs implements SearchableArguments {
    
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
