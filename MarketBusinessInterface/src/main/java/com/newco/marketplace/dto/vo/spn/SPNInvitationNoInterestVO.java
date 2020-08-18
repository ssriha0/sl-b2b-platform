package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;

public class SPNInvitationNoInterestVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2410958953991074184L;
	private String reasonId;
	private String reason;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	
}
