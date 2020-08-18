package com.servicelive.wallet.batch;

import com.servicelive.common.exception.DataServiceException;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseFileProcessor.
 */
public abstract class BaseFileProcessor extends BaseProcessor {

	/**
	 * Gets the archive file directory.
	 * 
	 * @param propertyName 
	 * 
	 * @return the archive file directory
	 * 
	 * @throws DataServiceException 
	 */
	public String getArchiveFileDirectory(String propertyName) throws DataServiceException {

		return applicationProperties.getPropertyValue(propertyName);
	}

	/**
	 * Gets the file directory.
	 * 
	 * @param propertyName 
	 * 
	 * @return the file directory
	 * 
	 * @throws DataServiceException 
	 */
	public String getFileDirectory(String propertyName) throws DataServiceException {

		return applicationProperties.getPropertyValue(propertyName);
	}
}
