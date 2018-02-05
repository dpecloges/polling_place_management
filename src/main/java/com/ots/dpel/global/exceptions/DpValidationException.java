package com.ots.dpel.global.exceptions;

import com.ots.dpel.global.errors.ErrorMessage;
import com.ots.dpel.global.errors.ErrorReport;

/**
 * @author sdimitriadis
 */
public class DpValidationException extends DpRuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public DpValidationException() {
        super();
    }
    
    public DpValidationException(String string) {
        super(string);
    }
    
    public DpValidationException(String code, String message) {
        super(code, message);
    }
    
    public DpValidationException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
    public DpValidationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
    
    public DpValidationException(Throwable thrwbl) {
        super(thrwbl);
    }
    
    public DpValidationException(ErrorReport errorReport) {
        super(errorReport);
    }
    
    public DpValidationException(String string, Throwable thrwbl,
                                 boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
}
