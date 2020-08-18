package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("reason")
public class ReasonCode {
	
	@XStreamAlias("reasonCodeId")
	private int reasonCodeId;

	@XStreamAlias("reasonCode")
	private String reasonCode;

	public int getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(int reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
}
