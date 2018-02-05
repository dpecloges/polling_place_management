package com.ots.dpel.results.services;

import com.ots.dpel.management.core.enums.ElectionRound;
import com.ots.dpel.results.core.enums.AttachmentType;
import com.ots.dpel.results.dto.ResultsDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;

public interface SubmissionService {
    
    ResultsDto findResults(Long id);
    
    ResultsDto saveResults(ResultsDto resultsDto);
    
    void uploadFile(Long electionDepartmentId, MultipartFile file, AttachmentType attachmentType);
    
    void downloadFile(Long electionDepartmentId, ServletOutputStream outputStream, AttachmentType attachmentType);
    
    byte[] findAttachmentTwo(Long electionDepartmentId, ElectionRound electionRound);
    
    byte[] findAttachment(Long electionDepartmentId, ElectionRound electionRound);
}
