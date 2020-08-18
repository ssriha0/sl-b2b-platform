package com.servicelive.service.dataTokenization;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a generic bean class for storing Error information.
 * @author Infosys
 *
 */
@XStreamAlias("error")
public class ErrorResult {

	@MaskedValue("9999")
	@XStreamAlias("code")
	@XStreamAsAttribute() 
	private String code;
	
	@XStreamAlias("message")
	private String message;

	public ErrorResult() {
		
	}
	
	public ErrorResult(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
