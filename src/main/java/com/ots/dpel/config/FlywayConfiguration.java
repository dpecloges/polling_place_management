package com.ots.dpel.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author glioliou
 */
@PropertySource("classpath:/properties/flyway.properties")
@Configuration
public class FlywayConfiguration {
    
    @Autowired
    private Environment env;
    
    public Flyway flyway() {
        String url = env.getProperty("jdbc.url", String.class);
        String username = env.getProperty("jdbc.username", String.class);
        String password = env.getProperty("jdbc.password", String.class);
        String locations = env.getProperty("flyway.locations", String.class);
        String table = env.getProperty("flyway.table", String.class);
        String schemas = env.getProperty("flyway.schemas", String.class);
        
        Flyway flyway = new Flyway();
        flyway.setDataSource(url, username, password);
        flyway.setLocations(locations);
        flyway.setTable(table);
        flyway.setSchemas((String[]) Arrays.asList(schemas.split("\\s*,\\s*")).toArray());
        flyway.setValidateOnMigrate(false);
        return flyway;
    }
}
