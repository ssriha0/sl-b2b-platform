package com.newco.marketplace.api.beans.so.retrieve;

import java.util.List;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("soSpendLimitHistory")
public class SOSpendLimitHistory {
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("currentPrice")
	private String currentPrice;
	
	@XStreamImplicit(itemFieldName="spendLimitHistory")
	private List<SpendLimitHistory> spendLimitIncreaseHistoryList;
	
   

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public List<SpendLimitHistory> getSpendLimitIncreaseHistoryList() {
		return spendLimitIncreaseHistoryList;
	}

	public void setSpendLimitIncreaseHistoryList(
			List<SpendLimitHistory> spendLimitIncreaseHistoryList) {
		this.spendLimitIncreaseHistoryList = spendLimitIncreaseHistoryList;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
	
}
