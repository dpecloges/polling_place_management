package com.ots.dpel.ext.persistence;

import org.springframework.stereotype.Repository;

import com.ots.dpel.ext.core.domain.Volunteer;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;

@Repository
public interface VolunteerRepository extends DpQueryDslJpaRepository<Volunteer, Long> {

}
