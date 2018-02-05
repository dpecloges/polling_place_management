package com.ots.dpel.global.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class NetworkUtil {
    
    /**
     * Returns the client's IP address which performs an HTTP request. This
     * method is reverse proxy aware and tries first to get the IP address from
     * <a href="http://en.wikipedia.org/wiki/X-Forwarded-For">X-Forwarded-For
     * (XFF)</a> header. If nothing is found, then it returns normal client IP.
     *
     * <p>
     * This solution was created by <a
     * href="http://www.mkyong.com/java/how-to-get-client-ip-address-in-java/"
     * >Mkyong.</a>
     * </p>
     * @param request an http servlet request
     * @return an IPv4 string or null if not found
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        
        // is client behind something?
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
        return ipAddress;
    }
    
    /**
     * Checks if a network port number is within bounds, i.e. port>0 and
     * port<65536
     * @param port a network port number
     * @return true if port number is within bounds
     */
    public static boolean isPortWithinBounds(int port) {
        return (port > 0) && (port <= 65536);
    }
}
