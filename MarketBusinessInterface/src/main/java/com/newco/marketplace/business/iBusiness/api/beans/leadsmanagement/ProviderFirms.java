package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmDetailList")
public class ProviderFirms {
	@XStreamImplicit(itemFieldName = "FirmDetail")
	private List<FirmDetails> firmDetailsList;

	public List<FirmDetails> getFirmDetailsList() {
		return firmDetailsList;
	}

	public void setFirmDetailsList(List<FirmDetails> firmDetailsList) {
		this.firmDetailsList = firmDetailsList;
	}

	

	

}
