package com.newco.marketplace.api.mobile.beans.eligibleProviders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response 0f eligible providers forSO for mobile
*
*/
@XSD(name="eligibleProviderResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "eligibleProviderResponse")
@XStreamAlias("eligibleProviderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetEligibleProviderResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("eligibleProviders")
	private EligibleProviders eligibleProviders;

	@XStreamAlias("assignedResource")
	private AssignedResource assignedResource;
	
	

	public AssignedResource getAssignedResource() {
		return assignedResource;
	}

	public void setAssignedResource(AssignedResource assignedResource) {
		this.assignedResource = assignedResource;
	}

	public EligibleProviders getEligibleProviders() {
		return eligibleProviders;
	}

	public void setEligibleProviders(EligibleProviders eligibleProviders) {
		this.eligibleProviders = eligibleProviders;
	}


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
