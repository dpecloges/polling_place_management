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
    entityManagerFactoryRef = "externalEntityManagerFactory",
    transactionManagerRef = "externalTransactionManager",
    basePackages = {
    "com.ots.dpel.ext.services.impl"
})
@EnableTransactionManagement
public class ExternalJpaConfiguration {
    
    @Autowired
    private Environment env;
    
    @Autowired
    @Qualifier("externalDataSource")
    private DataSource dataSource;
    
    @Bean
    public LocalContainerEntityManagerFactoryBean externalEntityManagerFactory() {
        HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
        
        hibernateJpa.setDatabasePlatform(env.getProperty("ext.hibernate.dialect"));
        hibernateJpa.setShowSql(env.getProperty("ext.hibernate.show_sql", Boolean.class));
        
        Map<String, String> jpaProperties = new HashMap<String, String>();
        
        jpaProperties.put("javax.persistence.validation.mode", "none");
        jpaProperties.put("hibernate.generate_statistics", env.getProperty("ext.hibernate.generate_statistics"));
        jpaProperties.put("hibernate.id.new_generator_mappings", env.getProperty("ext.hibernate.id.new_generator_mappings"));
        // jpaProperties.put("org.hibernate.envers.audit_table_suffix", env.getProperty("org.hibernate.envers.audit_table_suffix"));
        // jpaProperties.put("org.hibernate.envers.revision_field_name", env.getProperty("org.hibernate.envers.revision_field_name"));
        // jpaProperties.put("org.hibernate.envers.revision_type_field_name", env.getProperty("org.hibernate.envers.revision_type_field_name"));
        // jpaProperties.put("org.hibernate.envers.store_data_at_delete", env.getProperty("org.hibernate.envers.store_data_at_delete"));
        
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        
        // TODO - Dto? packages
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.ots.dpel.ext.core.domain");
        emf.setJpaVendorAdapter(hibernateJpa);
        emf.setJpaPropertyMap(jpaProperties);
        
        return emf;
    }
    
    @Bean(name = {"externalTxMgr", "externalTransactionManager"})
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txnMgr = new JpaTransactionManager();
        txnMgr.setEntityManagerFactory(externalEntityManagerFactory().getObject());
        return txnMgr;
    }
}
