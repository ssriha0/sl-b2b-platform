package com.newco.marketplace.api.beans.wallet;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("walletBalanceResponse")
public class WalletBalanceResponse  implements IAPIResponse {
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
	@XStreamAlias("pendingBalance")
	private double pendingBalance;
	
	@OptionalParam
	@XStreamAlias("projectBalance")
	private double projectBalance;
	
	@OptionalParam
	@XStreamAlias("availableBalance")
	private double availableBalance;

	public WalletBalanceResponse () {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.schemaLocation = PublicAPIConstant.Wallet.WalletBalance.SCHEMALOCATION;
		this.namespace = PublicAPIConstant.Wallet.WalletBalance.NAMESPACE;
	}
	
	public WalletBalanceResponse(Results results){
		this.results = results;
	}
	
	public WalletBalanceResponse(Results results, double pendingBalance,double projectBalance,double availableBalance) {
		this();
		this.results = results;
		this.pendingBalance = pendingBalance;		
		this.projectBalance = projectBalance;
		this.availableBalance = availableBalance;
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
	
	public Double getPendingBalance() {
		return pendingBalance;
	}

	public void setPendingBalance(Double pendingBalance) {
		this.pendingBalance = pendingBalance;
	}

	public Double getProjectBalance() {
		return projectBalance;
	}

	public void setProjectBalance(Double projectBalance) {
		this.projectBalance = projectBalance;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}	

	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}	
}
