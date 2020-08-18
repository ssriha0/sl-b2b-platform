package com.newco.marketplace.web.delegatesImpl.provider;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IServiceProviderBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO;
import com.newco.marketplace.web.delegates.provider.IServiceProviderDelegate;
import com.newco.marketplace.web.dto.provider.ServiceProviderRegistrationDto;

/**
 * 
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 01:13:52 $
 *
 */
public class ServiceProviderDelegateImpl implements IServiceProviderDelegate {
	
	private static final Logger logger = Logger.getLogger(ServiceProviderDelegateImpl.class.getName());
	private IServiceProviderBO iServiceProviderBO;
	
	/**
	 * Construtor
	 * @param serviceProviderBO
	 */
	public ServiceProviderDelegateImpl(IServiceProviderBO serviceProviderBO) {		
		this.iServiceProviderBO = serviceProviderBO;
	}

	/*
	 * Save VO to DB
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IServiceProviderDelegate#saveServiceProviderRegistration(com.newco.marketplace.web.dto.provider.ServiceProviderRegistrationDto)
	 */
	public void saveServiceProviderRegistration(ServiceProviderRegistrationDto serviceProviderRegistrationDto) throws DelegateException{
		
		ServiceProviderRegistrationVO serviceProviderRegistrationVO = new ServiceProviderRegistrationVO();
		
		System.out.println("--------ServiceProviderDelegateImpl.saveServiceProviderRegistration()--");
		System.out.println("-----DTO-ResourceID: " + serviceProviderRegistrationDto.getResourceId());
		//insert mapper code here
		serviceProviderRegistrationVO.setResourceId(serviceProviderRegistrationDto.getResourceId());
		serviceProviderRegistrationVO.setEmail(serviceProviderRegistrationDto.getEmail());
		serviceProviderRegistrationVO.setPassword(serviceProviderRegistrationDto.getPassword());
		serviceProviderRegistrationVO.setUserName(serviceProviderRegistrationDto.getUserName());
		
		try {
			this.iServiceProviderBO.saveServiceProviderRegistration(serviceProviderRegistrationVO);
		} catch (BusinessServiceException e) {
			throw new DelegateException("SQL Exception @ServiceProviderDelegateImpl.saveServiceProviderRegistration()");
		}		
	}

	/* 
	 * MTedder
	 * (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IServiceProviderDelegate#queryEmailForServiceProvider(com.newco.marketplace.web.dto.provider.ServiceProviderRegistrationDto)
	 */
	public ServiceProviderRegistrationDto queryEmailForServiceProvider(ServiceProviderRegistrationDto serviceProviderRegistrationDto) throws DelegateException {
		
		ServiceProviderRegistrationVO serviceProviderRegistrationVO = new ServiceProviderRegistrationVO();
		//mapping
		serviceProviderRegistrationVO.setResourceId(serviceProviderRegistrationDto.getResourceId());
		
		try {
			serviceProviderRegistrationVO= iServiceProviderBO.queryEmailForServiceProvider(serviceProviderRegistrationVO);
			//mapping
			serviceProviderRegistrationDto.setEmail(serviceProviderRegistrationVO.getEmail());
			
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return serviceProviderRegistrationDto;
	}


}
