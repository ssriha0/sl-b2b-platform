package com.servicelive.spn.services.interfaces;

import com.servicelive.spn.dao.provider.ServiceProviderDao;

public interface IServiceProviderService
{

	/**
	 * 
	 * @param serviceProviderDao
	 */
	public abstract void setServiceProviderDao(ServiceProviderDao serviceProviderDao);

}