/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-nov-2009	KMSTRSUP   Infosys				1.0
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
import com.newco.marketplace.api.beans.so.reschedule.SOAcceptRescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SOAcceptRescheduleResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
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
 * This is the service class for executing the Accept Reschedule request
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORescheduleResponseService extends SOBaseService {
	private Logger logger = Logger
			.getLogger(SORescheduleResponseService.class);
	private IServiceOrderBO serviceOrderBO;
	//added for R12.0 to Update NPS when the buyer accepts reschedule request using 'accept reschedule request' web service for in-home service orders
	private INotificationService notificationService;
	
	public SORescheduleResponseService() {
		super(PublicAPIConstant.ACCEPT_RESCHEDULE_XSD, 
				PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION, 
				SOAcceptRescheduleRequest.class,
				SOAcceptRescheduleResponse.class);

	}

	/**
	 * This method dispatches the Accept Reschedule Request.
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return IAPIResponse
	 */
    @Override
    protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {

		SecurityContext securityContext = null;
		SOAcceptRescheduleResponse soAcceptRescheduleResponse = new 
						SOAcceptRescheduleResponse();
		logger.info("Entering SORescheduleResponseService.execute()");
		Integer buyerId = apiVO.getBuyerIdInteger();				
		String soId = apiVO.getSOId();		
		SOAcceptRescheduleRequest soRescheduleRequest = 
			(SOAcceptRescheduleRequest) apiVO.getRequestFromPostPut();
		securityContext = getSecurityContextForBuyerAdmin(buyerId);
		int companyId = securityContext.getCompanyId();
		ServiceOrder serviceOrder = null;
			// Getting service order details
		try {
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
			if (serviceOrder.getBuyer().getBuyerId() == null
							|| (!(serviceOrder.getBuyer().getBuyerId()
									.equals(companyId)))) {
					logger.info("The BuyerId in ServiceOrder not " +
										"authorized !!!");
						Results results = Results.getError(
								ResultsCode.ACCEPT_RESCEDULE_NOT_AUTHORIZED
										.getMessage(),
								ResultsCode.ACCEPT_RESCEDULE_NOT_AUTHORIZED
										.getCode());
						soAcceptRescheduleResponse.setResults(results);

						return soAcceptRescheduleResponse;
			}
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
									soRescheduleRequest.getRescheduleResponse())){
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
									soAcceptRescheduleResponse.setResults(results);
	
									return soAcceptRescheduleResponse;
								} else {
									Results results = Results.getSuccess(
											ResultsCode.ACCEPT_RESCEDULE_SUCCESS
													.getMessage());
									soAcceptRescheduleResponse.setResults(
											results);
	
								}
						}else if(
									PublicAPIConstant.
									RESCHEDULE_RESPONSE_REJECT.equals(
									soRescheduleRequest.getRescheduleResponse())){
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
									soAcceptRescheduleResponse.setResults(
											results);
									return soAcceptRescheduleResponse;
								} else {
									Results results = Results.getSuccess(
										ResultsCode.REJECT_RESCEDULE_SUCCESS
												.getMessage());
									soAcceptRescheduleResponse.setResults(
											results);
								}							
							}
						} else {
							logger
							.info("No reschedule requests to accept/reject");
							Results results = Results.getError(
									ResultsCode.ACCEPT_RESCEDULE_NOREQUEST
											.getMessage()
											+ soRescheduleRequest.
											getRescheduleResponse() ,
									ResultsCode.ACCEPT_RESCEDULE_NOREQUEST
											.getCode());
							soAcceptRescheduleResponse.setResults(results);

							return soAcceptRescheduleResponse;

						}
					} 
				} catch (Exception e) {
					Results results = Results.getError(
							ResultsCode.ACCEPT_RESCEDULE_FAILURE.getMessage(),
							ResultsCode.ACCEPT_RESCEDULE_FAILURE
									.getCode());
					soAcceptRescheduleResponse.setResults(results);

					return soAcceptRescheduleResponse;
				}
		logger.info("Leaving SORescheduleResponseService.execute()");
		return soAcceptRescheduleResponse;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
    	String rescheduleMessage = "";
    	List<Parameter> parameters = new ArrayList<Parameter>();
        Integer buyerId = apiVO.getBuyerIdInteger();                
        String soId = apiVO.getSOId();
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
        SOAcceptRescheduleRequest soRescheduleRequest = (SOAcceptRescheduleRequest) apiVO.getRequestFromPostPut();
        Identification ident = OFMapper.createBuyerIdentFromSecCtx(securityContext);
        if(StringUtils.equalsIgnoreCase(PublicAPIConstant.RESCHEDULE_RESPONSE_ACCEPT, soRescheduleRequest.getRescheduleResponse())){
        	if(PublicAPIConstant.INHOME_BUYER_ID.equals(buyerId)){
	        	//R12.0 Update NPS when the buyer accepts reschedule request using 'accept reschedule request' web service for in-home service orders START
	          	// InHome NPS Notification for accept reschedule reschedule.
	        	InHomeRescheduleVO input=new InHomeRescheduleVO();
	            input.setSoId(soId);
	            input.setIsNPSMessageRequired(true);
	        	InHomeRescheduleVO result=new InHomeRescheduleVO();
	        	try{
	        	result=notificationService.getSoDetailsForReschedule(input);
	        	}catch(Exception e){
	        		logger.info("exception in getting SO details"+e);
	        	}
	        	rescheduleMessage = result.getRescheduleMessage(); 
	        	logger.info("respondToRescheduleRequest.rescheduleMessage : "+rescheduleMessage);
        	}
        	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE, rescheduleMessage));
        	
        	//Setting call code for creating request xml of Service Operation API
        	parameters.add(new Parameter(OrderfulfillmentConstants.INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE, InHomeNPSConstants.RESCHD_CALLCODE));
        	
        	//R12.0 Update NPS when the buyer accepts reschedule request using 'accept reschedule request' web service for in-home service orders END
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_ACCEPT_RESCHEDULE, new SOSchedule(),ident,parameters);
            return mapOFResponse(response,ResultsCode.ACCEPT_RESCEDULE_SUCCESS);
        }else if(StringUtils.equalsIgnoreCase(PublicAPIConstant.RESCHEDULE_RESPONSE_REJECT, soRescheduleRequest.getRescheduleResponse())){
            OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_REJECT_RESCHEDULE, new SOSchedule(),ident);
            return mapOFResponse(response,ResultsCode.REJECT_RESCEDULE_SUCCESS);
        }else{
            return null;
        }
    }
    
    private IAPIResponse mapOFResponse(OrderFulfillmentResponse response, ResultsCode responseCode){
        SOAcceptRescheduleResponse returnVal = new SOAcceptRescheduleResponse();
        if(!response.getErrors().isEmpty()){
            returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.FAILURE.getCode()));
        }else{
            returnVal.setResults(Results.getSuccess(responseCode.getMessage()));
        }
        return returnVal;
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
