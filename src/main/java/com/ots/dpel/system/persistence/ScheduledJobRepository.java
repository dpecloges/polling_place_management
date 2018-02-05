package com.ots.dpel.system.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.system.core.domain.ScheduledJob;
import org.springframework.stereotype.Repository;

/**
 * Repository για το Entity των Χρονοπρογραμματισμένων Εργασιών
 */
@Repository
public interface ScheduledJobRepository extends DpQueryDslJpaRepository<ScheduledJob, Long> {
}
