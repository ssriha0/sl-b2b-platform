package com.newco.marketplace.business.businessImpl.hi;



import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.hi.IAPILoggingBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.hi.IAPILogginDao;
import com.newco.marketplace.vo.hi.provider.APILoggingVO;


public class APILoggingBOImpl implements IAPILoggingBO {
	private IAPILogginDao APILogginDao;
	
	private static final Logger LOGGER = Logger.getLogger(APILoggingBOImpl.class);

	/**
	 * @param createFirmRequest
	 * @param methodType
	 * @param actionId
	 * @param firmId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer logAPIRequest(String request, String methodType,String apiName, String firmId,String response, String loggingType,String status) throws BusinessServiceException{
		Integer loggingId =null;
		try{
			APILoggingVO loggingVO = new APILoggingVO(request, apiName, methodType, response,loggingType,status);
			//To DO : needs to be changed after confirmation.
			if(!StringUtils.isEmpty(firmId)){
				loggingVO.setCreatedBy(Integer.parseInt(firmId));
			}
			loggingId = APILogginDao.logAPIHistoryRequest(loggingVO);
		}catch (Exception e) {
			LOGGER.error("Exception in Logging Request for HI API"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return loggingId;
	}

	public void logAPIHistoryResponse(Integer logginId, String responseXML,String status)throws BusinessServiceException {
		APILoggingVO loggingVO = new APILoggingVO();
		try{
			loggingVO.setLoggingId(logginId);
			loggingVO.setResponse(responseXML);
			loggingVO.setStatus(status);
			APILogginDao.updateAPIHistory(loggingVO);
			
		}catch (Exception e) {
			LOGGER.error("Exception in Logging Response for HI API"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		
	}
	
	
	public String apiLoggingSwitch() throws BusinessServiceException{
		String apiLoggingSwitch=null;
		try{
			apiLoggingSwitch=APILogginDao.apiLoggingSwitch();
		}catch (Exception e) {
			LOGGER.error("Exception in apiLoggingSwitch for HI API"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return apiLoggingSwitch;
	}


	public IAPILogginDao getAPILogginDao() {
		return APILogginDao;
	}

	public void setAPILogginDao(IAPILogginDao aPILogginDao) {
		APILogginDao = aPILogginDao;
	}



	
}

