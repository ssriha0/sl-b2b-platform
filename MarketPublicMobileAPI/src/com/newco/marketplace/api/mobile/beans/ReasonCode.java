package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("reasonCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReasonCode {

	@XStreamAlias("reasonCodeType")
	public String reasonCodeType;

	@XStreamAlias("codes")
	private Codes codes;

	public String getReasonCodeType() {
		return reasonCodeType;
	}

	public void setReasonCodeType(String reasonCodeType) {
		this.reasonCodeType = reasonCodeType;
	}

	public Codes getCodes() {
		return codes;
	}

	public void setCodes(Codes codes) {
		this.codes = codes;
	}

}
