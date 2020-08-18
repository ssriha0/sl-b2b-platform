package com.newco.marketplace.api.beans.search.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.searchportal.ISearchPortalBO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.api.beans.search.provider.SearchProviderRequest;
import com.newco.marketplace.api.beans.search.provider.SearchProviderResponse;
import com.newco.marketplace.vo.apiSearch.FirmResponseData;
import com.newco.marketplace.vo.apiSearch.ProviderResponseData;
import com.newco.marketplace.vo.apiSearch.ProviderResponse;
import com.newco.marketplace.vo.apiSearch.SearchProviderRequestCriteria;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.servicelive.common.properties.IApplicationProperties;

public class SearchProviderService {
	private Logger logger = Logger.getLogger(SearchProviderService.class);
	private ISearchPortalBO searchPortalBO;
	private IApplicationProperties applicationProperties;
	
	public SearchProviderResponse execute(SearchProviderRequest searchProviderRequest, String responseFilter){
		SearchProviderResponse searchProviderResponse = new SearchProviderResponse();
		List<ProviderResponse> providerResponseList = new ArrayList<ProviderResponse>();
		Map<String,String> idMap = new HashMap<String,String>();
		int totalSuccessCount=0;
		int totalfailureCount=0;
		if(null!=searchProviderRequest){
			try{
				//check the response filter, if it is null, set it as both.
				if(null==responseFilter){
					responseFilter=PublicAPIConstant.RESPONSE_FILTER_BOTH;
				}
				List<SearchProviderRequestCriteria> searchProviderRequesrList = searchProviderRequest.getProviderRequests();
				if(null!=searchProviderRequesrList){
					//checking if the number of search requests is more than the max allowed for this API.
					Integer maxAllowedRequestCount =  Integer.parseInt(
							applicationProperties.getPropertyFromDB(PublicAPIConstant.DEFAULT_PROV_SEARCH_API_LIMIT));
					if(null!= maxAllowedRequestCount){
						if(searchProviderRequesrList.size()>maxAllowedRequestCount){
							searchProviderResponse.setResult(PublicAPIConstant.API_RESULT_FAILURE);
							searchProviderResponse.setMessege(PublicAPIConstant.API_REQUEST_COUNT_EXCEEDED_MSG);
							return searchProviderResponse;
						}
					}
					//iterate through the request object
					for(SearchProviderRequestCriteria searchCriteria : searchProviderRequesrList){
						//validate the search criteria
						searchCriteria = validateSearchCriteria(searchCriteria, idMap);
						//create a provider response object
						ProviderResponse provResponse = new ProviderResponse();
						//checking for validation errors
						if(!searchCriteria.isInvalidCriteria()){	
							provResponse.setId(searchCriteria.getId());
							StringBuilder sb = new StringBuilder();
							
							// to replace special characters
							String name = searchCriteria.getName();
							if(null!=name && StringUtils.isNotEmpty(name)){
								name = replaceSpecialHtmlCharacters(name);
								searchCriteria.setName(name);
							}
							
							//depending upon the response filter call the search provider method or search firm method or both
							//if response filter is both, search firms as well as providers
							
							if(responseFilter.equalsIgnoreCase(PublicAPIConstant.RESPONSE_FILTER_BOTH)){
								List<FirmResponseData> firmResponseList = searchPortalBO.searchProviderFirmFromAPI(searchCriteria);
								List<ProviderResponseData> serviceProviderResponseList = searchPortalBO.searchServiceProviderFromAPI(searchCriteria);
								// to replace special characters to html form
								firmResponseList = removeSpecialCharactersOfFirmResponseList(firmResponseList);
								
								// to replace special characters to html form
								serviceProviderResponseList = removeSpecialCharactersOfProvResponseList(serviceProviderResponseList);

								provResponse.setListOfFirms(firmResponseList);
								provResponse.setListOfProviders(serviceProviderResponseList);
								
								if(null!=firmResponseList){
									int firmListSize = firmResponseList.size();
									sb.append("Found ").append(firmListSize).append(" matching firms");
								}else{
									sb.append("Found 0 matching firms");
								}
								if(null!=serviceProviderResponseList){
									int providerListSize = serviceProviderResponseList.size();
									sb.append(" and ").append(providerListSize).append(" matching service providers.");
								}else{
									sb.append(" and 0 matching service providers.");
								}
							}else if(responseFilter.equalsIgnoreCase(PublicAPIConstant.RESPONSE_FILTER_PROVIDER)){
								//if response filter is provider, search only matching providers
								List<ProviderResponseData> serviceProviderResponseList = searchPortalBO.searchServiceProviderFromAPI(searchCriteria);
								// to replace special characters to html form
								serviceProviderResponseList = removeSpecialCharactersOfProvResponseList(serviceProviderResponseList);

								provResponse.setListOfProviders(serviceProviderResponseList);
								
								if(null!=serviceProviderResponseList){
									int providerListSize = serviceProviderResponseList.size();
									sb.append("Found ").append(providerListSize).append(" matching service providers.");
								}else{
									sb.append("Found 0 matching service providers.");
								}
							}else if(responseFilter.equalsIgnoreCase(PublicAPIConstant.RESPONSE_FILTER_FIRM)){
								//if response filter is firm, search only matching firms
								List<FirmResponseData> firmResponseList = searchPortalBO.searchProviderFirmFromAPI(searchCriteria);
								// to replace special characters to html form
								firmResponseList = removeSpecialCharactersOfFirmResponseList(firmResponseList);
								provResponse.setListOfFirms(firmResponseList);								
								if(null!=firmResponseList){
									int providerListSize = firmResponseList.size();
									sb.append("Found ").append(providerListSize).append(" matching firms.");
								}else{
									sb.append("Found 0 matching firms.");
								}
							}
							provResponse.setResult(PublicAPIConstant.API_RESULT_SUCCESS);
							provResponse.setMessage(sb.toString());
							providerResponseList.add(provResponse);
							totalSuccessCount=totalSuccessCount+1;//setting the success count + 1
						}else{
							provResponse.setId(searchCriteria.getId());
							provResponse.setResult(PublicAPIConstant.API_RESULT_FAILURE);
							provResponse.setMessage(searchCriteria.getMessage());
							providerResponseList.add(provResponse);
							totalfailureCount=totalfailureCount+1;//setting the failure count + 1
						}										
					}
					searchProviderResponse.setResult(PublicAPIConstant.API_RESULT_SUCCESS);
					searchProviderResponse.setProviderResponse(providerResponseList);
					searchProviderResponse.setResponseFilter(responseFilter);
					//setting the message
					StringBuilder sb = new StringBuilder();
					sb.append("Successful request count: ").append(totalSuccessCount).append(" and failed request count: ").append(totalfailureCount);
					searchProviderResponse.setMessege(sb.toString());
				}else{
					//add error saying that request detail is empty
					searchProviderResponse.setResult(PublicAPIConstant.API_RESULT_FAILURE);
					searchProviderResponse.setMessege(PublicAPIConstant.API_REQUEST_EMPTY_MSG);
				}				
			}catch(Exception e){
				logger.info("Exception at execute method of SearchProviderService: " +e.getMessage());
			}			
		}else{
			//add error saying that request is empty
			searchProviderResponse.setResult(PublicAPIConstant.API_RESULT_FAILURE);
			searchProviderResponse.setMessege(PublicAPIConstant.API_REQUEST_EMPTY_MSG);
		}
		
		return searchProviderResponse;
	}
	
