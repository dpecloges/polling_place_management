package com.ots.dpel.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.Flyway;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FlywayContextListener implements ServletContextListener {
    
    /**
     * Commons Logging instance.
     */
    private static Log log = LogFactory.getLog(FlywayContextListener.class);
    
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        FlywayConfiguration flywayConfiguration = wac.getBean(FlywayConfiguration.class);
        Flyway flyway = flywayConfiguration.flyway();
        flyway.migrate();
        log.info("Migration listener was hit.");
    }
    
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    
    }
    
}