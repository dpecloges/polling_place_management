package com.ots.dpel.global.utils;

import java.text.SimpleDateFormat;

public class DateUtil {
    
    /**
     * Date format "dd/MM/yyyy" for Greek locale
     */
    public static SimpleDateFormat DATE_FORMAT_EL = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Datetime ISO 8601 format "yyyy-MM-dd hh:mm:ss"
     */
    public static SimpleDateFormat DATETIME_FORMAT_ISO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Datetime ISO 8601 format "yyyy-MM-dd hh:mm"
     */
    public static SimpleDateFormat DATETIME_NO_SECS_FORMAT_ISO = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    /**
     * Date ISO 8601 format "yyyy-MM-dd"
     */
    public static SimpleDateFormat DATE_FORMAT_ISO = new SimpleDateFormat("yyyy-MM-dd");
}