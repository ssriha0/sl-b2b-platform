package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ERRORCODE")
public class ErrorCode {
	
	@XStreamAlias("errorCode")
	private String code;
	
	@XStreamAlias("errorMessage")
	private String message;

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
