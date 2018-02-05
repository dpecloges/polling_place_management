package com.ots.dpel.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.ots.dpel.config.beans.MailConfigBean;

@Configuration
public class MailConfiguration {
    
    @Autowired
    private Properties configProperties;
    
    @Autowired
    private MailConfigBean mailProperties;

    
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        
        sender.setHost(mailProperties.getHost());
        sender.setPort(mailProperties.getPort());
        sender.setUsername(mailProperties.getUsername());
        sender.setPassword(mailProperties.getPassword());
        sender.setDefaultEncoding(mailProperties.getDefaultEncoding());
        
        sender.setJavaMailProperties(configProperties);
        
        return sender;
    }
}
