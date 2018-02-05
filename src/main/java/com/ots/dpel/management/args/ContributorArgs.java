package com.ots.dpel.management.args;

import com.ots.dpel.common.args.SearchableArguments;

public class ContributorArgs implements SearchableArguments {
    
    private Long electionProcedureId;
    
    private String lastName;
    
    private String firstName;
    
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
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
