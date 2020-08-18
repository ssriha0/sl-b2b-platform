package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Reschedule information.
 * @author Infosys
 *
 */

@XStreamAlias("counterOfferDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class CounterOfferDetail {

	@XStreamAlias("resourceId")
	private Integer resourceId;
	
	@XStreamAlias("providerFirstName")
	private String providerFirstName;
	
	@XStreamAlias("providerLastName")
	private String providerLastName;
	
	@XStreamAlias("distanceFromBuyer")
	private Double distanceFromBuyer;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public Double getDistanceFromBuyer() {
		return distanceFromBuyer;
	}

	public void setDistanceFromBuyer(Double distanceFromBuyer) {
		this.distanceFromBuyer = distanceFromBuyer;
	}
	
}
