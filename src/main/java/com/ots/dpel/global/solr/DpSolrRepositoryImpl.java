package com.ots.dpel.global.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.repository.query.SolrEntityInformation;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Υλοποίηση των μεθόδων που ορίζονται στο {@link DpSolrRepository}
 * @param <T>
 * @param <ID>
 * @param <Τ>
 */
public class DpSolrRepositoryImpl<T, ID extends Serializable, Τ>
        extends SimpleSolrRepository<T, ID> implements DpSolrRepository<T, ID> {
    
    public DpSolrRepositoryImpl(SolrEntityInformation<T, ?> metadata, SolrOperations solrOperations) {
        super(metadata, solrOperations);
    }
    
    @Override
    public <S extends T> S saveWithoutCommit(S entity) {
        getSolrOperations().saveBean(entity);
        return entity;
    }
    
    @Override
    public <S extends T> S saveWithCommit(S entity) {
        getSolrOperations().saveBean(entity);
        getSolrOperations().commit();
        return entity;
    }
    
    @Override
    public <S extends T> Iterable<S> saveWithoutCommit(Iterable<S> entities) {
        Assert.notNull(entities, "Cannot insert 'null' as a List.");
        
        if (!(entities instanceof Collection<?>)) {
            throw new InvalidDataAccessApiUsageException("Entities have to be inside a collection");
        }
        
        getSolrOperations().saveBeans((Collection<? extends T>) entities);
        return entities;
    }
    
    @Override
    public <S extends T> Iterable<S> saveWithCommit(Iterable<S> entities) {
        Assert.notNull(entities, "Cannot insert 'null' as a List.");
        
        if (!(entities instanceof Collection<?>)) {
            throw new InvalidDataAccessApiUsageException("Entities have to be inside a collection");
        }
        
        getSolrOperations().saveBeans((Collection<? extends T>) entities);
        getSolrOperations().commit();
        return entities;
    }
    
    @Override
    public Page<T> findAll(Criteria criteria, Pageable pageable) {
        
        ScoredPage<T> scoredPage = getSolrOperations().queryForPage(new SimpleQuery(criteria, pageable), getEntityClass());
        return scoredPage;
    }
    
    @Override
    public SolrPingResponse ping() {
        
        return getSolrOperations().ping();
    }
    
    @Override
    public UpdateResponse partialUpdate(Iterable<PartialUpdate> partialUpdates) {
        
        UpdateResponse updateResponse = getSolrOperations().saveBeans((Collection<? extends T>) partialUpdates);
        return updateResponse;
    }
    
    @Override
    public List<T> findAll(Criteria criteria) {
        
        ScoredPage<T> scoredPage = getSolrOperations().queryForPage(new SimpleQuery(criteria, new PageRequest(0, Integer.MAX_VALUE)), getEntityClass());
        return scoredPage.getContent();
    }
    
    @Override
    public Page<T> findAll(Criteria criteria, FacetOptions facetOptions, Pageable pageable) {
        
        SimpleFacetQuery facetQuery = new SimpleFacetQuery(criteria, pageable);
        facetQuery.setFacetOptions(facetOptions);
        
        FacetPage<T> facetPage = getSolrOperations().queryForFacetPage(facetQuery, getEntityClass());
        
        return facetPage;
    }
    
    @Override
    public QueryResponse executeSolrQuery(SolrQuery solrQuery) {
        
        DpSolrCallback<QueryResponse> callback = new DpSolrCallback<>(solrQuery);
        return (QueryResponse) getSolrOperations().execute(callback);
    }
    
}