	//to replace special characters in firm responses
	
	/**
	 * @param firmResponseList
	 * @return
	 */
	private List<FirmResponseData> removeSpecialCharactersOfFirmResponseList(
			List<FirmResponseData> firmResponseList) {
		if(firmResponseList!=null && firmResponseList.size()>0){
			for(FirmResponseData firmResponse : firmResponseList){
				if(null!=firmResponse){
					firmResponse.setLegalBusinessName(replaceSpecialCharacters(firmResponse.getLegalBusinessName()));
					//firmResponse.setLastActivity(replaceSpecialCharacters(firmResponse.getLastActivity()));
					firmResponse.setPrimaryIndustry(replaceSpecialCharacters(firmResponse.getPrimaryIndustry()));
					//firmResponse.setStatus(replaceSpecialCharacters(firmResponse.getStatus()));
					firmResponse.setCity(replaceSpecialCharacters(firmResponse.getCity()));
				}
			}
		}
		return firmResponseList;
	}
	
	
	// to replace special characters in provider responses
	
	/**
	 * @param provResponseList
	 * @return
	 */
	private List<ProviderResponseData> removeSpecialCharactersOfProvResponseList(
			List<ProviderResponseData> provResponseList) {
		if(provResponseList!=null && provResponseList.size()>0){
			for(ProviderResponseData providerResponse : provResponseList){
				if(null!=providerResponse){
					providerResponse.setFirmBusinessName(replaceSpecialCharacters(providerResponse.getFirmBusinessName()));
					providerResponse.setLastActivity(replaceSpecialCharacters(providerResponse.getLastActivity()));
					providerResponse.setPrimaryIndustry(replaceSpecialCharacters(providerResponse.getPrimaryIndustry()));
					providerResponse.setStatus(replaceSpecialCharacters(providerResponse.getStatus()));
					providerResponse.setCity(replaceSpecialCharacters(providerResponse.getCity()));
					providerResponse.setFirstName(replaceSpecialCharacters(providerResponse.getFirstName()));
					providerResponse.setLastName(replaceSpecialCharacters(providerResponse.getLastName()));

				}
			}
		}
		return provResponseList;
	}


