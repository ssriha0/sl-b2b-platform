package com.newco.marketplace.business.iBusiness.hi;

import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IAPILoggingBO {

	/**
	 * @param createFirmRequest
	 * @param methodType
	 * @param actionId
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer logAPIRequest(String createFirmRequest, String methodType,String apiName, String firmId,String response, String loggingType,String status) throws BusinessServiceException;

	/**
	 * @param logginId
	 * @param responseXML
	 * @throws BusinessServiceException
	 */
	public void logAPIHistoryResponse(Integer logginId, String responseXML,String status)throws BusinessServiceException;
	
	
	public String apiLoggingSwitch() throws BusinessServiceException;
	
   
}
