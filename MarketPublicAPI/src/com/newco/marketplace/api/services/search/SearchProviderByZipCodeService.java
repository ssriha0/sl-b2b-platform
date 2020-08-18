package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.FacetType;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderType;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchProviderMapper;
import com.newco.marketplace.search.types.Bucket;
import com.newco.marketplace.search.types.ServiceTypes;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants.DocumentTypes.CATEGORY;

/**
 * This class would act as a service class for getting providers by zip Code Search.
 * 
 * API     : /providers/zip
 * APIType : Get
 * Request Processor  : {@link SearchRequestProcessor#getProvidersByZipCode}
 * Spring Bean Name   : ZipCodeService
 * Spring Context XML : apiApplicationContext.xml
 * 
 * @author Infosys & Shekhar Nirkhe(chandra@servicelive.com)
 * @since 10/10/2009
 * @version 1.0
 * 
 */

@Namespace("http://www.servicelive.com/namespaces/byzip")
@APIResponseClass(ProviderResults.class)
public class SearchProviderByZipCodeService extends AbstractSearchService {
	
	private SearchProviderMapper providerMapper;
	private ILookupBO lookupBO;
	private Logger logger = Logger.getLogger(SearchProviderByZipCodeService.class);
	
	public SearchProviderByZipCodeService() {
		addOptionalGetParam(PublicAPIConstant.ZIP, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_DISTANCE, DataTypes.INTEGER);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MIN_RATING, DataTypes.DOUBLE);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_SIZE, DataTypes.INTEGER);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_NUMBER, DataTypes.INTEGER);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.FAVOARITE_ID_LIST, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.RESULT_MODE, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.LANGUAGE, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.CATEGORY_ID, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.SERVICE_TYPE, DataTypes.STRING);		
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.COMPANY_ID, DataTypes.INTEGER);		
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.STATE, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.CITY, DataTypes.STRING);
		super.addMoreClass(ProviderType.class);
	}
	
	/**
	 * This method is for retrieving providers based on zipCode.
	 * @see BaseService
	 * 
	 * @param zipMap  HashMap<String,String>
	 * @return String
	 */
	public IAPIResponse execute(APIRequestVO searchRequestVO) {
		logger.info("Entering dispatchProviderSearchByZipCodeRequest method");
		if (searchRequestVO == null)
			return null;
		
		Map<String,String> requestMap = searchRequestVO.getRequestFromGetDelete();

		ProviderResults providerResults = new ProviderResults();
		
		providerResults = validateRequestParams(requestMap, providerResults);
		
		if(null != providerResults.getResults() && null != providerResults.getResults().getError() && providerResults.getResults().getError().size() > 0){
			return providerResults;
		}
		
		ProviderSearchRequestVO providerSearchRequestVO = providerMapper
				.mapSearchProviderByZipRequest(requestMap);	
		
		String state = searchRequestVO.getRequestParamFromGetDelete(PublicAPIConstant.ProviderSearch.ProviderByZipCode.STATE);
		String city = searchRequestVO.getRequestParamFromGetDelete(PublicAPIConstant.ProviderSearch.ProviderByZipCode.CITY);
		if (providerSearchRequestVO.getZipCode() == null) {
			if (state == null || city == null) {				
				Results results = 
					Results.getError(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getMessage() , 
							ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
				providerResults.setResults(results);
				return providerResults;
			}
		}
		
		String catName = requestMap.get(PublicAPIConstant.CATEGORY_NAME);
		if (catName != null) {
			List<Integer> list = getNodeListByCategories(catName);
			providerSearchRequestVO.setCategoryIds(list);	
		}
		
		int pageSize = 0, pageNumber = 0, maxDistance = 0;
		if ((null != requestMap) && (!requestMap.isEmpty())) {
			String pageSizeStr = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_SIZE);
			String pageNumberStr = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_NUMBER);
			pageSize = ((null != pageSizeStr) ? new Integer(pageSizeStr) : PublicAPIConstant.ProviderSearch.ProviderByZipCode.DEFAULT_PAGE_SIZE);
			pageNumber = ((null != pageNumberStr) ? new Integer(pageNumberStr) : 0);	
			//Setting default maxdistance as 40
			String maxDistanceDefault=requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_DISTANCE);
			maxDistance=((null != maxDistanceDefault) ? new Integer(maxDistanceDefault) : PublicAPIConstant.ProviderSearch.ProviderByZipCode.DEFAULT_MAX_DISTANCE);		
		}
		
		String providerIds = searchRequestVO.getRequestParamFromGetDelete(PublicAPIConstant.ProviderSearch.ProviderByZipCode.FAVOARITE_ID_LIST);
		String sortBy = searchRequestVO.getRequestParamFromGetDelete(PublicAPIConstant.ProviderSearch.ProviderByZipCode.SORT_BY);
		String sortType = searchRequestVO.getRequestParamFromGetDelete(PublicAPIConstant.ProviderSearch.ProviderByZipCode.SORT_ORDER);
		String companyId = searchRequestVO.getRequestParamFromGetDelete(PublicAPIConstant.ProviderSearch.ProviderByZipCode.COMPANY_ID);
		
		if (sortBy !=null && sortBy.equalsIgnoreCase("rating"))
			providerSearchRequestVO.setSortBy(ProviderSearchRequestVO.SORT_BY_RATING);
		
		if (sortType !=null && sortType.equalsIgnoreCase("Asc"))
			providerSearchRequestVO.setSortOrder(ProviderSearchRequestVO.SORT_ORDER_ASC);
		
		if (companyId != null) {
			providerSearchRequestVO.setCompanyId(Integer.parseInt(companyId));	
		}
		
		if (state != null) {
			providerSearchRequestVO.setState(state);	
		}
		
		if (city != null) {
			providerSearchRequestVO.setCity(city);	
		}

		List<Integer> profileIdList = new ArrayList<Integer>();
		if (null != providerIds) {
			StringTokenizer strTok = new StringTokenizer(providerIds, "-", false);
			int noOfProfileIds = strTok.countTokens();
			for (int i = 1; i <= noOfProfileIds; i++) {
				profileIdList.add(new Integer(strTok.nextToken()));
			}
		}
		providerSearchRequestVO.setProviderIds(profileIdList);
		providerSearchRequestVO.setPageNumber(pageNumber);
		providerSearchRequestVO.setPageSize(pageSize);
		providerSearchRequestVO.setMaxDistance(maxDistance);
		
		ProviderListVO response = providerSearch.getProviderList(providerSearchRequestVO);
		
		if (response == null) {
			Results results = 
				Results.getError(ResultsCode.SEARCH_NO_RECORDS.getMessage(), ResultsCode.SEARCH_NO_RECORDS.getCode());
			providerResults.setResults(results);
			return providerResults;
		}
		
		providerResults.setPageSize(pageSize);
		providerResults.setPageNum(pageNumber);
	
		List<FacetType> facets = new ArrayList<FacetType>();
		for (Bucket bucket :response.getFacets()) {
			if (bucket != null) {
				FacetType f = new FacetType(bucket);
				facets.add(f);
			}
		}
		providerResults.setFacet(facets);
		
		String resultMode = requestMap.get(PublicAPIConstant.ProviderSearch.RESULT_MODE);
		providerResults = providerMapper.mapSearchProviderResult(response, providerResults, resultMode);
		logger.info("Exiting dispatchProviderSearchByZipCodeRequest method");
		return providerResults;
	}
	
	/**
	 * This method validates the following:
	 * zip is numeric and 5 in length
	 * @param requestMap
	 * @param providerResults 
	 * @return 
	 */
	private ProviderResults validateRequestParams(Map<String, String> requestMap, ProviderResults providerResults) {
		if ((null!=requestMap) && (!requestMap.isEmpty())) {
			//zip validation
			//retrieve zip from the request map
			
			String zip = requestMap.get(PublicAPIConstant.ZIP);
			if(!StringUtils.isBlank(zip)){
				if(!StringUtils.isNumeric(zip)){
					return setErrorResponse(ResultsCode.BUSINESSZIP_INVALID, providerResults);
				}
				if(zip.length() != 5){
					return setErrorResponse(ResultsCode.BUSINESSZIP_INVALID_LENGTH, providerResults);
				}
			}else{
				return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
			}
			
			
			//category id validation
			//retrieve category id from the request map
			String catId = requestMap.get(PublicAPIConstant.CATEGORY_ID);
			if(null!=catId){
				if(!StringUtils.isBlank(catId)){
					String []categoryIdArr = catId.split("-");
					for(String cat:categoryIdArr){
						if(!StringUtils.isNumeric(cat)){
							return setErrorResponse(ResultsCode.INVALID_CATEGORY_ID, providerResults);
						}
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			//service type validation
			//retrieve service type from the request map
			String serviceTypes = requestMap.get(PublicAPIConstant.SERVICE_TYPE);
			if(null!=serviceTypes){
				if(!StringUtils.isBlank(serviceTypes)){
					String []serviceTypeArr = serviceTypes.split("-");
					for(String s:serviceTypeArr){
						if((String.valueOf(ServiceTypes.findType(s))).equals(PublicAPIConstant.ProviderSearch.UNKNOWN)){
							return setErrorResponse(ResultsCode.INVALID_SERVICE_TYPE, providerResults);
						}
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			//max distance validation - enum
			//retrieve max distance from the request map
			String maxDist = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_DISTANCE);
			if(null!=maxDist){
				if(!StringUtils.isBlank(maxDist)){
					if(!PublicAPIConstant.MAX_DISTANCE().contains(maxDist)){
						return setErrorResponse(ResultsCode.INVALID_MAX_DISTANCE, providerResults);
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			//Min rating validation - decimal and greater than 1
			//retrieve min rating from the request map
			String minRating = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MIN_RATING);
			if(null!=minRating){
				if(!StringUtils.isBlank(minRating)){
					if(!NumberUtils.isNumber(minRating) || (Float.parseFloat(minRating) < 1)||(Float.parseFloat(minRating) > 5)){
						return setErrorResponse(ResultsCode.INVALID_MIN_RATING, providerResults);
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			//Max rating validation - decimal and less than 5
			//retrieve max rating from the request map
			String maxRating = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_RATING);
			if(null!=maxRating){
				if(!StringUtils.isBlank(maxRating)){
					if(!NumberUtils.isNumber(maxRating) ||(Float.parseFloat(maxRating) < 1)|| (Float.parseFloat(maxRating) > 5)){
						return setErrorResponse(ResultsCode.INVALID_MAX_RATING, providerResults);
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			//page size validation - enum
			//retrieve page size from the request map
			String pageSize = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_SIZE);
			if(null!=pageSize){
				if(!StringUtils.isBlank(pageSize)){
					if(!PublicAPIConstant.PAGE_SIZE().contains(pageSize)){
						return setErrorResponse(ResultsCode.INVALID_PAGE_SIZE, providerResults);
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			//page number validation - number
			//retrieve page number from the request map
			String pageNum = requestMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_NUMBER);
			if(null!=pageNum){
				if(!StringUtils.isBlank(pageNum)){
					if(!StringUtils.isNumeric(pageNum)){
						return setErrorResponse(ResultsCode.INVALID_PAGE_NUM, providerResults);
					}
				}else{
					return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
				}
			}
			
		}
		return providerResults;
	}

	private ProviderResults setErrorResponse(ResultsCode resultsCode,
			ProviderResults providerResults) {
		Results results = 
				Results.getError(resultsCode.getMessage() , 
						resultsCode.getCode());
			providerResults.setResults(results);
		return providerResults;
	}
	
	public ILookupBO getLookupBO() {
		return lookupBO;
	}


	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}
	

	public SearchProviderMapper getProviderMapper() {
		return providerMapper;
	}

	public void setProviderMapper(SearchProviderMapper providerMapper) {
		this.providerMapper = providerMapper;
	}

}
