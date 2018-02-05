package com.ots.dpel.system.controllers;

import com.ots.dpel.system.dto.CacheEvictionResponse;
import com.ots.dpel.system.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;

/**
 * Controller Διαχείρισης των Caches της Εφαρμογής
 */
@Controller
@RequestMapping("/system/cache")
public class CacheEvictionController {
    
    @Autowired
    private CacheService cacheService;
    
    /**
     * One Cache Eviction
     * @param principal
     * @param cacheName Το όνομα της Cache που θα γίνει evict
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(method = RequestMethod.GET, value = "evict/{cacheName}")
    public ResponseEntity<CacheEvictionResponse> evictCache(Principal principal, @PathVariable("cacheName") String cacheName) {
        
        CacheEvictionResponse response = new CacheEvictionResponse(cacheName, new Date(), true,
                "Data in " + cacheName + " cache were successfully evicted");
        
        switch (cacheName) {
            
            case CacheService.USERS_CACHE:
                cacheService.removeAllUsersCache();
                break;
            
            case CacheService.ADMIN_UNITS_CACHE:
                cacheService.removeAllAdminUnitsCache();
                break;
            
            case CacheService.COUNTRIES_CACHE:
                cacheService.removeAllCountriesCache();
                break;
            
            default:
                response.setEvicted(false);
                response.setMessage("Cache " + cacheName + " is not defined");
                break;
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * All Caches Eviction
     * @param principal
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(method = RequestMethod.GET, value = "evictall")
    public ResponseEntity<CacheEvictionResponse> evictCache(Principal principal) {
        
        CacheEvictionResponse response = new CacheEvictionResponse("all", new Date(), true,
                "Data in all caches were successfully evicted");
        
        cacheService.removeAllCaches();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * One User Eviction from Users Cache
     * @param principal
     * @param username
     * @return
     */
    @PreAuthorize("hasAuthority('sys.admin')")
    @RequestMapping(method = RequestMethod.GET, value = "evict/users/{username}")
    public ResponseEntity<CacheEvictionResponse> evictUserFromCache(Principal principal, @PathVariable("username") String username) {
        
        CacheEvictionResponse response = new CacheEvictionResponse(CacheService.USERS_CACHE, new Date(), true,
                "User " + username + " was successfully evicted from cache");
        
        cacheService.removeUserFromCache(username);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
