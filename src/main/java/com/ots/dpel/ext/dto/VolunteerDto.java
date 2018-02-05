package com.ots.dpel.ext.dto;

import java.util.Date;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class VolunteerDto {

    private Long id;
    private Long publicIdentifier;
    private String lastName;
    private String firstName;
    private String fatherFirstName;
    private String motherFirstName;
    private Integer birthYear;
    private String email;
    private String telephone;
    private String cellphone;
    private String eklSpecialNo;
    private String addressStreet;
    private String addressNumber;
    private String postalCode;
    private String area;
    private String municipality;
    private String division;
    private Boolean notNumberedAddress;
    private String job;
    private String miscellaneousSkills;
    private Date registrationTimestamp;
    
    public VolunteerDto() {
        
    }

    @QueryProjection
    public VolunteerDto(Long id, Long publicIdentifier, String lastName, String firstName, String fatherFirstName,
            String motherFirstName, Integer birthYear, String email, String telephone, String cellphone,
            String eklSpecialNo, String addressStreet, String addressNumber, String postalCode, String area,
            String municipality, String division, YesNoEnum notNumberedAddress, String job, String miscellaneousSkills,
            Date registrationTimestamp) {
        this.id = id;
        this.publicIdentifier = publicIdentifier;
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherFirstName = fatherFirstName;
        this.motherFirstName = motherFirstName;
        this.birthYear = birthYear;
        this.email = email;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.eklSpecialNo = eklSpecialNo;
        this.addressStreet = addressStreet;
        this.addressNumber = addressNumber;
        this.postalCode = postalCode;
        this.area = area;
        this.municipality = municipality;
        this.division = division;
        this.notNumberedAddress = notNumberedAddress != null && notNumberedAddress.equals(YesNoEnum.YES);
        this.job = job;
        this.miscellaneousSkills = miscellaneousSkills;
        this.registrationTimestamp = registrationTimestamp;
    }
    
    @QueryProjection
    public VolunteerDto(Long id, Long publicIdentifier, String eklSpecialNo,
            String lastName, String firstName, String email) {
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

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public String getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
    }
    
    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Boolean getNotNumberedAddress() {
        return notNumberedAddress;
    }

    public void setNotNumberedAddress(Boolean notNumberedAddress) {
        this.notNumberedAddress = notNumberedAddress;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMiscellaneousSkills() {
        return miscellaneousSkills;
    }

    public void setMiscellaneousSkills(String miscellaneousSkills) {
        this.miscellaneousSkills = miscellaneousSkills;
    }

    public Date getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(Date registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }
}
