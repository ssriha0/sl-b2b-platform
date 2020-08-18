package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("GetProviderFirmDetailRequest")
public class GetProviderFirmDetailRequest {

	@XStreamAlias("FirmIds")
	private FirmIds firmIds;

	public FirmIds getFirmIds() {
		return firmIds;
	}

	public void setFirmIds(FirmIds firmIds) {
		this.firmIds = firmIds;
	}
	
}
