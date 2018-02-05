package com.ots.dpel.management.services.impl;

import static com.ots.dpel.management.predicates.ContributionPredicates.createContributionIndexPredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.MessageSourceProvider;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.auth.dto.UserRegistrationMailMessageDto;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.domain.MailTemplate.MailTemplateCode;
import com.ots.dpel.common.core.domain.QAdminUnit;
import com.ots.dpel.common.core.domain.QCountry;
import com.ots.dpel.common.core.dto.MailMessageDto;
import com.ots.dpel.common.core.dto.MailTemplateDto;
import com.ots.dpel.common.services.MailService;
import com.ots.dpel.common.services.MailTemplateService;
import com.ots.dpel.config.beans.WebConfigBean;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.services.VolunteerService;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.ReflectionUtils;
import com.ots.dpel.global.utils.UuidUtils;
import com.ots.dpel.management.core.domain.Contribution;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import com.ots.dpel.management.core.domain.QContribution;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ContributionDto;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.dto.QElectionDepartmentBasicDto;
import com.ots.dpel.management.dto.list.ContributionListDto;
import com.ots.dpel.management.persistence.ContributionRepository;
import com.ots.dpel.management.persistence.ElectionDepartmentRepository;
import com.ots.dpel.management.services.ContributionService;
import com.ots.dpel.management.services.ElectionProcedureService;

@Service
public class ContributionServiceImpl implements ContributionService {
    
    private final Logger logger = LogManager.getLogger(getClass());
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ContributionRepository contributionRepository;
    
