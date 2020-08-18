package com.newco.marketplace.api.common;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "GenericApiResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "response")
@XStreamAlias("response")
public class GenericAPIResponse implements IAPIResponse{
	
	@XStreamAlias("results")
	private Results results;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	// default constructor
	public GenericAPIResponse() {
	}

	// overloaded constructor to set Results
	public GenericAPIResponse(Results results) {
		this.results = results;
	}
	
	public static GenericAPIResponse getInstanceForError(
			ResultsCode resultCode) {
		return new GenericAPIResponse(Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
	}

	public void setVersion(String version) {
		// Auto-generated method stub
	}

	public void setSchemaLocation(String schemaLocation) {
		// Auto-generated method stub
	}

	public void setSchemaInstance(String schemaInstance) {
		// Auto-generated method stub
	}

	public void setNamespace(String namespace) {
		// Auto-generated method stub
	}}
