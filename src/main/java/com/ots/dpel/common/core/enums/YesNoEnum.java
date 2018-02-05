package com.ots.dpel.common.core.enums;

/**
 * @author glioliou
 */

public enum YesNoEnum {
    NO {
        @Override
        public String toString() {
            return "ΟΧΙ";
        }
    },
    YES {
        @Override
        public String toString() {
            return "ΝΑΙ";
        }
    };
    
    /**
     * Evaluates {@code yesNo} as a boolean; {@code null}
     * arguments are evaluated to {@code false}.
     * 
     * @param yesNo
     *     instance of {@code YesNoEnum}
     * @return
     *     {@code true} if {@code yesNo} is {@code YES}, {@code false} otherwise
     */
    public static boolean booleanValue(YesNoEnum yesNo) {
        return YES.equals(yesNo);
    }
    
    /**
     * Evaluates this instance as a boolean.
     * 
     * @return
     *     {@code true} if {@code yesNo} is {@code YES}, {@code false} otherwise
     */
    public boolean booleanValue() {
        return YesNoEnum.booleanValue(this);
    }
    
    /**
     * Returns the {@code YesNoEnum} constant for the specified boolean value. 
     * 
     * @param booleanValue
     *     primitive boolean value
     * @return
     *     non-{@code null} instance of {@code YesNoEnum}
     */
    public static YesNoEnum of(boolean booleanValue) {
        return booleanValue? YES: NO;
    }
    
    /**
     * Returns the {@code YesNoEnum} constant for the specified boolean value. 
     * 
     * {@code null}s references are evaluated to {@link YesNoEnum#NO}.
     * 
     * @param booleanObject
     *     instance of {@link Boolean}
     * @return
     *     non-{@code null} instance of {@code YesNoEnum}
     */
    public static YesNoEnum of(Boolean booleanObject) {
        return booleanObject != null? of(booleanObject.booleanValue()): NO;
    }
}
