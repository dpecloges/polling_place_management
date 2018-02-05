package com.ots.dpel.management.dto.list;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class ElectionCenterListDto {
    
    private Long id;
    
    private String code;
    
    private String name;
    
    private String foreign;
    
    private String foreignCountryName;
    
    private String foreignCity;
    
    private String regionName;
    
    private String regionalUnitName;
    
    private String municipalityName;
    
    private String municipalUnitName;
    
    private String address;
    
    private String postalCode;
    
    private String telephone;
    
    private Integer estimatedBallotBoxes;
    
    private Integer ballotBoxes;
    
    private String supervisorFullName;
    
    private String supervisorEmail;
    
    private String supervisorCellphone;
    
    private String supervisorAddress;
    
    private String supervisorPostalCode;
    
    public ElectionCenterListDto() {
    }
    
    @QueryProjection
    public ElectionCenterListDto(
            Long id,
            String code,
            String name,
            YesNoEnum foreign,
            String foreignCountryName,
            String foreignCity,
            String regionName,
            String regionalUnitName,
            String municipalityName,
            String municipalUnitName,
            String address,
            String postalCode,
            String telephone,
            Integer estimatedBallotBoxes,
            Integer ballotBoxes,
            String supervisorFullName,
            String supervisorEmail,
            String supervisorCellphone,
            String supervisorAddress,
            String supervisorPostalCode
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.foreign = foreign != null && foreign.equals(YesNoEnum.YES) ? "ΝΑΙ" : "ΟΧΙ";
        this.foreignCountryName = foreignCountryName;
        this.foreignCity = foreignCity;
        this.regionName = regionName;
        this.regionalUnitName = regionalUnitName;
        this.municipalityName = municipalityName;
        this.municipalUnitName = municipalUnitName;
        this.address = address;
        this.postalCode = postalCode;
        this.telephone = telephone;
        this.estimatedBallotBoxes = estimatedBallotBoxes;
        this.ballotBoxes = ballotBoxes;
        this.supervisorFullName = supervisorFullName;
        this.supervisorEmail = supervisorEmail;
        this.supervisorCellphone = supervisorCellphone;
        this.supervisorAddress = supervisorAddress;
        this.supervisorPostalCode = supervisorPostalCode;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getForeign() {
        return foreign;
    }
    
    public void setForeign(String foreign) {
        this.foreign = foreign;
    }
    
    public String getForeignCountryName() {
        return foreignCountryName;
    }
    
    public void setForeignCountryName(String foreignCountryName) {
        this.foreignCountryName = foreignCountryName;
    }
    
    public String getForeignCity() {
        return foreignCity;
    }
    
    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
    
    public String getRegionName() {
        return regionName;
    }
    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    public String getRegionalUnitName() {
        return regionalUnitName;
    }
    
    public void setRegionalUnitName(String regionalUnitName) {
        this.regionalUnitName = regionalUnitName;
    }
    
    public String getMunicipalityName() {
        return municipalityName;
    }
    
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }
    
    public String getMunicipalUnitName() {
        return municipalUnitName;
    }
    
    public void setMunicipalUnitName(String municipalUnitName) {
        this.municipalUnitName = municipalUnitName;
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
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Integer getEstimatedBallotBoxes() {
        return estimatedBallotBoxes;
    }
    
    public void setEstimatedBallotBoxes(Integer estimatedBallotBoxes) {
        this.estimatedBallotBoxes = estimatedBallotBoxes;
    }
    
    public Integer getBallotBoxes() {
        return ballotBoxes;
    }
    
    public void setBallotBoxes(Integer ballotBoxes) {
        this.ballotBoxes = ballotBoxes;
    }
    
    public String getSupervisorFullName() {
        return supervisorFullName;
    }
    
    public void setSupervisorFullName(String supervisorFullName) {
        this.supervisorFullName = supervisorFullName;
    }
    
    public String getSupervisorEmail() {
        return supervisorEmail;
    }
    
    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }
    
    public String getSupervisorCellphone() {
        return supervisorCellphone;
    }
    
    public void setSupervisorCellphone(String supervisorCellphone) {
        this.supervisorCellphone = supervisorCellphone;
    }
    
    public String getSupervisorAddress() {
        return supervisorAddress;
    }
    
    public void setSupervisorAddress(String supervisorAddress) {
        this.supervisorAddress = supervisorAddress;
    }
    
    public String getSupervisorPostalCode() {
        return supervisorPostalCode;
    }
    
    public void setSupervisorPostalCode(String supervisorPostalCode) {
        this.supervisorPostalCode = supervisorPostalCode;
    }
}
