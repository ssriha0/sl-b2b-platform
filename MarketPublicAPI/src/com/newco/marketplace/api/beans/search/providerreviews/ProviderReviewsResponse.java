/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	Public API  Shekhar Nirkhe	1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerreviews;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.Review;
import com.newco.marketplace.api.beans.search.providerProfile.Reviews;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the Reviews 
 * @author Shekhar Nirkhe
 *
 */
@XSD(name="providerReviewsResponse.xsd", path="/resources/schemas/search/")
@XStreamAlias("providerReviewsResponse")
public class ProviderReviewsResponse implements IAPIResponse{
	
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance;
	
	@XStreamAlias("results")
	private Results results;	
	 
	@XStreamAlias("reviews")
	private Reviews reviews;

	public ProviderReviewsResponse() {			
		reviews =  new Reviews();
	}
	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}


	public String getSchemaLocation() {
		return schemaLocation;
	}


	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}


	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public String getSchemaInstance() {
		return schemaInstance;
	}


	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}


	public Results getResults() {
		return results;
	}


	public void setResults(Results results) {
		this.results = results;
	}


	public Reviews getReviews() {
		return reviews;
	}


	public void setReviews(Reviews reviews) {
		this.reviews = reviews;
	}
}
