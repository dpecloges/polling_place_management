package com.ots.dpel.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ots.dpel.global.errors.FilterExceptionResponse;
import com.ots.dpel.global.utils.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Φίλτρο που εκτελείται σε κάθε Request έχοντας ως βασική λειτουργία
 * το catching των exceptions που πραγματοποιούνται στην εκτέλεση των
 * υπόλοιπων φίλτρων της αλυσίδας security chain και κυρίως των
 * {@link AccessDeniedException} που γίνονται throw από το {@link OamAuthenticationFilter}
 */
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LogManager.getLogger(ExceptionHandlerFilter.class);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
        IOException {
        
        try {
            
            filterChain.doFilter(request, response);
            
        } catch (AccessDeniedException ex) {
            
            //Δημιουργία αντικειμένου για το ExceptionResponse
            FilterExceptionResponse errorResponse = (FilterExceptionResponse) ExceptionUtils.getExceptionResponse(ex, new ServletWebRequest
                (request), false);
            
            logger.error("Error Id: ".concat(errorResponse.getErrorId()));
            logger.error("Url: ".concat(errorResponse.getUrl()));
            logger.error("User: ".concat(errorResponse.getUsername() != null ? errorResponse.getUsername() : "-"));
            logger.error(errorResponse.getErrorMessage(), ex);
            
            ThreadContext.clearAll();
            
            //Στοιχεία των headers του response
            response.addHeader("Content-type", "application/json; charset=utf-8");
            
            //Status του response
            response.setStatus(HttpStatus.FORBIDDEN.value());
            
            //Body του response
            response.getWriter().write(convertExceptionResponseToJson(errorResponse));
        }
        
    }
    
    /**
     * Serialization του ExceptionResponse object σε String
     * Εξαιρούνται τα πεδία exception, username και sqlMessage
     * @param exceptionResponse
     * @return
     * @throws JsonProcessingException
     */
    private String convertExceptionResponseToJson(FilterExceptionResponse exceptionResponse) throws JsonProcessingException {
        
        if (exceptionResponse == null) {
            return "";
        }
        
        ObjectMapper mapper = new ObjectMapper();
        
        //Εξαίρεση συγκεκριμένων πεδίων από το serialization (exception, username, sqlMessage)
        SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("exception", "username", "sqlMessage");
        FilterProvider filters = new SimpleFilterProvider().addFilter("accessDeniedFilter", theFilter);
        
        return mapper.writer(filters).writeValueAsString(exceptionResponse);
    }
}
