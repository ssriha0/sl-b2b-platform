package com.newco.marketplace.api.mobile.services.v3_0;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.searchCriteria.SOGetSearchCriteriaResponse;
import com.newco.marketplace.api.beans.searchCriteria.SearchCriterias;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;

/**
 * This class would act as a Servicer class for Provider Retrieve Service Order
 * @author Infosys
 * @version 3.0
 */
@APIResponseClass(SOGetSearchCriteriaResponse.class)
public class SearchCriteriaService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(SearchCriteriaService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	private MobileGenericValidator mobileGenericValidator;	

	public SearchCriteriaService() {
				super();
	}
 /**
  * This method will fetch the search criteria details like market,service provider,status and subStatus
  * @param apiVO
  * @return IAPIResponse
  */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		
		SOGetSearchCriteriaResponse criteriaResponse = new SOGetSearchCriteriaResponse();
		SearchCriterias searchCriterias = new SearchCriterias();
		Results results = new Results();
		
		Integer roleId = apiVO.getRoleId();
		Integer resourceId = apiVO.getProviderResourceId();
		Integer vendorId = Integer.parseInt(apiVO.getProviderId());
		
		SoSearchCriteriaVO soSearchCriteriaVO = new SoSearchCriteriaVO();
		try {
	
			soSearchCriteriaVO = mobileGenericBO.getSearchCriteria(roleId,resourceId,vendorId);
			
			if(null!=soSearchCriteriaVO){
			searchCriterias = mobileGenericMapper.mapGetSearchCriteriaResponse(soSearchCriteriaVO);
			}
			criteriaResponse.setSearchCriterias(searchCriterias);
			results = Results.getSuccess();
			
		}catch(BusinessServiceException ex){
			ex.printStackTrace();
			LOGGER.error("Exception in execute method of SearchCriteriaService-->"+ ex.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception in execute method of SearchCriteriaService-->"+ e.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		if(null != results){
			criteriaResponse.setResults(results);
		}
		return criteriaResponse;
	}	

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}
	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	

}