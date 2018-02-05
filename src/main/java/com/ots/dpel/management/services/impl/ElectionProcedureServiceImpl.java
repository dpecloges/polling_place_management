package com.ots.dpel.management.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.management.core.domain.ElectionProcedure;
import com.ots.dpel.management.core.domain.QElectionProcedure;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.persistence.ElectionProcedureRepository;
import com.ots.dpel.management.services.ElectionProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Date;

@Service
public class ElectionProcedureServiceImpl implements ElectionProcedureService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ElectionProcedureRepository electionProcedureRepository;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Override
    public ElectionProcedureDto getCurrent() {
        
        QElectionProcedure electionProcedure = QElectionProcedure.electionProcedure;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionProcedure).where(electionProcedure.current.eq(YesNoEnum.YES));
        
        Expression<ElectionProcedureDto> expression = getElectionProcedureDtoConstructorExpression(electionProcedure);
        
        ElectionProcedureDto electionProcedureDto = query.singleResult(expression);
        
        return electionProcedureDto;
    }
    
    private Expression getElectionProcedureDtoConstructorExpression(QElectionProcedure electionProcedure) {
        return ConstructorExpression.create(ElectionProcedureDto.class,
                electionProcedure.id,
                electionProcedure.name,
                electionProcedure.current,
                electionProcedure.round,
                electionProcedure.resultsLastCalcDateTime,
                electionProcedure.snapshotLastCalcDateTime
        );
    }
    
    @Override
    public void validateCurrent(ElectionProcedureDto electionProcedureDto) {
        if (electionProcedureDto != null) {
            if (electionProcedureDto.getRound() == null) {
                throw new DpValidationException("NO_ELECTIONPROCEDURE_ROUND",
                    messageSourceProvider.getMessage("results.error.noelectionprocedureround"));
            }
        } else {
            throw new DpValidationException("NO_CURRENT_ELECTIONPROCEDURE",
                messageSourceProvider.getMessage("results.error.nocurrentelectionprocedure"));
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void updateResultsLastCalcDateTimeForCurrent(Date resultsLastCalcDateTime) {
        
        //Ανάκτηση της τρέχουσας εκλογικής διαδικασίας
        QElectionProcedure electionProcedure = QElectionProcedure.electionProcedure;
        
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(electionProcedure.current.eq(YesNoEnum.YES));
        
        ElectionProcedure currentElectionProcedure = electionProcedureRepository.findOne(predicate);
        
        //Ορισμός timestamp και αποθήκευση
        currentElectionProcedure.setResultsLastCalcDateTime(resultsLastCalcDateTime);
        electionProcedureRepository.save(currentElectionProcedure);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void updateSnapshotLastCalcDateTimeForCurrent(Date snapshotLastCalcDateTime) {
        
        //Ανάκτηση της τρέχουσας εκλογικής διαδικασίας
        QElectionProcedure electionProcedure = QElectionProcedure.electionProcedure;
        
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(electionProcedure.current.eq(YesNoEnum.YES));
        
        ElectionProcedure currentElectionProcedure = electionProcedureRepository.findOne(predicate);
        
        //Ορισμός timestamp και αποθήκευση
        currentElectionProcedure.setSnapshotLastCalcDateTime(snapshotLastCalcDateTime);
        electionProcedureRepository.save(currentElectionProcedure);
    }
}
