package com.ots.dpel.common.services;

import java.util.List;

import com.mysema.query.types.Expression;
import com.ots.dpel.common.core.domain.Country;
import com.ots.dpel.common.core.domain.QCountry;
import com.ots.dpel.common.core.dto.CountryDto;

public interface CountryService {
    
    CountryDto findByIsoCode(String isoCode);
    
    List<CountryDto> findAll();
    
    Expression<CountryDto> getCountryDtoConstructorExpression(QCountry country);
    
    void dtoToEntity(CountryDto countryDto, Country country);
}
