/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reschedule.SORejectRescheduleResponse;
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
 * This is the service class for executing the Reject Reschedule request
 * 
 * @author Infosys
 * @version 1.0
 */
public class RejectRescheduleRequestService extends SOBaseService {
	private Logger logger = Logger
			.getLogger(RejectRescheduleRequestService.class);
	private IServiceOrderBO serviceOrderBO;

	public RejectRescheduleRequestService() {
		super(PublicAPIConstant.REQUEST_XSD, PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION, null,
				SORejectRescheduleResponse.class);

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
		Results results = null;
		SORejectRescheduleResponse soRejectRescheduleResponse = new SORejectRescheduleResponse();
		logger.info("Entering RejectRescheduleRequestService.execute()");
		String resourceId = (String) apiVO
				.getProperty(APIRequestVO.BUYER_RESOURCE_ID);
		String soId = (String) apiVO.getProperty(APIRequestVO.SOID);
		if (null != resourceId && null != soId) {
			try {
				securityContext = getSecurityContext(new Integer(resourceId));
			} catch (NumberFormatException nme) {
				logger.error("RejectRescheduleRequestService.execute(): "
						+ "Number Format Exception occurred for resourceId:"
						+ resourceId, nme);
			} catch (Exception exception) {
				logger.error("Exception occurred while accessing security "
						+ "context using resourceId", exception);
			}
			if (securityContext == null) {
				logger.error("SecurityContext is null");
				results = Results.getError(ResultsCode.INVALID_RESOURCE_ID
						.getMessage(), ResultsCode.INVALID_RESOURCE_ID
						.getCode());
				soRejectRescheduleResponse.setResults(results);

				return soRejectRescheduleResponse;

			} else {
				int buyerId = securityContext.getCompanyId();
				ServiceOrder serviceOrder = null;
				// Getting service order details
				try {
					serviceOrder = serviceOrderBO.getServiceOrder(soId);
					int state = serviceOrder.getWfStateId();
					if (serviceOrder.getBuyer().getBuyerId() == null
							|| (!(serviceOrder.getBuyer().getBuyerId()
									.equals(buyerId)))) {
						logger
								.error("The BuyerId in ServiceOrder not authorized !!!");
						results = Results.getError(
								ResultsCode.REJECT_RESCEDULE_NOT_AUTHORIZED
										.getMessage(),
								ResultsCode.REJECT_RESCEDULE_NOT_AUTHORIZED
										.getCode());
						soRejectRescheduleResponse.setResults(results);
						return soRejectRescheduleResponse;
					}
					if (serviceOrder != null) {
						int role = -1;
						ServiceOrderRescheduleVO rescheduleVO = null;
						rescheduleVO = serviceOrderBO
								.getRescheduleRequestInfo(serviceOrder
										.getSoId());
						if (rescheduleVO != null
								&& rescheduleVO.getUserRole() != null) {
							role = rescheduleVO.getUserRole();
						} else {
							role = -1;
						}
						if (serviceOrder.getRescheduleServiceDate1() != null
								&& (!"".equals(serviceOrder
										.getRescheduleServiceDate1()))
								&& (state == OrderConstants.ACCEPTED_STATUS
										|| state == OrderConstants.ACTIVE_STATUS || state == OrderConstants.PROBLEM_STATUS)
								&& (role != securityContext.getRoleId())) {
							ProcessResponse processResponse = serviceOrderBO
									.rejectRescheduleRequest(serviceOrder
											.getSoId(), false, securityContext
											.getRoleId(), securityContext
											.getCompanyId(), securityContext);
							if (!(processResponse.getCode()
									.equalsIgnoreCase(ServiceConstants.VALID_RC))) {
								results = Results.getError(processResponse
										.getMessages().get(0),
										ResultsCode.REJECT_RESCEDULE_FAILURE
												.getCode());
								soRejectRescheduleResponse.setResults(results);
								return soRejectRescheduleResponse;
							} else {
								results = Results
										.getSuccess(ResultsCode.REJECT_RESCEDULE_SUCCESS
												.getMessage());
								soRejectRescheduleResponse.setResults(results);
							}
						} else {
							results = Results.getError(
									ResultsCode.REJECT_RESCEDULE_NOREQUEST
											.getMessage(),
									ResultsCode.REJECT_RESCEDULE_NOREQUEST
											.getCode());
							soRejectRescheduleResponse.setResults(results);

							return soRejectRescheduleResponse;

						}
					} else {
						results = Results
								.getError(ResultsCode.SERVICEORDER_NOT_FOUND
										.getMessage(),
										ResultsCode.SERVICEORDER_NOT_FOUND
												.getCode());
						soRejectRescheduleResponse.setResults(results);
						return soRejectRescheduleResponse;
					}
				} catch (Exception e) {
					logger
							.error(
									"Exception Occurred inside RejectRescheduleRequest.execute: ",
									e);
					results = Results.getError(e.getMessage(),
							ResultsCode.INTERNAL_SERVER_ERROR.getCode());
					soRejectRescheduleResponse.setResults(results);
				}

			}
		}
		return soRejectRescheduleResponse;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        String soId = (String) apiVO.getProperty(APIRequestVO.SOID);
        String buyerId = (String) apiVO.getProperty(APIRequestVO.BUYERID);
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(new Integer(buyerId));
        Identification ident = OFMapper.createBuyerIdentFromSecCtx(securityContext);
        OrderFulfillmentResponse response =ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_REJECT_RESCHEDULE, new SOSchedule(), ident);
        return mapOFResponse(response);
    }
    
    private IAPIResponse mapOFResponse(OrderFulfillmentResponse response){
        SORejectRescheduleResponse returnVal = new SORejectRescheduleResponse();
        if(!response.getErrors().isEmpty()){
            returnVal.setResults(Results.getError(response.getErrorMessage(), ResultsCode.REJECT_RESCEDULE_FAILURE.getCode()));
        }else{
            returnVal.setResults(Results.getSuccess(ResultsCode.REJECT_RESCEDULE_SUCCESS.getMessage()));
        }
        return returnVal;
    }
}
