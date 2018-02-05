package com.ots.dpel.management.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.management.args.ContributorArgs;
import com.ots.dpel.management.core.domain.QContributor;

public class ContributorPredicates {
    
    public static BooleanExpression electionProcedureIdEquals(final Long searchTerm) {
        return QContributor.contributor.electionProcedureId.eq(searchTerm);
    }
    
    public static BooleanExpression lastNameStartsWith(final String searchTerm) {
        return QContributor.contributor.lastName.upper().startsWith(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression firstNameStartsWith(final String searchTerm) {
        return QContributor.contributor.firstName.upper().startsWith(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanBuilder createContributorIndexPredicate(SearchableArguments arguments) {
        
        ContributorArgs args = (ContributorArgs) arguments;
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        if (args.getElectionProcedureId() != null) {
            predicate.and(electionProcedureIdEquals(args.getElectionProcedureId()));
        }
        
        if (!DpTextUtils.isEmpty(args.getLastName())) {
            predicate.and(lastNameStartsWith(args.getLastName()));
        }
        
        if (!DpTextUtils.isEmpty(args.getFirstName())) {
            predicate.and(firstNameStartsWith(args.getFirstName()));
        }
        
        return predicate;
    }
}
