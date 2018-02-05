package com.ots.dpel.config;

import com.github.ziplet.filter.compression.CompressingFilter;
import com.jamonapi.http.JAMonServletFilter;
import com.ots.dpel.config.scheduling.SchedulingContextListener;
import com.ots.dpel.config.security.SecurityConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Bootstrap implements WebApplicationInitializer {
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        addCharacterEncodingFilter(servletContext);
        addJamonFilter(servletContext);
        addCompressingFilter(servletContext);
        WebApplicationContext rootContext = createRootContext(servletContext);
        configureWebMvcApplication(servletContext, rootContext);
        addSecurityFilter(servletContext);
    }
    
    private void addCharacterEncodingFilter(ServletContext servletContext) {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        servletContext.addFilter("encoding-filter", filter).addMappingForUrlPatterns(null, false, "/*");
    }
    
    private void addJamonFilter(ServletContext servletContext) {
        servletContext.addFilter("jamonFilter", new JAMonServletFilter()).addMappingForUrlPatterns(null, true, "/*");
    }
    
    private void addCompressingFilter(ServletContext servletContext) {
        servletContext.addFilter("compressingFilter", new CompressingFilter()).addMappingForUrlPatterns(null, true, "/*");
    }
    
    private WebApplicationContext createRootContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class, SecurityConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(DpLogContextListener.class);
        servletContext.addListener(FlywayContextListener.class);
        servletContext.addListener(SolrContextListener.class);
        servletContext.addListener(SchedulingContextListener.class);
        servletContext.setInitParameter("defaultHtmlEscape", "true");
        return rootContext;
    }
    
    private void configureWebMvcApplication(ServletContext servletContext, WebApplicationContext rootContext) {
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(WebMvcConfiguration.class);
        mvcContext.register(WebMvcConfiguration.class, FlywayConfiguration.class, PerformanceMonitorConfiguration.class, SwaggerConfig.class);
        mvcContext.setParent(rootContext);
        mvcContext.setBeanName("DP");
        
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("webapplication", new DispatcherServlet(mvcContext));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
    
    private void addSecurityFilter(ServletContext servletContext) {
        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain")).addMappingForUrlPatterns(null, false,
            "/*");
    }
}