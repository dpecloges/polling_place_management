package com.ots.dpel.auth.dto;

import java.util.List;

import com.ots.dpel.common.core.dto.MailMessageDto;

public class UserRegistrationMailMessageDto extends MailMessageDto {
    
    public static final String ARG_USER_FULLNAME = "USER_FULLNAME";
    public static final String ARG_BASE_URL = "BASE_URL";
    public static final String ARG_USER_ACTIVATION_CODE = "USER_ACTIVATION_CODE";
    
    private Long contributionId;

    public UserRegistrationMailMessageDto() {
    }

    public UserRegistrationMailMessageDto(MailMessageDto other) {
        super(other);
    }

    public UserRegistrationMailMessageDto(String sender, List<String> recipients, String subject, String body,
            boolean html) {
        super(sender, recipients, subject, body, html);
    }

    public UserRegistrationMailMessageDto(String sender, String recipient, String subject, String body, boolean html) {
        super(sender, recipient, subject, body, html);
    }

    public Long getContributionId() {
        return contributionId;
    }

    public void setContributionId(Long contributionId) {
        this.contributionId = contributionId;
    }
}
