package com.newco.marketplace.api.beans;

import com.newco.marketplace.beans.annotations.MaskedValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("result")
public class Result {

	@MaskedValue("0000")
	@XStreamAlias("code")
	@XStreamAsAttribute()
	private String code;

	@XStreamAlias("message")
	private String message;

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
