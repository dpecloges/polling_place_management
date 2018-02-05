package com.ots.dpel.global.dto;

/**
 * Γενικό DTO για δεδομένα προς εξαγωγή
 */
public class ExportDataDTO {
    
    /**
     * Τίτλος του αρχείου
     */
    private String title;
    
    /**
     * Περιγραφή στηλών
     */
    private String model;
    
    /**
     * Δεδομένα
     */
    private String data;
    
    /**
     * Στήλη ταξινόμησης
     */
    private String sortColumn;
    
    /**
     * Σειρά ταξινόμησης (asc, desc)
     */
    private String sortOrder;
    
    public ExportDataDTO() {
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public String getSortColumn() {
        return sortColumn;
    }
    
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
