package com.newco.marketplace.api.services.hi;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.provider.update.UpdateProviderRegistrationRequest;
import com.newco.marketplace.api.beans.hi.account.provider.update.UpdateProviderRegistrationResponse;
import com.newco.marketplace.api.beans.hi.account.update.provider.ProviderRegistration;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.provider.ProviderResourceMapper;
import com.newco.marketplace.business.businessImpl.hi.ProviderResourceBOImpl;
import com.newco.marketplace.business.iBusiness.hi.IProviderResourceBO;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;

@Namespace("http://www.servicelive.com/namespaces/updateProviderRegistration")
@APIRequestClass(UpdateProviderRegistrationRequest.class)
@APIResponseClass(UpdateProviderRegistrationResponse.class)
public class UpdateProviderRegistrationService extends BaseService {

	private static final Logger LOGGER  =Logger.getLogger(ProviderResourceBOImpl.class);
	public UpdateProviderRegistrationService()
	{
	super();
	}

	private IProviderResourceBO providerResourceBO;
	private ProviderResourceMapper providerResourceMapper;
	

	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub

		String providerId = apiVO.getProviderResourceId().toString();
		
		UpdateProviderRegistrationResponse updateProviderRegistrationResponse = new UpdateProviderRegistrationResponse();
		
		ProviderAccountVO providerAccountVO =new ProviderAccountVO();
		
		UpdateProviderRegistrationRequest updateProviderRegistrationRequest = (UpdateProviderRegistrationRequest) apiVO
				.getRequestFromPostPut();
		
				try {
					
					if(null!=updateProviderRegistrationRequest.getProvider() && !updateProviderRegistrationRequest.getProvider().isEmpty())
					{
						ProviderRegistration providerRegistration = updateProviderRegistrationRequest.getProvider();
						
						//Mapping update provider registration API request to provider account VO
						providerAccountVO =providerResourceMapper.mapUpdateProviderRequest(providerRegistration);
						
						if(StringUtils.isNotBlank(providerId))
						{
							providerAccountVO.setResourceId(providerId);
						}

						//Validating provider account VO
						providerResourceBO.validateUpdateProviderDetails(providerAccountVO);	
						
						if(null!=providerAccountVO.getResults() && null!=providerAccountVO.getResults().getError() && !providerAccountVO.getResults().getError().isEmpty())
						{
							updateProviderRegistrationResponse.setResults(providerAccountVO.getResults());
						}
						else{
							//Updating provider account VO to database
							providerResourceBO.updateProvider(providerAccountVO);
							updateProviderRegistrationResponse=providerResourceMapper.createSuccessResponseUpdate(providerAccountVO);
							}
	
					}else
					{
						LOGGER.info("Incomplete Request in Update Provider Registration API.");
						Results results = Results.getError(ResultsCode.INCOMPLETE_UPDATE_PROVIDER_REQUEST.getMessage(),ResultsCode.INCOMPLETE_UPDATE_PROVIDER_REQUEST.getCode());
						providerAccountVO.setResults(results);
						updateProviderRegistrationResponse.setResults(providerAccountVO.getResults());
					}
					
				}catch (Exception e) {
					
					LOGGER.error("Exception Caught in UpdateProviderRegistrationService.java class :"+e);
					Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR
							.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR
							.getCode());
					providerAccountVO.setResults(results);
					updateProviderRegistrationResponse.setResults(providerAccountVO.getResults());
				}
		
		
		return updateProviderRegistrationResponse;
	}

	

	public IProviderResourceBO getProviderResourceBO() {
		return providerResourceBO;
	}


	public void setProviderResourceBO(IProviderResourceBO providerResourceBO) {
		this.providerResourceBO = providerResourceBO;
	}



	public ProviderResourceMapper getProviderResourceMapper() {
		return providerResourceMapper;
	}


	public void setProviderResourceMapper(
			ProviderResourceMapper providerResourceMapper) {
		this.providerResourceMapper = providerResourceMapper;
	}


}
