/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.reasonCodes.ReasonCode;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.LuProviderRespReasonVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Get Reason Codes
 * 
 * @author Infosys
 * @version 1.0
 */
public class GetReasonCodesService extends SOBaseService {
	private Logger logger = Logger.getLogger(GetReasonCodesService.class);

	private OrderManagementService managementService;

	private OrderManagementMapper omMapper ;
	private ILookupBO lookupBO;

	ProcessResponse processResponse= new ProcessResponse();

	public GetReasonCodesService() {
		super(null,
				PublicAPIConstant.GET_REASONCODES_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null, GetReasonCodesResponse.class);
	}



	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside GetReasonCodesService.execute()");
		GetReasonCodesResponse getReasonCodesResponse = new GetReasonCodesResponse();
		String reasonType = (String)apiVO.getProperty("reasonType") ;
		LuProviderRespReasonVO luReasonVO = new LuProviderRespReasonVO();
		luReasonVO.setSearchByResponse(Integer.parseInt(reasonType));
		if (null != reasonType) {
			ArrayList<LuProviderRespReasonVO> al = null;
			try {
				al = lookupBO.getProviderRespReason(luReasonVO);
			} catch (com.newco.marketplace.exception.core.DataServiceException e) {
				e.printStackTrace();
			}
			
			 List<ReasonCode> reasonList = mapReasonCode(al);
			getReasonCodesResponse = omMapper.mapReasonCodeResponse(reasonList);
		}
		logger.info("Leaving GetReasonCodesService.execute()");

		return getReasonCodesResponse;
	}
	
	private List<ReasonCode> mapReasonCode(ArrayList<LuProviderRespReasonVO> reasonCodes){
		 List<ReasonCode> reasonList = new ArrayList<ReasonCode>();
		 if(null != reasonCodes){
			 for(LuProviderRespReasonVO reason:reasonCodes){
				 ReasonCode reasonCode = new ReasonCode();
				 reasonCode.setReasonCode(reason.getDescr());
				 reasonCode.setReasonCodeId(reason.getRespReasonId());
				 reasonList.add(reasonCode);
			 }
		 }
		 return reasonList;
	}
	public OrderManagementService getManagementService() {
		return managementService;
	}



	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}



	public OrderManagementMapper getOmMapper() {
		return omMapper;
	}



	public void setOmMapper(OrderManagementMapper omMapper) {
		this.omMapper = omMapper;
	}



	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
	}



	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}



	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}


}