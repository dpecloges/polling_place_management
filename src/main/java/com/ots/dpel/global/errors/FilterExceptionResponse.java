package com.ots.dpel.global.errors;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * <p>Κλάση περιγραφής του Response για exceptions που διαχειρίζονται από την κλάση
 * {@link FilterExceptionResponse}</p>
 */
@JsonFilter("accessDeniedFilter")
public class FilterExceptionResponse extends ExceptionResponse {
    
    public FilterExceptionResponse(String errorId, String errorMessage, String url, String username, Exception exception) {
        super(errorId, errorMessage, url, username, exception);
    }
}
