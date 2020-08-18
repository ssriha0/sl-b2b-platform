package com.newco.marketplace.api.mobile.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.GenericAPIResponse;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateRequest;
import com.newco.marketplace.api.mobile.beans.updateApptTime.UpdateProviderProfileDetailsRequest;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.provider.IMarketPlaceBO;
import com.newco.marketplace.exception.core.BusinessServiceException;

@APIRequestClass(UpdateProviderProfileDetailsRequest.class)
@APIResponseClass(GenericAPIResponse.class)
public class UpdateProviderProfileService extends BaseService{
	private static final Logger logger = Logger.getLogger(ProviderProfileService.class);
	private IMobileSOManagementBO mobileSoManagement;
	private IMarketPlaceBO iMarketPlaceBO;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		Results results = new Results();
		GenericAPIResponse response = new GenericAPIResponse();
		
		//getting request from API vo
		UpdateProviderProfileDetailsRequest  updateProfileDetailsRequest = (UpdateProviderProfileDetailsRequest) apiVO.getRequestFromPostPut();
		
		if (StringUtils.isBlank(apiVO.getResourceId())) {
			logger.info("resource id blank-->"	);
			results = Results.getError(
					ResultsCode.INVALID_RESOURCE_ID
							.getMessage(),
					ResultsCode.INVALID_RESOURCE_ID.getCode());
			response.setResults(results);
			return response;
		} else{
			try {
				if(!mobileSoManagement.isValidProviderResource(apiVO.getResourceId())){
					results = Results.getError(
							ResultsCode.INVALID_RESOURCE_ID
									.getMessage(),
							ResultsCode.INVALID_RESOURCE_ID.getCode());
					response.setResults(results);
					return response;
				}
			} catch (BusinessServiceException e) {
				logger.error("Error occurred while executing mobileSoManagement.isValidProviderResource...Track back there", e);
				results = Results.getError(ResultsCode.INVALID_PROVIDER.getMessage(), ResultsCode.INVALID_PROVIDER.getCode());
				response.setResults(results);
				return response;
			}
		}
		
		if(!isValidNumber(updateProfileDetailsRequest.getMobileNo())){
			logger.info("Mobile no is invalid"	);
			results = Results.getError(
					ResultsCode.IMVALID_MOBILE_NO
							.getMessage(),
					ResultsCode.IMVALID_MOBILE_NO.getCode());
			response.setResults(results);
			return response;
		}
		
		try {
			iMarketPlaceBO.updateMobileNo(apiVO.getResourceId(), updateProfileDetailsRequest.getMobileNo());
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			logger.error("Error occurred while executing iMarketPlaceBO.updateMobileNo...Track back there", e);
			results = Results.getError(ResultsCode.INVALID_PROVIDER.getMessage(), ResultsCode.INVALID_PROVIDER.getCode());
			response.setResults(results);
			return response;
		}
		response.setResults(Results.getSuccess());
		logger.info("Leaving execute method");
		return response;
	}

	private boolean isValidNumber(String mobileNo) {

		if (null == mobileNo || mobileNo.trim().length() <= 0
				|| mobileNo.trim().equals("") || mobileNo.trim().length() < 10) {
			return false;
		}
		// Validating for the NUMBER type
		try {
			long numInt1 = Long.parseLong(mobileNo.trim());

		} catch (NumberFormatException a_Ex) {

			return false;
		} catch (Exception a_Ex) {

			return false;
		}
		return true;
	}


	public IMobileSOManagementBO getMobileSoManagement() {
		return mobileSoManagement;
	}

	public void setMobileSoManagement(IMobileSOManagementBO mobileSoManagement) {
		this.mobileSoManagement = mobileSoManagement;
	}

	public IMarketPlaceBO getiMarketPlaceBO() {
		return iMarketPlaceBO;
	}

	public void setiMarketPlaceBO(IMarketPlaceBO iMarketPlaceBO) {
		this.iMarketPlaceBO = iMarketPlaceBO;
	}
	
}