package com.ots.dpel.config.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DpAuthorizationPermissionEvaluator implements PermissionEvaluator {
    private static final Logger logger = LogManager.getLogger(DpAuthorizationPermissionEvaluator.class);
    
    public DpAuthorizationPermissionEvaluator() {
    }
    
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return hasPermission(authentication, permission);
    }
    
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        
        int indexOf = -1;
        String combinedPermission = (String) permission;
        
        //Έλεγχος δικαιώματος "combinedsave"
        indexOf = combinedPermission.indexOf("combinedsave");
        
        if (indexOf > -1) {
            //Μετασχηματισμός σε βασικό permission (.create, .update)
            permission = combinedPermission.substring(0, indexOf) + (targetId == null ? "create" : "update");
            return hasPermission(authentication, permission);
        }
        ;
        
        //Έλεγχος δικαιώματος "combinedview"
        indexOf = combinedPermission.indexOf("combinedview");
        
        if (indexOf > -1) {
            //Μετασχηματισμός σε βασικό permission (.create, .view)
            permission = combinedPermission.substring(0, indexOf) + (targetId == null ? "create" : "view");
            return hasPermission(authentication, permission);
        }
        ;
        
        //Σε αντίθετη περίπτωση γίνεται έλεγχος αυτούσιας της τιμής του permission
        return hasPermission(authentication, permission);
    }
    
    private boolean hasPermission(Authentication authentication, Object permission) {
        return authentication != null && permission != null ? authentication.getAuthorities().contains(new SimpleGrantedAuthority(String.valueOf(permission))) : false;
    }
    
}