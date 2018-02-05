package com.ots.dpel.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ots.dpel.auth.core.domain.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Στοιχεία συνδεδεμένου χρήστη - Αντικείμενο DTO
 */
public class DpUserDetailsDTO implements UserDetails {
    
    /**
     * Id
     */
    @JsonIgnore
    private Long id;
    
    /**
     * Username
     */
    private String username;
    
    /**
     * Password
     */
    @JsonIgnore
    private String password;
    
    /**
     * Επωνυμία (Όνομα Επώνυμο)
     */
    private String fullName;
    
    private Long electionCenterId;
    
    private String electionCenterName;
    
    private String electionCenterDisplayName;
    
    private Long electionDepartmentId;
    
    private String electionDepartmentName;
    
    private String electionDepartmentDisplayName;
    
    private Long electionProcedureId;
    
    private String electionProcedureRound;
    
    /**
     * Λίστα δικαιωμάτων (permissions - programs)
     */
    private List<Permission> permissionList;
    
    /**
     * Λίστα δικαιωμάτων ως Granted Authorities
     */
    private Collection<? extends GrantedAuthority> authorities;
    
    public DpUserDetailsDTO() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }
    
    public String getElectionCenterName() {
        return electionCenterName;
    }
    
    public void setElectionCenterName(String electionCenterName) {
        this.electionCenterName = electionCenterName;
    }
    
    public String getElectionCenterDisplayName() {
        return electionCenterDisplayName;
    }
    
    public void setElectionCenterDisplayName(String electionCenterDisplayName) {
        this.electionCenterDisplayName = electionCenterDisplayName;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public String getElectionDepartmentName() {
        return electionDepartmentName;
    }
    
    public void setElectionDepartmentName(String electionDepartmentName) {
        this.electionDepartmentName = electionDepartmentName;
    }
    
    public String getElectionDepartmentDisplayName() {
        return electionDepartmentDisplayName;
    }
    
    public void setElectionDepartmentDisplayName(String electionDepartmentDisplayName) {
        this.electionDepartmentDisplayName = electionDepartmentDisplayName;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public String getElectionProcedureRound() {
        return electionProcedureRound;
    }
    
    public void setElectionProcedureRound(String electionProcedureRound) {
        this.electionProcedureRound = electionProcedureRound;
    }
    
    public List<Permission> getPermissionList() {
        return permissionList;
    }
    
    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
