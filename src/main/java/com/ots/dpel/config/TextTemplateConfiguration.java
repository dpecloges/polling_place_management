package com.ots.dpel.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ots.dpel.global.tpl.FreemarkerRenderer;
import com.ots.dpel.global.tpl.SimpleRenderer;
import com.ots.dpel.global.tpl.StringTemplateRenderer;
import com.ots.dpel.global.tpl.TextTemplateRenderer;
import com.ots.dpel.global.tpl.TextTemplateRendererProvider;

@Configuration
public class TextTemplateConfiguration {
    
    @Bean
    public TextTemplateRenderer simpleRenderer() {
        return new SimpleRenderer();
    }
    
    @Bean
    public TextTemplateRenderer stringTemplateRenderer() {
        return new StringTemplateRenderer();
    }
    
    @Bean
    public TextTemplateRenderer freemarkerRenderer() {
        return new FreemarkerRenderer();
    }

    @Bean
    public TextTemplateRendererProvider textTemplateRendererProvider() {
        return new TextTemplateRendererProvider(
            Arrays.asList(
                simpleRenderer(), 
                stringTemplateRenderer(), 
                freemarkerRenderer()));
    }
}
