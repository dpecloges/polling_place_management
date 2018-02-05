package com.ots.dpel.ep.services;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.ep.args.SnapshotArgs;
import com.ots.dpel.ep.dto.SnapshotsDto;
import com.ots.dpel.system.dto.ScheduleJobCronDTO;
import com.ots.dpel.system.dto.ScheduledJobTriggerDTO;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SnapshotService {
    
    void calculateSnapshots() throws SchedulerException;
    
    SnapshotsDto searchSnapshot(SnapshotArgs args);
    
    List<SnapshotsDto> findAllSnapshots(SnapshotArgs args);
    
    void scheduleCalculateSnapshotsJob(ScheduleJobCronDTO scheduleJobCronDTO) throws SchedulerException;
    
    void unscheduleCalculateSnapshotsJob() throws SchedulerException;
    
    ScheduledJobTriggerDTO getCalculateSnapshotsJobStatus() throws SchedulerException;
    
    Page<SnapshotsDto> getStatisticsIndex(SearchableArguments arguments, Pageable pageable);
}
