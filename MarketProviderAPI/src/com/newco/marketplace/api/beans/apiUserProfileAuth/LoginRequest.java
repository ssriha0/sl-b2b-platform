/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	KMSRVTIM    infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.apiUserProfileAuth;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class serves as POJO object used for converting request xml request, 
 * map data to backend VO Object. 
 *
 * @author infosys
 *
 */
@XmlRootElement(name = "authenticationRequest")
@XStreamAlias("authenticationRequest")
public class LoginRequest {
	
	@XStreamAlias("login")
	private Login login;

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}