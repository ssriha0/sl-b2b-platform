package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Infosys
 *
 */
@XStreamAlias("verificationDetails")
public class VerificationDetails {

	@XStreamAlias("securityQuestAnsDetails")
	private SecurityQuestAnsDetails securityQuestAnsDetails; 
	
	@XStreamAlias("additionalVerificationDetails")
	private AdditionalVerificationDetails additionalVerificationDetails ;

	public SecurityQuestAnsDetails getSecurityQuestAnsDetails() {
		return securityQuestAnsDetails;
	}

	public void setSecurityQuestAnsDetails(
			SecurityQuestAnsDetails securityQuestAnsDetails) {
		this.securityQuestAnsDetails = securityQuestAnsDetails;
	}

	public AdditionalVerificationDetails getAdditionalVerificationDetails() {
		return additionalVerificationDetails;
	}

	public void setAdditionalVerificationDetails(
			AdditionalVerificationDetails additionalVerificationDetails) {
		this.additionalVerificationDetails = additionalVerificationDetails;
	} 
	
	

	
}
