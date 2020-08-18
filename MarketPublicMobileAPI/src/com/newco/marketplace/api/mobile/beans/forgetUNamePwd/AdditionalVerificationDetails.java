package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Infosys
 *
 */
@XStreamAlias("additionalVerificationDetails")
public class AdditionalVerificationDetails {

	@XStreamAlias("userZipCode")   
	private String userZipCode;
	
	@XStreamAlias("userPhoneNumber")
	private String userPhoneNumber;
	
	@XStreamAlias("userCompanyName")
	private String userCompanyName;

	public String getUserZipCode() {
		return userZipCode;
	}

	public void setUserZipCode(String userZipCode) {
		this.userZipCode = userZipCode;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserCompanyName() {
		return userCompanyName;
	}

	public void setUserCompanyName(String userCompanyName) {
		this.userCompanyName = userCompanyName;
	}

	
}
