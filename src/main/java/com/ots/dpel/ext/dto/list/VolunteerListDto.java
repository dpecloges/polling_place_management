package com.ots.dpel.ext.dto.list;

import com.mysema.query.annotations.QueryProjection;

public class VolunteerListDto {


    private Long id;
    private Long publicIdentifier;
    private String eklSpecialNo;
    
    private String lastName;
    private String firstName;
    
    private String email;

    public VolunteerListDto() {
        
    }
    
    @QueryProjection
    public VolunteerListDto(Long id, Long publicIdentifier, String eklSpecialNo, String lastName, String firstName,
            String email) {
        super();
        this.id = id;
        this.publicIdentifier = publicIdentifier;
        this.eklSpecialNo = eklSpecialNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(Long publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }
    
    public String getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
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
}
