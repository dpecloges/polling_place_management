package com.ots.dpel.ep.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.common.core.domain.QAdminUnit;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.core.domain.QVoter;
import com.ots.dpel.ep.core.domain.Voter;
import com.ots.dpel.ep.core.enums.IdType;
import com.ots.dpel.ep.dto.ElectionDepartmentVoterDto;
import com.ots.dpel.ep.dto.VoterDto;
import com.ots.dpel.ep.persistence.VoterRepository;
import com.ots.dpel.ep.services.VoterService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.global.utils.UserUtils;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoterServiceImpl implements VoterService {
    
    private static final Logger logger = LogManager.getLogger(VoterServiceImpl.class);
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private VoterRepository voterRepository;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    
    @Override
    public VoterDto findVoter(Long id) {
        
        QVoter voter = QVoter.voter;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(voter).where(voter.id.eq(id));
        
        Expression<VoterDto> expression = getVoterDtoConstructorExpression(voter);
        
        VoterDto voterDto = query.singleResult(expression);
        
        return voterDto;
    }
    
    @Override
    public VoterDto findVotedByElectorIdAndCurrentElectionProcedureRound(Long electorId) {
        
        if (electorId == null) {
            return null;
        }
        
        //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        
        if (currentElectionProcedureDto == null) {
            return null;
        }
        
        QVoter voter = QVoter.voter;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QAdminUnit municipality = new QAdminUnit("municipality");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        //Ψηφίσας συνδεδεμένος με τον εκλογέα με το δεδομένο id
        predicate.and(voter.electorId.eq(electorId));
        
        //Ψηφίσας στον παρόντα γύρο της τρέχουσας εκλογικής διαδιασίας
        if (currentElectionProcedureDto.getId() != null) {
            predicate.and(voter.electionProcedureId.eq(currentElectionProcedureDto.getId()));
        }
        if (!DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            predicate.and(voter.round.eq(ElectionRound.valueOf(currentElectionProcedureDto.getRound())));
        }
        
        //Με ένδειξη ψήφου true
        predicate.and(voter.voted.eq(YesNoEnum.YES));
        
        query.from(voter)
                .leftJoin(voter.electionDepartment, electionDepartment)
                .leftJoin(electionDepartment.electionCenter, electionCenter)
                .leftJoin(electionCenter.municipality, municipality)
                .where(predicate);
        
        Expression<VoterDto> expression = getVoterDtoConstructorExpressionForVerification(voter, electionDepartment, electionCenter, municipality);
        
        VoterDto voterDto = query.singleResult(expression);
        return voterDto;
    }
    
    private Expression<VoterDto> getVoterDtoConstructorExpression(QVoter voter) {
        return ConstructorExpression.create(VoterDto.class,
                voter.id,
                voter.electorId,
                voter.electionProcedureId,
                voter.round,
                voter.electionDepartmentId,
                voter.verificationNumber,
                voter.eklSpecialNo,
                voter.lastName,
                voter.firstName,
                voter.fatherFirstName,
                voter.motherFirstName,
                voter.birthDate,
                voter.birthYear,
                voter.address,
                voter.addressNo,
                voter.city,
                voter.postalCode,
                voter.country,
                voter.cellphone,
                voter.email,
                voter.idType,
                voter.idNumber,
                voter.voted,
                voter.voteDateTime,
                voter.member,
                voter.payment,
                voter.electionDepartment.name.as("electionDepartmentName")
        );
    }
    
    private Expression getVoterDtoConstructorExpressionForVerification(QVoter voter, QElectionDepartment electionDepartment, QElectionCenter electionCenter, QAdminUnit municipality) {
        return ConstructorExpression.create(VoterDto.class,
                voter.id,
                voter.voteDateTime,
                electionDepartmentService.getElectionDepartmentBasicDtoConstructorExpression(electionDepartment, electionCenter, municipality),
                voter.address,
                voter.addressNo,
                voter.city,
                voter.postalCode,
                voter.country,
                voter.cellphone,
                voter.email,
                voter.idType,
                voter.idNumber,
                voter.member,
                voter.payment,
                voter.electionDepartmentId,
                voter.verificationNumber
        );
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public VoterDto saveVoter(VoterDto voterDto) {
        
        Voter voter = (voterDto.getId() == null) ? new Voter() : voterRepository.findOne(voterDto.getId());
        
        dtoToEntity(voterDto, voter);
        
        //Μετατροπή του αριθμού ταυτ. εγγράφου σε κεφαλαία
        if(!DpTextUtils.isEmpty(voter.getIdNumber())) {
            voter.setIdNumber(DpTextUtils.toUpperCaseGreekSupportExtended(voter.getIdNumber()));
        }
        
        if (voter.getId() == null) {
            //Διαπίστευση
            
            //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
            ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
            
            if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
                return null;
            }
            else {
                voter.setElectionProcedureId(currentElectionProcedureDto.getId());
                voter.setRound(currentElectionProcedureDto.getRound() == null ? null : ElectionRound.valueOf(currentElectionProcedureDto.getRound()));
                voter.setVoted(YesNoEnum.YES);
                voter.setVoteDateTime(new Date());
            }
            
            //Έλεγχος ότι ο εκλογέας δεν έχει ήδη διαπιστευτεί
            if(hasVoted(voter.getElectorId(), voter.getElectionProcedureId(), voter.getRound())) {
                throw new DpValidationException(VerificationServiceImpl.ERROR_SAVEVOTER_CREATE_ALREADY_VOTED, messageSourceProvider.getMessage("ep.verification.saveVoter.error.create.alreadyVoted"));
            }
            
            if(voter.getElectionDepartmentId() != null) {
                
                //Έλεγχος αν το εκλογικό τμήμα έχει ήδη υποβάλει αποτελέσματα
                if(electionDepartmentService.hasSubmitted(voter.getElectionDepartmentId(), ElectionRound.valueOf(currentElectionProcedureDto.getRound()))) {
                    throw new DpValidationException(VerificationServiceImpl.ERROR_SAVEVOTER_CREATE_DEPARTMENT_HAS_SUBMITTED, messageSourceProvider.getMessage("ep.verification.saveVoter.error.create.departmentHasSubmitted"));
                }
                
                //Παραγωγή αριθμού διαπίστευσης και ορισμός του πάνω στον voter προς αποθήκευση
                Integer currentVerificationSerial = electionDepartmentService.getVerificationSerial(voter.getElectionDepartmentId(), ElectionRound.valueOf(currentElectionProcedureDto.getRound()));
                voter.setVerificationNumber(currentVerificationSerial + 1);
    
                //Ενημέρωση του σειριακού αριθμού στο εκλογικό τμήμα
                electionDepartmentService.incrementVerificationSerial(voter.getElectionDepartmentId(), ElectionRound.valueOf(currentElectionProcedureDto.getRound()));
            }
            
        }
        else {
            //Ενημέρωση στοιχείων
            
            //Αν ο χρήστης ανήκει σε εκλογικό τμήμα, τότε δεν μπορεί να γίνει ενημέρωση στοιχείων για ψηφίσαντα σε άλλο εκλογικό τμήμα.
            Long userElectionDepartmentId = UserUtils.getUserElectionDepartmentId();
            if(userElectionDepartmentId != null && voter.getElectionDepartmentId() != null && !userElectionDepartmentId.equals(voter.getElectionDepartmentId())) {
                throw new DpValidationException(VerificationServiceImpl.ERROR_SAVEVOTER_UPDATE_VOTED_IN_ANOTHER_DEPARTMENT, messageSourceProvider.getMessage("ep.verification.saveVoter.error.update.votedInAnotherDepartment"));
            }
        }
        
        try {
            voterRepository.save(voter);
        }
        catch (DataIntegrityViolationException e) {
            //Έλεγχος ότι δεν υπάρχει ήδη αποθηκευμένος ο ίδιος αριθμός διαπίστευσης στο συγκεκριμένο εκλογικό τμήμα
            if (e.getCause() != null && e.getCause().getCause() != null &&
                    e.getCause().getCause().getMessage().contains("voter_verification_number_fields")) {
                logger.error("Duplicate verification number [{}] found in department id [{}] for round [{}] in election procedure id [{}].",
                        voter.getVerificationNumber(), voter.getElectionDepartmentId(), voter.getRound(), voter.getElectionProcedureId());
                throw new DpValidationException(VerificationServiceImpl.ERROR_SAVEVOTER_CREATE_DUPLICATE_VERIFICATION_NUMBER,
                        messageSourceProvider.getMessage("ep.verification.saveVoter.error.create.duplicateVerificationNumber"));
            }
            else {
                throw e;
            }
        }
        
        return findVoter(voter.getId());
    }
    
    private void dtoToEntity(VoterDto voterDto, Voter voter) {
        
        if (voterDto == null) {
            return;
        }
        
        if (voter == null) {
            voter = new Voter();
        }
        
        BeanUtils.copyProperties(voterDto, voter, "electionDepartmentDto", "electionProcedureId", "round", "voted", "voteDateTime", "verificationNumber");
        voter.setMember(voterDto.getMember() != null && voterDto.getMember() ? YesNoEnum.YES : YesNoEnum.NO);
        voter.setIdType(DpTextUtils.isEmpty(voterDto.getIdType()) ? null : IdType.valueOf(voterDto.getIdType()));
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void undoVote(Long id, String undoReason) {
    
        Voter voter = null;
        if(id != null) {
            voter = voterRepository.findOne(id);
        }
        
        if(voter == null) {
            throw new DpValidationException(VerificationServiceImpl.ERROR_UNDOVOTE_VOTER_IS_NULL, messageSourceProvider.getMessage("ep.verification.undoVote.error.voterIsNull"));
        }
        else if(voter.getVoted() == null || voter.getVoted().equals(YesNoEnum.NO)) {
            throw new DpValidationException(VerificationServiceImpl.ERROR_UNDOVOTE_NOT_VOTED, messageSourceProvider.getMessage("ep.verification.undoVote.error.notVoted"));
        }
        else {
            //Αν ο χρήστης ανήκει σε εκλογικό τμήμα, τότε δεν μπορεί να γίνει αναίρεση διαπίστευσης για ψηφίσαντα σε άλλο εκλογικό τμήμα.
            Long userElectionDepartmentId = UserUtils.getUserElectionDepartmentId();
            if(userElectionDepartmentId != null && voter.getElectionDepartmentId() != null && !userElectionDepartmentId.equals(voter.getElectionDepartmentId())) {
                throw new DpValidationException(VerificationServiceImpl.ERROR_UNDOVOTE_VOTED_IN_ANOTHER_DEPARTMENT, messageSourceProvider.getMessage("ep.verification.undoVote.error.votedInAnotherDepartment"));
            }
            
            //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
            ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
            if(currentElectionProcedureDto != null && !DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
                //Αν το εκλογικό τμήμα έχει υποβάλει αποτελέσματα, τότε δεν μπορεί να γίνει αναίρεση διαπίστευσης.
                if(electionDepartmentService.hasSubmitted(voter.getElectionDepartmentId(), ElectionRound.valueOf(currentElectionProcedureDto.getRound()))) {
                    throw new DpValidationException(VerificationServiceImpl.ERROR_UNDOVOTE_DEPARTMENT_HAS_SUBMITTED, messageSourceProvider.getMessage("ep.verification.undoVote.error.departmentHasSubmitted"));
                }
            }
            
            //Ορισμός voted σε 0
            voter.setVoted(YesNoEnum.NO);
            
            if(!DpTextUtils.isEmpty(undoReason)) {
                //Ορισμός αιτιολογίας αναίρεσης
                voter.setUndoReason(undoReason);
            }
            
            //Αποθήκευση
            voterRepository.save(voter);
        }
    }
    
    @Override
    public Long getVoterCountByElectionDepartmentId(Long electionDepartmentId, YesNoEnum voted) {
        
        //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null) {
            return 0L;
        }
        
        //Αν δεν έχει δοθεί id εκλογικού τμήματος, παίρνουμε αυτό του χρήστη
        if (electionDepartmentId == null) {
            electionDepartmentId = UserUtils.getUserElectionDepartmentId();
        }
        
        if(electionDepartmentId == null) {
            return 0L;
        }
        
        QVoter voter = QVoter.voter;
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        predicate.and(voter.electionProcedureId.eq(currentElectionProcedureDto.getId()));
        predicate.and(voter.electionDepartmentId.eq(electionDepartmentId));
        predicate.and(voter.round.eq(ElectionRound.valueOf(currentElectionProcedureDto.getRound())));
        
        if (voted != null) {
            predicate.and(voter.voted.eq(voted));
        }
        
        return query.from(voter)
                .where(predicate)
                .count();
    }
    
    private Boolean hasVoted(Long electorId, Long electionProcedureId, ElectionRound round) {
        
        if(electorId == null || electionProcedureId == null || round == null) {
            return false;
        }
        
        QVoter voter = QVoter.voter;
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(voter.electorId.eq(electorId));
        predicate.and(voter.electionProcedureId.eq(electionProcedureId));
        predicate.and(voter.round.eq(round));
        predicate.and(voter.voted.eq(YesNoEnum.YES));
        
        return query.from(voter).where(predicate).exists();
    }
    
    @Override
    public Map<Long, Integer> getAllElectionDepartmentVoterCount(Long electionProcedureId, String round, YesNoEnum voted) {
        
        String sql = "SELECT " +
                "voter.n_electiondepartment_id AS electionDepartmentId, " +
                "count(*) AS voterCount " +
                "FROM dp.voter voter " +
                "USE INDEX (voter_verification_snapshot_fields) " +
                "WHERE voter.n_electionprocedure_id = :electionProcedureId AND " +
                "voter.v_round = :round AND " +
                "voter.n_voted = :voted " +
                "GROUP BY voter.n_electiondepartment_id " +
                "ORDER BY voter.n_electiondepartment_id ASC";
    
        Query query = entityManager.createNativeQuery(sql, ElectionDepartmentVoterDto.class);
        query.setParameter("electionProcedureId", electionProcedureId);
        query.setParameter("round", round);
        query.setParameter("voted", voted.ordinal());
    
        List<ElectionDepartmentVoterDto> voters = query.getResultList();
        
        Map<Long, Integer> votersMap = new HashMap<>();
        
        for (ElectionDepartmentVoterDto electionDepartmentVoterDto : voters) {
            votersMap.put(electionDepartmentVoterDto.getElectionDepartmentId(), electionDepartmentVoterDto.getVoterCount());
        }
        
        return votersMap;
    }
    
    @Override
    public Page<VoterDto> findVotersFromUnsubmitted(YesNoEnum voted, Pageable pageable) {
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return new PageImpl<>(Collections.<VoterDto>emptyList());
        }
        
        ElectionRound electionRound = ElectionRound.valueOf(currentElectionProcedureDto.getRound());
        
        QVoter voter = QVoter.voter;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        BooleanBuilder predicate = new BooleanBuilder();
        
        switch (electionRound) {
            case FIRST:
                predicate.and(electionDepartment.submittedFirst.eq(YesNoEnum.NO));
                break;
            case SECOND:
                predicate.and(electionDepartment.submittedSecond.eq(YesNoEnum.NO));
                break;
        }
        
        if (voted != null) predicate.and(voter.voted.eq(voted));
        
        query.from(voter)
            .join(voter.electionDepartment, electionDepartment)
            .where(predicate);
        
        FactoryExpression<VoterDto> expression = (FactoryExpression<VoterDto>) getVoterDtoConstructorExpression(voter);
        
        return voterRepository.findAll(query, expression, pageable);
    }
}
