package com.ots.dpel.management.services.impl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.auth.services.UserService;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.domain.QAdminUnit;
import com.ots.dpel.common.core.domain.QCountry;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.dto.ElectionDepartmentVoterDto;
import com.ots.dpel.ep.services.VoterService;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.services.VolunteerService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.management.args.ElectionDepartmentArgs;
import com.ots.dpel.management.core.domain.Contribution;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ContributionDto;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.ElectionDepartmentDto;
import com.ots.dpel.management.dto.ElectionDepartmentInfoDto;
import com.ots.dpel.management.dto.ElectionDepartmentResultDto;
import com.ots.dpel.management.dto.ElectionDepartmentSnapshotDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.dto.QElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.QElectionDepartmentDto;
import com.ots.dpel.management.dto.list.ElectionDepartmentIndexListDto;
import com.ots.dpel.management.dto.list.ElectionDepartmentListDto;
import com.ots.dpel.management.dto.list.QElectionDepartmentIndexListDto;
import com.ots.dpel.management.dto.list.QElectionDepartmentListDto;
import com.ots.dpel.management.persistence.ElectionDepartmentRepository;
import com.ots.dpel.management.services.CandidateService;
import com.ots.dpel.management.services.ContributionService;
import com.ots.dpel.management.services.ElectionCenterService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.ots.dpel.management.dto.list.ElectionDepartmentIndexListDto.CONTRIBUTION_DATA_NEWLINE_WEB;
import static com.ots.dpel.management.predicates.ElectionDepartmentPredicates.createElectionDepartmentIndexPredicate;

@Service
public class ElectionDepartmentServiceImpl implements ElectionDepartmentService {
    
    private static final Logger logger = LogManager.getLogger(ElectionDepartmentServiceImpl.class);
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ElectionDepartmentRepository electionDepartmentRepository;
    
    @Autowired
    private ContributionService contributionService;
    
    @Autowired
    private ElectionCenterService electionCenterService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired @Lazy
    private VoterService voterService;
    
    @Autowired
    private CandidateService candidateService;
    
    @Autowired @Lazy
    private UserService userService;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ElectionDepartmentDto findElectionDepartment(Long id) {
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment).where(electionDepartment.id.eq(id));
        
        Expression<ElectionDepartmentDto> expression = getElectionDepartmentDtoConstructorExpression(electionDepartment);
        
        ElectionDepartmentDto electionDepartmentDto = query.singleResult(expression);
        
        electionDepartmentDto.setContributionDtos(contributionService.getContributionsByElectionDepartment(electionDepartmentDto.getId()));
        
