package com.ots.dpel.auth.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.auth.args.UserArgs;
import com.ots.dpel.auth.core.domain.QUser;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.global.utils.DpTextUtils;

/**
 * Created by lzagkaretos on 15/9/2016.
 */
public class UserPredicates {
    
    public static BooleanExpression usernameContains(final String searchTerm) {
        return QUser.user.username.contains(searchTerm);
    }
    
    public static BooleanExpression lastNameContains(final String searchTerm) {
        return QUser.user.lastName.upper().contains(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression firstNameContains(final String searchTerm) {
        return QUser.user.firstName.upper().contains(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression emailContains(final String searchTerm) {
        return QUser.user.email.contains(searchTerm);
    }
    
    public static BooleanExpression electionDepartmentIdIsNotNull() {
        return QUser.user.electionDepartmentId.isNotNull();
    }
    
    public static BooleanExpression electionDepartmentIdIsNull() {
        return QUser.user.electionDepartmentId.isNull();
    }
    
    public static BooleanExpression electionCenterIdEquals(final Long searchTerm) {
        return QUser.user.electionDepartment.electionCenter.id.eq(searchTerm);
    }
    
    public static BooleanExpression electionDepartmentIdEquals(final Long searchTerm) {
        return QUser.user.electionDepartment.id.eq(searchTerm);
    }
    
    public static BooleanBuilder createUserIndexPredicate(SearchableArguments arguments) {
        
        UserArgs args = (UserArgs) arguments;
        BooleanBuilder predicate = new BooleanBuilder();
        
        if (!DpTextUtils.isEmpty(args.getUsername())) {
            predicate.and(usernameContains(args.getUsername()));
        }
        
        if (!DpTextUtils.isEmpty(args.getLastName())) {
            predicate.and(lastNameContains(args.getLastName()));
        }
        
        if (!DpTextUtils.isEmpty(args.getFirstName())) {
            predicate.and(firstNameContains(args.getFirstName()));
        }
    
        if (!DpTextUtils.isEmpty(args.getEmail())) {
            predicate.and(emailContains(args.getEmail()));
        }
        
        if (args.getHasElectionDepartmentId() != null) {
            if (args.getHasElectionDepartmentId().equals(YesNoEnum.YES)) {
                predicate.and(electionDepartmentIdIsNotNull());
            }
            else if (args.getHasElectionDepartmentId().equals(YesNoEnum.NO)) {
                predicate.and(electionDepartmentIdIsNull());
            }
        }
        
        if (args.getElectionCenterId() != null) {
            predicate.and(electionCenterIdEquals(args.getElectionCenterId()));
        }
        
        if (args.getElectionDepartmentId() != null) {
            predicate.and(electionDepartmentIdEquals(args.getElectionDepartmentId()));
        }
        
        return predicate;
    }
}
