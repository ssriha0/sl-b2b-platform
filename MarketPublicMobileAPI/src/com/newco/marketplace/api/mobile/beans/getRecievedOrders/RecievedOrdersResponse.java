package com.newco.marketplace.api.mobile.beans.getRecievedOrders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.EligibleProviders;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f recieved orders
*
*/
@XSD(name="recievedOrdersResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "recievedOrdersResponse")
@XStreamAlias("recievedOrdersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecievedOrdersResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("recievedServiceOrders")
	private RecievedServiceOrders recievedServiceOrders;

	
	
	/**
	 * @return the recievedServiceOrders
	 */
	public RecievedServiceOrders getRecievedServiceOrders() {
		return recievedServiceOrders;
	}

	/**
	 * @param recievedServiceOrders the recievedServiceOrders to set
	 */
	public void setRecievedServiceOrders(RecievedServiceOrders recievedServiceOrders) {
		this.recievedServiceOrders = recievedServiceOrders;
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
}
