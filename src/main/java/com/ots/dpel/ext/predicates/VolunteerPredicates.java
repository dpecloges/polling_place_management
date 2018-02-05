package com.ots.dpel.ext.predicates;

import org.apache.commons.lang3.StringUtils;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.ext.args.VolunteerArgs;
import com.ots.dpel.ext.core.domain.QVolunteer;
import com.ots.dpel.global.utils.DpTextUtils;

public final class VolunteerPredicates {
    
    private VolunteerPredicates() {}
    
    public static BooleanExpression publicIdentifierEquals(final Long publicIdentifier) {
        return QVolunteer.volunteer.publicIdentifier.eq(publicIdentifier);
    }
    
    public static BooleanExpression eklSpecialNoEquals(final String electorNumber) {
        return QVolunteer.volunteer.eklSpecialNo.eq(electorNumber);
    }
    
    public static BooleanExpression lastNameContains(final String lastName) {
        return QVolunteer.volunteer.lastName.upper().contains(DpTextUtils.toUpperCaseGreekSupport(lastName));
    }
    
    public static BooleanExpression firstNameContains(final String firstName) {
        return QVolunteer.volunteer.firstName.upper().contains(DpTextUtils.toUpperCaseGreekSupport(firstName));
    }

    public static BooleanBuilder createVolunteerIndexPredicate(SearchableArguments arguments) {
        
        VolunteerArgs args = (VolunteerArgs) arguments;
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        if (args.getPublicIdentifier() != null) {
            predicate.and(publicIdentifierEquals(args.getPublicIdentifier()));
        }
        
        if (StringUtils.isNotBlank(args.getEklSpecialNo())) {
            predicate.and(eklSpecialNoEquals(args.getEklSpecialNo()));
        }
        
        if (StringUtils.isNotBlank(args.getLastName())) {
            predicate.and(lastNameContains(args.getLastName()));
        }
        
        if (StringUtils.isNotBlank(args.getFirstName())) {
            predicate.and(firstNameContains(args.getFirstName()));
        }
        
        return predicate;
    }
}
