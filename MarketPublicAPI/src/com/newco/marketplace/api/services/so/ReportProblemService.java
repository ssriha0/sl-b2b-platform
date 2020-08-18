/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 19-Oct-2009	pgangra   SHC				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.List;

import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.ReportProblemRequest;
import com.newco.marketplace.api.beans.so.ReportProblemResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a service class for reporting a problem on SO
 * 
 * @author pgangra
 * @version 1.0
 */

@Namespace("http://www.servicelive.com/namespaces/so")
@APIRequestClass(ReportProblemRequest.class)
@APIResponseClass(ReportProblemResponse.class)
public class ReportProblemService extends SOBaseService {
	
	private IServiceOrderBO serviceOrderBO;
	private Logger logger = Logger.getLogger(ReportProblemService.class);
	
//	public ReportProblemService() {
//		super();
//	}
	
	/**
	 * This method is for retrieving providers based on zipCode.
	 * 
	 * @param zipMap  HashMap<String,String>
	 * @return String
	 */
	@Override
    protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		logger.info("Entering ReportProblem method");
		ReportProblemRequest request  = (ReportProblemRequest) apiVO.getRequestFromPostPut();
		ReportProblemResponse response = new ReportProblemResponse();
		
		
		String soId = apiVO.getSOId();
		Integer buyerId = apiVO.getBuyerIdInteger();
		int subStatusId = 1;
		SecurityContext sContext = getSecurityContextForBuyerAdmin(buyerId);
		
		int entityId = sContext.getCompanyId();
		int roleType = sContext.getRoleId();
		String userName = sContext.getUsername();
		boolean skipAlertLogging = false;
		ProcessResponse result = null;
		try {
			if (request.getProblemDescription().trim().contentEquals("")) {
				response.setResults(Results.getError(
						ResultsCode.REPORT_PROBLEM_DESCRIPTION_EMPTY
								.getMessage(),
						ResultsCode.REPORT_PROBLEM_DESCRIPTION_EMPTY
								.getCode()));
				return response;
			} else {
				logger.info("ReportProblem request attributes: so_id: " + soId
						+ ", substatusId: " + subStatusId
						+ ", problemDescription: "
						+ request.getProblemDescription() + ", entityId: "
						+ entityId + ", roleType: " + roleType
						+ ", problemCode: " + request.getProblemCode()
						+ ", userName: " + userName + ", skipAlterLogging: "
						+ skipAlertLogging);
				result = serviceOrderBO
						.reportProblem(soId, Integer.parseInt(request
								.getProblemCode()), request
								.getProblemDescription(), entityId, roleType,
								PublicAPIConstant.problemDesc(request
										.getProblemCode()), userName,
								skipAlertLogging, sContext);
			}
		} catch (BusinessServiceException e) {
			logger.error("BusinessServiceException occurred in reporting problem on SO: " + soId + ", error msg: " + e.getMessage());
		}
		
		if(null==result) {
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
				ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		} else if (result.getCode() == ServiceConstants.VALID_RC) {
			response.setResults(Results.getSuccess());
		} else {
			List<String> listMessages = result.getMessages();
			String messages = "";
			for(String msg: listMessages) {
				messages += "; " + msg;
			}
			logger.error("Error occured in reporting problem, error code: " + messages);
			response.setResults(Results.getError(messages, ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			
		}
		
		logger.info("Exiting SOReportAProblem method");
		return response;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        ReportProblemRequest request  = (ReportProblemRequest) apiVO.getRequestFromPostPut();
        String soId = apiVO.getSOId();
        Integer buyerId = apiVO.getBuyerIdInteger();
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
        
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(OFMapper.createBuyerIdentFromSecCtx(securityContext));
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, request.getProblemDescription());
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC, request.getProblemCode());
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_REPORT_PROBLEM, ofRequest);
        return mapOFResponse(response);
    }
    
    private IAPIResponse mapOFResponse(OrderFulfillmentResponse response){
        ReportProblemResponse returnVal= new ReportProblemResponse();
        if(!response.getErrors().isEmpty()){
            returnVal.setResults(Results.getError(response.getErrorMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
        }else{
            returnVal.setResults(Results.getSuccess());
        }
        return returnVal;
    }
}
