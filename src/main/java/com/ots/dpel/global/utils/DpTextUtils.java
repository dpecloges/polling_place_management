package com.ots.dpel.global.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sdimitriadis
 */
public class DpTextUtils {
    
    private static final Logger logger = LogManager.getLogger(DpTextUtils.class);
    
    /**
     * Μετατροπή του δοθέντος αλφαριθμητικού σε κεφαλαία.
     * Η μέθοδος φροντίζει για την αφαίρεση των τόνων από τα ελληνικά φωνήεντα.
     * @param text - Το αλφαριθμητικό προς μετατροπή
     * @return Το αλφαριθμητικό με κεφαλαία γράμματα και χωρίς τόνους
     */
    public static String toUpperCaseGreekSupport(String text) {
        if (text == null) {
            return null;
        } else {
            text = text.toUpperCase();
            
            text = text.replace("Ά", "Α");
            text = text.replace("Έ", "Ε");
            text = text.replace("Ή", "Η");
            text = text.replace("Ί", "Ι");
            text = text.replace("Ό", "Ο");
            text = text.replace("Ύ", "Υ");
            text = text.replace("Ώ", "Ω");
            
            return text;
        }
    }
    
    /**
     * Μετατροπή του δοθέντος αλφαριθμητικού σε κεφαλαία αφαιρώντας σημεία στίξης.
     * Η μέθοδος φροντίζει για την αφαίρεση των τόνων και των διαλυτικών από τα ελληνικά φωνήεντα.
     * @param text - Το αλφαριθμητικό προς μετατροπή
     * @return Το αλφαριθμητικό με κεφαλαία γράμματα και χωρίς τόνους και διαλυτικά
     */
    public static String toUpperCaseGreekSupportExtended(String text) {
        if (text == null) {
            return null;
        } else {
            
            text = text.replace("ΐ", "ϊ");
            text = text.replace("ΰ", "ϋ");
            
            text = text.toUpperCase();
            
            text = text.replace("Ά", "Α");
            text = text.replace("Έ", "Ε");
            text = text.replace("Ή", "Η");
            text = text.replace("Ί", "Ι");
            text = text.replace("Ϊ", "Ι");
            text = text.replace("Ό", "Ο");
            text = text.replace("Ύ", "Υ");
            text = text.replace("Ϋ", "Υ");
            text = text.replace("Ώ", "Ω");
            
            return text;
        }
    }
    
    /**
     * Concatenation των στοιχείων μιας λίστας από strings σε ένα string.
     * Τα στοιχεία διαχωρίζονται με το δοθέν διαχωριστικό.
     */
    public static String concatListToString(List<String> list, String separator) {
        
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        String output = "";
        
        for (String element : list) {
            output += element + separator;
        }
        
        output = output.substring(0, output.length() - separator.length());
        
        return output;
    }
    
    /**
     * Παραγωγή λίστας με τιμές Long από ένα string αριθμών διαχωρισμένων με κόμμα.
     */
    public static List<Long> createListOfLongFromCommaSeparatedString(String input) {
        
        //Η λίστα που θα επιστραφεί
        List<Long> list = new ArrayList<Long>();
        
        if (input == null || input.isEmpty()) {
            return list;
        }
        
        input = input.replaceAll(" ", "");
        
        //Διαχωρισμός του string
        String[] splitResults = input.split(",");
        
        try {
            for (String splitResult : splitResults) {
                //Μετατροπή του υποstring σε long και προσθήκη στη λίστα
                Long value = Long.valueOf(splitResult);
                list.add(value);
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            logger.error(e.getMessage());
        }
        
        return list;
    }
    
    /**
     * Έλεγχος αν δύο strings είναι όμοια.
     * Λαμβάνονται υπόψη και οι null τιμές. Αν είναι και τα δύο null, τότε θεωρούνται όμοια.
     * Επίσης λαμβάνονται υπόψη τα κενά strings και οι boolean τιμές false.
     */
    public static Boolean stringsAreEqual(String s1, String s2) {
        
        if (s1 != null && (s1.isEmpty() || s1.equals("false"))) {
            s1 = null;
        }
        if (s2 != null && (s2.isEmpty() || s2.equals("false"))) {
            s2 = null;
        }
        
        if (s1 == null && s2 == null) {
            return true;
        } else if (s1 == null && s2 != null) {
            return false;
        } else if (s1 != null && s2 == null) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }
    
    /**
     * Έλεγχος αν ένα string είναι null ή κενό
     */
    public static Boolean stringIsBlank(String s) {
        return s == null || s.isEmpty();
    }
    
    /**
     * Έλεγχος εάν ένα string είναι null ή κενό (trimmed)
     * Πρόκειται για τη StringUtils.isEmpty() συν τον έλεγχο του trim()
     * @param s
     * @return
     */
    public static Boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }
    
    /**
     *
     */
    public static String nl2space(String text) {
        return text != null ? text.replace("\r\n", " ").replace("\n", " ").replace("\r", " ") : null;
    }
    
    public static String getPersonFullName(String firstName, String lastName, boolean startWithLastName) {
        
        String fullName = "";
        
        if (startWithLastName) {
            fullName = (StringUtils.isEmpty(lastName) ? "" : lastName) +
                (StringUtils.isEmpty(firstName) ? "" : " " + firstName);
        } else {
            fullName = (StringUtils.isEmpty(firstName) ? "" : firstName) +
                (StringUtils.isEmpty(lastName) ? "" : " " + lastName);
        }
        
        fullName = StringUtils.trim(fullName);
        
        return fullName;
    }
    
    public static String truncate(String value, int length) {
        if (value != null && value.length() > length) {
            return value.substring(0, length - 5) + "...";
        } else {
            return value;
        }
    }
    
    /**
     * Κανονικοποίηση (μετατροπή) αλφαριθμητικού πεδίου τύπου ΤΚ
     * Αφαίρεση κάποιων χαρακτήρων [" ", "-", "/"]
     * @param text
     * @return
     */
    public static String normalizeTk(String text) {
        if (text == null) {
            return null;
        } else {
            text = text.replace(" ", "");
            text = text.replace("-", "");
            text = text.replace("/", "");
            
            return text;
        }
    }
    
    public static String normalizeForeignCity(String text) {
        if (text == null) {
            return null;
        } else {
            text = text.replace(" ", "_");
            
            return text;
        }
    }
}
