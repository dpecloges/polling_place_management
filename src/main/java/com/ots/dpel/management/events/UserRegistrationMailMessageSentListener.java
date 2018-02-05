package com.ots.dpel.management.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ots.dpel.auth.dto.UserRegistrationMailMessageDto;
import com.ots.dpel.common.events.MailMessageSentEvent;
import com.ots.dpel.management.core.domain.Contribution;
import com.ots.dpel.management.core.enums.ContributionStatus;
import com.ots.dpel.management.persistence.ContributionRepository;

/**
 * Listener για events τύπου {@link MailMessageSentEvent}
 * που αφορούν μηνύματα ενεργοποίησης χρηστών.
 * 
 * @author ktzonas
 */
@Component
public class UserRegistrationMailMessageSentListener {
    
    private Logger logger = LogManager.getLogger(getClass());
    
    @Autowired
    private ContributionRepository contributionRepository;

    @SuppressWarnings("unchecked")
    @EventListener(MailMessageSentEvent.class)
    @Transactional(transactionManager = "txMgr")
    public void handleMailMessageSentEvent(MailMessageSentEvent event) {
        
        Object source = event.getSource();
        
        if (!isSourceValid(source)) {
            return;
        }
        
        List<UserRegistrationMailMessageDto> mailMessageDtos = 
            (List<UserRegistrationMailMessageDto>) source;
        
        List<Long> contributionIds = extractContributionIds(mailMessageDtos);
        
        markEmailsSent(contributionIds);
    }
    
    private boolean isSourceValid(Object source) {
        if (source == null) {
            return false;
        }
        
        if (!(source instanceof List)) {
            return false;
        }
        
        List<?> list = (List<?>) source;
        if (list.isEmpty()) {
            return false;
        }
        
        Object listItem = list.get(0);
        if (!(listItem instanceof UserRegistrationMailMessageDto)) {
            return false;
        }
        
        return true;
    }
    
    private List<Long> extractContributionIds(
            List<UserRegistrationMailMessageDto> mailMessageDtos) {
        
        List<Long> contributionIds = new ArrayList<>();
        
        for (UserRegistrationMailMessageDto mailMessageDto: mailMessageDtos) {
            Long contributionId = mailMessageDto.getContributionId();
            if (contributionId != null) {
                contributionIds.add(contributionId);
            }
        }
        
        return contributionIds;
    } 
    
    private void markEmailsSent(List<Long> contributionIds) {
        
        if (CollectionUtils.isEmpty(contributionIds)) {
            return;
        }
        
        List<Contribution> contributions = this.contributionRepository.findByIdIn(contributionIds);
        for (Contribution contribution: contributions) {
            contribution.setStatus(ContributionStatus.EMAIL_SENT);
            contribution.setEmailSentDate(new Date());

            logger.trace("Marked contribution {} as EMAIL_SENT", contribution.getId());
        }
        
        logger.info("Marked {} contributions as EMAIL_SENT", contributionIds.size());
    }
}
