package com.servicelive.common.transaction;

public class RetryableOperationException extends RuntimeException {

	/** generate serialVersionUID */
	private static final long serialVersionUID = 2662137615284818873L;

	public RetryableOperationException() {
		super();
	}
	
	public RetryableOperationException(String message) {
		super(message);
	}
	
	public RetryableOperationException(Throwable cause) {
		super(cause);
	}
	
	public RetryableOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
