package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

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
@XSD(name="getSODetailsResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XStreamAlias("soResponse")
@XmlRootElement(name="soResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOGetMobileDetailsResponse implements IAPIResponse {
	
	@XStreamAlias("results")
	@XmlElement(name="results")
	private Results results;
	
	@XStreamAlias("serviceOrder")
	@XmlElement(name="serviceOrder")
	private RetrieveSODetailsMobile serviceOrder;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public RetrieveSODetailsMobile getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(RetrieveSODetailsMobile serviceOrder) {
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
