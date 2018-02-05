package com.ots.dpel.ep.services;

import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.dto.ElectionDepartmentVoterDto;
import com.ots.dpel.ep.dto.VoterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface VoterService {
    
    VoterDto findVoter(Long id);
    
    VoterDto findVotedByElectorIdAndCurrentElectionProcedureRound(Long electorId);
    
    VoterDto saveVoter(VoterDto voterDto);
    
    void undoVote(Long id, String undoReason);
    
    Long getVoterCountByElectionDepartmentId(Long electionDepartmentId, YesNoEnum voted);
    
    Map<Long, Integer> getAllElectionDepartmentVoterCount(Long electionProcedureId, String round, YesNoEnum voted);
    
    Page<VoterDto> findVotersFromUnsubmitted(YesNoEnum voted, Pageable pageable);
}
