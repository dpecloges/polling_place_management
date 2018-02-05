package com.ots.dpel.results.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.results.core.domain.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends DpQueryDslJpaRepository<Result, Long> {
}
