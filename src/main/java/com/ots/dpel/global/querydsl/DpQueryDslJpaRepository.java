package com.ots.dpel.global.querydsl;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.sql.JPASQLQuery;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.FactoryExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface στο οποίο περιλαμβάνονται οι επιπλέον μέθοδοι παραγωγής ερωτημάτων
 * προς τη βάση χρησιμοποιώντας QueryDsl
 * @param <T>
 * @param <ID>
 * @author lzagkaretos
 */
@NoRepositoryBean
public interface DpQueryDslJpaRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {
    
    /**
     * Ανάκτηση σελίδας (page) με αποτελέσματα από τη βάση για δοθέν ερώτημα QueryDsl
     * @param query             Το ερώτημα QueryDsl προς τη βάση
     * @param factoryExpression Περιγραφή των στηλών τις οποίες θέλουμε να επιστρέψουμε
     * @param pageable          Η ζητούμενη σελίδα
     * @return Σελίδα με τα ανακτημένα αποτελέσματα
     */
    <T1> Page<T1> findAll(JPQLQuery query, FactoryExpression<T1> factoryExpression, Pageable pageable);
    
    /**
     * Ανάκτηση σελίδας (page) με αποτελέσματα από τη βάση για δοθέν ερώτημα QueryDsl
     * @param query             Το ερώτημα QueryDsl προς τη βάση
     * @param countQuery        Το ερώτημα QueryDsl για Count προς τη βάση
     * @param factoryExpression Περιγραφή των στηλών τις οποίες θέλουμε να επιστρέψουμε
     * @param pageable          Η ζητούμενη σελίδα
     * @return Σελίδα με τα ανακτημένα αποτελέσματα
     */
    <T1> Page<T1> findAll(JPQLQuery query, JPQLQuery countQuery, FactoryExpression<T1> factoryExpression, Pageable pageable);
    
    /**
     * Ανάκτηση λίστας με αποτελέσματα από τη βάση για δοθένα ερώτημα QueryDsl
     * @param query             Το ερώτημα QueryDsl προς τη βάση
     * @param factoryExpression Περιγραφή των στηλών τις οποίες θέλουμε να επιστρέψουμε
     * @return Λίστα με τα ανακτημένα αποτελέσματα
     */
    <T1> List<T1> findAll(JPQLQuery query, FactoryExpression<T1> factoryExpression);
    
    /**
     * Ανάκτηση λίστας με αποτελέσματα από τη βάση για δοθένα ερώτημα QueryDsl.
     * Αν δεν βρεθούν αποτελέσματα, επιστρέφει κενή λίστα.
     * @param query             Το ερώτημα QueryDsl προς τη βάση
     * @param factoryExpression Περιγραφή των στηλών τις οποίες θέλουμε να επιστρέψουμε
     * @return Λίστα με τα ανακτημένα αποτελέσματα ή κενή λίστα
     */
    <T1> List<T1> findList(JPQLQuery query, FactoryExpression<T1> factoryExpression);
    
    /**
     * Ανάκτηση ενός αποτελέσματος από τη βάση για το δοθέν ερώτημα QueryDsl
     * @param <T1>
     * @param query             Το ερώτημα QueryDsl προς τη βάση
     * @param factoryExpression Περιγραφή των στηλών τις οποίες θέλουμε να επιστρέψουμε
     * @return Το ανακτημένο αποτέλεσμα
     */
    <T1> T1 findOne(JPQLQuery query, FactoryExpression<T1> factoryExpression);
    
    /**
     * Ανάκτηση σελίδας (page) με αποτελέσματα από τη βάση για δοθέν ερώτημα QueryDsl Native SQL
     * @param <T1>
     * @param query              Το ερώτημα QueryDsl προς τη βάση Native SQL
     * @param factoryExpression  Περιγραφή των στηλών τις οποίες θέλουμε να επιστρέψουμε
     * @param pageable           Η ζητούμενη σελίδα
     * @param relationalPathBase Το auto generated object από το QueryDsl
     * @return Σελίδα με τα ανακτημένα αποτελέσματα
     */
    <T1> Page<T1> findAll(JPASQLQuery query, FactoryExpression<T1> factoryExpression, Pageable pageable, RelationalPathBase relationalPathBase);
    
    /**
     * Ανάκτηση σελίδας (page) με αποτελέσματα από τη βάση για δοθέν ερώτημα Native SQL
     * @param <T1>
     * @param sql         Το ερώτημα προς τη βάση Native SQL
     * @param parameters  Παράμετροι εκτέλεσης ερωτήματος
     * @param resultClass Κλάση αντικειμένων αποτελεσμάτων DTO
     * @param pageable    Η ζητούμενη σελίδα
     * @return Σελίδα με τα ανακτημένα αποτελέσματα
     */
    <T1> Page<T1> findAllNativeSql(String sql, Map<String, Object> parameters, Class resultClass, Pageable pageable);
}
