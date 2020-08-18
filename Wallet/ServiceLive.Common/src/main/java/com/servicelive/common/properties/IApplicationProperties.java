package com.servicelive.common.properties;

import com.servicelive.common.exception.DataNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IApplicationProperties.
 */
public interface IApplicationProperties {

	/**
	 * Gets the property value directly from the database.
	 * 
	 * @param key 
	 * 
	 * @return the fM property value
	 * 
	 * @throws DataNotFoundException 
	 */
	public abstract String getPropertyFromDB(String key) throws DataNotFoundException;

	/**
	 * Gets the property value from database and caches it in Hash Table
	 * 
	 * @param key 
	 * 
	 * @return the property value
	 * 
	 * @throws DataNotFoundException 
	 */
	public abstract String getPropertyValue(String key) throws DataNotFoundException;

}