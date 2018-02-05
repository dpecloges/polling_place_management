package com.ots.dpel.us.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.us.dto.ManualUserCreationRequest;
import com.ots.dpel.us.dto.VolunteerAssignmentRequestDto;
import com.ots.dpel.us.dto.VolunteerReassignmentRequestDto;
import com.ots.dpel.us.dto.list.UsVolunteerListDto;

public interface UsVolunteerService {

    Page<UsVolunteerListDto> getUsVolunteerIndex(SearchableArguments args, Pageable pageable);
    
    void assignVolunteer(VolunteerAssignmentRequestDto assignment);
    
    void unassignVolunteer(Long volunteerId);
    
    void notifyVolunteer(Long volunteerId);
    
    void notifyAll(boolean pending, boolean notified);
    
    void manuallyCreateUser(ManualUserCreationRequest userCreation);
    
    void reassignVolunteer(VolunteerReassignmentRequestDto reassignment);
}
