package com.ots.dpel.ext.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.core.enums.IdType;

public class PreregistrationDto {
    
    private Long id;
    private Boolean notMember;
    private String address;
    private String addressNo;
    private String area;
    private String postalCode;
    private String country;
    private String cellphone;
    private String email;
    private String idType;
    private String idNumber;
    private Float payment;
    
    public PreregistrationDto() {
    
    }
    
    @QueryProjection
    public PreregistrationDto(Long id, YesNoEnum notMember, String address, String addressNo, String area, String postalCode,
                              String countryIsoCode, String cellphone, String email, Integer idType, String idNumber, Float paymentValue) {
        this.id = id;
        this.notMember = notMember != null && notMember.equals(YesNoEnum.YES);
        this.address = address;
        this.addressNo = addressNo;
        this.area = area;
        this.postalCode = postalCode;
        this.country = (countryIsoCode != null && countryIsoCode.equals("GR")) ? "ΕΛΛΑΔΑ" : countryIsoCode;
        this.cellphone = cellphone;
        this.email = email;
        this.idType = (idType == null) ? null : IdType.values()[idType-1].name();
        this.idNumber = idNumber;
        this.payment = (paymentValue == null) ? 0 : paymentValue / 100;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getNotMember() {
        return notMember;
    }
    
    public void setNotMember(Boolean notMember) {
        this.notMember = notMember;
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
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
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
    
    public String getIdType() {
        return idType;
    }
    
    public void setIdType(String idType) {
        this.idType = idType;
    }
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public Float getPayment() {
        return payment;
    }
    
    public void setPayment(Float payment) {
        this.payment = payment;
    }
}
