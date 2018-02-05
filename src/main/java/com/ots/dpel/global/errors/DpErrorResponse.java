package com.ots.dpel.global.errors;

import java.util.List;

/**
 * Holds the information that is passed in the body of an HTTP error response.
 *
 */
public class DpErrorResponse {
	private String description;
	private String user;
	private List<ErrorMessage> errors;
	private String exception;

	public DpErrorResponse() {
	}

	public DpErrorResponse(List<ErrorMessage> errors, String user,
                           String exception, String description) {
		this.description = description;
		this.user = user;
		this.errors = errors;
		this.exception = exception;
	}

	/**
	 * Returns the description of the exception thrown.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the exception thrown.
	 */
	public DpErrorResponse setDescription(String description) {
		this.description = description;

		return this;
	}

	/**
	 * Returns the remote user which fired this request, if any.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the remote user which fired this request, if any.
	 */
	public DpErrorResponse setUser(String user) {
		this.user = user;
		return this;
	}

	/**
	 * Returns user friendly error messages.
	 */
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	/**
	 * Sets the user friendly error messages.
	 */
	public DpErrorResponse setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
		return this;
	}

	/**
	 * Returns the simple name of the exception thrown in the application.
	 */
	public String getException() {
		return exception;
	}

	/**
	 * Sets the simple name of the exception thrown in the application.
	 */
	public DpErrorResponse setException(String exception) {
		this.exception = exception;
		return this;
	}

}
