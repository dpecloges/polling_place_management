package com.ots.dpel.ext.persistence;

import com.ots.dpel.ext.core.domain.Preregistration;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreregistrationRepository extends DpQueryDslJpaRepository<Preregistration, Long> {
}
