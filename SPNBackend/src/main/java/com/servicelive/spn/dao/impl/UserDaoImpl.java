/**
 *
 */
package com.servicelive.spn.dao.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.UserDao;

/**
 * @author Admin
 *
 */
@Transactional
public class UserDaoImpl  extends AbstractBaseDao    implements UserDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.UserDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAll(int... rowStartIdxAndCount) throws Exception {
		return ( List<User>) super.findAll("User",rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.UserDao#findById(java.lang.String)
	 */
	public User findById(String userName) throws Exception {
		return ( User) super.findById(User.class, userName);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.UserDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<User> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return ( List<User>) super.findByProperty("User",propertyName,value, rowStartIdxAndCount);
	}

	
	
	




}
