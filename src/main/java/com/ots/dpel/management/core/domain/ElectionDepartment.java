package com.ots.dpel.management.core.domain;

import com.ots.dpel.common.core.enums.YesNoEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "electiondepartment", schema = "dp")
public class ElectionDepartment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "n_electioncenter_id")
    private Long electionCenterId;
    
    @ManyToOne
    @JoinColumn(name = "n_electioncenter_id", referencedColumnName = "n_id", updatable = false, insertable = false)
    private ElectionCenter electionCenter;
    
    @Column(name = "n_serialno")
    private Integer serialNo;
    
    @Column(name = "v_code")
    private String code;
    
    @Column(name = "v_name")
    private String name;
    
    @Column(name = "v_comments")
    private String comments;
    
    @Column(name = "n_totalvotesfirst")
    private Integer totalVotesFirst;
    
    @Column(name = "n_whitevotesfirst")
    private Integer whiteVotesFirst;
    
    @Column(name = "n_invalidvotesfirst")
    private Integer invalidVotesFirst;
    
    @Column(name = "n_validvotesfirst")
    private Integer validVotesFirst;
    
    @Column(name = "n_candidateonevotesfirst")
    private Integer candidateOneVotesFirst;
    
    @Column(name = "n_candidatetwovotesfirst")
    private Integer candidateTwoVotesFirst;
    
    @Column(name = "n_candidatethreevotesfirst")
    private Integer candidateThreeVotesFirst;
    
    @Column(name = "n_candidatefourvotesfirst")
    private Integer candidateFourVotesFirst;
    
    @Column(name = "n_candidatefivevotesfirst")
    private Integer candidateFiveVotesFirst;
    
    @Column(name = "n_candidatesixvotesfirst")
    private Integer candidateSixVotesFirst;
    
    @Column(name = "n_candidatesevenvotesfirst")
    private Integer candidateSevenVotesFirst;
    
    @Column(name = "n_candidateeightvotesfirst")
    private Integer candidateEightVotesFirst;
    
    @Column(name = "n_candidateninevotesfirst")
    private Integer candidateNineVotesFirst;
    
    @Column(name = "n_candidatetenvotesfirst")
    private Integer candidateTenVotesFirst;
    
    @Column(name = "n_totalvotessecond")
    private Integer totalVotesSecond;
    
    @Column(name = "n_whitevotessecond")
    private Integer whiteVotesSecond;
    
    @Column(name = "n_invalidvotessecond")
    private Integer invalidVotesSecond;
    
    @Column(name = "n_validvotessecond")
    private Integer validVotesSecond;
    
    @Column(name = "n_candidateonevotessecond")
    private Integer candidateOneVotesSecond;
    
    @Column(name = "n_candidatetwovotessecond")
    private Integer candidateTwoVotesSecond;
    
    @Column(name = "n_candidatethreevotessecond")
    private Integer candidateThreeVotesSecond;
    
    @Column(name = "n_candidatefourvotessecond")
    private Integer candidateFourVotesSecond;
    
    @Column(name = "n_candidatefivevotessecond")
    private Integer candidateFiveVotesSecond;
    
    @Column(name = "n_candidatesixvotessecond")
    private Integer candidateSixVotesSecond;
    
    @Column(name = "n_candidatesevenvotessecond")
    private Integer candidateSevenVotesSecond;
    
    @Column(name = "n_candidateeightvotessecond")
    private Integer candidateEightVotesSecond;
    
    @Column(name = "n_candidateninevotessecond")
    private Integer candidateNineVotesSecond;
    
    @Column(name = "n_candidatetenvotessecond")
    private Integer candidateTenVotesSecond;
    
    @Column(name = "n_submittedfirst")
    private YesNoEnum submittedFirst;
    
    @Column(name = "dt_submissiondatefirst")
    private Date submissionDateFirst;
    
    @Column(name = "n_submittedsecond")
    private YesNoEnum submittedSecond;
    
    @Column(name = "dt_submissiondatesecond")
    private Date submissionDateSecond;
    
    @Column(name = "n_accessdifficulty")
    private YesNoEnum accessDifficulty;
    
    @Column(name = "v_attachmenttwonamefirst")
    private String attachmentTwoNameFirst;
    
    @Column(name = "v_attachmenttwonamesecond")
    private String attachmentTwoNameSecond;
    
    @Lob
    @Column(name = "v_attachmenttwofirst")
    private byte [] attachmentTwoFirst;
    
    @Lob
    @Column(name = "v_attachmenttwosecond")
    private byte [] attachmentTwoSecond;
    
    @Column(name = "v_attachmentnamefirst")
    private String attachmentNameFirst;
    
    @Column(name = "v_attachmentnamesecond")
    private String attachmentNameSecond;
    
    @Lob
    @Column(name = "v_attachmentfirst")
    private byte [] attachmentFirst;
    
    @Lob
    @Column(name = "v_attachmentsecond")
    private byte [] attachmentSecond;
    
    @Column(name = "n_verificationserialfirst")
    private Integer verificationSerialFirst = 0;
    
    @Column(name = "n_verificationserialsecond")
    private Integer verificationSerialSecond = 0;
    
    @Column(name = "n_allowinconsistentsubmission")
    private YesNoEnum allowInconsistentSubmission;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "electionDepartment", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Contribution> contributions = new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }
    
    public ElectionCenter getElectionCenter() {
        return electionCenter;
    }
    
    public void setElectionCenter(ElectionCenter electionCenter) {
        this.electionCenter = electionCenter;
    }
    
    public Integer getSerialNo() {
        return serialNo;
    }
    
    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
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
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Integer getTotalVotesFirst() {
        return totalVotesFirst;
    }
    
    public void setTotalVotesFirst(Integer totalVotesFirst) {
        this.totalVotesFirst = totalVotesFirst;
    }
    
    public Integer getWhiteVotesFirst() {
        return whiteVotesFirst;
    }
    
    public void setWhiteVotesFirst(Integer whiteVotesFirst) {
        this.whiteVotesFirst = whiteVotesFirst;
    }
    
    public Integer getInvalidVotesFirst() {
        return invalidVotesFirst;
    }
    
    public void setInvalidVotesFirst(Integer invalidVotesFirst) {
        this.invalidVotesFirst = invalidVotesFirst;
    }
    
    public Integer getValidVotesFirst() {
        return validVotesFirst;
    }
    
    public void setValidVotesFirst(Integer validVotesFirst) {
        this.validVotesFirst = validVotesFirst;
    }
    
    public Integer getCandidateOneVotesFirst() {
        return candidateOneVotesFirst;
    }
    
    public void setCandidateOneVotesFirst(Integer candidateOneVotesFirst) {
        this.candidateOneVotesFirst = candidateOneVotesFirst;
    }
    
    public Integer getCandidateTwoVotesFirst() {
        return candidateTwoVotesFirst;
    }
    
    public void setCandidateTwoVotesFirst(Integer candidateTwoVotesFirst) {
        this.candidateTwoVotesFirst = candidateTwoVotesFirst;
    }
    
    public Integer getCandidateThreeVotesFirst() {
        return candidateThreeVotesFirst;
    }
    
    public void setCandidateThreeVotesFirst(Integer candidateThreeVotesFirst) {
        this.candidateThreeVotesFirst = candidateThreeVotesFirst;
    }
    
    public Integer getCandidateFourVotesFirst() {
        return candidateFourVotesFirst;
    }
    
    public void setCandidateFourVotesFirst(Integer candidateFourVotesFirst) {
        this.candidateFourVotesFirst = candidateFourVotesFirst;
    }
    
    public Integer getCandidateFiveVotesFirst() {
        return candidateFiveVotesFirst;
    }
    
    public void setCandidateFiveVotesFirst(Integer candidateFiveVotesFirst) {
        this.candidateFiveVotesFirst = candidateFiveVotesFirst;
    }
    
    public Integer getCandidateSixVotesFirst() {
        return candidateSixVotesFirst;
    }
    
    public void setCandidateSixVotesFirst(Integer candidateSixVotesFirst) {
        this.candidateSixVotesFirst = candidateSixVotesFirst;
    }
    
    public Integer getCandidateSevenVotesFirst() {
        return candidateSevenVotesFirst;
    }
    
    public void setCandidateSevenVotesFirst(Integer candidateSevenVotesFirst) {
        this.candidateSevenVotesFirst = candidateSevenVotesFirst;
    }
    
    public Integer getCandidateEightVotesFirst() {
        return candidateEightVotesFirst;
    }
    
    public void setCandidateEightVotesFirst(Integer candidateEightVotesFirst) {
        this.candidateEightVotesFirst = candidateEightVotesFirst;
    }
    
    public Integer getCandidateNineVotesFirst() {
        return candidateNineVotesFirst;
    }
    
    public void setCandidateNineVotesFirst(Integer candidateNineVotesFirst) {
        this.candidateNineVotesFirst = candidateNineVotesFirst;
    }
    
    public Integer getCandidateTenVotesFirst() {
        return candidateTenVotesFirst;
    }
    
    public void setCandidateTenVotesFirst(Integer candidateTenVotesFirst) {
        this.candidateTenVotesFirst = candidateTenVotesFirst;
    }
    
    public Integer getTotalVotesSecond() {
        return totalVotesSecond;
    }
    
    public void setTotalVotesSecond(Integer totalVotesSecond) {
        this.totalVotesSecond = totalVotesSecond;
    }
    
    public Integer getWhiteVotesSecond() {
        return whiteVotesSecond;
    }
    
    public void setWhiteVotesSecond(Integer whiteVotesSecond) {
        this.whiteVotesSecond = whiteVotesSecond;
    }
    
    public Integer getInvalidVotesSecond() {
        return invalidVotesSecond;
    }
    
    public void setInvalidVotesSecond(Integer invalidVotesSecond) {
        this.invalidVotesSecond = invalidVotesSecond;
    }
    
    public Integer getValidVotesSecond() {
        return validVotesSecond;
    }
    
    public void setValidVotesSecond(Integer validVotesSecond) {
        this.validVotesSecond = validVotesSecond;
    }
    
    public Integer getCandidateOneVotesSecond() {
        return candidateOneVotesSecond;
    }
    
    public void setCandidateOneVotesSecond(Integer candidateOneVotesSecond) {
        this.candidateOneVotesSecond = candidateOneVotesSecond;
    }
    
    public Integer getCandidateTwoVotesSecond() {
        return candidateTwoVotesSecond;
    }
    
    public void setCandidateTwoVotesSecond(Integer candidateTwoVotesSecond) {
        this.candidateTwoVotesSecond = candidateTwoVotesSecond;
    }
    
    public Integer getCandidateThreeVotesSecond() {
        return candidateThreeVotesSecond;
    }
    
    public void setCandidateThreeVotesSecond(Integer candidateThreeVotesSecond) {
        this.candidateThreeVotesSecond = candidateThreeVotesSecond;
    }
    
    public Integer getCandidateFourVotesSecond() {
        return candidateFourVotesSecond;
    }
    
    public void setCandidateFourVotesSecond(Integer candidateFourVotesSecond) {
        this.candidateFourVotesSecond = candidateFourVotesSecond;
    }
    
    public Integer getCandidateFiveVotesSecond() {
        return candidateFiveVotesSecond;
    }
    
    public void setCandidateFiveVotesSecond(Integer candidateFiveVotesSecond) {
        this.candidateFiveVotesSecond = candidateFiveVotesSecond;
    }
    
    public Integer getCandidateSixVotesSecond() {
        return candidateSixVotesSecond;
    }
    
    public void setCandidateSixVotesSecond(Integer candidateSixVotesSecond) {
        this.candidateSixVotesSecond = candidateSixVotesSecond;
    }
    
    public Integer getCandidateSevenVotesSecond() {
        return candidateSevenVotesSecond;
    }
    
    public void setCandidateSevenVotesSecond(Integer candidateSevenVotesSecond) {
        this.candidateSevenVotesSecond = candidateSevenVotesSecond;
    }
    
    public Integer getCandidateEightVotesSecond() {
        return candidateEightVotesSecond;
    }
    
    public void setCandidateEightVotesSecond(Integer candidateEightVotesSecond) {
        this.candidateEightVotesSecond = candidateEightVotesSecond;
    }
    
    public Integer getCandidateNineVotesSecond() {
        return candidateNineVotesSecond;
    }
    
    public void setCandidateNineVotesSecond(Integer candidateNineVotesSecond) {
        this.candidateNineVotesSecond = candidateNineVotesSecond;
    }
    
    public Integer getCandidateTenVotesSecond() {
        return candidateTenVotesSecond;
    }
    
    public void setCandidateTenVotesSecond(Integer candidateTenVotesSecond) {
        this.candidateTenVotesSecond = candidateTenVotesSecond;
    }
    
    public List<Contribution> getContributions() {
        return contributions;
    }
    
    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }
    
    public YesNoEnum getSubmittedFirst() {
        return submittedFirst;
    }
    
    public void setSubmittedFirst(YesNoEnum submittedFirst) {
        this.submittedFirst = submittedFirst;
    }
    
    public YesNoEnum getSubmittedSecond() {
        return submittedSecond;
    }
    
    public void setSubmittedSecond(YesNoEnum submittedSecond) {
        this.submittedSecond = submittedSecond;
    }
    
    public byte[] getAttachmentFirst() {
        return attachmentFirst;
    }
    
    public void setAttachmentFirst(byte[] attachmentFirst) {
        this.attachmentFirst = attachmentFirst;
    }
    
    public byte[] getAttachmentSecond() {
        return attachmentSecond;
    }
    
    public void setAttachmentSecond(byte[] attachmentSecond) {
        this.attachmentSecond = attachmentSecond;
    }

    public YesNoEnum getAccessDifficulty() {
        return accessDifficulty;
    }

    public void setAccessDifficulty(YesNoEnum accessDifficulty) {
        this.accessDifficulty = accessDifficulty;
    }
    
    public String getAttachmentNameFirst() {
        return attachmentNameFirst;
    }
    
    public void setAttachmentNameFirst(String attachmentNameFirst) {
        this.attachmentNameFirst = attachmentNameFirst;
    }
    
    public String getAttachmentNameSecond() {
        return attachmentNameSecond;
    }
    
    public void setAttachmentNameSecond(String attachmentNameSecond) {
        this.attachmentNameSecond = attachmentNameSecond;
    }
    
    public Date getSubmissionDateFirst() {
        return submissionDateFirst;
    }
    
    public void setSubmissionDateFirst(Date submissionDateFirst) {
        this.submissionDateFirst = submissionDateFirst;
    }
    
    public Date getSubmissionDateSecond() {
        return submissionDateSecond;
    }
    
    public void setSubmissionDateSecond(Date submissionDateSecond) {
        this.submissionDateSecond = submissionDateSecond;
    }
    
    public Integer getVerificationSerialFirst() {
        return verificationSerialFirst;
    }
    
    public void setVerificationSerialFirst(Integer verificationSerialFirst) {
        this.verificationSerialFirst = verificationSerialFirst;
    }
    
    public Integer getVerificationSerialSecond() {
        return verificationSerialSecond;
    }
    
    public void setVerificationSerialSecond(Integer verificationSerialSecond) {
        this.verificationSerialSecond = verificationSerialSecond;
    }
    
    public YesNoEnum getAllowInconsistentSubmission() {
        return allowInconsistentSubmission;
    }
    
    public void setAllowInconsistentSubmission(YesNoEnum allowInconsistentSubmission) {
        this.allowInconsistentSubmission = allowInconsistentSubmission;
    }
    
    public String getAttachmentTwoNameFirst() {
        return attachmentTwoNameFirst;
    }
    
    public void setAttachmentTwoNameFirst(String attachmentTwoNameFirst) {
        this.attachmentTwoNameFirst = attachmentTwoNameFirst;
    }
    
    public String getAttachmentTwoNameSecond() {
        return attachmentTwoNameSecond;
    }
    
    public void setAttachmentTwoNameSecond(String attachmentTwoNameSecond) {
        this.attachmentTwoNameSecond = attachmentTwoNameSecond;
    }
    
    public byte[] getAttachmentTwoFirst() {
        return attachmentTwoFirst;
    }
    
    public void setAttachmentTwoFirst(byte[] attachmentTwoFirst) {
        this.attachmentTwoFirst = attachmentTwoFirst;
    }
    
    public byte[] getAttachmentTwoSecond() {
        return attachmentTwoSecond;
    }
    
    public void setAttachmentTwoSecond(byte[] attachmentTwoSecond) {
        this.attachmentTwoSecond = attachmentTwoSecond;
    }
}
