package com.ots.dpel.auth.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ots.dpel.auth.core.domain.User;
import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;

/**
 * Repository για την οντότητα των χρηστών
 */
@Repository
public interface DpUserRepository extends DpQueryDslJpaRepository<User, Long> {
    
    User findByUsername(String username);
    
    List<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.email = ?1")
    User findByUsernameAndEmail(String usernameAndEmail);
    
    User findByUsernameAndAndElectionProcedureId(String username, Long electionProcedureId);
    
}