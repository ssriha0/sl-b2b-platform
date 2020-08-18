/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

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
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This is the service class for executing the Accept Reschedule request
 * 
 * @author Infosys
 * @version 1.0
 */
public class AcceptRescheduleRequestService extends SOBaseService {
	private Logger logger = Logger
			.getLogger(AcceptRescheduleRequestService.class);
	private IServiceOrderBO serviceOrderBO;

	public AcceptRescheduleRequestService() {
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
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		SOAcceptRescheduleResponse soAcceptRescheduleResponse = new 
						SOAcceptRescheduleResponse();
		logger.info("Entering AcceptRescheduleRequestService.execute()");
		String buyerId = (String) apiVO
				.getProperty(APIRequestVO.BUYERID);
		String soId = (String) apiVO.getProperty(APIRequestVO.SOID);
		
		SOAcceptRescheduleRequest soRescheduleRequest = 
			(SOAcceptRescheduleRequest) apiVO.getRequestFromPostPut();
		if (null != buyerId && null != soId) {
			try {
				securityContext = getSecurityContextForBuyerAdmin(
						new Integer(buyerId));
			} catch (NumberFormatException nme) {
				logger.error("AcceptRescheduleRequestService.execute(): " +
										"Number Format Exception "
										+ "occurred for resourceId: "
										+ buyerId, nme);
			} catch (Exception exception) {
				logger.error("Exception occurred while accessing security " +
						"context using resourceId");
			}
			if (securityContext == null) {
				logger.error("SecurityContext is null");
				Results results = Results.getError(
						ResultsCode.INVALID_RESOURCE_ID.getMessage(),
						ResultsCode.INVALID_RESOURCE_ID.getCode());
				soAcceptRescheduleResponse.setResults(results);

				return soAcceptRescheduleResponse;

			} else {
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
						logger
								.error("The BuyerId in ServiceOrder not " +
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
							.error("No reschedule requests to accept/reject");
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
					} else {
						Results results = Results
								.getError(ResultsCode.SERVICEORDER_NOT_FOUND
										.getMessage(),
										ResultsCode.SERVICEORDER_NOT_FOUND
												.getCode());
						soAcceptRescheduleResponse.setResults(results);

						return soAcceptRescheduleResponse;
					}
				} catch (Exception e) {
					Results results = Results.getError(
							ResultsCode.ACCEPT_RESCEDULE_FAILURE.getMessage(),
							ResultsCode.ACCEPT_RESCEDULE_FAILURE
									.getCode());
					soAcceptRescheduleResponse.setResults(results);

					return soAcceptRescheduleResponse;
				}

			}
		}
		return soAcceptRescheduleResponse;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	/*
	 * As this is the Accept service, I only expect Accept messages here. We will
	 * bypass the response type check and directly request OF to accept this
	 * Reschedule request. 
	 */
    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        String soId = (String) apiVO.getProperty(APIRequestVO.SOID);
        String buyerId = (String) apiVO.getProperty(APIRequestVO.BUYERID);
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(new Integer(buyerId));
        Identification ident = OFMapper.createBuyerIdentFromSecCtx(securityContext);
        OrderFulfillmentResponse response =ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_ACCEPT_RESCHEDULE, new SOSchedule(), ident);
        return mapOFResponse(response);
    }
    
    private IAPIResponse mapOFResponse(OrderFulfillmentResponse response){
        SOAcceptRescheduleResponse returnVal = new SOAcceptRescheduleResponse();
        if(!response.getErrors().isEmpty()){
            returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.ACCEPT_RESCEDULE_FAILURE.getCode()));
        }else{
            returnVal.setResults(Results.getSuccess(ResultsCode.ACCEPT_RESCEDULE_SUCCESS.getMessage()));
        }
        return returnVal;
    }
}
