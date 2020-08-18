package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderCompany;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderFirmResults;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchProvidersByZipMapper;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.search.types.ServiceTypes;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
/** 
 * R 16_2_1: SL-21376:
 * New Service class for Search provider firms by zip and category and skill, SKU
 * This class extends the service class for Search Providers by zip code service 
 * The two additional parameters in the new service are SKU and subCategory.
 * 
 */
@Namespace("http://www.servicelive.com/namespaces/searchbyzip")
@APIResponseClass(ProviderFirmResults.class)
public class SearchProvidersByZipService extends SearchProviderByZipCodeService {
	
	private Logger logger = Logger.getLogger(SearchProvidersByZipService.class);
	private SearchProvidersByZipMapper searchByZipMapper;
	
	public SearchProvidersByZipService() {
		addOptionalGetParam(PublicAPIConstant.ZIP, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_DISTANCE, DataTypes.INTEGER);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MIN_RATING, DataTypes.DOUBLE);
		addOptionalGetParam(PublicAPIConstant.ProviderSearch.RESULT_MODE, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.LANGUAGE, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.CATEGORY_ID, DataTypes.STRING);
		addOptionalGetParam(PublicAPIConstant.SERVICE_TYPE, DataTypes.STRING);		
		addOptionalGetParam(PublicAPIConstant.SKU, DataTypes.STRING);
		super.addMoreClass(ProviderCompany.class);
	}
	
	/**
	 * This method is for retrieving providers based on  zip and category and skill, SKU.
	 * @see BaseService
	 * 
	 * 
	 */
	public IAPIResponse execute(APIRequestVO searchRequestVO) {
		logger.info("Entering searchProvidersByZipService method");
		ProviderFirmResults providerResults = new ProviderFirmResults();
		if(null!=searchRequestVO){
			Map<String,String> requestMap = searchRequestVO.getRequestFromGetDelete();
			boolean errorOccured = false;
			providerResults = validateRequestParams(requestMap, providerResults);
			if(null != providerResults.getResults() && null != providerResults.getResults().getError() && providerResults.getResults().getError().size() > 0){
				return providerResults;
			}
				//no validation errors occured
				try{
					//Fetching the categoryIds for the SKU in the request
					if(null!=requestMap && !(requestMap.isEmpty())){
						String sku = requestMap.get(PublicAPIConstant.SKU);
						List<Integer> skuCategoryIdList = null;
						List<String> mainCategoryNamesList = null;
						String categoryId = requestMap.get(PublicAPIConstant.CATEGORY_ID);
						List<Integer> categoryIdList = new ArrayList<Integer>();
						if (StringUtils.isNotBlank(categoryId)) {
							String []categoryIdArr = categoryId.split(PublicAPIConstant.HYPHEN);
							for (String categoryIdT : categoryIdArr) {
									categoryIdList.add(new Integer(categoryIdT));
							}
							// find the names of the main category from the categoryIdList, this list may 
							// contain category and sub-category ids as well
							mainCategoryNamesList = providerSearch.getMainCategoryNames(categoryIdList);
						}
						if (StringUtils.isNotBlank(sku)){
							//forming skuList from request
							String []skuArr = sku.split(PublicAPIConstant.SEPERATOR);
							List<String> skuList = new ArrayList<String>();
							for (String skuName : skuArr) {
									skuList.add(skuName);
							}
							//fetching categoryIds for the sku list
							skuCategoryIdList = providerSearch.getSkuCategoryIds(searchRequestVO.getBuyerId(),skuList);
							if(null==skuCategoryIdList || skuCategoryIdList.isEmpty()){
								Results results = 
										Results.getError(ResultsCode.INVALID_SKU.getMessage() , 
												ResultsCode.INVALID_SKU.getCode());
									providerResults.setResults(results);
									errorOccured = true;
							}
							else if (StringUtils.isNotBlank(categoryId)) {
								if (Collections.disjoint(skuCategoryIdList, categoryIdList)){
									Results results = 
											Results.getError(ResultsCode.SKU_CATEGORY_MISMATCH.getMessage() , 
													ResultsCode.SKU_CATEGORY_MISMATCH.getCode());
										providerResults.setResults(results);
										errorOccured = true;
								}
							}
						}
					
						if (!errorOccured){
							ProviderSearchRequestVO providerSearchRequestVO = searchByZipMapper.mapSearchProvidersRequest(requestMap,skuCategoryIdList);	
						
						ProviderListVO response = providerSearch.getProviders(providerSearchRequestVO);
						
						// Remove all Providers firms which do not match with the main category names
						if(null!=mainCategoryNamesList && !(mainCategoryNamesList.isEmpty())){
							List<ProviderSearchResponseVO> providerSearchResponseMainCategoryMatched = new ArrayList<ProviderSearchResponseVO>();
							for (ProviderSearchResponseVO providerSearchResponseVO : response.getProviderSearchResponseVO()) {
								if(mainCategoryNamesList.contains(providerSearchResponseVO.getPrimaryIndustry())){
									providerSearchResponseMainCategoryMatched.add(providerSearchResponseVO);
								}
							}
							response.setProviderSearchResponseVO(providerSearchResponseMainCategoryMatched);
						}
						
						if (response == null) {
							Results results = 
								Results.getError(ResultsCode.SEARCH_NO_RECORDS.getMessage(), ResultsCode.SEARCH_NO_RECORDS.getCode());
							providerResults.setResults(results);
						}
						
						else{
							String resultMode = requestMap.get(PublicAPIConstant.ProviderSearch.RESULT_MODE);
							
							//forming vendorIdList to fetch total review count
							List<String> vendorIdList = new ArrayList<String>();
							Map <Integer,Long> reviewCount = null;
							for (ProviderSearchResponseVO providerSearchResponseVO : response.getProviderSearchResponseVO()) {
								if (!(vendorIdList.contains(providerSearchResponseVO.getCompanyId()))){
									vendorIdList.add(new Integer(providerSearchResponseVO.getCompanyId()).toString());
								}
							}
							if (!vendorIdList.isEmpty()){
								reviewCount = providerSearch.getReviewCount(vendorIdList);
							}
							// SL-21468 add companyLogo URL
							Map<Long, String> companyLogos = null;
							if (!vendorIdList.isEmpty()){
								companyLogos = providerSearch.getCompanyLogo(vendorIdList);
							}
							//SL-21468  get values from application_properties table 
							Map<String,String> companyLogoPath=providerSearch.getCompanyLogoValues(); 
							
							Map<Integer,BasicFirmDetailsVO> basicFirmDetails=providerSearch.getBasicFirmDetails(vendorIdList);							
							Map<String,List<FirmServiceVO>> firmServiceVOs=null;
							if(null!=vendorIdList && null!=categoryIdList && !vendorIdList.isEmpty() && !categoryIdList.isEmpty()){
								firmServiceVOs=providerSearch.getVendorServiceDetails(vendorIdList,categoryIdList);
							}
							
							providerResults = searchByZipMapper.mapSearchProviderResponse(response, providerResults, resultMode, reviewCount,
									companyLogos,companyLogoPath,basicFirmDetails,firmServiceVOs);
						}
					}	
				}
			}
			catch (Exception e) {			
					Results results = 
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
						providerResults.setResults(results);
			}
			
		}
		

		logger.info("Exiting searchProvidersByZipService method");
		return providerResults;
	}
	
	
	private ProviderFirmResults validateRequestParams(
			Map<String, String> requestMap,
			ProviderFirmResults providerResults) {
		if ((null!=requestMap) && (!requestMap.isEmpty())) {
			//zip validation
			//retrieve zip from the request map
			
			String zip = requestMap.get(PublicAPIConstant.ZIP);
			if(!StringUtils.isBlank(zip)){
				if(!StringUtils.isNumeric(zip)){
					return setErrorResponse(ResultsCode.BUSINESSZIP_INVALID, providerResults);
				}
				else if(zip.length() != 5){
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
						String []categoryIdArr = catId.split(PublicAPIConstant.HYPHEN);
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
						String []serviceTypeArr = serviceTypes.split(PublicAPIConstant.HYPHEN);
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
						if(!NumberUtils.isNumber(minRating) || (Float.parseFloat(minRating) < 1) ||(Float.parseFloat(minRating) > 5)){
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
						if(!NumberUtils.isNumber(maxRating) || (Float.parseFloat(maxRating) < 1)|| (Float.parseFloat(maxRating) > 5)){
							return setErrorResponse(ResultsCode.INVALID_MAX_RATING, providerResults);
						}
					}else{
						return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
					}
				}
		
		}
		else{
			return setErrorResponse(ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS, providerResults);
		}
		return providerResults;
	}


	private ProviderFirmResults setErrorResponse(
			ResultsCode resultsCode,
			ProviderFirmResults providerResults) {
		Results results = 
				Results.getError(resultsCode.getMessage() , 
						resultsCode.getCode());
			providerResults.setResults(results);
		return providerResults;
	}

	public SearchProvidersByZipMapper getSearchByZipMapper() {
		return searchByZipMapper;
	}

	public void setSearchByZipMapper(SearchProvidersByZipMapper searchByZipMapper) {
		this.searchByZipMapper = searchByZipMapper;
	}

}


