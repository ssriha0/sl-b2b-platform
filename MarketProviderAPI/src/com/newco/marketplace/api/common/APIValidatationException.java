package com.newco.marketplace.api.common;

public class APIValidatationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

	public APIValidatationException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public APIValidatationException(String message) {
		this(ResultsCode.GENERIC_ERROR.getCode(), message);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
