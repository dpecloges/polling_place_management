package com.ots.dpel.global.querydsl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.sql.JPASQLQuery;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.FactoryExpression;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Υλοποίηση των μεθόδων που ορίζονται στο {@link DpQueryDslJpaRepository}
 * @param <T>
 * @param <ID>
 * @author lzagkaretos
 */
public class DpQueryDslJpaRepositoryImpl<T, ID extends Serializable>
    extends QueryDslJpaRepository<T, ID> implements DpQueryDslJpaRepository<T, ID> {
    
    //All instance variables are available in super, but they are private
    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;
    
    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;
    
    private EntityManager entityManager;
    
    public DpQueryDslJpaRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }
    
    public DpQueryDslJpaRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {
        
        super(entityInformation, entityManager);
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder(this.path.getType(), this.path.getMetadata());
        this.querydsl = new Querydsl(entityManager, this.builder);
        this.entityManager = entityManager;
    }
    
    @Override
    public <T1> Page<T1> findAll(JPQLQuery query, FactoryExpression<T1> factoryExpression, Pageable pageable) {
        
        JPQLQuery countQuery = query;
        JPQLQuery basicQuery = querydsl.applyPagination(pageable, query);
        
        List<T1> t = basicQuery.list(factoryExpression);
        Long total = countQuery.count();
        
        List<T1> content = total > pageable.getOffset() ? t : Collections.<T1>emptyList();
        
        return new PageImpl<T1>(content, pageable, total);
    }
    
    @Override
    public <T1> Page<T1> findAll(JPQLQuery query, JPQLQuery countQuery, FactoryExpression<T1> factoryExpression, Pageable pageable) {
        
        JPQLQuery basicQuery = querydsl.applyPagination(pageable, query);
        
        List<T1> t = basicQuery.list(factoryExpression);
        Long total = countQuery.count();
        
        List<T1> content = total > pageable.getOffset() ? t : Collections.<T1>emptyList();
        
        return new PageImpl<T1>(content, pageable, total);
    }
    
    @Override
    public <T1> List<T1> findAll(JPQLQuery query, FactoryExpression<T1> factoryExpression) {
        return query.list(factoryExpression);
    }
    
    @Override
    public <T1> List<T1> findList(JPQLQuery query, FactoryExpression<T1> factoryExpression) {
        List<T1> result = query.list(factoryExpression);
        return result != null ? result : Collections.<T1>emptyList();
    }
    
    @Override
    public <T1> T1 findOne(JPQLQuery query, FactoryExpression<T1> factoryExpression) {
        
        return query.singleResult(factoryExpression);
    }
    
    @Override
    public <T1> Page<T1> findAll(JPASQLQuery query, FactoryExpression<T1> factoryExpression, Pageable pageable, RelationalPathBase
        relationalPathBase) {
        
        JPASQLQuery countQuery = query.clone();
        
        //Παράμετροι για την ταξινόμηση
        List<Path<?>> columns = relationalPathBase.getColumns();
        
        for (Sort.Order o : pageable.getSort()) {
            for (Path<?> column : columns) {
                if (StringUtils.endsWith(column.toString(), o.getProperty())) {
                    query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, column));
                }
            }
        }
        
        //Παραμετροι για το paging
        query.limit(pageable.getPageSize());
        query.offset(pageable.getOffset());
        
        List<T1> t = query.list(factoryExpression);
        Long total = countQuery.count();
        
        List<T1> content = total > pageable.getOffset() ? t : Collections.<T1>emptyList();
        
        return new PageImpl<T1>(content, pageable, total);
    }
    
    @Override
    public <T1> Page<T1> findAllNativeSql(String sql, Map<String, Object> parameters, Class resultClass, Pageable pageable) {
        
        //Το ερώτημα για το πλήθος προκύπτει με την αντικατάσταση του τμήματος "SELECT ..." με "SELECT COUNT(*)"
        String countSql = "SELECT COUNT(*) " + sql.substring(sql.indexOf("FROM"));
        
        if (pageable.getSort() != null) {
            int sortOrderCount = 0;
            for (Sort.Order o : pageable.getSort()) {
                sql = sql.concat(sortOrderCount == 0 ? " ORDER BY " : ", ");
                sql = sql.concat(o.getProperty()).concat(o.isAscending() ? " ASC " : " DESC ");
                sortOrderCount++;
            }
        }
        
        Query query = entityManager.createNativeQuery(sql, resultClass);
        Query countQuery = entityManager.createNativeQuery(countSql);
        
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getOffset());
        
        List<T1> t = query.getResultList();
        Long total = ((BigDecimal) countQuery.getSingleResult()).longValue();
        
        List<T1> content = total > pageable.getOffset() ? t : Collections.<T1>emptyList();
        
        return new PageImpl<T1>(content, pageable, total);
    }
}
