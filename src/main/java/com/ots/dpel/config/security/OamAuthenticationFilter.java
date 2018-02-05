package com.ots.dpel.config.security;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Φίλτρο που εκτελείται σε κάθε Request έχοντας ως βασική λειτουργία
 * την ανάκτηση (φόρτωση) των στοιχείων του συνδεδεμένου χρήστη στο αντικείμενο
 * {@link Authentication} του {@link SecurityContextHolder}
 * ώστε να είναι εφικτοί οι έλεγχοι στα PreAuthorize με το Spring Security
 */
public class OamAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private static final Logger logger = LogManager.getLogger(OamAuthenticationFilter.class);
    private static String INSECURED_URLS[] = {"auth/login", "auth/logout", "system/indexing/reindex", "ct/citizens/updatecodes"};
    private static String CHECK_URL = "auth/check";
    @Autowired
    private Environment environment;
    
    // @Autowired
    // private SecurityConfigBean securityProperties;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        //Το url για τον έλεγχο του εάν ο χρήστης είναι ήδη συνδεδεμένος στην εφαρμογή εξαιρείται από τον έλεγχο
        //που πραγματοποιείται από το συγκεκριμένο φίλτρο
        //Ομοίως για το url για το εικονικό Logout του χρήστη από την εφαρμογή με την έννοια ότι δεν κάνει τίποτε
        //άλλο παρά μόνο να κάνει evict τα στοιχεία του από την Cache
        for (int i = 0; i < INSECURED_URLS.length; i++) {
            if (httpRequest.getRequestURI().contains(INSECURED_URLS[i])) chain.doFilter(request, response);
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            
            //Ανάκτηση των στοιχείων του χρήστη με βάση το username του
            String requestHeaderUser = ""; // securityProperties.getRequestHeaderUsernameOam();
            String username = httpRequest.getHeader(requestHeaderUser);
            
            //Ανάκτηση ένδειξης εάν είναι ενεργό το development spring profile
            boolean productionProfile = Arrays.asList(environment.getActiveProfiles()).contains("production");
            boolean stagingProfile = Arrays.asList(environment.getActiveProfiles()).contains("staging");
            
            //Εάν δεν είναι ενεργό το production και το staging spring profile
            if (!productionProfile && !stagingProfile) {
                
                //Καταχώρηση username από σχετικό header του request ή
                //παράμετρο του security.properties εάν δεν υπάρχει ήδη στα headers του request
                if (username == null) {

                    /*//Έλεγχος ότι έχει ήδη προστεθεί στα headers του request το Dev-User
                    String devUser = httpRequest.getHeader(securityProperties.getRequestHeaderDevUser());

                    username = ((devUser == null || devUser.isEmpty()) ?
                            securityProperties.getRequestHeaderUsernameDevelopmentTest() :
                            devUser);*/
                }
                
            } else {
                
                //Έλεγχος απαγόρευσης άμεσης πρόσβασης στις κλήσεις προς το API της εφαρμογής
                
                if (logger.isTraceEnabled()) {
                    
                    logger.trace("*** httpRequest.getHeaderNames()");
                    Enumeration<String> requestHeaderNames = httpRequest.getHeaderNames();
                    while (requestHeaderNames.hasMoreElements()) {
                        String headerName = requestHeaderNames.nextElement();
                        logger.trace(headerName + ": " + httpRequest.getHeader(headerName));
                    }
                    
                    logger.trace("*** httpResponse.getHeaderNames()");
                    Iterator<String> responseHeadersIterator = httpResponse.getHeaderNames().iterator();
                    while (responseHeadersIterator.hasNext()) {
                        String headerName = responseHeadersIterator.next();
                        logger.trace(headerName + ": " + httpResponse.getHeader(headerName));
                    }
                }
                
                //1. Έλεγχος μεταξύ των headers του request (ECID-Context) και του response (X-ORACLE-DMS-ECID)
                
                boolean oracleECIDContextCheckError = false;
                String requestHeaderECIDContext = StringUtils.substringBetween(httpRequest.getHeader("ECID-Context"), ".", ";");
                String responseHeaderXOracleDmsECID = httpResponse.getHeader("X-ORACLE-DMS-ECID");
                
                if (requestHeaderECIDContext == null || responseHeaderXOracleDmsECID == null || !requestHeaderECIDContext.equals
                    (responseHeaderXOracleDmsECID)) {
                    oracleECIDContextCheckError = true;
                }
                
                //Εάν δεν ικανοποιείται καμία από τις 2 συνθήκες το request δεν εκτελείται
                if (oracleECIDContextCheckError) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("*** oracleECIDContextCheckError: " + oracleECIDContextCheckError);
                    }
                    //throw new AccessDeniedException("Direct access to api resource is not allowed.");
                }
                
                String crAccessHeader = "AccessGranted1";
                ((HttpServletResponse) response).addHeader("Cr-Access", crAccessHeader);
                
            }
            
            UserDetails userDetails = null;
            
            //Εάν το request url δεν είναι του ελέγχου για το login "auth/check" οι έλεγχοι πραγματοποιούνται κανονικά
            if (!httpRequest.getRequestURI().endsWith(CHECK_URL)) {
                
                //Έλεγχος ότι υπάρχει username διαθέσιμο που να δηλώνει το συνδεδεμένο χρήστη
                if (username == null) {
                    throw new AccessDeniedException("Access to api resource is not allowed. Not specified user.");
                }
                
                userDetails = userDetailsService.loadUserByUsername(username);
                
                //Έλεγχος ότι υπάρχει ο συνδεδεμένος χρήστης στους χρήστες της εφαρμογής
                if (userDetails == null) {
                    throw new AccessDeniedException("Access to api resource is not allowed. Not specified user details.");
                }
            } else {
                
                //Εάν το request url είναι του ελέγχου για το login "auth/check" οι έλεγχοι δεν πραγματοποιούνται
                //Από τη σχετική μέθοδο του AuthenticationController επιστρέφεται response status 400
                try {
                    userDetails = userDetailsService.loadUserByUsername(username);
                } catch (UsernameNotFoundException | AccessDeniedException ex) {
                    logger.trace("Caught exception from loadUserByUsername for Check Url");
                }
                
            }
            
            if (userDetails != null) {
                //Καταχώρηση στο Security Context
                UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails
                    .getAuthorities());
                userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            }
            
            chain.doFilter(request, response);
        }
    }
}
