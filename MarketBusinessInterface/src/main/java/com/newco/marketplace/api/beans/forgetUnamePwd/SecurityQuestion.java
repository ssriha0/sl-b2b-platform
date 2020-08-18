package com.newco.marketplace.api.beans.forgetUnamePwd;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XStreamAlias("securityQuestion")
public class SecurityQuestion {
	
	@XStreamAlias("questionId")
	private Integer questionId;
	
	@XStreamAlias("questionText")
	private String questionText;

	/**
	 * @return the questionId
	 */
	public Integer getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
}
