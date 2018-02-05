package com.ots.dpel.auth.args;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class UserArgs implements SearchableArguments {
    
    private String username;
    
    private String lastName;
    
    private String firstName;
    
    private String email;
    
    private YesNoEnum hasElectionDepartmentId;
    
    private Long electionCenterId;
    
    private Long electionDepartmentId;
    
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public YesNoEnum getHasElectionDepartmentId() {
        return hasElectionDepartmentId;
    }
    
    public void setHasElectionDepartmentId(YesNoEnum hasElectionDepartmentId) {
        this.hasElectionDepartmentId = hasElectionDepartmentId;
    }
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
}

