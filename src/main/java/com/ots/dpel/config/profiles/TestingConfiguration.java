package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile(value = "testing")
@PropertySource({
        "classpath://properties/testing/jdbc-dev.properties",
        "classpath://properties/testing/hibernate.properties",
        "classpath://properties/testing/api.properties",
        "classpath://properties/testing/security.properties",
        "classpath://properties/testing/cache.properties",
        "classpath://properties/testing/log.properties",
        "classpath://properties/testing/solr.properties",
        "classpath://properties/testing/mail.properties",
        "classpath://properties/testing/web.properties"
})
public class TestingConfiguration {
    
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
        return new ClassPathResource("properties/testing/quartz.properties");
    }
}
