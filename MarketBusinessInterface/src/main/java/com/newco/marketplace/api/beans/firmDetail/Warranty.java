package com.newco.marketplace.api.beans.firmDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("warranty")
@XmlAccessorType(XmlAccessType.FIELD)
public class Warranty {

	@XStreamAlias("warrantyType")
	private String warrantyType;
	
	@XStreamAlias("warrantyValue")
	private String warrantyValue;
	
	@XStreamAlias("warrantyDays")
	private String warrantyDays;

	public String getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}

	public String getWarrantyValue() {
		return warrantyValue;
	}

	public void setWarrantyValue(String warrantyValue) {
		this.warrantyValue = warrantyValue;
	}

	public String getWarrantyDays() {
		return warrantyDays;
	}

	public void setWarrantyDays(String warrantyDays) {
		this.warrantyDays = warrantyDays;
	}

	

}
