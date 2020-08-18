package com.newco.marketplace.api.beans.so.post;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing specific provider information for 
 * the SOPostService
 * @author Infosys
 *
 */
@XStreamAlias("specificProviders")
public class SpecificProviders {
	
	@XStreamImplicit(itemFieldName="resourceID")
	private  List<Integer> resourceId;

	public List<Integer> getResourceId() {
		return resourceId;
	}

	public void setResourceId(List<Integer> resourceId) {
		this.resourceId = resourceId;
	}

}
