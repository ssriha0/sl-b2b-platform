/**
 * 
 */
package com.newco.marketplace.persistence.iDao.common;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.common.InterimPasswordVO;
 
/**
 * @author Shekhar Nirkhke
 */
public interface IInterimPasswordDao {
	
	/**
	 * Create a new entry in interim table.
	 * @param interimPassword
	 * @return
	 * @throws DBException
	 */
	public InterimPasswordVO resetPassword(String userName)throws DBException;
	
	/**
	 * Returns null if no user found
	 * @param password
	 * @return
	 * @throws DBException
	 */
	public InterimPasswordVO getUserFromInterimPassword(InterimPasswordVO interimPassword) throws DBException;
	
	/**
	 * Set password to invalid
	 * @param password
	 * @throws DBException
	 */
	public void invalidatePassword(String password) throws DBException;
}