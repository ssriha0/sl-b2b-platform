/*package com.newco.marketplace.api.mobile.beans.submitReschedule;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "mobileSOSubmitRescheduleResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "submitRescheduleResponse")
@XStreamAlias("submitRescheduleResponse")
public class MobileSOSubmitRescheduleResponse implements IAPIResponse{
	@XStreamAlias("results")
	private Results results;

	// default constructor
	public MobileSOSubmitRescheduleResponse() {
	}

	// overloaded constructor to set Results
	public MobileSOSubmitRescheduleResponse(Results results) {
		this.results = results;
	}
	
	*//**
	 * @return the results
	 *//*
	public Results getResults() {
		return results;
	}

	*//**
	 * @param results
	 *            the results to set
	 *//*
	public void setResults(Results results) {
		this.results = results;
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
	public static MobileSOSubmitRescheduleResponse getInstanceForError(
			ResultsCode resultCode) {
		return new MobileSOSubmitRescheduleResponse(Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
	}

	
	
}
*/