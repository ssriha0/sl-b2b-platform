package com.newco.marketplace.api.beans.login;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("failure")
public class LoginFailureResult {
	@XStreamAlias("number")
	@XStreamAsAttribute()
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
