package com.ots.dpel.system.services;

/**
 * Interface λειτουργιών που αφορούν το Caching της εφαρμογής
 */
public interface CacheService {
    
    /**
     * Cache για τα στοιχεία των Χρηστών Εφαρμογής
     */
    public static final String USERS_CACHE = "users";
    
    /**
     * Cache για τα στοιχεία των Γεωγραφικών Περιοχών
     */
    public static final String ADMIN_UNITS_CACHE = "adminUnits";
    
    /**
     * Cache για τα στοιχεία των Χωρών
     */
    public static final String COUNTRIES_CACHE = "countries";
    
    /**
     * Cache για τα στοιχεία των Αποτελεσμάτων
     */
    public static final String RESULTS_CACHE = "results";
    
    /**
     * Cache για τα στοιχεία των Στιγμιοτύπων
     */
    public static final String SNAPSHOTS_CACHE = "snapshots";
    
    /**
     * Μαζικό Eviction των στοιχείων από όλες τις Caches
     */
    public void removeAllCaches();
    
    /**
     * Μαζικό Eviction της Cache για τα στοιχεία των Χρηστών Εφαρμογής
     */
    public void removeAllUsersCache();
    
    /**
     * Eviction από την Cache για τα στοιχεία των Χρηστών Εφαρμογής για ένα συγκεκριμένο Χρήστη (Username)
     */
    public void removeUserFromCache(String username);
    
    /**
     * Μαζικό Eviction της Cache για τα στοιχεία των Γεωγραφικών Περιοχών
     */
    public void removeAllAdminUnitsCache();
    
    /**
     * Μαζικό Eviction της Cache για τα στοιχεία των Χωρών
     */
    public void removeAllCountriesCache();
    
    /**
     * Μαζικό Eviction της Cache για τα στοιχεία των Αποτελεσμάτων
     */
    public void removeAllResultsCache();
    
    /**
     * Μαζικό Eviction της Cache για τα στοιχεία των Στιγμιοτύπων
     */
    public void removeAllSnapshotsCache();
}
