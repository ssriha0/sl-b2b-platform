package com.newco.marketplace.api.services.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderResults;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.search.SearchProviderMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;


/**
 * This class would act as a service class for getting providers by zip Code Search.
 * 
 * API     : /providers
 * APIType : Get
 * Request Processor  : {@link SearchRequestProcessor#getProvidersByProfileId}
 * Spring Bean Name   : ProviderByIdService
 * Spring Context XML : apiApplicationContext.xml
 * 
 * @author Infosys & Shekhar Nirkhe(chandra@servicelive.com)
 * @since 10/10/2009
 * @version 1.0
 * 
 */

@Namespace("http://www.servicelive.com/namespaces/byzip")
@APIResponseClass(ProviderResults.class)
public class SearchProviderByIdService extends BaseService{
	private ISearchProvider providerSearch;
	private SearchProviderMapper providerMapper;
	private XStreamUtility conversionUtility;
	private ILookupBO lookupBO;
	private Logger logger = Logger.getLogger(SearchProviderByIdService.class);
	
	public SearchProviderByIdService() {
		addOptionalGetParam(PublicAPIConstant.ZIP, DataTypes.STRING);
		addRequiredGetParam(PublicAPIConstant.IDLIST, DataTypes.STRING);
	}
	
	
	/**
	 * This method is for retrieving providers based on profileIds. It will call solr service to
	 * retrieve provider details for requested ID(s).  The request can contain multiple IDs separated
	 * by hyphen(-) . It will convert them into an array before sending it to back-end.
	 * 
	 * @param providers String
	 * @return String
	 */	
	public IAPIResponse execute(APIRequestVO searchVO) {
		logger.info("Entering execute method");
		Map<String,String> requestMap = searchVO.getRequestFromGetDelete();
		
		ProviderResults providerResults = new ProviderResults();
		
		String providerIds = requestMap.get(PublicAPIConstant.IDLIST);
		String zipCode = requestMap.get(PublicAPIConstant.ZIP);

		List<Integer> profileIdList = new ArrayList<Integer>();
		ProviderSearchRequestVO providerSearchRequestVO = new ProviderSearchRequestVO();

		if (null != providerIds) {
			StringTokenizer strTok = new StringTokenizer(providerIds, "-", false);		
			if (strTok != null) {
				while (strTok.hasMoreTokens()) {
					String token = strTok.nextToken();
					try {
						if (token != null)
							profileIdList.add(new Integer(token));
					} catch (NumberFormatException e) {
						logger.warn("Provider Id " + token + " can not be added to the list");
					}
				}
			}
		}
		providerSearchRequestVO.setProviderIds(profileIdList);
		providerSearchRequestVO.setZipCode(zipCode);
		
		List<ProviderSearchResponseVO> providerResponseList = providerSearch
											.getProvidersById(providerSearchRequestVO);
		providerResults.setPageSize(200);
		providerResults.setPageNum(1);
		providerResults = providerMapper
								.mapSearchProviderResult(providerResponseList,providerResults, "All");
		logger.info("Exiting dispatchProviderSearchByIdRequest method");
		return providerResults;
	}
	
	/**
	 * Method to validate zip code
	 * @param zip
	 * @return boolean
	 * @throws DataServiceException
	 * No need to validate ZIP as all zips are valid for search.
	 */
//	private boolean validateZipCode(String zip) throws DataServiceException {
//		try {
//			 if(StringUtils.isEmpty(zip)) {
//				 return true;
//			 }
//			 if (lookupBO.checkIFZipExists(zip) != null) {
//				 return true;
//			 }
//		} catch(DataServiceException e) {
//			logger.error("zip code validation failed for zip: " + zip + ", error message: " 
//					+ e.getMessage());
//			throw e;
//		}
//		return false;
//	}
	public ISearchProvider getProviderSearch() {
		return providerSearch;
	}

	public void setProviderSearch(ISearchProvider providerSearch) {
		this.providerSearch = providerSearch;
	}

	public SearchProviderMapper getProviderMapper() {
		return providerMapper;
	}

	public void setProviderMapper(SearchProviderMapper providerMapper) {
		this.providerMapper = providerMapper;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}


	public ILookupBO getLookupBO() {
		return lookupBO;
	}


	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}
}
