package com.ots.dpel.global.utils;

import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;


/**
 * @author sdimitriadis
 */
public class DpIndexUtils {
    
    /**
     * Έλεγχος για την ύπαρξη κριτηρίων αναζήτησης.
     */
    public static boolean checkSearchCriteriaExists(Object obj, Logger logger) {
        return checkSearchCriteriaExists(obj, logger, new String[0]);
    }
    
    /**
     * Έλεγχος για την ύπαρξη κριτηρίων αναζήτησης. Εξαιρούνται τα πεδία με τα δεδομένα ονόματα.
     */
    public static boolean checkSearchCriteriaExists(Object obj, Logger logger, String[] excludedNames) {
        
        for (Field f : obj.getClass().getDeclaredFields()) {
            
            if (Arrays.asList(excludedNames).contains(f.getName())) {
                continue;
            }
            
            f.setAccessible(true);
            try {
                //Αν το πεδίο έχει τιμή
                if (f.get(obj) != null) {
                    //και η τιμή αυτή δεν είναι το κενό string
                    if (((f.get(obj) instanceof String) && f.get(obj).toString().isEmpty())) {
                        continue;
                    }
                    //υπάρχουν κριτήρια αναζήτησης
                    return true;
                }
            } catch (IllegalArgumentException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
        }
        //δεν υπάρχουν κριτήρια αναζήτησης
        return false;
    }
}
