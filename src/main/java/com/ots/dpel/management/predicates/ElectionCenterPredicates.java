package com.ots.dpel.management.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.management.args.ElectionCenterArgs;
import com.ots.dpel.management.core.domain.QElectionCenter;

public class ElectionCenterPredicates {
    
    public static BooleanExpression codeEquals(final String searchTerm) {
        return QElectionCenter.electionCenter.code.eq(searchTerm);
    }
    
    public static BooleanExpression nameContains(final String searchTerm) {
        return QElectionCenter.electionCenter.name.upper().contains(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    public static BooleanExpression addressContains(final String searchTerm) {
        return QElectionCenter.electionCenter.address.upper().contains(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
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

    public static BooleanBuilder createElectionCenterIndexPredicate(SearchableArguments arguments) {
        
        ElectionCenterArgs args = (ElectionCenterArgs) arguments;
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        if (!DpTextUtils.isEmpty(args.getCode())) {
            predicate.and(codeEquals(args.getCode()));
        }
        
        if (!DpTextUtils.isEmpty(args.getName())) {
            predicate.and(nameContains(args.getName()));
        }
        
        if (!DpTextUtils.isEmpty(args.getAddress())) {
            predicate.and(addressContains(args.getAddress()));
        }
        
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
        
        return predicate;
    }
}
