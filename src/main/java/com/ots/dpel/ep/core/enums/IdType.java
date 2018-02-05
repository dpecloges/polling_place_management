package com.ots.dpel.ep.core.enums;

public enum IdType {
    POLICE_ID {
        @Override
        public String toString() {
            return "Αστ. Ταυτότητα";
        }
    },
    PASSPORT {
        @Override
        public String toString() {
            return "Διαβατήριο";
        }
    },
    OTHER {
        @Override
        public String toString() {
            return "Άλλο";
        }
    }
}
