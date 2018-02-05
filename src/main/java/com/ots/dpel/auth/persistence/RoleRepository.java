package com.ots.dpel.auth.persistence;

import com.ots.dpel.auth.core.domain.Role;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends DpQueryDslJpaRepository<Role, Long> {
    
    List<Role> findByCode(String code);
}
