package com.ots.dpel.ep.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.ep.args.VerificationArgs;
import com.ots.dpel.ep.core.domain.QElector;
import com.ots.dpel.ep.dto.ElectorDto;
import com.ots.dpel.ep.dto.converters.ElectorIndexedDocumentConverter;
import com.ots.dpel.ep.dto.indexing.ElectorIndexedDocument;
import com.ots.dpel.ep.persistence.ElectorRepository;
import com.ots.dpel.ep.persistence.indexing.ElectorIndexedRepository;
import com.ots.dpel.ep.services.ElectorService;
import com.ots.dpel.global.utils.UuidUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import static com.ots.dpel.ep.predicates.ElectorPredicates.createElectorVerificationPredicate;
import static com.ots.dpel.ep.predicates.ElectorPredicates.createMatchingElectorsSolrCriteria;

@Service
public class ElectorServiceImpl implements ElectorService {
    
    private static final Logger logger = LogManager.getLogger(ElectorServiceImpl.class);
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ElectorRepository electorRepository;
    
    @Autowired
    private ElectorIndexedRepository electorIndexedRepository;
    
    @Override
    public Page<ElectorDto> findMatchingElectors(VerificationArgs args, Pageable pageable) {
        
        QElector elector = QElector.elector;
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = createElectorVerificationPredicate(args);
        
        query.from(elector).where(predicate);
    
        FactoryExpression<ElectorDto> expression = getElectorDtoConstructorExpression(elector);
        
        Page<ElectorDto> electorDtos = electorRepository.findAll(query, expression, pageable);
        
        return electorDtos;
    }
    
    @Override
    public Page<ElectorDto> findMatchingElectorsFromSolr(VerificationArgs args, Pageable pageable) {
        
        Criteria criteria = createMatchingElectorsSolrCriteria(args);
        
        Page<ElectorIndexedDocument> electorIndexedDocuments = electorIndexedRepository.findAll(criteria, pageable);
        
        return electorIndexedDocuments.map(new ElectorIndexedDocumentConverter());
    }
    
    @Override
    public ElectorDto findElectorBasic(Long id) {
        
        QElector elector = QElector.elector;
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(elector).where(elector.id.eq(id));
        
        FactoryExpression<ElectorDto> expression = getElectorDtoConstructorExpression(elector);
        
        ElectorDto electorDto = query.singleResult(expression);
        return electorDto;
    }
    
    @Override
    public ElectorDto findElectorBasicFromSolr(Long id) {
        
        ElectorIndexedDocument electorIndexedDocument = electorIndexedRepository.findOne(String.valueOf(id));
        return new ElectorIndexedDocumentConverter().convert(electorIndexedDocument);
    }
    
    private ConstructorExpression<ElectorDto> getElectorDtoConstructorExpression(QElector elector) {
        return ConstructorExpression.create(ElectorDto.class,
                elector.id,
                elector.lastName,
                elector.firstName,
                elector.fatherFirstName,
                elector.motherFirstName,
                elector.birthYear,
                elector.birthMonth,
                elector.birthDay,
                elector.municipalRecordNo,
                elector.municipalUnitDescription,
                elector.eklSpecialNo
        );
    }
    
    @SuppressWarnings("unused")
    @Override
    public Boolean electorsIndexIsOnline() {
        
        try {
            SolrPingResponse pingResponse = electorIndexedRepository.ping();
        } catch (DataAccessException dataAccessException) {
            //Δεν υπάρχει συνδεση με τον Solr Index
            //Πραγματοποιείται log του exception σε αυτό το σημείο και επιστρέφεται απλά η ένδειξη false
            logger.error("Error Id: ".concat(UuidUtils.generateId()).concat(" - Message: ").concat(dataAccessException.getMessage()),
                    dataAccessException);
            return false;
        }
        
        return true;
    }
    
    @Override
    public Page<ElectorDto> findGenericElectors(Pageable pageable) {
        QElector elector = QElector.elector;
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(elector);
        
        FactoryExpression<ElectorDto> expression = getElectorDtoConstructorExpression(elector);
    
        return electorRepository.findAll(query, expression, pageable);
    }
    
    @Override
    public Page<ElectorDto> findGenericElectorsFromSolr(Pageable pageable) {
        Page<ElectorIndexedDocument> electorIndexedDocuments = electorIndexedRepository.findAll(pageable);
        return electorIndexedDocuments.map(new ElectorIndexedDocumentConverter());
    }
}
