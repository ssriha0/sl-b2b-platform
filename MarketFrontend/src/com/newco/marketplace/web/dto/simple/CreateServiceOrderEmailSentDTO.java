package com.newco.marketplace.web.dto.simple;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class CreateServiceOrderEmailSentDTO extends SerializedBaseDTO{

	private static final long serialVersionUID = 0L;
	
	private String email;
	private String emailConfirm;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailConfirm() {
		return emailConfirm;
	}
	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
	
}
