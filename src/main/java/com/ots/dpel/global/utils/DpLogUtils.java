package com.ots.dpel.global.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.core.env.Environment;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import static java.util.Collections.singletonList;

/**
 * Βοηθητικές λειτουργίες που αφορούν το Logging
 * Περιλαμβάνονται στατικές μέθοδοι
 */
public class DpLogUtils {
    
    /**
     * Στατική μέθοδος που επιστρέφει αντικείμενο MongoClient
     * Χρησιμοποιείται σε περίπτωση που υπάρχει ενεργοποιημένος NoSQL Appender στο log4j2.xml
     * @return
     */
    public static MongoClient getLogMongoDatabaseClient() {
        
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        Environment environment = context.getBean(Environment.class);
        
        String host = environment.getProperty("log.mongodb.host");
        int port = Integer.parseInt(environment.getProperty("log.mongodb.port"));
        String username = environment.getProperty("log.mongodb.username");
        String password = environment.getProperty("log.mongodb.password");
        String dbAdmin = environment.getProperty("log.mongodb.db.admin");
        
        return new MongoClient(singletonList(new ServerAddress(host, port)),
            singletonList(MongoCredential.createCredential(username, dbAdmin, password.toCharArray())));
    }
}
