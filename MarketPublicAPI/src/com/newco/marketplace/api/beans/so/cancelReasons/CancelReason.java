package com.newco.marketplace.api.beans.so.cancelReasons;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("cancelReason")
public class CancelReason {
	
	@XStreamAlias("code")
	private int code;
	
	@XStreamAlias("reason")
	private String reason;
	
	public CancelReason(){}
	
	public CancelReason(int code, String reason) {
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
