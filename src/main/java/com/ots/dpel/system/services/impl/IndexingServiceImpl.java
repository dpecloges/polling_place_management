package com.ots.dpel.system.services.impl;

import com.ots.dpel.config.beans.SolrConfigBean;
import com.ots.dpel.ep.services.ElectorService;
import com.ots.dpel.system.services.IndexingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Υλοποίηση λειτουργιών που αφορούν το Indexing της εφαρμογής
 */
@Service
public class IndexingServiceImpl implements IndexingService {
    
    private static final Logger logger = LogManager.getLogger(IndexingServiceImpl.class);
    
    @Autowired
    private SolrConfigBean solrProperties;
    
    @Autowired
    @Lazy
    private ElectorService electorService;
    
    @Override
    public Boolean solrEnabled() {
        return solrProperties.getEnabled();
    }
    
    @Override
    public Boolean isCoreOnline(String core) {
        return solrProperties.isCoreOnline(core);
    }
    
    @Override
    public void setCoreOnlineStatus(String core, Boolean status) {
        
        logger.trace("Set core {} status to {}", core, status);
        
        if (!core.equals(IndexingService.ELECTORS_INDEX)) {
            logger.error("Core {} is not available in valid Solr cores list", core);
            throw new IllegalArgumentException("Core " + core + " is not available in valid Solr cores list");
        }
        
        if (status.equals(Boolean.TRUE)) {
            logger.info("Setting Solr core {} to online mode", core);
            solrProperties.setCoreOnline(core);
        } else {
            logger.info("Setting Solr core {} to offline mode", core);
            solrProperties.setCoreOffline(core);
        }
    }
}
