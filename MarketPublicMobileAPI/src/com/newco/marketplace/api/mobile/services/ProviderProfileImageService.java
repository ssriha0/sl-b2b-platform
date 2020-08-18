package com.newco.marketplace.api.mobile.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.getProviderProfileImage.ProviderProfileImageResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class ProviderProfileImageService{
	
	private static final Logger logger = Logger.getLogger(ProviderProfileImageService.class);
	private IProviderInfoPagesBO providerInfoPage;
	private IMobileSOManagementBO mobileSoManagement; 

	public ProviderProfileImageResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		Results results = new Results();
		ProviderDocumentVO providerDocument = null;
		ProviderProfileImageResponse providerProfileImageResponse = new ProviderProfileImageResponse();
		
		if (StringUtils.isBlank(apiVO.getResourceId())) {
			logger.info(
					"resource id blank-->"	);
			results = Results.getError(
					ResultsCode.INVALID_RESOURCE_ID
							.getMessage(),
					ResultsCode.INVALID_RESOURCE_ID.getCode());
			providerProfileImageResponse.setResults(results);
			return providerProfileImageResponse;
		} else{
			try {
				if(!mobileSoManagement.isValidProviderResource(apiVO.getResourceId())){
					results = Results.getError(
							ResultsCode.INVALID_RESOURCE_ID
									.getMessage(),
							ResultsCode.INVALID_RESOURCE_ID.getCode());
					providerProfileImageResponse.setResults(results);
					return providerProfileImageResponse;
				}
			} catch (BusinessServiceException e) {
				logger.error("Error occurred while executing mobileSoManagement.isValidProviderResource...Track back there", e);
				results = Results.getError(ResultsCode.INVALID_PROVIDER.getMessage(), ResultsCode.INVALID_PROVIDER.getCode());
				providerProfileImageResponse.setResults(results);
				return providerProfileImageResponse;
			}
		}
		try {
			providerDocument = providerInfoPage.getProviderPrimaryPhoto(Integer.parseInt(apiVO.getResourceId()));
		} catch (BusinessServiceException e) {
			logger.error("Error occurred while retrieving Provider profile image...Track back there", e);
			results = Results.getError(ResultsCode.PROFILE_IMAGE_API_ERROR.getMessage(), ResultsCode.PROFILE_IMAGE_API_ERROR.getCode());
			providerProfileImageResponse.setResults(results);
			return providerProfileImageResponse;
		}
		if(null == providerDocument){
			logger.error("Got null response for provider profile image");
			results = Results.getError(ResultsCode.PROFILE_IMAGE_API_ERROR.getMessage(), ResultsCode.PROFILE_IMAGE_API_ERROR.getCode());
			providerProfileImageResponse.setResults(results);
			return providerProfileImageResponse;
		}
		providerProfileImageResponse.setProviderDocument(providerDocument);
		providerProfileImageResponse.setResults(Results.getSuccess());
		logger.info("Leaving execute method");
		return providerProfileImageResponse;
	}

	public IProviderInfoPagesBO getProviderInfoPage() {
		return providerInfoPage;
	}

	public void setProviderInfoPage(IProviderInfoPagesBO providerInfoPage) {
		this.providerInfoPage = providerInfoPage;
	}

	public IMobileSOManagementBO getMobileSoManagement() {
		return mobileSoManagement;
	}

	public void setMobileSoManagement(IMobileSOManagementBO mobileSoManagement) {
		this.mobileSoManagement = mobileSoManagement;
	}
}
