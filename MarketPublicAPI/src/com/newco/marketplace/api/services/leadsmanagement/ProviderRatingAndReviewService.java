package com.newco.marketplace.api.services.leadsmanagement;

import org.apache.log4j.Logger;


import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AddRatingsAndReviewResponse;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class ProviderRatingAndReviewService extends BaseService {
	private static Logger logger = Logger
			.getLogger(ProviderRatingAndReviewService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;
	
	public static String POINTS_AWARDED = "300";
	
	public ProviderRatingAndReviewService() {
		super(
				PublicAPIConstant.ADD_RATING_AND_REVIEW_REQUEST_XSD,
				PublicAPIConstant.ADD_RATING_AND_REVIEW_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.ADD_RATING_AND_REVIEW_RESPONSE_SCHEMALOCATION,
				AddRatingsAndReviewRequest.class,
				AddRatingsAndReviewResponse.class);
	}
 
	@Override
	public IAPIResponse execute(APIRequestVO apiVO){
	long start = System.currentTimeMillis();
		AddRatingsAndReviewRequest addRatingsAndRequest = (AddRatingsAndReviewRequest) apiVO
				.getRequestFromPostPut();
		AddRatingsAndReviewResponse response = new AddRatingsAndReviewResponse();

		//setTestResponse(updateMembershipInfoRequest, response);
		// Invoke Validation Service to validate the request
		try {
			addRatingsAndRequest = leadManagementValidator
					.validate(addRatingsAndRequest);
			if (ResultsCode.SUCCESS != addRatingsAndRequest
					.getValidationCode()) {
				return createErrorResponse(addRatingsAndRequest
						.getValidationCode().getMessage(),
						addRatingsAndRequest.getValidationCode().getCode());
			}
		} catch (Exception e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		try {
		
			leadProcessingBO.addRatingsAndReview(addRatingsAndRequest);
		} catch (BusinessServiceException e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		response.setResults(Results.getSuccess());		
		response.setLeadId(addRatingsAndRequest.getLeadId());
		long end = System.currentTimeMillis();
    	if (logger.isInfoEnabled()) {
            logger.info("Inside ProviderRatingAndReviewService.execute()..>>" +
            		"Time Taken to add Reviews to the lead from service class >>"+(end-start));
		}
		return response;
	}

	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	@SuppressWarnings("unchecked")
	private void setTestResponse(
			AddRatingsAndReviewRequest addRatingsAndReviewRequest,
			AddRatingsAndReviewResponse addRatingsAndReviewResponse) {
		addRatingsAndReviewResponse.setLeadId(addRatingsAndReviewRequest
				.getLeadId());
		addRatingsAndReviewResponse.setResults(Results.getSuccess());
	}
	
	private AddRatingsAndReviewResponse createErrorResponse(String message, String code){
		AddRatingsAndReviewResponse createResponse = new AddRatingsAndReviewResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}	

}
