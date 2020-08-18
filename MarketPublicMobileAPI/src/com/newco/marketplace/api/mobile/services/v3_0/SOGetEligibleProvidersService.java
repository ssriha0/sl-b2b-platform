
package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.GetEligibleProviderResponse;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

@APIResponseClass(GetEligibleProviderResponse.class)
public class SOGetEligibleProvidersService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(SOGetEligibleProvidersService.class);

	private IMobileGenericBO mobileGenericBO;
	
	private MobileGenericMapper mobileGenericMapper;

	/**
	 * This method handle the main logic for fetching eligible providers.
	 * @param apiVO
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		Results results = null;
		List<ProviderResultVO> providers = null;
		EligibleProvider assignedProvider =null;
		GetEligibleProviderResponse soGetEligibleProvResponse = null;
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		String firmId = apiVO.getProviderId();
		String soId = apiVO.getSOId();
		String groupIndParam = requestMap.get(PublicAPIConstant.GROUP_IND_PARAM);

		if (null != firmId && null != soId) {
			try{
				// gets the sorted list of routed providers based on distance
				providers = mobileGenericBO.getRoutedProviders(firmId,soId,groupIndParam);
				//To get assigned resource in case of re-assign provider
				assignedProvider = mobileGenericBO.getAssignedResource(soId);
				soGetEligibleProvResponse = mobileGenericMapper.mapEligibleProviderResponse(providers,assignedProvider);
			}
			catch (BusinessServiceException ex) {
				LOGGER.error("Exception in getting routed provider list"+soId);	
				ex.printStackTrace();
				results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				soGetEligibleProvResponse = new GetEligibleProviderResponse();
				soGetEligibleProvResponse.setResults(results);
				return soGetEligibleProvResponse;
			}
			catch (Exception ex) {
				LOGGER.error("Exception in getting routed provider list"+soId);	
				ex.printStackTrace();
				results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				soGetEligibleProvResponse = new GetEligibleProviderResponse();
				soGetEligibleProvResponse.setResults(results);
				return soGetEligibleProvResponse;
			}
		}
		return soGetEligibleProvResponse;
	}
	
	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}


	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}








}