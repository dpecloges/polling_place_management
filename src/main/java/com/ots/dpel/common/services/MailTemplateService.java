package com.ots.dpel.common.services;

import java.util.List;
import java.util.Map;

import com.ots.dpel.common.core.domain.MailTemplate.MailTemplateCode;
import com.ots.dpel.common.core.dto.MailMessageDto;
import com.ots.dpel.common.core.dto.MailTemplateDto;

public interface MailTemplateService {

    MailTemplateDto findByCode(String code);
    
    MailTemplateDto findByCodeEnum(MailTemplateCode mailTemplateCode);
    
    /**
     * Processes the given mail template by merging the specified variables
     * and creates a mail message for the specified recipients.
     * 
     * <p>The sender is defined from the mail-related configuration of the application.</p>
     * 
     * @param mailTemplateDto 
     *     mail template
     * @param args 
     *     variables to be merged in template
     * @param recipients 
     *     list of recipient addresses
     * @return
     *     mail message
     */
    MailMessageDto createMailMessageFromTemplate(
        MailTemplateDto mailTemplateDto, Map<String, Object> args, List<String> recipients);
    
    /**
     * Processes the given mail template by merging the specified variables
     * and sets the respective properties of {@code mailMessageDto}.
     * 
     * @param mailMessageDto
     *     mail message
     * @param mailTemplateDto
     *     mail template
     * @param args
     *     variables to be merged in template
     * @param recipients
     *     list of recipient addresses
     */
    void populateMailMessageFromTemplate(MailMessageDto mailMessageDto, 
        MailTemplateDto mailTemplateDto, Map<String, Object> args, List<String> recipients);
}
