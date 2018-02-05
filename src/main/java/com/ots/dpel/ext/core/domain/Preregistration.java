package com.ots.dpel.ext.core.domain;

import com.ots.dpel.common.core.enums.YesNoEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "voter_registration")
public class Preregistration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "IsFriend")
    private YesNoEnum notMember;
    
    @Column(name = "EidEklAr")
    private Long eklSpecialNo;
    
    @Column(name = "Street")
    private String address;
    
    @Column(name = "StreetNo")
    private String addressNo;
    
    @Column(name = "Area")
    private String area;
    
    @Column(name = "Zip")
    private String postalCode;
    
    @Column(name = "CountryISO")
    private String countryIsoCode;
    
    @Column(name = "MobilePhone")
    private String cellphone;
    
    @Column(name = "EMail")
    private String email;
    
    @Column(name = "IDDocumentType")
    private Integer idType;
    
    @Column(name = "IDDocumentNumber")
    private String idNumber;
    
    @Column(name = "PaymentValue")
    private Float paymentValue;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public YesNoEnum getNotMember() {
        return notMember;
    }
    
    public void setNotMember(YesNoEnum notMember) {
        this.notMember = notMember;
    }
    
    public Long getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(Long eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
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
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountryIsoCode() {
        return countryIsoCode;
    }
    
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
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
    
    public Integer getIdType() {
        return idType;
    }
    
    public void setIdType(Integer idType) {
        this.idType = idType;
    }
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public Float getPaymentValue() {
        return paymentValue;
    }
    
    public void setPaymentValue(Float paymentValue) {
        this.paymentValue = paymentValue;
    }
}
