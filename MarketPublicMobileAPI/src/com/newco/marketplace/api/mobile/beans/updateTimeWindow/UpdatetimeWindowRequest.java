package com.newco.marketplace.api.mobile.beans.updateTimeWindow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "updateTimeWindowRequest.xsd", path = "/resources/schemas/mobile/v3_1/")
@XmlRootElement(name = "soUpdateTimeWindowRequest")
@XStreamAlias("soUpdateTimeWindowRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdatetimeWindowRequest {
	
	 @XStreamAlias("startTime")   
	 private String startTime;
	 
	 @XStreamAlias("endTime")   
	 private String endTime;
	 
	 @XStreamAlias("eta")   
	 private String eta;
	 
	 @XStreamAlias("customerConfirmedInd")   
	 private Boolean customerConfirmedInd;
	 
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public Boolean getCustomerConfirmedInd() {
		return customerConfirmedInd;
	}

	public void setCustomerConfirmedInd(Boolean customerConfirmedInd) {
		this.customerConfirmedInd = customerConfirmedInd;
	}

}
