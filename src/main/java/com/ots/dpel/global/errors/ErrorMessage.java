package com.ots.dpel.global.errors;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
public class ErrorMessage {
    
    private String errorCode;
    private String errorMessage;
    private Serializable errorDetails;
    private boolean canBeIgnored;
    
    public ErrorMessage() {
    }
    
    public ErrorMessage(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public ErrorMessage(String errorCode, String errorMessage,
                        Serializable errorDetails) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }
    
    public ErrorMessage(String errorCode, String errorMessage,
                        Serializable errorDetails, boolean canBeIgnored) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.canBeIgnored = canBeIgnored;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public Serializable getErrorDetails() {
        return errorDetails;
    }
    
    public void setErrorDetails(Serializable errorDetails) {
        this.errorDetails = errorDetails;
    }
    
    public boolean isCanBeIgnored() {
        return canBeIgnored;
    }
    
    public void setCanBeIgnored(boolean canBeIgnored) {
        this.canBeIgnored = canBeIgnored;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.errorCode);
        hash = 71 * hash + Objects.hashCode(this.errorMessage);
        hash = 71 * hash + Objects.hashCode(this.errorDetails);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ErrorMessage other = (ErrorMessage) obj;
        if (!Objects.equals(this.errorCode, other.errorCode)) {
            return false;
        }
        if (!Objects.equals(this.errorMessage, other.errorMessage)) {
            return false;
        }
        if (!Objects.equals(this.errorDetails, other.errorDetails)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ErrorMessage{" + "errorCode=" + errorCode + ", errorMessage="
            + errorMessage + ", errorDetails=" + errorDetails
            + ", canBeIgnored=" + canBeIgnored + "}";
    }
}
