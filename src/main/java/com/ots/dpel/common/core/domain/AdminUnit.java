package com.ots.dpel.common.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adminunit", schema = "dp")
public class AdminUnit {
    
    /**
     * Id εγγραφής
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    /**
     * Id πατρικής εγγραφής
     */
    @Column(name = "n_parentid")
    private Long parentId;
    
    /**
     * Επίπεδο ιεραρχίας
     */
    @Column(name = "n_level")
    private Short level;
    
    /**
     * Τύπος
     */
    @Column(name = "v_type")
    private String type;
    
    /**
     * Κωδικός
     */
    @Column(name = "v_code")
    private String code;
    
    /**
     * Ονομασία
     */
    @Column(name = "v_name")
    private String name;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public Short getLevel() {
        return level;
    }
    
    public void setLevel(Short level) {
        this.level = level;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        AdminUnit adminUnit = (AdminUnit) o;
    
        return id != null ? id.equals(adminUnit.id) : adminUnit.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
