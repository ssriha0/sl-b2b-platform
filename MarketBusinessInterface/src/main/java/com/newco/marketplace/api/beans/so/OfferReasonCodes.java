package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("reasonCodes")
public class OfferReasonCodes {

	@XStreamImplicit(itemFieldName="reasonCode")
	private List<Integer> reasonCode;

	public List<Integer> getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(List<Integer> reasonCode) {
		this.reasonCode = reasonCode;
	}

}
