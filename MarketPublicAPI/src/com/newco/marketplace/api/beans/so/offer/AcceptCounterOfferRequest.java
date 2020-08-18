package com.newco.marketplace.api.beans.so.offer;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the AcceptCounterOfferRequest
 * @author Infosys
 *
 */
@XStreamAlias("acceptCounterOffer")
public class AcceptCounterOfferRequest {
	
	
	@XStreamAlias("type")
	private String  type;
	
	@XStreamAlias("providerResource")
	private String  providerResource;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProviderResource() {
		return providerResource;
	}

	public void setProviderResource(String providerResource) {
		this.providerResource = providerResource;
	}

	
}
