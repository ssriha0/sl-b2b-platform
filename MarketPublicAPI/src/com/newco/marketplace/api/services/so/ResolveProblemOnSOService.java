package com.newco.marketplace.api.services.so;

import java.util.List;

import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

@Namespace("http://www.servicelive.com/namespaces/so")
@APIRequestClass(ResolveProblemOnSORequest.class)
@APIResponseClass(ReportProblemResponse.class)
public class ResolveProblemOnSOService extends SOBaseService{
	private IServiceOrderBO serviceOrderBO;
	private Logger logger = Logger.getLogger(ResolveProblemOnSOService.class);
	
	/**
	 * Constructor
	 */

	public ResolveProblemOnSOService() {
		super();
		addRequiredURLParam(APIRequestVO.BUYERID, DataTypes.STRING);
		addRequiredURLParam(APIRequestVO.SOID, DataTypes.STRING);
	}
	
	/**
	 * This method is for resolving the issue in SO.
	 * 
	 * @param apiVO  APIRequestVO
	 * @return AddSODocResponse
	 */
    @Override
    protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {

		logger.info("Entering execute() method of ResolveProblemOnSOService");
		ResolveProblemOnSORequest request;
		ReportProblemResponse response;
		ProcessResponse processResponse = null;
		try{		
			response = new ReportProblemResponse();
			request  = (ResolveProblemOnSORequest) apiVO.getRequestFromPostPut();
			
			String soId = apiVO.getSOId();
			int buyerId = apiVO.getBuyerIdInteger();	
			int subStatusId = 0;
			SecurityContext sContext = getSecurityContextForBuyerAdmin(
					new Integer(buyerId));			
			int entityId = sContext.getCompanyId();
			int roleType = sContext.getRoleId();
			String userName = sContext.getUsername();
			String strComment = request.getResolveComments();
			if(StringUtils.isEmpty(strComment)){
				response.setResults(Results.getError(
						ResultsCode.RESOLVE_COMMENTS_EMPTY.getMessage(),
						ResultsCode.RESOLVE_COMMENTS_EMPTY.getCode()));
				return response;
			}
			String strPbDesc = "";
			String strPbDetails = "";
			
			processResponse = serviceOrderBO.reportResolution(
					soId, subStatusId, strComment, entityId, 
					roleType, strPbDesc, strPbDetails, userName, 
					sContext);
			
		}catch (Exception ex) {
			logger.error("ResolveProblemOnSOService-->execute()--> Exception-->"
					+ ex.getMessage(), ex);
			Results results = Results.getError(ex.getMessage(),
					ResultsCode.GENERIC_ERROR.getCode());
			response = new ReportProblemResponse();
			response.setResults(results);
		}
		if(null==processResponse) {
			response.setResults(Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
				ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
			response.setResults(Results.getSuccess(
					ResultsCode.RESOLVE_SUCCESSFUL.getMessage()));					
		} else {
			List<String> eMsgs = processResponse.getMessages();
			StringBuilder eMsg = new StringBuilder();
			for(String msg: eMsgs) {
				eMsg = eMsg.append("; " + msg);
			}
			logger.error("Error occured in resolving problem, error code: " + processResponse.getCode() 
				+ ", messages: " + eMsg);
			response.setResults(Results.getError(eMsg.toString(), 
				ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			
		
		}
		logger.info("Leaving execute() method of ResolveProblemOnSOService");
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
        ResolveProblemOnSORequest request  = (ResolveProblemOnSORequest) apiVO.getRequestFromPostPut();
        String soId = apiVO.getSOId();
        Integer buyerId = apiVO.getBuyerIdInteger();
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
        
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(OFMapper.createBuyerIdentFromSecCtx(securityContext));
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, request.getResolveComments());
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_RESOLVE_PROBLEM, ofRequest);
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
