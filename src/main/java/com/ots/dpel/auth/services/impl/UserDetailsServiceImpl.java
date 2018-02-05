package com.ots.dpel.auth.services.impl;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.auth.services.UserService;
import com.ots.dpel.system.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    
    @Override
    // @Cacheable(value = CacheService.USERS_CACHE, key = "#root.caches[0].name.concat(\"-\").concat(#username)")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        DpUserDetailsDTO crUserDetails = userService.findCrUserDetails(username);
        
        if (crUserDetails == null) {
            throw new UsernameNotFoundException(String.format("No such user: [ %s ]", username));
        } else {
            return crUserDetails;
        }
    }
    
}