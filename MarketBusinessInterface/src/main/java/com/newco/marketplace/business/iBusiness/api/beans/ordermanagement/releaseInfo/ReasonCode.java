package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("reason")
public class ReasonCode {
	
	@XStreamAlias("reasonCodeId")
	private Integer reasonCodeId;
	
	@XStreamAlias("reasonCode")
	private String reasonCode;

	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
}
