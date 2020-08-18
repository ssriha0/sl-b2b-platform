package com.servicelive.esb.integration;

public class DataException extends RuntimeException {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 4294773079493615270L;
	
	public DataException() { super(); }
	public DataException(String message) { super(message); }
	public DataException(Throwable cause) { super(cause); }
	public DataException(String message, Throwable cause) { super(message, cause); }
}
