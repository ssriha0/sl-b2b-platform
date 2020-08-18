package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("reasonCodes")
public class ReasonCodes {

	@XStreamImplicit(itemFieldName="reasonCode")
	private List<ReasonCodeVO> reasonCode;

	public List<ReasonCodeVO> getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(List<ReasonCodeVO> reasonCode) {
		this.reasonCode = reasonCode;
	}

}
