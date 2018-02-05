package com.ots.dpel.management.services;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.management.dto.ElectionCenterBasicDto;
import com.ots.dpel.management.dto.ElectionCenterDto;
import com.ots.dpel.management.dto.list.ElectionCenterListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ElectionCenterService {
    
    Page<ElectionCenterListDto> getElectionCenterIndex(SearchableArguments arguments, Pageable pageable);
    
    List<ElectionCenterBasicDto> findAllBasic();
    
    ElectionCenterDto findElectionCenter(Long id);
    
    ElectionCenterDto saveElectionCenter(ElectionCenterDto electionCenterDto);
    
    void deleteElectionCenter(Long id);
    
    ElectionCenterDto findElectionCenterBasic(Long id);
    
    String getCode(Long id);
    
    List<String> getCitiesUsedInElectionCenters();
    
    List<Long> getElectionCenterIds();
    
    List<ElectionCenterBasicDto> getElectionCenterBasicForSnapshot();
    
    List<ElectionCenterBasicDto> getElectionCenterBasicByForeignCountry(String foreignCountryIsoCode);
    
    List<ElectionCenterBasicDto> getElectionCenterBasicByMunicipalityId(Long municipalityId);
}
