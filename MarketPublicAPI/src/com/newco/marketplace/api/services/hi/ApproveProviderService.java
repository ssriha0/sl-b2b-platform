package com.newco.marketplace.api.services.hi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsResponse;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveProvidersRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveProvidersResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderBO;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;

@Namespace("http://www.servicelive.com/namespaces/approveProvider")
@APIRequestClass(ApproveProvidersRequest.class)
@APIResponseClass(ApproveProvidersResponse.class)
public class ApproveProviderService extends BaseService {
    
	private static final Logger LOGGER= Logger.getLogger(ApproveProviderService.class);
	private IProviderBO providerBO;
    private ProviderMapper providerMapper;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		//Declaring variables
		List<ApproveProvidersVO> approveProvidersVOList = null;
		List<ApproveProvidersVO> validProvidersList = new ArrayList<ApproveProvidersVO>();
		List<ApproveProvidersVO> invalidProviders =  new ArrayList<ApproveProvidersVO>();
		ApproveProvidersResponse response = null;
		try{
			ApproveProvidersRequest request =(ApproveProvidersRequest) apiVO.getRequestFromPostPut();
			approveProvidersVOList = providerMapper.mapApproveProviderRequest(request);
			if(null!= approveProvidersVOList){
				approveProvidersVOList = providerBO.validateProviders(approveProvidersVOList,request.getAdminResourceId());
				  if(null!= approveProvidersVOList && !approveProvidersVOList.isEmpty()){
					  validProvidersList = providerBO.removeInvalidOrValidProviders(approveProvidersVOList,ProviderConstants.VALID_FIRM);
					  invalidProviders = providerBO.removeInvalidOrValidProviders(approveProvidersVOList,ProviderConstants.IN_VALID_FIRM);
					    //Updating Provider Firm Status and ReasonCodes.
					    if(null!= validProvidersList&& !validProvidersList.isEmpty()){
					    	validProvidersList = providerBO.updateProviderWFStatusAndReasonCodes(validProvidersList,request.getAdminResourceId()); 
					     }
					     //Valid and Invalid providers
					      if(null!= validProvidersList && !(validProvidersList.isEmpty()) && null!= invalidProviders &&!(invalidProviders.isEmpty())){
					    	  response = providerMapper.createResponseForProvider(validProvidersList,invalidProviders);
					      } 
					      //All providers are valid and Updated Successfully
					      else if(null!=validProvidersList &&  !validProvidersList.isEmpty()){
					    	  response = providerMapper.createResponseForProvider(validProvidersList,null);
					      } //All providers  are invalid
					      else if(null!= invalidProviders && !invalidProviders.isEmpty()){
					    	  response = providerMapper.createResponseForProvider(null,invalidProviders);
					      }// Error Response.
					      else{
					    	  response = providerMapper.createErrorResponseForProvider(null);
					      }
				  }//Empty if admin resource id is invalid
				  else if(null!= approveProvidersVOList && approveProvidersVOList.isEmpty()){
					  response = providerMapper.createErrorResponseForProvider(ResultsCode.PROVIDER_INVALID_ADMIN_RESOURCE_ID);
				  }else{
					  response = providerMapper.createErrorResponseForProvider(null);
				  }
			}else{
				response = providerMapper.createErrorResponseForProvider(null);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in Changing the status of the Provider"+ e.getMessage());
			//This code has to removed after completing UT.
			//response = providerMapper.setTestResponse();
			response = providerMapper.createErrorResponseForProvider(null);
		}
		return response;
	}

	public IProviderBO getProviderBO() {
		return providerBO;
	}

	public void setProviderBO(IProviderBO providerBO) {
		this.providerBO = providerBO;
	}

	public ProviderMapper getProviderMapper() {
		return providerMapper;
	}

	public void setProviderMapper(ProviderMapper providerMapper) {
		this.providerMapper = providerMapper;
	}

}
