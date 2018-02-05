package com.ots.dpel.auth.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Δικαιώματα Ρόλων Χρηστών Εφαρμογής
 */
@Entity
@Table(name = "permission", schema = "dp")
public class Permission implements Serializable {
    
    /**
     * Id
     */
    @Id
    @NotNull
    @Column(name = "n_id")
    private Long id;
    
    /**
     * Κωδικός
     */
    @Size(max = 50)
    @Column(name = "v_code", unique = true)
    private String code;
    
    /**
     * Όνομα
     */
    @Size(max = 100)
    @Column(name = "v_name")
    private String name;
    
    public Permission() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
