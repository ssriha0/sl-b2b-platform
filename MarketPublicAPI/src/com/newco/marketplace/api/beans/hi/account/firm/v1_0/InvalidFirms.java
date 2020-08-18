package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("InvalidFirms")
public class InvalidFirms {

	@XStreamImplicit(itemFieldName = "invalidFirm")
	private List<ApprovedInvalidFirm> invalidFirm;

	public List<ApprovedInvalidFirm> getInvalidFirm() {
		return invalidFirm;
	}

	public void setInvalidFirm(List<ApprovedInvalidFirm> invalidFirm) {
		this.invalidFirm = invalidFirm;
	}

}
