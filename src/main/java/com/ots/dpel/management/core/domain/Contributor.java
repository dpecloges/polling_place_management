package com.ots.dpel.management.core.domain;

import com.ots.dpel.management.core.enums.ContributorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contributor", schema = "dp")
public class Contributor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "n_electionprocedure_id")
    private Long electionProcedureId;
    
    @Column(name = "v_type")
    @Enumerated(EnumType.STRING)
    private ContributorType type;
    
    @Column(name = "v_lastname")
    private String lastName;
    
    @Column(name = "v_firstname")
    private String firstName;
    
    @Column(name = "v_fatherfirstname")
    private String fatherFirstName;
    
    @Column(name = "v_telephone")
    private String telephone;
    
    @Column(name = "v_cellphone")
    private String cellphone;
    
    @Column(name = "v_email")
    private String email;
    
    @Column(name = "v_address")
    private String address;
    
    @Column(name = "v_postalcode")
    private String postalCode;
    
    @Column(name = "v_comments")
    private String comments;
    
    
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
    
    public ContributorType getType() {
        return type;
    }
    
    public void setType(ContributorType type) {
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
