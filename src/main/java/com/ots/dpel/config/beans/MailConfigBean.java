package com.ots.dpel.config.beans;

import java.util.Properties;

/**
 * Ρυθμίσεις αποστολής e-mail.
 * 
 * @author ktzonas
 */
public class MailConfigBean {
    
    /**
     * Διεύθυνση mail server
     */
    private String host;
    
    /**
     * Πόρτα σύνδεσης mail server
     */
    private int port;
    
    /**
     * Αναγνωριστικό χρήστη του mail server
     */
    private String username;
    
    /**
     * Κωδικός χρήστη του mail server
     */
    private String password;
    
    /**
     * Διεύθυνση αποστολέα
     */
    private String senderMail;
    
    /**
     * Κωδικοποίηση μηνυμάτων
     */
    private String defaultEncoding;
    
    /**
     * Ενεργοποίηση batch αποστολής μηνυμάτων.
     */
    private boolean batchEnabled;
    
    /**
     * Μέγιστο πλήθος μηνυμάτων που αποστέλλονται μαζικά.
     */
    private int batchSize;
    
    /**
     * Διάφορες ρυθμίσεις του Java Mail
     */
    private Properties javaMailProperties;
    
    /**
     * Ένδειξη δοκιμαστικής χρήσης. Αν είναι {@code true}, 
     * όλα τα email θα αποστέλλονται στη διεύθυνση που υποδεικνύεται
     * στο {@code testModeRecipient}.
     */
    private boolean testMode = true;
    
    /**
     * Διεύθυνση στην οποία θα γίνεται αποστολή email
     * όταν είναι ενεργοποιημένο το {@code testMode}.
     */
    private String testModeRecipient = "ktzonas@gmail.com";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public boolean isBatchEnabled() {
        return batchEnabled;
    }

    public void setBatchEnabled(boolean batchEnabled) {
        this.batchEnabled = batchEnabled;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public String getTestModeRecipient() {
        return testModeRecipient;
    }

    public void setTestModeRecipient(String testModeRecipient) {
        this.testModeRecipient = testModeRecipient;
    }
}
