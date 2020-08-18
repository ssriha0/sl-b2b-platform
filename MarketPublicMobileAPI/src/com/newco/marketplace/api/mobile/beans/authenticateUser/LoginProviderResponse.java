package com.newco.marketplace.api.mobile.beans.authenticateUser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="proLoginResponse.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name = "loginProviderResponse")
@XStreamAlias("loginProviderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginProviderResponse implements IAPIResponse {

	
	
	@XStreamAlias("results")
	private Results results;
	
	public Results getResults() {
		return results;
	}



	public void setResults(Results results) {
		this.results = results;
	}
	@XmlElement(name="userDetails")
	private UserProviderDetails userDetails;
	
	
	public LoginProviderResponse() {
	
	}


	public UserProviderDetails getUserDetails() {
		return userDetails;
	}



	public void setUserDetails(UserProviderDetails userDetails) {
		this.userDetails = userDetails;
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
