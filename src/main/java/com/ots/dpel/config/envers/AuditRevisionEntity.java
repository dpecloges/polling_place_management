package com.ots.dpel.config.envers;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@RevisionEntity(AuditRevisionListener.class)
@Entity
@Table(name = "CM_REVISION", schema = "CR")
public class AuditRevisionEntity {
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revisionSequence")
    @SequenceGenerator(name = "revisionSequence", sequenceName = "SE_REVISION", schema = "CR", allocationSize = 1)
    @Column(name = "N_ID")
    private Long id;

    @RevisionTimestamp
    @Column(name = "DT_TIMESTAMP")
    private Date timestamp;

    @Column(name = "N_USER_ID")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}