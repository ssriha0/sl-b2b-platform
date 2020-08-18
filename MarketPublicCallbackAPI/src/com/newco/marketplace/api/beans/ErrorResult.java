package com.newco.marketplace.api.beans;

import com.newco.marketplace.beans.annotations.MaskedValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

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
