package com.ots.dpel.ext.services;

import com.ots.dpel.ext.dto.PreregistrationDto;

public interface PreregistrationService {
    
    PreregistrationDto findByEklSpecialNo(Long eklSpecialNo);
}
