package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing BuyerReference information.
 * @author Infosys
 *
 */

@XStreamAlias("buyerReference")
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyerReference {
	
	@XStreamAlias("refName")
	private String refName;
	
	@XStreamAlias("refValue")
	private String refValue;

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}


}
