/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.CallDetails;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.GetCallInfoResponse;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Get Call Info
 * 
 * @author Infosys
 * @version 1.0
 */
public class GetCallInfoService extends SOBaseService {
	private Logger logger = Logger.getLogger(GetCallInfoService.class);

	private OrderManagementService managementService;



	private OrderManagementMapper omMapper ;
	ProcessResponse processResponse= new ProcessResponse();

	public GetCallInfoService() {
		super(null,
				PublicAPIConstant.GET_CALLINFO_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null, GetCallInfoResponse.class);
	}



	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside GetCallInfoService.execute()");
		Results results = null;
		GetCallInfoResponse callInfoResponse = new GetCallInfoResponse();
		HashMap<String,List<LookupVO>> filters ;
		String firmId = (String) apiVO
				.getProviderId();
	
		String soId = (String) apiVO.getSOId();
		if (null != firmId && null != soId) {

			
			CallDetails callDetails = managementService.getCallDetails(firmId,soId);
			
			callInfoResponse = omMapper.mapGetCallDetailsResponse(callDetails);



		}
		logger.info("Leaving GetCallInfoService.execute()");

		return callInfoResponse;
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




}