package com.newco.marketplace.api.mobile.beans.assign;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/23
* for fetching response 0f search SO for mobile
*
*/
@XSD(name = "assignServiceOrderResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "assignOrderResponse")
@XStreamAlias("assignOrderResponse")
public class AssignServiceOrderResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	// default constructor
	public AssignServiceOrderResponse() {
	}

	// overloaded constructor to set Results
	public AssignServiceOrderResponse(Results results) {
		this.results = results;
	}
	
	/**
	 * @return the results
	 */
	public Results getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
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

	public static AssignServiceOrderResponse getInstanceForError(
			ResultsCode resultCode) {
		return new AssignServiceOrderResponse(Results.getError(
					resultCode.getMessage(), resultCode.getCode()));
	}
}
