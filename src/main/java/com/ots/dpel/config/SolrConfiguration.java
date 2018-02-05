package com.ots.dpel.config;

import com.ots.dpel.config.beans.SolrConfigBean;
import com.ots.dpel.global.solr.DpSolrRepositoryImpl;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(multicoreSupport = true,
        basePackages = {
                "com.ots.dpel.ep.persistence.indexing"
        },
        repositoryBaseClass = DpSolrRepositoryImpl.class)
public class SolrConfiguration {
    
    @Autowired
    private SolrConfigBean solrProperties;
    
    @Bean
    public SolrServer solrServer() {
        return new HttpSolrServer(solrProperties.getHost());
    }
    
    @Bean
    public SolrTemplate solrTemplate() {
        return new SolrTemplate(solrServer());
    }
}
