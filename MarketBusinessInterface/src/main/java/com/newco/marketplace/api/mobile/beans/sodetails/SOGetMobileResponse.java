package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the
 * SODetailsRetrieveService
 * 
 * @author Infosys
 * 
 */
@XSD(name="getSODetailsResponse.xsd", path="/resources/schemas/mobile/")
@XStreamAlias("soResponse")
@XmlRootElement(name="soResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOGetMobileResponse implements IAPIResponse {
	
	@XStreamAlias("results")
	@XmlElement(name="results")
	private Results results;
	
	@XStreamAlias("serviceOrder")
	@XmlElement(name="serviceOrder")
	private RetrieveServiceOrderMobile serviceOrder;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public RetrieveServiceOrderMobile getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(RetrieveServiceOrderMobile serviceOrder) {
		this.serviceOrder = serviceOrder;
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
