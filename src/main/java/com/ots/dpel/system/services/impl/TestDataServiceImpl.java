package com.ots.dpel.system.services.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.config.beans.SolrConfigBean;
import com.ots.dpel.ep.dto.ElectorDto;
import com.ots.dpel.ep.dto.VoterDto;
import com.ots.dpel.ep.services.ElectorService;
import com.ots.dpel.ep.services.VoterService;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.system.services.IndexingService;
import com.ots.dpel.system.services.TestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TestDataServiceImpl implements TestDataService {
    
    @Autowired
    private SolrConfigBean solrProperties;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private ElectorService electorService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    private final Integer CHUNK_SIZE = 3000;
    
    private final String TEST_DATA_TEXT = "Test data - created by an automated process";
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void createVoters(Integer count) {
        createVoters(count, CHUNK_SIZE);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void createVoters(Integer count, Integer chunksize) {
        if (count == null || count == 0) return;
    
        Integer chunkSize = chunksize != null && chunksize != 0 ? chunksize : CHUNK_SIZE;
        chunkSize = count > CHUNK_SIZE ? chunkSize : count;
        
        int pages = count / chunkSize;
        
        List<Long> electionDepartmentIds = electionDepartmentService.findUnsubmittedIds();
    
        for (int i = 0; i < pages; i++) {
            Pageable pageable = new PageRequest(i, chunkSize);
            
            // Ανάκτηση σελίδας εκλογέων είτε από τον Index είτε από τη βάση
            Page<ElectorDto> electorPage = solrProperties.getEnabled() && solrProperties.isCoreOnline(IndexingService.ELECTORS_INDEX)
                ? electorService.findGenericElectorsFromSolr(pageable)
                : electorService.findGenericElectors(pageable);
            
            List<VoterDto> voters = Lists.transform(electorPage.getContent(), convertElectorToVoter(electionDepartmentIds));
    
            // Αποθήκευση ψηφισάντων
            for (VoterDto voter: voters) {
                voterService.saveVoter(voter);
            }
    
            if (!electorPage.hasNext()) break;
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void undoVotes(Integer count) {
        undoVotes(count, CHUNK_SIZE);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void undoVotes(Integer count, Integer chunksize) {
        if (count == null || count == 0) return;
    
        Integer chunkSize = chunksize != null && chunksize != 0 ? chunksize : CHUNK_SIZE;
        chunkSize = count > CHUNK_SIZE ? chunkSize : count;
    
        int pages = count / chunkSize;
    
        for (int i = 0; i < pages; i++) {
            Pageable pageable = new PageRequest(i, chunkSize);
        
            // Ανάκτηση σελίδας ψηφισάντων
            Page<VoterDto> voterPage = voterService.findVotersFromUnsubmitted(YesNoEnum.YES, pageable);
            List<VoterDto> voters = voterPage.getContent();
        
            // Αποθήκευση ψηφισάντων
            for (VoterDto voter: voters) {
                voterService.undoVote(voter.getId(), TEST_DATA_TEXT);
            }
            
            if (!voterPage.hasNext()) break;
        }
    }
    
    private Function<ElectorDto, VoterDto> convertElectorToVoter(final List<Long> electionDepartmentIds) {
        return new Function<ElectorDto, VoterDto>() {
            @Override
            public VoterDto apply(ElectorDto elector) {
                VoterDto voter = new VoterDto();
                
                voter.setEklSpecialNo(elector.getEklSpecialNo());
                voter.setLastName(elector.getLastName());
                voter.setFirstName(elector.getFirstName());
                voter.setFatherFirstName(elector.getFatherFirstName());
                voter.setMotherFirstName(elector.getMotherFirstName());
                voter.setBirthDate(elector.getBirthDate());
                voter.setCellphone(TEST_DATA_TEXT);
                
                // Ανάκτηση id Εκλ. Τμήματος τυχαία από τη λίστα Εκλ. Τμημάτων
                int randomIdx = ThreadLocalRandom.current().nextInt(electionDepartmentIds.size());
                voter.setElectionDepartmentId(electionDepartmentIds.get(randomIdx));
                
                return voter;
            }
        };
    }
    
}
