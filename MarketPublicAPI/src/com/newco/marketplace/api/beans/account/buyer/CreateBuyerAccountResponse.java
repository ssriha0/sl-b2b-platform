/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.account.buyer;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class serves as POJO object used for converting server response to POJO
 * object and then create XML response 
 * 
 *
 * @author priti
 *
 */
@XStreamAlias("createBuyerAccountResponse")
public class CreateBuyerAccountResponse implements IAPIResponse {

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
	@XStreamAlias("buyerId")
	private Integer buyerId;

	@OptionalParam
	@XStreamAlias("buyerResourceId")
	private Integer buyerResourceId;
	
	public Integer getBuyerResourceId() {
		return buyerResourceId;
	}
	public void setBuyerResourceId(Integer buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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
	
}
