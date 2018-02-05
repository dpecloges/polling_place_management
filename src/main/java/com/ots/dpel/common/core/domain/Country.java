package com.ots.dpel.common.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country", schema = "dp")
public class Country {
    
    /**
     * Κωδικός σύμφωνα με το πρότυπο ISO 3166-1 alpha-3
     */
    @Id
    @Column(name = "v_isocode")
    private String isoCode;
    
    /**
     * Ονομασία
     */
    @Column(name = "v_name")
    private String name;
    
    /**
     * Διεθνής ονομασία
     */
    @Column(name = "v_namelatin")
    private String nameLatin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getNameLatin() {
        return nameLatin;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
    }
}
