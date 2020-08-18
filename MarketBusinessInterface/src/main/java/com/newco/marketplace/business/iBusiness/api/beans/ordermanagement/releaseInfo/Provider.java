package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("assignedProvider")
public class Provider {
	
	@XStreamAlias("resourceName")
	private String	resourceName;
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@XStreamAlias("resourceId")
	private String	resourceId;


}
