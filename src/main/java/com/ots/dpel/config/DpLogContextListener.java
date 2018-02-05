package com.ots.dpel.config;

import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener που αρχικοποιείται στην έναρξη της εφαρμογής και αφορά την παραμετροποίηση του Log4j2
 * Χρησιμοποιεί το αρχείο log4j2.xml που βρίσκεται μέσα στο φάκελο properties του εκάστοτε
 * runtime configuration
 */
public class DpLogContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        ServletContext servletContext = servletContextEvent.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        
        Environment env = wac.getEnvironment();
        
        String activeProfiles[] = env.getActiveProfiles();
        String profile = (activeProfiles == null || activeProfiles.length == 0 ? "development" : activeProfiles[0]);
        
        String pathToConfigFile = "properties/" + profile + "/log4j2.xml";
        
        Configurator.initialize(null, pathToConfigFile);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    
    }
    
}
