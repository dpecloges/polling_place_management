package com.ots.dpel.ep.core.enums;

public enum SnapshotType {
    
    ALL {
        @Override
        public String toString() {
            return "Όλα";
        }
    },
    
    
    ABROAD {
        @Override
        public String toString() {
            return "Εξωτερικό";
        }
    },
    FOREIGN_COUNTRY {
        @Override
        public String toString() {
            return "Χώρα Εξωτερικού";
        }
    },
    FOREIGN_CITY {
        @Override
        public String toString() {
            return "Πόλη Εξωτερικού";
        }
    },
    
    
    GREECE {
        @Override
        public String toString() {
            return "Ελλάδα";
        }
    },
    GEOGRAPHICAL_UNIT {
        @Override
        public String toString() {
            return "Γεωγραφική Ενότητα";
        }
    },
    DECENTRAL_ADMIN {
        @Override
        public String toString() {
            return "Αποκεντρωμένη Διοίκηση";
        }
    },
    REGION {
        @Override
        public String toString() {
            return "Περιφέρεια";
        }
    },
    REGIONAL_UNIT {
        @Override
        public String toString() {
            return "Περιφερειακή Ενότητα";
        }
    },
    MUNICIPALITY {
        @Override
        public String toString() {
            return "Δήμος";
        }
    },
    MUNICIPAL_UNIT {
        @Override
        public String toString() {
            return "Δημοτική Ενότητα";
        }
    },
    
    
    ELECTION_CENTER {
        @Override
        public String toString() {
            return "Εκλογικό Κέντρο";
        }
    },
    ELECTION_DEPARTMENT {
        @Override
        public String toString() {
            return "Εκλογικό Τμήμα";
        }
    }
}
