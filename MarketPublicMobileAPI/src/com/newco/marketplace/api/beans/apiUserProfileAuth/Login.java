/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	KMSRVTIM    infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.apiUserProfileAuth;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class serves as POJO object used for converting request xml request, 
 * map data to backend VO Object. 
 *
 * @author infosys
 *
 */
@XStreamAlias("login")
public class Login {
	
	@XStreamAlias("type")   
	@XStreamAsAttribute() 
	private String type;
	
	@XStreamAlias("userName")
	private String userName;
	
	@XStreamAlias("password")
	private String password;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
