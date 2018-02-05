package com.ots.dpel.management.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.management.core.domain.ElectionCenter;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionCenterRepository extends DpQueryDslJpaRepository<ElectionCenter, Long> {
}
