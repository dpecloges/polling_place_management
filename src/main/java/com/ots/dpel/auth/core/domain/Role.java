package com.ots.dpel.auth.core.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Ρόλοι Χρήστών Εφαρμογής
 */
@Entity
@Table(name = "role", schema = "dp")
public class Role implements Serializable {
    
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
    @Size(max = 30)
    @Column(name = "v_code", unique = true)
    private String code;
    
    /**
     * Όνομα
     */
    @Size(max = 100)
    @Column(name = "v_name")
    private String name;
    
    /**
     * Δικαιώματα Ρόλου
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "dp", name = "rolepermission",
            joinColumns = @JoinColumn(name = "n_role_id", referencedColumnName = "n_id"),
            inverseJoinColumns = @JoinColumn(name = "n_permission_id", referencedColumnName = "n_id"))
    @Fetch(FetchMode.JOIN)
    private List<Permission> permissionList = new LinkedList<Permission>();
    
    public Role() {
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
    
    public List<Permission> getPermissionList() {
        return permissionList;
    }
    
    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
    
}
