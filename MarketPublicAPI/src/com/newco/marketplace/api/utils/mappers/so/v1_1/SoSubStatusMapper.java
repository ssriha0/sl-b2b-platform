package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.substatusrespose.GetSubStatusResponse;
import com.newco.marketplace.api.beans.substatusrespose.SoSubStatus;
import com.newco.marketplace.api.beans.substatusrespose.SoSubStatuses;
import com.newco.marketplace.api.beans.substatusrespose.SubStatus;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class SoSubStatusMapper {
	
	private Logger LOGGER = Logger.getLogger(SoSubStatusMapper.class);
	
	/**@Description : This will create internal Server Error Message for sub status get Request API
	 * @return
	 */
	public GetSubStatusResponse createErrorResponse() {
		GetSubStatusResponse response = new GetSubStatusResponse();
		ResultsCode error =ResultsCode.INTERNAL_SERVER_ERROR;
		response.setResults(Results.getError(error.getMessage(), error.getCode()));
		return response;
	}

	/**@Description : This will create  Error Response for status validation and buyer id validation
	 * @return
	 */
	public GetSubStatusResponse createErrorResponse(List<ErrorResult> results) {
		GetSubStatusResponse response = new GetSubStatusResponse();
		if(null!=results && null!= results.get(0)){
			response.setResults(Results.getError(results.get(0).getMessage(), results.get(0).getCode()));
		}
		return response;
	}

	/**
	 * @param soSubStatusMap
	 * @param statusId
	 * @param statusNameMap 
	 * @return
	 */
	public GetSubStatusResponse mapSoSubStatusResponse(Map<Integer, List<ServiceOrderSubStatusVO>> soSubStatusMap, Integer[] statusId, Map<Integer, String> statusNameMap) {
		GetSubStatusResponse response = new GetSubStatusResponse();
		//setting success Response
		Results successResult = Results.getSuccess();
		List<SoSubStatuses> soSubStatusesList = new ArrayList<SoSubStatuses>();
		for(int i=0; i < statusId.length;i++){
			Integer wfStateId = statusId[i];
			SoSubStatuses soSubStatus = new SoSubStatuses();
			List<SubStatus> subStatusList = new ArrayList<SubStatus>();
			if(null!= soSubStatusMap && !soSubStatusMap.isEmpty() && null!=statusNameMap && !statusNameMap.isEmpty()){
			   List<ServiceOrderSubStatusVO> subStatusVOList = soSubStatusMap.get(wfStateId);
			   String statusName = statusNameMap.get(wfStateId);
			   soSubStatus.setStatusValue(statusName);
			   for(ServiceOrderSubStatusVO subStatusVO :subStatusVOList ){
				   if(null!= subStatusVO && StringUtils.isNotBlank(subStatusVO.getSubStatusName())){
					   SubStatus subStatus = new SubStatus();
					   if(!PublicAPIConstant.MOBILE_SUB_STATUS().contains(subStatusVO.getSubStatusId())){
					      subStatus.setId(subStatusVO.getSubStatusId());
					      subStatus.setDescription(subStatusVO.getSubStatusName());
					      subStatusList.add(subStatus);
					   }
					 }
			   }
			   soSubStatus.setSubStatus(subStatusList);
			}
			soSubStatusesList.add(soSubStatus);
		}
		response.setSoSubStatuses(soSubStatusesList);
		response.setResults(successResult);
		return response;
	}
	/**
     * @param response
     * @return
     * @throws BusinessServiceException
     */
    public GetSubStatusResponse setStatusIdArray(GetSubStatusResponse response,List<ServiceOrderStatusVO> soStatusListDB) throws BusinessServiceException {
		try{
			if(null!= soStatusListDB && !soStatusListDB.isEmpty()){
				int size = soStatusListDB.size();
				Integer statusId []= new Integer[size];
			    for(int i=0;i< soStatusListDB.size();i++){
					statusId[i] = soStatusListDB.get(i).getStatusId();
				}	
			    response.setStatusIdArray(statusId);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in getting the status id available for the buyer"+ e);
			throw new BusinessServiceException(e);
		}
		return response;
	}

	/**
	 * @param soStatusListDB
	 * @param response 
	 * @return
	 */
	public GetSubStatusResponse setStatusNameMap(List<ServiceOrderStatusVO> soStatusListDB, GetSubStatusResponse response) {
		Map<Integer, String> statusNameMap = new HashMap<Integer, String>();
		if(null!= soStatusListDB && !soStatusListDB.isEmpty()){
			for(ServiceOrderStatusVO statusVO : soStatusListDB){
				if(null != statusVO && StringUtils.isNotBlank(statusVO.getStatusName())){
				  statusNameMap.put(new Integer(statusVO.getStatusId()), statusVO.getStatusName());
				}
			}
		}
		response.setStatusNameMap(statusNameMap);
		return response;
	}
}
