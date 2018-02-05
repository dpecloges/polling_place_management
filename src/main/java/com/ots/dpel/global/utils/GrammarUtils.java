package com.ots.dpel.global.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Βοηθητικές λειτουργίες που αφορούν τους Όρους Γραμματικής
 * Περιλαμβάνονται στατικές μέθοδοι
 */
public class GrammarUtils {
    
    /**
     * Πρόταση τιμής για τη γενική πτώση ενός όρου γραμματικής
     * Η μέθοδος εκτελεί κάποιους πολύ βασικούς ελέγχους με βάση την κατάληξη του όρου και δεν είναι πλήρης
     * @param nominative τιμή στην ονομαστική πτώση
     * @return
     */
    public static String getGenitiveSuggestion(String nominative) {
        
        if (nominative == null || nominative.isEmpty()) return null;
        
        String suffix1 = StringUtils.right(nominative, 1);
        String suffix2 = StringUtils.right(nominative, 2);
        String genitive = null;
        
        if (suffix2.equals("ος")) {
            genitive = StringUtils.left(nominative, nominative.length() - 2) + "ου";
        } else if (suffix2.equals("ός")) {
            genitive = StringUtils.left(nominative, nominative.length() - 2) + "ού";
        } else if (suffix2.equals("ης") || suffix2.equals("ας") || suffix2.equals("ες") || suffix2.equals("ής") || suffix2.equals("άς") ||
            suffix2.equals("ές") || suffix2.equals("ις")) {
            genitive = StringUtils.left(nominative, nominative.length() - 1);
        } else if (suffix1.equals("α") || suffix1.equals("ά")) {
            genitive = nominative + "ς";
        } else {
            genitive = nominative;
        }
        
        return genitive;
    }
    
    /**
     * Πρόταση τιμής για την αιτιατική πτώση ενός όρου γραμματικής
     * Η μέθοδος εκτελεί κάποιους πολύ βασικούς ελέγχους με βάση την κατάληξη του όρου και δεν είναι πλήρης
     * @param nominative τιμή στην ονομαστική πτώση
     * @return
     */
    public static String getAccusativeSuggestion(String nominative) {
        
        if (nominative == null || nominative.isEmpty()) return null;
        
        String suffix1 = StringUtils.right(nominative, 1);
        String suffix2 = StringUtils.right(nominative, 2);
        String accusative = null;
        
        if (suffix2.equals("ος") || suffix2.equals("ός") || suffix2.equals("ης") || suffix2.equals("ας") || suffix2.equals("ες") || suffix2.equals
            ("ής") || suffix2.equals("άς") || suffix2.equals("ές") || suffix2.equals("ις")) {
            accusative = StringUtils.left(nominative, nominative.length() - 1);
        } else {
            accusative = nominative;
        }
        
        return accusative;
    }
    
}
