package com.ots.dpel.auth.dto;

import com.mysema.query.annotations.QueryProjection;

public class DpUserBasicDto {
    
    private Long id;
    
    private Long electionDepartmentId;
    
    private String username;
    
    private String email;
    
    private String firstName;
    
    private String lastName;
    
    private String type;

    public DpUserBasicDto() {
        
    }

    @QueryProjection
    public DpUserBasicDto(Long id, Long electionDepartmentId, String username, String email, 
            String firstName, String lastName, String type) {
        
        this.id = id;
        this.electionDepartmentId = electionDepartmentId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }

    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
}
