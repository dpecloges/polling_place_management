package com.ots.dpel.config;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "externalEntityManagerFactory",
    transactionManagerRef = "externalTransactionManager",
    basePackages = {
            "com.ots.dpel.ext.persistence"
    },
    repositoryBaseClass = DpQueryDslJpaRepositoryImpl.class)
public class ExternalRepositoryConfiguration {

}
