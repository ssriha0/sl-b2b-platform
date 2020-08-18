package com.newco.marketplace.api.beans.serviceOrderDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceOrder {
	
	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("serviceStartTime")
	private String serviceStartTime;
	
	@XStreamAlias("serviceEndTime")
	private String serviceEndTime;

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	
	
	
	

}
