package com.ots.dpel.management.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.management.core.domain.Candidate;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends DpQueryDslJpaRepository<Candidate, Long> {
}
