package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("LeadFirmStatus")
public class LeadFirmStatus {
	
	@XStreamAlias("FirmId")
	private String firmId;
	
	@XStreamAlias("FirmStatus")
	private String firmStatus;
	

	public String getFirmStatus() {
		return firmStatus;
	}

	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

}