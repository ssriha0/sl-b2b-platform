/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.ordermanagement.v1_0;


import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.providerCall.SOProviderCallRequest;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.providerCall.SOProviderCallResponse;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.ordermanagement.services.OrderManagementService;
/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOProviderCallService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOProviderCallService.class);

	private OrderManagementService managementService;



	private OrderManagementMapper omMapper ;
	ProcessResponse processResponse= new ProcessResponse();

	public SOProviderCallService() {
		super(PublicAPIConstant.SO_PROVIDER_CALL_REQUEST_XSD,
				PublicAPIConstant.SO_PROVIDER_CALL_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOProviderCallRequest.class, SOProviderCallResponse.class);
	}



	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		Results results = null;
		SOProviderCallResponse providerCallResponse = new SOProviderCallResponse();
		logger.info("Entering SOProviderCallService.execute()");
		String firmId = (String) apiVO
				.getProviderId();
	
		String soId = (String) apiVO.getSOId();
		if (null != firmId && null != soId) {
			try {
				securityContext = getSecurityContextForVendorAdmin(new Integer(firmId));
			} catch (NumberFormatException nme) {
				/*logger.error("CompleteRequestService.execute(): "
						+ "Number Format Exception occurred for resourceId:"
						+ resourceId, nme);*/
			} 

			SOProviderCallRequest providerCallRequest = (SOProviderCallRequest)apiVO.getRequestFromPostPut();
			PreCallHistory callHistory = new PreCallHistory();
			callHistory.setReason(providerCallRequest.getReason());
			callHistory.setScheduleStatus(providerCallRequest.getScheduleStatus());
			
			managementService.providerCall(callHistory,providerCallRequest.getSoNotes(),providerCallRequest.getSpecialInstructions()) ;
			
			
			providerCallResponse = omMapper.mapResponseForProviderCall();



		}
		logger.info("Leaving SOProviderCallService.execute()");

		return providerCallResponse;
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