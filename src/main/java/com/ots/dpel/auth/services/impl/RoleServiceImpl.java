package com.ots.dpel.auth.services.impl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.auth.core.domain.QRole;
import com.ots.dpel.auth.dto.QRoleDto;
import com.ots.dpel.auth.dto.RoleDto;
import com.ots.dpel.auth.persistence.RoleRepository;
import com.ots.dpel.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public List<RoleDto> getAll() {
        
        QRole role = QRole.role;
        
        JPQLQuery query = new JPAQuery(entityManager);
        
        query.from(role);
        
        FactoryExpression<RoleDto> factoryExpression = new QRoleDto(
                role.id,
                role.code,
                role.name
        );
        
        return roleRepository.findAll(query, factoryExpression);
    }
    
    @Override
    public Long getRoleIdByUser(Long userId) {
        
        if (userId == null) {
            return null;
        }
        
        String sql = "" +
                "SELECT n_role_id "+
                "FROM dp.userrole " +
                "WHERE n_user_id = :userId";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        
        try {
            Object result = query.getSingleResult();
            return result == null ? null : ((BigInteger) result).longValue();
        }
        catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public String getCode(Long id) {
        
        if (id == null) {
            return null;
        }
    
        QRole role = QRole.role;
        JPQLQuery query = new JPAQuery(entityManager);
        
        return query.from(role).where(role.id.eq(id)).singleResult(role.code);
    }
}
