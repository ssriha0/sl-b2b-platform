/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
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
@XSD(name="providerResults.xsd", path="/resources/schemas/search/")
@XStreamAlias("providerResults")
public class ProviderResults implements IAPIResponse{
	
	
	 @XStreamAlias("xsi:schemaLocation")   
	 @XStreamAsAttribute()   
	private String schemaLocation;
	 
	 @XStreamAlias("xmlns")   
	 @XStreamAsAttribute()   
	private String namespace;
	 
	 @XStreamAlias("xmlns:xsi")   
	 @XStreamAsAttribute()   
	private String schemaInstance;
	
	
	 @XStreamAlias("pageSize")   
	 @XStreamAsAttribute()   
	private Integer pageSize;
	 
	 @XStreamAlias("pageNum")   
	 @XStreamAsAttribute()   
	private Integer pageNum;
	 
	 @XStreamAlias("recordCount")   
	 @XStreamAsAttribute()   
	private Integer recordCount;
	
	@XStreamAlias("results")
	private Results results;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="provider")
	private List<ProviderType> providerList;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="facet")
	private List<FacetType> facet;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public List<ProviderType> getProviderList() {
		return providerList;
	}

	public void setProviderList(List<ProviderType> providerList) {
		this.providerList = providerList;
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

	public List<FacetType> getFacet() {
		return facet;
	}

	public void setFacet(List<FacetType> facet) {
		this.facet = facet;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	
}
