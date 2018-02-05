package com.ots.dpel.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    private static final String RESOURCES_ID = "dpel_res";
    
    @Autowired
    private ResourceServerTokenServices defaultTokenServices;
    
    @Autowired
    private CorsFilter corsFilter;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsFilter, AbstractPreAuthenticatedProcessingFilter.class);
        http.authorizeRequests()
                .antMatchers("/auth/registration/find/**").permitAll()
                .antMatchers("/auth/registration/register").permitAll()
                // .antMatchers("/us/volunteer/**").permitAll()
                // .antMatchers("/us/user/**").permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable();
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.resourceId(RESOURCES_ID);
        config.tokenServices(defaultTokenServices);
    }
    
}
