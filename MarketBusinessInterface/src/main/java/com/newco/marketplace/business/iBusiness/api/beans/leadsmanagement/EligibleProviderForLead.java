package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("EligibleProvider")
public class EligibleProviderForLead {
	
	@XStreamAlias("ResourceId")
	private Integer resourceId;
	
	@XStreamAlias("ProviderDistance")
	private double providerDistance;
	
	@XStreamAlias("ResourceFirstName")
	private String resFirstName;
	
	@XStreamAlias("ResourceLastName")
	private String resLastName;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public double getProviderDistance() {
		return providerDistance;
	}

	public void setProviderDistance(double providerDistance) {
		this.providerDistance = providerDistance;
	}

	public String getResFirstName() {
		return resFirstName;
	}

	public void setResFirstName(String resFirstName) {
		this.resFirstName = resFirstName;
	}

	public String getResLastName() {
		return resLastName;
	}

	public void setResLastName(String resLastName) {
		this.resLastName = resLastName;
	}



}
