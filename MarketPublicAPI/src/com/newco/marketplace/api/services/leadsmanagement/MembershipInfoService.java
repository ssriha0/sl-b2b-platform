package com.newco.marketplace.api.services.leadsmanagement;
import java.util.Date;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateMembershipInfoResponse;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;


public class MembershipInfoService extends BaseService {
	private static Logger logger = Logger
			.getLogger(MembershipInfoService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;
	
	public static String POINTS_AWARDED = "300";
	
	public MembershipInfoService() {
		super(
				PublicAPIConstant.UPDATE_MEMBERSHIP_INFO_REQUEST_XSD,
				PublicAPIConstant.UPDATE_MEMBERSHIP_INFO_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.UPDATE_MEMBERSHIP_INFO_RESPONSE_SCHEMALOCATION,
				UpdateMembershipInfoRequest.class,
				UpdateMembershipInfoResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO){
		long start = System.currentTimeMillis();
		UpdateMembershipInfoRequest updateMembershipInfoRequest = (UpdateMembershipInfoRequest) apiVO
				.getRequestFromPostPut();
		UpdateMembershipInfoResponse response = new UpdateMembershipInfoResponse();
        // Invoke Validation Service to validate the request
		try {
			updateMembershipInfoRequest = leadManagementValidator
					.validate(updateMembershipInfoRequest);
			if (ResultsCode.SUCCESS != updateMembershipInfoRequest
					.getValidationCode()) {
				return createErrorResponse(updateMembershipInfoRequest
						.getValidationCode().getMessage(),
						updateMembershipInfoRequest.getValidationCode().getCode());
			}
		} catch (Exception e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		try {
			updateMembershipInfoRequest.setModifiedDate(new Date());
			leadProcessingBO.updateMembershipInfo(updateMembershipInfoRequest);
			String comments="";
			if(updateMembershipInfoRequest.isEligibleForReward()){
				comments=NewServiceConstants.SWYR_NUMBER_UPDATED+"  and "+ NewServiceConstants.SWYR_POINTS_AWARDED;
			}else{
				comments=NewServiceConstants.SWYR_NUMBER_UPDATED;
			}
			//getting security context 					
			SecurityContext securityContext=null;					
			securityContext = getSecurityContextForBuyerAdmin(NewServiceConstants.HOME_SERVICES_BUYER_ID);
			
			String modifiedBy=securityContext.getUsername();
			Integer entityId=securityContext.getVendBuyerResId();
			
			String createdBy=leadProcessingBO.getBuyerName(NewServiceConstants.HOME_SERVICES_BUYER_ID);
			LeadLoggingVO leadLoggingVO=new LeadLoggingVO(updateMembershipInfoRequest.getLeadId(),NewServiceConstants.LEAD_MEMBERSHIP_INFO_UPDATED, 
					NewServiceConstants.OLD_VALUE, NewServiceConstants.NEW_VALUE,comments, 
					createdBy,modifiedBy, NewServiceConstants.ROLE_ID_BUYER,entityId);					
			leadProcessingBO.insertLeadLogging(leadLoggingVO);
		} catch (BusinessServiceException e) {
			return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS
					.getMessage(), ResultsCode.UNABLE_TO_PROCESS.getCode());
		}
		
		response.setResults(Results.getSuccess());		
		response.setLeadId(updateMembershipInfoRequest.getLeadId());
		response.setPointsAwarded(NewServiceConstants.SWYR_REWARD_POINTS);
		long end = System.currentTimeMillis();
    	if (logger.isInfoEnabled()) {
            logger.info("Inside MembershipInfoService.execute()..>>" +
            		"Time Taken to update membership info service class >>"+(end-start));
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
			UpdateMembershipInfoRequest updateMembershipInfoRequest,
			UpdateMembershipInfoResponse updateMembershipInfoResponse) {
		updateMembershipInfoResponse.setLeadId(updateMembershipInfoRequest
				.getLeadId());
		updateMembershipInfoResponse.setPointsAwarded(300);
		updateMembershipInfoResponse.setResults(Results.getSuccess());
	}
	
	private UpdateMembershipInfoResponse createErrorResponse(String message, String code){
		UpdateMembershipInfoResponse createResponse = new UpdateMembershipInfoResponse();
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
