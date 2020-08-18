package com.newco.marketplace.api.beans.wallet;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("buyerWalletThresholdResponse")
public class BuyerWalletThresholdResponse  implements IAPIResponse {
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
	@XStreamAlias("active")
	private boolean active;
	
	@OptionalParam
	@XStreamAlias("amount")
	private double amount;
	

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BuyerWalletThresholdResponse () {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.schemaLocation = PublicAPIConstant.Wallet.BuyerWalletThreshold.SCHEMALOCATION;
		this.namespace = PublicAPIConstant.Wallet.BuyerWalletThreshold.NAMESPACE;
	}
	
	public BuyerWalletThresholdResponse(Results results){
		this.results = results;
	}
	
	public BuyerWalletThresholdResponse(Results results, boolean active,double amount) {
		this();
		this.results = results;
		this.active = active;
		this.amount = amount;
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
