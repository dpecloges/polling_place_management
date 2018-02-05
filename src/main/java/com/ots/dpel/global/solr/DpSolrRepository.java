package com.ots.dpel.global.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Interface στο οποίο περιλαμβάνονται οι επιπλέον μέθοδοι παραγωγής ερωτημάτων
 * προς το Solr για την υποστήριξη full text searching
 *
 * @author lzagkaretos
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface DpSolrRepository <T, ID extends Serializable> extends SolrCrudRepository<T, ID> {
    
    /**
     * Αποθήκευση ενός αντικειμένου ως Solr Document χωρίς την πραγματοποίηση
     * κάποιου commit στο Solr (hard commit, soft commit)
     * <br/>
     * Η μέθοδος χρησιμοποιείται στην πραγματοποίηση ενημερώσεων στα entities
     * @param entity Το αντικείμενο προς αποθήκευση (indexing)
     * @param <S>
     * @return
     */
    public <S extends T> S saveWithoutCommit(S entity);
    
    /**
     * Αποθήκευση ενός αντικειμένου ως Solr Document με την πραγματοποίηση
     * commit στο Solr
     * <br/>
     * Η μέθοδος χρησιμοποιείται στην πραγματοποίηση ενημερώσεων στα entities
     * @param entity Το αντικείμενο προς αποθήκευση (indexing)
     * @param <S>
     * @return
     */
    public <S extends T> S saveWithCommit(S entity);
    
    /**
     * Αποθήκευση λίστας αντικειμένων ως Solr Documents χωρίς την πραγματοποίηση
     * κάποιου commit στο Solr (hard commit, soft commit)
     * <br/>
     * Η μέθοδος χρησιμοποιείται στο μαζικό indexing εγγραφών (αρχικοποίηση, reindexing κτλ.)
     * @param entities Η λίστα των αντικειμένων προς αποθήκευση (indexing)
     * @param <S>
     * @return
     */
    public <S extends T> Iterable<S> saveWithoutCommit(Iterable<S> entities);
    
    /**
     * Αποθήκευση λίστας αντικειμένων ως Solr Documents με την πραγματοποίηση
     * commit στο Solr
     * <br/>
     * Η μέθοδος χρησιμοποιείται στο μαζικό indexing εγγραφών (αρχικοποίηση, reindexing κτλ.)
     * @param entities Η λίστα των αντικειμένων προς αποθήκευση (indexing)
     * @param <S>
     * @return
     */
    public <S extends T> Iterable<S> saveWithCommit(Iterable<S> entities);
    
    /**
     * Ανάκτηση σελίδας (page) με αποτελέσματα από το Solr για δοθέν ερώτημα {@link Criteria}
     * @param criteria Το ερώτημα προς το Solr
     * @param pageable Η ζητούμενη σελίδα
     * @return Σελίδα με τα ανακτημένα αποτελέσματα
     */
    public Page<T> findAll(Criteria criteria, Pageable pageable);
    
    /**
     * Ανάκτηση σελίδας (page) με αποτελέσματα από το Solr {@link Criteria}
     *
     * @param pageable Η ζητούμενη σελίδα
     * @return Σελίδα με τα ανακτημένα αποτελέσματα
     */
    Page<T> findAll(Pageable pageable);
    
    /**
     * Εκτέλεση ερωτήματος ping στο Solr
     * Χρησιμοποιείται για τον έλεγχο εάν υπάρχει ενεργή σύνδεση
     * @return Το response για το ερώτημα ping
     */
    public SolrPingResponse ping();
    
    /**
     * Εκτέλεση ενημερώσεων συγκεκριμένων πεδίων στα documents του Solr (partial updates)
     * χωρίς την πραγματοποίηση κάποιου commit στο Solr (hard commit, soft commit)
     * @param partialUpdates Λίστα αντικειμένων ενημερώσεων
     * @return Το response του ερωτήματος
     */
    public UpdateResponse partialUpdate(Iterable<PartialUpdate> partialUpdates);
    
    /**
     * Ανάκτηση λίστας με αποτελέσματα από το Solr για δοθέν ερώτημα {@link Criteria}
     * @param criteria Το ερώτημα προς το Solr
     * @return Λίστα με τα ανακτημένα αποτελέσματα
     */
    public List<T> findAll(Criteria criteria);
    
    /**
     * Ανάκτηση σελίδας (facet page) με αποτελέσματα από το Solr για δοθέν ερώτημα {@link Criteria}
     * και {@link FacetOptions}
     * @param criteria Το ερώτημα προς το Solr
     * @param facetOptions Παράμετροι faceting
     * @param pageable Σελίδα με τα ανακτημένα αποτελέσματα
     * @return
     */
    public Page<T> findAll(Criteria criteria, FacetOptions facetOptions, Pageable pageable);
    
    /**
     * Εκτέλεση ερωτήματος {@link SolrQuery} στο Solr
     * @param solrQuery Το ερώτημα προς το Solr
     * @return Η απάντηση {@link QueryResponse}
     */
    public QueryResponse executeSolrQuery(SolrQuery solrQuery);
    
}
