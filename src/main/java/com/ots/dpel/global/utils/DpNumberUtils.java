package com.ots.dpel.global.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sdimitriadis
 */
public class DpNumberUtils {
    
    /**
     * Μετατροπή λίστας με τιμές BigDecimal σε λίστα με τιμές Long
     */
    public static List<Long> bigDecimalListToLongList(List<BigDecimal> bigDecimalList) {
        if (bigDecimalList == null) {
            return null;
        }
        
        List<Long> longList = new ArrayList<Long>();
        for (BigDecimal bigDecimal : bigDecimalList) {
            longList.add(bigDecimal.longValue());
        }
        
        return longList;
    }
    
    /**
     * Έλεγχος αν δύο τιμές Long είναι όμοιες.
     * Λαμβάνονται υπόψη και οι null τιμές. Αν είναι και οι δύο null, τότε θεωρούνται όμοιες.
     */
    public static boolean longsAreEqual(Long n1, Long n2) {
        return n1 == null ? n2 == null : n1.equals(n2);
    }
    
    /**
     * Έλεγχος αν δύο τιμές Integer είναι όμοιες.
     * Λαμβάνονται υπόψη και οι null τιμές. Αν είναι και οι δύο null, τότε θεωρούνται όμοιες.
     */
    public static boolean integersAreEqual(Integer n1, Integer n2) {
        return n1 == null ? n2 == null : n1.equals(n2);
    }
    
    /**
     * Null-safe Long.toString
     * Εάν η τιμή Long είναι null επιστρέφεται null, σε αντίθετη περίπτωση επιστρέφεται toString
     * @param n1
     * @return
     */
    public static String longToString(Long n1) {
        return n1 == null ? null : n1.toString();
    }
    
    /**
     * Null-safe Integer.toString
     * Εάν η τιμή Integer είναι null επιστρέφεται null, σε αντίθετη περίπτωση επιστρέφεται toString
     * @param n1
     * @return
     */
    public static String integerToString(Integer n1) {
        return n1 == null ? null : n1.toString();
    }
    
    /**
     * Μετατροπή του δεδομένου string σε integer εφόσον είναι εφικτό.
     */
    public static Integer stringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
