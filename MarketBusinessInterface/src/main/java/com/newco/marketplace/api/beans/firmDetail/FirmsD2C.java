package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firms")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmsD2C {
	
	@XStreamImplicit(itemFieldName="firmDetails")
	private List<FirmD2CDetails> firmDetails;

	public List<FirmD2CDetails> getFirmDetails() {
		return firmDetails;
	}

	public void setFirmDetails(List<FirmD2CDetails> firmDetails) {
		this.firmDetails = firmDetails;
	}
}
