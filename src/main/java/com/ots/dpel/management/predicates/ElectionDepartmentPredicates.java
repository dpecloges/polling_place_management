package com.ots.dpel.management.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.core.domain.QVoter;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.management.args.ElectionCenterArgs;
import com.ots.dpel.management.args.ElectionDepartmentArgs;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.core.enums.ElectionRound;

public class ElectionDepartmentPredicates {
    
    public static BooleanExpression geographicalUnitIdEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.geographicalUnitId.eq(searchTerm);
    }
    
    public static BooleanExpression decentralAdminIdEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.decentralAdminId.eq(searchTerm);
    }
    
    public static BooleanExpression regionIdEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.regionId.eq(searchTerm);
    }
    
    public static BooleanExpression regionalUnitIdEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.regionalUnitId.eq(searchTerm);
    }
    
    public static BooleanExpression municipalityIdEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.municipalityId.eq(searchTerm);
    }
    
    public static BooleanExpression municipalUnitIdEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.municipalUnitId.eq(searchTerm);
    }
    
    public static BooleanExpression municipalCommunityEquals(final Long searchTerm) {
        return QElectionCenter.electionCenter.municipalCommunityId.eq(searchTerm);
    }
    
    public static BooleanExpression foreignEquals(final Boolean searchTerm) {
        YesNoEnum foreign = YesNoEnum.of(searchTerm);
        return QElectionCenter.electionCenter.foreign.eq(foreign);
    }
    
    public static BooleanExpression foreignCountryIsoCodeEquals(final String isoCode) {
        return QElectionCenter.electionCenter.foreignCountry.isoCode.eq(isoCode);
    }
    
    public static BooleanExpression foreignCityContains(final String foreignCityName) {
        return QElectionCenter.electionCenter.foreignCity.upper().contains(DpTextUtils.toUpperCaseGreekSupport(foreignCityName));
    }
    
    public static BooleanExpression votersExist() {
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QVoter voter = QVoter.voter;
        
        return new JPASubQuery().from(voter).where(voter.electionDepartmentId.eq(electionDepartment.id).and(voter.voted.eq(YesNoEnum.YES))).exists();
    }
    
    public static BooleanExpression votersDoNotExist() {
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QVoter voter = QVoter.voter;
        
        return new JPASubQuery().from(voter).where(voter.electionDepartmentId.eq(electionDepartment.id).and(voter.voted.eq(YesNoEnum.YES))).notExists();
    }
    
    public static BooleanExpression hasSubmittedFirst() {
        return QElectionDepartment.electionDepartment.submittedFirst.eq(YesNoEnum.YES);
    }
    
    public static BooleanExpression hasNotSubmittedFirst() {
        return QElectionDepartment.electionDepartment.submittedFirst.isNull().or(QElectionDepartment.electionDepartment.submittedFirst.eq(YesNoEnum.NO));
    }
    
    public static BooleanExpression hasSubmittedSecond() {
        return QElectionDepartment.electionDepartment.submittedSecond.eq(YesNoEnum.YES);
    }
    
    public static BooleanExpression hasNotSubmittedSecond() {
        return QElectionDepartment.electionDepartment.submittedSecond.isNull().or(QElectionDepartment.electionDepartment.submittedSecond.eq(YesNoEnum.NO));
    }
    
    public static BooleanBuilder createElectionDepartmentIndexPredicate(SearchableArguments arguments) {
        
        ElectionDepartmentArgs args = (ElectionDepartmentArgs) arguments;
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        Boolean foreign = Boolean.TRUE.equals(args.getForeign());
        predicate.and(foreignEquals(foreign));
        
        if (Boolean.TRUE.equals(foreign)) {
            if (!DpTextUtils.isEmpty(args.getForeignCountryIsoCode())) {
                predicate.and(foreignCountryIsoCodeEquals(args.getForeignCountryIsoCode()));
            }
            
            if (!DpTextUtils.isEmpty(args.getForeignCity())) {
                predicate.and(foreignCityContains(args.getForeignCity()));
            }
        } else {
            if (args.getGeographicalUnitId() != null) {
                predicate.and(geographicalUnitIdEquals(args.getGeographicalUnitId()));
            }
            
            if (args.getDecentralAdminId() != null) {
                predicate.and(decentralAdminIdEquals(args.getDecentralAdminId()));
            }
            
            if (args.getRegionId() != null) {
                predicate.and(regionIdEquals(args.getRegionId()));
            }
            
            if (args.getRegionalUnitId() != null) {
                predicate.and(regionalUnitIdEquals(args.getRegionalUnitId()));
            }
            
            if (args.getMunicipalityId() != null) {
                predicate.and(municipalityIdEquals(args.getMunicipalityId()));
            }
            
            if (args.getMunicipalUnitId() != null) {
                predicate.and(municipalUnitIdEquals(args.getMunicipalUnitId()));
            }
        }
        
        if(args.getVotersExist() != null) {
            if(args.getVotersExist().equals(YesNoEnum.YES)) {
                predicate.and(votersExist());
            }
            else if(args.getVotersExist().equals(YesNoEnum.NO)) {
                predicate.and(votersDoNotExist());
            }
        }
        
        if(args.getSubmitted() != null && args.getElectionRound() != null) {
            if(args.getElectionRound().equals(ElectionRound.FIRST)) {
                if(args.getSubmitted().equals(YesNoEnum.YES)) {
                    predicate.and(hasSubmittedFirst());
                }
                else if(args.getSubmitted().equals(YesNoEnum.NO)) {
                    predicate.and(hasNotSubmittedFirst());
                }
            }
            else if(args.getElectionRound().equals(ElectionRound.SECOND)) {
                if(args.getSubmitted().equals(YesNoEnum.YES)) {
                    predicate.and(hasSubmittedSecond());
                }
                else if(args.getSubmitted().equals(YesNoEnum.NO)) {
                    predicate.and(hasNotSubmittedSecond());
                }
            }
        }
        
        return predicate;
    }
}
