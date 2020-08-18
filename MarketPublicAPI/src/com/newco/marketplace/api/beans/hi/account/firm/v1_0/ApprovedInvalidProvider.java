package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ApprovedInvalidProvider {
	
	@XStreamAlias("providerId")
	private Integer providerId;
	
	@XStreamAlias("message")
	private String message;
	
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
