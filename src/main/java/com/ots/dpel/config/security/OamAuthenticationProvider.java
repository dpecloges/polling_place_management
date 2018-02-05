package com.ots.dpel.config.security;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Simple Authentication Provider
 * Δε γίνεται κάποιος έλεγχος κάποιου password μόνο ότι υπάρχει χρήστης με συγκεκριμένο username στη βάση των χρηστών
 */
@Component
public class OamAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    /**
     * Simple User Authentication
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        DpUserDetailsDTO user = (DpUserDetailsDTO) userDetailsService.loadUserByUsername(username);
        
        //TODO Έλεγχος ότι υπάρχει ο χρήστης και δεν είναι Null
        
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