	/**
	 * @param name
	 * 
	 * to replace special html characters in name
	 * 
	 */
	private String replaceSpecialHtmlCharacters(String name) {
		if(StringUtils.isNotBlank(name)){
			name = name.replaceAll("&quot;","\"");
			name = name.replaceAll("&amp;","&");
			name = name.replaceAll("&rsquo;","\\?");
			name = name.replaceAll("&gt;",">");
			name = name.replaceAll("&lt;","<");
			name = name.replaceAll("&apos;","'");
		}
	
		return name;
	}

	
	/**
	 * @param name
	 * @return
	 * 
	 * to replace special characters
	 * 
	 */
	private String replaceSpecialCharacters(String name) {
		if(StringUtils.isNotBlank(name)){
			name = name.replaceAll("\"","&quot;");
			name = name.replaceAll("&","&amp;");
			name = name.replaceAll("\\?","&rsquo;");
			name = name.replaceAll(">","&gt;");
			name = name.replaceAll("<","&lt;");
			name = name.replaceAll("'","&apos;");
		}
		
		return name;
	}
	
	private SearchProviderRequestCriteria validateSearchCriteria(SearchProviderRequestCriteria searchCriteria, Map<String,String> idMap){
		
		int countOfTagsWithNullValue=0;
		//if id column is null then no need to proceed
		if(StringUtils.isBlank(searchCriteria.getId())){
			searchCriteria.setInvalidCriteria(true);
			searchCriteria.setMessage(PublicAPIConstant.INVALID_ID_MSG);
			return searchCriteria;			
		}else{
			if(null!=idMap){
				String id = idMap.get(searchCriteria.getId());
				if(null!=id){
					searchCriteria.setInvalidCriteria(true);
					searchCriteria.setMessage(PublicAPIConstant.DUPLICATE_ID_MSG);
					return searchCriteria;
				}else{
					idMap.put(searchCriteria.getId(), "id");
				}
			}
		}
		//if search parameters are empty setting it to null
		if(null!=searchCriteria.getState() && StringUtils.isEmpty(searchCriteria.getState())){
			searchCriteria.setState(null);
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}else if(null!=searchCriteria.getState()){
			if(searchCriteria.getState().length()!=2){//state code should be a 2 letter abbreviation
				searchCriteria.setInvalidCriteria(true);
				searchCriteria.setMessage(PublicAPIConstant.INVALID_STATE_CD_MSG);
				return searchCriteria;	
			}
		}else if(null==searchCriteria.getState()){
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}
		//if search parameters are empty setting it to null
		if(null!=searchCriteria.getZipCode() && StringUtils.isEmpty(searchCriteria.getZipCode())){
			searchCriteria.setZipCode(null);
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}else if(null!=searchCriteria.getZipCode()){
			//validating whether zipcode is a number or not
			try{
				long intPhone = Long.parseLong(searchCriteria.getZipCode());
			}catch(NumberFormatException ex){
				searchCriteria.setInvalidCriteria(true);
				searchCriteria.setMessage(PublicAPIConstant.INVALID_ZIP_CODE_NUMBER_MSG);
				return searchCriteria;
			}
			//zip code should be a 5 letter code
			if(searchCriteria.getZipCode().length()!=5){
				searchCriteria.setInvalidCriteria(true);
				searchCriteria.setMessage(PublicAPIConstant.INVALID_ZIP_CODE_MSG);
				return searchCriteria;	
			}
			
		}else if(null==searchCriteria.getZipCode()){
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}
		//if search parameters are empty setting it to null
		if(null!=searchCriteria.getCity() && StringUtils.isEmpty(searchCriteria.getCity())){
			searchCriteria.setCity(null);
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}else if(null==searchCriteria.getCity()){
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}
		//if search parameters are empty setting it to null
		if(null!=searchCriteria.getName() && StringUtils.isEmpty(searchCriteria.getName())){
			searchCriteria.setName(null);
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}else if(null==searchCriteria.getName()){
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}
		//if search parameters are empty setting it to null
		if(null!=searchCriteria.getEmail() && StringUtils.isEmpty(searchCriteria.getEmail())){
			searchCriteria.setEmail(null);
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}else if(null==searchCriteria.getEmail()){
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}
		//if search parameters are empty setting it to null
		if(null!=searchCriteria.getPhone() && StringUtils.isEmpty(searchCriteria.getPhone())){
			searchCriteria.setPhone(null);
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}else if(null!=searchCriteria.getPhone()){
			//validating whether phone number is a number or not
			try{
				long intPhone = Long.parseLong(searchCriteria.getPhone());
			}catch(NumberFormatException ex){
				searchCriteria.setInvalidCriteria(true);
				searchCriteria.setMessage(PublicAPIConstant.INVALID_PHONE_NUMBER_MSG);
				return searchCriteria;
			}
			if(searchCriteria.getPhone().length()!=10){//phone number should be a 10 letter code
				searchCriteria.setInvalidCriteria(true);
				searchCriteria.setMessage(PublicAPIConstant.INVALID_PHONE_NUMBER_MSG);
				return searchCriteria;	
			}
			
		}else if(null==searchCriteria.getPhone()){
			countOfTagsWithNullValue = countOfTagsWithNullValue+1;
		}
		
		//if all of the search parameters are empty setting it as error scenario
		if(PublicAPIConstant.MAX_COUNT_OF_TAGS_WITH_NULL_VALUE==countOfTagsWithNullValue){
			searchCriteria.setInvalidCriteria(true);
			searchCriteria.setMessage(PublicAPIConstant.MAX_COUNT_OF_TAGS_WITH_NULL_VALUE_MSG);
		}
		
		return searchCriteria;
	}
	
	public ISearchPortalBO getSearchPortalBO() {
		return searchPortalBO;
	}
	public void setSearchPortalBO(ISearchPortalBO searchPortalBO) {
		this.searchPortalBO = searchPortalBO;
	}
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
}