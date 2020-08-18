/**
 * 
 */
package com.servicelive.spn.services;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hoza
 *
 */
public class AuthorizationService extends BaseServices {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		//do 

	}
	
	@SuppressWarnings(value = { "unchecked" })
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getAuthoritiesForUser(String authenticateUserName) throws Exception {
		return  getSqlMapClient().queryForList("spn.login.authority.getAuthorities", authenticateUserName);
	}

}
