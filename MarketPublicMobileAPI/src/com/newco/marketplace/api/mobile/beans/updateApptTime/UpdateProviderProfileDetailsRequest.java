package com.newco.marketplace.api.mobile.beans.updateApptTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="UpdateProviderProfileDetailsRequest.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name="updateProviderProfileDetailsRequest")
@XStreamAlias("updateProviderProfileDetailsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateProviderProfileDetailsRequest {

	@XStreamAlias("mobileNo")
	private String mobileNo;
	UpdateProviderProfileDetailsRequest(){
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
}
