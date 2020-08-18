package com.newco.marketplace.api.mobile.beans.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="deleteSOProviderPartResponse.xsd", path="/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "deleteSOProviderPartResponse")
@XStreamAlias("deleteSOProviderPartResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteSOProviderPartResponse implements IAPIResponse {

	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("results")
	private Results results;

	// default constructor
	public DeleteSOProviderPartResponse(){

	}
	
	public DeleteSOProviderPartResponse(String soId, Results results) {	
		this.soId = soId;
		this.results = results;
	}

	
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
