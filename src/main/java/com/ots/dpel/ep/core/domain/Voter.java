package com.ots.dpel.ep.core.domain;

import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.core.enums.IdType;
import com.ots.dpel.management.core.domain.ElectionDepartment;
import com.ots.dpel.management.core.enums.ElectionRound;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "voter", schema = "dp")
public class Voter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "n_elector_id")
    private Long electorId;
    
    @Column(name = "n_electionprocedure_id")
    private Long electionProcedureId;
    
    @Column(name = "v_round")
    @Enumerated(EnumType.STRING)
    private ElectionRound round;
    
    @Column(name = "n_electiondepartment_id")
    private Long electionDepartmentId;
    
    @ManyToOne
    @JoinColumn(name = "n_electiondepartment_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private ElectionDepartment electionDepartment;
    
    @Column(name = "n_verificationnumber")
    private Integer verificationNumber;
    
    @Column(name = "v_eklspecialno")
    private String eklSpecialNo;
    
    @Column(name = "v_lastname")
    private String lastName;
    
    @Column(name = "v_firstname")
    private String firstName;
    
    @Column(name = "v_fatherfirstname")
    private String fatherFirstName;
    
    @Column(name = "v_motherfirstname")
    private String motherFirstName;
    
    @Column(name = "dt_birthdate")
    private Date birthDate;
    
    @Column(name = "n_birthyear")
    private Integer birthYear;
    
    @Column(name = "v_address")
    private String address;
    
    @Column(name = "v_addressno")
    private String addressNo;
    
    @Column(name = "v_city")
    private String city;
    
    @Column(name = "v_postalcode")
    private String postalCode;
    
    @Column(name = "v_country")
    private String country;
    
    @Column(name = "v_cellphone")
    private String cellphone;
    
    @Column(name = "v_email")
    private String email;
    
    @Column(name = "v_idtype")
    @Enumerated(EnumType.STRING)
    private IdType idType;
    
    @Column(name = "v_idnumber")
    private String idNumber;
    
    @Column(name = "n_voted")
    private YesNoEnum voted;
    
    @Column(name = "dt_votedatetime")
    private Date voteDateTime;
    
    @Column(name = "n_member")
    private YesNoEnum member;
    
    @Column(name = "n_payment")
    private Double payment;
    
    @Column(name = "v_undoreason")
    private String undoReason;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getElectorId() {
        return electorId;
    }
    
    public void setElectorId(Long electorId) {
        this.electorId = electorId;
    }
    
    public Long getElectionProcedureId() {
        return electionProcedureId;
    }
    
    public void setElectionProcedureId(Long electionProcedureId) {
        this.electionProcedureId = electionProcedureId;
    }
    
    public ElectionRound getRound() {
        return round;
    }
    
    public void setRound(ElectionRound round) {
        this.round = round;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
    }
    
    public ElectionDepartment getElectionDepartment() {
        return electionDepartment;
    }
    
    public void setElectionDepartment(ElectionDepartment electionDepartment) {
        this.electionDepartment = electionDepartment;
    }
    
    public Integer getVerificationNumber() {
        return verificationNumber;
    }
    
    public void setVerificationNumber(Integer verificationNumber) {
        this.verificationNumber = verificationNumber;
    }
    
    public String getEklSpecialNo() {
        return eklSpecialNo;
    }
    
    public void setEklSpecialNo(String eklSpecialNo) {
        this.eklSpecialNo = eklSpecialNo;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFatherFirstName() {
        return fatherFirstName;
    }
    
    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }
    
    public String getMotherFirstName() {
        return motherFirstName;
    }
    
    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public Integer getBirthYear() {
        return birthYear;
    }
    
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddressNo() {
        return addressNo;
    }
    
    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getCellphone() {
        return cellphone;
    }
    
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public IdType getIdType() {
        return idType;
    }
    
    public void setIdType(IdType idType) {
        this.idType = idType;
    }
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public YesNoEnum getVoted() {
        return voted;
    }
    
    public void setVoted(YesNoEnum voted) {
        this.voted = voted;
    }
    
    public Date getVoteDateTime() {
        return voteDateTime;
    }
    
    public void setVoteDateTime(Date voteDateTime) {
        this.voteDateTime = voteDateTime;
    }
    
    public YesNoEnum getMember() {
        return member;
    }
    
    public void setMember(YesNoEnum member) {
        this.member = member;
    }
    
    public Double getPayment() {
        return payment;
    }
    
    public void setPayment(Double payment) {
        this.payment = payment;
    }
    
    public String getUndoReason() {
        return undoReason;
    }
    
    public void setUndoReason(String undoReason) {
        this.undoReason = undoReason;
    }
}
