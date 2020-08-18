package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ApprovedFirms")
public class ApprovedFirms {

	@XStreamImplicit(itemFieldName = "approvedFirm")
	private List<ApprovedInvalidFirm> approvedFirm;

	public List<ApprovedInvalidFirm> getApprovedFirm() {
		return approvedFirm;
	}

	public void setApprovedFirm(List<ApprovedInvalidFirm> approvedFirm) {
		this.approvedFirm = approvedFirm;
	}
}
