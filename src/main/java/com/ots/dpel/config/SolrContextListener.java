package com.ots.dpel.config;

import com.ots.dpel.config.beans.SolrConfigBean;
import com.ots.dpel.ep.services.ElectorService;
import com.ots.dpel.system.services.IndexingService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Listener που αρχικοποιείται στην έναρξη της εφαρμογής και αφορά τον έλεγχο
 * της διασύνδεσης με το Solr για τη λειτουργία του Indexing
 */
public class SolrContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        ServletContext servletContext = servletContextEvent.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        
        SolrConfigBean solrProperties = wac.getBean(SolrConfigBean.class);
        ElectorService electorService = wac.getBean(ElectorService.class);
        
        Map<String, Boolean> onlineCores = new HashMap<>();
        
        if (solrProperties.getEnabled()) {
            onlineCores.put(IndexingService.ELECTORS_INDEX, electorService.electorsIndexIsOnline());
        } else {
            onlineCores.put(IndexingService.ELECTORS_INDEX, Boolean.FALSE);
        }
    
        solrProperties.setOnlineCores(onlineCores);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    
    }
}
