package com.ots.dpel.ep.services.impl;

import com.ots.dpel.config.beans.SolrConfigBean;
import com.ots.dpel.ep.args.VerificationArgs;
import com.ots.dpel.ep.core.enums.VerificationSearchType;
import com.ots.dpel.ep.dto.ElectorDto;
import com.ots.dpel.ep.dto.VerificationDto;
import com.ots.dpel.ep.dto.VoterDto;
import com.ots.dpel.ep.services.ElectorService;
import com.ots.dpel.ep.services.VerificationService;
import com.ots.dpel.ep.services.VoterService;
import com.ots.dpel.ext.dto.PreregistrationDto;
import com.ots.dpel.ext.services.PreregistrationService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.global.utils.UserUtils;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionProcedureService;
import com.ots.dpel.system.services.IndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class VerificationServiceImpl implements VerificationService {
    
    @Autowired
    private SolrConfigBean solrProperties;
    
    @Autowired
    private ElectorService electorService;
    
    @Autowired
    private PreregistrationService preregistrationService;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    private static final String ERROR_VERIFY_NO_RECORD_FOUND = "NO_RECORD_FOUND";
    private static final String ERROR_VERIFY_MULTIPLE_RECORDS_FOUND = "MULTIPLE_RECORDS_FOUND";
    private static final String ERROR_VERIFY_ILLEGAL_ARGS = "ILLEGAL_ARGS";
    private static final String ERROR_VERIFY_INVALID_ARGS_EKLSPECIALNO = "INVALID_ARGS_EKLSPECIALNO";
    private static final String ERROR_VERIFY_INVALID_ARGS_VOTER_VERIFICATION_NUMBER = "INVALID_ARGS_VOTER_VERIFICATION_NUMBER";
    private static final String ERROR_VERIFY_INVALID_ARGS_PERSONAL_INFO = "INVALID_ARGS_PERSONAL_INFO";
    
    private static final String ERROR_SAVEVOTER_NO_ELECTOR = "NO_ELECTOR";
    private static final String ERROR_SAVEVOTER_NO_ELECTION_DEPARTMENT_ID = "NO_ELECTION_DEPARTMENT_ID";
    private static final String ERROR_SAVEVOTER_INVALID_ELECTOR = "INVALID_ELECTOR";
    private static final String ERROR_SAVEVOTER_INVALID_PAYMENT = "INVALID_PAYMENT";
    public static final String ERROR_SAVEVOTER_CREATE_ALREADY_VOTED = "CREATE_ALREADY_VOTED";
    public static final String ERROR_SAVEVOTER_CREATE_DEPARTMENT_HAS_SUBMITTED = "CREATE_DEPARTMENT_HAS_SUBMITTED";
    public static final String ERROR_SAVEVOTER_CREATE_DUPLICATE_VERIFICATION_NUMBER = "CREATE_DUPLICATE_VERIFICATION_NUMBER";
    public static final String ERROR_SAVEVOTER_UPDATE_VOTED_IN_ANOTHER_DEPARTMENT = "UPDATE_VOTED_IN_ANOTHER_DEPARTMENT";
    
    public static final String ERROR_UNDOVOTE_VOTER_IS_NULL = "VOTER_IS_NULL";
    public static final String ERROR_UNDOVOTE_NOT_VOTED = "NOT_VOTED";
    public static final String ERROR_UNDOVOTE_VOTED_IN_ANOTHER_DEPARTMENT = "VOTED_IN_ANOTHER_DEPARTMENT";
    public static final String ERROR_UNDOVOTE_DEPARTMENT_HAS_SUBMITTED = "DEPARTMENT_HAS_SUBMITTED";
    
    
    private static final Double DEFAULT_PAYMENT = 3D;
    
    
    @Override
    public VerificationDto verify(VerificationArgs args) {
        
        if(args == null) {
            throw new DpValidationException(ERROR_VERIFY_ILLEGAL_ARGS, messageSourceProvider.getMessage("ep.verification.verify.error.illegalArgs"));
        }
        
        //Αν δεν έχει δοθεί τύπος αναζήτησης
        if(DpTextUtils.isEmpty(args.getSearchType())) {
            if(!DpTextUtils.isEmpty(args.getEklSpecialNo())) {
                //Αν έχει δοθεί ειδικός εκλογικός αριθμός, η αναζήτηση γίνεται μ' αυτόν.
                args.setSearchType(VerificationSearchType.EKLSPECIALNO.name());
            }
            else if(args.getVoterVerificationNumber() != null) {
                //Αλλιώς, αν έχει δοθεί αριθμός διαπίστευσης, η αναζήτηση γίνεται μ' αυτόν.
                args.setSearchType(VerificationSearchType.VOTER_VERIFICATION_NUMBER.name());
                
                //Ορισμός του εκλογικού τμήματος του χρήστη, αν αυτό είναι κενό στα args
                if (args.getVoterElectionDepartmentId() == null) {
                    args.setVoterElectionDepartmentId(UserUtils.getUserElectionDepartmentId());
                }
                
                //Ορισμός της τρέχουσας εκλογικής διαδικασίας και του γύρου στα args
                ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
                if(currentElectionProcedureDto != null) {
                    args.setVoterElectionProcedureId(currentElectionProcedureDto.getId());
                    args.setVoterRound(currentElectionProcedureDto.getRound());
                }
            }
            else {
                //Αλλιώς, γίνεται με τα προσωπικά στοιχεία.
                args.setSearchType(VerificationSearchType.PERSONAL_INFO.name());
            }
        }
        
        if(!DpTextUtils.isEmpty(args.getSearchType()) &&
                (args.getSearchType().equals(VerificationSearchType.EKLSPECIALNO.name()) ||
                        args.getSearchType().equals(VerificationSearchType.VOTER_VERIFICATION_NUMBER.name()) ||
                        args.getSearchType().equals(VerificationSearchType.PERSONAL_INFO.name()))) {
            
            //Εξασφάλιση εγκυρότητας κριτηρίων αναζήτησης
            validateArgs(args);
            
            //Αφαίρεση κενών χαρακτήρων από την αρχή συγκεκριμένων κριτηρίων αναζήτησης
            trimLeadingWhitespacesFromArgs(args);
            
            Pageable pageable = new PageRequest(0, 5);
            Page<ElectorDto> matchingElectorsPage = null;
            
            if (solrProperties.getEnabled() && solrProperties.isCoreOnline(IndexingService.ELECTORS_INDEX) && !args.getSearchType().equals(VerificationSearchType.VOTER_VERIFICATION_NUMBER.name())) {
                matchingElectorsPage = electorService.findMatchingElectorsFromSolr(args, pageable);
            }
            else {
                matchingElectorsPage = electorService.findMatchingElectors(args, pageable);
            }
            
            List<ElectorDto> matchingElectors = matchingElectorsPage.getContent();
            
            if(matchingElectors == null || matchingElectors.size() == 0) {
                throw new DpValidationException(ERROR_VERIFY_NO_RECORD_FOUND, messageSourceProvider.getMessage("ep.verification.verify.error.noRecordFound"));
            }
            else if(matchingElectors.size() > 1) {
                throw new DpValidationException(ERROR_VERIFY_MULTIPLE_RECORDS_FOUND, messageSourceProvider.getMessage("ep.verification.verify.error.multipleRecordsFound"));
            }
            else {
                ElectorDto electorDto = matchingElectors.get(0);
                
                VerificationDto verificationDto = new VerificationDto();
                
                //Ορισμός πεδίων από τον elector
                verificationDto.setElectorId(electorDto.getId());
                verificationDto.setLastName(electorDto.getLastName());
                verificationDto.setFirstName(electorDto.getFirstName());
                verificationDto.setFatherFirstName(electorDto.getFatherFirstName());
                verificationDto.setMotherFirstName(electorDto.getMotherFirstName());
                verificationDto.setBirthDate(electorDto.getBirthDate());
                verificationDto.setMunicipalRecordNo(electorDto.getMunicipalRecordNo());
                verificationDto.setMunicipalUnitDescription(electorDto.getMunicipalUnitDescription());
                verificationDto.setEklSpecialNo(electorDto.getEklSpecialNo());
                
                //Ανάκτηση πιθανής προεγγραφής με το δεδομένο ειδικό εκλογικό αριθμό
                try {
                    Long eklSpecialNoNumber = Long.valueOf(electorDto.getEklSpecialNo());
                    PreregistrationDto preregistrationDto = preregistrationService.findByEklSpecialNo(eklSpecialNoNumber);
                    if(preregistrationDto != null) {
                        verificationDto.setHasPreregistrationRecord(true);
                        verificationDto.setPreregistrationMember(!preregistrationDto.getNotMember());
                        
                        //Τα συμπληρωματικά πεδία της προεγγραφής μπαίνουν στα πεδία voterAddress, voterAddressNo κτλ.
                        //Αν υπάρχει voter (παρακάτω), τότε αυτά θα αντικατασταθούν από τις τιμές που έρχονται από τον voter.
                        verificationDto.setVoterAddress(preregistrationDto.getAddress());
                        verificationDto.setVoterAddressNo(preregistrationDto.getAddressNo());
                        verificationDto.setVoterCity(preregistrationDto.getArea());
                        verificationDto.setVoterPostalCode(preregistrationDto.getPostalCode());
                        verificationDto.setVoterCountry(preregistrationDto.getCountry());
                        verificationDto.setVoterCellphone(preregistrationDto.getCellphone());
                        verificationDto.setVoterEmail(preregistrationDto.getEmail());
                        verificationDto.setVoterIdType(preregistrationDto.getIdType());
                        verificationDto.setVoterIdNumber(preregistrationDto.getIdNumber());
                        
                        //Ποσό πληρωμής από την προεγγραφή
                        //Αν υπάρχει voter (παρακάτω), τότε θα αντικατασταθεί από την τιμή που έρχεται από τον voter.
                        verificationDto.setVoterPayment(preregistrationDto.getPayment().doubleValue());
                    }
                }
                catch(NumberFormatException ex) {
                
                }
                
                //Ανάκτηση πιθανής πληροφορίας ψηφίσαντα
                VoterDto voterDto = voterService.findVotedByElectorIdAndCurrentElectionProcedureRound(electorDto.getId());
                if(voterDto != null) {
                    verificationDto.setVoteDateTime(voterDto.getVoteDateTime());
                    
                    if(voterDto.getElectionDepartmentBasicDto() != null) {
                        verificationDto.setHasVoterRecord(true);
                        verificationDto.setVoterId(voterDto.getId());
                        verificationDto.setVoteElectionDepartmentName(voterDto.getElectionDepartmentBasicDto().getDisplayName());
                        verificationDto.setVoterAddress(voterDto.getAddress());
                        verificationDto.setVoterAddressNo(voterDto.getAddressNo());
                        verificationDto.setVoterCity(voterDto.getCity());
                        verificationDto.setVoterPostalCode(voterDto.getPostalCode());
                        verificationDto.setVoterCountry(voterDto.getCountry());
                        verificationDto.setVoterCellphone(voterDto.getCellphone());
                        verificationDto.setVoterEmail(voterDto.getEmail());
                        verificationDto.setVoterIdType(voterDto.getIdType());
                        verificationDto.setVoterIdNumber(voterDto.getIdNumber());
                        verificationDto.setVoterMember(voterDto.getMember());
                        verificationDto.setVoterPayment(voterDto.getPayment());
                        verificationDto.setVoterElectionDepartmentId(voterDto.getElectionDepartmentId());
                        verificationDto.setVoterVerificationNumber(voterDto.getVerificationNumber());
                    }
                }
                
                //Ορισμός του default ποσού πληρωμής.
                verificationDto.setDefaultPayment(DEFAULT_PAYMENT);
                
                return verificationDto;
            }
        }
        else {
            throw new DpValidationException(ERROR_VERIFY_ILLEGAL_ARGS, messageSourceProvider.getMessage("ep.verification.verify.error.illegalArgs"));
        }
        
    }
    
    private void validateArgs(VerificationArgs args) {
        if(args != null && !DpTextUtils.isEmpty(args.getSearchType())) {
            if(args.getSearchType().equals(VerificationSearchType.EKLSPECIALNO.name())) {
                //Συμπληρωμένος ειδικός εκλογικός αριθμός
                if(DpTextUtils.isEmpty(args.getEklSpecialNo())) {
                    throw new DpValidationException(ERROR_VERIFY_INVALID_ARGS_EKLSPECIALNO, messageSourceProvider.getMessage("ep.verification.verify.error.invalidArgsEklSpecialNo"));
                }
            }
            else if(args.getSearchType().equals(VerificationSearchType.VOTER_VERIFICATION_NUMBER.name())) {
                //Συμπληρωμένος αριθμός διαπίστευσης και εκλογικό τμήμα
                if(args.getVoterVerificationNumber() == null || args.getVoterElectionDepartmentId() == null) {
                    if(UserUtils.getUserElectionDepartmentId() == null) {
                        throw new DpValidationException(ERROR_VERIFY_INVALID_ARGS_VOTER_VERIFICATION_NUMBER, messageSourceProvider.getMessage("ep.verification.verify.error.invalidArgsVoterVerificationNumber.numberAndDepartment"));
                    }
                    else {
                        throw new DpValidationException(ERROR_VERIFY_INVALID_ARGS_VOTER_VERIFICATION_NUMBER, messageSourceProvider.getMessage("ep.verification.verify.error.invalidArgsVoterVerificationNumber.numberOnly"));
                    }
                }
                else if(args.getVoterElectionProcedureId() == null || DpTextUtils.isEmpty(args.getVoterRound())) {
                    throw new DpValidationException(ERROR_VERIFY_INVALID_ARGS_VOTER_VERIFICATION_NUMBER, messageSourceProvider.getMessage("ep.verification.verify.error.invalidArgsVoterVerificationNumber.noElectionProcedure"));
                }
            }
            else if(args.getSearchType().equals(VerificationSearchType.PERSONAL_INFO.name())) {
                
                Boolean validPersonalInfoArgs = true;
                
                //Συμπληρωμένα επώνυμο, όνομα, πατρώνυμο, μητρώνυμο με τουλάχιστον 2 χαρακτήρες
                if(DpTextUtils.isEmpty(args.getLastName()) || args.getLastName().length() < 2) {
                    validPersonalInfoArgs = false;
                }
                if(DpTextUtils.isEmpty(args.getFirstName()) || args.getFirstName().length() < 2) {
                    validPersonalInfoArgs = false;
                }
                if(DpTextUtils.isEmpty(args.getFatherFirstName()) || args.getFatherFirstName().length() < 2) {
                    validPersonalInfoArgs = false;
                }
                if(DpTextUtils.isEmpty(args.getMotherFirstName()) || args.getMotherFirstName().length() < 2) {
                    validPersonalInfoArgs = false;
                }
                
                //Συμπληρωμένο έτος γέννησης
                if(args.getBirthYear() == null) {
                    validPersonalInfoArgs = false;
                }
                
                if(!validPersonalInfoArgs) {
                    throw new DpValidationException(ERROR_VERIFY_INVALID_ARGS_PERSONAL_INFO, messageSourceProvider.getMessage("ep.verification.verify.error.invalidArgsPersonalInfo"));
                }
            }
        }
    }
    
    private void trimLeadingWhitespacesFromArgs(VerificationArgs args) {
        
        if(args != null) {
            if(args.getEklSpecialNo() != null) {
                args.setEklSpecialNo(StringUtils.trimLeadingWhitespace(args.getEklSpecialNo()));
            }
            if(args.getLastName() != null) {
                args.setLastName(StringUtils.trimLeadingWhitespace(args.getLastName()));
            }
            if(args.getFirstName() != null) {
                args.setFirstName(StringUtils.trimLeadingWhitespace(args.getFirstName()));
            }
            if(args.getFatherFirstName() != null) {
                args.setFatherFirstName(StringUtils.trimLeadingWhitespace(args.getFatherFirstName()));
            }
            if(args.getMotherFirstName() != null) {
                args.setMotherFirstName(StringUtils.trimLeadingWhitespace(args.getMotherFirstName()));
            }
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public VoterDto saveVoter(VoterDto voterDto) {
        
        if(voterDto == null) {
            return null;
        }
        
        if(voterDto.getElectorId() == null || DpTextUtils.isEmpty(voterDto.getEklSpecialNo())) {
            //Πρέπει να έχει δοθεί id εκλογέα και ειδικός εκλογικός αριθμός
            throw new DpValidationException(ERROR_SAVEVOTER_NO_ELECTOR, messageSourceProvider.getMessage("ep.verification.saveVoter.error.noElector"));
        }
        
        if(voterDto.getElectionDepartmentId() == null) {
            //Πρέπει να έχει δοθεί id εκλογικού τμήματος
            throw new DpValidationException(ERROR_SAVEVOTER_NO_ELECTION_DEPARTMENT_ID, messageSourceProvider.getMessage("ep.verification.saveVoter.error.noElectionDepartmentId"));
        }
        
        //Ανάκτηση εκλογέα με βάση το id
        ElectorDto electorDto = null;
        
        if (solrProperties.getEnabled() && solrProperties.isCoreOnline(IndexingService.ELECTORS_INDEX)) {
            electorDto = electorService.findElectorBasicFromSolr(voterDto.getElectorId());
        }
        else {
            electorDto = electorService.findElectorBasic(voterDto.getElectorId());
        }
        
        if(electorDto == null) {
            //Δε βρέθηκε εκλογέας με το δεδομένο id
            throw new DpValidationException(ERROR_SAVEVOTER_NO_ELECTOR, messageSourceProvider.getMessage("ep.verification.saveVoter.error.noElector"));
        }
        
        if(!DpTextUtils.isEmpty(electorDto.getEklSpecialNo()) && !DpTextUtils.isEmpty(voterDto.getEklSpecialNo())) {
            voterDto.setEklSpecialNo(String.format("%013d", Long.valueOf(voterDto.getEklSpecialNo())));
            if(!electorDto.getEklSpecialNo().equals(voterDto.getEklSpecialNo())) {
                //Ο δεδομένος ειδικός εκλογικός αριθμός δεν ταυτίζεται με αυτόν του εκλογέα
                throw new DpValidationException(ERROR_SAVEVOTER_INVALID_ELECTOR, messageSourceProvider.getMessage("ep.verification.saveVoter.error.invalidElector"));
            }
        }
        
        if(voterDto.getPayment() == null || voterDto.getPayment() < DEFAULT_PAYMENT) {
            //Το ποσό πληρωμής είναι κενό ή μικρότερο από το default
            throw new DpValidationException(ERROR_SAVEVOTER_INVALID_PAYMENT, messageSourceProvider.getMessage("ep.verification.saveVoter.error.invalidPayment", new Object[]{DEFAULT_PAYMENT.intValue()}));
        }
        
        //Αντιγραφή στοιχείων από τον elector
        voterDto.setLastName(electorDto.getLastName());
        voterDto.setFirstName(electorDto.getFirstName());
        voterDto.setFatherFirstName(electorDto.getFatherFirstName());
        voterDto.setMotherFirstName(electorDto.getMotherFirstName());
        voterDto.setBirthDate(electorDto.getBirthDate());
        if(electorDto.getBirthDate() != null) {
            voterDto.setBirthYear(DpDateUtils.getDateYear(electorDto.getBirthDate()).intValue());
        }
        
        //Αποθήκευση ψηφίσαντα
        return voterService.saveVoter(voterDto);
    }
}
