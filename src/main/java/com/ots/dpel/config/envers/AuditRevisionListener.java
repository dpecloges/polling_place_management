package com.ots.dpel.config.envers;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditedRevisionEntity = (AuditRevisionEntity) revisionEntity;
        
        DpUserDetailsDTO userDetails = (DpUserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        auditedRevisionEntity.setUserId(userDetails.getId());
    }
}