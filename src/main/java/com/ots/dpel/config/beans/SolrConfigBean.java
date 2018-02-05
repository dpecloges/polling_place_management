package com.ots.dpel.config.beans;

import java.util.Map;

/**
 * Παράμετροι που σχετίζονται με το αρχείο solr.properties
 * Δημιουργήθηκε νέο αντικείμενο Bean για να είναι εφικτή η φόρτωση
 * των τιμών των ιδιοτήτων σε συνδυασμό ενεργού Profile
 */
public class SolrConfigBean {
    
    /**
     * Ένδειξη ενεργοποίησης κυκλώματος indexing στο Solr
     */
    private Boolean enabled;
    
    /**
     * Solr host name
     */
    private String host;
    
    /**
     * Ένδειξη ενεργοποιημένης ομάδας εγγραφών Solr Core
     */
    private Map<String, Boolean> onlineCores;
    
    public SolrConfigBean() {
    }
    
    public SolrConfigBean(Boolean enabled, String host) {
        this.enabled = enabled;
        this.host = host;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public Map<String, Boolean> getOnlineCores() {
        return onlineCores;
    }
    
    public void setOnlineCores(Map<String, Boolean> onlineCores) {
        this.onlineCores = onlineCores;
    }
    
    public Boolean isCoreOnline(String core) {
        return onlineCores.get(core);
    }
    
    public void setCoreOffline(String core) {
        onlineCores.put(core, false);
    }
    
    public void setCoreOnline(String core) {
        onlineCores.put(core, true);
    }
}
