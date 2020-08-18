package com.newco.marketplace.business.businessImpl.mobile;



import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.mobile.IMobileSOLoggingBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOManagementDao;
import com.newco.marketplace.vo.mobile.MobileSOLoggingVO;


public class MobileSOLoggingBOImpl implements IMobileSOLoggingBO {
	private IMobileSOManagementDao mobileSOManagementDao;
	
	private static final Logger logger = Logger
			.getLogger(MobileSOLoggingBOImpl.class);


	/**
	 * To check if given firm is authorized to view so
	 * 
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */

	public Integer logSOMobileHistory(String request, Integer actionId, Integer resourceId,String soId,String httpMethod)
			throws BusinessServiceException{
		Integer loggingId;
		try {
			loggingId=mobileSOManagementDao.logSOMobileHistory(request, actionId, resourceId,soId,httpMethod);
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->logSOMobileHistory()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return loggingId;
	}
	public Integer logSOMobileHistory(MobileSOLoggingVO loggingVo) throws BusinessServiceException{
		Integer loggingId;
		try {
			loggingId=mobileSOManagementDao.logSOMobileHistory(loggingVo);
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->logSOMobileHistory()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return loggingId;
	}
	
	public Integer getResourceId(String userName) throws BusinessServiceException{
		Integer resourceId;
		try {
			resourceId = mobileSOManagementDao.getResourceId(userName);
		} catch (Exception e) {
			logger.error("Exception in getting Resource Id "
					+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return resourceId;
	}
	public void updateSOMobileResponse(Integer loggingId,String response) throws BusinessServiceException{
		try {
			mobileSOManagementDao.updateSOMobileResponse(loggingId, response);
		} catch (DataServiceException ex) {
			logger.error("Exception Occured in MobileSOManagementBO-->updateSOMobileResponse()");
			throw new BusinessServiceException(ex.getMessage());
		}
		
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
	


	
	
	public IMobileSOManagementDao getMobileSOManagementDao() {
		return mobileSOManagementDao;
	}

	public void setMobileSOManagementDao(
			IMobileSOManagementDao mobileSOManagementDao) {
		this.mobileSOManagementDao = mobileSOManagementDao;
	}


	
}

