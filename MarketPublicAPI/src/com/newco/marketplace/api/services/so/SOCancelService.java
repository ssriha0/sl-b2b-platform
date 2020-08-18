/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 01-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.cancel.SOCancelRequest;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOCancelMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class is a service class for Canceling ,Voiding and Deleting Service
 * Orders
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCancelService {
	private Logger logger = Logger.getLogger(SOCancelService.class);
	private XStreamUtility conversionUtility;
	private SOCancelMapper cancelMapper;
	private SecurityProcess securityProcess;
	private IServiceOrderBO serviceOrderBO;
	ProcessResponse pResp = new ProcessResponse();

	/**
	 * This method dispatches the cancel service order request.
	 * @param String serviceOrderCancelRequest
	 * @param String soId
	 * @throws Exception
	 * @return String
	 */
	public String dispatchCancelServiceRequest(
			String soCancelRequest, String soId) throws Exception {
		logger.info("Entering SOCancelService.dispatchCancelServiceRequest()");
		String stringResponse = null;
		List<String> messageList = new ArrayList<String>();
		SOCancelRequest cancelRequest = conversionUtility
				.getCancelRequestObject(soCancelRequest);
		SecurityContext securityContext = securityProcess
				.getSecurityContext(cancelRequest.getIdentification()
						.getUsername(), cancelRequest.getIdentification()
						.getPassWord());
		if (securityContext == null) {
			logger.error("SecurityContext is null");
			throw new BusinessServiceException("SecurityContext is null");

		} else {
			int buyerId = securityContext.getCompanyId();
			
			// Getting service order details
			ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
			int roleId = securityContext.getRoleId();

			if (null != serviceOrder) {
				int stateId = serviceOrder.getWfStateId();
				// Cancel,Delete,Void service order according to the status of
				// the service order

				switch (stateId) {
					case OrderConstants.DRAFT_STATUS:
						// Checking if user is a buyer
						if (roleId == OrderConstants.BUYER_ROLEID
								|| roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
							logger.info("Deleting a service order in 'Draft' status");
							pResp = serviceOrderBO.deleteDraftSO(soId,
									securityContext);
						} else {
							pResp.setCode(ServiceConstants.USER_ERROR_RC);
							pResp.setMessage(OrderConstants.PROVIDER_IS_NOT_AUTHORIZED);
						}
						break;

					case OrderConstants.ROUTED_STATUS:
						// Checking if user is a buyer
						if (roleId == OrderConstants.BUYER_ROLEID
								|| roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
							logger.info("Voiding a service order in 'Routed' status");
							pResp = serviceOrderBO.processVoidSO(buyerId, soId,
									securityContext);
						} else {
							pResp.setCode(ServiceConstants.USER_ERROR_RC);
							pResp.setMessage(OrderConstants.PROVIDER_IS_NOT_AUTHORIZED);
						}
						break;

					case OrderConstants.EXPIRED_STATUS:
						// Checking if user is a buyer
						if (roleId == OrderConstants.BUYER_ROLEID
								|| roleId == OrderConstants.SIMPLE_BUYER_ROLEID) {
							logger.info("Voiding a service order in 'Expired' status");
							pResp = serviceOrderBO.processVoidSO(buyerId, soId,
									securityContext);
						} else {
							pResp.setCode(ServiceConstants.USER_ERROR_RC);
							pResp.setMessage(OrderConstants.PROVIDER_IS_NOT_AUTHORIZED);
						}
						break;

					default:
						logger.info("Service order is not in Accepted,Active,Draft,Expired,"
										+ "Routed status and cannot be cancelled");
					messageList.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_ERROR_CODE));
					pResp.setMessages(messageList);
					pResp.setCode(ServiceConstants.USER_ERROR_RC);
				}
				ServiceOrder soFinal = serviceOrderBO.getServiceOrder(soId);
				SOCancelResponse soCancelResponse = new SOCancelResponse();
				soCancelResponse = cancelMapper.mapServiceOrder(pResp, soFinal);
				stringResponse = conversionUtility
						.getCancelResponseXML(soCancelResponse);
			} else {
				return null;
			}
		}
		logger.info("Leaving SOCancelService.dispatchCancelServiceRequest()");
		return stringResponse;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setCancelMapper(SOCancelMapper cancelMapper) {
		this.cancelMapper = cancelMapper;
	}

	
	
}
