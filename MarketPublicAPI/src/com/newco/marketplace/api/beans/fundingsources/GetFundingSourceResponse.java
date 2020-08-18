package com.newco.marketplace.api.beans.fundingsources;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author ndixit
 * This a POJO for GetFundingSource response. 
 */
@XStreamAlias("getFundingSourceResponse")
public class GetFundingSourceResponse implements IAPIResponse {
	
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
	@XStreamAlias("bankAccounts")
	private BankAccounts bankAccounts;

	@OptionalParam
	@XStreamAlias("creditCards")
	private CreditCards creditCards;
	
	public GetFundingSourceResponse(){
		
	}
	public CreditCards getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(CreditCards creditCards) {
		this.creditCards = creditCards;
	}

	public GetFundingSourceResponse(Results results, BankAccounts bankAccounts, CreditCards creditCards) {
		this();
		this.setResults(results);
		this.setBankAccounts(bankAccounts);
		this.setCreditCards(creditCards);
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
	
	public BankAccounts getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(BankAccounts bankAccounts) {
		this.bankAccounts = bankAccounts;
	}


	
}
