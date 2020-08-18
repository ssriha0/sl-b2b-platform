package com.newco.marketplace.api.beans.so.complete;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("buyerRef")
public class BuyerRef {

	@XStreamAlias("referenceType")
	private String referenceType;
	
	@XStreamAlias("referenceValue")
	private String referenceValue;

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}
	
}
