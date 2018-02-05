package com.ots.dpel.management.services.impl;

import static com.ots.dpel.management.predicates.ElectionCenterPredicates.createElectionCenterIndexPredicate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.ots.dpel.common.core.domain.QAdminUnit;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionCenterBasicDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.dto.QElectionCenterBasicDto;
import com.ots.dpel.management.dto.QElectionProcedureDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.domain.Country;
import com.ots.dpel.common.core.domain.QCountry;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.common.services.CountryService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.management.core.domain.Contributor;
import com.ots.dpel.management.core.domain.ElectionCenter;
import com.ots.dpel.management.core.domain.QContributor;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.enums.ContributorType;
import com.ots.dpel.management.dto.ElectionCenterDto;
import com.ots.dpel.management.dto.list.ElectionCenterListDto;
import com.ots.dpel.management.dto.list.QElectionCenterListDto;
import com.ots.dpel.management.persistence.ElectionCenterRepository;
import com.ots.dpel.management.services.ContributorService;
import com.ots.dpel.management.services.ElectionCenterService;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;

@Service
public class ElectionCenterServiceImpl implements ElectionCenterService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ElectionCenterRepository electionCenterRepository;
    
    @Autowired
    private ContributorService contributorService;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public Page<ElectionCenterListDto> getElectionCenterIndex(SearchableArguments arguments, Pageable pageable) {
    
        ElectionProcedureDto currentElectionProcedureDto = electionProcedureService.getCurrent();
        
        if(currentElectionProcedureDto == null || DpTextUtils.isEmpty(currentElectionProcedureDto.getRound())) {
            return new PageImpl<ElectionCenterListDto>(new ArrayList<ElectionCenterListDto>());
        }
        ElectionRound electionRound = ElectionRound.valueOf(currentElectionProcedureDto.getRound());
        
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QCountry country = QCountry.country;
        QAdminUnit region = new QAdminUnit("region");
        QAdminUnit regionalUnit = new QAdminUnit("regionalUnit");
        QAdminUnit municipality = new QAdminUnit("municipality");
        QAdminUnit municipalUnit = new QAdminUnit("municipalUnit");
        QContributor supervisor = new QContributor("supervisor");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        BooleanBuilder predicate = createElectionCenterIndexPredicate(arguments);
        
        query.from(electionCenter)
                .leftJoin(electionCenter.foreignCountry, country)
                .leftJoin(electionCenter.region, region)
                .leftJoin(electionCenter.regionalUnit, regionalUnit)
                .leftJoin(electionCenter.municipality, municipality)
                .leftJoin(electionCenter.municipalUnit, municipalUnit)
                .leftJoin(electionRound.equals(ElectionRound.FIRST) ? electionCenter.supervisorFirst : electionCenter.supervisorSecond, supervisor)
                .where(predicate);
        
        FactoryExpression<ElectionCenterListDto> factoryExpression = new QElectionCenterListDto(
                electionCenter.id,
                electionCenter.code,
                electionCenter.name,
                electionCenter.foreign,
                country.name,
                electionCenter.foreignCity,
                region.name,
                regionalUnit.name,
                municipality.name,
                municipalUnit.name,
                electionCenter.address,
                electionCenter.postalCode,
                electionCenter.telephone,
                electionCenter.estimatedBallotBoxes,
                electionCenter.ballotBoxes,
                supervisor.lastName.concat(" ").concat(supervisor.firstName).as("supervisorFullName"),
                supervisor.email.as("supervisorEmail"),
                supervisor.cellphone.as("supervisorCellphone"),
                supervisor.address.as("supervisorAddress"),
                supervisor.postalCode.as("supervisorPostalCode")
        );
        
        return electionCenterRepository.findAll(query, factoryExpression, pageable);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public List<ElectionCenterBasicDto> findAllBasic() {
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QAdminUnit municipality = new QAdminUnit("municipality");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionCenter)
                .leftJoin(electionCenter.municipality, municipality)
                .orderBy(electionCenter.code.asc());
        
        FactoryExpression<ElectionCenterBasicDto> factoryExpression = new QElectionCenterBasicDto(
            electionCenter.id,
            electionCenter.electionProcedureId,
            electionCenter.code,
            electionCenter.name,
            electionCenter.geographicalUnitId,
            electionCenter.decentralAdminId,
            electionCenter.regionId,
            electionCenter.regionalUnitId,
            electionCenter.municipalityId,
            municipality.name,
            electionCenter.municipalUnitId,
            electionCenter.municipalCommunityId,
            electionCenter.foreign,
            electionCenter.foreignCountryIsoCode,
            electionCenter.foreignCity
        );
        
        return electionCenterRepository.findList(query, factoryExpression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ElectionCenterDto findElectionCenter(Long id) {
        
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        QContributor supervisorFirst = new QContributor("supervisorFirst");
        QContributor supervisorSecond = new QContributor("supervisorSecond");
        QCountry foreignCountry = new QCountry("foreignCountry");
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionCenter)
                .leftJoin(electionCenter.supervisorFirst, supervisorFirst)
                .leftJoin(electionCenter.supervisorSecond, supervisorSecond)
                .leftJoin(electionCenter.foreignCountry, foreignCountry)
                .where(electionCenter.id.eq(id));
        
        Expression<ElectionCenterDto> expression = getElectionCenterDtoConstructorExpression(electionCenter, supervisorFirst, supervisorSecond);
        
        ElectionCenterDto electionCenterDto = query.singleResult(expression);
        
        return electionCenterDto;
    }
    
    private Expression<ElectionCenterDto> getElectionCenterDtoConstructorExpression(QElectionCenter electionCenter, QContributor supervisorFirst,
                                                                                    QContributor supervisorSecond) {
        return ConstructorExpression.create(ElectionCenterDto.class,
            electionCenter.id,
            electionCenter.electionProcedureId,
            electionCenter.code,
            electionCenter.name,
            electionCenter.address,
            electionCenter.postalCode,
            electionCenter.telephone,
            contributorService.getContributorDtoConstructorExpression(supervisorFirst),
            contributorService.getContributorDtoConstructorExpression(supervisorSecond),
            electionCenter.comments,
            electionCenter.geographicalUnitId,
            electionCenter.decentralAdminId,
            electionCenter.regionId,
            electionCenter.regionalUnitId,
            electionCenter.municipalityId,
            electionCenter.municipalUnitId,
            electionCenter.municipalCommunityId,
            electionCenter.floorNumber,
            electionCenter.disabledAccess,
            electionCenter.foreign,
            countryService.getCountryDtoConstructorExpression(electionCenter.foreignCountry),
            electionCenter.foreignCity,
            electionCenter.ballotBoxes,
            electionCenter.estimatedBallotBoxes,
            electionCenter.voters2007);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ElectionCenterDto saveElectionCenter(ElectionCenterDto electionCenterDto) {
        
        ElectionCenter electionCenter = (electionCenterDto.getId() == null) ? new ElectionCenter() : electionCenterRepository.findOne
                (electionCenterDto.getId());
        
        dtoToEntity(electionCenterDto, electionCenter);
        
        if (electionCenter.getId() == null) {
            electionCenter.setElectionProcedureId(electionProcedureService.getCurrent().getId());
        }
        
        if (electionCenterDto.getSupervisorFirstDto() != null) {
            Contributor supervisorFirst = new Contributor();
            contributorService.dtoToEntity(electionCenterDto.getSupervisorFirstDto(), supervisorFirst);
            if (supervisorFirst.getId() == null) {
                supervisorFirst.setElectionProcedureId(electionProcedureService.getCurrent().getId());
                supervisorFirst.setType(ContributorType.SUPERVISOR);
            }
            electionCenter.setSupervisorFirst(supervisorFirst);
        } else {
            electionCenter.setSupervisorFirst(null);
        }
        
        if (electionCenterDto.getSupervisorSecondDto() != null) {
            Contributor supervisorSecond = new Contributor();
            contributorService.dtoToEntity(electionCenterDto.getSupervisorSecondDto(), supervisorSecond);
            if (supervisorSecond.getId() == null) {
                supervisorSecond.setElectionProcedureId(electionProcedureService.getCurrent().getId());
                supervisorSecond.setType(ContributorType.SUPERVISOR);
            }
            electionCenter.setSupervisorSecond(supervisorSecond);
        } else {
            electionCenter.setSupervisorSecond(null);
        }
        
        Country foreignCountry = null;
        if (electionCenterDto.getForeignCountryDto() != null) {
            foreignCountry = new Country();
            countryService.dtoToEntity(electionCenterDto.getForeignCountryDto(), foreignCountry);
        }
        electionCenter.setForeignCountry(foreignCountry);
        electionCenter.removeRedundantGeographicalData();
        
        validateElectionCenter(electionCenter);
        
        electionCenterRepository.save(electionCenter);
        
        return findElectionCenter(electionCenter.getId());
    }
    
    private void validateElectionCenter(ElectionCenter electionCenter) {
        
        if (DpTextUtils.isEmpty(electionCenter.getCode())) {
            throw new DpValidationException("CODE_EMPTY", messageSourceProvider.getMessage("mg.electioncenter.error.code.empty"));
        }
        
        if (DpTextUtils.isEmpty(electionCenter.getName())) {
            throw new DpValidationException("NAME_EMPTY", messageSourceProvider.getMessage("mg.electioncenter.error.name.empty"));
        }
        
        if (!electionCenter.hasGeographicalData()) {
            String msgKey = electionCenter.getForeign().booleanValue()? 
                "mg.electioncenter.error.geographicalData.foreign.incomplete": 
                "mg.electioncenter.error.geographicalData.local.incomplete";
            throw new DpValidationException("GEO_DATA_INCOMPLETE", messageSourceProvider.getMessage(msgKey));
        }
    }
    
    private void dtoToEntity(ElectionCenterDto electionCenterDto, ElectionCenter electionCenter) {
        
        if (electionCenterDto == null) {
            return;
        }
        
        if (electionCenter == null) {
            electionCenter = new ElectionCenter();
        }
        
        BeanUtils.copyProperties(electionCenterDto, electionCenter, 
            "electionProcedureId", "supervisorFirstDto", "supervisorSecondDto", 
            "disabledAccess", "foreign", "foreignCountryDto");
        
        electionCenter.setDisabledAccess(YesNoEnum.of(electionCenterDto.getDisabledAccess()));
        electionCenter.setForeign(YesNoEnum.of(electionCenterDto.getForeign()));
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void deleteElectionCenter(Long id) {
        
        //Διαγραφή εκλογικών τμημάτων
        List<Long> electionDepartmentIds = electionDepartmentService.findIdsByElectionCenter(id);
        for (Long electionDepartmentId : electionDepartmentIds) {
            electionDepartmentService.deleteElectionDepartment(electionDepartmentId);
        }
        
        //Διαγραφή εκλογικού κέντρου
        electionCenterRepository.delete(id);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public ElectionCenterDto findElectionCenterBasic(Long id) {
        
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionCenter).where(electionCenter.id.eq(id));
        
        Expression<ElectionCenterDto> expression = ConstructorExpression.create(ElectionCenterDto.class,
                electionCenter.id,
                electionCenter.code,
                electionCenter.name
        );
        
        return query.singleResult(expression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public String getCode(Long id) {
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        JPQLQuery query = new JPAQuery(entityManager);
    
        return query.from(electionCenter)
                .where(electionCenter.id.eq(id))
                .singleResult(electionCenter.code);
    }
    
    @Override
    public List<String> getCitiesUsedInElectionCenters() {
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(electionCenter)
                .where(electionCenter.foreignCity.isNotNull())
                .distinct()
                .list(electionCenter.foreignCity);
    }
    
    @Override
    public List<Long> getElectionCenterIds() {
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(electionCenter).list(electionCenter.id);
    }
    
    @Override
    public List<ElectionCenterBasicDto> getElectionCenterBasicForSnapshot() {
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(electionCenter);
        
        FactoryExpression<ElectionCenterBasicDto> factoryExpression = new QElectionCenterBasicDto(
                electionCenter.id,
                electionCenter.code,
                electionCenter.name);
        
        return electionCenterRepository.findList(query, factoryExpression);
    }
    
    @Override
    public List<ElectionCenterBasicDto> getElectionCenterBasicByForeignCountry(String foreignCountryIsoCode) {
    
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
    
        JPQLQuery query = new JPAQuery(entityManager);
    
        query.from(electionCenter)
                .where(electionCenter.foreign.eq(YesNoEnum.YES)
                        .and(electionCenter.foreignCountryIsoCode.eq(foreignCountryIsoCode)))
                .orderBy(electionCenter.name.asc());
    
        FactoryExpression<ElectionCenterBasicDto> factoryExpression = new QElectionCenterBasicDto(
                electionCenter.id,
                electionCenter.code,
                electionCenter.name);
    
        return electionCenterRepository.findList(query, factoryExpression);
    }
    
    @Override
    public List<ElectionCenterBasicDto> getElectionCenterBasicByMunicipalityId(Long municipalityId) {
    
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
    
        JPQLQuery query = new JPAQuery(entityManager);
    
        query.from(electionCenter)
                .where(electionCenter.municipalityId.eq(municipalityId))
                .orderBy(electionCenter.name.asc());
    
        FactoryExpression<ElectionCenterBasicDto> factoryExpression = new QElectionCenterBasicDto(
                electionCenter.id,
                electionCenter.code,
                electionCenter.name);
    
        return electionCenterRepository.findList(query, factoryExpression);
    }
}
