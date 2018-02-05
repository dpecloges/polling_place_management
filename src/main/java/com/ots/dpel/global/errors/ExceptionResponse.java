package com.ots.dpel.global.errors;

import com.ots.dpel.global.handlers.GlobalExceptionHandler;

/**
 * <p>Κλάση περιγραφής του Response για exceptions που διαχειρίζονται από την κλάση
 * {@link GlobalExceptionHandler}</p>
 */
public abstract class ExceptionResponse {
    
    /**
     * Αναγνωριστικό Σφάλματος
     */
    private String errorId;
    
    /**
     * Βασικό Μήνυμα Σφάλματος
     */
    private String errorMessage;
    
    /**
     * Url Κλήσης στο API
     */
    private String url;
    
    /**
     * Το αντικείμενο του Exception
     */
    private Exception exception;
    
    /**
     * Username του Συνδεδεμένου Χρήστη
     */
    private String username;
    
    /**
     * Μήνυμα SQL Σφάλματος σε περίπτωση που το σφάλμα σχετίζεται με τη Βάση Δεδομένων
     */
    private String sqlMessage;
    
    /**
     * Μήνυμα Σφάλματος που περιλαμβάνει επιπλέον λεπτομέρειες
     */
    private String causeMessage;
    
    /**
     * Περαιτέρω Στοιχεία Σφάλματος
     * Όνομα κλάσης, μεθόδου κτλ.
     */
    private String causeDetails;
    
    public ExceptionResponse() {
    }
    
    public ExceptionResponse(String errorId, String errorMessage, String url, String username, Exception exception) {
        this.errorId = errorId;
        this.errorMessage = errorMessage;
        this.url = url;
        this.username = username;
        this.exception = exception;
    }
    
    public String getErrorId() {
        return errorId;
    }
    
    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Exception getException() {
        return exception;
    }
    
    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    public String getSqlMessage() {
        return sqlMessage;
    }
    
    public void setSqlMessage(String sqlMessage) {
        this.sqlMessage = sqlMessage;
    }
    
    public String getCauseMessage() {
        return causeMessage;
    }
    
    public void setCauseMessage(String causeMessage) {
        this.causeMessage = causeMessage;
    }
    
    public String getCauseDetails() {
        return causeDetails;
    }
    
    public void setCauseDetails(String causeDetails) {
        this.causeDetails = causeDetails;
    }
}
