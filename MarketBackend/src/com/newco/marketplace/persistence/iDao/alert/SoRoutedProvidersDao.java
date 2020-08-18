package com.newco.marketplace.persistence.iDao.alert;

import java.util.List;

import com.newco.marketplace.dto.vo.SoRoutedProviders;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author sahmad7
 * 
 */
public interface SoRoutedProvidersDao {

	public int update(SoRoutedProviders soRoutedProviders)
			throws DataServiceException;

	public SoRoutedProviders query(SoRoutedProviders soRoutedProviders)
			throws DataServiceException;

	public List queryList(SoRoutedProviders soRoutedProviders)
			throws DataServiceException;

	public SoRoutedProviders insert(SoRoutedProviders soRoutedProviders)
			throws DataServiceException;

}
