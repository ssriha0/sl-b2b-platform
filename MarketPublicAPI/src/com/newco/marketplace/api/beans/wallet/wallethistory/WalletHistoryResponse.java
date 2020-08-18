/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 15-SEP-2009	KMSTRSUP      Infosys		 1.0
 * 
 * 
 */

package com.newco.marketplace.api.beans.wallet.wallethistory;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.newco.marketplace.api.common.IAPIResponse;

/**
 * This is a bean class for storing response information for 
 * the walletHistory Service
 * @author Infosys
 *
 */
@XStreamAlias("walletHistoryResponse")
public class WalletHistoryResponse  implements IAPIResponse {
	
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
	@XStreamAlias("walletHistoryResults")
	private WalletHistoryResults walletHistoryResults;
	
	@XStreamAlias("totalDeposit")
	private double totalDeposit;

	public WalletHistoryResponse () {	
		
	}
	
	public WalletHistoryResponse (Results results){
		this.results = results;
	}
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public WalletHistoryResults getWalletHistoryResults() {
		return walletHistoryResults;
	}

	public void setWalletHistoryResults(WalletHistoryResults walletHistoryResults) {
		this.walletHistoryResults = walletHistoryResults;
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

	public double getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(double totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

}
