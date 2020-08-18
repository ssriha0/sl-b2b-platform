package com.newco.marketplace.api.beans.so.search;

import java.util.List;

import com.newco.marketplace.api.beans.so.OrderStatus;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing search results information for 
 * the SOSearchService
 * @author Infosys
 *
 */
@XStreamAlias("searchResults")
public class SearchResults {

	@XStreamImplicit(itemFieldName="orderstatus")
	private  List<OrderStatus> orderstatus;

	public List<OrderStatus> getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(List<OrderStatus> orderstatus) {
		this.orderstatus = orderstatus;
	}
}
