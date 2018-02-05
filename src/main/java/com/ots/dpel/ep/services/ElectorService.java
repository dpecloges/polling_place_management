package com.ots.dpel.ep.services;

import com.ots.dpel.ep.args.VerificationArgs;
import com.ots.dpel.ep.dto.ElectorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElectorService {
    
    Page<ElectorDto> findMatchingElectors(VerificationArgs args, Pageable pageable);
    
    Page<ElectorDto> findMatchingElectorsFromSolr(VerificationArgs args, Pageable pageable);
    
    ElectorDto findElectorBasic(Long id);
    
    ElectorDto findElectorBasicFromSolr(Long id);
    
    Boolean electorsIndexIsOnline();
    
    Page<ElectorDto> findGenericElectors(Pageable pageable);
    
    Page<ElectorDto> findGenericElectorsFromSolr(Pageable pageable);
}
