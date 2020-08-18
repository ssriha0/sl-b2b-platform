package com.newco.marketplace.searchImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.search.solr.bo.ProviderSearchSolrBO;
import com.newco.marketplace.search.solr.bo.SkillTreeSearchBO;
import com.newco.marketplace.search.types.Bucket;
import com.newco.marketplace.search.types.Category;
import com.newco.marketplace.search.types.CustomerFeedback;
import com.newco.marketplace.search.types.ProviderCredential;
import com.newco.marketplace.search.types.Skill;
import com.newco.marketplace.search.vo.BaseRequestVO;
import com.newco.marketplace.search.vo.BaseResponseVO;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.search.vo.SearchQueryResponse;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.searchInterface.ISearchProvider;
import com.newco.marketplace.util.CommonUtility;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.VendorCredentialsVO;


public class SearchProviderImpl implements ISearchProvider {
	private static final int CUSTOMER_REVIEWS_COUNT = 5;
	private static final long CACHE_TIMEOUT = SimpleCache.ONE_HOUR * 6; // 6 hours
	private static final Logger logger = Logger.getLogger(SearchProviderImpl.class);

	private String solrServerURL;
	private String proxyHost;
	private Integer proxyPort;
	private boolean proxySet;

	private boolean isYahooSpellCheck;
	private Long yahooServiceTimeOut;
	private String yahooServiceURL;
	private String yahooServiceappId;

	private IProviderSearchBO backendBO;
	private ProviderSearchSolrBO providerSearchBO;
	private static List<Bucket> facetList = new ArrayList<Bucket>();

	public SearchProviderImpl() {
		loadFacets(CommonUtility.getPropertiesAsMap());
	}


	/**
	 * This method loads information from property file regarding various
	 * facets start and end ranges.
	 *
	 * @param map
	 */
	private void loadFacets (HashMap<String, String> map) {
		if(facetList.size() > 0) {
			return;
		}

		for (String key: map.keySet()) {
			Bucket bucket = null;

			//populate provider ratings bucket
			if (key.toString().startsWith("provider.ratings.bucket")) {
				bucket = new Bucket(Bucket.RATING_TYPE);
			} else if(key.toString().startsWith("provider.distance.bucket")) {
				//populate provider distance bucket
				bucket = new Bucket(Bucket.DISTANCE_TYPE);
			} else if(key.toString().startsWith("provider.language.bucket")) {
				bucket = new Bucket(Bucket.LANGUAGE_TYPE);
			} else {
				continue;
			}

			if(bucket != null) {
				String keyArray[] = key.toString().split("-");
				String valueArray[] = CommonUtility.getMessage(key.toString()).split(":");
				String startRange= null;
				String endRange = null;
				if(valueArray.length > 0) {
					startRange = valueArray[0];
					if(bucket.getType()!= Bucket.LANGUAGE_TYPE && !startRange.matches("\\d+")) {
						throw new NumberFormatException("startRange needs to be a number, number found: " + startRange);
					}
				}

				if(valueArray.length > 1) {
					endRange = valueArray[1];
					if(bucket.getType()!= Bucket.LANGUAGE_TYPE && !endRange.matches("\\d+")) {
						throw new NumberFormatException("endRange needs to be a number, number found: " + endRange);
					}
				}

				if(keyArray != null && keyArray.length>1) {
					bucket.setSortOrder(new Integer(keyArray[1]));
				}

				if(valueArray!=null) {
					bucket.setStartRange(startRange);
					if(valueArray.length>1) {
						bucket.setEndRange(endRange);
					}
				}
				facetList.add(bucket);
				logger.info(bucket);
			}

		}
	}


	public SearchProviderImpl(String solrServerURL,String proxyHost, String proxyPort,
			String proxySet, String yahooSpellCheckSet, String yahooAppId, String yahooTimeout, String yahooServiceURL){
			this.solrServerURL = solrServerURL;
			this.isYahooSpellCheck = new Boolean(yahooSpellCheckSet);
			this.proxyHost = proxyHost;
			this.proxyPort = new Integer(proxyPort);
			this.proxySet = new Boolean(proxySet);
			this.yahooServiceappId = yahooAppId;
			this.yahooServiceTimeOut = new Long(yahooTimeout);
			this.yahooServiceURL = yahooServiceURL;
	}


