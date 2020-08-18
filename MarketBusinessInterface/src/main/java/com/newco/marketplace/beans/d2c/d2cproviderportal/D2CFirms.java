package com.newco.marketplace.beans.d2c.d2cproviderportal;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("firms")
@XmlAccessorType(XmlAccessType.FIELD)
public class D2CFirms {
	@XStreamImplicit(itemFieldName="firmDetails")
	private List<D2CFirmDetails> firmDetails;

	public List<D2CFirmDetails> getFirmDetails() {
		return firmDetails;
	}

	public void setFirmDetails(List<D2CFirmDetails> firmDetails) {
		this.firmDetails = firmDetails;
	}

	
}
