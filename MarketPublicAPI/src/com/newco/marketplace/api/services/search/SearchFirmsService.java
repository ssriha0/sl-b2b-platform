package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsRequest;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsResults;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.mappers.search.SearchFirmsMapper;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsResponseVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsVO;
/**
 * This class would act as a service class for getting firms for standard service offerings
 * 
 * API     : /buyers/{buyerId}/search/firms
 * APIType : Get
 * 
 */

@Namespace("http://www.servicelive.com/namespaces/searchFirmsService")
@APIRequestClass(SearchFirmsRequest.class)
@APIResponseClass(SearchFirmsResults.class)
public class SearchFirmsService extends BaseService {
	
	private IServiceOrderSearchBO serviceOrderSearchBO;
	private SearchFirmsMapper searchFirmsMapper;
	private Logger logger = Logger.getLogger(SearchFirmsService.class);
	
	
	/**
	 * This method is for retrieving firms based on zipCode,mainCategory.
	 * @see BaseService
	 * 
	 * @param zipMap  HashMap<String,String>
	 * @return String
	 */
	public IAPIResponse execute(APIRequestVO searchRequestVO) {
		logger.info("Entering SearchFirmsService method");
		SearchFirmsResults searchFirmsResults = new SearchFirmsResults();
		int buyerId = searchRequestVO.getBuyerIdInteger();
		SearchFirmsRequest searchFirmsRequest = (SearchFirmsRequest) searchRequestVO.getRequestFromPostPut();
		SearchFirmsVO searchFirmsVO=null;
		SearchFirmsResponseVO searchFirmsResponseVO =null;
		Results results = null;
		try {
			searchFirmsVO = searchFirmsMapper.mapRequest(searchFirmsRequest,buyerId);
			
			if (null!=searchFirmsVO){
					List<ErrorResult> validationErrors = validate(searchFirmsVO);
					if(null!=validationErrors && !validationErrors.isEmpty()){				
						results = new Results();
						results.setError(validationErrors);
					}
					else {
						searchFirmsResponseVO = serviceOrderSearchBO.getSearchFirmsResult(searchFirmsVO);
						if(null != searchFirmsResponseVO){
							// method to map the response to response object from VO
							searchFirmsResults = searchFirmsMapper.mapSearchFirms(searchFirmsResponseVO);
						}
						else {
							results=Results.getError(ResultsCode.SEARCH_NO_RECORDS.getMessage(), 
									ResultsCode.SEARCH_NO_RECORDS.getCode());
						}
					}
				}
		}
		catch (Exception e) {
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			e.printStackTrace();
			logger.error("Exception in Execute method of searchFirmsService"+ e.getMessage());
		}
		if(null != results){
			searchFirmsResults.setResults(results);
		}
		return searchFirmsResults;
	}
	
	/**
	 * This method is used for validating request
	 * @param searchFirmsVO	
	 * @return boolean
	 */
	private List<ErrorResult> validate(SearchFirmsVO searchFirmsVO) {
		logger.info("Entering SearchFirmsService.validate()");

		List<ErrorResult> validationErrors = null;
			validationErrors = new ArrayList<ErrorResult>();
			
			if(null !=searchFirmsVO.getServiceDate1() && null !=searchFirmsVO.getServiceDate2()){
				Date today = new Date();
				if (searchFirmsVO.getServiceDate1().compareTo(searchFirmsVO.getServiceDate2()) > 0) {
					addError(ResultsCode.START_DATE_NOT_PRIOR_ERROR.getMessage(),ResultsCode.START_DATE_NOT_PRIOR_ERROR.getCode(),validationErrors);
				}
				else if (searchFirmsVO.getServiceDate1().compareTo(today) < 0) {
					addError(ResultsCode.START_DATE_PAST_ERROR.getMessage(),ResultsCode.START_DATE_PAST_ERROR.getCode(),validationErrors);
				}
			}
			else if (null ==searchFirmsVO.getServiceDate1() && null !=searchFirmsVO.getServiceDate2()){
					addError(ResultsCode.PROVIDE_START_DATE.getMessage(),ResultsCode.PROVIDE_START_DATE.getCode(),validationErrors);
			
			}
		
		return validationErrors;
	}
	
	/**
	 * method to add the error code and message to the response
	 * @param message
	 * @param code
	 * @param validationErrors
	 */
	private void addError(String message, String code,List<ErrorResult> validationErrors) {
		ErrorResult result = new ErrorResult();
		result.setCode(code);
		result.setMessage(message);
		validationErrors.add(result);
	}

	public void setServiceOrderSearchBO(ServiceOrderSearchBO serviceOrderSearchBO) {
		this.serviceOrderSearchBO = serviceOrderSearchBO;
	}

	public SearchFirmsMapper getSearchFirmsMapper() {
		return searchFirmsMapper;
	}

	public void setSearchFirmsMapper(SearchFirmsMapper searchFirmsMapper) {
		this.searchFirmsMapper = searchFirmsMapper;
	}

	public IServiceOrderSearchBO getServiceOrderSearchBO() {
		return serviceOrderSearchBO;
	}

	public void setServiceOrderSearchBO(IServiceOrderSearchBO serviceOrderSearchBO) {
		this.serviceOrderSearchBO = serviceOrderSearchBO;
	}

}
