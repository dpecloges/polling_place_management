package com.ots.dpel.global.errors;

import com.ots.dpel.global.handlers.GlobalExceptionHandler;

/**
 * <p>Κλάση περιγραφής του Response για exceptions που διαχειρίζονται από την κλάση
 * {@link GlobalExceptionHandler}</p>
 */
public class GlobalExceptionResponse extends ExceptionResponse {
    
    public GlobalExceptionResponse(String errorId, String errorMessage, String url, String username, Exception exception) {
        super(errorId, errorMessage, url, username, exception);
    }
}
