/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import java.util.Map;


/**
 * @author KSudhanshu
 *
 */
public class ChangePasswordDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3073858814486804142L;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String userName;
	private Map questionMap;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
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
	
	
	
	
}
