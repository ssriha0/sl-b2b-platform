/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.offer.CreateCounterOfferRequest;
import com.newco.marketplace.api.beans.so.reschedule.ProviderRescheduleResponseRequest;
import com.newco.marketplace.api.beans.so.reschedule.SOProviderRescheduleResponse;
import com.newco.marketplace.api.beans.so.reschedule.SORejectRescheduleResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;

/**
 * This is the service class for executing the Reject Reschedule request
 * for provider
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOProviderRescheduleResponseService extends SOBaseService {
	private Logger logger = Logger
			.getLogger(RejectRescheduleRequestService.class);
	private IServiceOrderBO serviceOrderBO;
	private INotificationService notificationService;

	public SOProviderRescheduleResponseService() {
		super(PublicAPIConstant.PROVIDER_RESCHEDULE_PRO_RESPONSE_XSD, PublicAPIConstant.SOPROVIDERRESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_PROVIDER_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.SOPRORESPONSE_SCHEMALOCATION,ProviderRescheduleResponseRequest.class,
				SOProviderRescheduleResponse.class);

	}

	/**
	 * This method dispatches the Accept Reschedule Request.
	 * R12.0 : This method will be a legacy method and will not be used for new service orders. 
	 * @param apiVO
	 *            APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		
		SOProviderRescheduleResponse soProviderRescheduleResponse = new SOProviderRescheduleResponse();
		ProviderRescheduleResponseRequest providerRescheduleResponseRequest=
			(ProviderRescheduleResponseRequest) apiVO
		.getRequestFromPostPut();
		logger.info("Entering RejectRescheduleRequestService.execute()");
		String providerId=(String) apiVO.getProviderId();
		String soId = (String) apiVO.getSOId();
		ServiceOrder serviceOrder = null;
		
			// Getting service order details
		try {
			securityContext = getSecurityContextForVendorAdmin(new Integer(providerId));		
			int companyId = securityContext.getCompanyId();
			
			int role = -1;
			ServiceOrderRescheduleVO reschedule = null;
			serviceOrder = serviceOrderBO.getServiceOrder(soId);
			reschedule = serviceOrderBO.getRescheduleRequestInfo(soId);
			if (reschedule != null && reschedule.getUserRole() != null) {
					role = reschedule.getUserRole();
			} else {
					role = -1;
			}
			int state = serviceOrder.getWfStateId();
		
			if (serviceOrder != null) {
				if (serviceOrder.getRescheduleServiceDate1() != null
								&& (!"".equals(serviceOrder
										.getRescheduleServiceDate1()))
								&& (state == OrderConstants.ACCEPTED_STATUS
										|| state == OrderConstants.ACTIVE_STATUS 
										|| state == OrderConstants.PROBLEM_STATUS)
								&& (role != securityContext.getRoleId())) {
							//if reschedule response is accept
							if(PublicAPIConstant.RESCHEDULE_RESPONSE_ACCEPT.equals(
									providerRescheduleResponseRequest.getRescheduleResponse())){
								ProcessResponse processResponse = serviceOrderBO
										.acceptRescheduleRequest(serviceOrder
												.getSoId(), true, securityContext
												.getRoleId(), securityContext
												.getCompanyId(), securityContext);
								if (!(processResponse.getCode()
										.equalsIgnoreCase(
												ServiceConstants.VALID_RC))) {
									Results results = Results.getError(
											processResponse.getMessages().get(0),
											ResultsCode.ACCEPT_RESCEDULE_FAILURE
													.getCode());
									soProviderRescheduleResponse.setResults(results);
	
									return soProviderRescheduleResponse;
								} else {
									Results results = Results.getSuccess(
											ResultsCode.ACCEPT_RESCEDULE_SUCCESS
													.getMessage());
									soProviderRescheduleResponse.setResults(
											results);
	
								}
						}else if(
									PublicAPIConstant.
									RESCHEDULE_RESPONSE_REJECT.equals(
											providerRescheduleResponseRequest.getRescheduleResponse())){
								ProcessResponse processResponse = serviceOrderBO
										.rejectRescheduleRequest(serviceOrder
												.getSoId(), false, securityContext
												.getRoleId(), securityContext
												.getCompanyId(), securityContext);
								if (!(processResponse.getCode()
										.equalsIgnoreCase(
												ServiceConstants.VALID_RC))) {
									Results results = Results.getError(
											processResponse.getMessages().get(0),
											ResultsCode.REJECT_RESCEDULE_FAILURE
													.getCode());
									soProviderRescheduleResponse.setResults(
											results);
									return soProviderRescheduleResponse;
								} else {
									Results results = Results.getSuccess(
										ResultsCode.REJECT_RESCEDULE_SUCCESS
												.getMessage());
									soProviderRescheduleResponse.setResults(
											results);
								}							
							}
						} else {
							logger
							.info("No reschedule requests to accept/reject");
							Results results = Results.getError(
									ResultsCode.ACCEPT_RESCEDULE_NOREQUEST
											.getMessage()
											+ providerRescheduleResponseRequest.
											getRescheduleResponse() ,
									ResultsCode.ACCEPT_RESCEDULE_NOREQUEST
											.getCode());
							soProviderRescheduleResponse.setResults(results);

							return soProviderRescheduleResponse;

						}
					} 
				} catch (Exception e) {
					Results results = Results.getError(
							ResultsCode.ACCEPT_RESCEDULE_FAILURE.getMessage(),
							ResultsCode.ACCEPT_RESCEDULE_FAILURE
									.getCode());
					soProviderRescheduleResponse.setResults(results);

					return soProviderRescheduleResponse;
				}
		logger.info("Leaving SORescheduleResponseService.execute()");
		return soProviderRescheduleResponse;
	}
	
	// R12.0 SLM-12: Modifying existing provider accept/reject reschedule api to OF.
	// This method will act as the new execute method for provider accept/reject reschedule.
	@Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        Integer providerId = (null != apiVO.getProviderId()?Integer.parseInt(apiVO.getProviderId()):null);
        String soId = apiVO.getSOId();
        String rescheduleMessage = "";
        SecurityContext securityContext = getSecurityContextForVendorAdmin(providerId);
        ProviderRescheduleResponseRequest soRescheduleRequest = (ProviderRescheduleResponseRequest) apiVO.getRequestFromPostPut();
        Identification ident = OFMapper.createProviderIdentFromSecCtx(securityContext);
        if(StringUtils.equalsIgnoreCase(PublicAPIConstant.RESCHEDULE_RESPONSE_ACCEPT, soRescheduleRequest.getRescheduleResponse())){
        	// R12.0 Update NPS when the provider accepts reschedule request using 'accept reschedule request' web service for in-home service orders
          	// InHome NPS Notification for accept reschedule reschedule.
        	InHomeRescheduleVO input=new InHomeRescheduleVO();
        	InHomeRescheduleVO result=new InHomeRescheduleVO();
        	input.setSoId(soId);
        	input.setIsNPSMessageRequired(true);
        	try{
        		result=notificationService.getSoDetailsForReschedule(input);
        	}catch(Exception e){
        		logger.info("exception in getting SO details"+e);
        	}
        	List<Parameter> parameters = new ArrayList<Parameter>();
        	rescheduleMessage = result.getRescheduleMessage();
        	logger.info("respondToRescheduleRequest.rescheduleMessage : "+rescheduleMessage);
        	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE, rescheduleMessage));
        	
        	//Setting call code for creating request xml of Service Operation API
        	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
        	
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_ACCEPT_RESCHEDULE, new SOSchedule(),ident,parameters);
            return mapOFResponse(response,ResultsCode.ACCEPT_RESCEDULE_SUCCESS);
        }else if(StringUtils.equalsIgnoreCase(PublicAPIConstant.RESCHEDULE_RESPONSE_REJECT, soRescheduleRequest.getRescheduleResponse())){
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_REJECT_RESCHEDULE, new SOSchedule(),ident);
            return mapOFResponse(response,ResultsCode.REJECT_RESCEDULE_SUCCESS);
        }else{
            return null;
        }
    }
    
    private IAPIResponse mapOFResponse(OrderFulfillmentResponse response, ResultsCode responseCode){
    	SOProviderRescheduleResponse returnVal = new SOProviderRescheduleResponse();
        if(!response.getErrors().isEmpty()){
            returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode()));
        }else{
            returnVal.setResults(Results.getSuccess(responseCode.getMessage()));
        }
        return returnVal;
    }    

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	
	/**
	 * @return the notificationService
	 */
	public INotificationService getNotificationService() {
		return notificationService;
	}

	/**
	 * @param notificationService the notificationService to set
	 */
	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
