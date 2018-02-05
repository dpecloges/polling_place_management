package com.ots.dpel.global.tpl;

/**
 *
 * @author Kostas Tzonas <ktzonas@ots.gr>
 */
public class InvalidTemplateException extends RuntimeException {

    public InvalidTemplateException() {
    }

    public InvalidTemplateException(String message) {
        super(message);
    }

    public InvalidTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTemplateException(Throwable cause) {
        super(cause);
    }

    public InvalidTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
