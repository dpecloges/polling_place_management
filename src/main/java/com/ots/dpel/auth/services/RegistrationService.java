package com.ots.dpel.auth.services;

import com.ots.dpel.auth.dto.RegisterDto;
import com.ots.dpel.auth.dto.UserRegistrationDto;

public interface RegistrationService {
    
    UserRegistrationDto findUserRegistrationByIdentifier(String identifier);
    
    void registerUser(RegisterDto registerDto);
}
