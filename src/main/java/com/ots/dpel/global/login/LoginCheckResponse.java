package com.ots.dpel.global.login;

/**
 * <p>Κλάση περιγραφής του Response για το Check Login των χρηστών
 */
public class LoginCheckResponse {
    
    /**
     * Ένδειξη εάν ο χρήστης είναι ήδη συνδεδεμένος
     */
    private boolean loggedIn;
    
    /**
     * Username συνδεδεμένου χρήστη
     */
    private String username;
    
    public LoginCheckResponse() {
    }
    
    public LoginCheckResponse(boolean loggedIn, String username) {
        this.loggedIn = loggedIn;
        this.username = username;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
