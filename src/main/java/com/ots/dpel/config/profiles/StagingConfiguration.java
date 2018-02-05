package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile(value = "staging")
@PropertySource({
        "classpath://properties/staging/jdbc-dev.properties",
        "classpath://properties/staging/hibernate.properties",
        "classpath://properties/staging/api.properties",
        "classpath://properties/staging/security.properties",
        "classpath://properties/staging/cache.properties",
        "classpath://properties/staging/log.properties",
        "classpath://properties/staging/solr.properties",
        "classpath://properties/staging/mail.properties",
        "classpath://properties/staging/web.properties"
})
public class StagingConfiguration {
    
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
        return new ClassPathResource("properties/staging/quartz.properties");
    }
}
