package com.newco.marketplace.api.mobile.beans.Filter;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("markets")
public class MarketCriteria {

	@XStreamImplicit(itemFieldName="market")
	private List<Market> market;

	public List<Market> getMarket() {
		return market;
	}

	public void setMarket(List<Market> market) {
		this.market = market;
	}

	
}
