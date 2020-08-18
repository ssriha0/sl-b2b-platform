package com.newco.marketplace.api.beans.closedOrders;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "closedSOProvResponse.xsd", path = "/resources/schemas/pro/so")
@XmlRootElement(name = "getClosedsoResponse")
@XStreamAlias("getClosedsoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetrieveClosedOrdersResponse implements IAPIResponse {
	
	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("serviceorders")
	private ClosedServiceOrders serviceorders;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public ClosedServiceOrders getClosedServiceOrders() {
		return serviceorders;
	}

	public void setClosedServiceOrders(ClosedServiceOrders serviceorders) {
		this.serviceorders = serviceorders;
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
	




}
