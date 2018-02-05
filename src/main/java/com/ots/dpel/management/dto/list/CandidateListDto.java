package com.ots.dpel.management.dto.list;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.management.core.enums.ElectionRound;

public class CandidateListDto {
    
    private Long id;
    
    private String lastName;
    
    private String firstName;
    
    private ElectionRound round;
    
    private Short order;
    
    public CandidateListDto() {
    }
    
    @QueryProjection
    public CandidateListDto(Long id, String lastName, String firstName, ElectionRound round, Short order) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.round = round;
        this.order = order;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public ElectionRound getRound() {
        return round;
    }
    
    public void setRound(ElectionRound round) {
        this.round = round;
    }
    
    public Short getOrder() {
        return order;
    }
    
    public void setOrder(Short order) {
        this.order = order;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        CandidateListDto that = (CandidateListDto) o;
    
        return id != null ? id.equals(that.id) : that.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
