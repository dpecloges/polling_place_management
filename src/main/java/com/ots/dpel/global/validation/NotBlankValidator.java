package com.ots.dpel.global.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence> {
    
    @Override
    public void initialize(NotBlank constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value instanceof String) {
            return ((String) value).trim().length() > 0;
        }
        return value.toString().trim().length() > 0;
    }
    
}
