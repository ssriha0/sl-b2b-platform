package com.newco.marketplace.api.mobile.beans.eligibleProviders;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("eligibleProvider")
public class EligibleProvider {
	
	@XStreamAlias("resourceId")
	private Integer resourceId;
	
//	@XStreamAlias("acceptedResourceId")
//	private Integer acceptedResourceId;
	
	@XStreamAlias("providerFirstName")
	private String providerFirstName;
	
	@XStreamAlias("providerLastName")
	private String providerLastName;
	
	@XStreamAlias("distancefromSOLocation")
	private double distancefromSOLocation;
	
	@XStreamAlias("providerRespid")
	private Integer providerRespid;

	public double getDistancefromSOLocation() {
		return distancefromSOLocation;
	}

	public void setDistancefromSOLocation(double distancefromSOLocation) {
		this.distancefromSOLocation = distancefromSOLocation;
	}


//	public Integer getAcceptedResourceId() {
//		return acceptedResourceId;
//	}
//
//	public void setAcceptedResourceId(Integer acceptedResourceId) {
//		this.acceptedResourceId = acceptedResourceId;
//	}

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
	
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getProviderRespid() {
		return providerRespid;
	}

	public void setProviderRespid(Integer providerRespid) {
		this.providerRespid = providerRespid;
	}


	
}
