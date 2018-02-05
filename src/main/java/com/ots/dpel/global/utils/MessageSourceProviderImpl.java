package com.ots.dpel.global.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
@Scope("prototype")
public class MessageSourceProviderImpl implements MessageSourceProvider {
    
    private static final Locale LOCALE = new Locale("el");
    private static final String DEFAULT_MESSAGE = "Γενικό μήνυμα";
    private static final Object[] ARGS = new Object[]{};
    
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private LocaleResolver localeResolver;
    
    
    public MessageSourceProviderImpl() {
    
    }
    
    @Override
    public String getMessage(String messageName) {
        return messageSource.getMessage(messageName, ARGS, DEFAULT_MESSAGE, LOCALE);
    }
    
    @Override
    public String getMessage(String messageName, Object[] args) {
        return messageSource.getMessage(messageName, args, DEFAULT_MESSAGE, LOCALE);
    }
    
    @Override
    public String getMessage(String messageName, Object[] args, String defaultMessage) {
        return messageSource.getMessage(messageName, args, defaultMessage, LOCALE);
    }
    
    @Override
    public String getMessage(String messageName, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(messageName, args, defaultMessage, locale);
    }
    
    @Override
    public Locale getLocale() {
        return LOCALE;
    }
}