package com.ots.dpel.ep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;
import com.ots.dpel.ep.core.enums.IdType;
import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.management.dto.ElectionDepartmentBasicDto;

import java.util.Date;

public class VoterDto {
    
    private Long id;
    
    private Long electorId;
    
    private Long electionProcedureId;
    
    private String round;
    
    private Long electionDepartmentId;
    
    private Integer verificationNumber;
    
    private String eklSpecialNo;
    
    private String lastName;
    
    private String firstName;
    
    private String fatherFirstName;
    
    private String motherFirstName;
    
    private Date birthDate;
    
    private Integer birthYear;
    
    private String address;
    
    private String addressNo;
    
    private String city;
    
    private String postalCode;
    
    private String country;
    
    private String cellphone;
    
    private String email;
    
    private String idType;
    
    private String idNumber;
    
    private Boolean voted;
    
    private Date voteDateTime;
    
    private Boolean member;
    
    private Double payment;
    
    @JsonProperty("electionDepartment")
    private ElectionDepartmentBasicDto electionDepartmentBasicDto;
    
    private String electionDepartmentName;
    
    public VoterDto() {
    }
    
    @QueryProjection
    public VoterDto(Long id, Long electorId, Long electionProcedureId, ElectionRound round, Long electionDepartmentId, Integer verificationNumber,
                    String eklSpecialNo, String lastName, String firstName, String fatherFirstName, String motherFirstName, Date birthDate,
                    Integer birthYear, String address, String addressNo, String city, String postalCode, String country, String cellphone,
                    String email, IdType idType, String idNumber, YesNoEnum voted, Date voteDateTime, YesNoEnum member, Double payment,
                    String electionDepartmentName) {
        this.id = id;
        this.electorId = electorId;
        this.electionProcedureId = electionProcedureId;
        this.round = round != null ? round.name() : null;
        this.electionDepartmentId = electionDepartmentId;
        this.verificationNumber = verificationNumber;
        this.eklSpecialNo = eklSpecialNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.fatherFirstName = fatherFirstName;
        this.motherFirstName = motherFirstName;
        this.birthDate = birthDate;
        this.birthYear = birthYear;
        this.address = address;
        this.addressNo = addressNo;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.cellphone = cellphone;
        this.email = email;
        this.idType = idType != null ? idType.name() : null;
        this.idNumber = idNumber;
        this.voted = voted != null && voted.equals(YesNoEnum.YES);
        this.voteDateTime = voteDateTime;
        this.member = member != null && member.equals(YesNoEnum.YES);
        this.payment = payment;
        this.electionDepartmentName = electionDepartmentName;
    }
    
    @QueryProjection
    public VoterDto(Long id, Date voteDateTime, ElectionDepartmentBasicDto electionDepartmentBasicDto,
                    String address, String addressNo, String city, String postalCode, String country, String cellphone, String email,
                    IdType idType, String idNumber, YesNoEnum member, Double payment, Long electionDepartmentId, Integer verificationNumber) {
        this.id = id;
        this.voteDateTime = voteDateTime;
        this.electionDepartmentBasicDto = electionDepartmentBasicDto;
        this.address = address;
        this.addressNo = addressNo;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.cellphone = cellphone;
        this.email = email;
        this.idType = idType != null ? idType.name() : null;
        this.idNumber = idNumber;
        this.member = member != null && member.equals(YesNoEnum.YES);
        this.payment = payment;
        this.electionDepartmentId = electionDepartmentId;
        this.verificationNumber = verificationNumber;
    }
    
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
    
    public String getRound() {
        return round;
    }
    
    public void setRound(String round) {
        this.round = round;
    }
    
    public Long getElectionDepartmentId() {
        return electionDepartmentId;
    }
    
    public void setElectionDepartmentId(Long electionDepartmentId) {
        this.electionDepartmentId = electionDepartmentId;
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
    
    public String getIdType() {
        return idType;
    }
    
    public void setIdType(String idType) {
        this.idType = idType;
    }
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public Boolean getVoted() {
        return voted;
    }
    
    public void setVoted(Boolean voted) {
        this.voted = voted;
    }
    
    public Date getVoteDateTime() {
        return voteDateTime;
    }
    
    public void setVoteDateTime(Date voteDateTime) {
        this.voteDateTime = voteDateTime;
    }
    
    public Boolean getMember() {
        return member;
    }
    
    public void setMember(Boolean member) {
        this.member = member;
    }
    
    public Double getPayment() {
        return payment;
    }
    
    public void setPayment(Double payment) {
        this.payment = payment;
    }
    
    public ElectionDepartmentBasicDto getElectionDepartmentBasicDto() {
        return electionDepartmentBasicDto;
    }
    
    public void setElectionDepartmentBasicDto(ElectionDepartmentBasicDto electionDepartmentBasicDto) {
        this.electionDepartmentBasicDto = electionDepartmentBasicDto;
    }
    
    public String getElectionDepartmentName() {
        return electionDepartmentName;
    }
    
    public void setElectionDepartmentName(String electionDepartmentName) {
        this.electionDepartmentName = electionDepartmentName;
    }
}
