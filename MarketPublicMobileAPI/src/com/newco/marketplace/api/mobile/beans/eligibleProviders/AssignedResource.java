package com.newco.marketplace.api.mobile.beans.eligibleProviders;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("assignedResource")
public class AssignedResource {
	
	@XStreamAlias("assignedResourceId")
	private Integer assignedResourceId;
	
	@XStreamAlias("assignedResourceName")
	private String assignedResourceName;

	

	public Integer getAssignedResourceId() {
		return assignedResourceId;
	}

	public void setAssignedResourceId(Integer assignedResourceId) {
		this.assignedResourceId = assignedResourceId;
	}

	public String getAssignedResourceName() {
		return assignedResourceName;
	}

	public void setAssignedResourceName(String assignedResourceName) {
		this.assignedResourceName = assignedResourceName;
	}
	
	
}
