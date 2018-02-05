package com.ots.dpel.management.dto.list;

import java.util.List;

import com.mysema.query.annotations.QueryProjection;
import com.ots.dpel.common.core.enums.YesNoEnum;

public class ElectionDepartmentIndexListDto {
    
    /** Διαχωριστικό γραμμών στα δεδομένα μελών των ΕΤ τα οποία παρουσιάζονται στην εφαρμογή */
    public static final String CONTRIBUTION_DATA_NEWLINE_WEB = "<br/>";
    /** Διαχωριστικό γραμμών στα δεδομένα μελών των ΕΤ τα οποία εξάγονται σε Excel */
    public static final String CONTRIBUTION_DATA_NEWLINE_EXCEL = "\n";
    
    private Long id;
    
    private String code;
    
    private String name;
    
    private String comments;
    
    private String accessDifficulty;
    
    private Long electionCenterId;
    
    private String electionCenterCode;
    
    private String electionCenterName;
    
    private String foreign;
    
    private String foreignCountry;
    
    private String foreignCity;
    
    private String geographicalUnit;
    
    private String decentralAdmin;
    
    private String region;
    
    private String regionalUnit;
    
    private String municipality;
    
    private String municipalUnit;
    
    private String address;
    
    private String postalCode;
    
    private String telephone;
    
    private String electionCenterComments;
    
    private Integer floorNumber;
    
    private String disabledAccess;
    
    private Integer estimatedBallotBoxes;
    
    private Integer ballotBoxes;
    
    private Integer voters2007;
    
    private String committeeLeader;
    
    private String committeeLeaderVice;
    
    private String committeeMember;
    
    private String idVerifier;
    
    private String idVerifierVice;
    
    private String treasurer;
    
    private String candidateOneRep;
    
    private String candidateTwoRep;
    
    private String candidateThreeRep;
    
    private String candidateFourRep;
    
    private String candidateFiveRep;
    
    private String candidateSixRep;
    
    private String candidateSevenRep;
    
    private String candidateEightRep;
    
    private String candidateNineRep;
    
    private String submitted;
    
    private Long voterCount;
    
    private Long undoneVoterCount;
    
    private String electionCenterDisplayName;
    
    private String electionDepartmentDisplayName;
    
    public ElectionDepartmentIndexListDto() {
    }
    
