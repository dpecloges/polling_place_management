package com.ots.dpel.management.services;

import com.mysema.query.types.Expression;
import com.ots.dpel.management.core.domain.Contributor;
import com.ots.dpel.management.core.domain.QContributor;
import com.ots.dpel.management.dto.ContributorDto;

public interface ContributorService {
    
    Expression getContributorDtoConstructorExpression(QContributor contributor);
    
    Expression getContributorListDtoConstructorExpression(QContributor contributor);
    
    void dtoToEntity(ContributorDto contributorDto, Contributor contributor);
}
