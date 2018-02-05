package com.ots.dpel.ep.args;

import com.ots.dpel.common.args.SearchableArguments;

public class VoterArgs implements SearchableArguments {
    
    private String lastName;
    
    private String firstName;
    
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
