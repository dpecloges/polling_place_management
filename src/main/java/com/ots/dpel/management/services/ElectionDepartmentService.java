package com.ots.dpel.management.services;

import com.mysema.query.types.Expression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.domain.QAdminUnit;
import com.ots.dpel.management.args.ElectionDepartmentArgs;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.ElectionDepartmentDto;
import com.ots.dpel.management.dto.ElectionDepartmentInfoDto;
import com.ots.dpel.management.dto.ElectionDepartmentResultDto;
import com.ots.dpel.management.dto.ElectionDepartmentSnapshotDto;
import com.ots.dpel.management.dto.list.ElectionDepartmentIndexListDto;
import com.ots.dpel.management.dto.list.ElectionDepartmentListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ElectionDepartmentService {
    
    ElectionDepartmentDto findElectionDepartment(Long id);

    Expression getElectionDepartmentBasicDtoConstructorExpression(QElectionDepartment electionDepartment, QElectionCenter electionCenter, QAdminUnit municipality);
    
    ElectionDepartmentDto saveElectionDepartment(ElectionDepartmentDto electionDepartmentDto);

    void deleteElectionDepartment(Long id);
    
    Page<ElectionDepartmentListDto> getIndex(SearchableArguments arguments, Pageable pageable);
    
    List<Long> findIdsByElectionCenter(Long electionCenterId);
    
    List<Long> findAllIds();
    
    List<Long> findUnsubmittedIds();
    
    ElectionDepartmentDto generateSerialNoAndCode(Long electionCenterId);
    
    ElectionDepartmentInfoDto getElectionDepartmentInfo(Long id);
    
    List<ElectionDepartmentBasicDto> findAllBasic();
    
    List<ElectionDepartmentListDto> getByElectionCenter(Long electionCenterId);
    
    Page<ElectionDepartmentIndexListDto> getElectionDepartmentIndex(SearchableArguments arguments, Pageable pageable);
    
    List<ElectionDepartmentResultDto> getAllElectionDepartmentResults();
    
    Integer getVerificationSerial(Long id, ElectionRound electionRound);
    
    void incrementVerificationSerial(Long id, ElectionRound electionRound);
    
    Boolean hasSubmitted(Long id, ElectionRound electionRound);
    
    Boolean allowsInconsistentSubmission(Long id);
    
    Page<ElectionDepartmentIndexListDto> getElectionDepartmentResultsIndex(ElectionDepartmentArgs args, Pageable pageable);
    
    List<ElectionDepartmentSnapshotDto> getAllElectionDepartmentSnapshots();

}
