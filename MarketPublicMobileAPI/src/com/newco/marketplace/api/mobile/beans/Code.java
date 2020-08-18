package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;



@XStreamAlias("code")
@XmlAccessorType(XmlAccessType.FIELD)
public class Code {
	
	@XStreamAlias("reasonCodeId")
	private int reasonCodeId;

	@XStreamAlias("reasonCodeDescription")
	private String reasonCode;
	
	public Code() {}
	
	public Code(int reasonCodeId, String reasonCode) {
		this.reasonCodeId = reasonCodeId;
		this.reasonCode = reasonCode;
	}

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
