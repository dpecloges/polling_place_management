package com.ots.dpel.ep.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.args.VerificationArgs;
import com.ots.dpel.ep.core.domain.QElector;
import com.ots.dpel.ep.core.domain.QVoter;
import com.ots.dpel.ep.core.enums.VerificationSearchType;
import com.ots.dpel.ep.dto.indexing.ElectorIndexedDocument;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.management.core.enums.ElectionRound;
import org.springframework.data.solr.core.query.Criteria;

import java.util.Date;

public class ElectorPredicates {
    
    public static BooleanExpression lastNameEquals(final String searchTerm) {
        return QElector.elector.lastName.upper().eq(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression firstNameStartsWith(final String searchTerm) {
        return QElector.elector.firstName.upper().startsWith(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression fatherFirstNameStartsWith(final String searchTerm) {
        return QElector.elector.fatherFirstName.upper().startsWith(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression motherFirstNameStartsWith(final String searchTerm) {
        return QElector.elector.motherFirstName.upper().startsWith(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression birthYearEquals(final Integer searchTerm) {
        return QElector.elector.birthYear.eq(searchTerm);
    }
    
    public static BooleanExpression birthMonthEquals(final Integer searchTerm) {
        return QElector.elector.birthMonth.eq(searchTerm);
    }
    
    public static BooleanExpression birthDayEquals(final Integer searchTerm) {
        return QElector.elector.birthDay.eq(searchTerm);
    }
    
    public static BooleanExpression eklSpecialNoEquals(final String searchTerm) {
        return QElector.elector.eklSpecialNo.upper().eq(searchTerm);
    }
    
    public static BooleanExpression noRecord() {
        return QElector.elector.id.isNull();
    }
    
    public static BooleanBuilder createElectorVerificationPredicate(VerificationArgs args) {
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        if (args.getSearchType().equals(VerificationSearchType.EKLSPECIALNO.name())) {
        
            String eklSpecialNo = args.getEklSpecialNo();
        
            if (!DpTextUtils.isEmpty(eklSpecialNo)) {
                try {
                    eklSpecialNo = String.format("%013d", Long.valueOf(eklSpecialNo));
                    predicate.and(eklSpecialNoEquals(eklSpecialNo));
                }
                catch(NumberFormatException ex) {
                    predicate.and(QElector.elector.id.isNull());
                }
            }
        }
        else if (args.getSearchType().equals(VerificationSearchType.VOTER_VERIFICATION_NUMBER.name())) {
            
            if(args.getVoterVerificationNumber() != null && args.getVoterElectionDepartmentId() != null &&
                    args.getVoterElectionProcedureId() != null && !DpTextUtils.isEmpty(args.getVoterRound())) {
                
                QElector elector = QElector.elector;
                QVoter voter = QVoter.voter;
                
                predicate.and(elector.id.eq(
                        new JPASubQuery()
                                .from(voter)
                                .where(voter.verificationNumber.eq(args.getVoterVerificationNumber())
                                        .and(voter.electionDepartmentId.eq(args.getVoterElectionDepartmentId()))
                                        .and(voter.electionProcedureId.eq(args.getVoterElectionProcedureId()))
                                        .and(voter.round.eq(ElectionRound.valueOf(args.getVoterRound())))
                                        .and(voter.voted.eq(YesNoEnum.YES)))
                                .unique(voter.electorId)
                ));
            }
        }
        else if (args.getSearchType().equals(VerificationSearchType.PERSONAL_INFO.name())) {
            
            if (!DpTextUtils.isEmpty(args.getLastName())) {
                predicate.and(lastNameEquals(args.getLastName()));
            }
            
            if (!DpTextUtils.isEmpty(args.getFirstName())) {
                predicate.and(firstNameStartsWith(args.getFirstName()));
            }
            
            if (!DpTextUtils.isEmpty(args.getFatherFirstName())) {
                predicate.and(fatherFirstNameStartsWith(args.getFatherFirstName()));
            }
            
            if (!DpTextUtils.isEmpty(args.getMotherFirstName())) {
                predicate.and(motherFirstNameStartsWith(args.getMotherFirstName()));
            }
            
            if (args.getBirthYear() != null) {
                predicate.and(birthYearEquals(args.getBirthYear()));
            }
            
            if (args.getBirthDate() != null) {
    
                Integer birthYear = DpDateUtils.getDateYear(args.getBirthDate()).intValue();
                Integer birthMonth = DpDateUtils.getDateMonth(args.getBirthDate()).intValue() + 1;
                Integer birthDay = DpDateUtils.getDateDay(args.getBirthDate()).intValue();
    
                predicate.and(birthYearEquals(birthYear));
                predicate.and(birthMonthEquals(birthMonth));
                predicate.and(birthDayEquals(birthDay));
            }
        }
        else {
            predicate.and(QElector.elector.id.isNull());
        }
        
        return predicate;
    }
    
    public static Criteria createMatchingElectorsSolrCriteria(VerificationArgs args) {
        
        Criteria criteria = null;
        
        if (args.getSearchType().equals(VerificationSearchType.EKLSPECIALNO.name())) {
        
            String eklSpecialNo = args.getEklSpecialNo();
        
            if (!DpTextUtils.isEmpty(eklSpecialNo)) {
                try {
                    eklSpecialNo = String.format("%013d", Long.valueOf(eklSpecialNo));
                    criteria = (criteria == null) ?
                            new Criteria(ElectorIndexedDocument.FIELD_EKL_SPECIAL_NO).is(eklSpecialNo) :
                            criteria.and(ElectorIndexedDocument.FIELD_EKL_SPECIAL_NO).is(eklSpecialNo);
                }
                catch(NumberFormatException ex) {
                    criteria = (criteria == null) ?
                            new Criteria(ElectorIndexedDocument.FIELD_ID).isNull() :
                            criteria.and(ElectorIndexedDocument.FIELD_ID).isNull();
                }
            }
        
        }
        else if (args.getSearchType().equals(VerificationSearchType.PERSONAL_INFO.name())) {
            
            if (!DpTextUtils.isEmpty(args.getLastName())) {
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_LAST_NAME_PLAIN).is(args.getLastName()) :
                        criteria.and(ElectorIndexedDocument.FIELD_LAST_NAME_PLAIN).is(args.getLastName());
            }
            
            if (!DpTextUtils.isEmpty(args.getFirstName())) {
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_FIRST_NAME).is(args.getFirstName()) :
                        criteria.and(ElectorIndexedDocument.FIELD_FIRST_NAME).is(args.getFirstName());
            }
            
            if (!DpTextUtils.isEmpty(args.getFatherFirstName())) {
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_FATHER_FIRST_NAME).is(args.getFatherFirstName()) :
                        criteria.and(ElectorIndexedDocument.FIELD_FATHER_FIRST_NAME).is(args.getFatherFirstName());
            }
            
            if (!DpTextUtils.isEmpty(args.getMotherFirstName())) {
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_MOTHER_FIRST_NAME).is(args.getMotherFirstName()) :
                        criteria.and(ElectorIndexedDocument.FIELD_MOTHER_FIRST_NAME).is(args.getMotherFirstName());
            }
            
            if (args.getBirthYear() != null) {
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_BIRTH_YEAR).is(args.getBirthYear()) :
                        criteria.and(ElectorIndexedDocument.FIELD_BIRTH_YEAR).is(args.getBirthYear());
            }
    
            if (args.getBirthDate() != null) {
    
                Integer birthYear = DpDateUtils.getDateYear(args.getBirthDate()).intValue();
                Integer birthMonth = DpDateUtils.getDateMonth(args.getBirthDate()).intValue() + 1;
                Integer birthDay = DpDateUtils.getDateDay(args.getBirthDate()).intValue();
                
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_BIRTH_YEAR).is(birthYear) :
                        criteria.and(ElectorIndexedDocument.FIELD_BIRTH_YEAR).is(birthYear);
    
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_BIRTH_MONTH).is(birthMonth) :
                        criteria.and(ElectorIndexedDocument.FIELD_BIRTH_MONTH).is(birthMonth);
    
                criteria = (criteria == null) ?
                        new Criteria(ElectorIndexedDocument.FIELD_BIRTH_DAY).is(birthDay) :
                        criteria.and(ElectorIndexedDocument.FIELD_BIRTH_DAY).is(birthDay);
            }
            
        }
        else {
            criteria = (criteria == null) ?
                    new Criteria(ElectorIndexedDocument.FIELD_ID).isNull() :
                    criteria.and(ElectorIndexedDocument.FIELD_ID).isNull();
        }
        
        return criteria;
    }
}
