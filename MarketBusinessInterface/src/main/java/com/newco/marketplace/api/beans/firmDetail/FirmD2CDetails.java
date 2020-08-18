package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("firmDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmD2CDetails extends FirmDetails {
	
	@XStreamAlias("primaryIndustry")
	private String primaryIndustryDesc;

	public String getPrimaryIndustryDesc() {
		return primaryIndustryDesc;
	}

	public void setPrimaryIndustryDesc(String primaryIndustryDesc) {
		this.primaryIndustryDesc = primaryIndustryDesc;
	}

}
