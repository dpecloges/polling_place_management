package com.ots.dpel.management.services;

import com.ots.dpel.management.dto.list.CandidateListDto;

import java.util.List;

public interface CandidateService {
    
    List<CandidateListDto> getAll();
    
    List<CandidateListDto> getByCurrentElectionProcedure();
    
    Short getOrder(Long id);
}
