package com.ots.dpel.global.utils;

import org.springframework.util.AlternativeJdkIdGenerator;

/**
 * Κλάση με μεθόδους που σχετίζονται με την παραγωγή
 * και τη διαχείριση μοναδικών Id UUID
 */
public class UuidUtils {
    
    /**
     * Ανάκτηση μοναδικού κωδικού UUID χρησιμοποιώντας
     * την κλάση {@link AlternativeJdkIdGenerator}
     * @return UUID
     */
    public static String generateId() {
        return new AlternativeJdkIdGenerator().generateId().toString();
    }
}
