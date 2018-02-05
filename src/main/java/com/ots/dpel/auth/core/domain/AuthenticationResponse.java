package com.ots.dpel.auth.core.domain;

import java.util.Set;

/**
 * Στοιχεία Συνδεδεμένου Χρήστη - Αντικείμενο Response DTO
 */
public class AuthenticationResponse {
    /**
     * Id του χρήστη
     */
    private Long id;
    
    /**
     * Username του χρήστη
     */
    private String username;
    
    /**
     * Id του ληξιαρχείου
     */
    private Long registerOfficeId;
    
    /**
     * Ονομασία του ληξιαρχείου
     */
    private String registerOfficeName;
    
    /**
     * Id του δημοτολογίου
     */
    private Long municipalRegistryId;
    
    /**
     * Ονομασία του δημοτολογίου
     */
    private String municipalRegistryName;
    
    /**
     * Ένδειξη εάν ο χρήστης έχει δικαιώματα χρήστη υπουργείου
     */
    private Boolean isMinisterial;
    
    /**
     * Σύνολο δικαιωμάτων του χρήστη
     */
    private Set<String> permissions;
    
    /**
     * Η πλήρης επωνυμία του χρήστη (Όνομα Επώνυμο)
     */
    private String fullName;
    
    /**
     * Ένδειξη εάν ο χρήστης είναι χρήστης του ειδικού ληξιαρχείου
     */
    private Boolean isSpecialRegisterOffice;
    
    /**
     * Ένδειξη ενεργοποιημένης διασύνδεσης με Access στα εκλογικά
     */
    private Boolean eprAccessEnabled;
    
    public AuthenticationResponse() {
    
    }
    
    public AuthenticationResponse(String username) {
        this.username = username;
    }
    
    public AuthenticationResponse(Long id, String username, Long registerOfficeId, String registerOfficeName, Long municipalRegistryId, String
        municipalRegistryName, Boolean isMinisterial, Set<String> permissions, String fullName, Boolean isSpecialRegisterOffice, Boolean
        eprAccessEnabled) {
        this.id = id;
        this.username = username;
        this.registerOfficeId = registerOfficeId;
        this.registerOfficeName = registerOfficeName;
        this.municipalRegistryId = municipalRegistryId;
        this.municipalRegistryName = municipalRegistryName;
        this.isMinisterial = isMinisterial;
        this.permissions = permissions;
        this.fullName = fullName;
        this.isSpecialRegisterOffice = isSpecialRegisterOffice;
        this.eprAccessEnabled = eprAccessEnabled;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Long getRegisterOfficeId() {
        return registerOfficeId;
    }
    
    public void setRegisterOfficeId(Long registerOfficeId) {
        this.registerOfficeId = registerOfficeId;
    }
    
    public String getRegisterOfficeName() {
        return registerOfficeName;
    }
    
    public void setRegisterOfficeName(String registerOfficeName) {
        this.registerOfficeName = registerOfficeName;
    }
    
    public Long getMunicipalRegistryId() {
        return municipalRegistryId;
    }
    
    public void setMunicipalRegistryId(Long municipalRegistryId) {
        this.municipalRegistryId = municipalRegistryId;
    }
    
    public String getMunicipalRegistryName() {
        return municipalRegistryName;
    }
    
    public void setMunicipalRegistryName(String municipalRegistryName) {
        this.municipalRegistryName = municipalRegistryName;
    }
    
    public Boolean getIsMinisterial() {
        return isMinisterial;
    }
    
    public void setIsMinisterial(Boolean isMinisterial) {
        this.isMinisterial = isMinisterial;
    }
    
    public Set<String> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Boolean getMinisterial() {
        return isMinisterial;
    }
    
    public void setMinisterial(Boolean ministerial) {
        isMinisterial = ministerial;
    }
    
    public Boolean getSpecialRegisterOffice() {
        return isSpecialRegisterOffice;
    }
    
    public void setSpecialRegisterOffice(Boolean specialRegisterOffice) {
        isSpecialRegisterOffice = specialRegisterOffice;
    }
    
    public Boolean getEprAccessEnabled() {
        return eprAccessEnabled;
    }
    
    public void setEprAccessEnabled(Boolean eprAccessEnabled) {
        this.eprAccessEnabled = eprAccessEnabled;
    }
}