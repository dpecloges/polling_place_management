/**
 *
 */
package com.ots.dpel.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

import com.ots.dpel.config.beans.ApiConfigBean;
import com.ots.dpel.config.beans.MailConfigBean;
import com.ots.dpel.config.beans.SolrConfigBean;
import com.ots.dpel.config.beans.WebConfigBean;

/**
 * @author mbellas
 */
@Configuration
public class ApiConfiguration {
    
    @Autowired
    private ConfigurableEnvironment env;
    
    @Bean(name = "configProperties")
    public Properties configProperties() {
        
        Properties props = new Properties();
        
        MutablePropertySources propSources = this.env.getPropertySources();
        
        for (PropertySource<?> propSource: propSources) {
            if (propSource instanceof ResourcePropertySource) {
                ResourcePropertySource resPropSource = (ResourcePropertySource) propSource;
                String[] propNames = resPropSource.getPropertyNames();
                for (String propName: propNames) {
                    props.put(propName, resPropSource.getProperty(propName));
                }
            }
        }
        
        return props;
    }
    
    @Bean
    public ApiConfigBean getApiConfiguration() {
        ApiConfigBean apiProperties = new ApiConfigBean();
        return apiProperties;
    }
    
    @Bean
    public SolrConfigBean getSolrProperties() {
        SolrConfigBean solrProperties = new SolrConfigBean();
        solrProperties.setEnabled(Boolean.valueOf(env.getProperty("solr.enabled")));
        solrProperties.setHost(env.getProperty("solr.host"));
        return solrProperties;
    }
    
    @Bean
    public MailConfigBean getMailProperties() {
        MailConfigBean mailProperties = new MailConfigBean();
        
        mailProperties.setHost(env.getProperty("dpelapi.mail.host"));
        mailProperties.setPort(Integer.parseInt(env.getProperty("dpelapi.mail.port")));
        String username = env.getProperty("dpelapi.mail.username");
        if (username != null && !username.isEmpty()) {
            mailProperties.setUsername(username);
        }
        String password = env.getProperty("dpelapi.mail.password");
        if (password != null && !password.isEmpty()) {
            mailProperties.setPassword(password);
        }
        mailProperties.setDefaultEncoding(env.getProperty("dpelapi.mail.defaultEncoding"));
        mailProperties.setSenderMail(env.getProperty("dpelapi.mail.senderMail"));
        
        mailProperties.setJavaMailProperties(configProperties());
        
        boolean batchEnabled = Boolean.valueOf(env.getProperty("dpelapi.mail.batch.enabled"));
        mailProperties.setBatchEnabled(batchEnabled);
        if (batchEnabled) {
            mailProperties.setBatchSize(Integer.parseInt(env.getProperty("dpelapi.mail.batch.size")));
        }
        
        mailProperties.setTestMode(Boolean.valueOf(env.getProperty("dpelapi.mail.testMode")));
        mailProperties.setTestModeRecipient(env.getProperty("dpelapi.mail.testMode.recipient"));
        
        return mailProperties;
    }
    
    @Bean
    public WebConfigBean getWebProperties() {
        
        WebConfigBean webProperties = new WebConfigBean();
        
        webProperties.setDpElectionViewBaseUrl(env.getProperty("web.urls.view.base"));
        
        return webProperties;
    }
}
