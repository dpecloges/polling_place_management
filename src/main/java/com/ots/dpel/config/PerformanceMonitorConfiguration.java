package com.ots.dpel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class PerformanceMonitorConfiguration {
    
    @Bean
    public PerformanceMonitor getPerformanceMonitor() {
        return new PerformanceMonitor();
    }
    
}
