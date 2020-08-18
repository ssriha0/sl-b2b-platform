package com.newco.marketplace.api.mobile.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("reasonCodesList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReasonCodesList {
	
	@XStreamImplicit(itemFieldName="reasonCode")
	private List<ReasonCode> reasonCode;

	public List<ReasonCode> getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(List<ReasonCode> reasonCode) {
		this.reasonCode = reasonCode;
	}

	

}
