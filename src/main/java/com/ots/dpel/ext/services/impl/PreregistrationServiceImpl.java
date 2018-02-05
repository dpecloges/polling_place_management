package com.ots.dpel.ext.services.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.ots.dpel.ext.core.domain.QPreregistration;
import com.ots.dpel.ext.dto.PreregistrationDto;
import com.ots.dpel.ext.persistence.PreregistrationRepository;
import com.ots.dpel.ext.services.PreregistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PreregistrationServiceImpl implements PreregistrationService {
    
    @Autowired
    @Qualifier("externalEntityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private PreregistrationRepository preregistrationRepository;
    
    
    public PreregistrationDto findByEklSpecialNo(Long eklSpecialNo) {
        
        if(eklSpecialNo == null) {
            return null;
        }
        
        QPreregistration preregistration = QPreregistration.preregistration;
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(preregistration).where(preregistration.eklSpecialNo.eq(eklSpecialNo));
        
        Expression<PreregistrationDto> expression = getVolunteerDtoConstructorExpression(preregistration);
        
        PreregistrationDto preregistrationDto = query.singleResult(expression);
        return preregistrationDto;
    }
    
    private Expression getVolunteerDtoConstructorExpression(QPreregistration preregistration) {
        return ConstructorExpression.create(PreregistrationDto.class,
                preregistration.id,
                preregistration.notMember,
                preregistration.address,
                preregistration.addressNo,
                preregistration.area,
                preregistration.postalCode,
                preregistration.countryIsoCode,
                preregistration.cellphone,
                preregistration.email,
                preregistration.idType,
                preregistration.idNumber,
                preregistration.paymentValue
        );
    }
}
