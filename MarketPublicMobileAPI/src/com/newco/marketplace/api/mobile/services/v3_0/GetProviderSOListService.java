package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.SoResultsVO;

@APIResponseClass(GetProviderSOListResponse.class)
public class GetProviderSOListService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(GetProviderSOListService.class);
	private MobileGenericValidator mobileValidator;
	private IMobileGenericBO mobileBO;
	private MobileGenericMapper mobileMapper;

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		//Declaring Variables
		ProviderParamVO providerParamVO =null;
		GetProviderSOListResponse response =null;
		List<ProviderSOSearchVO> providerSOList = null;
		SoResultsVO soResultsVo = null;
		try{
			// Mapping url path parameters and query parameters to VO object
			providerParamVO = mobileMapper.mapGetSoListRequest(apiVO);
			if(null != providerParamVO){
				providerParamVO = mobileValidator.validateGetSoRequest(providerParamVO);
				if(providerParamVO.getValidationCode().equals(ResultsCode.SUCCESS)){
					soResultsVo = mobileBO.getProviderSOList(providerParamVO);
					//Search Results count  will be null iff the pageNo > exceeds the lastPageNo.
					if(null!= soResultsVo && null== soResultsVo.getTotalSoCount()){
						providerParamVO.setValidationCode(ResultsCode.PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER);
						response = mobileMapper.createGetSoListErrorResponse(providerParamVO.getValidationCode());
					}
					// No service orders are available
					else if(null!= soResultsVo && null!= soResultsVo.getSoResultList() && soResultsVo.getSoResultList().isEmpty()){
						response = mobileMapper.createGetSoListErrorResponse(ResultsCode.PROVIDER_SO_SEARCH_NO_MATCH);
					}
					// Creating success response of type GetProviderSOListResponse
					else{
						if(null!= soResultsVo){
							providerSOList = soResultsVo.getSoResultList();
							 //Mapping the list of service orders to response
							response = mobileMapper.mapProviderResponse(providerSOList,providerParamVO,soResultsVo.getTotalSoCount());  
						}
					   
					}
				}else{
					//Creating Error Response for specific error code.
					response = mobileMapper.createGetSoListErrorResponse(providerParamVO.getValidationCode());
				}
			}else{
				//Generic Error Response.
				response = mobileMapper.createErrorResponse();
			}
			
		}catch (Exception e) {
			LOGGER.error("Exception in processing request for getSoProviderList API"+ e.getMessage());
			//Generic Error Response.
			response = mobileMapper.createErrorResponse();
		}
		return response;
	}

	public MobileGenericValidator getMobileValidator() {
		return mobileValidator;
	}

	public void setMobileValidator(MobileGenericValidator mobileValidator) {
		this.mobileValidator = mobileValidator;
	}

	

	public MobileGenericMapper getMobileMapper() {
		return mobileMapper;
	}

	public void setMobileMapper(MobileGenericMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
	}

	public IMobileGenericBO getMobileBO() {
		return mobileBO;
	}

	public void setMobileBO(IMobileGenericBO mobileBO) {
		this.mobileBO = mobileBO;
	}

	
}
