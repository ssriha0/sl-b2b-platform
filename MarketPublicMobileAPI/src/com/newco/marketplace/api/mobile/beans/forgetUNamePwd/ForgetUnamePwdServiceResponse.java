package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.forgetUnamePwd.SecurityQuestion;
import com.newco.marketplace.api.beans.forgetUnamePwd.UserDetails;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing response information for the Mobile
 * ForgetUnamePwdService1Response
 * 
 * @author Infosys
 * 
 */
@XSD(name = "forgetUnamePwdServiceResponse.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "forgetUnamePwdServiceResponse")
@XStreamAlias("forgetUnamePwdServiceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForgetUnamePwdServiceResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("requestFor")
	private String requestFor;

	@XStreamAlias("userDetails")
	private UserDetails userDetails;
	
	@XStreamAlias("securityQuestion")
	private SecurityQuestion securityQuestion;
	

	/**
	 * @return the results
	 */
	public Results getResults() {
		return results;
	}

	public void setResults(final Results results) {
		this.results = results;
	}
	
	public String getRequestFor() {
		return requestFor;
	}

	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}
	

	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * @return the securityQuestion
	 */
	public SecurityQuestion getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion the securityQuestion to set
	 */
	public void setSecurityQuestion(SecurityQuestion securityQuestion) {
		this.securityQuestion = securityQuestion;
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
