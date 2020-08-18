package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name="updateSOCompletionResponse.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name = "updateSOCompletionResponse")
@XStreamAlias("updateSOCompletionResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateSOCompletionResponse implements IAPIResponse {

	
	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("results")
	private Results results;



	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
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
