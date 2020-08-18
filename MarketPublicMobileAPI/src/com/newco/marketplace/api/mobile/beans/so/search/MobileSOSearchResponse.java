package com.newco.marketplace.api.mobile.beans.so.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProviders;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f search SO for mobile
*
*/
@XSD(name="mobileSearchSOResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "mobileSOSearchResponse")
@XStreamAlias("mobileSOSearchResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileSOSearchResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("totalOrderCount")
	private Integer totalOrderCount;
	
	@XStreamAlias("totalOrderCountFetched")
	private Integer totalOrderCountFetched;
	
	@XStreamAlias("orderDetails")
	private OrderDetails orderDetails;

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
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

	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}

	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}

	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public Integer getTotalOrderCountFetched() {
		return totalOrderCountFetched;
	}

	public void setTotalOrderCountFetched(Integer totalOrderCountFetched) {
		this.totalOrderCountFetched = totalOrderCountFetched;
	}
	

}
