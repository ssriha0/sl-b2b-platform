package com.newco.marketplace.api.mobile.beans.UpdateServiceOrderFlag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "updateFlagResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "updateFlagResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XStreamAlias("updateFlagResponse")
public class UpdateServiceOrderFlagResponse implements IAPIResponse {
	
	
	// default constructor
	public UpdateServiceOrderFlagResponse() {
	}

	// overloaded constructor to set Results
	public UpdateServiceOrderFlagResponse(Results results) {
		this.results = results;
	}
	
	public static UpdateServiceOrderFlagResponse getInstanceForError(
			ResultsCode resultCode) {
		return new UpdateServiceOrderFlagResponse(Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
	}
	
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
