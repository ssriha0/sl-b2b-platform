package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.mobile.common.ResultsCode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XSD(name = "addSOProviderPartRequest.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "addSOProviderPartRequest")
@XStreamAlias("addSOProviderPartRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddSOProviderPartRequest {
	
	@XStreamAlias("currentTripNo")
	private String currentTripNo;
	
	@XStreamAlias("tripStatus")
	private String tripStatus;

	
	@XStreamAlias("providerPartsList")
	private ProviderPartsList providerPartsList;
	/**Result of validation*/
	@XStreamOmitField
	private ResultsCode validationCode;	
	
	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	
	public String getCurrentTripNo() {
		return currentTripNo;
	}

	public void setCurrentTripNo(String currentTripNo) {
		this.currentTripNo = currentTripNo;
	}

	public ProviderPartsList getProviderPartsList() {
		return providerPartsList;
	}

	public void setProviderPartsList(ProviderPartsList providerPartsList) {
		this.providerPartsList = providerPartsList;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}


}
