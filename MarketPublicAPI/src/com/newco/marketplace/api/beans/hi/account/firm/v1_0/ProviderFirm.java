package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerFirm")
public class ProviderFirm {
	
	@XStreamAlias("providerFirmId")
	private Integer providerFirmId;
	
	@XStreamAlias("providerFirmStatus")
	private ProviderFirmStatus providerFirmStatus;
	
	public Integer getProviderFirmId() {
		return providerFirmId;
	}

	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}

	public ProviderFirmStatus getProviderFirmStatus() {
		return providerFirmStatus;
	}

	public void setProviderFirmStatus(ProviderFirmStatus providerFirmStatus) {
		this.providerFirmStatus = providerFirmStatus;
	}

}
