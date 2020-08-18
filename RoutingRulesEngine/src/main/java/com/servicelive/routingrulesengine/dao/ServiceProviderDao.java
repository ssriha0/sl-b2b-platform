package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.provider.ServiceProvider;

/**
 * 
 * @author svanloon
 *
 */
public interface ServiceProviderDao {

	/**
	 * 
	 * @param providerFirmId
	 * @return List<ServiceProvider>
	 */
	public List<ServiceProvider> findByProviderFirmId(Integer providerFirmId);
}
