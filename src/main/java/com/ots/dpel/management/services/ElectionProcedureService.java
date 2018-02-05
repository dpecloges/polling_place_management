package com.ots.dpel.management.services;

import com.ots.dpel.management.dto.ElectionProcedureDto;

import java.util.Date;

public interface ElectionProcedureService {
    
    ElectionProcedureDto getCurrent();
    
    void validateCurrent(ElectionProcedureDto electionProcedureDto);
    
    void updateResultsLastCalcDateTimeForCurrent(Date resultsLastCalcDateTime);
    
    void updateSnapshotLastCalcDateTimeForCurrent(Date snapshotLastCalcDateTime);
}
