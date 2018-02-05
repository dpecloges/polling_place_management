package com.ots.dpel.system.services.impl;

import com.ots.dpel.system.services.CacheService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Υλοποίηση λειτουργιών που αφορούν το Caching της εφαρμογής
 * Κυρίως παρέχονται μέθοδοι για το Cache Eviction
 */
@Service
public class CacheServiceImpl implements CacheService {
    
    private static final Logger logger = LogManager.getLogger(CacheServiceImpl.class);
    
    @Override
    @CacheEvict(value = {CacheService.USERS_CACHE, CacheService.ADMIN_UNITS_CACHE, CacheService.COUNTRIES_CACHE, CacheService.RESULTS_CACHE, CacheService.SNAPSHOTS_CACHE},
            allEntries = true, beforeInvocation = true)
    public void removeAllCaches() {
        logger.debug("Executing removeAllCaches method");
    }
    
    @Override
    @CacheEvict(value = CacheService.USERS_CACHE, allEntries = true, beforeInvocation = true)
    public void removeAllUsersCache() {
        logger.debug("Executing removeAllUsersCache method");
    }
    
    @Override
    @CacheEvict(value = CacheService.USERS_CACHE, key = "#root.caches[0].name.concat(\"-\").concat(#username)")
    public void removeUserFromCache(String username) {
        logger.debug("Executing removeUserFromCache method");
    }
    
    @Override
    @CacheEvict(value = CacheService.ADMIN_UNITS_CACHE, allEntries = true, beforeInvocation = true)
    public void removeAllAdminUnitsCache() {
        logger.debug("Executing removeAllAdminUnitsCache method");
    }
    
    @Override
    @CacheEvict(value = CacheService.COUNTRIES_CACHE, allEntries = true, beforeInvocation = true)
    public void removeAllCountriesCache() {
        logger.debug("Executing removeAllCountriesCache method");
    }
    
    @Override
    @CacheEvict(value = CacheService.RESULTS_CACHE, allEntries = true, beforeInvocation = true)
    public void removeAllResultsCache() {
        logger.debug("Executing removeAllResultsCache method");
    }
    
    @Override
    @CacheEvict(value = CacheService.SNAPSHOTS_CACHE, allEntries = true, beforeInvocation = true)
    public void removeAllSnapshotsCache() {
        logger.debug("Executing removeAllSnapshotsCache method");
    }
}
