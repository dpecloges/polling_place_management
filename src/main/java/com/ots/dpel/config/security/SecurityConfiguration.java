package com.ots.dpel.config.security;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Map;

@Configuration
// @Import(DpMethodSecurityConfiguration.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private static final String SIGNING_KEY = "|g}vh7YdDqU5zmRN";
    private static final Integer ENCODING_STRENGTH = 256;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private CorsFilter corsFilter;
    
    @Bean
    public org.springframework.security.authentication.encoding.PasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder(ENCODING_STRENGTH);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
    @Bean
    public CorsFilter corsFilter() {
        
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(environment.getProperty("cors.filter.allowed.origin"));
        config.addAllowedOrigin(environment.getProperty("cors.filter.allowed.origin2"));
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    
        httpSecurity.csrf().disable();
        
        httpSecurity.addFilterBefore(exceptionHandlerFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        httpSecurity.addFilterBefore(corsFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        converter.setAccessTokenConverter(getAuthenticationAccessTokenConverter());
        return converter;
    }
    
    private DefaultAccessTokenConverter getAuthenticationAccessTokenConverter() {
        return new DefaultAccessTokenConverter() {
            @Override
            public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
                OAuth2Authentication authentication = (OAuth2Authentication) super.extractAuthentication(map);
    
                DpOAuth2Authentication dpOAuth2Authentication =
                        new DpOAuth2Authentication(authentication.getOAuth2Request(), authentication.getUserAuthentication());
                
                DpUserDetailsDTO user = new DpUserDetailsDTO();
    
                user.setId(map.get("id") != null ? Long.valueOf(map.get("id").toString()) : null);
                user.setUsername(map.get("user_name") != null ? map.get("user_name").toString() : null);
                user.setFullName(map.get("fullName") != null ? map.get("fullName").toString() : null);
                user.setElectionCenterId(map.get("electionCenterId") != null ? Long.valueOf(map.get("electionCenterId").toString()) : null);
                user.setElectionCenterName(map.get("electionCenterName") != null ? (map.get("electionCenterName")).toString() : null);
                user.setElectionCenterDisplayName(map.get("electionCenterDisplayName") != null ? (map.get("electionCenterDisplayName")).toString() : null);
                user.setElectionDepartmentId(map.get("electionDepartmentId") != null ? Long.valueOf(map.get("electionDepartmentId").toString()) : null);
                user.setElectionDepartmentName(map.get("electionDepartmentName") != null ? (map.get("electionDepartmentName")).toString() : null);
                user.setElectionDepartmentDisplayName(map.get("electionDepartmentDisplayName") != null ? (map.get("electionDepartmentDisplayName")).toString() : null);
                
                dpOAuth2Authentication.setDpUserDetailsDTO(user);
                
                return dpOAuth2Authentication;
            }
        };
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    public ResourceServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
    
}