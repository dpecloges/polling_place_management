package com.ots.dpel.management.services.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.management.core.domain.QCandidate;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.dto.list.CandidateListDto;
import com.ots.dpel.management.dto.list.QCandidateListDto;
import com.ots.dpel.management.persistence.CandidateRepository;
import com.ots.dpel.management.services.CandidateService;
import com.ots.dpel.management.services.ElectionProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    ElectionProcedureService electionProcedureService;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public List<CandidateListDto> getAll() {
        QCandidate candidate = QCandidate.candidate;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(candidate);
        
        FactoryExpression<CandidateListDto> factoryExpression = new QCandidateListDto(
            candidate.id,
            candidate.lastName,
            candidate.firstName,
            candidate.round,
            candidate.order);
        
        return candidateRepository.findList(query, factoryExpression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public List<CandidateListDto> getByCurrentElectionProcedure() {
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        
        QCandidate candidate = QCandidate.candidate;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(candidate)
            .where(candidate.electionProcedureId.eq(electionProcedureDto.getId())
                .and(candidate.round.eq(ElectionRound.valueOf(electionProcedureDto.getRound()))));
        
        FactoryExpression<CandidateListDto> factoryExpression = new QCandidateListDto(
            candidate.id,
            candidate.lastName,
            candidate.firstName,
            candidate.round,
            candidate.order);
    
        return candidateRepository.findList(query, factoryExpression);
    }
    
    @Override
    public Short getOrder(Long id) {
        
        if(id == null) {
            return null;
        }
        
        QCandidate candidate = QCandidate.candidate;
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(candidate).where(candidate.id.eq(id)).singleResult(candidate.order);
    }
}
