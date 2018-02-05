package com.ots.dpel.config.security;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class DpTokenEnhancer implements TokenEnhancer {
    
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        
        DpUserDetailsDTO principal = (DpUserDetailsDTO) authentication.getPrincipal();
        
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("id", principal.getId());
        additionalInfo.put("fullName", principal.getFullName());
        additionalInfo.put("electionCenterId", principal.getElectionCenterId());
        additionalInfo.put("electionCenterName", principal.getElectionCenterName());
        additionalInfo.put("electionCenterDisplayName", principal.getElectionCenterDisplayName());
        additionalInfo.put("electionDepartmentId", principal.getElectionDepartmentId());
        additionalInfo.put("electionDepartmentName", principal.getElectionDepartmentName());
        additionalInfo.put("electionDepartmentDisplayName", principal.getElectionDepartmentDisplayName());
        additionalInfo.put("electionProcedureId", principal.getElectionProcedureId());
        additionalInfo.put("electionProcedureRound", principal.getElectionProcedureRound());
        
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        
        return accessToken;
    }
}
