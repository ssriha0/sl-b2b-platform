package com.newco.marketplace.api.services.leadsmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.EligibleProviderForLead;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.EligibleProvidersForLead;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetEligibleProviderResponse;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderDetailsVO;

public class GetEligibleProviderService extends BaseService {
	private static Logger logger = Logger.getLogger(FetchProvidersService.class);
	private ILeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;
	
	public GetEligibleProviderService() {
		super(null,
				PublicAPIConstant.GET_ELIGIBLE_PROVIDERS_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.GET_ELIGIBLE_PROVIDERS_RESPONSE_SCHEMALOCATION,
				null, GetEligibleProviderResponse.class);
	}
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		GetEligibleProviderResponse response=new GetEligibleProviderResponse();
		 List<ProviderDetailsVO>providerList=null;
		 String firmId="";
		 String leadId="";
		//Checking leadId and FirmId present
		if(null!= apiVO && StringUtils.isNotBlank(apiVO.getFirmId())
				  && StringUtils.isNotBlank(apiVO.getleadId())){
			 firmId=apiVO.getFirmId();
			 leadId=apiVO.getleadId();
			 try {
				 response =leadManagementValidator.validate(firmId,leadId);
				 if( null!= response && null!= response.getValidationCode()){
					 if(response.getValidationCode().equals(ResultsCode.SUCCESS)){
						 //Making null to proceed
						 response.setValidationCode(null);
					 }else{
						 //Validation failed.
						 return createErrorResponse(response.getValidationCode().getMessage(),response.getValidationCode().getCode(),leadId,firmId);
					 }
					 
				 }
				
				 providerList=leadProcessingBO.getEligibleProviders(firmId,leadId);
			} catch (Exception e) {
				return createErrorResponse(ResultsCode.UNABLE_TO_PROCESS.getMessage(),ResultsCode.UNABLE_TO_PROCESS.getCode(),leadId,firmId);
			  }
			}
		
		response=setEligibleProviders(providerList,response);
	  //response=setTestsetEligibleProviders(response);
		
	  return response;
	
     }

    private GetEligibleProviderResponse setEligibleProviders(List<ProviderDetailsVO> providerList, GetEligibleProviderResponse response) {
		if(null != providerList && providerList.isEmpty()){
			 String firmId=response.getFirmId().toString();
			 String leadId=response.getLeadId();
			//Setting error message and returning response.
			return createErrorResponse(ResultsCode.NO_ELIGIBLE_PROVIDER_FOUND.getMessage(), ResultsCode.NO_ELIGIBLE_PROVIDER_FOUND.getCode(),leadId,firmId);
		}else if(null != providerList && !providerList.isEmpty()){
			Results results = Results.getSuccess();
			EligibleProvidersForLead providers = new EligibleProvidersForLead();
			List<EligibleProviderForLead> providerListFinal=new ArrayList<EligibleProviderForLead>();
			for(ProviderDetailsVO providersReturned:providerList){
				EligibleProviderForLead provider=new EligibleProviderForLead();
				if(null!= providersReturned && StringUtils.isNotBlank(providersReturned.getProviderId())){
				   provider.setResourceId(Integer.parseInt(providersReturned.getProviderId()));
				}
				   provider.setProviderDistance(providersReturned.getDistanceFromZipInMiles());
				   provider.setResFirstName(providersReturned.getResFirstname());
				   provider.setResLastName(providersReturned.getResLastName());
				   providerListFinal.add(provider) ; 
			}
			providers.setProvider(providerListFinal);
			response.setResults(results);
			response.setProviders(providers);
		}
		return response;
	}
    private GetEligibleProviderResponse createErrorResponse(String message,String code, String leadId, String firmId) {
    	   GetEligibleProviderResponse createResponse = new GetEligibleProviderResponse();
    	   //createResponse.setLeadId(leadId);
    	   //createResponse.setFirmId(Integer.parseInt(firmId));
	       Results results = Results.getError(message, code);
	       createResponse.setResults(results);
	       return createResponse;
      }
	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}
	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
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
