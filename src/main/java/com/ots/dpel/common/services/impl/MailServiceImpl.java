package com.ots.dpel.common.services.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ots.dpel.common.core.domain.MailTemplate.MailTemplateCode;
import com.ots.dpel.common.core.dto.MailMessageDto;
import com.ots.dpel.common.core.dto.MailTemplateDto;
import com.ots.dpel.common.events.MailMessageSentEvent;
import com.ots.dpel.common.services.MailService;
import com.ots.dpel.common.services.MailTemplateService;
import com.ots.dpel.config.beans.MailConfigBean;

@Service
public class MailServiceImpl implements MailService, InitializingBean {

    private static final Logger logger = LoggerFactory
            .getLogger(MailServiceImpl.class);

    @Autowired
    private MailTemplateService mailTemplateService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private MailConfigBean mailConfig;
    
    @Autowired
    private ApplicationContext applicationContext;

    private List<String> testModeRecipients = Collections.emptyList();
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (mailConfig.isTestMode()) {
            String testModeRecipient = mailConfig.getTestModeRecipient();
            if (StringUtils.isNotBlank(testModeRecipient)) {
                this.testModeRecipients = Collections.singletonList(testModeRecipient);
            }
        }
    }
    
    @Async
    @Override
    public void sendEmail(String toAddress, String templateCode,
            Map<String, Object> args) {
        this.sendEmail(Arrays.asList(toAddress), templateCode, args);
    }

    @Async
    @Override
    public void sendEmail(String toAddress, MailTemplateCode templateCode,
            Map<String, Object> args) {
        sendEmail(Arrays.asList(toAddress), templateCode.name(), args);
    }

    @Async
    @Override
    public void sendEmail(String toAddress, String title, String body) {
        sendEmail(Arrays.asList(toAddress), title, body);
    }

    @Async
    @Override
    public void sendEmail(List<String> toAddresses,
            MailTemplateCode templateCode, Map<String, Object> args) {
        sendEmail(toAddresses, templateCode.name(), args);
    }

    @Async
    @Override
    public void sendEmail(List<String> toAddresses, String templateCode,
            Map<String, Object> args) {
        
        MailTemplateDto mailTemplateDto = this.mailTemplateService.findByCode(templateCode);

        if (mailTemplateDto == null) {
            logger.error("Could not send mail to: {}. Mail template:{} was not found", templateCode);
            return;
        }
        
        MailMessageDto mailMessageDto = new MailMessageDto();
        this.mailTemplateService.populateMailMessageFromTemplate(mailMessageDto, mailTemplateDto, args, toAddresses);
        
        this.sendEmail(mailMessageDto);
    }

    @Async
    @Override
    public void sendEmail(List<String> toAddresses, String title, String body) {
        this.sendEmail(toAddresses, title, body, false);
    }

    @Async
    @Override
    public void sendEmail(List<String> toAddresses, String title, String body,
            boolean html) {
        sendEmail(new MailMessageDto(mailConfig.getSenderMail(), toAddresses, title, body, html));
    }

    @Async
    @Override
    public void sendEmail(MailMessageDto mailMessageDto) {
        sendEmails(Collections.singletonList(mailMessageDto));
    }

    @Async
    @Override
    public void sendEmails(MailMessageDto ... mailMessageDtos) {
        
        if (mailMessageDtos == null || mailMessageDtos.length == 0) {
            return;
        }
        
        sendEmails(Arrays.asList(mailMessageDtos));
    }

    @Async
    @Override
    public void sendEmails(List<MailMessageDto> mailMessageDtos) {
        
        if (CollectionUtils.isEmpty(mailMessageDtos)) {
            return;
        }
        
        if (!mailConfig.isBatchEnabled()) {
            batchSendEmails(mailMessageDtos);
            return;
        }
        
        int batchSize = mailConfig.getBatchSize();
        int messageCount = mailMessageDtos.size();
        int startIndex = 0;
        int endIndex = batchSize;
        
        while (startIndex < messageCount) {
            endIndex = Math.min(startIndex + batchSize, messageCount);
            
            List<MailMessageDto> batch = mailMessageDtos.subList(startIndex, endIndex);
            batchSendEmails(batch);
            
            startIndex += batchSize;
        }
    }
    
    /**
     * Στέλνει μαζικά τα email στη λίστα {@code mailMessageDtos}.
     * 
     * @param mailMessageDtos 
     *     λίστα email messages
     */
    private void batchSendEmails(List<MailMessageDto> mailMessageDtos) {
        
        int i = 0;
        int batchSize = mailMessageDtos.size();
        SimpleMimeMessagePreparator[] mailMessagePreparators = 
            new SimpleMimeMessagePreparator[batchSize];
        
        if (mailConfig.isTestMode()) {
            setTestModeRecipients(mailMessageDtos);
        }
        
        for (MailMessageDto dto: mailMessageDtos) {
            logger.debug("Adding mail {}/{} to batch. Recipients: {}. Subject: {}. Body: {}", 
                new Object[] { (i+1), batchSize, dto.getRecipients(), dto.getSubject(), dto.getBody() });
            
            dto.setSender(mailConfig.getSenderMail());
            mailMessagePreparators[i] = new SimpleMimeMessagePreparator(dto);
            
            i++;
        }
        
        mailSender.send(mailMessagePreparators);
        
        applicationContext.publishEvent(new MailMessageSentEvent(mailMessageDtos));
    }
    
    /**
     * Θέτει αντικαθιστά τις λίστες παραληπτών των {@code mailMessageDtos}
     * με τη λίστα παραληπτών του test mode.
     * 
     * @param mailMessageDtos
     */
    private void setTestModeRecipients(List<MailMessageDto> mailMessageDtos) {
        for (MailMessageDto mailMessageDto: mailMessageDtos) {
            mailMessageDto.setRecipients(testModeRecipients);
        }
    }

    private static class SimpleMimeMessagePreparator implements
            MimeMessagePreparator {

        private final String sender;
        private final List<String> recipients;
        private final String title;
        private final String body;
        private final boolean html;
        
        /**
         * 
         * @param mailMessageDto
         */
        public SimpleMimeMessagePreparator(MailMessageDto mailMessageDto) {
            this.sender = mailMessageDto.getSender();
            this.recipients = mailMessageDto.getRecipients();
            this.title = mailMessageDto.getSubject();
            this.body = mailMessageDto.getBody();
            this.html = mailMessageDto.isHtml();
        }

        @Override
        public void prepare(MimeMessage msg) throws Exception {
            MimeMessageHelper helper = new MimeMessageHelper(msg, false,
                    "UTF-8");
            String[] recp = new String[recipients.size()];
            recipients.toArray(recp);
            helper.setTo(recp);

            if (StringUtils.isNotBlank(sender)) {
                helper.setFrom(sender);
            }

            helper.setSubject(title);
            helper.setText(body, html);
        }
    }
}
