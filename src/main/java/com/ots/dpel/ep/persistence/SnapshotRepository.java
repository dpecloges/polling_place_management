package com.ots.dpel.ep.persistence;

import com.ots.dpel.ep.core.domain.Snapshot;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends DpQueryDslJpaRepository<Snapshot, Long> {
}
