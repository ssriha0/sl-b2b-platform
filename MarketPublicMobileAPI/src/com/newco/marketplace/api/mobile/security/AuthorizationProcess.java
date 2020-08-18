/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.api.mobile.security;

import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
/**
 * The class invokes the necessary business 
 * classes for the publicAPI authorization purpose. 
 * 
 */
public class AuthorizationProcess {

	private ILoginBO loginBOImpl;

	/**
	 * it triggers the authorization process
	 * 
	 * @param username : username from xml
	 * @param apiKey :  apikey from xml
	 * @param ipAddress : client ip address
	 * @return : authorization status
	 * @throws Exception
	 */
	public int authorize(String username, String apiKey, String ipAddress)
			throws Exception {
		return loginBOImpl.authorize(username, apiKey, ipAddress);
	}

	public void setLoginBOImpl(ILoginBO loginBOImpl) {
		this.loginBOImpl = loginBOImpl;
	}

}
