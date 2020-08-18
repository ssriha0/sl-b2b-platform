package com.newco.marketplace.api.beans.hi.account.firm.update.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="updateFirmAccountResponse.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name = "updateFirmAccountResponse")
@XStreamAlias("updateFirmAccountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateFirmAccountResponse implements IAPIResponse {

	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("firmId")
	private String firmId;
	
	@XStreamAlias("providerId")
	private String providerId;
	
	public Results getResults() {
		return results;
	}


	public void setResults(Results results) {
		this.results = results;
	}

	
	
	public String getFirmId() {
		return firmId;
	}


	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}


	public String getProviderId() {
		return providerId;
	}


	public void setProviderId(String providerId) {
		this.providerId = providerId;
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
