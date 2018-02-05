package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile(value = "development")
@PropertySource({
        "classpath://properties/development/jdbc-dev.properties",
        "classpath://properties/development/hibernate.properties",
        "classpath://properties/development/api.properties",
        "classpath://properties/development/security.properties",
        "classpath://properties/development/cache.properties",
        "classpath://properties/development/log.properties",
        "classpath://properties/development/solr.properties",
        "classpath://properties/development/mail.properties",
        "classpath://properties/development/web.properties"
})
public class DevelopmentConfiguration {
    
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
        return new ClassPathResource("properties/development/quartz.properties");
    }
}