    @QueryProjection
    public ElectionDepartmentIndexListDto(Long id, String code, String name, String comments, YesNoEnum accessDifficulty, Long electionCenterId,
                                          String electionCenterCode, String electionCenterName, YesNoEnum foreign, String foreignCountry,
                                          String foreignCity, String geographicalUnit, String decentralAdmin, String region, String regionalUnit,
                                          String municipality, String municipalUnit, String address, String postalCode, String telephone,
                                          String electionCenterComments, Integer floorNumber, YesNoEnum disabledAccess, Integer estimatedBallotBoxes,
                                          Integer ballotBoxes, Integer voters2007) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.comments = comments;
        this.accessDifficulty = accessDifficulty != null && accessDifficulty.equals(YesNoEnum.YES) ? "ΝΑΙ" : "ΟΧΙ";
        this.electionCenterId = electionCenterId;
        this.electionCenterCode = electionCenterCode;
        this.electionCenterName = electionCenterName;
        this.foreign = foreign != null && foreign.equals(YesNoEnum.YES) ? "ΝΑΙ" : "ΟΧΙ";
        this.foreignCountry = foreignCountry;
        this.foreignCity = foreignCity;
        this.geographicalUnit = geographicalUnit;
        this.decentralAdmin = decentralAdmin;
        this.region = region;
        this.regionalUnit = regionalUnit;
        this.municipality = municipality;
        this.municipalUnit = municipalUnit;
        this.address = address;
        this.postalCode = postalCode;
        this.telephone = telephone;
        this.electionCenterComments = electionCenterComments;
        this.floorNumber = floorNumber;
        this.disabledAccess = disabledAccess != null && disabledAccess.equals(YesNoEnum.YES) ? "ΝΑΙ" : "ΟΧΙ";
        this.estimatedBallotBoxes = estimatedBallotBoxes;
        this.ballotBoxes = ballotBoxes;
        this.voters2007 = voters2007;
    }
    
    @QueryProjection
    public ElectionDepartmentIndexListDto(Long id, String code, String name, Long electionCenterId, String electionCenterCode,
                                          String electionCenterName, YesNoEnum foreign, String foreignCountry,String foreignCity,
                                          String geographicalUnit, String decentralAdmin, String region, String regionalUnit, String municipality,
                                          String municipalUnit, YesNoEnum submitted) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.electionCenterId = electionCenterId;
        this.electionCenterCode = electionCenterCode;
        this.electionCenterName = electionCenterName;
        this.foreign = foreign != null && foreign.equals(YesNoEnum.YES) ? "ΝΑΙ" : "ΟΧΙ";
        this.foreignCountry = foreignCountry;
        this.foreignCity = foreignCity;
        this.geographicalUnit = geographicalUnit;
        this.decentralAdmin = decentralAdmin;
        this.region = region;
        this.regionalUnit = regionalUnit;
        this.municipality = municipality;
        this.municipalUnit = municipalUnit;
        this.submitted = submitted != null && submitted.equals(YesNoEnum.YES) ? "ΝΑΙ" : "ΟΧΙ";
    }
    
    /**
     * Επεξεργασία των δεδομένων Εκλογικών Τμημάτων
     * μετά την ανάκτησή τους και πριν την εξαγωγή τους σε Excel.
     * 
     * @param departments
     *     list of {@code ElectionDepartmentIndexListDto}s
     */
    public static void postProcessExcelReportData(
            List<ElectionDepartmentIndexListDto> departments) {
        
        if (departments == null) {
            return;
        }
        
        for (ElectionDepartmentIndexListDto department: departments) {
            department.convertNewLinesToExcel();
        }
        
    }
    
    /**
     * Αντικαθιστά τα {@code <br/>} σε {@code \n} στα δεδομένα
     * των μελών του Εκλογικού Τμήματος.
     */
    public void convertNewLinesToExcel() {
        
        if (committeeLeader != null) {
            committeeLeader = convertNewLinesToExcel(committeeLeader);
        }
        
        if (committeeLeaderVice != null) {
            committeeLeaderVice = convertNewLinesToExcel(committeeLeaderVice);
        }
        
        if (committeeMember != null) {
            committeeMember = convertNewLinesToExcel(committeeMember);
        }
        
        if (idVerifier != null) {
            idVerifier = convertNewLinesToExcel(idVerifier);
        }
    
        if (idVerifierVice != null) {
            idVerifierVice = convertNewLinesToExcel(idVerifierVice);
        }
        
        if (treasurer != null) {
            treasurer = convertNewLinesToExcel(treasurer);
        }
    
        if (candidateOneRep != null) {
            candidateOneRep = convertNewLinesToExcel(candidateOneRep);
        }
    
        if (candidateTwoRep != null) {
            candidateTwoRep = convertNewLinesToExcel(candidateTwoRep);
        }
    
        if (candidateThreeRep != null) {
            candidateThreeRep = convertNewLinesToExcel(candidateThreeRep);
        }
    
        if (candidateFourRep != null) {
            candidateFourRep = convertNewLinesToExcel(candidateFourRep);
        }
    
        if (candidateFiveRep != null) {
            candidateFiveRep = convertNewLinesToExcel(candidateFiveRep);
        }
    
        if (candidateSixRep != null) {
            candidateSixRep = convertNewLinesToExcel(candidateSixRep);
        }
    
        if (candidateSevenRep != null) {
            candidateSevenRep = convertNewLinesToExcel(candidateSevenRep);
        }
    
        if (candidateEightRep != null) {
            candidateEightRep = convertNewLinesToExcel(candidateEightRep);
        }
    
        if (candidateNineRep != null) {
            candidateNineRep = convertNewLinesToExcel(candidateNineRep);
        }
    }
    
    private static String convertNewLinesToExcel(String data) {
        return data.replaceAll(CONTRIBUTION_DATA_NEWLINE_WEB, CONTRIBUTION_DATA_NEWLINE_EXCEL);
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
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Integer getFloorNumber() {
        return floorNumber;
    }
    
    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }
    
    public String getDisabledAccess() {
        return disabledAccess;
    }
    
    public void setDisabledAccess(String disabledAccess) {
        this.disabledAccess = disabledAccess;
    }
    
    public Integer getEstimatedBallotBoxes() {
        return estimatedBallotBoxes;
    }
    
    public void setEstimatedBallotBoxes(Integer estimatedBallotBoxes) {
        this.estimatedBallotBoxes = estimatedBallotBoxes;
    }
    
    public Integer getBallotBoxes() {
        return ballotBoxes;
    }
    
    public void setBallotBoxes(Integer ballotBoxes) {
        this.ballotBoxes = ballotBoxes;
    }
    
    public Integer getVoters2007() {
        return voters2007;
    }
    
    public void setVoters2007(Integer voters2007) {
        this.voters2007 = voters2007;
    }
    
    public String getAccessDifficulty() {
        return accessDifficulty;
    }
    
    public void setAccessDifficulty(String accessDifficulty) {
        this.accessDifficulty = accessDifficulty;
    }
    
    public Long getElectionCenterId() {
        return electionCenterId;
    }
    
    public void setElectionCenterId(Long electionCenterId) {
        this.electionCenterId = electionCenterId;
    }
    
    public String getElectionCenterCode() {
        return electionCenterCode;
    }
    
    public void setElectionCenterCode(String electionCenterCode) {
        this.electionCenterCode = electionCenterCode;
    }
    
    public String getElectionCenterName() {
        return electionCenterName;
    }
    
    public void setElectionCenterName(String electionCenterName) {
        this.electionCenterName = electionCenterName;
    }
    
    public String getForeign() {
        return foreign;
    }
    
    public void setForeign(String foreign) {
        this.foreign = foreign;
    }
    
    public String getForeignCountry() {
        return foreignCountry;
    }
    
    public void setForeignCountry(String foreignCountry) {
        this.foreignCountry = foreignCountry;
    }
    
    public String getForeignCity() {
        return foreignCity;
    }
    
    public void setForeignCity(String foreignCity) {
        this.foreignCity = foreignCity;
    }
    
    public String getGeographicalUnit() {
        return geographicalUnit;
    }
    
    public void setGeographicalUnit(String geographicalUnit) {
        this.geographicalUnit = geographicalUnit;
    }
    
    public String getDecentralAdmin() {
        return decentralAdmin;
    }
    
    public void setDecentralAdmin(String decentralAdmin) {
        this.decentralAdmin = decentralAdmin;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getRegionalUnit() {
        return regionalUnit;
    }
    
    public void setRegionalUnit(String regionalUnit) {
        this.regionalUnit = regionalUnit;
    }
    
    public String getMunicipality() {
        return municipality;
    }
    
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
    
    public String getMunicipalUnit() {
        return municipalUnit;
    }
    
    public void setMunicipalUnit(String municipalUnit) {
        this.municipalUnit = municipalUnit;
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
    
    public String getElectionCenterComments() {
        return electionCenterComments;
    }
    
    public void setElectionCenterComments(String electionCenterComments) {
        this.electionCenterComments = electionCenterComments;
    }
    
    public String getCommitteeLeader() {
        return committeeLeader;
    }
    
    public void setCommitteeLeader(String committeeLeader) {
        this.committeeLeader = committeeLeader;
    }
    
    public String getCommitteeLeaderVice() {
        return committeeLeaderVice;
    }
    
    public void setCommitteeLeaderVice(String committeeLeaderVice) {
        this.committeeLeaderVice = committeeLeaderVice;
    }
    
    public String getCommitteeMember() {
        return committeeMember;
    }
    
    public void setCommitteeMember(String committeeMember) {
        this.committeeMember = committeeMember;
    }
    
    public String getIdVerifier() {
        return idVerifier;
    }
    
    public void setIdVerifier(String idVerifier) {
        this.idVerifier = idVerifier;
    }
    
    public String getIdVerifierVice() {
        return idVerifierVice;
    }
    
    public void setIdVerifierVice(String idVerifierVice) {
        this.idVerifierVice = idVerifierVice;
    }
    
    public String getTreasurer() {
        return treasurer;
    }
    
    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }
    
    public String getCandidateOneRep() {
        return candidateOneRep;
    }
    
    public void setCandidateOneRep(String candidateOneRep) {
        this.candidateOneRep = candidateOneRep;
    }
    
    public String getCandidateTwoRep() {
        return candidateTwoRep;
    }
    
    public void setCandidateTwoRep(String candidateTwoRep) {
        this.candidateTwoRep = candidateTwoRep;
    }
    
    public String getCandidateThreeRep() {
        return candidateThreeRep;
    }
    
    public void setCandidateThreeRep(String candidateThreeRep) {
        this.candidateThreeRep = candidateThreeRep;
    }
    
    public String getCandidateFourRep() {
        return candidateFourRep;
    }
    
    public void setCandidateFourRep(String candidateFourRep) {
        this.candidateFourRep = candidateFourRep;
    }
    
    public String getCandidateFiveRep() {
        return candidateFiveRep;
    }
    
    public void setCandidateFiveRep(String candidateFiveRep) {
        this.candidateFiveRep = candidateFiveRep;
    }
    
    public String getCandidateSixRep() {
        return candidateSixRep;
    }
    
    public void setCandidateSixRep(String candidateSixRep) {
        this.candidateSixRep = candidateSixRep;
    }
    
    public String getCandidateSevenRep() {
        return candidateSevenRep;
    }
    
    public void setCandidateSevenRep(String candidateSevenRep) {
        this.candidateSevenRep = candidateSevenRep;
    }
    
    public String getCandidateEightRep() {
        return candidateEightRep;
    }
    
    public void setCandidateEightRep(String candidateEightRep) {
        this.candidateEightRep = candidateEightRep;
    }
    
    public String getCandidateNineRep() {
        return candidateNineRep;
    }
    
    public void setCandidateNineRep(String candidateNineRep) {
        this.candidateNineRep = candidateNineRep;
    }
    
    public String getSubmitted() {
        return submitted;
    }
    
    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }
    
    public Long getVoterCount() {
        return voterCount;
    }
    
    public void setVoterCount(Long voterCount) {
        this.voterCount = voterCount;
    }
    
    public Long getUndoneVoterCount() {
        return undoneVoterCount;
    }
    
    public void setUndoneVoterCount(Long undoneVoterCount) {
        this.undoneVoterCount = undoneVoterCount;
    }
    
    public String getElectionCenterDisplayName() {
        String thisElectionCenterCode = (this.electionCenterCode == null) ? "" : this.electionCenterCode;
        Boolean thisForeign = (this.foreign != null && this.foreign == "ΝΑΙ");
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipality = (this.municipality == null) ? "" : this.municipality;
        
        return thisElectionCenterCode + " - " + (thisForeign ? thisElectionCenterName : thisMunicipality);
    }
    
    public void setElectionCenterDisplayName(String electionCenterDisplayName) {
        this.electionCenterDisplayName = electionCenterDisplayName;
    }
    
    public String getElectionDepartmentDisplayName() {
        String thisElectionDepartmentName = (this.name == null) ? "" : this.name;
        Boolean thisForeign = (this.foreign != null && this.foreign == "ΝΑΙ");
        String thisElectionCenterName = (this.electionCenterName == null) ? "" : this.electionCenterName;
        String thisMunicipality = (this.municipality == null) ? "" : this.municipality;
        
        return thisElectionDepartmentName + " - " + (thisForeign ? thisElectionCenterName : thisMunicipality);
    }
    
    public void setElectionDepartmentDisplayName(String electionDepartmentDisplayName) {
        this.electionDepartmentDisplayName = electionDepartmentDisplayName;
    }
}
