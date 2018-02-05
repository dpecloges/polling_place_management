package com.ots.dpel.ep.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.global.utils.DpDateUtils;

import java.util.Date;

public class ElectorDto {
    
    private Long id;
    
    private String lastName;
    
    private String firstName;
    
    private String fatherFirstName;
    
    private String motherFirstName;
    
    private Date birthDate;
    
    private String municipalRecordNo;
    
    private String municipalUnitDescription;
    
    private String eklSpecialNo;
    
    
    public ElectorDto() {
    }
    
    @QueryProjection
    public ElectorDto(Long id, String lastName, String firstName, String fatherFirstName, String motherFirstName, Integer birthYear,
                      Integer birthMonth, Integer birthDay, String municipalRecordNo, String municipalUnitDescription, String eklSpecialNo) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherFirstName = fatherFirstName;
        this.motherFirstName = motherFirstName;
        this.birthDate = DpDateUtils.getDate(birthYear, birthMonth - 1, birthDay, 0, 0, 0);
        this.municipalRecordNo = municipalRecordNo;
        this.municipalUnitDescription = municipalUnitDescription;
        this.eklSpecialNo = eklSpecialNo;
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
    
    public String getFatherFirstName() {
        return fatherFirstName;
    }
    
    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }
    
    public String getMotherFirstName() {
        return motherFirstName;
    }
    
    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getMunicipalRecordNo() {
        return municipalRecordNo;
    }
    
    public void setMunicipalRecordNo(String municipalRecordNo) {
        this.municipalRecordNo = municipalRecordNo;
    }
    
    public String getMunicipalUnitDescription() {
        return municipalUnitDescription;
    }
    
    public void setMunicipalUnitDescription(String municipalUnitDescription) {
        this.municipalUnitDescription = municipalUnitDescription;
    }
    
    public String getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
    }
}
