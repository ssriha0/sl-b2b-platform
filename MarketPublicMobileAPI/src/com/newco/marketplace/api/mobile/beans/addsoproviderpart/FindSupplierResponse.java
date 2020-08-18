package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "findSupplierResponse.xsd", path = "/resources/schemas/mobile/v2_0/")
@XmlRootElement(name = "findSupplierResponse")
@XStreamAlias("findSupplierResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FindSupplierResponse implements IAPIResponse {
    
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("correlationId")
	private String correlationId;
	
	@XStreamAlias("responseCode")
	private String responseCode;
	
	@XStreamAlias("responseMessage")
	private String responseMessage;
	
	@XStreamAlias("messages")
	private  Messages messages;
	
	@XStreamAlias("suppliers")
	private Suppliers suppliers;
	
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
    public Suppliers getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Suppliers suppliers) {
		this.suppliers = suppliers;
	}

	public void setVersion(String version){}

	public void setSchemaLocation(String schemaLocation) {}

	public void setNamespace(String namespace) {}

	public void setSchemaInstance(String schemaInstance) {}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

}
