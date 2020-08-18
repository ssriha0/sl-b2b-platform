package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("cancellationReasonCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class CancellationReasonCode {
	
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
