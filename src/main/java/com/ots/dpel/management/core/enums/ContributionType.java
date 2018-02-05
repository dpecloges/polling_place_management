package com.ots.dpel.management.core.enums;

public enum ContributionType {
    
    CANDIDATE_REPRESENTATIVE {
        @Override
        public String toString() {
            return "Εκπρόσωπος Υποψηφίου";
        }
    },
    COMMITTEE_LEADER {
        @Override
        public String toString() {
            return "Πρόεδρος ΕΕ";
        }
    },
    COMMITTEE_LEADER_VICE {
        @Override
        public String toString() {
            return "Αναπληρωτής Πρόεδρος ΕΕ";
        }
    },
    COMMITTEE_MEMBER {
        @Override
        public String toString() {
            return "Μέλος ΕΕ";
        }
    },
    ID_VERIFIER {
        @Override
        public String toString() {
            return "Χειριστής";
        }
    },
    ID_VERIFIER_VICE {
        @Override
        public String toString() {
            return "Αναπληρωτής Χειριστής";
        }
    },
    TREASURER {
        @Override
        public String toString() {
            return "Ταμίας";
        }
    };
    
    public static String convertToEquivalent(String contributionType) {
        if(contributionType == null) {
            return null;
        }
        else if(contributionType.equals(COMMITTEE_LEADER_VICE.name())) {
            return COMMITTEE_LEADER.name();
        }
        else if(contributionType.equals(ID_VERIFIER_VICE.name())) {
            return ID_VERIFIER.name();
        }
        else {
            return contributionType;
        }
    }
    
    public static boolean isLeader(ContributionType contributionType) {
        return contributionType != null && (contributionType.equals(COMMITTEE_LEADER) || contributionType.equals(COMMITTEE_LEADER_VICE));
    }
}
