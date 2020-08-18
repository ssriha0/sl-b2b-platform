package com.newco.marketplace.api.beans.apiUserProfileAuth;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.vo.apiUserProfile.UserDetails;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "userAuthenticationResponse")
@XStreamAlias("userAuthenticationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserAuthenticationResponse {
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("message")
	private String message;
	
	@XmlElement(name="userDetails")
	private UserDetails userDetails;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String messege) {
		this.message = messege;
	}
	
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
}
