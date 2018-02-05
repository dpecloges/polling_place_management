package com.ots.dpel.global.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class with hash related methods.
 * @author ktzonas
 */
public class HashUtil {
    private static final Logger logger = LoggerFactory
        .getLogger(HashUtil.class);
    
    public static final String SHA256 = "SHA-256";
    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";
    
    /**
     * Calculates the SHA-256 hash of a byte stream.
     * @param is  byte stream
     * @param len length of the stream
     * @return byte array
     */
    public static byte[] sha256(InputStream is, int len) {
        return hashAsBytes(SHA256, is, len);
    }
    
    /**
     * Calculates the SHA-256 hash of a byte array.
     * @param bytes byte array
     * @return byte array
     */
    public static byte[] sha256(byte[] bytes) {
        return hashAsBytes(SHA256, bytes);
    }
    
    /**
     * Returns a hex string of the SHA-256 hash of a byte array.
     * @param bytes byte array
     * @return hex string
     */
    public static String sha256AsString(byte[] bytes) {
        return hashAsHexString(SHA256, bytes);
    }
    
    /**
     * Returns a hex string of the SHA-256 hash of a byte stream.
     * @param is  byte stream
     * @param len stream length
     * @return hex string
     */
    public static String sha256AsString(InputStream is, int len) {
        return hashAsHexString(SHA256, is, len);
    }
    
    /**
     * Calculates the SHA-1 hash of a byte stream.
     * @param is  byte stream
     * @param len length of the stream
     * @return byte array
     */
    public static byte[] sha1(InputStream is, int len) {
        return hashAsBytes(SHA1, is, len);
    }
    
    /**
     * Calculates the SHA-1 hash of a byte array.
     * @param bytes byte array
     * @return byte array
     */
    public static byte[] sha1(byte[] bytes) {
        return hashAsBytes(SHA1, bytes);
    }
    
    /**
     * Returns a hex string of the SHA-1 hash of a byte array.
     * @param bytes byte array
     * @return hex string
     */
    public static String sha1AsString(byte[] bytes) {
        return hashAsHexString(SHA1, bytes);
    }
    
    /**
     * Returns a hex string of the SHA-1 hash of a byte stream.
     * @param is  byte stream
     * @param len stream length
     * @return hex string
     */
    public static String sha1AsString(InputStream is, int len) {
        return hashAsHexString(SHA1, is, len);
    }
    
    /**
     * Calculates the MD-5 hash of a byte stream.
     * @param is  byte stream
     * @param len length of the stream
     * @return byte array
     */
    public static byte[] md5(InputStream is, int len) {
        return hashAsBytes(MD5, is, len);
    }
    
    /**
     * Calculates the MD-5 hash of a byte array.
     * @param bytes byte array
     * @return byte array
     */
    public static byte[] md5(byte[] bytes) {
        return hashAsBytes(MD5, bytes);
    }
    
    /**
     * Returns a hex string of the MD-5 hash of a byte array.
     * @param bytes byte array
     * @return hex string
     */
    public static String md5AsString(byte[] bytes) {
        return hashAsHexString(MD5, bytes);
    }
    
    /**
     * Returns a hex string of the MD-5 hash of a byte stream.
     * @param is  byte stream
     * @param len stream length
     * @return hex string
     */
    public static String md5AsString(InputStream is, int len) {
        return hashAsHexString(MD5, is, len);
    }
    
    /**
     * Calculates a hash of a byte array using the specified algorithm. The
     * algorithms that can be defined are the ones supported by
     * {@link java.security.MessageDigest}.
     * @param algorithm Distinctive name of a hashing algorithm
     * @param bytes     byte array
     * @return byte array
     */
    public static byte[] hashAsBytes(String algorithm, byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bytes);
            return md.digest();
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            logger.error("Error while computing digest: ", e);
        }
        return null;
    }
    
    /**
     * Calculates a hash of a byte stream using the specified algorithm. The
     * algorithms that can be defined are the ones supported by
     * {@link java.security.MessageDigest}.
     * @param algorithm Distinctive name of a hashing algorithm
     * @param is        byte stream
     * @param len       length of the byte stream
     * @return byte array
     */
    public static byte[] hashAsBytes(String algorithm, InputStream is, int len) {
        byte[] bytes = new byte[len];
        
        try {
            is.read(bytes);
        } catch (Exception e) {
            logger.error("Error while reading bytes: ", e);
        }
        
        byte[] hashBytes = hashAsBytes(algorithm, bytes);
        
        try {
            is.reset();
        } catch (Exception e) {
            logger.warn("Error while resetting input stream: ", e);
        }
        return hashBytes;
    }
    
    /**
     * Returns a hex string of a hash of a byte array using the specified
     * algorithm. The algorithms that can be defined are the ones supported by
     * {@link java.security.MessageDigest}.
     * @param algorithm Distinctive name of a hashing algorithm
     * @param bytes     byte array
     * @return hex string
     */
    public static String hashAsHexString(String algorithm, byte[] bytes) {
        byte[] digest = hashAsBytes(algorithm, bytes);
        StringBuilder str = new StringBuilder();
        for (byte b : digest) {
            str.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return str.toString();
    }
    
    /**
     * Returns a hex string of a hash of a byte stream using the specified
     * algorithm. The algorithms that can be defined are the ones supported by
     * {@link java.security.MessageDigest}.
     * @param algorithm Distinctive name of a hashing algorithm
     * @param is        byte stream
     * @param len       length of the byte stream
     * @return hex string
     */
    public static String hashAsHexString(String algorithm, InputStream is,
                                         int len) {
        byte[] digest = hashAsBytes(algorithm, is, len);
        StringBuilder str = new StringBuilder();
        for (byte b : digest) {
            str.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return str.toString();
    }
}
