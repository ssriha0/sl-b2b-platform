/**
 *
 */
package com.servicelive.spn.dao;

import java.util.List;

import com.servicelive.domain.userprofile.User;

/**
 * @author Admin
 *
 */
public interface UserDao extends BaseDao {
	
	
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List <User> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param userName
	 * @return User
	 * @throws Exception
	 */
	public User findById(String   userName) throws Exception ;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<User> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

}
