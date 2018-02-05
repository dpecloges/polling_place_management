package com.ots.dpel.common.core.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

public class MailTemplateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String subject;
    private String body;
    private String renderer;
    private boolean html;
    

    public MailTemplateDto() {
        
    }
    
    @QueryProjection
    public MailTemplateDto(Long id, String code, String subject, String body, String renderer, boolean html) {
        this.id = id;
        this.code = code;
        this.subject = subject;
        this.body = body;
        this.renderer = renderer;
        this.html = html;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

}
