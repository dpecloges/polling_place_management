package com.ots.dpel.ep.services;

import com.ots.dpel.ep.args.VerificationArgs;
import com.ots.dpel.ep.dto.VerificationDto;
import com.ots.dpel.ep.dto.VoterDto;

public interface VerificationService {
    
    VerificationDto verify(VerificationArgs args);
    
    VoterDto saveVoter(VoterDto voterDto);
}
