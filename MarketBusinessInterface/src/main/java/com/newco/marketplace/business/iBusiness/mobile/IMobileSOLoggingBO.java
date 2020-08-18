package com.newco.marketplace.business.iBusiness.mobile;



import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.MobileSOLoggingVO;

public interface IMobileSOLoggingBO {

	/**
	 * To get the firm matching the resource from the Database.
	 * 
	 * @param providerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer logSOMobileHistory(String request,Integer actionId, Integer resourceId,String soId,String httpMethod)
			throws BusinessServiceException;
	public Integer getResourceId(String userName) throws BusinessServiceException;

	public void updateSOMobileResponse(Integer loggingId,String response) throws BusinessServiceException;
	public Integer logSOMobileHistory(MobileSOLoggingVO loggingVo)throws BusinessServiceException;

	
}
