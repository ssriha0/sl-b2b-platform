package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("marketList")
public class MarketFilterList {
	
	@XStreamImplicit(itemFieldName="markets")
	private List<MarketFilterVO> markets;

	public List<MarketFilterVO> getMarkets() {
		return markets;
	}

	public void setMarkets(List<MarketFilterVO> markets) {
		this.markets = markets;
	}
}
