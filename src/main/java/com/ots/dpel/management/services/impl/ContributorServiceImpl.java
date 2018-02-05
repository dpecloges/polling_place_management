package com.ots.dpel.management.services.impl;

import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.ots.dpel.management.core.domain.Contributor;
import com.ots.dpel.management.core.domain.QContributor;
import com.ots.dpel.management.core.enums.ContributorType;
import com.ots.dpel.management.dto.ContributorDto;
import com.ots.dpel.management.dto.list.ContributorListDto;
import com.ots.dpel.management.persistence.ContributorRepository;
import com.ots.dpel.management.services.ContributorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class ContributorServiceImpl implements ContributorService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private ContributorRepository contributorRepository;
    
    
    @Override
    public Expression getContributorDtoConstructorExpression(QContributor contributor) {
        return ConstructorExpression.create(ContributorDto.class,
                contributor.id,
                contributor.electionProcedureId,
                contributor.type,
                contributor.lastName,
                contributor.firstName,
                contributor.fatherFirstName,
                contributor.telephone,
                contributor.cellphone,
                contributor.email,
                contributor.address,
                contributor.postalCode,
                contributor.comments
        );
    }
    
    @Override
    public Expression getContributorListDtoConstructorExpression(QContributor contributor) {
        return ConstructorExpression.create(ContributorListDto.class,
                contributor.id,
                contributor.lastName,
                contributor.firstName,
                contributor.email,
                contributor.cellphone
        );
    }
    
    @Override
    public void dtoToEntity(ContributorDto contributorDto, Contributor contributor) {
        
        if (contributorDto == null) {
            return;
        }
        
        if (contributor == null) {
            contributor = new Contributor();
        }
        
        BeanUtils.copyProperties(contributorDto, contributor);
        contributor.setType(contributorDto.getType() == null ? null : ContributorType.valueOf(contributorDto.getType()));
    }
}
