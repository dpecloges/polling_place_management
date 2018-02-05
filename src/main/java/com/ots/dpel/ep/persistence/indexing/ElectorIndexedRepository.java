package com.ots.dpel.ep.persistence.indexing;

import com.ots.dpel.ep.dto.indexing.ElectorIndexedDocument;
import com.ots.dpel.global.solr.DpSolrRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectorIndexedRepository extends DpSolrRepository<ElectorIndexedDocument, String> {

}
