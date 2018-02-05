package com.ots.dpel.results.services.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.services.VoterService;
import com.ots.dpel.global.exceptions.DpRuntimeException;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.results.core.enums.AttachmentType;
import com.ots.dpel.results.dto.ResultsDto;
import com.ots.dpel.management.persistence.ElectionDepartmentRepository;
import com.ots.dpel.management.services.ElectionProcedureService;
import com.ots.dpel.results.services.SubmissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    
    private static final Logger logger = LogManager.getLogger(SubmissionServiceImpl.class);
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ElectionDepartmentRepository electionDepartmentRepository;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    // Σε MySQL βάση προέρχεται από το max_allowed_packet
    private static int MAX_FILE_SIZE = 4194304;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ResultsDto findResults(Long id) {
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        ElectionRound currentRound = electionProcedureDto.getRound() != null ? ElectionRound.valueOf(electionProcedureDto.getRound()) : null;
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        Expression<ResultsDto> expression = getResultsDtoConstructorExpression(electionDepartment, currentRound);
        
        return query.from(electionDepartment)
            .where(electionDepartment.id.eq(id))
            .singleResult(expression);
    }
    
    private Expression<ResultsDto> getResultsDtoConstructorExpression(QElectionDepartment electionDepartment, ElectionRound round) {
        if (round != null) {
            switch (round) {
                case FIRST:
                    return ConstructorExpression.create(ResultsDto.class,
                        electionDepartment.id,
                        electionDepartment.code,
                        electionDepartment.name,
                        electionDepartment.totalVotesFirst,
                        electionDepartment.whiteVotesFirst,
                        electionDepartment.invalidVotesFirst,
                        electionDepartment.validVotesFirst,
                        electionDepartment.candidateOneVotesFirst,
                        electionDepartment.candidateTwoVotesFirst,
                        electionDepartment.candidateThreeVotesFirst,
                        electionDepartment.candidateFourVotesFirst,
                        electionDepartment.candidateFiveVotesFirst,
                        electionDepartment.candidateSixVotesFirst,
                        electionDepartment.candidateSevenVotesFirst,
                        electionDepartment.candidateEightVotesFirst,
                        electionDepartment.candidateNineVotesFirst,
                        electionDepartment.candidateTenVotesFirst,
                        electionDepartment.submittedFirst,
                        electionDepartment.attachmentNameFirst,
                        electionDepartment.attachmentTwoNameFirst);
                case SECOND:
                    return ConstructorExpression.create(ResultsDto.class,
                        electionDepartment.id,
                        electionDepartment.code,
                        electionDepartment.name,
                        electionDepartment.totalVotesSecond,
                        electionDepartment.whiteVotesSecond,
                        electionDepartment.invalidVotesSecond,
                        electionDepartment.validVotesSecond,
                        electionDepartment.candidateOneVotesSecond,
                        electionDepartment.candidateTwoVotesSecond,
                        electionDepartment.candidateThreeVotesSecond,
                        electionDepartment.candidateFourVotesSecond,
                        electionDepartment.candidateFiveVotesSecond,
                        electionDepartment.candidateSixVotesSecond,
                        electionDepartment.candidateSevenVotesSecond,
                        electionDepartment.candidateEightVotesSecond,
                        electionDepartment.candidateNineVotesSecond,
                        electionDepartment.candidateTenVotesSecond,
                        electionDepartment.submittedSecond,
                        electionDepartment.attachmentNameSecond,
                        electionDepartment.attachmentTwoNameSecond);
            }
        }
        
        return ConstructorExpression.create(ResultsDto.class,
            electionDepartment.id,
            electionDepartment.code,
            electionDepartment.name);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ResultsDto saveResults(ResultsDto resultsDto) {
        if (resultsDto.getId() == null) return null;
        
        ElectionDepartment electionDepartment = electionDepartmentRepository.findOne(resultsDto.getId());
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        
        electionProcedureService.validateCurrent(electionProcedureDto);
        
        ElectionRound currentRound = ElectionRound.valueOf(electionProcedureDto.getRound());
        
        // Έλεγχος προηγούμενης αποστολής αποτελεσμάτων.
        if (currentRound == ElectionRound.FIRST && electionDepartment.getSubmittedFirst() == YesNoEnum.YES
            || currentRound == ElectionRound.SECOND && electionDepartment.getSubmittedSecond() == YesNoEnum.YES) {
            throw new DpValidationException("RESULTS_SUBMITTED", messageSourceProvider.getMessage("results.error.resultssubmitted"));
        }
        
        // Validation αποτελεσμάτων
        validateResults(resultsDto);
        
        // Αν στο εκλογικό τμήμα δεν επιτρέπεται υποβολή αποτελεσμάτων χωρίς τον έλεγχο διαπιστεύσεων
        if (!electionDepartmentService.allowsInconsistentSubmission(resultsDto.getId())) {
            // Το σύνολο ψηφισάντων πρέπει να συμφωνεί με το πλήθος διαπιστεύσεων
            Long voterCount = voterService.getVoterCountByElectionDepartmentId(resultsDto.getId(), YesNoEnum.YES);
            Integer totalVotes = resultsDto.getTotalVotes() == null ? 0 : resultsDto.getTotalVotes();
            
            if (voterCount.intValue() != totalVotes) {
                throw new DpValidationException("INCONSISTENT_VOTES", messageSourceProvider.getMessage("results.error.inconsistentvotes"));
            }
        }
        
        dtoToEntity(resultsDto, electionDepartment, currentRound);
        
        // Ορισμός ενδείξεων αποστολής αποτελεσμάτων για κάθε γύρο εκλ. διαδικασίας.
        if (currentRound == ElectionRound.FIRST) {
            electionDepartment.setSubmittedFirst(YesNoEnum.YES);
            electionDepartment.setSubmissionDateFirst(new Date());
        } else if (currentRound == ElectionRound.SECOND) {
            electionDepartment.setSubmittedSecond(YesNoEnum.YES);
            electionDepartment.setSubmissionDateSecond(new Date());
        }
        
        electionDepartmentRepository.save(electionDepartment);
        
        return findResults(electionDepartment.getId());
    }
    
    private void dtoToEntity(ResultsDto resultsDto, ElectionDepartment electionDepartment, ElectionRound round) {
        if (resultsDto == null) return;
        
        if (electionDepartment == null) {
            electionDepartment = new ElectionDepartment();
        }
        
        BeanUtils.copyProperties(resultsDto, electionDepartment, "code", "name");
        
        if (round == ElectionRound.FIRST) {
            electionDepartment.setTotalVotesFirst(resultsDto.getTotalVotes());
            electionDepartment.setValidVotesFirst(resultsDto.getValidVotes());
            electionDepartment.setInvalidVotesFirst(resultsDto.getInvalidVotes());
            electionDepartment.setWhiteVotesFirst(resultsDto.getWhiteVotes());
            electionDepartment.setCandidateOneVotesFirst(resultsDto.getCandidateOneVotes());
            electionDepartment.setCandidateTwoVotesFirst(resultsDto.getCandidateTwoVotes());
            electionDepartment.setCandidateThreeVotesFirst(resultsDto.getCandidateThreeVotes());
            electionDepartment.setCandidateFourVotesFirst(resultsDto.getCandidateFourVotes());
            electionDepartment.setCandidateFiveVotesFirst(resultsDto.getCandidateFiveVotes());
            electionDepartment.setCandidateSixVotesFirst(resultsDto.getCandidateSixVotes());
            electionDepartment.setCandidateSevenVotesFirst(resultsDto.getCandidateSevenVotes());
            electionDepartment.setCandidateEightVotesFirst(resultsDto.getCandidateEightVotes());
            electionDepartment.setCandidateNineVotesFirst(resultsDto.getCandidateNineVotes());
            electionDepartment.setCandidateTenVotesFirst(resultsDto.getCandidateTenVotes());
        } else if (round == ElectionRound.SECOND) {
            electionDepartment.setTotalVotesSecond(resultsDto.getTotalVotes());
            electionDepartment.setValidVotesSecond(resultsDto.getValidVotes());
            electionDepartment.setInvalidVotesSecond(resultsDto.getInvalidVotes());
            electionDepartment.setWhiteVotesSecond(resultsDto.getWhiteVotes());
            electionDepartment.setCandidateOneVotesSecond(resultsDto.getCandidateOneVotes());
            electionDepartment.setCandidateTwoVotesSecond(resultsDto.getCandidateTwoVotes());
            electionDepartment.setCandidateThreeVotesSecond(resultsDto.getCandidateThreeVotes());
            electionDepartment.setCandidateFourVotesSecond(resultsDto.getCandidateFourVotes());
            electionDepartment.setCandidateFiveVotesSecond(resultsDto.getCandidateFiveVotes());
            electionDepartment.setCandidateSixVotesSecond(resultsDto.getCandidateSixVotes());
            electionDepartment.setCandidateSevenVotesSecond(resultsDto.getCandidateSevenVotes());
            electionDepartment.setCandidateEightVotesSecond(resultsDto.getCandidateEightVotes());
            electionDepartment.setCandidateNineVotesSecond(resultsDto.getCandidateNineVotes());
            electionDepartment.setCandidateTenVotesSecond(resultsDto.getCandidateTenVotes());
        }
    }
    
    private void validateResults(ResultsDto resultsDto) {
        if (resultsDto == null) return;
        
        Integer totalVotes = resultsDto.getTotalVotes() != null ? resultsDto.getTotalVotes() : 0;
        Integer validVotes = resultsDto.getValidVotes() != null ? resultsDto.getValidVotes() : 0;
        Integer invalidVotes = resultsDto.getInvalidVotes() != null ? resultsDto.getInvalidVotes() : 0;
        Integer whiteVotes = resultsDto.getWhiteVotes() != null ? resultsDto.getWhiteVotes() : 0;
        
        Integer candidateOneVotes = resultsDto.getCandidateOneVotes() != null ? resultsDto.getCandidateOneVotes() : 0;
        Integer candidateTwoVotes = resultsDto.getCandidateTwoVotes() != null ? resultsDto.getCandidateTwoVotes() : 0;
        Integer candidateThreeVotes = resultsDto.getCandidateThreeVotes() != null ? resultsDto.getCandidateThreeVotes() : 0;
        Integer candidateFourVotes = resultsDto.getCandidateFourVotes() != null ? resultsDto.getCandidateFourVotes() : 0;
        Integer candidateFiveVotes = resultsDto.getCandidateFiveVotes() != null ? resultsDto.getCandidateFiveVotes() : 0;
        Integer candidateSixVotes = resultsDto.getCandidateSixVotes() != null ? resultsDto.getCandidateSixVotes() : 0;
        Integer candidateSevenVotes = resultsDto.getCandidateSevenVotes() != null ? resultsDto.getCandidateSevenVotes() : 0;
        Integer candidateEightVotes = resultsDto.getCandidateEightVotes() != null ? resultsDto.getCandidateEightVotes() : 0;
        Integer candidateNineVotes = resultsDto.getCandidateNineVotes() != null ? resultsDto.getCandidateNineVotes() : 0;
        Integer candidateTenVotes = resultsDto.getCandidateTenVotes() != null ? resultsDto.getCandidateTenVotes() : 0;
        
        Integer partialSum = validVotes + invalidVotes + whiteVotes;
        
        Integer candidateSum = candidateOneVotes + candidateTwoVotes + candidateThreeVotes + candidateFourVotes + candidateFiveVotes +
            candidateSixVotes + candidateSevenVotes + candidateEightVotes + candidateNineVotes + candidateTenVotes;
        
        if (!partialSum.equals(totalVotes)) {
            throw new DpValidationException("TOTAL_SUM_ERROR", messageSourceProvider.getMessage("results.error.totalsum"));
        }
        
        if (!candidateSum.equals(validVotes)) {
            throw new DpValidationException("CANDIDATE_SUM_ERROR", messageSourceProvider.getMessage("results.error.candidatesum"));
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void uploadFile(Long electionDepartmentId, MultipartFile file, AttachmentType attachmentType) {
        if (attachmentType == null) return;
        
        ElectionDepartment electionDepartment = electionDepartmentRepository.findOne(electionDepartmentId);
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        
        electionProcedureService.validateCurrent(electionProcedureDto);
        
        ElectionRound currentRound = ElectionRound.valueOf(electionProcedureDto.getRound());
        
        try {
            if (file.getBytes() != null && file.getBytes().length > MAX_FILE_SIZE) {
                throw new DpValidationException("INVALID_FILE_SIZE",
                    messageSourceProvider.getMessage("results.error.invalidfilesize", new Object[] { MAX_FILE_SIZE / 1024 / 1024 }));
            }
            
            switch (attachmentType) {
                case RESULTS:
                    if (currentRound == ElectionRound.FIRST) {
                        electionDepartment.setAttachmentFirst(file.getBytes());
                        electionDepartment.setAttachmentNameFirst(file.getOriginalFilename());
                    } else if (currentRound == ElectionRound.SECOND) {
                        electionDepartment.setAttachmentSecond(file.getBytes());
                        electionDepartment.setAttachmentNameSecond(file.getName());
                    }
                    break;
                case CASHIER:
                    if (currentRound == ElectionRound.FIRST) {
                        electionDepartment.setAttachmentTwoFirst(file.getBytes());
                        electionDepartment.setAttachmentTwoNameFirst(file.getOriginalFilename());
                    } else if (currentRound == ElectionRound.SECOND) {
                        electionDepartment.setAttachmentTwoSecond(file.getBytes());
                        electionDepartment.setAttachmentTwoNameSecond(file.getName());
                    }
                    break;
            }
            
            electionDepartmentRepository.save(electionDepartment);
        } catch (IOException e) {
            throw new DpRuntimeException("FILE_NOT_SAVED", messageSourceProvider.getMessage("results.error.filenotuploaded"));
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void downloadFile(Long electionDepartmentId, ServletOutputStream outputStream, AttachmentType attachmentType) {
        if (attachmentType == null) return;
        ElectionDepartment electionDepartment = electionDepartmentRepository.findOne(electionDepartmentId);
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        
        electionProcedureService.validateCurrent(electionProcedureDto);
        
        ElectionRound currentRound = ElectionRound.valueOf(electionProcedureDto.getRound());
        
        try {
            switch (attachmentType) {
                case RESULTS:
                    if (currentRound == ElectionRound.FIRST) {
                        outputStream.write(electionDepartment.getAttachmentFirst());
                    } else if (currentRound == ElectionRound.SECOND) {
                        outputStream.write(electionDepartment.getAttachmentSecond());
                    }
                    break;
                case CASHIER:
                    if (currentRound == ElectionRound.FIRST) {
                        outputStream.write(electionDepartment.getAttachmentTwoFirst());
                    } else if (currentRound == ElectionRound.SECOND) {
                        outputStream.write(electionDepartment.getAttachmentTwoSecond());
                    }
                    break;
            }
        } catch (IOException e) {
            logger.error(messageSourceProvider.getMessage("results.error.imagedownloadfail"), e);
            
            throw new DpValidationException("IMAGE_DOWNLOAD_FAIL",
                messageSourceProvider.getMessage("results.error.imagedownloadfail"));
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public byte[] findAttachment(Long electionDepartmentId, ElectionRound electionRound) {
        if (electionRound == null) return null;
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment)
            .where(electionDepartment.id.eq(electionDepartmentId));
        
        switch (electionRound) {
            case FIRST:
                return query.singleResult(electionDepartment.attachmentFirst);
            case SECOND:
                return query.singleResult(electionDepartment.attachmentSecond);
            default:
                return null;
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public byte[] findAttachmentTwo(Long electionDepartmentId, ElectionRound electionRound) {
        if (electionRound == null) return null;
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment)
            .where(electionDepartment.id.eq(electionDepartmentId));
        
        switch (electionRound) {
            case FIRST:
                return query.singleResult(electionDepartment.attachmentTwoFirst);
            case SECOND:
                return query.singleResult(electionDepartment.attachmentTwoSecond);
            default:
                return null;
        }
    }
}
