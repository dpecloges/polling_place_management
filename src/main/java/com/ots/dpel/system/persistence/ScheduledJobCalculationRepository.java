package com.ots.dpel.system.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.system.core.domain.ScheduledJobCalculation;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledJobCalculationRepository extends DpQueryDslJpaRepository<ScheduledJobCalculation, Long> {
}
