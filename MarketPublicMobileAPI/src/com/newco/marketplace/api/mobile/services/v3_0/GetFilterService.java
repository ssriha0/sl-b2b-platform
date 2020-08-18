package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.Filter.GetFilterResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;

/**
 * This class would act as a Servicer class for fetch filternames and search
 * criterias for a provider
 * 
 * @author Infosys
 * @version 1.0
 */
@APIResponseClass(GetFilterResponse.class)
public class GetFilterService extends BaseService {
	private Logger logger = Logger.getLogger(GetFilterService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		GetFilterResponse response = new GetFilterResponse();
		Integer providerId = apiVO.getProviderResourceId();
		Map<Integer,FiltersVO > filterDetailsMap =null;
		Results results = null;
		try {
			if(null !=providerId){
				filterDetailsMap = mobileGenericBO.getSearchFilterDetails(providerId);
				
				if(null !=filterDetailsMap && ! filterDetailsMap.isEmpty()){
					response = mobileGenericMapper.mapFilterDetailsResponse(filterDetailsMap);
				}else{
					logger.error("No filters associated with the user");
					results = Results.getError(
							ResultsCode.GET_FILTER_NO_FILTERS_TO_FETCH
							.getMessage(), ResultsCode.GET_FILTER_NO_FILTERS_TO_FETCH.getCode());
					response.setResults(results);
				}
			}
			
		} catch (Exception e) {
			
			logger.error("Severe exception occured while fetching filters");
			results = Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR
					.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}
		return response;
	}

	/**
	 * @return the mobileGenericBO
	 */
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	/**
	 * @param mobileGenericBO
	 *            the mobileGenericBO to set
	 */
	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
	/**
	 * @return the mobileGenericMapper
	 */
	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	/**
	 * @param mobileGenericMapper
	 *            the mobileGenericMapper to set
	 */
	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}

}
