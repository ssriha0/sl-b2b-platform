package com.newco.marketplace.api.beans.wallet;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name = "addFundsToWalletResponse.xsd", path = "/resources/schemas/wallet/")
@XStreamAlias("addFundsToWalletResponse")
public class AddFundsToWalletResponse  implements IAPIResponse {
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
	
	@XStreamAlias("projectBalance")
	private Double projectBalance;
	
	@XStreamAlias("pendingBalance")
	private Double pendingBalance;
	
	@XStreamAlias("availableBalance")
	private Double availableBalance;

	public AddFundsToWalletResponse () {	
		
	}
	
	public AddFundsToWalletResponse(Results results) {
		this();
		this.results = results;	
		this.pendingBalance = 0.0;
		this.projectBalance = 0.0;
		this.availableBalance = 0.0;
	}
	
	public AddFundsToWalletResponse(Results results, Double pendingBalance, Double projectBalance, Double availableBalance) {
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
