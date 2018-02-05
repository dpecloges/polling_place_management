package com.ots.dpel.ep.persistence;

import com.ots.dpel.ep.core.domain.Elector;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectorRepository extends DpQueryDslJpaRepository<Elector, Long> {
}
