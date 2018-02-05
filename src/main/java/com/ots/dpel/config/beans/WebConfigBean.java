package com.ots.dpel.config.beans;

/**
 * Ρυθμίσεις επικοινωνίας σε Web περιβάλλον.
 * 
 * <p>Οι ρυθμίσεις προέρχονται από το αρχείο {@code web.properties} του ενεργού Spring profile.</p>
 * 
 * @author ktzonas
 */
public class WebConfigBean {

    /**
     * Base URL της web εφαρμογής (dp-election-view)
     */
    private String dpElectionViewBaseUrl;

    public String getDpElectionViewBaseUrl() {
        return dpElectionViewBaseUrl;
    }

    public void setDpElectionViewBaseUrl(String dpElectionViewBaseUrl) {
        this.dpElectionViewBaseUrl = dpElectionViewBaseUrl;
    }
}
