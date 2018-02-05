package com.ots.dpel.common.persistence;

import org.springframework.stereotype.Repository;

import com.ots.dpel.common.core.domain.Country;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;

@Repository
public interface CountryRepository extends DpQueryDslJpaRepository<Country, String> {
    
    Country findByIsoCode(String isoCode);
    
}
