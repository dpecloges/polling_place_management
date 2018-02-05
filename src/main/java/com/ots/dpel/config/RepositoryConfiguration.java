package com.ots.dpel.config;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.ots.dpel.auth.persistence",
        "com.ots.dpel.management.persistence",
        "com.ots.dpel.ep.persistence",
        "com.ots.dpel.results.persistence",
        "com.ots.dpel.common.persistence",
        "com.ots.dpel.system.persistence"
    },
    repositoryBaseClass = DpQueryDslJpaRepositoryImpl.class)
public class RepositoryConfiguration {

}