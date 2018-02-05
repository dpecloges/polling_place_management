package com.ots.dpel.management.core.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ots.dpel.common.core.domain.AdminUnit;
import com.ots.dpel.common.core.domain.Country;
import com.ots.dpel.common.core.enums.YesNoEnum;

@Entity
@Table(name = "electioncenter", schema = "dp")
public class ElectionCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "n_electionprocedure_id")
    private Long electionProcedureId;
    
    @Column(name = "v_code")
    private String code;
    
    @Column(name = "v_name")
    private String name;
    
    @Column(name = "v_address")
    private String address;
    
    @Column(name = "v_postalcode")
    private String postalCode;
    
    @Column(name = "v_telephone")
    private String telephone;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "n_supervisorfirst_id", referencedColumnName = "N_ID")
    private Contributor supervisorFirst;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "n_supervisorsecond_id", referencedColumnName = "N_ID")
    private Contributor supervisorSecond;
    
    @Column(name = "v_comments")
    private String comments;
    
    @Column(name = "n_geographicalunit_id")
    private Long geographicalUnitId;
    
    @ManyToOne
    @JoinColumn(name = "n_geographicalunit_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private AdminUnit geographicalUnit;
    
    @Column(name = "n_decentraladmin_id")
    private Long decentralAdminId;
    
    @ManyToOne
    @JoinColumn(name = "n_decentraladmin_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private AdminUnit decentralAdmin;
    
    @Column(name = "n_region_id")
    private Long regionId;
    
    @ManyToOne
    @JoinColumn(name = "n_region_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private AdminUnit region;
    
    @Column(name = "n_regionalunit_id")
    private Long regionalUnitId;
    
    @ManyToOne
    @JoinColumn(name = "n_regionalunit_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private AdminUnit regionalUnit;
    
    @Column(name = "n_municipality_id")
    private Long municipalityId;
    
    @ManyToOne
    @JoinColumn(name = "n_municipality_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private AdminUnit municipality;
    
    @Column(name = "n_municipalunit_id")
    private Long municipalUnitId;
    
    @ManyToOne
    @JoinColumn(name = "n_municipalunit_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private AdminUnit municipalUnit;
    
    @Column(name = "n_municipalcommunity_id")
    private Long municipalCommunityId;
    
    @Column(name = "n_floornumber")
    private Integer floorNumber;
    
    @Column(name = "n_disabledaccess")
    private YesNoEnum disabledAccess;
    
    @Column(name = "n_foreign")
    private YesNoEnum foreign;
    
    @ManyToOne
    @JoinColumn(name = "v_foreigncountry_isocode", referencedColumnName = "v_isocode")
    private Country foreignCountry;
    
    @Column(name = "v_foreigncountry_isocode", updatable = false, insertable = false)
    private String foreignCountryIsoCode;
    
    @Column(name = "v_foreigncity")
    private String foreignCity;
    
    /**
     * Αριθμός κάλπεων
     */
    @Column(name = "n_ballotboxes")
    private Integer ballotBoxes;
    
    /**
     * Αριθμός εκτιμώμενων κάλπεων
     */
    @Column(name = "n_estimated_ballotboxes")
    private Integer estimatedBallotBoxes;
    
    /**
     * Αριθμός ψηφισάντων 2007
     */
    @Column(name = "n_voters2007")
    private Integer voters2007;
    
    /**
     * Θέτει {@code null} τιμές στα γεωγραφικά δεδομένα ανάλογα
     * με την τιμή του {@code foreign}, έτσι ώστε να μην αποθηκεύονται
     * τοπικά δεδομένα σε κέντρα που αφορούν ξένες χώρες και το αντίστροφο.
     */
    public void removeRedundantGeographicalData() {
        if (foreign.booleanValue()) {
            this.geographicalUnitId = null;
            this.decentralAdminId = null;
            this.regionId = null;
            this.regionalUnitId = null;
            this.municipalityId = null;
            this.municipalUnitId = null;
            this.municipalCommunityId = null;
        } else {
            this.foreignCountry = null;
            this.foreignCity = null;
        }
    }
    
    public boolean hasGeographicalData() {
        return foreign.booleanValue()? hasForeignGeographicalData(): hasLocalGeographicalData();
    }
    
    private boolean hasForeignGeographicalData() {
        return this.foreignCountry != null;
    }

    private boolean hasLocalGeographicalData() {
        return
            this.geographicalUnitId != null &&
            this.decentralAdminId != null &&
            this.regionId != null &&
            this.regionalUnitId != null &&
            this.municipalityId != null;
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Contributor getSupervisorFirst() {
        return supervisorFirst;
    }
    
    public void setSupervisorFirst(Contributor supervisorFirst) {
        this.supervisorFirst = supervisorFirst;
    }
    
    public Contributor getSupervisorSecond() {
        return supervisorSecond;
    }
    
    public void setSupervisorSecond(Contributor supervisorSecond) {
        this.supervisorSecond = supervisorSecond;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Long getGeographicalUnitId() {
        return geographicalUnitId;
    }
    
    public void setGeographicalUnitId(Long geographicalUnitId) {
        this.geographicalUnitId = geographicalUnitId;
    }
    
    public AdminUnit getGeographicalUnit() {
        return geographicalUnit;
    }
    
    public void setGeographicalUnit(AdminUnit geographicalUnit) {
        this.geographicalUnit = geographicalUnit;
    }
    
    public Long getDecentralAdminId() {
        return decentralAdminId;
    }
    
    public void setDecentralAdminId(Long decentralAdminId) {
        this.decentralAdminId = decentralAdminId;
    }
    
    public AdminUnit getDecentralAdmin() {
        return decentralAdmin;
    }
    
    public void setDecentralAdmin(AdminUnit decentralAdmin) {
        this.decentralAdmin = decentralAdmin;
    }
    
    public Long getRegionId() {
        return regionId;
    }
    
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public AdminUnit getRegion() {
        return region;
    }
    
    public void setRegion(AdminUnit region) {
        this.region = region;
    }
    
    public Long getRegionalUnitId() {
        return regionalUnitId;
    }
    
    public void setRegionalUnitId(Long regionalUnitId) {
        this.regionalUnitId = regionalUnitId;
    }
    
    public AdminUnit getRegionalUnit() {
        return regionalUnit;
    }
    
    public void setRegionalUnit(AdminUnit regionalUnit) {
        this.regionalUnit = regionalUnit;
    }
    
    public Long getMunicipalityId() {
        return municipalityId;
    }
    
    public void setMunicipalityId(Long municipalityId) {
        this.municipalityId = municipalityId;
    }
    
    public AdminUnit getMunicipality() {
        return municipality;
    }
    
    public void setMunicipality(AdminUnit municipality) {
        this.municipality = municipality;
    }
    
    public Long getMunicipalUnitId() {
        return municipalUnitId;
    }
    
    public void setMunicipalUnitId(Long municipalUnitId) {
        this.municipalUnitId = municipalUnitId;
    }
    
    public AdminUnit getMunicipalUnit() {
        return municipalUnit;
    }
    
    public void setMunicipalUnit(AdminUnit municipalUnit) {
        this.municipalUnit = municipalUnit;
    }
    
    public Long getMunicipalCommunityId() {
        return municipalCommunityId;
    }
    
    public void setMunicipalCommunityId(Long municipalCommunityId) {
        this.municipalCommunityId = municipalCommunityId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public YesNoEnum getDisabledAccess() {
        return disabledAccess;
    }

    public void setDisabledAccess(YesNoEnum disabledAccess) {
        this.disabledAccess = disabledAccess;
    }

    public YesNoEnum getForeign() {
        return foreign;
    }

    public void setForeign(YesNoEnum foreign) {
        this.foreign = foreign;
    }

    public Country getForeignCountry() {
        return foreignCountry;
    }

    public void setForeignCountry(Country foreignCountry) {
        this.foreignCountry = foreignCountry;
    }
    
    public String getForeignCountryIsoCode() {
        return foreignCountryIsoCode;
    }
    
    public void setForeignCountryIsoCode(String foreignCountryIsoCode) {
        this.foreignCountryIsoCode = foreignCountryIsoCode;
    }
    
    public String getForeignCity() {
        return foreignCity;
    }

    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
    
    public Integer getBallotBoxes() {
        return ballotBoxes;
    }
    
    public void setBallotBoxes(Integer ballotBoxes) {
        this.ballotBoxes = ballotBoxes;
    }
    
    public Integer getEstimatedBallotBoxes() {
        return estimatedBallotBoxes;
    }
    
    public void setEstimatedBallotBoxes(Integer estimatedBallotBoxes) {
        this.estimatedBallotBoxes = estimatedBallotBoxes;
    }
    
    public Integer getVoters2007() {
        return voters2007;
    }
    
    public void setVoters2007(Integer voters2007) {
        this.voters2007 = voters2007;
    }
}
