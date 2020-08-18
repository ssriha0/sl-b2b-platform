package com.newco.marketplace.api.services.hi;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.hi.account.provider.create.ProviderRegistrationRequest;
import com.newco.marketplace.api.beans.hi.account.provider.create.ProviderRegistrationResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderResourceMapper;
import com.newco.marketplace.business.iBusiness.hi.IProviderResourceBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;
import common.Logger;

@Namespace("http://www.servicelive.com/namespaces/approveFirm")
@APIRequestClass(ProviderRegistrationRequest.class)
@APIResponseClass(ProviderRegistrationResponse.class)
public class CreateHIProviderAccountService extends BaseService{
    private static Logger LOGGER = Logger.getLogger(CreateHIProviderAccountService.class);
	private IProviderResourceBO providerResourceBO;
	private ProviderResourceMapper providerResourceMapper;
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// Declaring Variables
		ProviderRegistrationResponse response = null;
		ProviderAccountVO providerVO =null;
		ProviderRegistrationRequest request = (ProviderRegistrationRequest) apiVO.getRequestFromPostPut();
		try{
			//Mapping Request with VO
			providerVO = providerResourceMapper.mapCreateProviderRequest(request);
			if(null!= providerVO){
				providerVO = providerResourceBO.validateCreateProviderAccount(providerVO);
				if(null!=providerVO.getResults() && null!=providerVO.getResults().getError()){
					response = providerResourceMapper.createErrorResponse(providerVO.getResults());
				}else{
					//Creating Provider Account.
					providerVO = providerResourceBO.createProviderProfile(providerVO);
					if(null!= providerVO){
						//Create Success Response
						response = providerResourceMapper.createSuccessResponse(providerVO);
					}else{
						throw new BusinessServiceException("Exception in creating provider profile") ;
					}
				}
			}else{
				throw new BusinessServiceException("Exception in mappinge") ;
			}
		}catch (Exception e) {
			LOGGER.error("Exception in Processing Request to create Provider Account"+ e.getMessage());
			e.printStackTrace();
			response = providerResourceMapper.createErrorResponse(null);
		}
		
		
		return response;
	}

	

	public ProviderResourceMapper getProviderResourceMapper() {
		return providerResourceMapper;
	}

	public void setProviderResourceMapper(
			ProviderResourceMapper providerResourceMapper) {
		this.providerResourceMapper = providerResourceMapper;
	}



	public IProviderResourceBO getProviderResourceBO() {
		return providerResourceBO;
	}



	public void setProviderResourceBO(IProviderResourceBO providerResourceBO) {
		this.providerResourceBO = providerResourceBO;
	}

}
