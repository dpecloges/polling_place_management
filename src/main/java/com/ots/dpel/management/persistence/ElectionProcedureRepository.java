package com.ots.dpel.management.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.management.core.domain.ElectionProcedure;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionProcedureRepository extends DpQueryDslJpaRepository<ElectionProcedure, Long> {
}
