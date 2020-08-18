/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-August-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.search;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderAvailability;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderCompany;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderCredentials;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderFirmResults;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderMetrics;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderReviews;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderSkills;
import com.newco.marketplace.api.beans.search.providerProfile.SearchProviderType;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.search.types.ResultMode;
import com.newco.marketplace.search.types.ServiceTypes;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.api.beans.firmDetail.FirmLocation;
import com.newco.marketplace.api.beans.firmDetail.FirmService;
import com.newco.marketplace.api.beans.firmDetail.FirmServices;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
/**
 * R 16_2_1: SL-21376:
 * This class would act as a mapper class for Search provider firms by zip and category and skill, SKU
 * This class extends the mapper class for Search Providers by zip code service 
 * The two additional parameters in the new service are SKU and subCategory.
 * 
 */

public class SearchProvidersByZipMapper extends SearchProviderMapper {
	private Logger logger = Logger.getLogger(SearchProvidersByZipMapper.class);

	public ProviderSearchRequestVO mapSearchProvidersRequest(
			Map<String, String> requestMap, List<Integer> skuCategoryIdList) {
		ProviderSearchRequestVO providerSearchRequest = null;
		if (null!=requestMap && !(requestMap.isEmpty())){
			String categoryId = requestMap.get(PublicAPIConstant.CATEGORY_ID);
			if (StringUtils.isBlank(categoryId) && null!=skuCategoryIdList && !(skuCategoryIdList.isEmpty())){
				//Add the categoryIds in the sku in the request
				StringBuffer skuCategory = new StringBuffer();
				for (Integer skuCategoryId:skuCategoryIdList){
					skuCategory.append(skuCategoryId);
					skuCategory.append(PublicAPIConstant.HYPHEN);
				}
				requestMap.put(PublicAPIConstant.CATEGORY_ID, skuCategory.toString());
			}
			//calling the mapRequest of search providers by zipcode API
			providerSearchRequest = mapSearchProviderByZipRequest(requestMap);
		}
		return providerSearchRequest;
	}
	
	/**
	 * This method is for mapping Zip Code and filter details to 
	 * ProviderSearchRequestVO object.
	 * 
	 * @param zipMap HashMap<String,String>
	 * @return ProviderSearchRequestVO
	 */
	public ProviderSearchRequestVO mapSearchProviderByZipRequest(
			Map<String, String> zipMap) {
		logger.info("Entering mapSearchProviderByZipRequest method");
		ProviderSearchRequestVO searchRequestVO = new ProviderSearchRequestVO();
		List<Integer> categoryIdList = new ArrayList<Integer>();
		
		if ((null!=zipMap) && (!zipMap.isEmpty())) {

			String zip = zipMap.get(PublicAPIConstant.ZIP);			
			String categoryId = zipMap.get(PublicAPIConstant.CATEGORY_ID);
			 
			if (StringUtils.isNotBlank(categoryId)) {
				String []categoryIdArr = categoryId.split(PublicAPIConstant.HYPHEN);
				for (String categoryIdT : categoryIdArr) {
						categoryIdList.add(new Integer(categoryIdT));
				}
			}
			String serviceType = zipMap.get(PublicAPIConstant.SERVICE_TYPE);
			List<String> languageList = new ArrayList<String>();
			String language = zipMap.get(PublicAPIConstant.LANGUAGE);
			
			if (StringUtils.isNotBlank(language)) {
			   String []languages = language.split(PublicAPIConstant.HYPHEN);
			   for (String lang : languages) {					
					languageList.add(lang);
				}
			}		
			
			String minRating = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MIN_RATING);
			String maxRating = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_RATING);
			String resultMode = zipMap.get(PublicAPIConstant.ProviderSearch.RESULT_MODE);
			
			ResultMode result = ResultMode.getObj(resultMode);			

			searchRequestVO.setCategoryIds(categoryIdList);
			if (null != serviceType) {
				String []serviceTypeArr = serviceType.split(PublicAPIConstant.HYPHEN);
				for (String serviceType1 : serviceTypeArr)
					searchRequestVO.addServiceType(ServiceTypes.findType(serviceType1));
			}
			
