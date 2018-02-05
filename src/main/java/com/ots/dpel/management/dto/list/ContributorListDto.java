package com.ots.dpel.management.dto.list;

import com.mysema.query.annotations.QueryProjection;

public class ContributorListDto {
    
    private Long id;
    
    private String lastName;
    
    private String firstName;
    
    private String email;
    
    private String cellphone;
    
    
    public ContributorListDto() {
    }
    
    @QueryProjection
    public ContributorListDto(Long id, String lastName, String firstName, String email, String cellphone) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.cellphone = cellphone;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
