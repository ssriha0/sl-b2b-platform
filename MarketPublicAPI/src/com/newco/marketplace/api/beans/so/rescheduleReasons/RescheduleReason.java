package com.newco.marketplace.api.beans.so.rescheduleReasons;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("rescheduleReason")
public class RescheduleReason {

	@XStreamAlias("code")
	private int code;
	
	@XStreamAlias("reason")
	private String reason;
	
	public RescheduleReason(){}
	
	public RescheduleReason(int code, String reason) {
		super();
		this.code = code;
		this.reason = reason;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
}
