package com.ots.dpel.global.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class ErrorReport {
    private final List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
    private final List<ErrorMessage> warnings = new ArrayList<ErrorMessage>();
    private int errorCount = 0;
    private int warningCount = 0;
    private String description = "";
    
    public ErrorReport() {
    }
    
    public ErrorReport(String description) {
        this.description = description;
    }
    
    public ErrorReport(ErrorMessage errorMessage) {
        this.addError(errorMessage);
    }
    
    public final void addError(String errorCode, String errorMessage, Serializable details) {
        this.addError(new ErrorMessage(errorCode, errorMessage, details));
    }
    
    public final void addError(String errorMessage) {
        this.addError(new ErrorMessage(null, errorMessage, null));
    }
    
    public final void addError(ErrorMessage error) {
        errors.add(error);
        errorCount++;
    }
    
    public final void addWarning(String errorCode, String errorMessage, Serializable details) {
        this.addWarning(new ErrorMessage(errorCode, errorMessage, details));
    }
    
    public final void addWarning(ErrorMessage error) {
        warnings.add(error);
        warningCount++;
    }
    
    public final void resetErrors() {
        errors.clear();
        errorCount = 0;
    }
    
    public final void resetWarnings() {
        warnings.clear();
        warningCount = 0;
    }
    
    public final void resetAll() {
        resetErrors();
        resetWarnings();
    }
    
    public final List<ErrorMessage> getErrors() {
        return errors;
    }
    
    public final List<ErrorMessage> getWarnings() {
        return warnings;
    }
    
    public final int getErrorCount() {
        return errorCount;
    }
    
    /**
     * Returns true if {@link #getErrorCount() error count} is greater than 0.
     */
    public final boolean hasErrors() {
        return this.getErrorCount() > 0;
    }
    
    public final String getDescription() {
        return description;
    }
    
    public int getWarningCount() {
        return warningCount;
    }
    
    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.errors);
        hash = 89 * hash + Objects.hashCode(this.warnings);
        hash = 89 * hash + Objects.hashCode(this.description);
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
        final ErrorReport other = (ErrorReport) obj;
        if (!Objects.equals(this.errors, other.errors)) {
            return false;
        }
        if (!Objects.equals(this.warnings, other.warnings)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "ErrorReport{" + "errors=" + errors + ", errorCount="
            + errorCount + "warnings=" + warnings + ", warningCount="
            + warningCount + ", description=" + description + '}';
    }
}
