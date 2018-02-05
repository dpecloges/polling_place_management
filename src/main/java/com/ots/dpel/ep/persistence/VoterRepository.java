package com.ots.dpel.ep.persistence;

import com.ots.dpel.ep.core.domain.Voter;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends DpQueryDslJpaRepository<Voter, Long> {
}
