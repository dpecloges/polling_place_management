package com.ots.dpel.common.services.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.common.core.domain.QAdminUnit;
import com.ots.dpel.common.core.dto.AdminUnitDto;
import com.ots.dpel.common.core.dto.QAdminUnitDto;
import com.ots.dpel.common.persistence.AdminUnitRepository;
import com.ots.dpel.common.services.AdminUnitService;
import com.ots.dpel.ep.core.enums.SnapshotType;
import com.ots.dpel.results.core.enums.ResultType;
import com.ots.dpel.system.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class AdminUnitServiceImpl implements AdminUnitService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private AdminUnitRepository adminUnitRepository;
    
    @Override
    @Transactional(transactionManager = "txMgr")
    @Cacheable(value = CacheService.ADMIN_UNITS_CACHE)
    public List<AdminUnitDto> getAll() {
        QAdminUnit adminUnit = QAdminUnit.adminUnit;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        // Αποστολή μόνο τον επιπέδων 1 - 6.
        query.from(adminUnit)
            .where(adminUnit.level.in(1, 2, 3, 4, 5, 6))
            .orderBy(adminUnit.name.asc());
        
        FactoryExpression<AdminUnitDto> factoryExpression = new QAdminUnitDto(
            adminUnit.id,
            adminUnit.parentId,
            adminUnit.level,
            adminUnit.code,
            adminUnit.name);
        
        return adminUnitRepository.findAll(query, factoryExpression);
    }
    
    @Override
    public ResultType getResultType(Short level) {
        if (level == null) {
            return null;
        }
        
        switch (level) {
            case 1:
                return ResultType.GEOGRAPHICAL_UNIT;
            case 2:
                return ResultType.DECENTRAL_ADMIN;
            case 3:
                return ResultType.REGION;
            case 4:
                return ResultType.REGIONAL_UNIT;
            case 5:
                return ResultType.MUNICIPALITY;
            case 6:
                return ResultType.MUNICIPAL_UNIT;
            default:
                return null;
        }
    }
    
    @Override
    public SnapshotType getSnapshotType(Short level) {
        if (level == null) {
            return null;
        }
        
        switch (level) {
            case 1:
                return SnapshotType.GEOGRAPHICAL_UNIT;
            case 2:
                return SnapshotType.DECENTRAL_ADMIN;
            case 3:
                return SnapshotType.REGION;
            case 4:
                return SnapshotType.REGIONAL_UNIT;
            case 5:
                return SnapshotType.MUNICIPALITY;
            case 6:
                return SnapshotType.MUNICIPAL_UNIT;
            default:
                return null;
        }
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    @Cacheable(value = CacheService.ADMIN_UNITS_CACHE)
    public List<AdminUnitDto> getByParentId(Long parentId) {
        
        QAdminUnit adminUnit = QAdminUnit.adminUnit;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(adminUnit)
            .where(adminUnit.parentId.eq(parentId))
            .orderBy(adminUnit.name.asc());
        
        FactoryExpression<AdminUnitDto> factoryExpression = new QAdminUnitDto(
            adminUnit.id,
            adminUnit.parentId,
            adminUnit.level,
            adminUnit.code,
            adminUnit.name);
        
        return adminUnitRepository.findAll(query, factoryExpression);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    @Cacheable(value = CacheService.ADMIN_UNITS_CACHE)
    public List<AdminUnitDto> getByType(String type) {
        
        QAdminUnit adminUnit = QAdminUnit.adminUnit;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(adminUnit)
            .where(adminUnit.type.eq(type))
            .orderBy(adminUnit.name.asc());
        
        FactoryExpression<AdminUnitDto> factoryExpression = new QAdminUnitDto(
            adminUnit.id,
            adminUnit.parentId,
            adminUnit.level,
            adminUnit.code,
            adminUnit.name);
        
        return adminUnitRepository.findAll(query, factoryExpression);
    }
}
