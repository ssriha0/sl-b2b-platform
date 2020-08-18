/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-August-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.search;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.Availability;
import com.newco.marketplace.api.beans.search.providerProfile.Company;
import com.newco.marketplace.api.beans.search.providerProfile.Credentials;
import com.newco.marketplace.api.beans.search.providerProfile.Metrics;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderType;
import com.newco.marketplace.api.beans.search.providerProfile.Reviews;
import com.newco.marketplace.api.beans.search.providerProfile.Skills;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.search.types.ResultMode;
import com.newco.marketplace.search.types.ServiceTypes;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.utils.DateUtils;


/**
 * This class would act as a mapper class for Searching Providers by Zip/ProfileId
 *
 * @author Infosys
 * Modified by Shekhar on 15/Sept/2009
 * @version 1.0
 */
public class SearchProviderMapper {
	private Logger logger = Logger.getLogger(SearchProviderMapper.class);
	private String resourcesContext;
	/**
	 * This method is for mapping providers returned from MarketProviderSearch
	 *  to SearchAPI objects
	 * 
	 * @param providerList List<ProviderSearchResponseVO>
	 * @return ProviderResults
	 */


	public ProviderResults mapSearchProviderResult(ProviderListVO response,ProviderResults providerResults, String resultMode) {
		List<ProviderSearchResponseVO> providerList = response.getProviderSearchResponseVO();		
		ProviderResults result =  mapSearchProviderResult(providerList, providerResults, resultMode);
		Result resultCode = result.getResults().getResult().get(0);
		result.setRecordCount(response.getTotalProvidersFound());
		resultCode.setMessage(response.getTotalProvidersFound() + " Provider(s) Found. " + resultCode.getMessage());
		return result;
	}

	public ProviderResults mapSearchProviderResult(List<ProviderSearchResponseVO> providerList,ProviderResults providerResults, 
			String resultMode) {
		logger.info("Entering mapSearchProviderResult method");

		List<ProviderType> providerTypeList = new ArrayList<ProviderType>();
		if (providerList == null) 
			providerList = new ArrayList<ProviderSearchResponseVO>();


		for (ProviderSearchResponseVO providerSearchResponseVO : providerList) {			
			ProviderType providerType = new ProviderType(providerSearchResponseVO.getResourceId(),
					providerSearchResponseVO.getName(),
					providerSearchResponseVO.getDistance(),
					providerSearchResponseVO.getMemberSince(), providerSearchResponseVO.getJobTitle(),
					getProviderImageURL(providerSearchResponseVO.getImageUrl()));
			providerType.setZip(providerSearchResponseVO.getZip());
 
			ResultMode resultModeObj = ResultMode.getObj(resultMode);

			switch (resultModeObj) {
			case MINIMUN:
				break;					
			case ALL:
				//Mapping  Skills starts
				Skills skills = new Skills(providerSearchResponseVO);				
				providerType.setSkills(skills);
				
				//Mapping provider's availability
				Availability availability = new Availability(providerSearchResponseVO.getAvailability());
				providerType.setAvailability(availability);
				providerType.setLanguages(providerSearchResponseVO.getLanguages());
				// No break;
			case LARGE:					
				Reviews reviews = new Reviews(providerSearchResponseVO);				
				providerType.setReviews(reviews);

				Credentials credentials = new Credentials(providerSearchResponseVO);				
				providerType.setCredentials(credentials);
				// No break;
			case MEDIUM:
				Company company = new Company(providerSearchResponseVO);				
				providerType.setCompany(company);
				Metrics metrics = new Metrics(providerSearchResponseVO);				
				providerType.setMetrics(metrics);
				break;
			}

			providerTypeList.add(providerType);
		}

		providerResults.setProviderList(providerTypeList);
		String msg = providerList.size() + " Provider(s) Returned";
		Results results = Results.getSuccess(msg);	
		providerResults.setResults(results);

		providerResults.setSchemaLocation(
				PublicAPIConstant.PROVIDER_SEARCH_RESPONSE_NAMESPACE);
		providerResults.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		providerResults.setNamespace(
				PublicAPIConstant.PROVIDER_SEARCH_RESPONSE_NAMESPACE);

		logger.info("Exiting mapSearchProviderResult method");
		return providerResults;
	}
	
