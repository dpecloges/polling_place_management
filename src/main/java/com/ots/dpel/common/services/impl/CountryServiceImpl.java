package com.ots.dpel.common.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.ots.dpel.system.services.CacheService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.ots.dpel.common.core.domain.Country;
import com.ots.dpel.common.core.domain.QCountry;
import com.ots.dpel.common.core.dto.CountryDto;
import com.ots.dpel.common.persistence.CountryRepository;
import com.ots.dpel.common.services.CountryService;
import com.ots.dpel.global.utils.DpTextUtils;

@Service
public class CountryServiceImpl implements CountryService {
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Cacheable(value = CacheService.COUNTRIES_CACHE)
    public CountryDto findByIsoCode(String isoCode) {
        return entityToDto(countryRepository.findByIsoCode(isoCode));
    }

    @Override
    @Cacheable(value = CacheService.COUNTRIES_CACHE)
    public List<CountryDto> findAll() {
        
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.ASC, "name"); 
        
        Page<Country> page = countryRepository.findAll(pageable);
        List<Country> countries = page.getContent();
        
        return entityListToDtoList(countries);
    }
    
    @Override
    public Expression<CountryDto> getCountryDtoConstructorExpression(QCountry country) {
        return ConstructorExpression.create(CountryDto.class, country.isoCode, country.name);
    }

    private CountryDto entityToDto(Country country) {
        if (country == null) {
            return null;
        }
        
        CountryDto countryDto = new CountryDto();
        BeanUtils.copyProperties(country, countryDto);
        
        return countryDto;
    }
    
    @Override
    public void dtoToEntity(CountryDto countryDto, Country country) {
        
        if (countryDto == null || country == null) {
            return;
        }
        
        String isoCode = countryDto.getIsoCode();
        if (DpTextUtils.isEmpty(isoCode)) {
            return;
        }
        
        country.setIsoCode(isoCode);
        
        if (DpTextUtils.isEmpty(countryDto.getName())) {
            Country storedCountry = entityManager.find(Country.class, isoCode);
            if (storedCountry != null) {
                country.setName(storedCountry.getName());
            }
        } else {
            country.setName(countryDto.getName());
        }
    }

    private List<CountryDto> entityListToDtoList(List<Country> countries) {
        List<CountryDto> countryDtos = new ArrayList<>();
        
        if (CollectionUtils.isNotEmpty(countries)) {
            for (Country country: countries) {
                countryDtos.add(entityToDto(country));
            }
        }
        
        return countryDtos;
    }
}
