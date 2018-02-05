package com.ots.dpel.management.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionDepartmentRepository extends DpQueryDslJpaRepository<ElectionDepartment, Long> {
}
