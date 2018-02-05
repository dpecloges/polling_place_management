package com.ots.dpel.management.persistence;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.management.core.domain.Contributor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends DpQueryDslJpaRepository<Contributor, Long> {
}
