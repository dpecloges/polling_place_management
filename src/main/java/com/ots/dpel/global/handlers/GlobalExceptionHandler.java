package com.ots.dpel.global.handlers;

import com.ots.dpel.global.errors.ExceptionResponse;
import com.ots.dpel.global.utils.ExceptionUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Handler για το σύνολο των Runtime Exceptions της εφαρμογής
 * <br/>Οι μέθοδοι περιλαμβάνουν τα ακόλουθα Exceptions
 * <ul>
 * <li>{@link HibernateJdbcException}</li>
 * <li>{@link JpaSystemException}</li>
 * <li>{@link DataIntegrityViolationException}</li>
 * <li>{@link Exception}</li>
 * </ul>
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    /**
     * Δημιουργία αντικειμένου response με τα βασικά στοιχεία του exception
     * @param ex      αντικείμενο exception
     * @param request αντικείμενο http request
     * @return
     */
    private ExceptionResponse getExceptionResponse(Exception ex, WebRequest request) {
        return ExceptionUtils.getExceptionResponse(ex, request, true);
    }
    
    @ExceptionHandler(value = {HibernateJdbcException.class})
    protected ResponseEntity<Object> handleHibernateJdbcException(HibernateJdbcException ex, WebRequest request) {
        
        ExceptionResponse response = this.getExceptionResponse(ex, request);
        
        //Μήνυμα SQL σφάλματος
        response.setSqlMessage(ex.getSQLException().getMessage());
        
        ThreadContext.put("sqlMessage", response.getSqlMessage());
        
        logger.warn(response.getSqlMessage());
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = {JpaSystemException.class})
    protected ResponseEntity<Object> handleJpaSystemException(JpaSystemException ex, WebRequest request) {
        
        GenericJDBCException genericJdbcException = (GenericJDBCException) ex.getCause();
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        //Μήνυμα SQL σφάλματος
        response.setSqlMessage(genericJdbcException.getSQLException().getMessage());
        
        ThreadContext.put("sqlMessage", response.getSqlMessage());
        
        logger.warn(response.getSqlMessage());
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        //Μήνυμα SQL σφάλματος
        response.setSqlMessage(ex.getCause().getCause().getMessage());
        
        ThreadContext.put("sqlMessage", response.getSqlMessage());
        
        logger.warn(response.getSqlMessage());
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        logger.warn("User: ".concat(response.getUsername()));
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.FORBIDDEN, request);
    }
    
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        String causeMessage = "";
        String causeDetails = "";
        boolean methodNullReturnObject = false;
        
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            
            //Πληροφορίες σχετικά με το είδος και το σημείο εμφάνισης του ContraintValidationException
            ConstraintDescriptor<?> constraintDescriptor = constraintViolation.getConstraintDescriptor();
            String annotationTypeName = ((ConstraintDescriptorImpl) constraintDescriptor).getAnnotationType().getName();
            String elementTypeName = ((ConstraintDescriptorImpl) constraintDescriptor).getElementType().name();
            String rootBeanClassName = ((ConstraintViolationImpl) constraintViolation).getRootBeanClass().getName();
            String propertyPathName = ((ConstraintViolationImpl) constraintViolation).getPropertyPath().toString();
            
            causeMessage += (causeMessage.isEmpty() ? "" : "\r\n") + constraintViolation.getMessage();
            causeDetails += (causeDetails.isEmpty() ? "" : "\r\n") +
                (rootBeanClassName == null ? "" : rootBeanClassName) + "." +
                (propertyPathName == null ? "" : propertyPathName);
            
            if (annotationTypeName.equals("javax.validation.constraints.NotNull") && elementTypeName.equals("METHOD")) {
                methodNullReturnObject = true;
            }
        }
        
        if (methodNullReturnObject) {
            response.setCauseMessage(causeMessage);
            response.setException(null);
            response.setCauseDetails(causeDetails);
        }
        
        ThreadContext.put("causeMessage", response.getCauseMessage());
        ThreadContext.put("causeDetails", response.getCauseDetails());
        
        logger.warn("User: ".concat(response.getUsername()));
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(null, response, headers, HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        //Το αναγνωριστικό του σφάλματος γίνεται localized
        response.setErrorMessage(messageSourceProvider.getMessage(ex.getMessage()));
        
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = {DataAccessException.class})
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        //Εξέταση και ενημέρωση της τιμής της παραμέτρου σύνδεσης με το Solr
        if (ex.getStackTrace()[0].getFileName().contains("Solr")) {
        }
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        
        ExceptionResponse response = getExceptionResponse(ex, request);
        
        logger.error("Error Id: ".concat(response.getErrorId()).concat(" - Message: ").concat(response.getErrorMessage()), ex);
        
        ThreadContext.clearAll();
        
        //Στοιχεία των headers του response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        
        return handleExceptionInternal(ex, response, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
}
