package com.ots.dpel.global.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * A special implementation of {@link ClientHttpRequestInterceptor}, which
 * intercepts all requests performed by {@link RestTemplate} and writes the URL
 * if log level is DEBUG.
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    
    private static final Logger logger = LoggerFactory
        .getLogger(LoggingRequestInterceptor.class);
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        
        // perform the request
        ClientHttpResponse response = execution.execute(request, body);
        
        // examine or log request and response
        log(request, body, response);
        
        return response;
    }
    
    private void log(HttpRequest request, byte[] body,
                     ClientHttpResponse response) throws IOException {
        
        if (!logger.isDebugEnabled()) {
            return;
        }
        
        logger.debug("Perform request to yproxy: {}", request.getURI()
            .toString());
    }
}