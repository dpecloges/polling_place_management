package com.ots.dpel.ext.services.impl;

import static com.ots.dpel.ext.predicates.VolunteerPredicates.createVolunteerIndexPredicate;

import java.util.List;

import javax.persistence.EntityManager;

import com.ots.dpel.global.utils.DpTextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.ext.core.domain.QVolunteer;
import com.ots.dpel.ext.dto.VolunteerDto;
import com.ots.dpel.ext.dto.list.QVolunteerListDto;
import com.ots.dpel.ext.dto.list.VolunteerListDto;
import com.ots.dpel.ext.persistence.VolunteerRepository;
import com.ots.dpel.ext.services.VolunteerService;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    
    @Autowired
    @Qualifier("externalEntityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public VolunteerDto findVolunteer(Long id) {
         
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer).where(volunteer.id.eq(id));
        
        Expression<VolunteerDto> expression = getVolunteerDtoConstructorExpression(volunteer);
    
        return query.singleResult(expression);
    }

    @Override
    public VolunteerDto findVolunteerBasic(Long id) {
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer).where(volunteer.id.eq(id));
        
        Expression<VolunteerDto> expression = ConstructorExpression.create(VolunteerDto.class, 
            volunteer.id, volunteer.publicIdentifier, volunteer.eklSpecialNo,
            volunteer.lastName, volunteer.firstName, volunteer.email);
    
        return query.singleResult(expression);
    }
    
    @Override
    public List<VolunteerDto> findVolunteers(List<Long> ids) {
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer).where(volunteer.id.in(ids));
        
        Expression<VolunteerDto> expression = getVolunteerDtoConstructorExpression(volunteer);
        
        return query.list(expression);
    }
    
    @Override
    public List<VolunteerDto> findVolunteersBasic(List<Long> ids) {
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer).where(volunteer.id.in(ids));
        
        Expression<VolunteerDto> expression = ConstructorExpression.create(VolunteerDto.class, 
            volunteer.id, volunteer.publicIdentifier, volunteer.eklSpecialNo,
            volunteer.lastName, volunteer.firstName, volunteer.email);
        
        return query.list(expression);
    }

    @Override
    public Page<VolunteerListDto> getVolunteerIndex(SearchableArguments arguments, Pageable pageable) {

        BooleanBuilder predicate = createVolunteerIndexPredicate(arguments);
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer).where(predicate);
        
        FactoryExpression<VolunteerListDto> factoryExpression = getVolunteerListDtoFactoryExpression(volunteer);
    
        return this.volunteerRepository.findAll(query, factoryExpression, pageable);
    }
    
    @Override
    public List<VolunteerDto> findAllVolunteers() {
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer);
        
        Expression<VolunteerDto> expression = getVolunteerDtoConstructorExpression(volunteer);
    
        return query.list(expression);
    }

    @Override
    public List<VolunteerDto> findAllVolunteersBasic() {
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(volunteer);
        
        Expression<VolunteerDto> expression = ConstructorExpression.create(VolunteerDto.class, 
            volunteer.id, volunteer.publicIdentifier, volunteer.eklSpecialNo,
            volunteer.lastName, volunteer.firstName, volunteer.email);
    
        return query.list(expression);
    }

    @Override
    public List<VolunteerDto> findVolunteersBasic(SearchableArguments arguments) {

        BooleanBuilder predicate = createVolunteerIndexPredicate(arguments);
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager).from(volunteer).where(predicate);
        
        Expression<VolunteerDto> expression = ConstructorExpression.create(VolunteerDto.class, 
            volunteer.id, volunteer.publicIdentifier, volunteer.eklSpecialNo,
            volunteer.lastName, volunteer.firstName, volunteer.email);
        
        return query.list(expression);
    }

    private Expression<VolunteerDto> getVolunteerDtoConstructorExpression(QVolunteer volunteer) {
        return ConstructorExpression.create(VolunteerDto.class, 
                volunteer.id,
                volunteer.publicIdentifier,
                volunteer.lastName,
                volunteer.firstName,
                volunteer.fatherFirstName,
                volunteer.motherFirstName,
                volunteer.birthYear,
                volunteer.email,
                volunteer.telephone,
                volunteer.cellphone,
                volunteer.eklSpecialNo,
                volunteer.addressStreet,
                volunteer.addressNumber,
                volunteer.postalCode,
                volunteer.area,
                volunteer.municipality,
                volunteer.division,
                volunteer.notNumberedAddress,
                volunteer.job,
                volunteer.miscellaneousSkills,
                volunteer.registrationTimestamp);
    }
    
    private FactoryExpression<VolunteerListDto> getVolunteerListDtoFactoryExpression(QVolunteer volunteer) {
        return new QVolunteerListDto(
                volunteer.id, 
                volunteer.publicIdentifier, 
                volunteer.eklSpecialNo,
                volunteer.lastName, 
                volunteer.firstName, 
                volunteer.email);
    }
    
    @Override
    public Long getVolunteerIdByEmail(String email) {
        
        if(DpTextUtils.isEmpty(email)) {
            return null;
        }
        
        QVolunteer volunteer = QVolunteer.volunteer;
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(volunteer).where(volunteer.email.eq(email)).singleResult(volunteer.id);
    }
}
