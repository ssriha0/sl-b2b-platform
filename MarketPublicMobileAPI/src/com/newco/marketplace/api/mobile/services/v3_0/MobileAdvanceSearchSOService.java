
package com.newco.marketplace.api.mobile.services.v3_0;


import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultsVO;

/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/16
* service class for fetching response 0f advance search SO for mobile
*
*/
@APIRequestClass(MobileSOAdvanceSearchRequest.class)
@APIResponseClass(MobileSOAdvanceSearchResponse.class)
public class MobileAdvanceSearchSOService extends BaseService {
	private static final Logger LOGGER = Logger.getLogger(MobileAdvanceSearchSOService.class);

	private MobileGenericMapper mobileGenericMapper;
	private MobileGenericValidator mobileGenericValidator;

	private IMobileGenericBO mobileGenericBO;
	

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	/**
	 * This method handle the main logic for fetching service orders based on search criteria
	 * @param apiVO
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		MobileSOAdvanceSearchResponse providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
		Results results = null;
		SecurityContext securityContext = null;
		Integer firmId =null;
		Integer roleId = apiVO.getRoleId();
		MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest = (MobileSOAdvanceSearchRequest) apiVO.getRequestFromPostPut();
		Integer providerId = apiVO.getProviderResourceId();
		String vendorId = apiVO.getProviderId();
		SOAdvanceSearchCriteriaVO searchRequestVO = null;
		SOSearchResultsVO soSearchResultsVO = null;
		if(null!=vendorId){
			firmId = Integer.parseInt(vendorId);
		}
		if(null!= mobileSOAdvanceSearchRequest){
			try {
				securityContext = getSecurityContextForVendor(providerId);
				providerSOSearchResponse = mobileGenericValidator.validateAdvanceSearchRequest(mobileSOAdvanceSearchRequest,securityContext,roleId);
				if (null == providerSOSearchResponse) {
					searchRequestVO = mobileGenericMapper.mapSoAdvanceSearchRequest(
							mobileSOAdvanceSearchRequest,  providerId,firmId,roleId);
					soSearchResultsVO = mobileGenericBO
							.getServiceOrderAdvanceSearchResults(searchRequestVO);
					if(null != soSearchResultsVO){
						if(soSearchResultsVO.isPageNumberExceededInd()){
							results  = Results.getError(ResultsCode.INVALID_PAGE_NUMBER
									.getMessage(), ResultsCode.INVALID_PAGE_NUMBER.getCode());
							providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
							providerSOSearchResponse.setResults(results);
						}
						else{
							providerSOSearchResponse = mobileGenericMapper
									.mapProviderSOAdvanceSearchResponse(soSearchResultsVO);
						}
					}
				}
				}
			catch (BusinessServiceException ex) {
				LOGGER.error("MobileSearchSOService.execute()--> Exception-->");
				ex.printStackTrace();
				results  = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR
						.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
				providerSOSearchResponse.setResults(results);
			}
			catch (Exception ex) {
				LOGGER.error("MobileSearchSOService.execute()--> Exception-->");
				ex.printStackTrace();
				results  = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR
						.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				providerSOSearchResponse = new MobileSOAdvanceSearchResponse();
				providerSOSearchResponse.setResults(results);
			}
		}
		return providerSOSearchResponse;
		
	}
	
	
	

	

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}










}