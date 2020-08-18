/**
 * 
 */
package com.servicelive.common.properties;

import com.servicelive.common.exception.DataNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author schavda
 */
public class ApplicationProperties implements IApplicationProperties {

	/** The application properties dao. */
	private IApplicationPropertiesDao applicationPropertiesDao;

	/**
	 * Gets the application properties dao.
	 * 
	 * @return the application properties dao
	 */
	public IApplicationPropertiesDao getApplicationPropertiesDao() {

		return applicationPropertiesDao;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.common.properties.IApplicationProperties#getPropertyValue(java.lang.String)
	 */
	public String getPropertyValue(String key) throws DataNotFoundException {

		ApplicationPropertiesVO prop = new ApplicationPropertiesVO();

		prop = applicationPropertiesDao.query(key);

		return prop.getAppValue();
	}

	/**
	 * Sets the application properties dao.
	 * 
	 * @param applicationPropertiesDao the new application properties dao
	 */
	public void setApplicationPropertiesDao(IApplicationPropertiesDao applicationPropertiesDao) {

		this.applicationPropertiesDao = applicationPropertiesDao;
	}


	public String getPropertyFromDB(String key) throws DataNotFoundException {
		ApplicationPropertiesVO prop = new ApplicationPropertiesVO();

		prop = applicationPropertiesDao.queryToDatabase(key);
		if (prop != null)
			return prop.getAppValue();
		else
			return null;
	}

}
