package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Infosys
 *
 */
@XStreamAlias("securityQuestAnsDetails")
public class SecurityQuestAnsDetails {
	
	@XStreamAlias("userAnswer")
	private String userAnswer;

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}


	
}