	//TODO This method should return BaseResponseVO. Need to change.
	public List<SkillTreeResponseVO> getSkillTree(SkillRequestVO skillTreeRequest){
		final String key = "/skilltree/full";
		List<SkillTreeResponseVO> voSkillList;
		String keyWord = skillTreeRequest.getSkillTree();
		SearchQueryResponse response = null;

		if (StringUtils.isBlank(keyWord)) { //Full Tree, Make sense to cache
			response = (SearchQueryResponse)SimpleCache.getInstance().get(key);
			if (response == null) {
				response = getSkillTreeSearchBO().query(skillTreeRequest);
				SimpleCache.getInstance().put(key, response, CACHE_TIMEOUT);
			}			
		} else {
			response = getSkillTreeSearchBO().query(skillTreeRequest);
		}
		if (response == null)
			return null;
		voSkillList = (List)response.getBaseResponseVO();
		return voSkillList;
	}

	
    public int getProvidersCount(String state, String city, List<Integer> categoryIds){
    	ProviderSearchRequestVO requestProvider = new ProviderSearchRequestVO();
    	if (state != null) {    		
    		requestProvider.setState(state);    		
    		requestProvider.setCity(city);
    	}
    
    	requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_CITY_STATE);
    	if (categoryIds != null)
    		requestProvider.setCategoryIds(categoryIds);
    	SearchQueryResponse response = getProviderSearchBO().query(requestProvider);
    	if (response != null)
    		return (Integer)response.getProperty("count");

