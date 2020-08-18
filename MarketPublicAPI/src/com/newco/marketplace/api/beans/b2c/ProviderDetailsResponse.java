package com.newco.marketplace.api.beans.b2c;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="providerDetailsResponse.xsd", path="/resources/schemas/b2c/")
@XmlRootElement(name = "providerDetailsResponse")
@XStreamAlias("providerDetailsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderDetailsResponse implements IAPIResponse {

	
	@XStreamAlias("results")
	private Results results;
	
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
