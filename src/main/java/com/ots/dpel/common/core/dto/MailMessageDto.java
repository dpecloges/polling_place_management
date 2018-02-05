package com.ots.dpel.common.core.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class MailMessageDto {

    private String sender;
    private List<String> recipients = new ArrayList<>();
    private String subject;
    private String body;
    private boolean html;
    
    @Override
    public String toString() {
        return "MailMessageDto [sender=" + sender + ", recipients=" + recipients + ", subject=" + subject + ", body="
                + body + ", html=" + html + "]";
    }
    
    public MailMessageDto() {
        
    }
    
    public MailMessageDto(MailMessageDto other) {
        BeanUtils.copyProperties(other, this);
    }

    public MailMessageDto(
            String sender, List<String> recipients, 
            String subject, String body, boolean html) {
        
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.html = html;
    }

    public MailMessageDto(
            String sender, String recipient, 
            String subject, String body, boolean html) {
        
        this.sender = sender;
        this.recipients = Arrays.asList(recipient);
        this.subject = subject;
        this.body = body;
        this.html = html;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }
}
