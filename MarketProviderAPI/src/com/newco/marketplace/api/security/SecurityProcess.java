/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-May-2009	KMSTRSUP   	Infosys				1.0
 * 
 */
package com.newco.marketplace.api.security;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;

/**
 * This class is a service class for getting Security Context.
 * 
 * @author Infosys
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SecurityProcess extends ABaseRequestDispatcher {

	private Logger logger = Logger.getLogger(SecurityProcess.class);

	/**
	 * This method is for getting Security Context using Buyer_Credentials.
	 * 
	 * @param userName
	 *            String
	 * @param Password
	 *            String
	 * @return SecurityContext
	 */
	public SecurityContext getSecurityContext(String userName, String Password) {
		logger.info("Entering SecurityProcess.getSecurityContext()");
		SecurityContext securityContext = createAPISecurityContext(userName, Password);
		logger.info("Leaving SecurityProcess.getSecurityContext()");

		if (securityContext != null)
			securityContext.setAutoACH(false);
		return securityContext;
	}

	/**
	 * This method is for getting Security Context using Buyer Resource Id.
	 * 
	 * @param buyerResourceId
	 *            Integer
	 * @return SecurityContext
	 */
	public SecurityContext getSecurityContextForVendor(Integer vendorResourceId) {
		String userName = getVendorUserName(vendorResourceId);
		if (null != userName)
			return getSecurityContext(userName, null);
		else
			return null;
	}

	/**
	 * This method is for getting Security Context using vendor Admin.
	 * 
	 * @param buyerResourceId
	 *            Integer
	 * @return SecurityContext
	 */
	public SecurityContext getSecurityContextForVendorAdmin(Integer vendorId) {
		String userName = getVendorAdminName(vendorId);
		if (null != userName)
			return getSecurityContext(userName, null);
		else
			return null;
	}

	protected String getVendorAdminName(Integer vendorId) {
		return getAccessSecurity().getVendorAdminName(vendorId);
	}

	protected String getVendorUserName(Integer vendorResourceId) {
		logger.info("getAccessSecurity(): "+getAccessSecurity());
		
		return getAccessSecurity().getVendorUserName(vendorResourceId);
	}

	public String getOAuthConsumerSecret(String consumerKey) {
		// TODO Auto-generated method stub
		return getAccessSecurity().getOAuthConsumerSecret(consumerKey);
	}

	public boolean isProviderExist(Integer buyerId) {
		return getAccessSecurity().verifyEntity(buyerId, "PROVIDER");
	}

}
