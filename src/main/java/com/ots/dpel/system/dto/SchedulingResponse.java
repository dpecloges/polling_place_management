package com.ots.dpel.system.dto;

import java.util.Date;

/**
 * Στοιχεία λειτουργιών χρονοπρογραμματισμένων ενεργειών - Αντικείμενο Response DTO
 */
public class SchedulingResponse {

    /**
     * Προτεινόμενη ημερομηνία εκτέλεσης χρονοπρογραμματισμένης εργασίας
     */
    private Date suggestedFireDate;

    public SchedulingResponse() {
    }

    public SchedulingResponse(Date suggestedFireDate) {
        this.suggestedFireDate = suggestedFireDate;
    }

    public Date getSuggestedFireDate() {
        return suggestedFireDate;
    }

    public void setSuggestedFireDate(Date suggestedFireDate) {
        this.suggestedFireDate = suggestedFireDate;
    }
}
