package com.newco.marketplace.business.businessImpl.provider;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.business.iBusiness.provider.IServiceProviderBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.ITeamProfileDAO;
import com.newco.marketplace.persistence.iDao.provider.ProviderDao;
import com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO;

/**
 * 
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 *
 */
public class ServiceProviderBOImpl implements IServiceProviderBO{

	private static final Logger logger = Logger.getLogger(ServiceProviderBOImpl.class.getName());
	private IProviderEmailBO iProviderEmailBO;
	private ITeamProfileDAO iTeamProfileDAO;
	private ProviderDao providerDao;
	
	/**
	 * @param providerEmailBO
	 */
	public ServiceProviderBOImpl(IProviderEmailBO providerEmailBO, ITeamProfileDAO teamProfileDAO, ProviderDao providerDao) {		
		this.iProviderEmailBO = providerEmailBO;
		this.iTeamProfileDAO = teamProfileDAO;
		this.providerDao = providerDao;
	}
	/*
	 * MTedder@covansys.com
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IServiceProviderBO#queryEmailForServiceProvider(com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO)
	 */
	public ServiceProviderRegistrationVO queryEmailForServiceProvider(ServiceProviderRegistrationVO serviceProviderRegistrationVO) throws BusinessServiceException{
		ServiceProviderRegistrationVO result = null;
		
		try {
			// commented by kumar
			result = this.iTeamProfileDAO.queryEmailForServiceProvider(serviceProviderRegistrationVO);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		return result;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IServiceProviderBO#saveServiceProviderRegistration(com.newco.marketplace.vo.provider.ServiceProviderRegistrationVO)
	 */
	public void saveServiceProviderRegistration(ServiceProviderRegistrationVO serviceProviderRegistrationVO) throws BusinessServiceException {
		try {
			this.iProviderEmailBO.sendServiceProviderRegistrationConfirmationMail(serviceProviderRegistrationVO.getUserName(), serviceProviderRegistrationVO.getPassword(), serviceProviderRegistrationVO.getEmail());
		} catch (MessagingException e) {
			logger.info("Caught Exception and ignoring",e);
		} catch (IOException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
	}
	
	public void updateVendorInfoCache() throws BusinessServiceException {
		try {
			providerDao.updateVendorInfoCache();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception and ignoring", e);
		}
	}
	
	
	/**
	 * Update the vendor KPI in the system
	 */
	public void updateVendorKPI() throws BusinessServiceException {
		try {
			providerDao.updateVendorKPI();
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("Caught Exception while updating vendor KPI and ignoring", e);
		}
	}

}
