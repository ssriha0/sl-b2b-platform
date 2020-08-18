/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
@XSD(name="searchproviderResults.xsd", path="/resources/schemas/search/")
@XmlRootElement(name = "providerFirmResults")
@XStreamAlias("providerFirmResults")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderFirmResults implements IAPIResponse{
	
	
	 @XStreamAlias("xsi:schemaLocation")   
	 @XStreamAsAttribute()   
	private String schemaLocation;
	 
	 @XStreamAlias("xmlns")   
	 @XStreamAsAttribute()   
	private String namespace;
	 
	 @XStreamAlias("xmlns:xsi")   
	 @XStreamAsAttribute()   
	private String schemaInstance;
	 
	 @XStreamAlias("recordCount")   
	 @XStreamAsAttribute()   
	private Integer recordCount;
	
	@XStreamAlias("results")
	private Results results;
	
	@OptionalParam
	@XStreamImplicit(itemFieldName="companyList")
	@XStreamAlias("companyList")
	private List<ProviderCompany> companyList;
	
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public List<ProviderCompany> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<ProviderCompany> companyList) {
		this.companyList = companyList;
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

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	
}
