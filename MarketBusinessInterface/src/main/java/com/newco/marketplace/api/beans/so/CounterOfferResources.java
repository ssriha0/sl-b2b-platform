package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("resourceIds")
public class CounterOfferResources {

	@XStreamImplicit(itemFieldName="resourceId")
	private List<Integer> resourceId;

	public List<Integer> getResourceId() {
		return resourceId;
	}

	public void setResourceId(List<Integer> resourceId) {
		this.resourceId = resourceId;
	}

	
}
