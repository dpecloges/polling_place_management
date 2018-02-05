package com.ots.dpel.management.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.management.core.enums.ContributorType;

public class ContributorDto {
    
    private Long id;
    
    private Long electionProcedureId;
    
    private String type;
    
    private String lastName;
    
    private String firstName;
    
    private String fatherFirstName;
    
    private String telephone;
    
    private String cellphone;
    
    private String email;
    
    private String address;
    
    private String postalCode;
    
    private String comments;
    
    
    public ContributorDto() {
    }
    
    @QueryProjection
    public ContributorDto(Long id, Long electionProcedureId, ContributorType type, String lastName, String firstName, String fatherFirstName, String
            telephone, String cellphone, String email, String address, String postalCode, String comments) {
        this.id = id;
        this.electionProcedureId = electionProcedureId;
        this.type = type != null ? type.name() : null;
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherFirstName = fatherFirstName;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.comments = comments;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    public String getFatherFirstName() {
        return fatherFirstName;
    }
    
    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
}
