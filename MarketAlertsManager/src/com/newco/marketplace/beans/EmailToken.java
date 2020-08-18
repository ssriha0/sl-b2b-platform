package com.newco.marketplace.beans;

import java.util.Date;

public class EmailToken {
	String token_type;
	String access_token;
	Date expires_in;
	
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Date getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Date expires_in) {
		this.expires_in = expires_in;
	}
	
}
