package com.ots.dpel.global.utils;

import java.util.Locale;

/**
 * @author sdimitriadis
 */
public interface MessageSourceProvider {
    
    /**
     * Ανάκτηση μηνύματος
     */
    public String getMessage(String messageName);
    
    /**
     * Ανάκτηση μηνύματος με παραμέτρους
     */
    public String getMessage(String messageName, Object[] args);
    
    /**
     * Ανάκτηση μηνύματος με παραμέτρους και παροχή default μηνύματος
     */
    public String getMessage(String messageName, Object[] args, String defaultMessage);
    
    /**
     * Ανάκτηση μηνύματος με παραμέτρους και παροχή default μηνύματος καθορίζοντας και τη γλώσσα (Locale)
     */
    public String getMessage(String messageName, Object[] args, String defaultMessage, Locale locale);
    
    /**
     * Ανάκτηση της προεπιλεγμένης γλώσσας (Locale)
     */
    public Locale getLocale();
}
