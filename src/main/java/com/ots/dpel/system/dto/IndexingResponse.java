package com.ots.dpel.system.dto;

/**
 * Στοιχεία Ενεργοποιημένης Λειτουργίας Indexing - Αντικείμενο Response DTO
 */
public class IndexingResponse {
    
    /**
     * Ένδειξη response
     */
    private Boolean response;
    
    public IndexingResponse() {
    }
    
    public IndexingResponse(Boolean response) {
        this.response = response;
    }
    
    public Boolean getResponse() {
        return response;
    }
    
    public void setResponse(Boolean response) {
        this.response = response;
    }
}
