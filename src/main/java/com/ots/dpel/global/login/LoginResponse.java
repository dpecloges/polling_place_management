package com.ots.dpel.global.login;

/**
 * <p>Κλάση περιγραφής του Response για το Login των χρηστών
 * βάση του ελέγχου που γίνεται σε συνδυασμό με τον OAM
 */
public class LoginResponse {
    
    /**
     * Ένδειξη εάν ο χρήστης είναι ήδη συνδεδεμένος
     */
    private boolean loggedIn;
    
    /**
     * Url location σε περίπτωση που ο χρήστης δεν είναι συνδεδεμένος
     * Πρόκειται για το ενδιάμεσο url του OAM που πρέπει να εκτελεστεί στον browser
     */
    private String location;
    
    public LoginResponse() {
    }
    
    public LoginResponse(boolean loggedIn, String location) {
        this.loggedIn = loggedIn;
        this.location = location;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}
