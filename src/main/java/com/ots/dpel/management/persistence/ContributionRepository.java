package com.ots.dpel.management.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ots.dpel.global.querydsl.DpQueryDslJpaRepository;
import com.ots.dpel.management.core.domain.Contribution;

@Repository
public interface ContributionRepository extends DpQueryDslJpaRepository<Contribution, Long> {
    
    List<Contribution> findByIdIn(List<Long> ids);
    
    Contribution findByVolunteerId(Long volunteerId);
    
    @Modifying
    @Query("DELETE FROM Contribution c WHERE c.volunteerId = ?1")
    void deleteByVolunteerId(Long volunteerId);
    
}
