package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing identification information.
 * @author Infosys
 *
 */
@XStreamAlias("identification")
public class Identification {
	
	@XStreamAlias("applicationkey")
	private String applicationkey;
	
	@XStreamAlias("password")
	private String passWord;
	
	@XStreamAlias("username")
	private String username;

	public String getApplicationkey() {
		return applicationkey;
	}

	public void setApplicationkey(String applicationkey) {
		this.applicationkey = applicationkey;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
