package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("reasonList")
public class ReasonCodeBean {
	@XStreamImplicit(itemFieldName="reason")
	private List<ReasonCode> reasonList;

	public List<ReasonCode> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<ReasonCode> reasonList) {
		this.reasonList = reasonList;
	}
}
