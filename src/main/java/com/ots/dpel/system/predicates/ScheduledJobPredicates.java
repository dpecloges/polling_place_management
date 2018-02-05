package com.ots.dpel.system.predicates;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.global.utils.DpDateUtils;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.system.args.ScheduledJobArgs;
import com.ots.dpel.system.core.domain.QScheduledJob;
import com.ots.dpel.system.core.enums.ScheduledJobStatus;

import java.util.Date;

/**
 * Ορισμός predicates για το entity των προγραμματισμένων ενεργειών
 */
public class ScheduledJobPredicates {
    
    /**
     * Περιγραφή
     */
    public static Predicate descriptionContains(final String searchTerm) {
        return QScheduledJob.scheduledJob.description.upper().contains(DpTextUtils.toUpperCaseGreekSupport(searchTerm));
    }
    
    /**
     * Κατάσταση
     */
    public static Predicate statusEquals(final ScheduledJobStatus searchTerm) {
        return QScheduledJob.scheduledJob.status.eq(searchTerm);
    }
    
    /**
     * Ημερομηνία Προγραμματισμού από
     */
    public static Predicate scheduleDateAfter(final Date searchTerm) {
        return QScheduledJob.scheduledJob.scheduleDate.goe(searchTerm);
    }
    
    /**
     * Ημερομηνία Προγραμματισμού έως
     */
    public static Predicate scheduleDateBefore(final Date searchTerm) {
        return QScheduledJob.scheduledJob.scheduleDate.loe(searchTerm);
    }
    
    /**
     * Ημερομηνία Εκτέλεσης από
     */
    public static Predicate fireDateAfter(final Date searchTerm) {
        return QScheduledJob.scheduledJob.fireDate.goe(searchTerm);
    }
    
    /**
     * Ημερομηνία Εκτέλεσης έως
     */
    public static Predicate fireDateBefore(final Date searchTerm) {
        return QScheduledJob.scheduledJob.fireDate.loe(searchTerm);
    }
    
    /**
     * Χρήστης Προγραμματισμού Ενέργειας
     */
    public static Predicate scheduledByEquals(final String searchTerm) {
        return QScheduledJob.scheduledJob.scheduledBy.eq(searchTerm);
    }
    
    /**
     * Κριτήρια αναζήτησης του ευρετηρίου των Προγραμματισμένων Εργασιών
     */
    public static BooleanBuilder createScheduledJobsIndexPredicate(SearchableArguments arguments) {
        
        ScheduledJobArgs args = (ScheduledJobArgs) arguments;
        
        BooleanBuilder predicate = new BooleanBuilder();
        
        // Περιγραφή
        if (!DpTextUtils.isEmpty(args.getDescription())) {
            predicate.and(descriptionContains(args.getDescription()));
        }
        
        // Κατάσταση
        if (args.getStatus() != null) {
            predicate.and(statusEquals(args.getStatus()));
        }
        
        // Ημερομηνία Προγραμματισμού από
        if (args.getScheduleDateFrom() != null) {
            predicate.and(scheduleDateAfter(args.getScheduleDateFrom()));
        }
        
        // Ημερομηνία Προγραμματισμού έως
        if (args.getScheduleDateTo() != null) {
            predicate.and(scheduleDateBefore(DpDateUtils.getEndOfDay(args.getScheduleDateTo())));
        }
        
        // Ημερομηνία Εκτέλεσης από
        if (args.getFireDateFrom() != null) {
            predicate.and(fireDateAfter(args.getFireDateFrom()));
        }
        
        // Ημερομηνία Εκτέλεσης έως
        if (args.getFireDateTo() != null) {
            predicate.and(fireDateBefore(DpDateUtils.getEndOfDay(args.getFireDateTo())));
        }
        
        return predicate;
    }
    
}
