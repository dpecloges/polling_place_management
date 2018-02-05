package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile(value = "testing-dev")
@PropertySource({
        "properties/testing-dev/jdbc-dev.properties",
        "properties/testing-dev/hibernate.properties",
        "properties/testing-dev/api.properties",
        "properties/testing-dev/security.properties",
        "properties/testing-dev/cache.properties",
        "properties/testing-dev/log.properties",
        "properties/testing-dev/solr.properties",
        "properties/testing-dev/mail.properties",
        "properties/testing-dev/web.properties"
})
public class TestingDevConfiguration {
    
    /**
     * Ensures that placeholders are replaced with property values.
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    /**
     * Scheduler definition properties file
     * @return
     */
    @Bean(name = "quartzProperties")
    static ClassPathResource quartzPropertiesClassPathResource() {
        return new ClassPathResource("properties/testing-dev/quartz.properties");
    }
}
