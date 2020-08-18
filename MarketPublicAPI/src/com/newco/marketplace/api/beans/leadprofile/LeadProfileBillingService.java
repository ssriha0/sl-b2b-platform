package com.newco.marketplace.api.beans.leadprofile;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.LeadProfileBillingRequest;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.LeadProfileBillingResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.leadprofile.ILeadProfileBO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;

public class LeadProfileBillingService {
	private ILeadProfileBO leadProfileBO;
	private Logger logger = Logger.getLogger(LeadProfileBillingService.class);
	private boolean errorOccured = false;
	
	public LeadProfileBillingService() {

		this.errorOccured = false;
	}
	public LeadProfileBillingResponse execute(LeadProfileBillingRequest request) {
		LeadProfileBillingResponse response = new LeadProfileBillingResponse();
		if(null != request){
			if(errorOccured){
				errorOccured = false;
			}
			response = validateFields(request,response);
			LeadProfileBillingRequestVO leadProfileBillingRequestVO=new LeadProfileBillingRequestVO();
			if(errorOccured){
				return response;
			}
			else{
				try{
					//If no validation error then copying the request data along with the price data of the provided project type to the
					//master data object LeadProfileCreationRequestVO for persisting purpose
					copyRequestDataToVO(leadProfileBillingRequestVO,request);
					//Method to persist data related to Lead profile creations
					LeadProfileBillingResponseVO leadProfileBillingResponseVO = leadProfileBO.createLeadProfileBilling(leadProfileBillingRequestVO);
					
					
					if(null == leadProfileBillingResponseVO ){
						logger.info("LeadProfileBilling : + FAILURE" );
						response.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
						response.setResults(Results.getError(ResultsCode.LEAD_BILLING_FAILURE.getMessage(), 
						ResultsCode.LEAD_BILLING_FAILURE.getCode()));
						
					}else if(null != leadProfileBillingResponseVO && null != leadProfileBillingResponseVO.getErrors()){
						
						Results result = new Results();
						// Map errors
						List<String> errors = leadProfileBillingResponseVO.getErrors().getError();
						List<ErrorResult> errorList = new ArrayList<ErrorResult>();
						for(String error: errors ){
							logger.info("LeadProfileBilling ERROR : " + error);
							ErrorResult errorResult = new ErrorResult();
							errorResult.setMessage(error);
							errorList.add(errorResult);
						}
						result.setError(errorList);
						response.setResults(result);
						logger.info("LeadProfileBilling : + FAILURE");
						response.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
						//response.setResults(Results.getError(ResultsCode.LEAD_BILLING_FAILURE.getMessage(),ResultsCode.LEAD_BILLING_FAILURE.getCode()));
					}else{
						logger.info("LeadProfileBilling : + SUCCESS" );
						response.setMessage(PublicAPIConstant.API_RESULT_SUCCESS);
						response.setResults(Results.getError(ResultsCode.LEAD_BILLING_SUCCESS.getMessage(), 
										ResultsCode.LEAD_BILLING_SUCCESS.getCode()));
						
					}

				} 
				catch(Exception e) {
					logger.error("CreateProviderService exception detail: " + e.getMessage());
					response.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
					response.setResults(
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
									ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
				logger.info("Exiting CreateProviderAccount API's execute method");	
			} 
		
		
		
		}else{
			logger.info("Exiting LeadProfileBilling API's execute method due to request being null.");
			response.setMessage(PublicAPIConstant.API_RESULT_FAILURE);
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return response;
	}
	private void copyRequestDataToVO(LeadProfileBillingRequestVO leadProfileBillingRequestVO,LeadProfileBillingRequest request)
	{
		if(!StringUtils.isBlank(request.getPartnerId())){
			leadProfileBillingRequestVO.setPartnerId(request.getPartnerId());
		}
		if(!StringUtils.isBlank(request.getCardType())){
			leadProfileBillingRequestVO.setCardType(request.getCardType());
		}
		if(!StringUtils.isBlank(request.getCardNo())){
			leadProfileBillingRequestVO.setCardNo(request.getCardNo());
		}
		if(null != request.getExpirationMonth()){
			leadProfileBillingRequestVO.setExpirationMonth(request.getExpirationMonth());
		}
		if(null != request.getExpirationYear()){
			leadProfileBillingRequestVO.setExpirationYear(request.getExpirationYear());
		}
		if(null != request.getCcv()){
			leadProfileBillingRequestVO.setCCV(request.getCcv());
		}
		
	}
	private LeadProfileBillingResponse validateFields(LeadProfileBillingRequest request,LeadProfileBillingResponse response) {		
		Results results = null;
		if(!errorOccured){
			if (StringUtils.isBlank(request.getPartnerId())) {
				results = Results.getError(ResultsCode.INVALID_PARTNER_ID
						.getMessage(), ResultsCode.INVALID_PARTNER_ID
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			if(!errorOccured && StringUtils.isBlank(request.getCardType())){
				results = Results.getError(ResultsCode.INVALID_CARD_TYPE
						.getMessage(), ResultsCode.INVALID_CARD_TYPE
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			if(!errorOccured && StringUtils.isBlank(request.getCardNo())){
				results = Results.getError(ResultsCode.INVALID_CARD_NUMBER
						.getMessage(), ResultsCode.INVALID_CARD_NUMBER
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			if(!errorOccured && null == request.getExpirationYear()){
				results = Results.getError(ResultsCode.INVALID_EXPIRATION_YEAR
						.getMessage(), ResultsCode.INVALID_EXPIRATION_YEAR
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			if (!errorOccured && null == request.getExpirationMonth()) {
				//Still some changes need to be made for referral code error message
				results = Results.getError(ResultsCode.INVALID_EXPIRATION_MONTH
						.getMessage(), ResultsCode.INVALID_EXPIRATION_MONTH
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}
			if (!errorOccured && null == request.getCcv()) {

				results = Results.getError(ResultsCode.INVALID_CCV
						.getMessage(), ResultsCode.INVALID_CCV
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			
			//TODO - these fields are not given in the api spec
			/*if (!errorOccured && (null == request.getAmount())) {

				results = Results.getError(ResultsCode.INVALID_BILLING_AMOUNT
						.getMessage(), ResultsCode.INVALID_BILLING_AMOUNT
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
			
			else if (!errorOccured && StringUtils.isBlank(request.getDescription())) {

				results = Results.getError(ResultsCode.INVALID_DESCRIPTION
						.getMessage(), ResultsCode.INVALID_DESCRIPTION
						.getCode());
				response.setResults(results);
				errorOccured = true;

			}*/
			
		}
		return response;
	}
	public ILeadProfileBO getLeadProfileBO() {
		return leadProfileBO;
	}
	public void setLeadProfileBO(ILeadProfileBO leadProfileBO) {
		this.leadProfileBO = leadProfileBO;
	}
	
}
