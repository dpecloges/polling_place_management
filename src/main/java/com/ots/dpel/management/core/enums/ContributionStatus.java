package com.ots.dpel.management.core.enums;

public enum ContributionStatus {
    
    WITHOUT_ACCESS {
        @Override
        public String toString() {
            return "Χωρίς πρόσβαση";
        }
    },
    PENDING {
        @Override
        public String toString() {
            return "Σε εκκρεμότητα";
        }
    },
    EMAIL_SENT {
        @Override
        public String toString() {
            return "Αποστολή ειδοποίησης";
        }
    },
    REGISTRATION_COMPLETED {
        @Override
        public String toString() {
            return "Ολοκλήρωση εγγραφής";
        }
    }
}
