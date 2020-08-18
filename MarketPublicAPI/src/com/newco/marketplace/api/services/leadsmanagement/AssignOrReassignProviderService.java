package com.newco.marketplace.api.services.leadsmanagement;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.AssignOrReassignProviderResponse;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;

public class AssignOrReassignProviderService extends BaseService {
	private static Logger logger = Logger
			.getLogger(AssignOrReassignProviderService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;

	public AssignOrReassignProviderService() {
		super(
				PublicAPIConstant.ASSIGN_OR_REASSIGN_PROVIDER_REQUEST_XSD,
				PublicAPIConstant.ASSIGN_OR_REASSIGN_PROVIDER_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.ASSIGN_OR_REASSIGN_PROVIDER_RESPONSE_SCHEMALOCATION,
				AssignOrReassignProviderRequest.class,
				AssignOrReassignProviderResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		AssignOrReassignProviderRequest assignOrReassignProviderRequest = (AssignOrReassignProviderRequest) apiVO
				.getRequestFromPostPut();
		AssignOrReassignProviderResponse response = new AssignOrReassignProviderResponse();

		// Invoke Validation Service to validate the request
		try {
			assignOrReassignProviderRequest = leadManagementValidator
					.validate(assignOrReassignProviderRequest);
			if (ResultsCode.SUCCESS != assignOrReassignProviderRequest
					.getValidationCode()) {
				return createErrorResponse(assignOrReassignProviderRequest
						.getValidationCode().getMessage(),
						assignOrReassignProviderRequest.getValidationCode()
								.getCode());
			}
		} catch (Exception e) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}

		try {
			Map<String,String> checkMap=new HashMap<String, String>();
			checkMap.put("leadId",assignOrReassignProviderRequest.getLeadId());
			checkMap.put("firmId",assignOrReassignProviderRequest.getFirmId());
			
			String resId=leadProcessingBO.checkProvider(checkMap);
			response.setLeadId(assignOrReassignProviderRequest.getLeadId());		
           //getting security context 
			String reqType=null;
			SecurityContext securityContext=null;
			if(null!= assignOrReassignProviderRequest.getIdentification()){
			  reqType= assignOrReassignProviderRequest.getIdentification().getType();
			}
			if(null!= reqType && reqType.equals(NewServiceConstants.RESOURCE_ID)){
				Integer providerResourceId =null;					
				providerResourceId = assignOrReassignProviderRequest.getIdentification().getId();
				securityContext = getSecurityContextForVendor(providerResourceId);
                securityContext.getUsername();
			}
			else{
			securityContext = getSecurityContextForVendorAdmin(apiVO.getProviderResourceId());
			}
			//updating data
			if(resId == null){
				leadProcessingBO.updateProvider(assignOrReassignProviderRequest);
				//Logging details into lead history
				
				historyLogging(assignOrReassignProviderRequest, NewServiceConstants.LEAD_ASSIGNED,securityContext.getUsername());
			    response.setResults(Results.getSuccess(ResultsCode.ASSIGN_PROVIDER.getMessage()));
			}
			else if (resId.equals(assignOrReassignProviderRequest.getResourceId().toString())){
				response.setResults(Results.getSuccess(ResultsCode.ASSIGN_SAME_PROVIDER.getMessage()));
			}
			else{
				leadProcessingBO.updateProvider(assignOrReassignProviderRequest);
				
				//Logging details into lead history
				historyLogging(assignOrReassignProviderRequest, NewServiceConstants.LEAD_REASSIGNED,securityContext.getUsername());
		     	response.setResults(Results.getSuccess(ResultsCode.REASSIGN_PROVIDER.getMessage()));
			}
			
		} 
		catch (BusinessServiceException e) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		}


		return response;
	}

	private AssignOrReassignProviderResponse createErrorResponse(
			String message, String code) {
		AssignOrReassignProviderResponse createResponse = new AssignOrReassignProviderResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}

	public void historyLogging(	AssignOrReassignProviderRequest assignOrReassignProviderRequest,int actionId,String userName)
			throws BusinessServiceException {
		String comments = NewServiceConstants.LEAD_ASSIGNED_PROVIDER;
		try {
			// getting resource name
			String resourceName = leadProcessingBO
					.getResourceName(assignOrReassignProviderRequest
							.getResourceId().toString());
			if (actionId == NewServiceConstants.LEAD_REASSIGNED) {
				comments = NewServiceConstants.LEAD_REASSIGNED_PROVIDER;
			}
			//Concating resource name to history comments
			comments = comments + resourceName;
			/* commenting getting user name of firm and taking it from securitycontext to log into history		    
		     String userName = leadProcessingBO
					.getUserName(assignOrReassignProviderRequest.getFirmId());*/
			String createdBy=assignOrReassignProviderRequest.getIdentification().getFullName();
			String modifiedBy=assignOrReassignProviderRequest.getIdentification().getUserName();
			Integer entityId=assignOrReassignProviderRequest.getIdentification().getEntityId();
			// Logging details into lead history
			LeadLoggingVO leadLoggingVO = new LeadLoggingVO(
					assignOrReassignProviderRequest.getLeadId(), actionId,
					NewServiceConstants.OLD_VALUE,
					NewServiceConstants.NEW_VALUE, comments, createdBy,
					modifiedBy, NewServiceConstants.ROLE_ID_PROVIDER,entityId);
			leadProcessingBO.insertLeadLogging(leadLoggingVO);
		} catch (Exception e) {
			logger.error("Exception in historyLogging " + e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
	}
	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}

}
