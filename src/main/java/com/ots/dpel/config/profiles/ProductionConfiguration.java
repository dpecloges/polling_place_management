package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile(value = "production")
@PropertySource({
        "classpath://properties/production/jdbc-dev.properties",
        "classpath://properties/production/hibernate.properties",
        "classpath://properties/production/api.properties",
        "classpath://properties/production/security.properties",
        "classpath://properties/production/cache.properties",
        "classpath://properties/production/log.properties",
        "classpath://properties/production/solr.properties",
        "classpath://properties/production/mail.properties",
        "classpath://properties/production/web.properties"
})
public class ProductionConfiguration {
    
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
        return new ClassPathResource("properties/production/quartz.properties");
    }
}
