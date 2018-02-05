package com.ots.dpel.config.scheduling;

import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;

@Configuration
public class SchedulingConfiguration {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    @Qualifier(value = "quartzProperties")
    private ClassPathResource classPathResource;
    
    @Bean
    public SchedulerListener schedulerListener() {
        return new DpSchedulerListener();
    }
    
    @Bean
    public TriggerListener triggerListener() {
        return new DpTriggerListener("DpTriggerListener");
    }
    
    @Bean
    public Scheduler scheduler() throws SchedulerException, IOException {
        
        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(classPathResource.getInputStream());
        
        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(springBeanJobFactory());
        
        scheduler.getListenerManager().addSchedulerListener(schedulerListener());
        scheduler.getListenerManager().addTriggerListener(triggerListener());
        
        return scheduler;
    }
    
    @Bean
    public JobListener jobListener() throws IOException, SchedulerException {
        return scheduler().getListenerManager().getJobListener("DpJobListener");
    }
    
    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
}
