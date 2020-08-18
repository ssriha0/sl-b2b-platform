package com.newco.marketplace.api.beans.login;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name ="verifyUserCredentialsResponse")
public class SimpleBuyerLoginResponse {

	
	@XStreamAlias("buyer")
	@XmlElement(name = "buyer")
	private SimpleBuyer buyer;
	
	@XStreamAlias("result")
	@XmlElement(name = "results")
	private LoginResult results;
	
	public SimpleBuyerLoginResponse () {
		
	}

	public SimpleBuyer getBuyer() {
		return buyer;
	}

	public void setBuyer(SimpleBuyer buyer) {
		this.buyer = buyer;
	}

	public LoginResult getResults() {
		return results;
	}

	public void setResults(LoginResult results) {
		this.results = results;
	}

}
