package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerFirmStatus")
public class ProviderFirmStatus {
	
	@XStreamAlias("firmStatus")
	private String firmStatus;
	
	@XStreamAlias("emailIndicator")
	private String emailIndicator;
	
	@XStreamAlias("reasonCodes")
	private ReasonCodes reasonCodes;

	public String getFirmStatus() {
		return firmStatus;
	}

	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}

	public ReasonCodes getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(ReasonCodes reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public String getEmailIndicator() {
		return emailIndicator;
	}

	public void setEmailIndicator(String emailIndicator) {
		this.emailIndicator = emailIndicator;
	}
}
