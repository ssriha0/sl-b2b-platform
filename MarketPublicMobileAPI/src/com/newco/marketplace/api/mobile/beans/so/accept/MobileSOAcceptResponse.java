package com.newco.marketplace.api.mobile.beans.so.accept;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the Mobile
 * MobileSOAccept Response
 * 
 * @author Infosys
 * 
 */
@XSD(name = "mobileSOAcceptResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "mobileSOAcceptResponse")
@XStreamAlias("mobileSOAcceptResponse")
public class MobileSOAcceptResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	// default constructor
	public MobileSOAcceptResponse() {
	}

	// overloaded constructor to set Results
	public MobileSOAcceptResponse(Results results) {
		this.results = results;
	}
	
	public static MobileSOAcceptResponse getInstanceForError(
			ResultsCode resultCode) {
		return new MobileSOAcceptResponse(Results.getError(
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
	}
}
