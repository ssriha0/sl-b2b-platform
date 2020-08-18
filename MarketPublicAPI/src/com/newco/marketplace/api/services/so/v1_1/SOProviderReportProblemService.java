/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 19-Oct-2009	pgangra   SHC				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.engine.mapping.sql.dynamic.elements.IsEmptyTagHandler;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.ReportProblemRequest;
import com.newco.marketplace.api.beans.so.ReportProblemResponse;
import com.newco.marketplace.api.beans.so.ResolveProblemOnSORequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a service class for reporting a problem on SO
 * for provider
 * 
 * 
 * @version 1.0
 */

@Namespace("http://www.servicelive.com/namespaces/pro/so")
@APIRequestClass(ReportProblemRequest.class)
@APIResponseClass(ReportProblemResponse.class)
public class SOProviderReportProblemService extends BaseService {
	
	private IServiceOrderBO serviceOrderBO;
	private Logger logger = Logger.getLogger(
			SOProviderReportProblemService.class);
	
	public SOProviderReportProblemService() {
		
		super(PublicAPIConstant.REPORT_PROBLEM_PRO_REQUEST_XSD,
				PublicAPIConstant.REPORT_PROBLEM_PRO_RESPONSE_XSD,
				PublicAPIConstant.PROBLEM_PRO_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.PROBLEM_PRO_SCHEMALOCATION,
				ReportProblemRequest.class,
				ReportProblemResponse.class);
		addRequiredURLParam(APIRequestVO.PROVIDERID, DataTypes.STRING);
		addRequiredURLParam(APIRequestVO.SOID, DataTypes.STRING);
	}
	
	/**
	 * This method is for retrieving providers based on zipCode.
	 * 
	 * @param zipMap  HashMap<String,String>
	 * @return String
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute()");
		
		ReportProblemRequest request;
		ReportProblemResponse response = new ReportProblemResponse();		
		request  = (ReportProblemRequest) apiVO.getRequestFromPostPut();		
		String soId = (String)apiVO.getSOId();
		Integer providerId = 0;
		try{
			providerId = Integer.parseInt((String)apiVO.getProviderId());		
		}catch(NumberFormatException nfe){
			response.setResults(Results.getError(ResultsCode.INVALID_RESOURCE_ID
					.getMessage(), ResultsCode.INVALID_RESOURCE_ID
					.getCode()));
			return response;
		}
		
		SecurityContext sContext = getSecurityContextForVendorAdmin(providerId);
		if(null == sContext){
			logger.error("SecurityContext is null");
			response.setResults(Results.getError(ResultsCode.INVALID_RESOURCE_ID
					.getMessage(), ResultsCode.INVALID_RESOURCE_ID
					.getCode()));
			return response;
		}
		int entityId = sContext.getCompanyId();
		int roleType = sContext.getRoleId();
		String userName = sContext.getUsername();
		ServiceOrder serviceOrder = null;		
		boolean skipAlertLogging = false;
		ProcessResponse result = null;
		try {
			serviceOrder = serviceOrderBO.getServiceOrder(soId);
			
			if(null != serviceOrder.getWfStateId() && 
					!(serviceOrder.getWfStateId().equals(
							PublicAPIConstant.PROBLEM_STATUS)||
							serviceOrder.getWfStateId().equals(
									PublicAPIConstant.ACTIVE_STATUS)||
							serviceOrder.getWfStateId().equals(
									PublicAPIConstant.COMPLETED_STATUS))){
				response.setResults(Results.getError(
						ResultsCode.
						REPORT_PROBLEM_PROVIDER_NOT_APPROPRIATE_STATUS
						.getMessage(), 
						ResultsCode.
						REPORT_PROBLEM_PROVIDER_NOT_APPROPRIATE_STATUS
						.getCode()));
				return response;
			}
			if(request.getProblemDescription().trim().contentEquals(""))
			{
				response.setResults(Results.getError(
						ResultsCode.
						REPORT_PROBLEM_DESCRIPTION_EMPTY
						.getMessage(), 
						ResultsCode.
						REPORT_PROBLEM_DESCRIPTION_EMPTY
						.getCode()));
				return response;
			}
			else {
			logger.info("ReportProblem request attributes: so_id: " + soId +
					",  problemDescription: " + 
					request.getProblemDescription() + ", entityId: " + entityId + 
					", roleType: " + roleType + ", problemCode: " + 
					request.getProblemCode() + ", userName: " + userName +
					", skipAlterLogging: " + skipAlertLogging);
			result = serviceOrderBO.reportProblem(soId, Integer.parseInt(request.getProblemCode()), 
				request.getProblemDescription(), entityId, roleType, 
				PublicAPIConstant.problemDesc(request.getProblemCode()), userName, skipAlertLogging, 
				sContext);    }
		} catch (BusinessServiceException e) {
			logger.error("BusinessServiceException occurred in reporting" +
					" problem on SO: " + soId + ", error msg: " +
					e.getMessage());
			response.setResults(Results.getError(
					ResultsCode.REPORT_PROBLEM_PROVIDER_FAILURE.getMessage(), 
					ResultsCode.REPORT_PROBLEM_PROVIDER_FAILURE.getCode()));
			return response;
		}
		
		if(result == null) {
			response.setResults(Results.getError(
					ResultsCode.REPORT_PROBLEM_PROVIDER_FAILURE.getMessage(), 
				ResultsCode.REPORT_PROBLEM_PROVIDER_FAILURE.getCode()));
		} else if (result.getCode() == ServiceConstants.VALID_RC) {
			response.setResults(Results.getSuccess(
					ResultsCode.REPORT_PROBLEM_PROVIDER_SUCCESS.getMessage()));
		} else {
			List<String> eMsgs = result.getMessages();
			StringBuilder eMsg = null;
			for(String msg: eMsgs) {
				eMsg = eMsg.append("; " + msg);
			}
			logger.error("Error occured in reporting problem, error code: " 
					+ result.getCode() 
				+ ", messages: " + eMsg);
			response.setResults(Results.getError(
					ResultsCode.REPORT_PROBLEM_PROVIDER_FAILURE.getMessage() 
					+ eMsg, 
				ResultsCode.REPORT_PROBLEM_PROVIDER_FAILURE.getCode()));
			
		}
		
		logger.info("Exiting execute()");
		return response;
	}
	
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

}
