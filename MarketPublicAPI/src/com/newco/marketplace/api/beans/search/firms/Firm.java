package com.newco.marketplace.api.beans.search.firms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("firm")
@XmlAccessorType(XmlAccessType.FIELD)
public class Firm {
	
	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("firmName")
	private String firmName;
	
	@XStreamAlias("firmRating")
	private String firmRating;
	
	@XStreamAlias("serviceOfferingsList")
	private ServiceOfferingsList serviceOfferingList;
	

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmRating() {
		return firmRating;
	}

	public void setFirmRating(String firmRating) {
		this.firmRating = firmRating;
	}

	public ServiceOfferingsList getServiceOfferingList() {
		return serviceOfferingList;
	}

	public void setServiceOfferingList(ServiceOfferingsList serviceOfferingList) {
		this.serviceOfferingList = serviceOfferingList;
	}

}
