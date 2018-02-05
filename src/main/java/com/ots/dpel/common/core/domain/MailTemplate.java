package com.ots.dpel.common.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mailtemplate", schema = "dp")
public class MailTemplate {
    
    public enum MailTemplateCode {
        /**
         * Μήνυμα ενεργοποίησης χρήστη με σύνδεσμο προς σελίδα ενεργοποίησης, στην
         * οποία ο χρήστης μπορεί να εισάγει τον προσωπικό του κωδικό και να ολοκληρώσει
         * τη διαδικασία του registration.
         */
        USER_ACTIVATION,
        
        /**
         * Μήνυμα για τον εκ νέου καθορισμό κωδικού εισαγωγής χρήστη, για
         * διαδικασίες τύπου "ο χρήστης έχει ξεχάσει τον κωδικό του".
         */
        USER_PASSWORD_RESET
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    private Long id;
    
    @Column(name = "v_code")
    private String code;

    @Column(name = "v_subject")
    private String subject;

    @Column(name = "v_body")
    private String body;

    @Column(name = "v_renderer")
    private String renderer;

    @Column(name = "n_html")
    private boolean html;

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
