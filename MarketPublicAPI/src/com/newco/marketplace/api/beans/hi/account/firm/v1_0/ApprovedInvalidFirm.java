package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ApprovedInvalidFirm {
	
	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("message")
	private String message;
	
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
