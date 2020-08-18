/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.api.mobile.security;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
import com.newco.marketplace.vo.provider.LoginVO;

/**
 * The class invokes the necessary business classes for the publicAPI
 * authentication purpose.
 * 
 */
public class AuthenticationProcess {

	private ILoginBO loginBOImpl;
	private Logger logger = Logger.getLogger(AuthenticationProcess.class);

	/**
	 * This method checks the login details as well as api values
	 * 
	 * @param userName :
	 *            username from xml
	 * @param password :
	 *            password from xml
	 * @return : status of authentication
	 */
	public boolean authenticate(String userName, String password) {
		logger.info("Entering AuthenticationProcess.authenticate()");
		LoginVO loginVO = new LoginVO();
		int iUserLoginStatus = 0;
		boolean isValidUser = false;
		try {
			loginVO.setUsername(userName);
			loginVO.setPassword(password);
			iUserLoginStatus = loginBOImpl.validatePassword(loginVO);
			if (iUserLoginStatus == 0) {
				logger.info("Valid Username." + userName);
				isValidUser = true;
			}
		} catch (Exception e) {
			logger.error("The exception is " + e);
		}
		logger.info("Leaving AuthenticationProcess.authenticate()");
		return isValidUser;
	}

	public void setLoginBOImpl(ILoginBO loginBOImpl) {
		this.loginBOImpl = loginBOImpl;
	}

}
