package com.ots.dpel.common.core.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

public class CountryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isoCode;

    private String name;

    public CountryDto() {

    }
    
    @QueryProjection
    public CountryDto(String isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
