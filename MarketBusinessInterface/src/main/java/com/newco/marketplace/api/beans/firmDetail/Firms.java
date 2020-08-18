package com.newco.marketplace.api.beans.firmDetail;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firms")
@XmlAccessorType(XmlAccessType.FIELD)
public class Firms {
	
	@XStreamImplicit(itemFieldName="firmDetails")
	private List<FirmDetails> firmDetails;

	public List<FirmDetails> getFirmDetails() {
		return firmDetails;
	}

	public void setFirmDetails(List<FirmDetails> firmDetails) {
		this.firmDetails = firmDetails;
	}
	
	
}
