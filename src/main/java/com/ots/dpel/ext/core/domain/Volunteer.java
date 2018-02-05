package com.ots.dpel.ext.core.domain;

import com.ots.dpel.common.core.enums.YesNoEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vreg2_volunteers")
public class Volunteer {
    
    /*
     * Auto-generated ID
     */
    @Id
    @Column(name = "ID")
    private Long id;
    
    /*
     * Unique ID
     */
    @Column(name = "UID")
    private String uid;
    
    /*
     * Μοναδικός κωδικός του συστήματος καταχώρησης εθελοντών (γνωστοποιείται στους εθελοντές)
     */
    @Column(name = "ID_Number")
    private Long publicIdentifier;
    
    /*
     * Επώνυμο εθελοντή
     */
    @Column(name = "LastName")
    private String lastName;
    
    /*
     * Όνομα εθελοντή
     */
    @Column(name = "FirstName")
    private String firstName;
    
    /*
     * Πατρώνυμο εθελοντή
     */
    @Column(name = "FathersName")
    private String fatherFirstName;
    
    /*
     * Μητρώνυμο εθελοντή
     */
    @Column(name = "MothersName")
    private String motherFirstName;
    
    /*
     * Έτος γέννησης
     */
    @Column(name = "BirthYear")
    private Integer birthYear;
    
    /*
     * Διεύθυνση ηλ. αλληλογραφίας
     */
    @Column(name = "Email")
    private String email;
    
    /*
     * Αριθμός σταθερού τηλεφώνου
     */
    @Column(name = "FixedPhone")
    private String telephone;
    
    /*
     * Αριθμός κινητού
     */
    @Column(name = "Mobile")
    private String cellphone;
    
    /*
     * Ειδικός Εκλογικός Αριθμός
     */
    @Column(name = "EidEklAr")
    private String eklSpecialNo;
    
    /*
     * Οδός
     */
    @Column(name = "StreetName")
    private String addressStreet;

    /*
     * Αριθμός διεύθυνσης
     */
    @Column(name = "StreetNumber")
    private String addressNumber;

    /*
     * Τ.Κ.
     */
    @Column(name = "Zip")
    private String postalCode;

    /*
     * Περιοχή
     */
    @Column(name = "Area")
    private String area;

    /*
     * Δήμος
     */
    @Column(name = "MunicipalityName")
    private String municipality;

    /*
     * Νομός
     */
    @Column(name = "Division")
    private String division;
    
    /*
     * Ένδειξη διεύθυνσης χωρίς αριθμό
     */
    @Column(name = "NoNumbersAddress")
    private YesNoEnum notNumberedAddress;
    
    /*
     * Επάγγελμα
     */
    @Column(name = "Job")
    private String job;
    
    /*
     * Ικανότητες
     */
    @Column(name = "MiscSkills")
    private String miscellaneousSkills;
    
    /*
     * Ημερομηνία και ώρα εγγραφής
     */
    @Column(name = "RegDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public YesNoEnum getNotNumberedAddress() {
        return notNumberedAddress;
    }

    public void setNotNumberedAddress(YesNoEnum notNumberedAddress) {
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
