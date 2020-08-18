/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	KMSRVTIM    infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.login;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class serves as POJO object used for converting request xml request, 
 * map data to backend VO Object. 
 *
 * @author infosys
 *
 */
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