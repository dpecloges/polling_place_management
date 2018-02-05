package com.ots.dpel.management.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.management.args.ContributionArgs;
import com.ots.dpel.management.core.domain.QContribution;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.core.enums.ContributionType;

public class ContributionPredicates {

    public static BooleanExpression electionCenterIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.id.eq(searchTerm);
    }
    
    public static BooleanExpression electionDepartmentIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.id.eq(searchTerm);
    }
    
    public static BooleanExpression geographicalUnitIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.geographicalUnitId.eq(searchTerm);
    }
    
    public static BooleanExpression decentralAdminIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.decentralAdminId.eq(searchTerm);
    }
    
    public static BooleanExpression regionIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.regionId.eq(searchTerm);
    }
    
    public static BooleanExpression regionalUnitIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.regionalUnitId.eq(searchTerm);
    }
    
    public static BooleanExpression municipalityIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.municipalityId.eq(searchTerm);
    }
    
    public static BooleanExpression municipalUnitIdEquals(final Long searchTerm) {
        return QContribution.contribution.electionDepartment.electionCenter.municipalUnitId.eq(searchTerm);
    }
    
    public static BooleanExpression foreignEquals(final Boolean searchTerm) {
        YesNoEnum foreign = YesNoEnum.of(searchTerm);
        return QContribution.contribution.electionDepartment.electionCenter.foreign.eq(foreign);
    }
    
    public static BooleanExpression foreignCountryIsoCodeEquals(final String isoCode) {
        return QContribution.contribution.electionDepartment.electionCenter.foreignCountryIsoCode.eq(isoCode);
    }
    
    public static BooleanExpression foreignCityContains(final String foreignCityName) {
        return QContribution.contribution.electionDepartment.electionCenter.foreignCity.upper().contains(DpTextUtils.toUpperCaseGreekSupport(foreignCityName));
    }
    
    public static BooleanExpression contributionTypeEquals(final ContributionType contributionType) {
        return QContribution.contribution.type.eq(contributionType);
    }
    
    public static BooleanExpression contributionStatusEquals(final ContributionStatus contributionStatus) {
        return QContribution.contribution.status.eq(contributionStatus);
    }
    
    public static BooleanBuilder createContributionIndexPredicate(SearchableArguments arguments) {
        
        ContributionArgs args = (ContributionArgs) arguments;
        BooleanBuilder predicate = new BooleanBuilder();
        
        if (args.getElectionCenterId() != null) {
            predicate.and(electionCenterIdEquals(args.getElectionCenterId()));
        }
        
        if (args.getElectionDepartmentId() != null) {
            predicate.and(electionDepartmentIdEquals(args.getElectionDepartmentId()));
        }
        
        Boolean foreign = args.getForeign();
        boolean addForeignCriteria = foreign != null && foreign.booleanValue();
        boolean addLocalCriteria = foreign != null && !foreign.booleanValue();
        if (addForeignCriteria) {
            
            predicate.and(foreignEquals(Boolean.TRUE));
            
            if (!DpTextUtils.isEmpty(args.getForeignCountryIsoCode())) {
                predicate.and(foreignCountryIsoCodeEquals(args.getForeignCountryIsoCode()));
            }
            
            if (!DpTextUtils.isEmpty(args.getForeignCity())) {
                predicate.and(foreignCityContains(args.getForeignCity()));
            }
        }
        if (addLocalCriteria) {
            
            predicate.and(foreignEquals(Boolean.FALSE));
            
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
        
        if (args.getContributionType() != null) {
            predicate.and(contributionTypeEquals(args.getContributionType()));
        }
        
        if (args.getContributionStatus() != null) {
            predicate.and(contributionStatusEquals(args.getContributionStatus()));
        }
        
        return predicate;
    }
}
