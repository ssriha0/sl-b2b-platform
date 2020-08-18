package com.servicelive.common.properties;

import org.springframework.dao.DataAccessException;

import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.ABaseDao;

public class SlkSecureDao extends ABaseDao implements ISlkSecureDao{

	/**
	 * returns value for the key
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public synchronized String valueForKey(String key) throws DataAccessException,DataNotFoundException {
		// TODO Auto-generated method stub
		return (String)queryForObject("secure_valueforkey.query", key);
	}
}
