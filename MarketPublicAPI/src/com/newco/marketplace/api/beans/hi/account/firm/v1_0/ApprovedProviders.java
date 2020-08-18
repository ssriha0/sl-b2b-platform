package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("approvedProviders")
public class ApprovedProviders {

	@XStreamImplicit(itemFieldName = "approvedProvider")
	private List<ApprovedInvalidProvider> approvedProvider;

	public List<ApprovedInvalidProvider> getApprovedProvider() {
		return approvedProvider;
	}

	public void setApprovedProvider(List<ApprovedInvalidProvider> approvedProvider) {
		this.approvedProvider = approvedProvider;
	}

	
}