	protected String getProviderImageURL(String dbURL){
		if(dbURL==null || dbURL.equalsIgnoreCase("NULL") || getResourcesContext()==null) 
			return dbURL;
		else {
				dbURL = dbURL.replaceFirst("/appl/sl/doc/public", "/public_images");				
				dbURL = getResourcesContext().trim().replaceFirst("http://", "") + dbURL.trim();
				dbURL = dbURL.replaceFirst("https://", "");
		}
		return dbURL;
	}
	
	public String getResourcesContext(){
		return resourcesContext;
	}
	
	public void setResourcesContext(String resourcesContext){
		this.resourcesContext = resourcesContext;
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
		List<Integer> profileIdList = new ArrayList<Integer>();
		List<Integer> categoryIdList = new ArrayList<Integer>();
		
	
		if ((null!=zipMap) && (!zipMap.isEmpty())) {
			
			String zip = zipMap.get(PublicAPIConstant.ZIP);			
			String categoryId = zipMap.get(PublicAPIConstant.CATEGORY_ID);
			 
			
			if (StringUtils.isNotBlank(categoryId)) {
				String []categoryIdArr = categoryId.split("-");
				for (String categoryIdT : categoryIdArr) {
					try {
						categoryIdList.add(new Integer(categoryIdT));
					} catch (NumberFormatException e) {
						logger.warn("Invalid categoryId:" + categoryIdT);
					}
				}
			}
			String serviceType = zipMap.get(PublicAPIConstant.SERVICE_TYPE);
			List<String> languageList = new ArrayList<String>();
			String language = zipMap.get(PublicAPIConstant.LANGUAGE);
			
			if (StringUtils.isNotBlank(language)) {
			   String []languages = language.split("-");
			   for (String lang : languages) {					
					languageList.add(lang);
				}
			}		
			
			String maxDistance = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_DISTANCE);
			String minRating = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MIN_RATING);
			String maxRating = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.MAX_RATING);
			String resultMode = zipMap.get(PublicAPIConstant.ProviderSearch.RESULT_MODE);
			
			ResultMode result = ResultMode.getObj(resultMode);			

			String pageSize = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_SIZE);
			String pageNumber = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.PAGE_NUMBER);
			searchRequestVO.setCategoryIds(categoryIdList);
			if (null != serviceType) {
				String []serviceTypeArr = serviceType.split("-");
				for (String serviceType1 : serviceTypeArr)
					searchRequestVO.addServiceType(ServiceTypes.findType(serviceType1));
			}
			
			//Get the favorite provider list
			String providers = zipMap.get(PublicAPIConstant.ProviderSearch.ProviderByZipCode.FAVOARITE_ID_LIST);

			searchRequestVO.setLanguages(languageList);
			searchRequestVO.setMaxDistance((null != maxDistance) ? new Integer(
					maxDistance) : 0);
			searchRequestVO.setMinRating((null != minRating) ? new Float(minRating)
			: 0);
			if(maxRating != null) {
				searchRequestVO.setMaxRating(new Float(maxRating));
			}
			searchRequestVO.setResultMode(result);
			searchRequestVO.setPageSize((null != pageSize) ? new Integer(pageSize)
			: 0);
			searchRequestVO.setPageNumber((null != pageNumber) ? new Integer(
					pageNumber) : 1);
			searchRequestVO.setZipCode(zip);
			//
			if (null != providers) {
				StringTokenizer strTok = new StringTokenizer(providers, "-", false);
				int noOfProfileIds = strTok.countTokens();
				for (int i = 1; i <= noOfProfileIds; i++) {
					profileIdList.add(new Integer(strTok.nextToken()));
				}
			}
			searchRequestVO.setIds(profileIdList);

		}
		logger.info("Exiting mapSearchProviderByZipRequest method");
		return searchRequestVO;
	}

}
