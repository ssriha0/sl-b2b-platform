package com.servicelive.common.properties;

import org.springframework.dao.DataAccessException;

import com.servicelive.common.exception.DataNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IApplicationPropertiesDao.
 */
public interface IApplicationPropertiesDao {

	/**
	 * Queries application_properties table for key set in input parameter.
	 * 
	 * @param key 
	 * 
	 * @return 
	 * 
	 * @throws DataAccessException 
	 * @throws DataNotFoundException 
	 */
	public ApplicationPropertiesVO query(String key) throws DataAccessException, DataNotFoundException;

	public ApplicationPropertiesVO queryToDatabase(String key) throws DataAccessException, DataNotFoundException;

}
