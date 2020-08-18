package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("backgroundCheck")
public class BackgroundCheck {
	
	@XStreamAlias("backgroundCheckStatus")
	private String backgroundCheckStatus;
	
	@XStreamAlias("verificationDate")
	private String verificationDate;
	
	@XStreamAlias("reverificationDate")
	private String reverificationDate;
	
	@XStreamAlias("requestDate")
	private String requestDate;

	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}

	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}

	public String getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}

	public String getReverificationDate() {
		return reverificationDate;
	}

	public void setReverificationDate(String reverificationDate) {
		this.reverificationDate = reverificationDate;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	


	
}
