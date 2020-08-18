package com.newco.marketplace.exception;


/**
 * SL-15642
 * Exception when try to Assign an SO (ASSIGNMENT_TYPE=FIRM) which is already assigned.
 * */
public class AssignOrderException extends Exception {


	private static final long serialVersionUID = 165545L;

	public AssignOrderException(String message) {
		super(message);
	}

	public AssignOrderException(Throwable cause) {
		super(cause);
	}

	public AssignOrderException(String message, Throwable cause) {
		super(message, cause);
	}
}
