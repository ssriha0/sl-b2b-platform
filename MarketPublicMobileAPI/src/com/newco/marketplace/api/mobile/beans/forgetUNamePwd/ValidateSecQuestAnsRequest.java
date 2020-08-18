package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the ValidateSecQuestAnsRequest
 * @author Infosys
 *
 */
@XSD(name = "validateSecQuestAnsRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "validateSecQuestAnsRequest")
@XStreamAlias("validateSecQuestAnsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidateSecQuestAnsRequest {
	
	@XStreamAlias("requestFor")
	private String requestFor;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("userDetails")
	private UserDetails userDetails;

	@XStreamAlias("verificationDetails")
	private VerificationDetails verificationDetails;

	/**
	 * @return the requestFor
	 */
	public String getRequestFor() {
		return requestFor;
	}

	/**
	 * @param requestFor the requestFor to set
	 */
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	

	

	
	public VerificationDetails getVerificationDetails() {
		return verificationDetails;
	}

	public void setVerificationDetails(VerificationDetails verificationDetails) {
		this.verificationDetails = verificationDetails;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	

}
