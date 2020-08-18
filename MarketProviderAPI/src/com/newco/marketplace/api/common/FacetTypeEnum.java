/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-Oct-2009	MarketPublicAPI SHC			1.0
 * 
 * 
 */
package com.newco.marketplace.api.common;

/**
 * This defines enum types used as response sent to calling client.
 * These should be an excat match with "facetTypeEnum" element defined in 
 * searchCommon.xsd
 *  
 * @author priti
 *
 */
public enum FacetTypeEnum {

	PROVIDER_LANGUAGE("PROVIDER_LANGUAGE"),
	PROVIDER_DISTANCE("PROVIDER_DISTANCE"), 
	PROVIDER_RATINGS("PROVIDER_RATINGS");
	
	String value;
	
	private FacetTypeEnum(String value) {
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
