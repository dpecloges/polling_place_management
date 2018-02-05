package com.ots.dpel.system.services;

/**
 * Interface λειτουργιών που αφορούν το Indexing της εφαρμογής
 */
public interface IndexingService {
    
    /**
     * Αναγνωριστικό Index για τους Εκλογείς
     */
    public static final String ELECTORS_INDEX = "electors";
    
    /**
     * Ανάκτηση της ένδειξης ενεργοποίησης της διασύνδεσης με το Solr
     * @return
     */
    public Boolean solrEnabled();
    
    /**
     * Ανάκτηση της ένδειξης ενεργοποιημένου Solr Core για τη ζητούμενη ενότητα εγγραφών
     * @param core Το αναγνωριστικό του core
     * @return
     */
    public Boolean isCoreOnline(String core);
    
    /**
     * Ορισμός της ένδειξης ενεργοποιημένου Solr Core για τη ζητούμενη ενότητα εγγραφών
     * @param core   Το αναγνωριστικό του core
     * @param status Το online status που ορίζεται
     */
    public void setCoreOnlineStatus(String core, Boolean status);
}
