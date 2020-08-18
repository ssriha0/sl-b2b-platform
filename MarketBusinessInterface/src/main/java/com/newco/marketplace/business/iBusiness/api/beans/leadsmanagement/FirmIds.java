package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmIds")
public class FirmIds {
	@XStreamImplicit(itemFieldName = "FirmId")
	private List<String>firmId;

	public List<String> getFirmId() {
		return firmId;
	}

	public void setFirmId(List<String> firmId) {
		this.firmId = firmId;
	}
}
