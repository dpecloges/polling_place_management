package com.ots.dpel.common.services;

import java.util.List;
import java.util.Map;

import com.ots.dpel.common.core.domain.MailTemplate.MailTemplateCode;
import com.ots.dpel.common.core.dto.MailMessageDto;

public interface MailService {

	/**
	 * 
	 * @param toAddress
	 *            recipient email address
	 * @param templateName
	 *            the unique code which will be used to load mail template from
	 *            database.
	 * @param args
	 *            arguments which will be used by renderer to render the output
	 *            email string
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(String toAddress, String templateCode,
			Map<String, Object> args);

	/**
	 * 
	 * @param toAddress
	 *            recipient email address
	 * @param templateName
	 *            the unique code which will be used to load mail template from
	 *            database.
	 * @param args
	 *            arguments which will be used by renderer to render the output
	 *            email string
	 * 
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(String toAddress, MailTemplateCode templateCode,
			Map<String, Object> args);

	/**
	 * 
	 * @param toAddresses
	 *            list of recipient email address
	 * @param templateName
	 *            the unique code which will be used to load mail template from
	 *            database.
	 * @param args
	 *            arguments which will be used by renderer to render the output
	 *            email string
	 * 
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(List<String> toAddresses, String templateCode,
			Map<String, Object> args);

	/**
	 * 
	 * @param toAddresses
	 *            list of recipient email address
	 * @param templateName
	 *            the unique code which will be used to load mail template from
	 *            database.
	 * @param args
	 *            arguments which will be used by renderer to render the output
	 *            email string
	 * 
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(List<String> toAddresses, MailTemplateCode templateCode,
			Map<String, Object> args);

	/**
	 * 
	 * @param toAddress
	 *            recipient email address
	 * @param title
	 *            title of message
	 * @param body
	 *            body of mail message. It can be an html body.
	 * 
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(String toAddress, String title, String body);

	/**
	 * Sends an email message, with the specified title and body, to the
	 * specified email addresses.
	 * 
	 * @param title
	 * @param body
	 * @param toAddresses
	 *            a list of recipient email addresses
	 * 
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(List<String> toAddresses, String title, String body);

	/**
	 * Sends an email message, with the specified title and body, to the
	 * specified email addresses.
	 * 
	 * @param title
	 * @param body
	 * @param toAddresses
	 *            a list of recipient email addresses
	 * @param html
	 *            set to true if you want to apply content type "text/html" for
	 *            an HTML mail or false if you want to use default content type
	 *            ("text/plain") else. Default: false
	 * 
	 * @throws org.springframework.mail.MailPreparationException
	 *             in case of failure when preparing the message
	 * @throws org.springframework.mail.MailParseException
	 *             in case of failure when parsing the message
	 * @throws org.springframework.mail.MailAuthenticationException
	 *             in case of authentication failure
	 * @throws org.springframework.mail.MailSendException
	 *             in case of failure when sending the message
	 */
	void sendEmail(List<String> toAddresses, String title, String body,
			boolean html);
	
	/**
	 * 
	 * @param mailMessageDto mail message
     * 
     * @throws org.springframework.mail.MailPreparationException
     *             in case of failure when preparing the message
     * @throws org.springframework.mail.MailParseException
     *             in case of failure when parsing the message
     * @throws org.springframework.mail.MailAuthenticationException
     *             in case of authentication failure
     * @throws org.springframework.mail.MailSendException
     *             in case of failure when sending the message
	 */
	void sendEmail(MailMessageDto mailMessageDto);
	
	/**
	 * 
	 * @param mailMessageDtos mail messages
     * 
     * @throws org.springframework.mail.MailPreparationException
     *             in case of failure when preparing the messages
     * @throws org.springframework.mail.MailParseException
     *             in case of failure when parsing the messages
     * @throws org.springframework.mail.MailAuthenticationException
     *             in case of authentication failure
     * @throws org.springframework.mail.MailSendException
     *             in case of failure when sending the messages
     */
    void sendEmails(MailMessageDto ... mailMessageDtos);
	
	/**
	 * 
	 * @param mailMessageDtos mail messages
     * 
     * @throws org.springframework.mail.MailPreparationException
     *             in case of failure when preparing the messages
     * @throws org.springframework.mail.MailParseException
     *             in case of failure when parsing the messages
     * @throws org.springframework.mail.MailAuthenticationException
     *             in case of authentication failure
     * @throws org.springframework.mail.MailSendException
     *             in case of failure when sending the messages
	 */
    void sendEmails(List<MailMessageDto> mailMessageDtos);
}
