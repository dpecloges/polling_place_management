package com.ots.dpel.global.utils;

import com.ots.dpel.global.errors.ErrorMessage;
import com.ots.dpel.global.errors.ErrorReport;
import com.ots.dpel.global.exceptions.DpValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Βοηθητικές μέθοδοι σχετικές με το validation
 * @author sdimitriadis
 */
public class DpValidationUtils {
    
    /**
     * Μέθοδος που δέχεται μια λίστα με μηνύματα σφάλματος και, σε περίπτωση που η λίστα δεν είναι κενή,
     * πετάει DpValidationException με αυτή τη λίστα σφαλμάτων.
     * @param errorMessages
     */
    public static void throwIfExistsCrValidationException(List<String> errorMessages) {
        if (!errorMessages.isEmpty()) {
            ErrorReport errorReport = new ErrorReport();
            for (String errorMessage : errorMessages) {
                errorReport.addError(errorMessage);
            }
            throw new DpValidationException(errorReport);
        }
    }
    
    /**
     * Μέθοδος που δέχεται μια λίστα με μηνύματα σφάλματος και, σε περίπτωση που η λίστα δεν είναι κενή,
     * πετάει DpValidationException με αυτή τη λίστα σφαλμάτων.
     * @param errorMessages
     */
    public static void throwCrValidationException(List<ErrorMessage> errorMessages) {
        if (!errorMessages.isEmpty()) {
            ErrorReport errorReport = new ErrorReport();
            int maxErrorSize = 100;
            
            if (errorMessages != null && errorMessages.size() > maxErrorSize) {
                errorReport.addError(new ErrorMessage("0", "Βρέθηκαν " + String.valueOf(errorMessages.size()) + " μηνύματα σφάλματος. Για λόγους " +
                    "απόδοσης του συστήματος θα εμφανιστούν στο χρήστη τα πρώτα 100."));
            }
            
            int i = 0;
            for (ErrorMessage errorMessage : errorMessages) {
                if (i++ < maxErrorSize) {
                    errorReport.addError(errorMessage);
                } else {
                    break;
                }
            }
            
            throw new DpValidationException(errorReport);
        }
    }
    
    /**
     * Έλεγχος εγκυρότητας αριθμητικής τιμής
     * @param value
     * @param errorMessages
     * @param messageSourceProvider
     */
    public static void checkNumberValue(String value, List<String> errorMessages, MessageSourceProvider messageSourceProvider) {
        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            errorMessages.add(value + ": " + messageSourceProvider.getMessage("error.validation.number"));
        }
    }
    
    /**
     * Έλεγχος εγκυρότητας τιμής dropdown
     * @param value
     * @param messageSourceProvider
     * @param optionValue
     * @param errorMessages
     */
    public static void checkDropdownValue(String value, String optionValue, List<String> errorMessages, MessageSourceProvider messageSourceProvider) {
        if (optionValue != null && optionValue.equals("id")) {
            try {
                Long.parseLong(value);
            } catch (NumberFormatException e) {
                errorMessages.add(value + ": " + messageSourceProvider.getMessage("error.validation.dropdown"));
            }
        }
    }
    
    /**
     * Έλεγχος εγκυρότητας ημερομηνίας
     * @param value
     * @param errorMessages
     * @param messageSourceProvider
     */
    public static void checkDateValue(String value, List<String> errorMessages, MessageSourceProvider messageSourceProvider) {
        String ddMMyyyRegex = "^[0-3][0-9]/[0-1][0-9]/(?:[0-9][0-9])?[0-9][0-9]$";
        
        if (!value.matches(ddMMyyyRegex)) {
            errorMessages.add(value + ": " + messageSourceProvider.getMessage("error.validation.date"));
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                sdf.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
                errorMessages.add(value + ": " + messageSourceProvider.getMessage("error.validation.date"));
            }
        }
    }
    
    /**
     * Έλεγχος εγκυρότητας τιμής enum
     * @param value
     * @param enumClass
     * @param errorMessages
     * @param messageSourceProvider
     */
    public static void checkEnumValue(String value, String enumClass, List<String> errorMessages, MessageSourceProvider messageSourceProvider) {
        
        Boolean foundEnumMatch = false;
        
        try {
            Class<?> clazz = Class.forName("com.ots.dpel." + enumClass);
            
            for (Object obj : clazz.getEnumConstants()) {
                Enum<?> enumeration = (Enum<?>) obj;
                String enumValue = enumeration.name();
                Integer enumOrdinal = enumeration.ordinal();
                
                if (enumValue.equals(value)) {
                    foundEnumMatch = true;
                    break;
                } else if (enumOrdinal.toString().equals(value)) {
                    foundEnumMatch = true;
                    break;
                }
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if (!foundEnumMatch) {
            errorMessages.add(value + ": " + messageSourceProvider.getMessage("error.validation.enum"));
        }
    }
    
    /**
     * Έλεγχος εγκυρότητας τιμής true/false
     * @param value
     * @param errorMessages
     * @param messageSourceProvider
     */
    public static void checkYesNoValue(String value, List<String> errorMessages, MessageSourceProvider messageSourceProvider) {
        if (!value.equals("true") && !value.equals("false")) {
            errorMessages.add(value + ": " + messageSourceProvider.getMessage("error.validation.yesNo"));
        }
    }
}
