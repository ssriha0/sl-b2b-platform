package com.newco.marketplace.api.mobile.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.getProviderProfile.Address;
import com.newco.marketplace.api.mobile.beans.getProviderProfile.ProviderProfileResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.PublicProfileVO;

@APIResponseClass(ProviderProfileResponse.class)
public class ProviderProfileService extends BaseService{
	private static final Logger logger = Logger.getLogger(ProviderProfileService.class);
	private IProviderInfoPagesBO providerInfoPagesBO;
	private IMobileSOManagementBO mobileSoManagement; 

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		PublicProfileVO publicProfileVo = null;
		ProviderProfileResponse providerProfileResponse = new ProviderProfileResponse(); 
		Results results = new Results();
		
		if (StringUtils.isBlank(apiVO.getResourceId())) {
			logger.info(
					"resource id blank-->"	);
			results = Results.getError(
					ResultsCode.INVALID_RESOURCE_ID
							.getMessage(),
					ResultsCode.INVALID_RESOURCE_ID.getCode());
			providerProfileResponse.setResults(results);
			return providerProfileResponse;
		}else{
			try {
				if(!mobileSoManagement.isValidProviderResource(apiVO.getResourceId())){
					results = Results.getError(
							ResultsCode.INVALID_RESOURCE_ID
									.getMessage(),
							ResultsCode.INVALID_RESOURCE_ID.getCode());
					providerProfileResponse.setResults(results);
					return providerProfileResponse;
				}
			} catch (BusinessServiceException e) {
				logger.error("Error occurred while executing mobileSoManagement.isValidProviderResource...Track back there", e);
				results = Results.getError(ResultsCode.INVALID_PROVIDER.getMessage(), ResultsCode.INVALID_PROVIDER.getCode());
				providerProfileResponse.setResults(results);
				return providerProfileResponse;
			}
		}
		
		
		try {
			publicProfileVo = providerInfoPagesBO.getProviderDetails(Integer.parseInt(apiVO.getResourceId()));
		} catch (BusinessServiceException e) {
			logger.error("Error occurred while retrieving Provider Details...Track back there", e);
			results = Results.getError(ResultsCode.INVALID_PROVIDER.getMessage(), ResultsCode.INVALID_PROVIDER.getCode());
			providerProfileResponse.setResults(results);
			return providerProfileResponse;
		}
		mapVoToServiceResponse(publicProfileVo, providerProfileResponse);
		providerProfileResponse.setResults(Results.getSuccess());
		logger.info("Leaving execute method");
		return providerProfileResponse;
	}

	private void mapVoToServiceResponse(PublicProfileVO publicProfileVo, ProviderProfileResponse providerProfileResponse){
	
		providerProfileResponse.setResourceId(publicProfileVo.getResourceId().toString());
		providerProfileResponse.setFirstName(publicProfileVo.getFirstName());
		providerProfileResponse.setLastName(publicProfileVo.getLastName());
		providerProfileResponse.setEmail(publicProfileVo.getEmail());
		providerProfileResponse.setPhoneNumber(publicProfileVo.getPhoneNumber());
		providerProfileResponse.setPhoneNumberExt(publicProfileVo.getPhoneNumberExt());
		providerProfileResponse.setAlternatePhone(publicProfileVo.getAlternatePhone());
		
		Address address = new Address();
		address.setDispAddStreet1(publicProfileVo.getDispAddStreet1());
		address.setDispAddStreet2(publicProfileVo.getDispAddStreet2());
		address.setDispAddApt(publicProfileVo.getDispAddApt());
		address.setDispAddCity(publicProfileVo.getDispAddCity());
		address.setDispAddState(publicProfileVo.getDispAddState());
		address.setDispAddZip(publicProfileVo.getDispAddZip());
		
		providerProfileResponse.setAddress(address);
		
	}
	
	public IProviderInfoPagesBO getProviderInfoPagesBO() {
		return providerInfoPagesBO;
	}

	public void setProviderInfoPagesBO(IProviderInfoPagesBO providerInfoPagesBO) {
		this.providerInfoPagesBO = providerInfoPagesBO;
	}

	public IMobileSOManagementBO getMobileSoManagement() {
		return mobileSoManagement;
	}

	public void setMobileSoManagement(IMobileSOManagementBO mobileSoManagement) {
		this.mobileSoManagement = mobileSoManagement;
	}

}
