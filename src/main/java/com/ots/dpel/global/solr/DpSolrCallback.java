package com.ots.dpel.global.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.solr.core.SolrCallback;

import java.io.IOException;

/**
 * Κλάση που συνεισφέρει στη δυνατότητα εκτέλεσης SolrQuery ως string απευθείας στο SolrTemplate
 * Χρησιμοποιείται στο {@link DpSolrRepositoryImpl}
 */
public class DpSolrCallback<T> implements SolrCallback {
    
    private SolrQuery solrQuery;
    
    public DpSolrCallback(SolrQuery solrQuery) {
        this.solrQuery = solrQuery;
    }
    
    @Override
    public T doInSolr(SolrServer solrServer) throws SolrServerException, IOException {
        return (T) solrServer.query(solrQuery);
    }
}
