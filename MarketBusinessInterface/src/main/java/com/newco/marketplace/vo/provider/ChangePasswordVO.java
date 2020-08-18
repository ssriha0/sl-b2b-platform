/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.Map;


/**
 * @author KSudhanshu
 *
 */
public class ChangePasswordVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2156902450054109073L;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private Map questionMap;
	private String userName;
	
	
	/**
	 * @return the secretQuestion
	 */
	public String getSecretQuestion() {
		return secretQuestion;
	}
	/**
	 * @param secretQuestion the secretQuestion to set
	 */
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
	/**
	 * @return the secretAnswer
	 */
	public String getSecretAnswer() {
		return secretAnswer;
	}
	/**
	 * @param secretAnswer the secretAnswer to set
	 */
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the questionMap
	 */
	public Map getQuestionMap() {
		return questionMap;
	}
	/**
	 * @param questionMap the questionMap to set
	 */
	public void setQuestionMap(Map questionMap) {
		this.questionMap = questionMap;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
