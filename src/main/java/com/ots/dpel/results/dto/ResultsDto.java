package com.ots.dpel.results.dto;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

import java.io.Serializable;
import java.util.Date;

public class ResultsDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String code;
    
    private String name;
    
    private Integer totalVotes;
    
    private Integer whiteVotes;
    
    private Integer invalidVotes;
    
    private Integer validVotes;
    
    private Integer candidateOneVotes;
    
    private Integer candidateTwoVotes;
    
    private Integer candidateThreeVotes;
    
    private Integer candidateFourVotes;
    
    private Integer candidateFiveVotes;
    
    private Integer candidateSixVotes;
    
    private Integer candidateSevenVotes;
    
    private Integer candidateEightVotes;
    
    private Integer candidateNineVotes;
    
    private Integer candidateTenVotes;
    
    private Boolean submitted;
    
    private String attachmentName;
    
    private String attachmentTwoName;
    
    private Integer submittedDepartmentCount;
    
    private Integer totalDepartmentCount;
    
    private Date calculationDateTime;
    
    private Date nextCalculationDateTime;
    
    public ResultsDto() {
    }
    
    @QueryProjection
    public ResultsDto(Long id, String code, String name, Integer totalVotes, Integer whiteVotes, Integer invalidVotes, Integer validVotes,
                      Integer candidateOneVotes, Integer candidateTwoVotes, Integer candidateThreeVotes,
                      Integer candidateFourVotes, Integer candidateFiveVotes, Integer candidateSixVotes,
                      Integer candidateSevenVotes, Integer candidateEightVotes, Integer candidateNineVotes,
                      Integer candidateTenVotes, YesNoEnum submitted, String attachmentName, String attachmentTwoName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.totalVotes = totalVotes;
        this.whiteVotes = whiteVotes;
        this.invalidVotes = invalidVotes;
        this.validVotes = validVotes;
        this.candidateOneVotes = candidateOneVotes;
        this.candidateTwoVotes = candidateTwoVotes;
        this.candidateThreeVotes = candidateThreeVotes;
        this.candidateFourVotes = candidateFourVotes;
        this.candidateFiveVotes = candidateFiveVotes;
        this.candidateSixVotes = candidateSixVotes;
        this.candidateSevenVotes = candidateSevenVotes;
        this.candidateEightVotes = candidateEightVotes;
        this.candidateNineVotes = candidateNineVotes;
        this.candidateTenVotes = candidateTenVotes;
        this.submitted = submitted == YesNoEnum.YES;
        this.attachmentName = attachmentName;
        this.attachmentTwoName = attachmentTwoName;
    }
    
    @QueryProjection
    public ResultsDto(Long id, Integer totalVotes, Integer whiteVotes, Integer invalidVotes, Integer validVotes,
                     Integer candidateOneVotes, Integer candidateTwoVotes, Integer candidateThreeVotes, Integer candidateFourVotes,
                     Integer candidateFiveVotes, Integer candidateSixVotes, Integer candidateSevenVotes, Integer candidateEightVotes,
                     Integer candidateNineVotes, Integer candidateTenVotes, Integer submittedDepartmentCount, Integer totalDepartmentCount,
                     Date calculationDateTime, Date nextCalculationDateTime) {
        this.id = id;
        this.totalVotes = totalVotes;
        this.whiteVotes = whiteVotes;
        this.invalidVotes = invalidVotes;
        this.validVotes = validVotes;
        this.candidateOneVotes = candidateOneVotes;
        this.candidateTwoVotes = candidateTwoVotes;
        this.candidateThreeVotes = candidateThreeVotes;
        this.candidateFourVotes = candidateFourVotes;
        this.candidateFiveVotes = candidateFiveVotes;
        this.candidateSixVotes = candidateSixVotes;
        this.candidateSevenVotes = candidateSevenVotes;
        this.candidateEightVotes = candidateEightVotes;
        this.candidateNineVotes = candidateNineVotes;
        this.candidateTenVotes = candidateTenVotes;
        this.submittedDepartmentCount = submittedDepartmentCount;
        this.totalDepartmentCount = totalDepartmentCount;
        this.calculationDateTime = calculationDateTime;
        this.nextCalculationDateTime = nextCalculationDateTime;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public Integer getTotalVotes() {
        return totalVotes;
    }
    
    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }
    
    public Integer getWhiteVotes() {
        return whiteVotes;
    }
    
    public void setWhiteVotes(Integer whiteVotes) {
        this.whiteVotes = whiteVotes;
    }
    
    public Integer getInvalidVotes() {
        return invalidVotes;
    }
    
    public void setInvalidVotes(Integer invalidVotes) {
        this.invalidVotes = invalidVotes;
    }
    
    public Integer getValidVotes() {
        return validVotes;
    }
    
    public void setValidVotes(Integer validVotes) {
        this.validVotes = validVotes;
    }
    
    public Integer getCandidateOneVotes() {
        return candidateOneVotes;
    }
    
    public void setCandidateOneVotes(Integer candidateOneVotes) {
        this.candidateOneVotes = candidateOneVotes;
    }
    
    public Integer getCandidateTwoVotes() {
        return candidateTwoVotes;
    }
    
    public void setCandidateTwoVotes(Integer candidateTwoVotes) {
        this.candidateTwoVotes = candidateTwoVotes;
    }
    
    public Integer getCandidateThreeVotes() {
        return candidateThreeVotes;
    }
    
    public void setCandidateThreeVotes(Integer candidateThreeVotes) {
        this.candidateThreeVotes = candidateThreeVotes;
    }
    
    public Integer getCandidateFourVotes() {
        return candidateFourVotes;
    }
    
    public void setCandidateFourVotes(Integer candidateFourVotes) {
        this.candidateFourVotes = candidateFourVotes;
    }
    
    public Integer getCandidateFiveVotes() {
        return candidateFiveVotes;
    }
    
    public void setCandidateFiveVotes(Integer candidateFiveVotes) {
        this.candidateFiveVotes = candidateFiveVotes;
    }
    
    public Integer getCandidateSixVotes() {
        return candidateSixVotes;
    }
    
    public void setCandidateSixVotes(Integer candidateSixVotes) {
        this.candidateSixVotes = candidateSixVotes;
    }
    
    public Integer getCandidateSevenVotes() {
        return candidateSevenVotes;
    }
    
    public void setCandidateSevenVotes(Integer candidateSevenVotes) {
        this.candidateSevenVotes = candidateSevenVotes;
    }
    
    public Integer getCandidateEightVotes() {
        return candidateEightVotes;
    }
    
    public void setCandidateEightVotes(Integer candidateEightVotes) {
        this.candidateEightVotes = candidateEightVotes;
    }
    
    public Integer getCandidateNineVotes() {
        return candidateNineVotes;
    }
    
    public void setCandidateNineVotes(Integer candidateNineVotes) {
        this.candidateNineVotes = candidateNineVotes;
    }
    
    public Integer getCandidateTenVotes() {
        return candidateTenVotes;
    }
    
    public void setCandidateTenVotes(Integer candidateTenVotes) {
        this.candidateTenVotes = candidateTenVotes;
    }
    
    public Boolean getSubmitted() {
        return submitted;
    }
    
    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }
    
    public String getAttachmentName() {
        return attachmentName;
    }
    
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
    
    public Integer getSubmittedDepartmentCount() {
        return submittedDepartmentCount;
    }
    
    public void setSubmittedDepartmentCount(Integer submittedDepartmentCount) {
        this.submittedDepartmentCount = submittedDepartmentCount;
    }
    
    public Integer getTotalDepartmentCount() {
        return totalDepartmentCount;
    }
    
    public void setTotalDepartmentCount(Integer totalDepartmentCount) {
        this.totalDepartmentCount = totalDepartmentCount;
    }
    
    public Date getCalculationDateTime() {
        return calculationDateTime;
    }
    
    public void setCalculationDateTime(Date calculationDateTime) {
        this.calculationDateTime = calculationDateTime;
    }
    
    public Date getNextCalculationDateTime() {
        return nextCalculationDateTime;
    }
    
    public void setNextCalculationDateTime(Date nextCalculationDateTime) {
        this.nextCalculationDateTime = nextCalculationDateTime;
    }
    
    public String getAttachmentTwoName() {
        return attachmentTwoName;
    }
    
    public void setAttachmentTwoName(String attachmentTwoName) {
        this.attachmentTwoName = attachmentTwoName;
    }
}
