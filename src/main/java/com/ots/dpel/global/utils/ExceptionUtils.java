package com.ots.dpel.global.utils;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.global.errors.ExceptionResponse;
import com.ots.dpel.global.errors.FilterExceptionResponse;
import com.ots.dpel.global.errors.GlobalExceptionResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Βοηθητικές λειτουργίες που αφορούν Exceptions
 * Περιλαμβάνονται στατικές μέθοδοι
 */
public class ExceptionUtils {
    
    /**
     * Δημιουργία αντικειμένου response με τα βασικά στοιχεία του exception
     * @param ex      αντικείμενο exception
     * @param request αντικείμενο http request
     * @param global  ένδειξη εάν βρισκόμαστε στον {@link com.ots.dpel.global.handlers.GlobalExceptionHandler} true
     *                ή στον {@link com.ots.dpel.config.security.ExceptionHandlerFilter} false
     * @return
     */
    public static ExceptionResponse getExceptionResponse(Exception ex, WebRequest request, boolean global) {
        
        //Δημιουργία αναγνωριστικού σφάλματος
        String errorId = UuidUtils.generateId();
        //Βασικό μήνυμα σφάλματος
        String errorMessage = (ex.getMessage() == null ? ex.toString() : ex.getMessage());
        
        //Url της κλήσης στο API από την οποία προήλθε το exception
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        
        String url = httpServletRequest.getRequestURL().toString() +
            (httpServletRequest.getQueryString() == null ? "" : "?" + httpServletRequest.getQueryString());
        
        //Συνδεδεμένος χρήστης
        String username = UserUtils.getUsername();
        
        ThreadContext.put("errorId", errorId);
        ThreadContext.put("url", url);
        ThreadContext.put("user", username);
        
        return global ?
            new GlobalExceptionResponse(errorId, errorMessage, url, username, ex) :
            new FilterExceptionResponse(errorId, errorMessage, url, username, ex);
    }
}