    	return -1;
    }
    
  
    public HashMap<Integer, Integer> getProvidersCountBySkills(String state, String city){
    	ProviderSearchRequestVO requestProvider = new ProviderSearchRequestVO();
    	requestProvider.setState(state);
    	requestProvider.setCity(city);
    	
    	requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_SKILLS);
    	SearchQueryResponse response = getProviderSearchBO().query(requestProvider);
    	if (response != null)
    		return (HashMap<Integer, Integer>)response.getProperty("skills");

    	return new HashMap<Integer, Integer>();
    }
    
    public Map<String, Long> getCities(String state, List<Integer> categoryIds){
    	ProviderSearchRequestVO requestProvider = new ProviderSearchRequestVO();
    	Map<String, Long>  map = null;
    	
    	requestProvider.setState(state);
    	requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_CITY_LIST);
    	requestProvider.setCategoryIds(categoryIds);
    	SearchQueryResponse response = getProviderSearchBO().query(requestProvider);
    	if (response != null) {
    		map = (Map<String, Long>)response.getProperty("cities");
    	}
    	
    	if (map == null)
    		map = new HashMap<String, Long>(); 
    	return map;
    }


	@SuppressWarnings({ "fallthrough", "unchecked" })
	public ProviderListVO getProviderList(ProviderSearchRequestVO ProviderListRequest){
		ProviderSearchRequestVO requestProvider = (ProviderSearchRequestVO)ProviderListRequest;
		if (requestProvider.getState() != null)
			requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_CITY_STATE_SKILLS);
		else 	
		  requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_ZIP);


		List<ProviderSearchResponseVO> voProviderList;
		SearchQueryResponse res = getProviderSearchBO().query(requestProvider);
		if (res == null)
			return null;
		voProviderList = (List)res.getBaseResponseVO();
		if (backendBO != null) {
			switch (ProviderListRequest.getResultMode()) {
			case MINIMUN:
				break;
			case ALL:
				addSkills(voProviderList);
				// no break;
			case LARGE:
				addLicenses(voProviderList);
				addReviews(voProviderList);
				// No break;
			case MEDIUM:
				break;
			}
		}

		int totalCount = (Integer)res.getProperty(ISearchProvider.PROPERTY_TOTAL_PROVIDER_FOUND);
		ProviderListVO vo  =  new ProviderListVO(voProviderList, totalCount);

		List<Bucket> facetList = (List)res.getProperty(ISearchProvider.PROPERTY_FACETS);
		vo.setFacets(facetList);
		return vo;
	}

	public List<ProviderSearchResponseVO> getProvidersById(ProviderSearchRequestVO providerIdRequest){//List <Integer>providerId){
		ProviderSearchRequestVO requestProvider = (ProviderSearchRequestVO)providerIdRequest;
		requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_RESOURCE_ID);


		List<ProviderSearchResponseVO> voProviderList;// = new ArrayList<ProviderSearchResponseVO>();

		voProviderList = (List)getProviderSearchBO().query(requestProvider).getBaseResponseVO();
		if (backendBO != null) {
			addSkills(voProviderList);
			addLicenses(voProviderList);
			addReviews(voProviderList);
		}

    	for (ProviderSearchResponseVO vo : voProviderList) {
    		System.out.println(vo);
    	}

		return voProviderList;
	}


	public ProviderSearchSolrBO getProviderSearchBO() {
		if (providerSearchBO == null) {
			providerSearchBO =  new ProviderSearchSolrBO(getSolrServerURL());
			if(facetList.size() == 0) {
				loadFacets(CommonUtility.getPropertiesAsMap());
			}
			providerSearchBO.setFacetList(facetList);
		}
		return providerSearchBO;
	}

	public SkillTreeSearchBO getSkillTreeSearchBO() {
		SkillTreeSearchBO skillTreeBO = new SkillTreeSearchBO(
				getSolrServerURL(), getProxyHost(), getProxyPort(),isProxySet(),
				getYahooServiceappId(), isYahooSpellCheck(),
				getYahooServiceTimeOut(),
				getYahooServiceURL());
		return skillTreeBO;
	}

	public String getSolrServerURL() {
		return solrServerURL;
	}

	public BaseResponseVO getBaseResponseBO() {
		return new BaseResponseVO();
	}

	private void addLicenses(List<ProviderSearchResponseVO> list) {
		for (ProviderSearchResponseVO obj :list) {
			List<TeamCredentialsVO> cList = getResourceCredentials(obj.getResourceId());
			for (TeamCredentialsVO tvo: cList) {
				ProviderCredential p = new ProviderCredential(tvo);
				obj.addLicense(p);
			}

			List<VendorCredentialsVO> vList = getCompanyCredentials(obj.getCompanyId());
			for (VendorCredentialsVO tvo: vList) {
				ProviderCredential p = new ProviderCredential(tvo);
				obj.addLicense(p);
			}
		}
	}

	private List<TeamCredentialsVO> getResourceCredentials(int resourceId) {
		final String key = "/ProviderSearch/ResourceCredentials/resourceId/";
		List<TeamCredentialsVO> cList = (List)SimpleCache.getInstance().get(key + resourceId);
		if (cList == null) {
			cList = backendBO.getResourceCredentials(resourceId);
			SimpleCache.getInstance().put(key + resourceId, cList, CACHE_TIMEOUT);
		}
		return cList;
	}

	private List<VendorCredentialsVO> getCompanyCredentials(int resourceId) {
		final String key = "/ProviderSearch/CompanyCredentials/resourceId/";
		List<VendorCredentialsVO> cList = (List)SimpleCache.getInstance().get(key + resourceId);
		if (cList == null) {
			cList = backendBO.getVendorCredentials(resourceId);
			SimpleCache.getInstance().put(key + resourceId, cList, CACHE_TIMEOUT);
		}
		return cList;
	}

	private void addReviews(List<ProviderSearchResponseVO> list) {
		for (ProviderSearchResponseVO obj :list) {
			List<CustomerFeedbackVO> cList;
			cList = getCustomerFeedbacks(obj.getResourceId());
			for (CustomerFeedbackVO tvo: cList) {
				CustomerFeedback p = new CustomerFeedback(tvo);
				obj.addReview(p);
			}
			obj.setReviewCount(getReviewsCount(obj.getResourceId()));
		}
	}

	private Integer getReviewsCount(Integer providerId) {
		final String key = "/API/reviewCount/providerId/" + providerId;
		Integer count = (Integer)SimpleCache.getInstance().get(key);
		try {
			if (count == null) {
				count  =  backendBO.getCustomerFeedbacksCount(providerId);
				SimpleCache.getInstance().put(key, count, SimpleCache.TEN_MINUTES);
			}
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return count;
	}


	private List<CustomerFeedbackVO> getCustomerFeedbacks(int resourceId) {
		final String key = "/ProviderSearch/CustomerFeedback/resourceId/";
		try {
			List<CustomerFeedbackVO>  cList =
				(List<CustomerFeedbackVO>)SimpleCache.getInstance().get(key + resourceId);
			if (cList == null) {
				cList =
					backendBO.getCustomerFeedbacks(resourceId, CUSTOMER_REVIEWS_COUNT);
				SimpleCache.getInstance().put(key + resourceId, cList, CACHE_TIMEOUT);
			}
			return cList;
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		return new ArrayList<CustomerFeedbackVO>();
	}

	private void addSkills(List<ProviderSearchResponseVO> list) {
		Map<Integer, Category> catMap;
		for (ProviderSearchResponseVO obj :list) {
			catMap = getCategories(obj.getResourceId());
			for (Integer key:catMap.keySet()) {
				obj.addCategorie(catMap.get(key));
			}
		}

	}

	private Map<Integer, Category> getCategories(int resourceId) {
		final String key = "/ProviderSearch/SkillCategory/resourceId/";
		Map<Integer, Category> catMap = (Map)SimpleCache.getInstance().get(key + resourceId);

		if (catMap == null) {
			catMap = new HashMap<Integer, Category>();
			Map<Integer, Skill> skillMap = new HashMap<Integer, Skill>();
			Map<String, List> map = backendBO.getCheckedSkillsTree(resourceId);
			List<SkillNodeVO> catList = (List<SkillNodeVO>)map.get("cat");
			List<CheckedSkillsVO> skillsList = (List<CheckedSkillsVO>) map.get("skills");
			for (SkillNodeVO nVO : catList) {
				Category c = new Category(nVO.getNodeName());
				c.setRootId(nVO.getNodeId());
				catMap.put(nVO.getNodeId(), c);
			}

			for (CheckedSkillsVO nVO : skillsList) {
				if (nVO.getLevel() != 1) { // skip root node
					Skill c  = skillMap.get(nVO.getNodeId());
					if (c == null) {
						c = new Skill(nVO);
						skillMap.put(nVO.getNodeId(), c);
						Category parent = catMap.get(nVO.getRootNodeId());
						/**
						 * SL-10426 fix. Looks like provider has selected one of the skills but has
						 * not selected root category. This skill will be skipped from user profile.
						 * MFE should prevent user from making such choices.
						 */
						if (parent != null)
						  parent.addSkill(c);
					} else {
						c.addServiceType(nVO.getServiceType());
					}
				}
			}

			SimpleCache.getInstance().put(key + resourceId, catMap, CACHE_TIMEOUT);
		}
		return catMap;
	}

	public IProviderSearchBO getBackendBO() {
		return backendBO;
	}

	public void setBackendBO(IProviderSearchBO backendBO) {
		this.backendBO = backendBO;
	}

	public void setSolrServerURL(String solrServerURL) {
		this.solrServerURL = solrServerURL;
	}


	public String getProxyHost() {
		return proxyHost;
	}


	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}


	public Integer getProxyPort() {
		return proxyPort;
	}


	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}


	public String getYahooServiceappId() {
		return yahooServiceappId;
	}


	public void setYahooServiceappId(String yahooServiceappId) {
		this.yahooServiceappId = yahooServiceappId;
	}


	public boolean isYahooSpellCheck() {
		return isYahooSpellCheck;
	}


	public void setYahooSpellCheck(boolean isYahooSpellCheck) {
		this.isYahooSpellCheck = isYahooSpellCheck;
	}


	public Long getYahooServiceTimeOut() {
		return yahooServiceTimeOut;
	}


	public void setYahooServiceTimeOut(Long yahooServiceTimeOut) {
		this.yahooServiceTimeOut = yahooServiceTimeOut;
	}

	public String getYahooServiceURL() {
		return yahooServiceURL;
	}

	public void setYahooServiceURL(String yahooServiceURL) {
		this.yahooServiceURL = yahooServiceURL;
	}
	public boolean isProxySet() {
		return proxySet;
	}

	public void setProxySet(boolean proxySet) {
		this.proxySet = proxySet;
	}
	
	/* R 16_2_0_1: SL-21376:
	 * Fetching categoryIds for the skuList
	 */
	public List<Integer> getSkuCategoryIds(String buyerId, List<String> skuList) throws BusinessServiceException {
		try{
			return backendBO.getSkuCategoryIds(buyerId, skuList);
		}
		catch (BusinessServiceException exception){
			throw new BusinessServiceException (exception);
		}	
	}
	
	
	/** R 16_2_0_1: SL-21376:
	 * Fetching providers based on search request
	 * @param ProviderListRequest
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	public ProviderListVO getProviders(ProviderSearchRequestVO ProviderListRequest){
		ProviderSearchRequestVO requestProvider = (ProviderSearchRequestVO)ProviderListRequest;	
		requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_ZIP);

		List<ProviderSearchResponseVO> voProviderList;
		SearchQueryResponse res = getProviderSearchBO().queryforProviders(requestProvider);
		if (res == null)
			return null;
		voProviderList = (List)res.getBaseResponseVO();
		
		int totalCount = (Integer)res.getProperty(ISearchProvider.PROPERTY_TOTAL_PROVIDER_FOUND);
		//filter the categories 
		//Since solr does a LIKE search additional filtering needs to be done to ensure that providers fetched has the categories in the request
		if (null!= requestProvider.getCategoryIds() && !(requestProvider.getCategoryIds().isEmpty())){
			voProviderList = getProviderSearchBO().filterBasedOnSkills(voProviderList,requestProvider);
			//update total count if  filtering is done
			totalCount = voProviderList.size();
		}
		
		if (backendBO != null) {
			switch (ProviderListRequest.getResultMode()) {
			case MINIMUN:
				break;
			case ALL:
				addSkills(voProviderList);
				// no break;
			case LARGE:
				addLicenses(voProviderList);
				addReviews(voProviderList);
				// No break;
			case MEDIUM:
				break;
			}
		}
		
		ProviderListVO vo  =  new ProviderListVO(voProviderList, totalCount);

		return vo;
	}

	/* R 16_2_0_1: SL-21376:
	 * Fetching review count for the firm
	 */
	public Map <Integer,Long> getReviewCount(List<String> vendorIdList)
			throws BusinessServiceException {
		try{
			return backendBO.getReviewCount(vendorIdList);
		}
		catch (BusinessServiceException exception){
			throw new BusinessServiceException (exception);
		}	
		
	}
	
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> firmIds) throws BusinessServiceException {
		try{
			return backendBO.getCompanyLogo(firmIds);
		}
		catch (BusinessServiceException exception){
			throw new BusinessServiceException (exception);
		}	
		
	}
	
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws BusinessServiceException{
		try{
			return backendBO.getCompanyLogoValues();
		}
		catch (BusinessServiceException exception){
			throw new BusinessServiceException (exception);
		}	
		
	}


	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId,
			List<Integer> levelThreeCategories,String keyword) throws BusinessServiceException {
		return backendBO.getSkusForCategoryIds(buyerId, levelThreeCategories,keyword);
	}
	
	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws BusinessServiceException{

			return backendBO.getParentNodeIds(nodeIds); 

		}
	
	public Map<Long,String> getSkillTypList() throws BusinessServiceException{

			return backendBO.getSkillTypList();
		}

	public Map<Long,String> getNodeNames(List<String> nodeIds) throws BusinessServiceException{

			return backendBO.getNodeNames(nodeIds); 
		}


	public List<String> getMainCategoryNames(List categoryIdList)
			throws BusinessServiceException {
		return backendBO.getMainCategoryNames(categoryIdList); 
	}
	
	public Map<Integer,BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds) throws BusinessServiceException{
		
		return backendBO.getBasicFirmDetails(firmIds); 

	}

	public Map<String,List<FirmServiceVO>> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws BusinessServiceException {
		return backendBO.getVendorServiceDetails(firmIdList,categoryIdList);   
	}
	

	
	
	

}
