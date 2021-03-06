package com.ots.dpel.config.scheduling;

import com.ots.dpel.system.services.SchedulingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener που αρχικοποιείται στην έναρξη της εφαρμογής και αφορά
 * την έναρξη και τον τερματισμό του Scheduler
 */
public class SchedulingContextListener implements ServletContextListener {
    
    private static final Logger logger = LogManager.getLogger(SchedulingContextListener.class);
    
    private SchedulingService schedulingService(ServletContextEvent sce) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        return springContext.getBean(SchedulingService.class);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            this.schedulingService(sce).startScheduler();
        } catch (SchedulerException e) {
            logger.error("Error while Scheduler is being started", e);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            this.schedulingService(sce).shutdownScheduler();
        } catch (SchedulerException e) {
            logger.error("Error while Scheduler is being shutdown", e);
        }
    }
}
