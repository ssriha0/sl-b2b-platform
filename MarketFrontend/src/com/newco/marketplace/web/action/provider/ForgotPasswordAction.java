package com.newco.marketplace.web.action.provider;

import org.apache.struts2.interceptor.validation.SkipValidation;
import com.newco.marketplace.web.delegates.provider.IForgotPasswordDelegate;
import com.newco.marketplace.web.dto.provider.ForgotPasswordDto;
import com.opensymphony.xwork2.ActionSupport;


public class ForgotPasswordAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1302566019655319139L;
	private IForgotPasswordDelegate forgotPasswordDelegate;
	private ForgotPasswordDto forgotPasswordDto;
	private String email;
	private String userName;
	/*
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param changePasswordDelegate
	 * @param changePasswordDto
	 */
	public ForgotPasswordAction(IForgotPasswordDelegate forgotPasswordDelegate,
			ForgotPasswordDto forgotPasswordDto) {
		this.forgotPasswordDelegate = forgotPasswordDelegate;
		this.forgotPasswordDto = forgotPasswordDto;
	}

	public String execute() throws Exception {
		if(email == null && userName == null)
			return INPUT;
		else 
		{
			forgotPasswordDto.setEmail(email);
			forgotPasswordDto.setUserName(userName);
			Integer secretQuestionId = forgotPasswordDelegate.getSecretQuestionId(forgotPasswordDto);
			System.out.println("Question Id is " + secretQuestionId);
			return "question";
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@SkipValidation 
	public String doLoad() throws Exception {
		load();
		return INPUT;
	}

	private void load() throws Exception {
		//id = (String) ActionContext.getContext().getSession().get("vendorId");
		//questionMap = forgotPasswordDelegate.getSecretQuestionList();
	}

	private boolean isInvalid(String value) {
		return (value == null || value.length() == 0);
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

}