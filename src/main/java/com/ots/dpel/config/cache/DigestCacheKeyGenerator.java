package com.ots.dpel.config.cache;

import com.ots.dpel.global.utils.HashUtil;
import com.ots.dpel.global.utils.StringUtilities;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Custom implementation of {@link KeyGenerator} that computes key values from a
 * method call, using the class name, method name and the
 * {@link Object#toString()}s of the arguments. These values are used to create
 * a compound string where the different values are separated by a configurable
 * string, and the final key value is computed from this compound string using a
 * configurable digest algorithm.
 *
 * <p>
 * Note that the digest algorithm identifier and the key part separator must be
 * non-null and non-empty. Also, the digest algorithm identifier must be
 * supported by {@link MessageDigest}.
 * </p>
 *
 * <p>
 * The client can configure this cache key generator so that the class name and
 * method name of each call are either included in the compound string that gets
 * digested, or are present as-is in the cache key. In the first case, the final
 * key is shorter, but it may be harder to inspect and debug key generation. In
 * the second case, the key is more human-readable, but the key length will get
 * larger, which may be a problem when using caching systems like memcache which
 * pose a strict limit on cache key length.
 * </p>
 *
 * <p>
 * Whether the method signature is digested or not can be controlled with the
 * {@code encodeMethodSignature} property.
 * </p>
 *
 * <p>
 * In the case of an empty argument list, only the method signature is used in
 * the generation of the key. In case null values are present in the argument
 * list of the call that is being cached, they are replaced by a string value
 * that can be configured with the {@code nullArgumentReplacement} property.
 * </p>
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class DigestCacheKeyGenerator implements KeyGenerator {
    private static final Logger LOG = LoggerFactory
        .getLogger(DigestCacheKeyGenerator.class);
    
    private static final String DEFAULT_HASH_ALGORITHM = "md5";
    private static final String DEFAULT_KEY_PART_SEPARATOR = "###";
    private static final String DEFAULT_NULL_ARGUMENT_REPLACEMENT = "---";
    private static final boolean DEFAULT_ENCODE_METHOD_SIGNATURE = false;
    
    // Values supported by MessageDigest
    private String digestAlgorithm = DEFAULT_HASH_ALGORITHM;
    private String keyPartSeparator = DEFAULT_KEY_PART_SEPARATOR;
    private String nullArgumentReplacement = DEFAULT_NULL_ARGUMENT_REPLACEMENT;
    // If true, then the class name and method name will also be digested with
    // the arguments
    private boolean encodeMethodSignature = DEFAULT_ENCODE_METHOD_SIGNATURE;
    
    public DigestCacheKeyGenerator() {
        this(DEFAULT_HASH_ALGORITHM);
    }
    
    public DigestCacheKeyGenerator(String digestAlgorithm) {
        this(digestAlgorithm, DEFAULT_KEY_PART_SEPARATOR);
    }
    
    public DigestCacheKeyGenerator(String digestAlgorithm,
                                   String keyPartSeparator) {
        this(digestAlgorithm, keyPartSeparator,
            DEFAULT_NULL_ARGUMENT_REPLACEMENT);
    }
    
    public DigestCacheKeyGenerator(String digestAlgorithm,
                                   String keyPartSeparator, String nullArgumentReplacement) {
        this(digestAlgorithm, keyPartSeparator, nullArgumentReplacement,
            DEFAULT_ENCODE_METHOD_SIGNATURE);
    }
    
    public DigestCacheKeyGenerator(String digestAlgorithm,
                                   String keyPartSeparator, String nullArgumentReplacement,
                                   boolean encodeMethodSignature) {
        if (StringUtils.isBlank(digestAlgorithm)) {
            throw new IllegalArgumentException(
                "Missing digest algorithm for DigestCacheKeyGenerator");
        }
        if (StringUtils.isBlank(keyPartSeparator)) {
            throw new IllegalArgumentException(
                "Missing key part separator for DigestCacheKeyGenerator");
        }
        if (StringUtils.isBlank(nullArgumentReplacement)) {
            throw new IllegalArgumentException(
                "Missing null argument replacement for DigestCacheKeyGenerator");
        }
        
        try {
            MessageDigest.getInstance(digestAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(
                "Invalid digest algorithm for DigestCacheKeyGenerator: "
                    + digestAlgorithm);
        }
        
        this.digestAlgorithm = digestAlgorithm;
        this.keyPartSeparator = keyPartSeparator;
        this.nullArgumentReplacement = nullArgumentReplacement;
        this.encodeMethodSignature = encodeMethodSignature;
    }
    
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (target == null || method == null) {
            throw new IllegalArgumentException(
                "Object and method must not be null");
        }
        String className = AopProxyUtils.ultimateTargetClass(target)
            .getSimpleName().intern();
        String methodName = method.getName();
        
        try {
            return encodeMethodSignature ? digestAll(className, methodName,
                params) : digestArgsOnly(className, methodName, params);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported encoding", e);
        }
    }
    
    private String digestArgsOnly(String className, String methodName,
                                  Object... params) throws UnsupportedEncodingException {
        Object[] cacheKeyParts = (params != null && params.length > 0 ? new Object[params.length]
            : new Object[]{});
        
        // To stop join() from throwing NPE
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                cacheKeyParts[i] = (params[i] != null ? params[i].toString()
                    : this.nullArgumentReplacement);
            }
        }
        String digestedCacheKeyParts = "";
        String joinedCacheKeyParts = "";
        
        if (cacheKeyParts.length > 0) {
            joinedCacheKeyParts = StringUtilities.join(this.keyPartSeparator,
                cacheKeyParts);
            digestedCacheKeyParts = "-"
                + HashUtil.hashAsHexString(this.digestAlgorithm,
                joinedCacheKeyParts.getBytes("UTF-8"));
        }
        
        LOG.debug("Class: {}, Method: {}, Args: {}, Digested args: {}",
            new Object[]{className, methodName, joinedCacheKeyParts,
                digestedCacheKeyParts});
        
        String cacheKey = StringUtilities.join("", className, ".", methodName,
            digestedCacheKeyParts);
        LOG.debug("Cache key: {}", cacheKey);
        
        return cacheKey;
    }
    
    private String digestAll(String className, String methodName,
                             Object... params) throws UnsupportedEncodingException {
        String methodSignature = className + "." + methodName;
        Object[] cacheKeyParts = (params != null && params.length > 0 ? new Object[params.length + 1]
            : new Object[1]); // To stop join() from throwing NPE
        cacheKeyParts[0] = methodSignature;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                cacheKeyParts[i + 1] = (params[i] != null ? params[i]
                    .toString() : this.nullArgumentReplacement);
            }
        }
        
        String joinedCacheKeyParts = StringUtilities.join(
            this.keyPartSeparator, cacheKeyParts);
        String digestedCacheKeyParts = HashUtil.hashAsHexString(
            this.digestAlgorithm, joinedCacheKeyParts.getBytes("UTF-8"));
        
        LOG.debug(
            "Class: {}, Method: {}, Cache key: {}, Digested cache key: {}",
            new Object[]{className, methodName, joinedCacheKeyParts,
                digestedCacheKeyParts});
        
        return digestedCacheKeyParts;
    }
}
