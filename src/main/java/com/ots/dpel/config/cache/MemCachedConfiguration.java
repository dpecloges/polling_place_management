package com.ots.dpel.config.cache;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AddressProvider;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheClientFactory;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.ExtendedSSMCacheManager;
import com.google.code.ssm.spring.SSMCache;
import com.ots.dpel.system.services.CacheService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration σχετικά με Caching στην εφαρμογή στη MemCached
 * Εάν είναι ενεργοποιημένη η MemCached μέσα από το σχετικό String Runtime Profile
 */
@Configuration
@Profile(value = "memcached")
@EnableCaching
public class MemCachedConfiguration extends CachingConfigurerSupport {
    
    private static final Logger logger = LogManager.getLogger(MemCachedConfiguration.class);
    
    @Autowired
    private Environment environment;
    
    @Bean
    @Override
    public CacheManager cacheManager() {
        
        String memcachedHost = environment.getProperty("cache.memcached.host");
        String memcachedPort = environment.getProperty("cache.memcached.port");
        
        MemcacheClientFactoryImpl cacheClientFactory = new MemcacheClientFactoryImpl();
        AddressProvider addressProvider = new DefaultAddressProvider(memcachedHost.concat(":").concat(memcachedPort));
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true);
        
        List<SSMCache> ssmCaches = new ArrayList<SSMCache>();
        
        try {
            
            ssmCaches.add(getCacheFromFactory(cacheClientFactory, addressProvider, cacheConfiguration, CacheService.USERS_CACHE, 86400, true));
            ssmCaches.add(getCacheFromFactory(cacheClientFactory, addressProvider, cacheConfiguration, CacheService.ADMIN_UNITS_CACHE, 86400, true));
            ssmCaches.add(getCacheFromFactory(cacheClientFactory, addressProvider, cacheConfiguration, CacheService.COUNTRIES_CACHE, 86400, true));
            ssmCaches.add(getCacheFromFactory(cacheClientFactory, addressProvider, cacheConfiguration, CacheService.RESULTS_CACHE, 86400, true));
            ssmCaches.add(getCacheFromFactory(cacheClientFactory, addressProvider, cacheConfiguration, CacheService.SNAPSHOTS_CACHE, 86400, true));
            
        } catch (Exception e) {
            logger.error("Error in creating SSMCaches from factory: " + e.getMessage());
            e.printStackTrace();
        }
        
        ExtendedSSMCacheManager ssmCacheManager = new ExtendedSSMCacheManager();
        ssmCacheManager.setCaches(ssmCaches);
        
        return ssmCacheManager;
        
    }
    
    private SSMCache getCacheFromFactory(CacheClientFactory cacheClientFactory, AddressProvider addressProvider,
                                         CacheConfiguration cacheConfiguration, String cacheName, int expiration, boolean allowClear) throws
            Exception {
        
        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName(cacheName);
        cacheFactory.setCacheClientFactory(cacheClientFactory);
        cacheFactory.setAddressProvider(addressProvider);
        cacheFactory.setConfiguration(cacheConfiguration);
        
        Cache object = cacheFactory.getObject();
        
        return new SSMCache(object, expiration, allowClear);
    }
    
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        
        String algorithm = environment.getProperty("cache.keygen.digest.algorithm");
        String keyPartSeparator = environment.getProperty("cache.keygen.digest.keyPartSeparator");
        String nullArgumentReplacement = environment.getProperty("cache.keygen.digest.nullArgumentReplacement");
        boolean encodeMethodSignature = Boolean.parseBoolean(environment.getProperty("cache.keygen.digest.encodeMethodSignature"));
        
        DigestCacheKeyGenerator keyGenerator = new DigestCacheKeyGenerator(
                algorithm, keyPartSeparator, nullArgumentReplacement, encodeMethodSignature);
        return keyGenerator;
    }
    
}
