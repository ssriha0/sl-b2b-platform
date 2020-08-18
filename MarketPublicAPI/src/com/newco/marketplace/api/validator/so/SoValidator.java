package com.newco.marketplace.api.validator.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.substatusrespose.GetSubStatusResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SoSubStatusMapper;
import com.newco.marketplace.business.businessImpl.serviceorder.ServiceOrderMonitorBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class SoValidator {
	private static Logger LOGGER = Logger.getLogger(SoValidator.class);
	private ServiceOrderMonitorBO serviceOrderMonitorBO;
	private SoSubStatusMapper substatusMapper;
	/**
	 * @param securityContext
	 * @param soStausList
	 * @return
	 * @throws BusinessServiceException
	 */
	public GetSubStatusResponse validatesoSubStatus(List<String> soStausList) throws BusinessServiceException {
		 GetSubStatusResponse  response = null;
		 List<ServiceOrderStatusVO> soStatusListDB = serviceOrderMonitorBO.getAllSoStatus();
		 if(null!= soStausList && !soStausList.isEmpty()){
			   response = validateSoStatus(soStausList);
		  }else{
				response = new GetSubStatusResponse();
				response = substatusMapper.setStatusIdArray(response,soStatusListDB);
		  }
		 //This method is written to set the status id and status name in a map.
		 response = substatusMapper.setStatusNameMap(soStatusListDB,response);
		 return response;
	}
	
	

	/**
	 * @param soStausList
	 * @return
	 * @throws BusinessServiceException
	 */
	private GetSubStatusResponse validateSoStatus(List<String> soStausList) throws BusinessServiceException {
		List<Integer> validSoStatus = new ArrayList<Integer>();
		GetSubStatusResponse  response = null;
		try{
			List<ServiceOrderStatusVO> soStatusListDB = serviceOrderMonitorBO.getAllSoStatus();
			if(null!=soStausList && !soStausList.isEmpty() && !soStatusListDB.isEmpty() ){
				for(ServiceOrderStatusVO stausVO:soStatusListDB){
					if(soStausList.contains(stausVO.getStatusName())){
						validSoStatus.add(stausVO.getStatusId());
					}
				}
			}
			//This list empty means the status provided are invalid
			if(validSoStatus.isEmpty()){
				response = new GetSubStatusResponse();
				response = setErrorResponse(ResultsCode.INVALID_SERVICE_ORDER_STATUS);
			}else{
				response = new GetSubStatusResponse();
				int size = validSoStatus.size();
				Integer[] statusId = validSoStatus.toArray(new Integer[size]);
				response.setStatusIdArray(statusId);
			}
		}catch (Exception e) {
			LOGGER.error("EXception in validating service order status"+ e);
			throw new BusinessServiceException(e);
		}
		
		return response;
	}
   
   /**
    * @param resultsCode
    * @return
    */
    private GetSubStatusResponse setErrorResponse( ResultsCode resultsCode) {
		GetSubStatusResponse  response =  new GetSubStatusResponse();
		Results results =new Results();
		List<ErrorResult> resultList =new ArrayList<ErrorResult>();
		ErrorResult error =new ErrorResult();
		error.setCode(resultsCode.getCode());
		error.setMessage(resultsCode.getMessage());
		resultList.add(error);
		results.setError(resultList);
		response.setResults(results);
		return response;
	}
    
    

	public ServiceOrderMonitorBO getServiceOrderMonitorBO() {
		return serviceOrderMonitorBO;
	}

	public void setServiceOrderMonitorBO(ServiceOrderMonitorBO serviceOrderMonitorBO) {
		this.serviceOrderMonitorBO = serviceOrderMonitorBO;
	}

	public SoSubStatusMapper getSubstatusMapper() {
		return substatusMapper;
	}

	public void setSubstatusMapper(SoSubStatusMapper substatusMapper) {
		this.substatusMapper = substatusMapper;
	}
}
