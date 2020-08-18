package com.newco.marketplace.api.beans.fundingsources;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * @author ndixit
 *
 */
@XStreamAlias("getFundingSourceResponse")
public class CreateFundingSourceResponse implements IAPIResponse{
	
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

	@XStreamAlias("accountSourceType")
	private AccountSourceType accountSourceType;
	
	public CreateFundingSourceResponse() {
	}
	
	public CreateFundingSourceResponse(Results results) {
		this.setResults(results);
	}


	public AccountSourceType getAccountSourceType() {
		return accountSourceType;
	}

	public void setAccountSourceType(AccountSourceType accountSourceType) {
		this.accountSourceType = accountSourceType;
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
	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
	}

}
