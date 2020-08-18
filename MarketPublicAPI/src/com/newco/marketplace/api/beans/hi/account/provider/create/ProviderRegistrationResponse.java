package com.newco.marketplace.api.beans.hi.account.provider.create;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XSD(name = "providerRegistrationResponse.xsd", path = "/resources/schemas/ums/")
@XStreamAlias("providerRegistrationResponse")
@XmlRootElement(name = "providerRegistrationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderRegistrationResponse  implements IAPIResponse{
    
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("firmId")
	private Integer firmId;
	
	@XStreamAlias("providerId")
	private Integer providerId;
	
	public Results getResults() {
		return results;
	}


	public void setResults(Results results) {
		this.results = results;
	}


	public Integer getFirmId() {
		return firmId;
	}


	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}


	public Integer getProviderId() {
		return providerId;
	}


	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
    public void setVersion(String version) {}
    public void setSchemaLocation(String schemaLocation) {}
    public void setNamespace(String namespace) {}
    public void setSchemaInstance(String schemaInstance) {}
}
