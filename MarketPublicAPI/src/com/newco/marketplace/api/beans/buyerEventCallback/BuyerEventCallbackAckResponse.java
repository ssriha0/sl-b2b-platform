package com.newco.marketplace.api.beans.buyerEventCallback;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name="buyerEventCallbackAckResponse.xsd", path="/resources/schemas/buyerEventCallback/")
@XStreamAlias("buyerEventCallbackAckResponse")
public class BuyerEventCallbackAckResponse implements IAPIResponse{
	
	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	public BuyerEventCallbackAckResponse() {
	}

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;

	public BuyerEventCallbackAckResponse(Results results) {
		this.results = results;
	}

	@XStreamAlias("results")
	private Results results;
	
	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	@Override
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
