package com.newco.marketplace.api.mobile.beans.rescheduleRespond;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "rescheduleRespondRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "rescheduleRespondRequest")
@XStreamAlias("rescheduleRespondRequest")
public class RescheduleRespondRequest {

	@XStreamAlias("responseType")
	private String responseType;
	
	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
