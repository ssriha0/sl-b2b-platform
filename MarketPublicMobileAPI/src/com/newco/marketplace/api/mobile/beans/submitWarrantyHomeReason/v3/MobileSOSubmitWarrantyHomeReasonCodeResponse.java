package com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.newco.marketplace.api.mobile.common.ResultsCode;

@XSD(name = "mobileSOSubmitWarrantyHomeReasonCodeResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "submitWarrantyHomeReasonCodeResponse")
@XStreamAlias("submitWarrantyHomeReasonCodeResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class MobileSOSubmitWarrantyHomeReasonCodeResponse  implements IAPIResponse{

	@XStreamAlias("results")
	private Results results;
	
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
	// default constructor
	public MobileSOSubmitWarrantyHomeReasonCodeResponse() {
	}

	// overloaded constructor to set Results
	public MobileSOSubmitWarrantyHomeReasonCodeResponse(Results results) {
		this.results = results;
	}
	
	public static MobileSOSubmitWarrantyHomeReasonCodeResponse getInstanceForError(
			ResultsCode resultCode) {
		return new MobileSOSubmitWarrantyHomeReasonCodeResponse(Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
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
