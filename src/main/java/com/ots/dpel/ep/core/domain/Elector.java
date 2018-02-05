package com.ots.dpel.ep.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "elector", schema = "dp")
public class Elector {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    
    /**
     * Κωδικός ΟΤΑ
     */
    @Column(name = "Upd_prefix")
    private String municipalityCode;
    
    /**
     * Κωδικός Δημοτικής Ενότητας
     */
    @Column(name = "Kod_dhm_enot")
    private String municipalUnitCode;
    
    /**
     * Κωδ.Εκλ.Διαμ.
     */
    @Column(name = "Kod_ekl_diam")
    private String electionDepartmentCode;
    
    /**
     * Φύλο
     */
    @Column(name = "Fylo")
    private String gender;
    
    /**
     * Επώνυμο
     */
    @Column(name = "Eponymo")
    private String lastName;
    
    /**
     * Επώνυμο β'
     */
    @Column(name = "Eponymo_b")
    private String secondLastName;
    
    /**
     * Όνομα
     */
    @Column(name = "Onoma")
    private String firstName;
    
    /**
     * Όνομα β΄
     */
    @Column(name = "Onoma_b")
    private String secondName;
    
    /**
     * Όνομα πατέρα
     */
    @Column(name = "On_pat")
    private String fatherFirstName;
    
    /**
     * Επώνυμο πατέρα
     */
    @Column(name = "Epon_pat")
    private String fatherLastName;
    
    /**
     * Όνομα μητέρας
     */
    @Column(name = "On_mht")
    private String motherFirstName;
    
    /**
     * Όνομα συζύγου
     */
    @Column(name = "On_syz")
    private String spouseFirstName;
    
    /**
     * Έτος γέννησης
     */
    @Column(name = "Etos_gen")
    private Integer birthYear;
    
    /**
     * Μήνας γέννησης
     */
    @Column(name = "Mhn_gen")
    private Integer birthMonth;
    
    /**
     * Μέρα γέννησης
     */
    @Column(name = "Mer_gen")
    private Integer birthDay;
    
    /**
     * Αρ. Δημοτολογίου
     */
    @Column(name = "Dhmot")
    private String municipalRecordNo;
    
    /**
     * Οδός
     */
    @Column(name = "Odos_tax_dieyt")
    private String address;
    
    /**
     * Αριθμός
     */
    @Column(name = "Ar_tax_dieyt")
    private String addressNo;
    
    /**
     * Ταχ. Κώδικας
     */
    @Column(name = "Tax_kod")
    private String postalCode;
    
    /**
     * Πόλη-Περιοχή
     */
    @Column(name = "Poly_periox")
    private String city;
    
    /**
     * Ειδ. Εκλ. Αριθμός
     */
    @Column(name = "Eid_ekl_ar")
    private String eklSpecialNo;
    
    //Et_Id
    //Et_dhmos_diamon
    //Et_odos_tax_dieyt
    //Et_ar_tax_dieyt
    //Et_tax_kod
    //Et_poly_periox
    //Et_ked_diamon
    //Dipldiafdim_flag
    
    /**
     * Περιγραφή Δημοτικής Ενότητας
     */
    @Column(name = "Per_dhm_enot")
    private String municipalUnitDescription;
    
    /**
     * Περιγραφή Εκλ.Διαμ.
     */
    @Column(name = "Per_ekl_diam")
    private String electionDepartmentDescription;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMunicipalityCode() {
        return municipalityCode;
    }
    
    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }
    
    public String getMunicipalUnitCode() {
        return municipalUnitCode;
    }
    
    public void setMunicipalUnitCode(String municipalUnitCode) {
        this.municipalUnitCode = municipalUnitCode;
    }
    
    public String getElectionDepartmentCode() {
        return electionDepartmentCode;
    }
    
    public void setElectionDepartmentCode(String electionDepartmentCode) {
        this.electionDepartmentCode = electionDepartmentCode;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getSecondLastName() {
        return secondLastName;
    }
    
    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getSecondName() {
        return secondName;
    }
    
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public String getFatherFirstName() {
        return fatherFirstName;
    }
    
    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }
    
    public String getFatherLastName() {
        return fatherLastName;
    }
    
    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }
    
    public String getMotherFirstName() {
        return motherFirstName;
    }
    
    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }
    
    public String getSpouseFirstName() {
        return spouseFirstName;
    }
    
    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }
    
    public Integer getBirthYear() {
        return birthYear;
    }
    
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    
    public Integer getBirthMonth() {
        return birthMonth;
    }
    
    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }
    
    public Integer getBirthDay() {
        return birthDay;
    }
    
    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }
    
    public String getMunicipalRecordNo() {
        return municipalRecordNo;
    }
    
    public void setMunicipalRecordNo(String municipalRecordNo) {
        this.municipalRecordNo = municipalRecordNo;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddressNo() {
        return addressNo;
    }
    
    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
    }
    
    public String getMunicipalUnitDescription() {
        return municipalUnitDescription;
    }
    
    public void setMunicipalUnitDescription(String municipalUnitDescription) {
        this.municipalUnitDescription = municipalUnitDescription;
    }
    
    public String getElectionDepartmentDescription() {
        return electionDepartmentDescription;
    }
    
    public void setElectionDepartmentDescription(String electionDepartmentDescription) {
        this.electionDepartmentDescription = electionDepartmentDescription;
    }
}
