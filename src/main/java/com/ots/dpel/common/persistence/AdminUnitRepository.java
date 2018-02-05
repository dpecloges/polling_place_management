package com.ots.dpel.common.persistence;

import com.ots.dpel.common.core.domain.AdminUnit;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUnitRepository extends DpQueryDslJpaRepository<AdminUnit, Long> {
}
