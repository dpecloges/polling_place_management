package com.ots.dpel.system.core.enums;

/**
 * Κατάσταση Χρονοπρογραμματισμένης Εργασίας
 * <p/>Διαθέσιμες επιλογές
 * <ul>
 *     <li>ADDED: Δημιουργήθηκε</li>
 *     <li>SCHEDULED: Προγραμματισμένη</li>
 *     <li>RUNNING: Εκτελείται</li>
 *     <li>COMPLETED: Ολοκληρώθηκε</li>
 *     <li>FAILED: Απέτυχε</li>
 * </ul>
 */
public enum ScheduledJobStatus {
    
    /**
     * Δημιουργήθηκε
     */
    ADDED {
        @Override
        public String toString() {
            return "Δημιουργήθηκε";
        }
    },
    
    /**
     * Προγραμματισμένη
     */
    SCHEDULED {
        @Override
        public String toString() {
            return "Προγραμματισμένη";
        }
    },
    
    /**
     * Εκτελείται
     */
    RUNNING {
        @Override
        public String toString() {
            return "Εκτελείται";
        }
    },
    
    /**
     * Ολοκληρώθηκε
     */
    COMPLETED {
        @Override
        public String toString() {
            return "Ολοκληρώθηκε";
        }
    },
    
    /**
     * Απέτυχε
     */
    FAILED {
        @Override
        public String toString() {
            return "Απέτυχε";
        }
    }
}
