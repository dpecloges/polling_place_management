package com.ots.dpel.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = {
        "com.ots.dpel.auth.services.impl",
        "com.ots.dpel.management.services.impl",
        "com.ots.dpel.ep.services.impl",
        "com.ots.dpel.results.services.impl",
        "com.ots.dpel.us.services.impl",
        "com.ots.dpel.common.services.impl",
        "com.ots.dpel.system.services.impl"
})
@EnableTransactionManagement
public class JpaConfiguration {
    
    @Autowired
    private Environment env;
    
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
        
        hibernateJpa.setDatabasePlatform(env.getProperty("hibernate.dialect"));
        hibernateJpa.setShowSql(env.getProperty("hibernate.show_sql", Boolean.class));
        
        Map<String, String> jpaProperties = new HashMap<String, String>();
        
        jpaProperties.put("javax.persistence.validation.mode", "none");
        jpaProperties.put("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
        jpaProperties.put("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings"));
        jpaProperties.put("org.hibernate.envers.audit_table_suffix", env.getProperty("org.hibernate.envers.audit_table_suffix"));
        jpaProperties.put("org.hibernate.envers.revision_field_name", env.getProperty("org.hibernate.envers.revision_field_name"));
        jpaProperties.put("org.hibernate.envers.revision_type_field_name", env.getProperty("org.hibernate.envers.revision_type_field_name"));
        jpaProperties.put("org.hibernate.envers.store_data_at_delete", env.getProperty("org.hibernate.envers.store_data_at_delete"));
        
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        
        // TODO - Dto? packages
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(
            "com.ots.dpel.auth.core.domain",
            "com.ots.dpel.auth.core.converters",
            "com.ots.dpel.management.core.domain",
            "com.ots.dpel.management.core.converters",
            "com.ots.dpel.management.dto",
            "com.ots.dpel.ep.core.domain",
            "com.ots.dpel.ep.core.converters",
            "com.ots.dpel.ep.dto",
            "com.ots.dpel.results.core.domain",
            "com.ots.dpel.results.core.converters",
            "com.ots.dpel.common.core.domain",
            "com.ots.dpel.common.core.converters",
            "com.ots.dpel.system.core.domain",
            "com.ots.dpel.system.core.converters");
        emf.setJpaVendorAdapter(hibernateJpa);
        emf.setJpaPropertyMap(jpaProperties);
        
        return emf;
    }
    
    @Bean(name = {"txMgr", "transactionManager"})
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txnMgr = new JpaTransactionManager();
        txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
        return txnMgr;
    }
}
