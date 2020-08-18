package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerDetails")
public class EstimateProviderDetails {

	@XStreamAlias("resourceId")
	private Integer resourceId;
	
	@XStreamAlias("location")
	private EstimateLocation location;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public EstimateLocation getLocation() {
		return location;
	}

	public void setLocation(EstimateLocation location) {
		this.location = location;
	}
	
}
