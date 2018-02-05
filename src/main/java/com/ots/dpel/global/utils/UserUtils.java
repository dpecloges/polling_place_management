package com.ots.dpel.global.utils;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.config.security.DpOAuth2Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author sdimitriadis
 */
public class UserUtils {
    
    /**
     * Επιστροφή του συνδεδεμένου χρήστη
     */
    public static DpUserDetailsDTO getUser() {
        DpOAuth2Authentication authentication = (DpOAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.getDpUserDetailsDTO() != null ? authentication.getDpUserDetailsDTO() : new DpUserDetailsDTO());
    }
    
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) ?
                authentication.getPrincipal().toString() : "anonymousUser";
    }
    
    public static String getUserFullName(String firstName, String lastName, boolean startWithLastName) {
        
        String fullName = "";
        
        if (startWithLastName) {
            fullName = (StringUtils.isEmpty(lastName) ? "" : lastName) +
                (StringUtils.isEmpty(firstName) ? "" : " " + firstName);
        } else {
            fullName = (StringUtils.isEmpty(firstName) ? "" : firstName) +
                (StringUtils.isEmpty(lastName) ? "" : " " + lastName);
        }
        
        fullName = StringUtils.trim(fullName);
        
        return fullName;
    }
    
    /**
     * Επιστροφή του id του συνδεδεμένου χρήστη
     */
    public static Long getUserId() {
        return getUser().getId();
    }
    
    /**
     * Επιστροφή του id εκλογικού τμήματος του συνδεδεμένου χρήστη
     */
    public static Long getUserElectionDepartmentId() {
        return getUser().getElectionDepartmentId();
    }
}
