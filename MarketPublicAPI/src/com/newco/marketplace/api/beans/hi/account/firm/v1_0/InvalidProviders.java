package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("invalidProviders")
public class InvalidProviders {

	@XStreamImplicit(itemFieldName = "invalidProvider")
	private List<ApprovedInvalidProvider> invalidProvider;

	public List<ApprovedInvalidProvider> getInvalidProvider() {
		return invalidProvider;
	}

	public void setInvalidProvider(List<ApprovedInvalidProvider> invalidProvider) {
		this.invalidProvider = invalidProvider;
	}

	

}
