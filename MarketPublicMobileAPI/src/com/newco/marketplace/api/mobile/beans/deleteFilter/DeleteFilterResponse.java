package com.newco.marketplace.api.mobile.beans.deleteFilter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f recieved orders
*/
@XSD(name="deleteFilterResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "deleteFilterResponse")
@XStreamAlias("deleteFilterResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteFilterResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	
	// default constructor
	public DeleteFilterResponse() {
	}

	// overloaded constructor to set Results
	public DeleteFilterResponse(Results results) {
		this.results = results;
	}
	
	public static DeleteFilterResponse getInstanceForError(
			ResultsCode resultCode) {
		return new DeleteFilterResponse(Results.getError(
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

	public void setResults(Results results) {
		this.results = results;
	}

	public Results getResults() {
		return results;
	}

}
