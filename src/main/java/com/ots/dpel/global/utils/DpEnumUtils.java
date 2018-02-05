package com.ots.dpel.global.utils;

import com.ots.dpel.common.core.enums.YesNoEnum;


/**
 * @author sdimitriadis
 */
public class DpEnumUtils {
    
    /**
     * Έλεγχος αν η δεδομένη τιμή YesNoEnum είναι YES
     */
    public static Boolean ynTrue(YesNoEnum value) {
        return value != null && value.equals(YesNoEnum.YES);
    }
    
    /**
     * Έλεγχος αν η δεδομένη τιμή YesNoEnum είναι NO ή null
     */
    public static Boolean ynFalse(YesNoEnum value) {
        return value == null || value.equals(YesNoEnum.NO);
    }
    
    /**
     * Ανάκτηση του name από τη δεδομένη τιμή enum
     */
    public static String getName(Enum myEnum) {
        return myEnum == null ? null : myEnum.name();
    }
    
    /**
     * Ανάκτηση του λεκτικού "true" ή "false" σύμφωνα με τη δεδομένη τιμή του YesNoEnum.
     * Αν είναι null, επιστρέφεται null.
     */
    public static String getYnBooleanString(YesNoEnum yesNo) {
        if (yesNo == null) {
            return null;
        } else if (yesNo.equals(YesNoEnum.YES)) {
            return "true";
        } else if (yesNo.equals(YesNoEnum.NO)) {
            return "false";
        } else {
            return null;
        }
        
    }
}
