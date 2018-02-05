package com.ots.dpel.ext.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.dto.list.VolunteerListDto;

public interface VolunteerService {

    VolunteerDto findVolunteer(Long id);
    
    VolunteerDto findVolunteerBasic(Long id);
    
    List<VolunteerDto> findVolunteers(List<Long> ids);
    
    List<VolunteerDto> findVolunteersBasic(List<Long> ids);
    
    List<VolunteerDto> findAllVolunteers();
    
    List<VolunteerDto> findAllVolunteersBasic();
    
    List<VolunteerDto> findVolunteersBasic(SearchableArguments arguments);
    
    Page<VolunteerListDto> getVolunteerIndex(SearchableArguments arguments, Pageable pageable);
    
    Long getVolunteerIdByEmail(String email);
}
