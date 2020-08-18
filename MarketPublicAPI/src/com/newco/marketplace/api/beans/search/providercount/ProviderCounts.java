/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providercount;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.skillTree.Categories;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the providerResults
 * @author Infosys
 *
 */
@XSD(name="providerCounts.xsd", path="/resources/schemas/search/")
@XStreamAlias("providerCounts")
public class ProviderCounts implements IAPIResponse{
	
	
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
	
	@OptionalParam
	@XStreamAlias("locations")
	private Locations locations;
	
	@OptionalParam
	@XStreamAlias("categories")
	private Categories categories;
	
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
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

	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public Locations getLocations() {
		return locations;
	}

	public void setLocations(Locations locations) {
		this.locations = locations;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}
}
