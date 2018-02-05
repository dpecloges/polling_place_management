package com.ots.dpel.ep.dto.indexing;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.system.services.IndexingService;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Score;

@SolrDocument(solrCoreName = IndexingService.ELECTORS_INDEX)
public class ElectorIndexedDocument {
    
    public static final String FIELD_ID = "id";
    public static final String FIELD_EKL_SPECIAL_NO = "eklSpecialNo";
    public static final String FIELD_LAST_NAME_PLAIN = "lastNamePlain";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_FATHER_FIRST_NAME = "fatherFirstName";
    public static final String FIELD_MOTHER_FIRST_NAME = "motherFirstName";
    public static final String FIELD_BIRTH_YEAR = "birthYear";
    public static final String FIELD_BIRTH_MONTH = "birthMonth";
    public static final String FIELD_BIRTH_DAY = "birthDay";
    public static final String FIELD_MUNICIPAL_UNIT_DESCRIPTION = "municipalUnitDescription";
    public static final String FIELD_ELECTION_DEPARTMENT_DESCRIPTION = "electionDepartmentDescription";
    public static final String FIELD_MUNICIPAL_RECORD_NO = "municipalRecordNo";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_ADDRESS_NO = "addressNo";
    public static final String FIELD_POSTAL_CODE = "postalCode";
    public static final String FIELD_CITY = "city";
    
    @Id
    @Field
    private String id;
    
    @Score
    private Float score;
    
    @Field
    private String eklSpecialNo;
    
    @Field
    private String lastNamePlain;
    
    @Field
    private String lastName;
    
    @Field
    private String firstName;
    
    @Field
    private String fatherFirstName;
    
    @Field
    private String motherFirstName;
    
    @Field
    private Integer birthYear;
    
    @Field
    private Integer birthMonth;
    
    @Field
    private Integer birthDay;
    
    @Field
    private String municipalUnitDescription;
    
    @Field
    private String electionDepartmentDescription;
    
    @Field
    private String municipalRecordNo;
    
    @Field
    private String address;
    
    @Field
    private String addressNo;
    
    @Field
    private String postalCode;
    
    @Field
    private String city;
    
    public ElectorIndexedDocument() {
    
    }
    
    @QueryProjection
    public ElectorIndexedDocument(Long id) {
        this.id = String.valueOf(id);
    }
    
    @QueryProjection
    public ElectorIndexedDocument(Long id, String eklSpecialNo, String lastNamePlain, String lastName, String firstName, String fatherFirstName, String
            motherFirstName, Integer birthYear, Integer birthMonth, Integer birthDay, String municipalUnitDescription, String
            electionDepartmentDescription, String
                                          municipalRecordNo, String address, String addressNo, String postalCode, String city) {
        this.id = String.valueOf(id);
        this.eklSpecialNo = eklSpecialNo;
        this.lastNamePlain = lastNamePlain;
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherFirstName = fatherFirstName;
        this.motherFirstName = motherFirstName;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.municipalUnitDescription = municipalUnitDescription;
        this.electionDepartmentDescription = electionDepartmentDescription;
        this.municipalRecordNo = municipalRecordNo;
        this.address = address;
        this.addressNo = addressNo;
        this.postalCode = postalCode;
        this.city = city;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Float getScore() {
        return score;
    }
    
    public void setScore(Float score) {
        this.score = score;
    }
    
    public String getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
    }
    
    public String getLastNamePlain() {
        return lastNamePlain;
    }
    
    public void setLastNamePlain(String lastNamePlain) {
        this.lastNamePlain = lastNamePlain;
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
}
