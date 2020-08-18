package com.newco.marketplace.api.mobile.beans.companyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;

import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="profileResponse.xsd", path="/resources/schemas/mobile/v3_1/")
@XStreamAlias("profileResponse")
@XmlRootElement(name="profileResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ViewCompanyProfileResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("completeProfile")
	private CompleteProfile completeProfile;
	
	@XStreamAlias("publicProfile")
	private PublicProfile publicProfile;
	

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public CompleteProfile getCompleteProfile() {
		return completeProfile;
	}

	public void setCompleteProfile(CompleteProfile completeProfile) {
		this.completeProfile = completeProfile;
	}
	public PublicProfile getPublicProfile() {
		return publicProfile;
	}

	public void setPublicProfile(PublicProfile publicProfile) {
		this.publicProfile = publicProfile;
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
