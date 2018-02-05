package com.ots.dpel.global.dto;

/**
 * Γενικό DTO για enums
 * @author sdimitriadis
 */
public class EnumDTO {

    /*
     * Τιμή
     */
    private String value;

    /*
     * Λεκτικό
     */
    private String label;

    /*
     * Αριθμός
     */
    private Integer ordinal;


    public EnumDTO() {

    }

    public EnumDTO(String value, String label, Integer ordinal) {
        this.value = value;
        this.label = label;
        this.ordinal = ordinal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }
}
