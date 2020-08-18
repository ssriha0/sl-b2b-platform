/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing identification information.
 * @author Infosys
 *
 */
@XStreamAlias("identification")
public class IdentificationType {

	@XStreamAlias("id")
	private String resourceId;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
