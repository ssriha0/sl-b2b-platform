package com.newco.marketplace.business.businessImpl.mobile;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.mobile.IMobileSOValidationBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOManagementDao;
import com.servicelive.common.properties.IApplicationProperties;


public class MobileSOValidationBOImpl implements IMobileSOValidationBO {
	private IMobileSOManagementDao mobileSOManagementDao;
	private IApplicationProperties applicationProperties;
	private LookupDao lookupDao;
	private static final Logger logger = Logger
			.getLogger(MobileSOValidationBOImpl.class);


	/**
	 * To check if given firm is authorized to view so
	 * 
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

public boolean isAuthorizedInViewSODetails(String soId,String resourceId) throws BusinessServiceException{
		boolean valid= Boolean.FALSE;
		try {
			valid=mobileSOManagementDao.isAuthorizedInViewSODetails(soId,resourceId);
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->isAuthorizedInViewSODetls()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return valid;
	}

	/**
	 * To check if given provider id is valid or not
	 * 
	 * @param providerId
	 * @return firmId
	 * @throws DataServiceException
	 */
	public Integer validateProviderId(String providerId)
			throws BusinessServiceException {
		Integer firmId;
		try {
			firmId = mobileSOManagementDao.validateProviderId(providerId);
		} catch (Exception e) {
			logger.error("Exception in validating Resource Id "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return firmId;
	}
	
	public boolean isValidProvider(String providerId) throws BusinessServiceException
	{
		// TODO Auto-generated method stub
				boolean valid =  Boolean.FALSE;
				
				try{
					valid = mobileSOManagementDao.isValidProvider(providerId);
					
				}catch(Exception e){
					logger.error("Exception occured in isValidProvider() due to "+e.getMessage());
				}
				return valid;
		
	}
	
	/**
	 * To check if given service order is a valid one or not
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isValidServiceOrder(String soId) throws BusinessServiceException
	{
		// TODO Auto-generated method stub
		boolean valid =  Boolean.FALSE;
		try{
			valid = mobileSOManagementDao.isValidServiceOrder(soId);
			
		}catch(Exception e){
			logger.error("Exception occured in isValidServiceOrder() due to "+e.getMessage());
		}
		return valid;
	}
	public boolean isValidProviderResource(String providerId) throws BusinessServiceException {
		boolean valid =  Boolean.FALSE;
		try{
			valid = mobileSOManagementDao.isValidProviderResource(providerId);
			
		}catch(Exception e){
			logger.error("Exception occured in isValidProviderResource() due to "+e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return valid;
    }
	/**
	 * To check if provider can upload document in the current status of service order
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getValidServiceOrderWfStatus(String soId) throws BusinessServiceException
	{
		// TODO Auto-generated method stub
		Integer wfStatus = null;
		try{
			
			wfStatus = mobileSOManagementDao.getValidServiceOrderWfStatus(soId);
			
		}catch(Exception e){
			logger.error("Exception occured in getValidServiceOrderWfStatus() due to "+e.getMessage());
		}
		return wfStatus;
		
	}
	
	public Integer getFirmId(String providerId) throws BusinessServiceException{
		Integer firmId = null;
		try{
			firmId = mobileSOManagementDao.getFirmId(providerId);
			
		}catch(Exception e){
			logger.info("Exception in MobileSOManagementBOImpl.getFirmId() "+e.getMessage());
		}
		return firmId;
	}

	
	/**
	 * @return the buyer logo urls
	 */
	public HashMap<String, Object> getURLs() throws BusinessServiceException{
		HashMap<String, Object> pathParam=new HashMap<String, Object>();
		try {
			
			String baseUrl = applicationProperties.getPropertyFromDB(MPConstants.BASE_URL);
			String pathUrl = applicationProperties.getPropertyFromDB(MPConstants.PATH_URL);
			pathParam.put("baseUrl", baseUrl);
			pathParam.put("pathUrl", pathUrl);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException(e.getMessage());
		}
		return pathParam;
	}

	/**
	 * @return the lookupDao
	 */
	public LookupDao getLookupDao() {
		return lookupDao;
	}

	/**
	 * @param lookupDao the lookupDao to set
	 */
	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
	
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public IMobileSOManagementDao getMobileSOManagementDao() {
		return mobileSOManagementDao;
	}

	public void setMobileSOManagementDao(
			IMobileSOManagementDao mobileSOManagementDao) {
		this.mobileSOManagementDao = mobileSOManagementDao;
	}


	
}

