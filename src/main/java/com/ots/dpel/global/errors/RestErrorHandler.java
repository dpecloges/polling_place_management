package com.ots.dpel.global.errors;

import com.google.common.collect.Multimap;
import com.ots.dpel.global.utils.MessageSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sdimitriadis
 */
@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {
    
    @Autowired
    public MessageSourceProvider messageSourceProvider;
    
    // @Resource
    // @Qualifier("roFieldNameMap")
    // public Multimap<String, Object> roFieldNameMap;
    //
    // @Resource
    // @Qualifier("mrFieldNameMap")
    // public Multimap<String, Object> mrFieldNameMap;
    
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest
        request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        
        ErrorReport validationErrorDto = processFieldErrors(fieldErrors);
        return new ResponseEntity<ErrorReport>(validationErrorDto, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    private ErrorReport processFieldErrors(List<FieldError> fieldErrors) {
        ErrorReport dto = new ErrorReport();
        
        for (FieldError fieldError : fieldErrors) {
            //Message
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            
            //Όνομα πεδίου
            String fieldName = fieldError.getField();
            
            //Ανάκτηση του λεκτικού του ονόματος του πεδίου από το mapping του ro ή του mr.
            //Αν δεν το βρει, προσθέτει το κενό.
            String fieldLabel = "";
            /*List<Object> roList = (List<Object>) roFieldNameMap.get(fieldName);
            if (roList != null && !roList.isEmpty()) {
                fieldLabel = roList.get(0).toString() + ": ";
            } else {
                List<Object> mrList = (List<Object>) mrFieldNameMap.get(fieldName);
                if (mrList != null && !mrList.isEmpty()) {
                    fieldLabel = mrList.get(0).toString() + ": ";
                }
            }*/
            
            //Όνομα πεδίου: Message
            localizedErrorMessage = fieldLabel + localizedErrorMessage;
            
            dto.addError(fieldName, localizedErrorMessage, null);
        }
        
        return dto;
    }
    
    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        String messageName = fieldError.getDefaultMessage();
        return messageSourceProvider.getMessage(messageName);
    }
}