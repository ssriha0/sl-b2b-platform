package com.newco.marketplace.persistence.daoImpl.slkSecure;

import org.springframework.dao.DataAccessException;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.persistence.dao.slkSecure.SlkSecureDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class SlkSecureDaoImpl extends ABaseImplDao implements SlkSecureDao{

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
