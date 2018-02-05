package com.ots.dpel.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.ots.dpel.auth.controllers",
    "com.ots.dpel.management.controllers",
    "com.ots.dpel.ep.controllers",
    "com.ots.dpel.results.controllers",
    "com.ots.dpel.common.controllers",
    "com.ots.dpel.ext.controllers",
    "com.ots.dpel.us.controllers",
    "com.ots.dpel.system.controllers",
    "com.ots.dpel.global.controllers"
})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        
        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");
        vr.setExposedContextBeanNames(new String[]{"messageSource"});
        
        return vr;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Default format ημερομηνίας
        // objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        // ISO 8601 (YYYY-MM-DD)
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        // Μη συμπερίληψη null τιμών στα JSON
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localResolver = new SessionLocaleResolver();
        localResolver.setDefaultLocale(new Locale("el", "GR"));
        return localResolver;
    }
    
    @Bean
    public SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver() {
        DpSortHandlerMethodArgumentResolver sortResolver = new DpSortHandlerMethodArgumentResolver();
        sortResolver.setSortParameter("sidx");
        sortResolver.setSortOrder("sord");
        return sortResolver;
    }
    
    @Bean
    public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }
    
    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }
    
    @Bean
    public SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver() {
        return new SitePreferenceHandlerMethodArgumentResolver();
    }
    
    @Bean
    public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
        return new SitePreferenceHandlerInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new LocaleChangeInterceptor());
        registry.addInterceptor(deviceResolverHandlerInterceptor());
        registry.addInterceptor(sitePreferenceHandlerInterceptor());
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver(sortHandlerMethodArgumentResolver());
        
        resolver.setOneIndexedParameters(true);
        resolver.setSizeParameterName("rows");
        resolver.setMaxPageSize(Integer.MAX_VALUE);
        
        argumentResolvers.add(resolver);
        argumentResolvers.add(deviceHandlerMethodArgumentResolver());
        argumentResolvers.add(sitePreferenceHandlerMethodArgumentResolver());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        
        
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSizePerFile(16 * 1024 * 1024);
        return resolver;
    }
}
