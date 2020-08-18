/**
 * 
 */
package com.servicelive.spn.services;

import com.newco.marketplace.utils.CryptoUtil.CannotPerformOperationException;
import com.newco.marketplace.utils.CryptoUtil.InvalidHashException;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.common.util.CryptoUtil;
import com.servicelive.spn.dao.UserDao;

/**
 * @author hoza
 * This class used for Authentication as well as Authorization .. we will move out Authorization outside as when we get chance
 *
 */
public class AuthenticationService {
	private UserDao userDao;

	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	/**
	 * 
	 * @param userName
	 * @param credential
	 * @return User
	 * @throws Exception
	 */
	public User authenticateUser(String userName,String credential) throws Exception{
		User user = null;
		user = userDao.findById(userName);
		if(user != null && user.getPassword() != null ) {
			 return validatePassword(credential,user.getPassword() )? user : null;
		}
		return null;
	
	}
	
	/**This method will return user object by accepting username
	 * 
	 * @param userName
	 * @return User
	 * @throws Exception
	 */
	public User getUser(String userName) throws Exception{
		User user = null;
		user = userDao.findById(userName);
		return user;
	
	}
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) throws CannotPerformOperationException, InvalidHashException {
		
		if (inputPassword == null || expectedPassword == null) {
			return false;
		}
		
		String encrytedPassword=com.newco.marketplace.utils.CryptoUtil.generateHash(inputPassword);
		
		return  com.newco.marketplace.utils.CryptoUtil.verifyPassword(encrytedPassword, expectedPassword);
	}
}
