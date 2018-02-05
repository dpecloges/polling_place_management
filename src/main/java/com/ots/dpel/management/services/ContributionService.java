package com.ots.dpel.management.services;

import java.util.List;
import java.util.Map;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ContributionDto;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.list.ContributionListDto;

public interface ContributionService {
    
    List<ContributionDto> getContributionsByElectionDepartment(Long electionDepartmentId);
    
    List<ElectionDepartmentBasicDto> getVolunteerElectionDepartments(Long volunteerId, ElectionRound round, Long exceptElectionDepartmentId);
    
    void manageContributionsOfElectionDepartment(ElectionDepartment electionDepartment, List<ContributionDto> contributionDtos);
    
    void saveContribution(ContributionDto contributionDto);
    
    Map<Long, List<ContributionDto>> getElectionDepartmentIndexContributions();
    
    ContributionDto getContributionByIdentifier(String identifier);
    
    ContributionDto findContribution(Long contributionId);
    
    ContributionDto findContributionByVolunteerId(Long volunteerId);
    
    ContributionDto findPendingContribution(Long electionDepartmentId, Long contributionId);
    
    ContributionDto findNotifiedContribution(Long electionDepartmentId, Long contributionId);
    
    List<ContributionListDto> findAllContributions();
    
    List<ContributionListDto> findContributions(SearchableArguments arguments);
    
    List<ContributionDto> findPendingContributions(List<Long> contributionIds);
    
    List<ContributionDto> findAllPendingContributions();
    
    List<ContributionDto> findPendingContributionsByElectionDepartment(Long electionDepartmentId);
    
    List<ContributionDto> findNotifiedContributions(List<Long> contributionIds);
    
    List<ContributionDto> findAllNotifiedContributions();
    
    List<ContributionDto> findNotifiedContributionsByElectionDepartment(Long electionDepartmentId);
    
    void notifyPendingContribution(Long electionDepartmentId, Long contributionId);
    
    void notifyPendingContributionsByElectionDepartment(Long electionDepartmentId);
    
    void notifyPendingContributions(List<Long> contributionIds);
    
    void notifyAllPendingContributions();
    
    void renotifyPendingContribution(Long electionDepartmentId, Long contributionId);
    
    void renotifyPendingContributionsByElectionDepartment(Long electionDepartmentId);
    
    void renotifyPendingContributions(List<Long> contributionIds);
    
    void renotifyAllPendingContributions();
    
    void notifyVolunteer(Long volunteerId);
    
    void deleteContributionByVolunteerId(Long volunteerId);
    
    Boolean contributionWithVolunteerEmailExists(String email);
    
    void createCandidateRepresentativeContribution(Long volunteerId, Long electionDepartmentId, Long candidateId);
}