			searchRequestVO.setLanguages(languageList);
			String maxDistanceStr = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_DISTANCE);
			int maxDistance = ((null != maxDistanceStr) ? new Integer(maxDistanceStr) : PublicAPIConstant.DEFAULT_MAX_DISTANCE);
			searchRequestVO.setMaxDistance(maxDistance);
			searchRequestVO.setMinRating((null != minRating) ? new Float(minRating)
			: PublicAPIConstant.INTEGER_ZERO);
			if(maxRating != null) {
				searchRequestVO.setMaxRating(new Float(maxRating));
			}
			searchRequestVO.setResultMode(result);
			searchRequestVO.setZipCode(zip);
			
			int pageSize = PublicAPIConstant.DEFAULT_PAGE_SIZE; 
			int pageNumber = PublicAPIConstant.INTEGER_ZERO;		
				
			searchRequestVO.setPageNumber(pageNumber);
			searchRequestVO.setPageSize(pageSize);
			
			String sortBy = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.SORT_BY);
			String sortType = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.SORT_ORDER);
			if (sortBy !=null && sortBy.equalsIgnoreCase(PublicAPIConstant.RATING))
				searchRequestVO.setSortBy(ProviderSearchRequestVO.SORT_BY_RATING);
		
			if (sortType !=null && sortType.equalsIgnoreCase(PublicAPIConstant.DESC)){
				searchRequestVO.setSortOrder(ProviderSearchRequestVO.SORT_ORDER_DESC);
			}
			else{
				searchRequestVO.setSortOrder(ProviderSearchRequestVO.SORT_ORDER_ASC);
			}

		}
		logger.info("Exiting mapSearchProviderByZipRequest method");
		return searchRequestVO;
	}

	public ProviderFirmResults mapSearchProviderResponse(
			ProviderListVO response, ProviderFirmResults providerResults,
			String resultMode, Map<Integer, Long> reviewCount,Map<Long, String> companyLogos,
			Map <String,String> companyLogoPath,Map<Integer,BasicFirmDetailsVO> basicFirmDetails,Map<String,List<FirmServiceVO>> firmServiceVOs) { 
		
		List<ProviderSearchResponseVO> providerList = response.getProviderSearchResponseVO();		
		ProviderFirmResults result =  mapSearchProviderResponse(providerList, providerResults, resultMode, reviewCount,companyLogos,companyLogoPath,basicFirmDetails,firmServiceVOs);
		result.setRecordCount(result.getCompanyList().size());
		return result; 
		
	}

	private ProviderFirmResults mapSearchProviderResponse(
			List<ProviderSearchResponseVO> providerList,
			ProviderFirmResults providerResults, String resultMode, Map<Integer, Long> reviewCount,
			Map<Long, String> companyLogos,
			Map <String,String> companyLogoPath,Map<Integer,BasicFirmDetailsVO> basicFirmDetails,
			Map<String,List<FirmServiceVO>> firmServiceVOs) {
		logger.info("Entering mapSearchProviderResponse method");
		//SL-21468  get logo path variables 
        // default logo path
		String defaultLogo =companyLogoPath.get(PublicAPIConstant.DEFAULT_FIRM_LOGO);
		//getting the static logo path
		String staticUrl = companyLogoPath.get(PublicAPIConstant.FIRM_LOGO_PATH);
		// company logo save location
		String firmLogoSaveLocation = companyLogoPath.get(Constants.AppPropConstants.FIRM_LOGO_SAVE_LOC);	

		List<ProviderCompany> companyList = new ArrayList<ProviderCompany>();
		Map<Integer, List<Integer>> vendorProvidersMap = new HashMap<Integer, List<Integer>>();
		Map<Integer,ProviderSearchResponseVO> resourceMap = new HashMap<Integer, ProviderSearchResponseVO>();
		List<Integer> resourceIdList = null;
		for (ProviderSearchResponseVO providerSearchResponseVO : providerList) {
			if(vendorProvidersMap.containsKey(providerSearchResponseVO.getCompanyId())){
				resourceIdList = vendorProvidersMap.get(providerSearchResponseVO.getCompanyId());
				resourceIdList.add(providerSearchResponseVO.getResourceId());
			}
			else{
				resourceIdList = new ArrayList<Integer>();
				resourceIdList.add(providerSearchResponseVO.getResourceId());
				vendorProvidersMap.put(providerSearchResponseVO.getCompanyId(), resourceIdList);
			}
			
			resourceMap.put(providerSearchResponseVO.getResourceId(), providerSearchResponseVO);
		}

		ResultMode resultModeObj = ResultMode.getObj(resultMode);
		boolean minInd = false;
		for(Map.Entry<Integer, List<Integer>> entry : vendorProvidersMap.entrySet()){
			List<Integer> resourceList = entry.getValue();

			if(ResultMode.MINIMUN.equals(resultModeObj)){ 
				//insurance and policy list not needed for Minimum result mode 
				minInd=true;
			}
			ProviderCompany company = new ProviderCompany(resourceMap.get(resourceList.get(PublicAPIConstant.INTEGER_ZERO)),minInd);
			if(null != reviewCount && !reviewCount.isEmpty() && null != reviewCount.get(company.getCompanyId())){
				company.setFirmReviewCount(reviewCount.get(company.getCompanyId()).intValue());
			}
			
			//SL-21468-Adding company logo url
			if(null != companyLogos && !companyLogos.isEmpty() && null != companyLogos.get(new Long(company.getCompanyId()))){				
				String logoPath=companyLogos.get(new Long(company.getCompanyId()));
				logoPath=logoPath.replace(firmLogoSaveLocation,"");
				company.setCompanyLogoURL(staticUrl+logoPath); 	
			}
			else{
				//company logo not available
				company.setCompanyLogoURL(defaultLogo); 
			}
			// setting basic details
			if(null!=basicFirmDetails && null!=basicFirmDetails.get(company.getCompanyId())){
				
			BasicFirmDetailsVO detailsVO=basicFirmDetails.get(company.getCompanyId());
			if(null!=detailsVO){
			if(StringUtils.isNotBlank(detailsVO.getFirstName())){
				if(StringUtils.isNotBlank(detailsVO.getLastName())){
					company.setFirmOwner(detailsVO.getFirstName()+" "+detailsVO.getLastName());
				}
				else{
					company.setFirmOwner(detailsVO.getFirstName());
				}
			}
			DecimalFormat dfrmt = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WITH_ZEROS);

			if(null != detailsVO.getMinHourlyRate()){
				if(null != detailsVO.getMaxHourlyRate()){
					company.setHourlyRate("$"+dfrmt.format(detailsVO.getMinHourlyRate())+" - $"+dfrmt.format(detailsVO.getMaxHourlyRate()));
				}
				else{
					company.setHourlyRate("$"+dfrmt.format(detailsVO.getMinHourlyRate()));
				}
			}											
				// location 
			FirmLocation location = new FirmLocation();
			location.setStreet1(detailsVO.getStreet1());
			location.setStreet2(detailsVO.getStreet2());
			location.setCity(detailsVO.getCity());
			location.setState(detailsVO.getState());
			location.setZip(detailsVO.getZip());
			location.setZip4(detailsVO.getZip4());
			if(null !=location){
				company.setLocation(location); 
			} 
			}
			}
			
			
			// firm services
			
			if(null!=firmServiceVOs && null!=firmServiceVOs.get(company.getCompanyId().toString())){
			List<FirmServiceVO> firmServiceList=firmServiceVOs.get(company.getCompanyId().toString());
			if(!firmServiceList.isEmpty()){
			company.setServices(mapServiceDetails(firmServiceList));
			}
			}
		
			
			
			List<SearchProviderType> providerTypeList = new ArrayList<SearchProviderType>();
			for (Integer id:resourceList){
				ProviderSearchResponseVO providerSearchResponseVO =  resourceMap.get(id);
				SearchProviderType providerType = new SearchProviderType(providerSearchResponseVO.getResourceId(),
						providerSearchResponseVO.getName(),
						providerSearchResponseVO.getDistance(),
						providerSearchResponseVO.getMemberSince(), providerSearchResponseVO.getJobTitle(),
						getProviderImageURL(providerSearchResponseVO.getImageUrl()));
				providerType.setZip(providerSearchResponseVO.getZip());

				switch (resultModeObj) {
				case MINIMUN:
					break;					
				case ALL:
					//Mapping  Skills starts
					ProviderSkills skills = new ProviderSkills(providerSearchResponseVO);				
					providerType.setSkills(skills);
					
					//Mapping provider's availability
					ProviderAvailability availability = new ProviderAvailability(providerSearchResponseVO.getAvailability());
					providerType.setAvailability(availability);
					providerType.setLanguages(providerSearchResponseVO.getLanguages());
					// No break;
				case LARGE:					
					ProviderReviews reviews = new ProviderReviews(providerSearchResponseVO);				
					providerType.setReviews(reviews);

					ProviderCredentials credentials = new ProviderCredentials(providerSearchResponseVO);				
					providerType.setCredentials(credentials);
					// No break;
				case MEDIUM:
					ProviderMetrics metrics = new ProviderMetrics(providerSearchResponseVO);				
					providerType.setMetrics(metrics);
					break;
				}
				providerTypeList.add(providerType);
			}
			company.setProviders(providerTypeList);
			companyList.add(company);
		}
		
		providerResults.setCompanyList(companyList);
		String msg = companyList.size() + PublicAPIConstant.SEARCH_PROVIDER_FIRMS_RESPONSE;
		Results results = Results.getSuccess(msg);	
		providerResults.setResults(results);

		providerResults.setSchemaLocation(
				PublicAPIConstant.PROVIDER_FIRM_SEARCH_RESPONSE_NAMESPACE);
		providerResults.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		providerResults.setNamespace(
				PublicAPIConstant.PROVIDER_FIRM_SEARCH_RESPONSE_NAMESPACE);

		logger.info("Exiting mapSearchProviderResponse method");
		return providerResults;
	}
	
	
	private FirmServices mapServiceDetails(List<FirmServiceVO> serviceVOList) {
		// = firmDetailsResponseVO.getFirmServiceVOs();
		//List<FirmServiceVO>serviceVOList = firmServiceVOs.get(firmId);
		List<FirmService> serviceList = new ArrayList<FirmService>();
		FirmServices firmServices = null;

		if(null != serviceVOList && !serviceVOList.isEmpty()){
			firmServices = new FirmServices();
			for(FirmServiceVO serviceVO: serviceVOList){
				FirmService firmService = new FirmService();
				firmService.setProjectType(serviceVO.getProject());
				firmService.setServiceCategory(serviceVO.getRootCategory());
				serviceList.add(firmService);
			}
			firmServices.setService(serviceList);
		}
		return firmServices;
	}
 
}
