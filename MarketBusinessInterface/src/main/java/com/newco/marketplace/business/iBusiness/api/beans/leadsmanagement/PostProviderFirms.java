package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmDetailList")
public class PostProviderFirms {

	@XStreamImplicit(itemFieldName = "FirmDetail")
	private List<PostFirmDetails> firmDetailsList;

	public List<PostFirmDetails> getFirmDetailsList() {
		return firmDetailsList;
	}

	public void setFirmDetailsList(List<PostFirmDetails> firmDetailsList) {
		this.firmDetailsList = firmDetailsList;
	}

}
