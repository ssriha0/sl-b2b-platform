package com.newco.marketplace.vo.provider;

public class ForgotPasswordVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2834544291740008642L;
	private String email;
	private String userName;
	private String questionId;

   
    public ForgotPasswordVO(String email, String userName) {

        super();
        this.email = userName;
        this.userName = userName;
    }

    public ForgotPasswordVO() {

        super();
    }

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
