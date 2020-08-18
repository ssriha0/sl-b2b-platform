/*
 *	Date        Project    	  Author       	 Version
 * -----------  --------- 	-----------  	---------
 * 12-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 *
 */
package com.newco.marketplace.api.beans.account.buyer;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("getBuyerAccountResponse")
public class GetBuyerAccountResponse implements IAPIResponse {
	
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
	@XStreamAlias("buyerAccountDetails")
	private BuyerAccountDetails buyerAccountDetails;

	
	public BuyerAccountDetails getBuyerAccountDetails() {
		return buyerAccountDetails;
	}

	public void setBuyerAccountDetails(BuyerAccountDetails buyerAccountDetails)
	{
		this.buyerAccountDetails = buyerAccountDetails;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
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
	
	
}
