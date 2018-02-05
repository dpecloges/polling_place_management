package com.ots.dpel.global.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ots.dpel.global.errors.DpErrorResponse;
import com.ots.dpel.global.errors.ErrorMessage;
import com.ots.dpel.global.exceptions.DpRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * A convenient base class which provides centralized exception handling across
 * all exceptions generated from call to REST controllers of the application.
 * This class is responsible to convert the exceptions to appropriate messages
 * which will be sent back to user.
 * 
 * <p>
 * Helpful links:
 * <ul>
 * <li><a
 * href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">
 * Spring MVC exception Handling: Article by Paul Chapman</a></li>
 * <li><a href=
 * "http://stackoverflow.com/questions/5863472/spring-mvc-custom-scope-bean/5883270#5883270"
 * >Custom Flash Scope bean</a></li>
 * 
 * <li><a href=
 * "http://www.jayway.com/2013/02/03/improve-your-spring-rest-api-part-iii/#comment-3296"
 * >Improve Your Spring REST API : Article by Mattias Severson</a></li>
 * 
 * <li><a href=
 * "http://www.mkyong.com/spring-mvc/spring-mvc-exception-handling-example/"
 * >Spring MVC Exception Handling Example by MKyong</a></li>
 * 
 * <li><a href=
 * "http://stackoverflow.com/questions/14961869/performing-a-redirect-from-a-spring-mvc-exceptionhandler-method"
 * >Performing a redirect from a Spring MVC Exception Handler method</a></li>
 * </ul>
 * </p>
 *
 */
public class DpExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LogManager.getLogger(DpExceptionHandler.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * Handles the exceptions , producing a human readable and machine
	 * digestible JSON error response.
	 * 
	 * @param e
	 *            the exception thrown
	 * @param request
	 *            the http request which caused the exception
	 * @param response
	 *            http response which will be used to set HTTP status code.
	 * @param httpStatusCode
	 *            the http status code for this response.
	 *            <p>
	 *            You can use for convenience the {@link HttpServletResponse}
	 *            <code>SC_*</code> constants. For example
	 *            {@link HttpServletResponse#SC_INTERNAL_SERVER_ERROR}.
	 *            </p>
	 *            <p>
	 *            Valid status codes are those in the 2XX, 3XX, 4XX, and 5XX
	 *            ranges. Other status codes are treated as container specific.
	 *            </p>
	 * 
	 */
	protected ResponseEntity<Object> handleConflict(DpRuntimeException e,
                                                    WebRequest request, HttpStatus httpStatus) {

		// create a response object which encapsulates the errors
		DpErrorResponse errorResponse = prepareErrorResponse(e);
		errorResponse.setUser(request.getRemoteUser());

		String bodyOfResponse = stringifyErrorResponse(errorResponse);

		logger.debug("---- PREPARING JSON ERROR RESPONSE --- ");
		logger.debug("Error response string:{}", bodyOfResponse);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json; charset=utf-8");

		return handleExceptionInternal(e, bodyOfResponse, headers, httpStatus,
				request);
	}

	private String stringifyErrorResponse(DpErrorResponse resp) {
		String bodyOfResponse = null;

		try {
			bodyOfResponse = OBJECT_MAPPER.writeValueAsString(resp);
		} catch (JsonProcessingException jpe) {
			logger.error(
					"Error while creating response body of HTTP Error page: ",
					jpe);
			bodyOfResponse = "{\"error\":\"Unable to generate JSON response\"}";
		}

		return bodyOfResponse;
	}

	private DpErrorResponse prepareErrorResponse(DpRuntimeException ex) {
		List<ErrorMessage> errors = ex.getErrors();
		if (errors == null || errors.isEmpty()) {
			errors = new ArrayList<ErrorMessage>();
			ErrorMessage msg = new ErrorMessage();
			msg.setErrorMessage(ex.getMessage());
			errors.add(msg);
		}
		
		DpErrorResponse errorResponse = new DpErrorResponse();
		errorResponse.setDescription(ex.getDescription());
		errorResponse.setException(ex.getClass().getSimpleName());
		errorResponse.setErrors(errors);

		return errorResponse;
	}
}
