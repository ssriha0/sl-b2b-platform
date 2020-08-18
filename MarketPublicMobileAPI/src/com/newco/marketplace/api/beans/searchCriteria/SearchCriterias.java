package com.newco.marketplace.api.beans.searchCriteria;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/07/03
 * the response for SOGetSearchCriteria
 *
 */
@XStreamAlias("searchCriterias")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchCriterias {
	
	@XStreamAlias("markets")
	private Markets markets;
	
	@XStreamAlias("serviceProviders")
	private ServiceProviders serviceProviders;
	
	@XStreamAlias("orderStatuses")
	private OrderStatuses orderStatuses;
	
	public Markets getMarkets() {
		return markets;
	}
	public void setMarkets(Markets markets) {
		this.markets = markets;
	}
	public ServiceProviders getServiceProviders() {
		return serviceProviders;
	}
	public void setServiceProviders(ServiceProviders serviceProviders) {
		this.serviceProviders = serviceProviders;
	}
	public OrderStatuses getOrderStatuses() {
		return orderStatuses;
	}
	public void setOrderStatuses(OrderStatuses orderStatuses) {
		this.orderStatuses = orderStatuses;
	}
	
	
}