        return electionDepartmentDto;
    }
    
    private Expression getElectionDepartmentDtoConstructorExpression(QElectionDepartment electionDepartment) {
        return ConstructorExpression.create(ElectionDepartmentDto.class,
                electionDepartment.id,
                electionDepartment.electionCenterId,
                electionDepartment.serialNo,
                electionDepartment.code,
                electionDepartment.name,
                electionDepartment.comments,
                electionDepartment.accessDifficulty,
                electionDepartment.allowInconsistentSubmission
        );
    }
    
    @Override
    public Expression getElectionDepartmentBasicDtoConstructorExpression(QElectionDepartment electionDepartment, QElectionCenter electionCenter, QAdminUnit municipality) {
        return ConstructorExpression.create(ElectionDepartmentBasicDto.class,
                electionDepartment.id,
                electionDepartment.electionCenterId,
                electionDepartment.serialNo,
                electionDepartment.code,
                electionDepartment.name,
                electionCenter.foreign,
                municipality.name.as("municipalityName"),
                electionCenter.name.as("electionCenterName")
        );
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ElectionDepartmentDto saveElectionDepartment(ElectionDepartmentDto electionDepartmentDto) {
        
        ElectionDepartment electionDepartment = electionDepartmentDto.getId() != null
                ? electionDepartmentRepository.findOne(electionDepartmentDto.getId())
                : new ElectionDepartment();
        
        dtoToEntity(electionDepartmentDto, electionDepartment);
        
        // Στη νέα εγγραφή γίνεται παραγωγή και ανέθεση του αύξοντα αριθμού και του κωδικού του εκλογικού τμήματος.
        if (electionDepartment.getId() == null) {
            ElectionDepartmentDto serialNoAndCodeDto = generateSerialNoAndCode(electionDepartment.getElectionCenterId());
            electionDepartment.setSerialNo(serialNoAndCodeDto.getSerialNo());
            electionDepartment.setCode(serialNoAndCodeDto.getCode());
        }
        
        contributionService.manageContributionsOfElectionDepartment(electionDepartment, electionDepartmentDto.getContributionDtos());
        
        validateElectionDepartment(electionDepartment);
        
        electionDepartmentRepository.save(electionDepartment);
        
        return findElectionDepartment(electionDepartment.getId());
    }
    
    private void dtoToEntity(ElectionDepartmentDto electionDepartmentDto, ElectionDepartment electionDepartment) {
        
        if (electionDepartmentDto == null) {
            return;
        }
        
        if (electionDepartment == null) {
            electionDepartment = new ElectionDepartment();
        }
        
        BeanUtils.copyProperties(electionDepartmentDto, electionDepartment, "contributionDtos", "accessDifficulty", "allowInconsistentSubmission");
        electionDepartment.setAccessDifficulty(YesNoEnum.of(electionDepartmentDto.getAccessDifficulty()));
        electionDepartment.setAllowInconsistentSubmission(YesNoEnum.of(electionDepartmentDto.getAllowInconsistentSubmission()));
    }
    
    private void validateElectionDepartment(ElectionDepartment electionDepartment) {
        String errorCode = null;
        String errorKey = null;
        
        // Υποχρεωτικά πεδία
        if (electionDepartment.getElectionCenterId() == null) {
            errorCode = "ELECTION_CENTER_EMPTY";
            errorKey = "mg.electiondepartment.error.electionCenter.empty";
        } else if (DpTextUtils.isEmpty(electionDepartment.getCode())) {
            errorCode = "CODE_EMPTY";
            errorKey = "mg.electiondepartment.error.code.empty";
        } else if (DpTextUtils.isEmpty(electionDepartment.getName())) {
            errorCode = "NAME_EMPTY";
            errorKey = "mg.electiondepartment.error.name.empty";
        }
        
        if (!DpTextUtils.isEmpty(errorCode)) {
            throw new DpValidationException(errorCode, messageSourceProvider.getMessage(errorKey));
        }
        
        // Έλεγχος εγγραφών εθελοντών σε πολλαπλά Ε.Τ. (δεν ισχύει για προέδρους και αναπληρωτές προέδρους)
        if (electionDepartment.getContributions() != null) {
            for (Contribution contribution : electionDepartment.getContributions()) {
                List<ElectionDepartmentBasicDto> eds = contributionService.getVolunteerElectionDepartments(contribution.getVolunteerId(), contribution.getRound(), electionDepartment.getId());
                
                for(ElectionDepartmentBasicDto ed: eds) {
                    
                    if(ed.getContributionType() == null || contribution.getType() == null ||
                            !ContributionType.isLeader(ed.getContributionType()) || !ContributionType.isLeader(contribution.getType())) {
                        VolunteerDto volunteerDto = volunteerService.findVolunteerBasic(contribution.getVolunteerId());
                        
                        throw new DpValidationException("VOLUNTEER_HAS_CONTRIBUTION",
                                messageSourceProvider.getMessage("mg.electiondepartment.error.volunteer.hasContribution",
                                        new Object[]{
                                                volunteerDto.getLastName() != null ? volunteerDto.getLastName() : "",
                                                volunteerDto.getFirstName() != null ? volunteerDto.getFirstName() : "",
                                                volunteerDto.getFatherFirstName() != null ? "(" + volunteerDto.getFatherFirstName() + ")" : ""
                                        }));
                    }
                    
                }
                
            }
        }
        
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void deleteElectionDepartment(Long id) {
        
        if(userService.electionDepartmentHasUsers(id)) {
            throw new DpValidationException("USERS_EXIST", messageSourceProvider.getMessage("mg.electiondepartment.delete.error.usersExist"));
        }
        else if(!voterService.getVoterCountByElectionDepartmentId(id, null).equals(0L)) {
            throw new DpValidationException("VOTERS_EXIST", messageSourceProvider.getMessage("mg.electiondepartment.delete.error.votersExist"));
        }
        
        electionDepartmentRepository.delete(id);
    }
    
    @Override
    public Page<ElectionDepartmentListDto> getIndex(SearchableArguments arguments, Pageable pageable) {
        ElectionDepartmentArgs args = (ElectionDepartmentArgs) arguments;
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment)
                .where(electionDepartment.electionCenterId.eq(args.getElectionCenterId()));
        
        FactoryExpression<ElectionDepartmentListDto> factoryExpression = new QElectionDepartmentListDto(
                electionDepartment.id,
                electionDepartment.serialNo,
                electionDepartment.code,
                electionDepartment.name,
                electionDepartment.comments);
        
        return electionDepartmentRepository.findAll(query, factoryExpression, pageable);
    }
    
    @Override
    public List<Long> findIdsByElectionCenter(Long electionCenterId) {
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(electionDepartment)
                .where(electionDepartment.electionCenterId.eq(electionCenterId))
                .list(electionDepartment.id);
    }
    
    @Override
    public List<Long> findAllIds() {
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        List<Long> ids = new JPAQuery(entityManager).from(electionDepartment)
            .where(electionDepartment.submittedFirst.eq(YesNoEnum.NO))
            .list(electionDepartment.id);
        
        return ids != null ? ids : Collections.<Long>emptyList();
    }
    
    @Override
    public List<Long> findUnsubmittedIds() {
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return Collections.emptyList();
        }
        
        ElectionRound electionRound = ElectionRound.valueOf(currentElectionProcedureDto.getRound());
        
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
        
        List<Long> ids = query.from(electionDepartment)
            .where(predicate)
            .list(electionDepartment.id);
        
        return ids != null ? ids : Collections.<Long>emptyList();
    }
    
    @Override
    public ElectionDepartmentDto generateSerialNoAndCode(Long electionCenterId) {
        
        //Ανάκτηση κωδικού εκλογικού κέντρου
        String electionCenterCode = electionCenterService.getCode(electionCenterId);
        if (electionCenterCode == null) {
            electionCenterCode = "";
        }
        
        //Ανάκτηση του μέγιστου αύξοντα αριθμού εκλογικού τμήματος του κέντρου
        Integer maxSerialNo = getMaxSerialNoByElectionCenter(electionCenterId);
        if (maxSerialNo == null) {
            maxSerialNo = 0;
        }
        
        //Αύξων αριθμός = Υπάρχων μέγιστος αύξων αριθμός + 1
        Integer serialNo = maxSerialNo + 1;
        
        //Κωδικός
        String code = electionCenterCode + "-" + String.format("%02d", serialNo);
        
        ElectionDepartmentDto electionDepartmentDto = new ElectionDepartmentDto();
        electionDepartmentDto.setSerialNo(serialNo);
        electionDepartmentDto.setCode(code);
        return electionDepartmentDto;
    }
    
    private Integer getMaxSerialNoByElectionCenter(Long electionCenterId) {
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(electionDepartment)
                .where(electionDepartment.electionCenterId.eq(electionCenterId))
                .singleResult(electionDepartment.serialNo.max());
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ElectionDepartmentInfoDto getElectionDepartmentInfo(Long id) {
        
        if (id == null) {
            return null;
        }
        
        String sql = "" +
                "SELECT " +
                "department.n_id AS id, " +
                "department.v_code AS code, " +
                "department.v_name AS name, " +
                "center.n_id AS electionCenterId, " +
                "center.v_code AS electionCenterCode, " +
                "center.v_name AS electionCenterName, " +
                "center.n_foreign AS foreignCountry, " +
                "municipality.v_name AS municipalityName " +
                "FROM dp.electiondepartment department " +
                "LEFT JOIN dp.electioncenter center ON center.n_id = department.n_electioncenter_id " +
                "LEFT JOIN dp.adminunit municipality ON center.n_municipality_id = municipality.n_id " +
                "WHERE department.n_id = :id";
        
        Query query = entityManager.createNativeQuery(sql, ElectionDepartmentInfoDto.class);
        query.setParameter("id", id);
        
        try {
            return (ElectionDepartmentInfoDto) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public List<ElectionDepartmentBasicDto> findAllBasic() {
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QAdminUnit municipality = new QAdminUnit("municipality");
        
        JPQLQuery query = new JPAQuery(entityManager);
    
        query.from(electionDepartment)
                .leftJoin(electionDepartment.electionCenter, electionCenter)
                .leftJoin(electionCenter.municipality, municipality)
                .orderBy(electionDepartment.name.asc());
        
        FactoryExpression<ElectionDepartmentBasicDto> factoryExpression = new QElectionDepartmentBasicDto(
                electionDepartment.id,
                electionDepartment.electionCenterId,
                electionDepartment.serialNo,
                electionDepartment.code,
                electionDepartment.name,
                electionCenter.foreign,
                municipality.name.as("municipalityName"),
                electionCenter.name.as("electionCenterName")
        );
        
        return electionDepartmentRepository.findList(query, factoryExpression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public List<ElectionDepartmentListDto> getByElectionCenter(Long electionCenterId) {
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment)
                .where(electionDepartment.electionCenterId.eq(electionCenterId))
                .orderBy(electionDepartment.code.asc());
        
        FactoryExpression<ElectionDepartmentListDto> factoryExpression = new QElectionDepartmentListDto(
                electionDepartment.id,
                electionDepartment.serialNo,
                electionDepartment.code,
                electionDepartment.name,
                electionDepartment.comments);
        
        return electionDepartmentRepository.findList(query, factoryExpression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public Page<ElectionDepartmentIndexListDto> getElectionDepartmentIndex(SearchableArguments arguments, Pageable pageable) {
        
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        QCountry country = new QCountry("country");
        QAdminUnit geographicalUnit = new QAdminUnit("geographicalUnit");
        QAdminUnit decentralAdmin = new QAdminUnit("decentralAdmin");
        QAdminUnit region = new QAdminUnit("region");
        QAdminUnit regionalUnit = new QAdminUnit("regionalUnit");
        QAdminUnit municipality = new QAdminUnit("municipality");
        QAdminUnit municipalUnit = new QAdminUnit("municipalUnit");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = createElectionDepartmentIndexPredicate(arguments);
        
        query.from(electionDepartment)
                .join(electionDepartment.electionCenter, electionCenter)
                .leftJoin(electionCenter.foreignCountry, country)
                .leftJoin(electionCenter.geographicalUnit, geographicalUnit)
                .leftJoin(electionCenter.decentralAdmin, decentralAdmin)
                .leftJoin(electionCenter.region, region)
                .leftJoin(electionCenter.regionalUnit, regionalUnit)
                .leftJoin(electionCenter.municipality, municipality)
                .leftJoin(electionCenter.municipalUnit, municipalUnit)
                .where(predicate);
        
        FactoryExpression<ElectionDepartmentIndexListDto> factoryExpression = new QElectionDepartmentIndexListDto(
                electionDepartment.id, electionDepartment.code, electionDepartment.name, electionDepartment.comments, electionDepartment
                .accessDifficulty,
                electionCenter.id, electionCenter.code, electionCenter.name, electionCenter.foreign, country.name, electionCenter.foreignCity,
                geographicalUnit.name, decentralAdmin.name, region.name, regionalUnit.name, municipality.name, municipalUnit.name, electionCenter
                .address,
                electionCenter.postalCode, electionCenter.telephone, electionCenter.comments, electionCenter.floorNumber, electionCenter
                .disabledAccess,
                electionCenter.estimatedBallotBoxes, electionCenter.ballotBoxes, electionCenter.voters2007
        );
        
        Page<ElectionDepartmentIndexListDto> electionDepartmentsPage = electionDepartmentRepository.findAll(query, factoryExpression, pageable);
    
        ElectionDepartmentArgs args = (ElectionDepartmentArgs) arguments;
        if (args != null && args.getContribution() != null && args.getContribution()) {
            populateElectionDepartmentsIndexContributions(electionDepartmentsPage);
        }
        
        return electionDepartmentsPage;
    }
    
    private void populateElectionDepartmentsIndexContributions(Page<ElectionDepartmentIndexListDto> electionDepartmentsPage) {
        
        Map<Long, List<ContributionDto>> contributions = contributionService.getElectionDepartmentIndexContributions();
        
        for (ElectionDepartmentIndexListDto electionDepartmentDto : electionDepartmentsPage.getContent()) {
            
            List<ContributionDto> contributionDtos = contributions.get(electionDepartmentDto.getId());
            
            if (contributionDtos == null) continue;
            
            for (ContributionDto contributionDto : contributionDtos) {
                VolunteerDto volunteerDto = contributionDto.getVolunteerDto();
                if (volunteerDto == null) {
                    continue;
                }
                String tmp = volunteerDto.getLastName().concat(" ").concat(volunteerDto.getFirstName())
                        .concat(!DpTextUtils.isEmpty(contributionDto.getVolunteerDto().getEmail()) ? "<br/>Email: " + contributionDto.getVolunteerDto().getEmail() : "")
                        .concat(!DpTextUtils.isEmpty(contributionDto.getVolunteerDto().getCellphone()) ? "<br/>Τηλ: " + contributionDto.getVolunteerDto().getCellphone() : "");
                if (contributionDto.getType().equals("COMMITTEE_LEADER")) {
                    electionDepartmentDto.setCommitteeLeader(DpTextUtils.isEmpty(electionDepartmentDto.getCommitteeLeader()) ? tmp :
                            electionDepartmentDto.getCommitteeLeader().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                } else if (contributionDto.getType().equals("COMMITTEE_LEADER_VICE")) {
                    electionDepartmentDto.setCommitteeLeaderVice(DpTextUtils.isEmpty(electionDepartmentDto.getCommitteeLeaderVice()) ? tmp :
                            electionDepartmentDto.getCommitteeLeaderVice().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                } else if (contributionDto.getType().equals("COMMITTEE_MEMBER")) {
                    electionDepartmentDto.setCommitteeMember(DpTextUtils.isEmpty(electionDepartmentDto.getCommitteeMember()) ? tmp :
                            electionDepartmentDto.getCommitteeMember().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                } else if (contributionDto.getType().equals("ID_VERIFIER")) {
                    electionDepartmentDto.setIdVerifier(DpTextUtils.isEmpty(electionDepartmentDto.getIdVerifier()) ? tmp :
                            electionDepartmentDto.getIdVerifier().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                } else if (contributionDto.getType().equals("ID_VERIFIER_VICE")) {
                    electionDepartmentDto.setIdVerifierVice(DpTextUtils.isEmpty(electionDepartmentDto.getIdVerifierVice()) ? tmp :
                            electionDepartmentDto.getIdVerifierVice().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                } else if (contributionDto.getType().equals("TREASURER")) {
                    electionDepartmentDto.setTreasurer(DpTextUtils.isEmpty(electionDepartmentDto.getTreasurer()) ? tmp :
                            electionDepartmentDto.getTreasurer().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                } else if (contributionDto.getType().equals("CANDIDATE_REPRESENTATIVE")) {
                    
                    if(contributionDto.getCandidateId() != null) {
                        Short order = candidateService.getOrder(contributionDto.getCandidateId());
                        
                        if(order != null) {
                            switch (order) {
                                case 1:
                                    electionDepartmentDto.setCandidateOneRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateOneRep()) ? tmp :
                                            electionDepartmentDto.getCandidateOneRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 2:
                                    electionDepartmentDto.setCandidateTwoRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateTwoRep()) ? tmp :
                                            electionDepartmentDto.getCandidateTwoRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 3:
                                    electionDepartmentDto.setCandidateThreeRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateThreeRep()) ? tmp :
                                            electionDepartmentDto.getCandidateThreeRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 4:
                                    electionDepartmentDto.setCandidateFourRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateFourRep()) ? tmp :
                                            electionDepartmentDto.getCandidateFourRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 5:
                                    electionDepartmentDto.setCandidateFiveRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateFiveRep()) ? tmp :
                                            electionDepartmentDto.getCandidateFiveRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 6:
                                    electionDepartmentDto.setCandidateSixRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateSixRep()) ? tmp :
                                            electionDepartmentDto.getCandidateSixRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 7:
                                    electionDepartmentDto.setCandidateSevenRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateSevenRep()) ? tmp :
                                            electionDepartmentDto.getCandidateSevenRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 8:
                                    electionDepartmentDto.setCandidateEightRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateEightRep()) ? tmp :
                                            electionDepartmentDto.getCandidateEightRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                case 9:
                                    electionDepartmentDto.setCandidateNineRep(DpTextUtils.isEmpty(electionDepartmentDto.getCandidateNineRep()) ? tmp :
                                            electionDepartmentDto.getCandidateNineRep().concat(CONTRIBUTION_DATA_NEWLINE_WEB).concat(tmp));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
            
        }
    }
    
    @Override
    public List<ElectionDepartmentResultDto> getAllElectionDepartmentResults() {
        
        //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return new ArrayList<ElectionDepartmentResultDto>();
        }
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment)
                .join(electionDepartment.electionCenter, electionCenter)
                .where(electionCenter.electionProcedureId.eq(currentElectionProcedureDto.getId()));
    
    
        Expression<ElectionDepartmentResultDto> expression = null;
        
        if(currentElectionProcedureDto.getRound().equals(ElectionRound.FIRST.name())) {
            expression = getFirstRoundElectionDepartmentResultDtoConstructorExpression(electionDepartment, electionCenter);
        }
        else if(currentElectionProcedureDto.getRound().equals(ElectionRound.SECOND.name())) {
            expression = getSecondRoundElectionDepartmentResultDtoConstructorExpression(electionDepartment, electionCenter);
        }
        else {
            return new ArrayList<ElectionDepartmentResultDto>();
        }
        
        List<ElectionDepartmentResultDto> electionDepartmentResultDtos = query.list(expression);
        return electionDepartmentResultDtos;
    }
    
    private Expression getFirstRoundElectionDepartmentResultDtoConstructorExpression(QElectionDepartment electionDepartment, QElectionCenter electionCenter) {
        return ConstructorExpression.create(ElectionDepartmentResultDto.class,
                electionDepartment.id,
                electionDepartment.electionCenterId,
                electionDepartment.submittedFirst.as("submitted"),
                electionCenter.foreign,
                electionCenter.foreignCountryIsoCode,
                electionCenter.foreignCity,
                electionCenter.geographicalUnitId,
                electionCenter.decentralAdminId,
                electionCenter.regionId,
                electionCenter.regionalUnitId,
                electionCenter.municipalityId,
                electionCenter.municipalUnitId,
                electionDepartment.totalVotesFirst.as("totalVotes"),
                electionDepartment.whiteVotesFirst.as("whiteVotes"),
                electionDepartment.invalidVotesFirst.as("invalidVotes"),
                electionDepartment.validVotesFirst.as("validVotes"),
                electionDepartment.candidateOneVotesFirst.as("candidateOneVotes"),
                electionDepartment.candidateTwoVotesFirst.as("candidateTwoVotes"),
                electionDepartment.candidateThreeVotesFirst.as("candidateThreeVotes"),
                electionDepartment.candidateFourVotesFirst.as("candidateFourVotes"),
                electionDepartment.candidateFiveVotesFirst.as("candidateFiveVotes"),
                electionDepartment.candidateSixVotesFirst.as("candidateSixVotes"),
                electionDepartment.candidateSevenVotesFirst.as("candidateSevenVotes"),
                electionDepartment.candidateEightVotesFirst.as("candidateEightVotes"),
                electionDepartment.candidateNineVotesFirst.as("candidateNineVotes"),
                electionDepartment.candidateTenVotesFirst.as("candidateTenVotes")
        );
    }
    
    private Expression getSecondRoundElectionDepartmentResultDtoConstructorExpression(QElectionDepartment electionDepartment, QElectionCenter electionCenter) {
        return ConstructorExpression.create(ElectionDepartmentResultDto.class,
                electionDepartment.id,
                electionDepartment.electionCenterId,
                electionDepartment.submittedSecond.as("submitted"),
                electionCenter.foreign,
                electionCenter.foreignCountryIsoCode,
                electionCenter.foreignCity,
                electionCenter.geographicalUnitId,
                electionCenter.decentralAdminId,
                electionCenter.regionId,
                electionCenter.regionalUnitId,
                electionCenter.municipalityId,
                electionCenter.municipalUnitId,
                electionDepartment.totalVotesSecond.as("totalVotes"),
                electionDepartment.whiteVotesSecond.as("whiteVotes"),
                electionDepartment.invalidVotesSecond.as("invalidVotes"),
                electionDepartment.validVotesSecond.as("validVotes"),
                electionDepartment.candidateOneVotesSecond.as("candidateOneVotes"),
                electionDepartment.candidateTwoVotesSecond.as("candidateTwoVotes"),
                electionDepartment.candidateThreeVotesSecond.as("candidateThreeVotes"),
                electionDepartment.candidateFourVotesSecond.as("candidateFourVotes"),
                electionDepartment.candidateFiveVotesSecond.as("candidateFiveVotes"),
                electionDepartment.candidateSixVotesSecond.as("candidateSixVotes"),
                electionDepartment.candidateSevenVotesSecond.as("candidateSevenVotes"),
                electionDepartment.candidateEightVotesSecond.as("candidateEightVotes"),
                electionDepartment.candidateNineVotesSecond.as("candidateNineVotes"),
                electionDepartment.candidateTenVotesSecond.as("candidateTenVotes")
        );
    }
    
    @Override
    public Integer getVerificationSerial(Long id, ElectionRound electionRound) {
        
        if(electionRound == null) {
            return 0;
        }
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        JPQLQuery query = new JPAQuery(entityManager);
        
        if(electionRound.equals(ElectionRound.FIRST)) {
            return query.from(electionDepartment).where(electionDepartment.id.eq(id)).singleResult(electionDepartment.verificationSerialFirst);
        }
        else if(electionRound.equals(ElectionRound.SECOND)) {
            return query.from(electionDepartment).where(electionDepartment.id.eq(id)).singleResult(electionDepartment.verificationSerialSecond);
        }
        else {
            return 0;
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void incrementVerificationSerial(Long id, ElectionRound electionRound) {
        
        if(id == null || electionRound == null) {
            return;
        }
        
        ElectionDepartment electionDepartment = electionDepartmentRepository.findOne(id);
        
        if(electionRound.equals(ElectionRound.FIRST)) {
            electionDepartment.setVerificationSerialFirst(electionDepartment.getVerificationSerialFirst() + 1);
        }
        else if(electionRound.equals(ElectionRound.SECOND)) {
            electionDepartment.setVerificationSerialSecond(electionDepartment.getVerificationSerialSecond() + 1);
        }
        else {
            return;
        }
        
        electionDepartmentRepository.save(electionDepartment);
    }
    
    @Override
    public Boolean hasSubmitted(Long id, ElectionRound electionRound) {
        
        if(id == null || electionRound == null) {
            return false;
        }
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        JPQLQuery query = new JPAQuery(entityManager);
        
        if(electionRound.equals(ElectionRound.FIRST)) {
            YesNoEnum submittedFirst = query.from(electionDepartment).where(electionDepartment.id.eq(id)).singleResult(electionDepartment.submittedFirst);
            return (submittedFirst != null && submittedFirst.equals(YesNoEnum.YES));
        }
        else if(electionRound.equals(ElectionRound.SECOND)) {
            YesNoEnum submittedSecond = query.from(electionDepartment).where(electionDepartment.id.eq(id)).singleResult(electionDepartment.submittedSecond);
            return (submittedSecond != null && submittedSecond.equals(YesNoEnum.YES));
        }
        else {
            return false;
        }
    }
    
    @Override
    public Boolean allowsInconsistentSubmission(Long id) {
        
        if(id == null) {
            return false;
        }
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        JPQLQuery query = new JPAQuery(entityManager);
        
        YesNoEnum allowInconsistentSubmission = query.from(electionDepartment).where(electionDepartment.id.eq(id)).singleResult(electionDepartment.allowInconsistentSubmission);
        return (allowInconsistentSubmission != null && allowInconsistentSubmission.equals(YesNoEnum.YES));
    }
    
    @Override
    public Page<ElectionDepartmentIndexListDto> getElectionDepartmentResultsIndex(ElectionDepartmentArgs args, Pageable pageable) {
        
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if(currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return new PageImpl<ElectionDepartmentIndexListDto>(new ArrayList<ElectionDepartmentIndexListDto>());
        }
        
        ElectionRound electionRound = ElectionRound.valueOf(currentElectionProcedureDto.getRound());
        args.setElectionRound(electionRound);
        
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        
        QCountry country = new QCountry("country");
        QAdminUnit geographicalUnit = new QAdminUnit("geographicalUnit");
        QAdminUnit decentralAdmin = new QAdminUnit("decentralAdmin");
        QAdminUnit region = new QAdminUnit("region");
        QAdminUnit regionalUnit = new QAdminUnit("regionalUnit");
        QAdminUnit municipality = new QAdminUnit("municipality");
        QAdminUnit municipalUnit = new QAdminUnit("municipalUnit");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = createElectionDepartmentIndexPredicate(args);
        
        query.from(electionDepartment)
                .join(electionDepartment.electionCenter, electionCenter)
                .leftJoin(electionCenter.foreignCountry, country)
                .leftJoin(electionCenter.geographicalUnit, geographicalUnit)
                .leftJoin(electionCenter.decentralAdmin, decentralAdmin)
                .leftJoin(electionCenter.region, region)
                .leftJoin(electionCenter.regionalUnit, regionalUnit)
                .leftJoin(electionCenter.municipality, municipality)
                .leftJoin(electionCenter.municipalUnit, municipalUnit)
                .where(predicate);
        
        FactoryExpression<ElectionDepartmentIndexListDto> factoryExpression = new QElectionDepartmentIndexListDto(
                electionDepartment.id,
                electionDepartment.code,
                electionDepartment.name,
                electionCenter.id,
                electionCenter.code,
                electionCenter.name,
                electionCenter.foreign,
                country.name,
                electionCenter.foreignCity,
                geographicalUnit.name,
                decentralAdmin.name,
                region.name,
                regionalUnit.name,
                municipality.name,
                municipalUnit.name,
                electionRound.equals(ElectionRound.FIRST) ? electionDepartment.submittedFirst : electionDepartment.submittedSecond
        );
        
        Page<ElectionDepartmentIndexListDto> electionDepartmentsPage = electionDepartmentRepository.findAll(query, factoryExpression, pageable);
    
        populateElectionDepartmentsIndexContributions(electionDepartmentsPage);
        
        for(ElectionDepartmentIndexListDto electionDepartmentIndexListDto: electionDepartmentsPage) {
            electionDepartmentIndexListDto.setVoterCount(voterService.getVoterCountByElectionDepartmentId(electionDepartmentIndexListDto.getId(), YesNoEnum.YES));
            electionDepartmentIndexListDto.setUndoneVoterCount(voterService.getVoterCountByElectionDepartmentId(electionDepartmentIndexListDto.getId(), YesNoEnum.NO));
        }
        
        return electionDepartmentsPage;
    }
    
    @Override
    public List<ElectionDepartmentSnapshotDto> getAllElectionDepartmentSnapshots() {
        
        //Ανάκτηση τρέχουσας εκλογικής διαδικασίας
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if (currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return new ArrayList<ElectionDepartmentSnapshotDto>();
        }
        
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
    
        QCountry country = new QCountry("country");
        QAdminUnit geographicalUnit = new QAdminUnit("geographicalUnit");
        QAdminUnit decentralAdmin = new QAdminUnit("decentralAdmin");
        QAdminUnit region = new QAdminUnit("region");
        QAdminUnit regionalUnit = new QAdminUnit("regionalUnit");
        QAdminUnit municipality = new QAdminUnit("municipality");
        QAdminUnit municipalUnit = new QAdminUnit("municipalUnit");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionDepartment)
                .join(electionDepartment.electionCenter, electionCenter)
                .leftJoin(electionCenter.foreignCountry, country)
                .leftJoin(electionCenter.geographicalUnit, geographicalUnit)
                .leftJoin(electionCenter.decentralAdmin, decentralAdmin)
                .leftJoin(electionCenter.region, region)
                .leftJoin(electionCenter.regionalUnit, regionalUnit)
                .leftJoin(electionCenter.municipality, municipality)
                .leftJoin(electionCenter.municipalUnit, municipalUnit)
                .where(electionCenter.electionProcedureId.eq(currentElectionProcedureDto.getId()));
        
        Expression<ElectionDepartmentSnapshotDto> expression = getElectionDepartmentSnapshotDtoConstructorExpression(electionDepartment, electionCenter, country,
                geographicalUnit, decentralAdmin, region, regionalUnit, municipality, municipalUnit);
        
        List<ElectionDepartmentSnapshotDto> electionDepartmentResultDtos = query.list(expression);
        
        //Ανάκτηση συνόλου διαπιστεύσεων ανά εκλογικό τμήμα
        Map<Long, Integer> voterCountMap = voterService.getAllElectionDepartmentVoterCount
                (currentElectionProcedureDto.getId(), currentElectionProcedureDto.getRound(), YesNoEnum.YES);
                
        //Ανάκτηση συνόλου άρσεων διαπιστεύσεων ανά εκλογικό τμήμα
        Map<Long, Integer> undoneVoterCountMap = voterService.getAllElectionDepartmentVoterCount
                (currentElectionProcedureDto.getId(), currentElectionProcedureDto.getRound(), YesNoEnum.NO);
    
        //Καταχώριση των στοιχείων των διαπιστεύσεων στα εκλογικά τμήματα
        for (ElectionDepartmentSnapshotDto electionDepartmentSnapshotDto : electionDepartmentResultDtos) {
            
            Integer voterCount = voterCountMap.get(electionDepartmentSnapshotDto.getId());
            electionDepartmentSnapshotDto.setVoterCount(voterCount == null ? 0 : voterCount);
            
            Integer undoneVoterCount = undoneVoterCountMap.get(electionDepartmentSnapshotDto.getId());
            electionDepartmentSnapshotDto.setUndoneVoterCount(undoneVoterCount == null ? 0 : undoneVoterCount);
        }
        
        return electionDepartmentResultDtos;
    }
    
    private Expression getElectionDepartmentSnapshotDtoConstructorExpression(QElectionDepartment electionDepartment, QElectionCenter electionCenter, QCountry country,
                                                                             QAdminUnit geographicalUnit, QAdminUnit decentralAdmin, QAdminUnit region,
                                                                             QAdminUnit regionalUnit, QAdminUnit municipality, QAdminUnit municipalUnit) {
        return ConstructorExpression.create(ElectionDepartmentSnapshotDto.class,
                electionDepartment.id,
                electionDepartment.code,
                electionDepartment.name,
                electionDepartment.electionCenterId,
                electionCenter.code,
                electionCenter.name,
                electionDepartment.submittedFirst.as("submitted"),
                electionCenter.foreign,
                electionCenter.foreignCountryIsoCode,
                country.name,
                electionCenter.foreignCity,
                electionCenter.geographicalUnitId,
                geographicalUnit.name,
                electionCenter.decentralAdminId,
                decentralAdmin.name,
                electionCenter.regionId,
                region.name,
                electionCenter.regionalUnitId,
                regionalUnit.name,
                electionCenter.municipalityId,
                municipality.name,
                electionCenter.municipalUnitId,
                municipalUnit.name
        );
    }
}
