package com.ots.dpel.common.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.ots.dpel.common.core.dto.MailMessageDto;

/**
 * Event που δημιουργείται έπειτα από επιτυχημένη αποστολή 
 * ενός ή περισσότερων μηνυμάτων email.
 * 
 * <p>Το υποστηριζόμενο event source είναι τύπου {@code List<MailMessageDto>} 
 * και είναι η λίστα των απεσταλμένων μηνυμάτων email.
 * </p>
 * 
 * @author ktzonas
 * @throws NullPointerException
 *     αν το event source είναι {@code null}
 * @throws IllegalArgumentException 
 *     αν το event source είναι διαφορετικού τύπου από τον υποστηριζόμενο
 */
public class MailMessageSentEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;

    public MailMessageSentEvent(Object source) {
        super(source);
        
        validateSource(source);
    }
    
    private void validateSource(Object source) {
        if (source == null) {
            throw new NullPointerException("null source");
        }
        
        if (!(source instanceof List)) {
            throw new IllegalArgumentException("source must be a list");
        }
        
        List<?> sourceList = (List<?>) source;
        if (sourceList.isEmpty()) {
            throw new IllegalArgumentException("source must be a non-empty list");
        }
        
        Object sourceListItem = sourceList.get(0);
        if (!(sourceListItem instanceof MailMessageDto)) {
            throw new IllegalArgumentException("source must contain instances of MailMessageSentEvent");
        }
    }
}
