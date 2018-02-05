package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile(value = "support")
@PropertySource({
        "classpath://properties/support/jdbc-dev.properties",
        "classpath://properties/support/hibernate.properties",
        "classpath://properties/support/api.properties",
        "classpath://properties/support/security.properties",
        "classpath://properties/support/cache.properties",
        "classpath://properties/support/log.properties",
        "classpath://properties/support/solr.properties",
        "classpath://properties/support/mail.properties",
        "classpath://properties/support/web.properties"
})
public class SupportConfiguration {
    
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
        return new ClassPathResource("properties/support/quartz.properties");
    }
}
