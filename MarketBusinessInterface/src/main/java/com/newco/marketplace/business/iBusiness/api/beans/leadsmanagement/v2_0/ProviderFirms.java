package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FirmDetailList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderFirms {
	@XStreamImplicit(itemFieldName = "FirmDetail")
	@XmlElement(name="FirmDetail")
	private List<FirmDetails> firmDetailsList;

	public List<FirmDetails> getFirmDetailsList() {
		return firmDetailsList;
	}

	public void setFirmDetailsList(List<FirmDetails> firmDetailsList) {
		this.firmDetailsList = firmDetailsList;
	}

	

	

}
