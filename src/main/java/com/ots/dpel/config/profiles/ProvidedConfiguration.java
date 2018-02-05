package com.ots.dpel.config.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * Configuration από αρχεία {@code .properties} εκτός του web application. 
 * Η τοποθεσία αυτή θα πρέπει να είναι σε φάκελο που να περιλαμβάνεται 
 * στο classpath του application server, και από τον οποίο να μπορεί να κάνει
 * η Java ανάκτηση resources βάσει classpath location. Παραδείγματα
 * τέτοιων φακέλων είναι ο {@code lib} στον Tomcat 7+, και ο 
 * {@code resources} στον Jetty. 
 * 
 * @author ktzonas
 */
@Configuration
@Profile(value = "providedconf")
@PropertySource({
    "classpath:properties/provided/jdbc-dev.properties",
    "classpath:properties/provided/hibernate.properties",
    "classpath:properties/provided/api.properties",
    "classpath:properties/provided/security.properties",
    "classpath:properties/provided/cache.properties",
    "classpath:properties/provided/log.properties",
    "classpath:properties/provided/solr.properties",
    "classpath:properties/provided/mail.properties",
    "classpath:properties/provided/web.properties"
})
public class ProvidedConfiguration {
    
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
        return new ClassPathResource("properties/provided/quartz.properties");
    }
}
