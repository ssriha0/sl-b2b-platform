package com.newco.marketplace.web.dto.provider;

public class ForgotPasswordDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8772203478675618636L;
	private String email;
	private String userName;
	private String questionId;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
}
