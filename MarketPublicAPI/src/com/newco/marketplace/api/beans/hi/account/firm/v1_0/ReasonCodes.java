package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("reasonCodes")
public class ReasonCodes {
	@XStreamImplicit(itemFieldName = "reasonCode")
	private List<String> reasonCode;

	public List<String> getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(List<String> reasonCode) {
		this.reasonCode = reasonCode;
	}

	

}
