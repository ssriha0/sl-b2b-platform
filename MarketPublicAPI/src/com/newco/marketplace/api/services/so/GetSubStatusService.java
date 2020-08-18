package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.substatusrespose.GetSubStatusResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SoSubStatusMapper;
import com.newco.marketplace.api.validator.so.SoValidator;
import com.newco.marketplace.business.businessImpl.serviceorder.ServiceOrderMonitorBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;

public class GetSubStatusService extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(GetSubStatusService.class);
	private SoValidator soValidator;
	private SoSubStatusMapper substatusMapper;
	private ServiceOrderMonitorBO serviceOrderMonitorBO;
	public GetSubStatusService() {
		super(null,
				PublicAPIConstant.GET_SUBSTATUS_RESPONSE_XSD,
				PublicAPIConstant.GET_SUBSTATUS_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.GET_SUBSTATUS_NAMESPACE_SCHEMALOCATION,
				null, GetSubStatusResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		GetSubStatusResponse response = null;
		List<String> soStausList =null;
		String soStaus = null;
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		//Getting status  passed as a query @param in API request
		if(null!= requestMap){
			soStaus =requestMap.get(PublicAPIConstant.SO_STAUS_LIST);
		}
		if(StringUtils.isNotBlank(soStaus)){
			soStausList = new ArrayList<String>();
			StringTokenizer strTok = new StringTokenizer(soStaus,PublicAPIConstant.SO_SEPERATOR, false);
				while(strTok.hasMoreTokens()){
					soStausList.add(strTok.nextToken());
				}
		}
		try{
		  response = soValidator.validatesoSubStatus(soStausList);
		  if(null!= response && null!= response.getResults() && null!= response.getResults().getError() &&  !response.getResults().getError().isEmpty()){
			  response= substatusMapper.createErrorResponse(response.getResults().getError());
		  }else{
			  if(null!=response.getStatusIdArray()){
				  Map<Integer,List<ServiceOrderSubStatusVO>> soSubStatusMap = serviceOrderMonitorBO.getAllSubStatus(response.getStatusIdArray());
				  if(null!= soSubStatusMap && !soSubStatusMap.isEmpty()){
					  response= substatusMapper.mapSoSubStatusResponse(soSubStatusMap,response.getStatusIdArray(),response.getStatusNameMap());
				  }else{
					  response= substatusMapper.createErrorResponse();
				  }
			  }else{
				  response= substatusMapper.createErrorResponse();
			  }
		  }
		}catch (Exception e) {
			LOGGER.error("Exception in getting sub status for the status"+ soStaus +"is" + e);
			response = substatusMapper.createErrorResponse();
		}
		
		
		return response;
	}

	public SoValidator getSoValidator() {
		return soValidator;
	}

	public void setSoValidator(SoValidator soValidator) {
		this.soValidator = soValidator;
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
