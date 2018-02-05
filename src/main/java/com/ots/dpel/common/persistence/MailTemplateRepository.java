package com.ots.dpel.common.persistence;

import org.springframework.stereotype.Repository;

import com.ots.dpel.common.core.domain.MailTemplate;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;

@Repository
public interface MailTemplateRepository extends DpQueryDslJpaRepository<MailTemplate, Long> {
    
    MailTemplate findByCode(String code);
}
