package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerStatus")
public class ProviderStatus {
	
	@XStreamAlias("emailIndicator")
	private String emailIndicator;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("reasonCodes")
	private ReasonCodes reasonCodes;

	public String getEmailIndicator() {
		return emailIndicator;
	}

	public void setEmailIndicator(String emailIndicator) {
		this.emailIndicator = emailIndicator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ReasonCodes getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(ReasonCodes reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	
}
