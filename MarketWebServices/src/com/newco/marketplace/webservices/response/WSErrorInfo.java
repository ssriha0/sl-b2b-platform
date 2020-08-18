package com.newco.marketplace.webservices.response;
/**
 *  This class holds the code and the message for 
 *  errors or warnings
 *
 *@author     Siva
 *@created    January 4, 2008
 */
public class WSErrorInfo {
	private String code;
	private String message;
	public WSErrorInfo() {
		super();
	}
	public WSErrorInfo(String code, String message) {
		super();
		this.code = code;
		this.message = message;
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
