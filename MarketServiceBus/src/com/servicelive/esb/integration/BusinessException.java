package com.servicelive.esb.integration;

public class BusinessException extends RuntimeException {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -3130103561354661072L;

	public BusinessException() { super(); }
	public BusinessException(String message) { super(message); }
	public BusinessException(Throwable cause) { super(cause); }
	public BusinessException(String message, Throwable cause) { super(message, cause); }
}
