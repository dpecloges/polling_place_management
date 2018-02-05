package com.ots.dpel.management.core.enums;

public enum ElectionRound {
    
    FIRST {
        @Override
        public String toString() {
            return "Α' Γύρος";
        }
    },
    SECOND {
        @Override
        public String toString() {
            return "Β' Γύρος";
        }
    }
    
}
