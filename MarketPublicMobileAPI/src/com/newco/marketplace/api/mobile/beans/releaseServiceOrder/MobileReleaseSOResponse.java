package com.newco.marketplace.api.mobile.beans.releaseServiceOrder;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class to hold the response variables for the Release SO API
 * 
 * @author Infosys
 * 
 */
@XSD(name="mobileReleaseSOResponse.xsd", path="/resources/schemas/mobile/v3_1/")
@XStreamAlias("mobileReleaseSOResponse")
@XmlRootElement(name="mobileReleaseSOResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileReleaseSOResponse implements IAPIResponse {
	
	@XStreamAlias("results")
	private Results results; 

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
	public MobileReleaseSOResponse() {
	}

	// overloaded constructor to set Results
	public MobileReleaseSOResponse(Results results) {
		this.results = results;
	}
	
	public static MobileReleaseSOResponse getInstanceForError(ResultsCode resultCode) {
		return new MobileReleaseSOResponse(Results.getError(resultCode.getMessage(), resultCode.getCode()));
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
