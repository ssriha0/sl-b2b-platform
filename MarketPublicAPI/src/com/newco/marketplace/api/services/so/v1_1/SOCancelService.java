/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 05-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOCancelMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This is the service class for executing the Cancel Service Order request
 * @author Infosys
 * @version 1.0
 */
public class SOCancelService extends SOBaseService {
	// Private variables
	private Logger logger = Logger.getLogger(SOCancelService.class);
	private IServiceOrderBO serviceOrderBO;
	private SOCancelMapper cancelMapper;

	//Public Constructor
	public SOCancelService () {
		super (PublicAPIConstant.REQUEST_XSD,
				PublicAPIConstant.SORESPONSE_XSD, 
				PublicAPIConstant.SORESPONSE_NAMESPACE, 
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,	
				null,
				SOCancelResponse.class);
	}
	
	/**
	 * This method dispatches the Cancel Service Order request.
	 */
	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		String svcOrdrId = apiVO.getSOId();
		int buyerId = apiVO.getBuyerIdInteger();
		SecurityContext securityContext = getSecCtxtForBuyerAdmin(buyerId);
		
		OrderFulfillmentRequest ofRequest = createOrderFulfillmentCancelSORequest(apiVO, securityContext);
		SignalType signalType = SignalType.CANCEL_ORDER;
		if(securityContext.getRoleId() == OrderConstants.BUYER_ROLEID){
			signalType = SignalType.NO_FEE_CANCEL_ORDER;
		}
		OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(svcOrdrId, signalType, ofRequest);
		if (!ofResponse.isSignalAvailable()){
			return createInvalidStateResponse();
		}
		if (ofResponse.isError()) {
			return createErrorResponse(ofResponse);			
		}
		
