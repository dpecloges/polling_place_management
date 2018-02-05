package com.ots.dpel.global.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

/**
 * Hibernate sequence generator
 * Αν σε νέο entity που θα γίνεται insert υπάρχει ορισμένο το id δε γίνεται κλήση στο sequence
 * Εάν δεν υπάρχει ορισμενο το id η κλήση στο sequence εκτελείται κανονικά
 */
public class OptionalSequenceStyleGenerator extends SequenceStyleGenerator {
    
    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
    
}