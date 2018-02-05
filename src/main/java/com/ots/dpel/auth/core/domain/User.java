package com.ots.dpel.auth.core.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ots.dpel.management.core.domain.ElectionDepartment;

/**
 * Χρήστες Εφαρμογής
 */
@Entity
@Table(name = "user", schema = "dp")
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "n_electionprocedure_id")
    private Long electionProcedureId;
    
    /**
     * Username
     */
    @Column(name = "v_username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "v_password")
    @JsonIgnore
    private String password;
    
    /**
     * Email
     */
    @Size(max = 50)
    @Column(name = "v_email")
    private String email;
    
    @Column(name = "v_type")
    private String type;
    
    /**
     * Επώνυμο
     */
    @Size(max = 50)
    @Column(name = "v_lastname")
    private String lastName;
    
    /**
     * Όνομα
     */
    @Size(max = 50)
    @Column(name = "v_firstname")
    private String firstName;
    
    @Column(name = "n_electiondepartment_id")
    private Long electionDepartmentId;
    
    @ManyToOne
    @JoinColumn(name = "n_electiondepartment_id", insertable = false, updatable = false)
    private ElectionDepartment electionDepartment;
    
    /**
     * Ρόλοι συνδεδεμένου χρήστη
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "dp", name = "userrole",
            joinColumns = @JoinColumn(name = "n_user_id", referencedColumnName = "n_id"),
            inverseJoinColumns = @JoinColumn(name = "n_role_id", referencedColumnName = "n_id"))
    @Fetch(FetchMode.JOIN)
    private List<Role> roleList = new LinkedList<Role>();
    
    public User() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public List<Role> getRoleList() {
        return roleList;
    }
    
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public ElectionDepartment getElectionDepartment() {
        return electionDepartment;
    }

    public void setElectionDepartment(ElectionDepartment electionDepartment) {
        this.electionDepartment = electionDepartment;
    }
}