		return createCancelResponse(ofHelper.getServiceOrder(svcOrdrId));
	}

	private SOCancelResponse createInvalidStateResponse() {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = Results.getError(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_ERROR_CODE), 
				ResultsCode.FAILURE.getCode());
		soCancelResponse.setResults(results);
		return soCancelResponse;
	}

	private OrderFulfillmentRequest createOrderFulfillmentCancelSORequest(APIRequestVO apiVO, SecurityContext securityContext) {
	    OrderFulfillmentRequest returnVal = new OrderFulfillmentRequest();
	    returnVal.setIdentification(OFMapper.createBuyerIdentFromSecCtx(securityContext));

		String cancelComment = apiVO.getRequestFromGetDelete().get(PublicAPIConstant.cancelSO.CANCEL_COMMENT);
		if (!StringUtils.isBlank(cancelComment)) {
		    returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, cancelComment);
		}

		String cancelAmountString = apiVO.getRequestFromGetDelete().get(PublicAPIConstant.cancelSO.CANCEL_AMOUNT);
		if (!StringUtils.isBlank(cancelAmountString)) {
		    returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT, cancelAmountString);
		}
		
		return returnVal;
	}
	
	private SOCancelResponse createErrorResponse(OrderFulfillmentResponse ordrFlflmntResponse) {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		Results results = Results.getError(ordrFlflmntResponse.getErrorMessage(), 
				ServiceConstants.USER_ERROR_RC);
		soCancelResponse.setResults(results);
		return soCancelResponse;
	}
	
	
	private SOCancelResponse createCancelResponse(com.servicelive.orderfulfillment.domain.ServiceOrder svcOrdr) {
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		setOrderStatusInCancelResponse(soCancelResponse, svcOrdr);
		soCancelResponse.setResults(Results.getSuccess(OrderConstants.REQUEST_CANCELSO_SUCCESS));
		return soCancelResponse;
	}
	
	private void setOrderStatusInCancelResponse(SOCancelResponse soCancelResponse, com.servicelive.orderfulfillment.domain.ServiceOrder svcOrdr) {
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId(svcOrdr.getSoId());
		orderStatus.setStatus(svcOrdr.getWfStatus() == null ? "" : svcOrdr.getWfStatus());
		orderStatus.setSubstatus(svcOrdr.getWfSubStatus() == null ? "" : svcOrdr.getWfSubStatus());
		orderStatus.setCreatedDate(svcOrdr.getCreatedDate() == null ? "" : CommonUtility.sdfToDate.format(svcOrdr.getCreatedDate()));
		orderStatus.setPostedDate(svcOrdr.getRoutedDate() == null ? null : CommonUtility.sdfToDate.format(svcOrdr.getRoutedDate()));
		orderStatus.setAcceptedDate(svcOrdr.getAcceptedDate() == null ? null : CommonUtility.sdfToDate.format(svcOrdr.getAcceptedDate()));
		orderStatus.setActiveDate(svcOrdr.getActivatedDate() == null ? null : CommonUtility.sdfToDate.format(svcOrdr.getActivatedDate()));
		orderStatus.setCompletedDate(svcOrdr.getCompletedDate() == null ? null : CommonUtility.sdfToDate.format(svcOrdr.getCompletedDate()));
		orderStatus.setClosedDate(svcOrdr.getClosedDate() == null ? null : CommonUtility.sdfToDate.format(svcOrdr.getClosedDate()));
		
		soCancelResponse.setOrderstatus(orderStatus);
	}

	/**
	 * Legacy code to be eventually supplanted by executeOrderFulfillmentService().
	 */
	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {		
		logger.info("Entering SOCancelService.execute()");	
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		Results results = null;
		int buyerId = apiVO.getBuyerIdInteger();
		String soId =apiVO.getSOId();
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		String cancelComment = requestMap.get(PublicAPIConstant.cancelSO.CANCEL_COMMENT);
		String cancelAmountString = requestMap.get(PublicAPIConstant.cancelSO.CANCEL_AMOUNT);
		ProcessResponse pResp = new ProcessResponse();
		List<String> messageList = new ArrayList<String>();
		SecurityContext securityContext = null;
		try{
			securityContext = getSecurityContextForBuyerAdmin(buyerId);
		}catch(NumberFormatException nme){
			logger.error("SOCancelService.execute(): Number Format Exception " +
					"occurred for resourceId: " + buyerId, nme);
		}
		catch (Exception exception) {
			logger.error("SOCancelService.execute():  Exception occurred while " +
					"accessing security context with resourceId: " + buyerId, exception);
		}
		int buyer_Id = securityContext.getCompanyId();
		String buyerName=securityContext.getUsername();

		// Getting service order details
		try{
			ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
			int roleId = securityContext.getRoleId();
			ArrayList<Object> argumentList = new ArrayList<Object>();

			int stateId = serviceOrder.getWfStateId();

			// Cancel,Delete,Void service order according to the status of
			// the service order
			if(stateId==OrderConstants.ACCEPTED_STATUS){
				if(null==cancelComment){
					argumentList.add("("+PublicAPIConstant.cancelSO.CANCEL_COMMENT+")");
					results=Results.getError(
							ResultsCode.INVALID_OR_MISSING_PARAM.getMessage(argumentList), 
							ResultsCode.INVALID_OR_MISSING_PARAM.getCode());
					soCancelResponse.setResults(results);
					return soCancelResponse;
				}
			}else if(stateId == OrderConstants.ACTIVE_STATUS){
				if(null==cancelAmountString){
					argumentList.add("("+PublicAPIConstant.cancelSO.CANCEL_AMOUNT+")");
					results=Results.getError(
							ResultsCode.INVALID_OR_MISSING_PARAM.getMessage(argumentList), 
							ResultsCode.INVALID_OR_MISSING_PARAM.getCode());
					soCancelResponse.setResults(results);
					return soCancelResponse;
				}
			}

			if (roleId == OrderConstants.BUYER_ROLEID
					|| roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
				switch (stateId) {
				case OrderConstants.DRAFT_STATUS:
					logger.info("Deleting a service order in 'Draft' status");
					pResp = serviceOrderBO.deleteDraftSO(soId,
							securityContext);
					break;

				case OrderConstants.ROUTED_STATUS:
					logger.info("Voiding a service order in 'Routed' status");
					pResp = serviceOrderBO.processVoidSO(buyer_Id, soId,
							securityContext);
					break;

				case OrderConstants.EXPIRED_STATUS:
					logger.info("Voiding a service order in 'Expired' status");
					pResp = serviceOrderBO.processVoidSO(buyer_Id, soId,
							securityContext);
					break;

				case OrderConstants.ACCEPTED_STATUS:
					logger.info("Cancelling  a service order in 'Accepted' status");
					pResp = serviceOrderBO.processCancelSOInAccepted(
							buyer_Id, soId,cancelComment,buyerName,
							securityContext);
					break;

				case OrderConstants.ACTIVE_STATUS:
					Double cancelAmount=new Double(cancelAmountString);
					logger.info("Cancelling  a service order in 'Active' status");
					Double total = 0.0;
					if (serviceOrder.getSpendLimitLabor() != null)
						total += serviceOrder.getSpendLimitLabor();
					if (serviceOrder.getSpendLimitParts() != null)
						total += serviceOrder.getSpendLimitParts();
					if(cancelAmount<total){
						pResp = serviceOrderBO.processCancelRequestInActive(
								buyer_Id, soId,cancelAmount,
								buyerName,securityContext);
					}else{
						messageList.add(CommonUtility.getMessage(
								PublicAPIConstant.cancelSO.
								SO_CANCELLATION_AMOUNT_GREATER));
						pResp.setMessages(messageList);
						pResp.setCode(
								ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
					}
					break;

				default:
					logger.info("Service order is not in Accepted, Active, Draft," +
					"Expired or Routed status. Hence it cannot be cancelled");
					messageList.add(CommonUtility.getMessage(
							PublicAPIConstant.INVALID_STATE_ERROR_CODE));
					pResp.setMessages(messageList);
					pResp.setCode(ResultsCode.FAILURE.getCode());
				}
				ServiceOrder soFinal = serviceOrderBO.getServiceOrder(soId);
				soCancelResponse = cancelMapper.mapServiceOrder(pResp, soFinal);
			} else {
				results= Results.getError(ResultsCode.NOT_AUTHORISED.getMessage(),
						ResultsCode.NOT_AUTHORISED.getCode());
				soCancelResponse.setResults(results);
			}



		}catch(Exception e){
			logger.error("Exception Occurred inside SOCancelService.execute: ", e);
			results = Results.getError(e.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soCancelResponse.setResults(results);
		}

		logger.info("Exiting  SOCancelService.execute()");
		return soCancelResponse;
	}

	// Setter methods for Spring Injection {References in 
	// Spring Configuration file: apiApplicationContext.xml}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SOCancelMapper getCancelMapper() {
		return cancelMapper;
	}

	public void setCancelMapper(SOCancelMapper cancelMapper) {
		this.cancelMapper = cancelMapper;
	}
}