    @Autowired
    private ElectionDepartmentRepository electionDepartmentRepository;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @Autowired
    private MailTemplateService mailTemplateService;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private WebConfigBean webConfig;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public List<ContributionDto> getContributionsByElectionDepartment(Long electionDepartmentId) {
        
        QContribution contribution = QContribution.contribution;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(contribution)
                .where(contribution.electionDepartment.id.eq(electionDepartmentId))
                .orderBy(contribution.type.asc());
        
        Expression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        List<ContributionDto> contributionDtos = query.list(expression);
        
        for (ContributionDto contributionDto: contributionDtos) {
            contributionDto.setVolunteerDto(volunteerService.findVolunteer(contributionDto.getVolunteerId()));
        }
        
        return contributionDtos;
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public List<ElectionDepartmentBasicDto> getVolunteerElectionDepartments(Long volunteerId, ElectionRound round, Long exceptElectionDepartmentId) {
        QContribution contribution = QContribution.contribution;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QAdminUnit municipality = new QAdminUnit("municipality");
        
        JPQLQuery query = new JPAQuery(entityManager);
    
        BooleanBuilder predicate = new BooleanBuilder();
        
        predicate.and(contribution.volunteerId.eq(volunteerId))
            .and(contribution.round.eq(round));
    
        if (exceptElectionDepartmentId != null) {
            predicate.and(electionDepartment.id.ne(exceptElectionDepartmentId));
        }
        
        query.from(electionDepartment)
                .leftJoin(electionDepartment.electionCenter, electionCenter)
                .leftJoin(electionCenter.municipality, municipality)
                .join(electionDepartment.contributions, contribution)
                .where(predicate);
        
        FactoryExpression<ElectionDepartmentBasicDto> factoryExpression = new QElectionDepartmentBasicDto(
            electionDepartment.id,
            electionDepartment.serialNo,
            electionDepartment.code,
            electionDepartment.name,
            electionCenter.foreign,
            municipality.name.as("municipalityName"),
            electionCenter.name.as("electionCenterName"),
            contribution.type
        );
        
        return contributionRepository.findList(query, factoryExpression);
    }
    
    @Override
    public void manageContributionsOfElectionDepartment(ElectionDepartment electionDepartment, List<ContributionDto> contributionDtos) {
        
        //Κρατάμε τη λίστα με τα προηγούμενα contributions
        List<Contribution> previousContributions = new ArrayList<>(electionDepartment.getContributions());
        
        //Άδειασμα της λίστας των προηγούμενων contributions
        electionDepartment.getContributions().clear();
        
        
        //Για κάθε contribution DTO, δημιουργία νέου contribution αν δεν υπήρχε ή διατήρηση του υπάρχοντος.
        for (ContributionDto contributionDto : contributionDtos) {
            Contribution contribution = new Contribution();
            dtoToEntity(contributionDto, contribution);
            addNewOrExistingContributionToList(electionDepartment, previousContributions, contribution);
        }
    }
    
    @Override
    public void saveContribution(ContributionDto contributionDto) {
        
        if (contributionDto == null) {
            return;
        }
        
        Long contributionId = contributionDto.getId();
        Contribution contribution = contributionId != null? 
            this.contributionRepository.findOne(contributionId): new Contribution();
        if (contribution == null) {
            return;
        }
        
        dtoToEntity(contributionDto, contribution);
        
        Long electionDepartmentId = contributionDto.getElectionDepartmentId();
        if (electionDepartmentId != null) {
            ElectionDepartment electionDepartment = 
                this.electionDepartmentRepository.findOne(electionDepartmentId);
            contribution.setElectionDepartment(electionDepartment);
        }
        
        this.contributionRepository.save(contribution);
    }

    private void dtoToEntity(ContributionDto contributionDto, Contribution contribution) {
        
        if (contributionDto == null) {
            return;
        }
        
        if (contribution == null) {
            contribution = new Contribution();
        }
        
        BeanUtils.copyProperties(contributionDto, contribution, "volunteerDto");
        contribution.setType(contributionDto.getType() == null ? null : ContributionType.valueOf(contributionDto.getType()));
        contribution.setRound(contributionDto.getRound() == null ? null : ElectionRound.valueOf(contributionDto.getRound()));
        contribution.setStatus(contributionDto.getStatus() == null ? null : ContributionStatus.valueOf(contributionDto.getStatus()));
    }
    
    /**
     * Δημιουργία νέου ή διατήρηση υπάρχοντος contribution από τη δεδομένη λίστα και ανάθεση πάνω στο εκλογικό τμήμα
     */
    private void addNewOrExistingContributionToList(ElectionDepartment electionDepartment, List<Contribution> previousContributions, Contribution
            newContribution) {
        Contribution existingContribution = findInContributionList(previousContributions, newContribution);
        if (existingContribution != null) {
            //Το contribution υπήρχε από πριν: διατηρείται.
            electionDepartment.getContributions().add(existingContribution);
        } else {
            //Το contribution δεν υπήρχε από πριν: παίρνει το εκλογικό τμήμα - γονέα και μπαίνει στη λίστα.
            newContribution.setElectionDepartment(electionDepartment);
            
            if (newContribution.getType().equals(ContributionType.COMMITTEE_LEADER) || newContribution.getType().equals(ContributionType.ID_VERIFIER) ||
                    newContribution.getType().equals(ContributionType.COMMITTEE_LEADER_VICE) || newContribution.getType().equals(ContributionType.ID_VERIFIER_VICE)) {
                newContribution.setIdentifier(UuidUtils.generateId());
                newContribution.setStatus(ContributionStatus.PENDING);
            }
            else {
                newContribution.setStatus(ContributionStatus.WITHOUT_ACCESS);
            }
            electionDepartment.getContributions().add(newContribution);
        }
    }
    
    /**
     * Ανάκτηση (αν υπάρχει) από την υπάρχουσα λίστα του contribution με το ίδιο id.
     */
    private Contribution findInContributionList(List<Contribution> previousContributions, Contribution newContribution) {
        if (newContribution.getId() == null) {
            return null;
        }
        
        for (Contribution previousContribution : previousContributions) {
            if (previousContribution.getId().equals(newContribution.getId())) {
                return previousContribution;
            }
            
        }
        
        return null;
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public Map<Long, List<ContributionDto>> getElectionDepartmentIndexContributions() {
    
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        
        QContribution contribution = QContribution.contribution;

        JPQLQuery query = new JPAQuery(entityManager);

        query.from(contribution)
                .where(contribution.round.eq(ElectionRound.valueOf(electionProcedureDto.getRound())))
                .orderBy(contribution.type.asc());
    
        Expression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        List<ContributionDto> contributionDtos = query.list(expression);

        for (ContributionDto contributionDto: contributionDtos) {
            Long volunteerId = contributionDto.getVolunteerId();
            if (volunteerId != null) {
                contributionDto.setVolunteerDto(volunteerService.findVolunteer(contributionDto.getVolunteerId()));
            }
        }
    
        Map<Long, List<ContributionDto>> map = new HashMap<>();
    
        for (ContributionDto contributionDto : contributionDtos) {
            if (map.get(contributionDto.getElectionDepartmentId()) == null) {
                List<ContributionDto> list = new ArrayList<>();
                list.add(contributionDto);
                map.put(contributionDto.getElectionDepartmentId(), list);
            } else {
                map.get(contributionDto.getElectionDepartmentId()).add(contributionDto);
            }
        }
        
        return map;
    }
    
    private FactoryExpression<ContributionDto> getContributionDtoConstructorExpression(QContribution contribution) {
        return ConstructorExpression.create(ContributionDto.class,
                    contribution.id,
                    contribution.volunteerId,
                    contribution.type,
                    contribution.round,
                    contribution.electionDepartment.id,
                    contribution.status,
                    contribution.emailSentDate,
                    contribution.registrationDate,
                    contribution.identifier,
                    contribution.candidateId
            );
    }
    
    private FactoryExpression<ContributionListDto> getContributionListDtoConstructorExpression(
            QContribution contribution,
            QElectionDepartment electionDepartment,
            QElectionCenter electionCenter,
            QCountry country,
            QAdminUnit geographicalUnit,
            QAdminUnit decentralAdmin,
            QAdminUnit region,
            QAdminUnit regionalUnit,
            QAdminUnit municipality,
            QAdminUnit municipalUnit) {
        
        return ConstructorExpression.create(ContributionListDto.class,
                    contribution.id,
                    contribution.volunteerId,
                    contribution.type,
                    contribution.round,
                    electionCenter.id,
                    electionCenter.code,
                    electionCenter.name,
                    electionDepartment.id,
                    electionDepartment.code,
                    electionDepartment.name,
                    contribution.status,
                    contribution.emailSentDate,
                    contribution.registrationDate,
                    contribution.identifier,
                    electionCenter.foreign,
                    country.name,
                    electionCenter.foreignCity,
                    electionCenter.geographicalUnit.id,
                    electionCenter.geographicalUnit.name,
                    electionCenter.decentralAdmin.id,
                    electionCenter.decentralAdmin.name,
                    electionCenter.region.id,
                    electionCenter.region.name,
                    electionCenter.regionalUnit.id,
                    electionCenter.regionalUnit.name,
                    electionCenter.municipality.id,
                    electionCenter.municipality.name,
                    electionCenter.municipalUnit.id,
                    electionCenter.municipalUnit.name
            );
    }
    
    @Override
    public ContributionDto getContributionByIdentifier(String identifier) {
    
        QContribution contribution = QContribution.contribution;
    
        JPQLQuery query = new JPAQuery(entityManager);
    
        query.from(contribution).where(contribution.identifier.eq(identifier));
    
        FactoryExpression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        ContributionDto contributionDto = contributionRepository.findOne(query, expression);
    
        if (contributionDto != null && contributionDto.getVolunteerId() != null) {
            contributionDto.setVolunteerDto(volunteerService.findVolunteer(contributionDto.getVolunteerId()));
        }
    
        return contributionDto;
    }

    @Override
    public ContributionDto findContribution(Long contributionId) {
        QContribution contribution = QContribution.contribution;
        
        JPQLQuery query = new JPAQuery(entityManager);
    
        query.from(contribution).where(contribution.id.eq(contributionId));
    
        FactoryExpression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        ContributionDto contributionDto = contributionRepository.findOne(query, expression);
    
        if (contributionDto != null && contributionDto.getVolunteerId() != null) {
            contributionDto.setVolunteerDto(volunteerService.findVolunteer(contributionDto.getVolunteerId()));
        }
    
        return contributionDto;
    }

    @Override
    public ContributionDto findContributionByVolunteerId(Long volunteerId) {
        QContribution contribution = QContribution.contribution;
        
        JPQLQuery query = new JPAQuery(entityManager);
    
        query.from(contribution).where(contribution.volunteerId.eq(volunteerId));
    
        FactoryExpression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        ContributionDto contributionDto = contributionRepository.findOne(query, expression);
    
        if (contributionDto != null && contributionDto.getVolunteerId() != null) {
            contributionDto.setVolunteerDto(volunteerService.findVolunteer(contributionDto.getVolunteerId()));
        }
    
        return contributionDto;
    }

    @Override
    public ContributionDto findPendingContribution(Long electionDepartmentId, Long contributionId) {
        return findContributionInStatus(
            electionDepartmentId, contributionId, ContributionStatus.PENDING);
    }
    
    @Override
    public ContributionDto findNotifiedContribution(Long electionDepartmentId, Long contributionId) {
        return findContributionInStatus(
            electionDepartmentId, contributionId, ContributionStatus.EMAIL_SENT);
    }

    private ContributionDto findContributionInStatus(
            Long electionDepartmentId, Long contributionId, ContributionStatus status) {
        QContribution contribution = QContribution.contribution;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(contribution)
            .where(contribution.id.eq(contributionId)
              .and(contribution.electionDepartment.id.eq(electionDepartmentId))
              .and(contribution.status.eq(status)));
        
        Expression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        return query.uniqueResult(expression);
    }

    @Override
    public List<ContributionDto> findPendingContributions(List<Long> contributionIds) {
        return findContributionsInStatus(contributionIds, ContributionStatus.PENDING);
    }
    
    @Override
    public List<ContributionDto> findNotifiedContributions(List<Long> contributionIds) {
        return findContributionsInStatus(contributionIds, ContributionStatus.EMAIL_SENT);
    }

    private List<ContributionDto> findContributionsInStatus(
            List<Long> contributionIds, ContributionStatus status) {
        QContribution contribution = QContribution.contribution;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(contribution)
            .where(contribution.id.in(contributionIds).and(contribution.status.eq(status)));
        
        Expression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        return query.list(expression);
    }
    
   

    @Override
    public List<ContributionDto> findAllPendingContributions() {
        return findAllContributionsInStatus(ContributionStatus.PENDING);
    }

    @Override
    public List<ContributionDto> findAllNotifiedContributions() {
        return findAllContributionsInStatus(ContributionStatus.EMAIL_SENT);
    }

    private List<ContributionDto> findAllContributionsInStatus(ContributionStatus status) {
        
        QContribution contribution = QContribution.contribution;
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(contribution)
            .where(contribution.status.eq(status));
        
        Expression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        return query.list(expression);
    }

    @Override
    public List<ContributionListDto> findAllContributions() {
        QContribution contribution = QContribution.contribution;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QCountry country = new QCountry("country");
        QAdminUnit geographicalUnit = new QAdminUnit("geographicalUnit");
        QAdminUnit decentralAdmin = new QAdminUnit("decentralAdmin");
        QAdminUnit region = new QAdminUnit("region");
        QAdminUnit regionalUnit = new QAdminUnit("regionalUnit");
        QAdminUnit municipality = new QAdminUnit("municipality");
        QAdminUnit municipalUnit = new QAdminUnit("municipalUnit");
        
        JPQLQuery query = new JPAQuery(entityManager)
            .from(contribution)
            .join(contribution.electionDepartment, electionDepartment)
            .join(contribution.electionDepartment.electionCenter, electionCenter)
            .leftJoin(electionCenter.foreignCountry, country)
            .leftJoin(electionCenter.geographicalUnit, geographicalUnit)
            .leftJoin(electionCenter.decentralAdmin, decentralAdmin)
            .leftJoin(electionCenter.region, region)
            .leftJoin(electionCenter.regionalUnit, regionalUnit)
            .leftJoin(electionCenter.municipality, municipality)
            .leftJoin(electionCenter.municipalUnit, municipalUnit)
            ;
        
        Expression<ContributionListDto> expression = getContributionListDtoConstructorExpression(
                contribution, electionDepartment, electionCenter, country,
                geographicalUnit, decentralAdmin, region, regionalUnit,
                municipality, municipalUnit);
        
        return query.list(expression);
    }

    @Override
    public List<ContributionListDto> findContributions(SearchableArguments arguments) {
        QContribution contribution = QContribution.contribution;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QCountry country = new QCountry("country");
        QAdminUnit geographicalUnit = new QAdminUnit("geographicalUnit");
        QAdminUnit decentralAdmin = new QAdminUnit("decentralAdmin");
        QAdminUnit region = new QAdminUnit("region");
        QAdminUnit regionalUnit = new QAdminUnit("regionalUnit");
        QAdminUnit municipality = new QAdminUnit("municipality");
        QAdminUnit municipalUnit = new QAdminUnit("municipalUnit");
        
        JPQLQuery query = new JPAQuery(entityManager)
            .from(contribution)
            .join(contribution.electionDepartment, electionDepartment)
            .join(contribution.electionDepartment.electionCenter, electionCenter)
            .leftJoin(electionCenter.foreignCountry, country)
            .leftJoin(electionCenter.geographicalUnit, geographicalUnit)
            .leftJoin(electionCenter.decentralAdmin, decentralAdmin)
            .leftJoin(electionCenter.region, region)
            .leftJoin(electionCenter.regionalUnit, regionalUnit)
            .leftJoin(electionCenter.municipality, municipality)
            .leftJoin(electionCenter.municipalUnit, municipalUnit)
            ;
        
        BooleanBuilder predicate = createContributionIndexPredicate(arguments);
        query.where(predicate);
        
        Expression<ContributionListDto> expression = getContributionListDtoConstructorExpression(
                contribution, electionDepartment, electionCenter, country,
                geographicalUnit, decentralAdmin, region, regionalUnit,
                municipality, municipalUnit);
    
        return query.list(expression);
    }

    @Override
    public List<ContributionDto> findPendingContributionsByElectionDepartment(Long electionDepartmentId) {
        return findContributionsByElectionDepartmentInStatus(
            electionDepartmentId, ContributionStatus.PENDING);
    }
    
    @Override
    public List<ContributionDto> findNotifiedContributionsByElectionDepartment(Long electionDepartmentId) {
        return findContributionsByElectionDepartmentInStatus(
            electionDepartmentId, ContributionStatus.EMAIL_SENT);
    }

    private List<ContributionDto> findContributionsByElectionDepartmentInStatus(
            Long electionDepartmentId, ContributionStatus status) {
        QContribution contribution = QContribution.contribution;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(contribution)
        .where(contribution.electionDepartment.id.eq(electionDepartmentId).and(contribution.status.eq(status)));
        
        Expression<ContributionDto> expression = getContributionDtoConstructorExpression(contribution);
    
        return query.list(expression);
    }
    
    @Override
    public void notifyPendingContribution(Long electionDepartmentId, Long contributionId) {
        sendUserActivationMailMessages(findPendingContributions(Arrays.asList(contributionId)));
    }
    
    @Override
    public void renotifyPendingContribution(Long electionDepartmentId, Long contributionId) {
        sendUserActivationMailMessages(findNotifiedContributions(Arrays.asList(contributionId)));
    }

    @Override
    public void notifyPendingContributionsByElectionDepartment(Long electionDepartmentId) {
        sendUserActivationMailMessages(findPendingContributionsByElectionDepartment(electionDepartmentId));
    }

    @Override
    public void renotifyPendingContributionsByElectionDepartment(Long electionDepartmentId) {
        sendUserActivationMailMessages(findNotifiedContributionsByElectionDepartment(electionDepartmentId));
    }
    
    @Override
    public void notifyPendingContributions(List<Long> contributionIds) {
        sendUserActivationMailMessages(findPendingContributions(contributionIds));
    }
    
    @Override
    public void renotifyPendingContributions(List<Long> contributionIds) {
        sendUserActivationMailMessages(findNotifiedContributions(contributionIds));
    }
    
    @Override
    public void notifyAllPendingContributions() {
        sendUserActivationMailMessages(findAllPendingContributions());
    }
    
    @Override
    public void renotifyAllPendingContributions() {
        sendUserActivationMailMessages(findAllNotifiedContributions());
    }
    
    @Override
    public void notifyVolunteer(Long volunteerId) {
        if (volunteerId == null) {
            return;
        }
        
        ContributionDto contributionDto = this.findContributionByVolunteerId(volunteerId);
        if (contributionDto != null) {
            String status = contributionDto.getStatus();
            if (ContributionStatus.PENDING.name().equals(status) || 
                ContributionStatus.EMAIL_SENT.name().equals(status)) {
                sendUserActivationMailMessages(Arrays.asList(contributionDto));
            }
        }
    }

    @Override
    @Transactional(transactionManager = "txMgr")
    public void deleteContributionByVolunteerId(Long volunteerId) {
        if (volunteerId == null) {
            return;
        }
        
        this.contributionRepository.deleteByVolunteerId(volunteerId);
    }

    private void sendUserActivationMailMessages(List<ContributionDto> contributionDtos) {
        
        if (CollectionUtils.isEmpty(contributionDtos)) {
            return;
        }        
        
        List<MailMessageDto> mailMessageDtos = new ArrayList<>();
        MailTemplateDto mailTemplateDto = mailTemplateService.findByCodeEnum(MailTemplateCode.USER_ACTIVATION);
        
        // Ανάκτηση στοιχείων των εθελοντών που είναι συσχετισμένοι με τα contributions
        List<Long> volunteerIds = ReflectionUtils.extractFieldValuesToList(contributionDtos, "volunteerId", Long.class);
        List<VolunteerDto> volunteerDtos = this.volunteerService.findVolunteers(volunteerIds);
        Map<Long, VolunteerDto> volunteerDtosById = ReflectionUtils.indexObjectsById(volunteerDtos);
        
        for (ContributionDto contributionDto: contributionDtos) {
            Long contributionId = contributionDto.getId();
            Long volunteerId = contributionDto.getVolunteerId();
            if (volunteerId == null) {
                continue;
            }
            
            VolunteerDto volunteerDto = volunteerDtosById.get(volunteerId);
            if (volunteerDto == null) {
                continue;
            }
            
            contributionDto.setVolunteerDto(volunteerDto);
            Map<String, Object> args = userActivationMailMessageArgs(contributionDto);
            
            List<String> recipients = Collections.singletonList(volunteerDto.getEmail());
            
            // Δημιουργία email ενεργοποίησης χρήστη για το τρέχον μέλος
            UserRegistrationMailMessageDto mailMessageDto = new UserRegistrationMailMessageDto();
            mailTemplateService.populateMailMessageFromTemplate(mailMessageDto, mailTemplateDto, args, recipients);
            mailMessageDto.setContributionId(contributionId);
            
            mailMessageDtos.add(mailMessageDto);
        }
        
        if (CollectionUtils.isNotEmpty(mailMessageDtos)) {
            
            try {
                mailService.sendEmails(mailMessageDtos);
            } catch (Exception e) {
                logger.info("Error while sending notifications for user activation", e);
            }            
        }
    }

    /**
     * Δημιουργία μεταβλητών που θα συγχωνευθούν στο template του mail
     * ενεργοποίησης χρήστη μέλους εκλογικού τμήματος.
     *  
     * @param contributionDto
     *    στοιχεία μέλους εκλογικού τμήματος
     * @return
     *    μεταβλητές για το USER_ACTIVATION template
     */
    private Map<String, Object> userActivationMailMessageArgs(ContributionDto contributionDto) {
        
        Map<String, Object> args = new HashMap<>();
        
        VolunteerDto volunteerDto = contributionDto.getVolunteerDto();
        
        String fullName = DpTextUtils.getPersonFullName(
            volunteerDto.getFirstName(), volunteerDto.getLastName(), true);
        
        args.put(UserRegistrationMailMessageDto.ARG_USER_FULLNAME, fullName);
        args.put(UserRegistrationMailMessageDto.ARG_BASE_URL, webConfig.getDpElectionViewBaseUrl());
        args.put(UserRegistrationMailMessageDto.ARG_USER_ACTIVATION_CODE, contributionDto.getIdentifier());
        
        return args;
    }

    @Override
    public Boolean contributionWithVolunteerEmailExists(String email) {
        Long volunteerId = volunteerService.getVolunteerIdByEmail(email);
        return contributionWithVolunteerIdExists(volunteerId);
    }
    
    private Boolean contributionWithVolunteerIdExists(Long volunteerId) {
        if(volunteerId == null) {
            return false;
        }
        else {
            QContribution contribution = QContribution.contribution;
            JPQLQuery query = new JPAQuery(entityManager);
        
            return query.from(contribution).where(contribution.volunteerId.eq(volunteerId)).exists();
        }
    }
    
    @Override
    public void createCandidateRepresentativeContribution(Long volunteerId, Long electionDepartmentId, Long candidateId) {
        
        if(volunteerId == null) {
            throw new DpValidationException("NO_VOLUNTEER", messageSourceProvider.getMessage("mg.contribution.createCandidateRepresentative.error.volunteerIdNull"));
        }
        if(electionDepartmentId == null) {
            throw new DpValidationException("NO_ELECTION_DEPARTMENT", messageSourceProvider.getMessage("mg.contribution.createCandidateRepresentative.error.electionDepartmentIdNull"));
        }
        if(candidateId == null) {
            throw new DpValidationException("NO_CANDIDATE", messageSourceProvider.getMessage("mg.contribution.createCandidateRepresentative.error.candidateIdNull"));
        }
        
        if(contributionWithVolunteerIdExists(volunteerId)) {
            throw new DpValidationException("VOLUNTEER_CONTRIBUTION_EXISTS", messageSourceProvider.getMessage("mg.contribution.createCandidateRepresentative.error.volunteerContributionExists"));
        }
        
        
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        if(currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return;
        }
        
        ElectionDepartment electionDepartment = new ElectionDepartment();
        electionDepartment.setId(electionDepartmentId);
        
        Contribution contribution = new Contribution();
        contribution.setVolunteerId(volunteerId);
        contribution.setElectionDepartment(electionDepartment);
        contribution.setType(ContributionType.CANDIDATE_REPRESENTATIVE);
        contribution.setCandidateId(candidateId);
        contribution.setRound(ElectionRound.valueOf(currentElectionProcedureDto.getRound()));
        contribution.setStatus(ContributionStatus.WITHOUT_ACCESS);
        
        try {
            contributionRepository.save(contribution);
        }
        catch(DataIntegrityViolationException e) {
            throw new DpValidationException("DATA_INTEGRITY_VIOLATION",
                    messageSourceProvider.getMessage("mg.contribution.createCandidateRepresentative.error.dataIntegrityViolation") + ": " + e.getCause().getCause().getMessage());
        }
    }
}
