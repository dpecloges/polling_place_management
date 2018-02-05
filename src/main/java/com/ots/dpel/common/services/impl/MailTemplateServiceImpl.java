package com.ots.dpel.common.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ots.dpel.common.core.domain.MailTemplate;
import com.ots.dpel.common.core.domain.MailTemplate.MailTemplateCode;
import com.ots.dpel.common.core.dto.MailMessageDto;
import com.ots.dpel.common.core.dto.MailTemplateDto;
import com.ots.dpel.common.persistence.MailTemplateRepository;
import com.ots.dpel.common.services.MailTemplateService;
import com.ots.dpel.config.beans.MailConfigBean;
import com.ots.dpel.global.tpl.TextTemplate;
import com.ots.dpel.global.tpl.TextTemplateRenderer;
import com.ots.dpel.global.tpl.TextTemplateRendererProvider;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

    private static final String SUFFIX_TITLE = "_TITLE";
    private static final String SUFFIX_BODY = "_BODY";

    @Autowired
    private MailTemplateRepository mailTemplateRepository;
    
    @Autowired
    private TextTemplateRendererProvider rendererProvider;
    
    @Autowired
    private MailConfigBean mailConfig;
    
    @Override
    public MailTemplateDto findByCode(String code) {
        MailTemplate mailTemplate = mailTemplateRepository.findByCode(code);
        if (mailTemplate == null) {
            return null;
        }
        
        MailTemplateDto mailTemplateDto = new MailTemplateDto();
        entityToDto(mailTemplate, mailTemplateDto);
        
        return mailTemplateDto;
    }

    @Override
    public MailTemplateDto findByCodeEnum(MailTemplateCode mailTemplateCode) {
        return findByCode(mailTemplateCode.name());
    }
    
    
    
    private void entityToDto(MailTemplate entity, MailTemplateDto dto) {
        BeanUtils.copyProperties(entity, dto);
    }
    
    @Override
    public MailMessageDto createMailMessageFromTemplate(
            MailTemplateDto mailTemplateDto, Map<String, Object> args, List<String> recipients) {
        
        MailMessageDto mailMessageDto = new MailMessageDto();
        populateMailMessageFromTemplate(mailMessageDto, mailTemplateDto, args, recipients);
        
        return mailMessageDto;
    }

    @Override
    public void populateMailMessageFromTemplate(MailMessageDto mailMessageDto, 
            MailTemplateDto mailTemplateDto, Map<String, Object> args, List<String> recipients) {

        String mailTemplateCode = mailTemplateDto.getCode();
        String sender = mailConfig.getSenderMail();
        
        String rendererName = mailTemplateDto.getRenderer();
        TextTemplateRenderer renderer = this.rendererProvider.getTextRenderer(rendererName);
        TextTemplate subjectTemplate = new TextTemplate(mailTemplateCode + SUFFIX_TITLE, mailTemplateDto.getSubject());
        TextTemplate bodyTemplate = new TextTemplate(mailTemplateCode + SUFFIX_BODY, mailTemplateDto.getBody());
        String subject = renderer.render(subjectTemplate, args);
        String body = renderer.render(bodyTemplate, args);
        
        mailMessageDto.setSender(sender);
        mailMessageDto.setRecipients(recipients);
        mailMessageDto.setSubject(subject);
        mailMessageDto.setBody(body);
        mailMessageDto.setHtml(mailTemplateDto.isHtml());
    }
}
