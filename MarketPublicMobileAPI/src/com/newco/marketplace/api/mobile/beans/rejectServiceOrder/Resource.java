package com.newco.marketplace.api.mobile.beans.rejectServiceOrder;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("resourceList")
public class Resource {
	@XStreamImplicit(itemFieldName="resourceId")
	private List<Integer> resourceId;

	public List<Integer> getResourceId() {
		return resourceId;
	}

	public void setResourceId(List<Integer> resourceId) {
		this.resourceId = resourceId;
	}
	
}
