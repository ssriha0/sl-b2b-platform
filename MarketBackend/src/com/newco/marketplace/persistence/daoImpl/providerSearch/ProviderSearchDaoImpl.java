package com.newco.marketplace.persistence.daoImpl.providerSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.dto.vo.provider.AdditionalInsuranceVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.provider.VendorHdrVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderBackgroundCheckResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderCredentialResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLanguageResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLocationResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSPNetStateResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillRequestVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderStarResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.SPNProviderSearchVO;
import com.newco.marketplace.dto.vo.providerSearch.SearchResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.TierRoutedProvider;
import com.newco.marketplace.dto.vo.skillTree.ProviderIdsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;


/**
 * @author zizrale
 *
 * $Revision: 1.23 $ $Author: glacy $ $Date: 2008/05/29 02:37:13 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class ProviderSearchDaoImpl extends ABaseImplDao implements ProviderSearchDao, OrderConstants {
  
	private static final Logger logger = Logger.getLogger(ProviderSearchDaoImpl.class);
	private static final String SORT_COLUMN_KEY = "sort_column_key";
	private static final String SORT_ORDER_KEY = "sort_order_key";
	
    public List<ProviderSkillResultsVO> getProvidersBySkills(SkillNodeIdsVO skillNodes){
    	logger.debug("Entered getProvidersBySkills");
    	return queryForList("getProviders.query", skillNodes);
    }
    
    @Override
	public List<ProviderSkillResultsVO> getProvidersBySkillsAndFirmId(SkillNodeIdsVO skillNodes,
			ProviderSearchCriteriaVO searchCriteria) {
    	logger.debug("Entered getProvidersBySkillsAndFirmId");
    	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("skillNodeIds", skillNodes.getSkillNodeIds());
		params.put("firmIds", searchCriteria.getFirmId());
		params.put("distanceFilter", skillNodes.getDistanceFilter());
		params.put("zipLatitude", skillNodes.getZipLatitude());
		params.put("zipLongitude", skillNodes.getZipLongitude());
		//SLT-2545 Zip code based order routing changes START
		params.put("zipCode", skillNodes.getZipCode());
		//SLT-2545 Zip code based order routing changes END

    	return queryForList("getProvidersByFirm.query", params);
	}
    
    public List<ProviderSkillResultsVO> getProvidersBySkillsForNewSpn(
			SkillNodeIdsVO skillNodeIdsVO){
    	logger.debug("Entered getProvidersBySkills for new SPN");
    	return queryForList("getProvidersForNewSpn.query", skillNodeIdsVO);
    }
 
    public List<ProviderResultVO> getProvidersByZipLoc(SkillNodeIdsVO skillNodes){
    	logger.debug("Entered getProvidersByZip");
    	return queryForList("getProvidersZip.query", skillNodes);
    }
 
    public List<ProviderStarResultsVO> getProviderStarRating(ProviderIdsVO providerIds){
    	logger.debug("Entered getProviderStarRating");
    	return queryForList("getProviderStarRating.query", providerIds);
    }

	public List<ProviderCredentialResultsVO> getProviderCredentials(ProviderIdsVO providerIds){
		logger.debug("Entered getProviderCredentials");
    	return queryForList("getProviderCredentials.query", providerIds);
	}

	public List<ProviderLanguageResultsVO> getProviderLanguages(ProviderIdsVO providerIds){
		logger.debug("Entered getProviderLanguages");
    	return queryForList("getProviderLanguages.query", providerIds);
	}
	
	public List<ProviderLanguageResultsVO> getProviderLanguagesForOneResource(Integer providerId){
    	return queryForList("getProviderLanguagesForOneResource.query", providerId);
	}
	
	public List<ProviderLocationResultsVO> getProviderLocation(ProviderIdsVO providerIds){
		logger.debug("Entered getProviderLocation");
    	return queryForList("getProviderLocation.query", providerIds);
	}
	
	public List<ProviderSkillResultsVO> getAllProvidersInGivenTree(SkillNodeIdsVO skillNodes){
		return queryForList("getAllProvidersInTree.query", skillNodes);
	}

	public List<ProviderResultVO> getLockedProviderDetails(SkillNodeIdsVO skillNodes) {
		return queryForList("getLockedProviderDetails.query",skillNodes);
	}
	
	public List<ProviderBackgroundCheckResultsVO> getProviderBackgroundStatus(ProviderIdsVO providerIds) {
		logger.debug("Entered ProviderSearchDaoImpl ---> getProviderBackgroundStatus() ");
		
		List<ProviderBackgroundCheckResultsVO> backgroundCheckResultsVO = null;
		backgroundCheckResultsVO = queryForList("getProviderBackgroundStatus.query", providerIds);
		
		logger.debug("Leaving ProviderSearchDaoImpl ---> getProviderBackgroundStatus() ");
		return backgroundCheckResultsVO;
	}
	public ProviderResultForAdminVO getProviderAdmin(Integer vendorId)
		throws DataServiceException {
	return (ProviderResultForAdminVO)queryForObject("workplaceProviderSearchGetProviderAdmin.query", vendorId);
	}
	public List<VendorHdrVO> getVendorHdr(ProviderIdsVO providerIds) {
		return queryForList("vendor_hdr.query", providerIds);
	}

	public ArrayList<ProviderResultVO> getSelectedProviders(String serviceOrderID) {
		return (ArrayList)queryForList("getSelectedProviders.query", serviceOrderID);
	}
	
	public ArrayList<ProviderResultVO> getSelectedProvidersForTier(String serviceOrderID) {
		return (ArrayList)queryForList("getSelectedProvidersForTier.query", serviceOrderID);
	}
	
	public LocationVO getZipLatAndLong(String zip){
		return (LocationVO)queryForObject("zip.queryLatLong", zip);
	}
	
	public List<SPNProviderSearchVO> getNewServiceProsBySkillForSPN (Integer spnId, Integer marketId, Integer mainCategory,
			List<Integer> skillNodes, List<Integer> skillTypes) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("spnId", spnId);
		params.put("marketId", marketId);
		params.put("skillNodeIds",skillNodes);
		params.put("skillTypeIds",skillTypes);
		params.put("mainCategory",mainCategory);
		
		return queryForList("skillAssign.getNewServiceProsBySkillForSPN",params);
	}

	
	/**
	 * ensureSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order 
	 * @param sortColumn
	 * @param sortOrder
	 * @return map 
	 */
	protected Map<String, String> ensureSortProviderSearch (String sortColumn, String sortOrder) {
		Map<String, String> sort = new HashMap<String, String>();
		boolean sortOrderSet = false;

		if( StringUtils.isNotEmpty(sortColumn) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ) {
			if("MsId".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_MEMBERID);
			}else if("MsCompanyType".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_COMPANYTYPE);
			}else if("MsStatus".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_STATUS);
			}else if("MsPrimIndustry".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_PRIMARYINDUSTRY);
			}else if("MsMarket".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_MARKET);
			}else if("MsLastActivityTime".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_LASTACTIVITY);
			}else if("MsState".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_STATE);
			}else if("MsZip".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_ZIP);
			}else {
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_MEMBERID);
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
				sortOrderSet = true;
			}
		} else {
			sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_MEMBERID);
			sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			sortOrderSet = true;
		}
		
		if (!sortOrderSet) {
			if( StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ){
				sort.put(SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ProviderResultForAdminVO> getMatchingProvidersForAdminSearch(ProviderResultForAdminVO providerCriteria)
				throws DataServiceException{
		Map<String, String> sort = ensureSortProviderSearch(providerCriteria.getSortColumnName(), providerCriteria.getSortOrder());
		providerCriteria.setSortColumnName(sort.get(SORT_COLUMN_KEY));
		providerCriteria.setSortOrder(sort.get(SORT_ORDER_KEY));
		return queryForList("providerSearchForAdmin.query", providerCriteria);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao#getMatchingProvidersBySkillForAdminSearch(com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO)
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderResultForAdminVO> getMatchingProvidersBySkillForAdminSearch(ProviderResultForAdminVO providerCriteria)
		throws DataServiceException{
		Map<String, String> sort = ensureSortProviderSearch(providerCriteria.getSortColumnName(), providerCriteria.getSortOrder());
		providerCriteria.setSortColumnName(sort.get(SORT_COLUMN_KEY));
		providerCriteria.setSortOrder(sort.get(SORT_ORDER_KEY));
		return queryForList("workplaceProviderSearchBySkill.query", providerCriteria);
	}
	

	/**
	 * ensureSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order 
	 * @param sortColumn
	 * @param sortOrder
	 * @return map
	 */
	protected Map<String, String> ensureSortForAdminSearch (String sortColumn, String sortOrder) {
		Map<String, String> sort = new HashMap<String, String>();
		boolean sortOrderSet = false;

		if( StringUtils.isNotEmpty(sortColumn) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ) {
			if("AsId".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_ID);
			}else if("AsBusName".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_BUSNAME);
			}else if("AsUserName".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_USERNAME);
			}else if("AsName".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_NAME);
			}else if("AsPhone".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_PHONE);
			}else {
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_ID);
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
				sortOrderSet = true;
			}
		} else {
			sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_ID);
			sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			sortOrderSet = true;
		}
		
		if (!sortOrderSet) {
			if( StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ){
				sort.put(SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}

	
	@SuppressWarnings("unchecked")
	public List<SearchResultForAdminVO> getMatchingForAdminSearch(SearchResultForAdminVO searchCriteria)
				throws DataServiceException{
		Map<String, String> sort = ensureSortForAdminSearch(searchCriteria.getSortColumnName(), searchCriteria.getSortOrder());
		searchCriteria.setSortColumnName(sort.get(SORT_COLUMN_KEY));
		searchCriteria.setSortOrder(sort.get(SORT_ORDER_KEY));
		return queryForList("searchForAdmin.query", searchCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getProvidersForServiceSkillType(ProviderSkillRequestVO myProviderSkillRequestVO) throws DataServiceException {		
		if(myProviderSkillRequestVO != null){			
			return queryForList("getAllProvidersForGivenSkillType.query", myProviderSkillRequestVO);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProviderSPNetStateResultsVO> getProviderSPNetStates(
			ProviderIdsVO providerIds) {
		List<ProviderSPNetStateResultsVO> providerSPNetStates = new ArrayList<ProviderSPNetStateResultsVO>(); 
		logger.debug("Entered getProviderSPNetStates");
		providerSPNetStates =  queryForList("getProviderSPNetStates.query", providerIds);
		return providerSPNetStates;
	}
	
	//SL-18226
	@SuppressWarnings("unchecked")
	public List<LookupVO> fetchFirmPerfScores(Integer spnId, List<Integer> vendorIds){
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("spn", spnId);
		params.put("vendorIds", vendorIds);
		List<LookupVO> perfScores = null;
		perfScores =  queryForList("getProviderFirmPerfScore.query", params);
		return perfScores;
	}
	
	public SPNetHeaderVO fetchSpnDetails(Integer spnId){
		return (SPNetHeaderVO)queryForObject("buyer.fetchPerfCriteriaLevel.query", spnId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers){
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("noOfProvInCurentTier", noOfProvInCurentTier);
		params.put("noOfProvInPreviousTiers", noOfProvInPreviousTiers);
		List<Integer> ranks = new ArrayList<Integer>();
		ranks =  queryForList("findRanks.query", params);
		return ranks;
	}
	
	@SuppressWarnings("unchecked")
	public List<TierRoutedProvider> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("noOfProvInCurentTier", noOfProvInCurentTier);
		params.put("noOfProvInPreviousTiers", noOfProvInPreviousTiers);
		params.put("ranks", perfScores);
		List<TierRoutedProvider> provList = new ArrayList<TierRoutedProvider>();
		if(null!=perfScores && perfScores.size()>0){
			provList =  queryForList("fetchProvidersForFirmLevel.query", params);
		}else{
			provList =  queryForList("fetchProvidersForProviderLevel.query", params);
		}
		return provList;
	}
	
	
	/* 
	 * SL-10809 Addtional Insurance
	 * (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao#getAdditionalInsurance(com.newco.marketplace.dto.vo.skillTree.ProviderIdsVO)
	 */
	@SuppressWarnings("unchecked")
	public List<AdditionalInsuranceVO> getAdditionalInsurance(ProviderIdsVO providerIds) {
		return queryForList("additional_ins.query", providerIds);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getResourcesBasedOnProviderResponse(String soId, List<Integer> resourceIds) throws DataServiceException {
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("soId", soId);
		params.put("providerIds", resourceIds);
		if(resourceIds != null){			
			return queryForList("getResourcesBasedOnProviderResponse.query", params);
		}
		return null;
	}
	
	//SL-20760
	//Code added to fetch constant value from application_constants table.
	public String getApplicationConstantValueFromDB(String appkey) throws DataServiceException {
		String value = null;
		try{
			value=(String) queryForObject("getApplicationConstantValue.query",appkey);
		}catch(Exception e){
			logger.error("Exception occured in getApplicationConstantValueFromDB() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return value;
	}
	
	/* R 16_2_0_1: SL-21376:
	 * Fetching categoryIds for the skuList
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getSkuCategoryIds(String buyerId, List<String> skuList)
			throws DataServiceException {

		List<Integer> skuCategoryIdList =  new ArrayList<Integer>();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("buyerId", buyerId);
		params.put("skus", skuList);
		try{
			skuCategoryIdList =  (List<Integer>)queryForList("getSKUCategoryIds.query",params);
		}
		catch (Exception exception) {
			throw new DataServiceException("Exception occured in getSkuCategoryIds() due to", exception);
		}
		return skuCategoryIdList;
	}
	
	/* R 16_2_0_2: SL-21457:
	 * Fetching skus for the list of categoryIds
	 */
	@SuppressWarnings("unchecked")
	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId, List<Integer> levelThreeCategories,String keyword)
			throws DataServiceException {

		List<SkuDetailsVO> skuCategoryIdList =  new ArrayList<SkuDetailsVO>();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("buyerId", buyerId);
		params.put("cats", levelThreeCategories);
		params.put("keyword", keyword);
		try{
			skuCategoryIdList =  (List<SkuDetailsVO>)queryForList("getSKUsForCategoryIds.query",params);
		}
		catch (Exception exception) {
			throw new DataServiceException("Exception occured in getSkusForCategoryIds() due to", exception);
		}
		return skuCategoryIdList;
	}
	
	/* R 16_2_0_1: SL-21376:
	 * Fetching review count for the firms
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Long> getOverallReviewCount(List<String> vendorIdList) throws DataServiceException {
		Map<Integer, Integer> reviewCount = null;
		Map<Integer, Long> reviewCountRes = new HashMap<Integer, Long>();
		try{
			reviewCount = (Map<Integer, Integer>)getSqlMapClient().queryForMap(
					"getFirmReviewCount.query", vendorIdList,
					"vendor_id", "reviewCount");
			if(!reviewCount.isEmpty()){
			 for (Map.Entry<Integer, Integer> entry : reviewCount.entrySet())  
				 reviewCountRes.put(entry.getKey(), new Long(entry.getValue()));
			}

		} 
		catch(Exception e){
			throw new DataServiceException("Exception in getOverallReviewCount() due to "	+ e.getMessage(), e);
		}
		return reviewCountRes;
	}
	
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> firmIds)
			throws DataServiceException {
		Map<Long, String> companyLogo = null;
		try{
			companyLogo = (Map<Long, String>)getSqlMapClient().queryForMap(
					"getCompanyLogo.query", firmIds,
					"vendor_id", "companyLogo"); 

		} 
		catch(Exception e){
			throw new DataServiceException("Exception in ProviderSearchDaoImpl.getCompanyLogo.query() due to "	+ e.getMessage(), e);
		}
		return companyLogo;
	}
	
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws DataServiceException{
		Map <String, String> companyLogoValues = new HashMap <String, String>();
		try{ 
			
			companyLogoValues = (HashMap<String, String>)getSqlMapClient().queryForMap("getCompanyLogoValues.query",null,"app_key","app_value");
		
		}
		catch(Exception e){
			logger.info("Exception occurred in getCompanyLogoValues"+e);
			throw new DataServiceException("Exception occurred in getCompanyLogoValues :"+e.getMessage(),e);
		} 
		return companyLogoValues;  
	}
	
	
	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws DataServiceException{
		Map <Long, Long> parentNodeIdMap = new HashMap <Long, Long>();
		try{ 
			
			parentNodeIdMap = (HashMap<Long, Long>)getSqlMapClient().queryForMap("getParentNodeIds.query",nodeIds,"node_id","parent_node");
		
		}
		catch(Exception e){
			logger.info("Exception occurred in getParentNodeIds"+e);
			throw new DataServiceException("Exception occurred in getParentNodeIds :"+e.getMessage(),e);
		} 
		return parentNodeIdMap;  				
	}
	
	
	public Map<Long,String> getNodeNames(List<String> nodeIds) throws DataServiceException{
		Map <Long, String> parentNodeIdMap = new HashMap <Long, String>();
		try{ 
			
			parentNodeIdMap = (HashMap<Long, String>)getSqlMapClient().queryForMap("getNodeNames.query",nodeIds,"node_id","node_name");
		
		}
		catch(Exception e){
			logger.info("Exception occurred in getNodeNames"+e);
			throw new DataServiceException("Exception occurred in getNodeNames :"+e.getMessage(),e);
		} 
		return parentNodeIdMap;  				
	}
	
	public List<String> getMainCategoryNames(List<Integer> nodeIds) throws DataServiceException{
		
		List<String> categoryNamesList =  new ArrayList<String>();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("nodeIds", nodeIds);

		try{
			categoryNamesList =  (List<String>)queryForList("getMainCategoryNames.query",params);
		}
		catch(Exception e){
			logger.error("Exception occurred in getMainCategoryNames"+e);
			throw new DataServiceException("Exception occurred in getMainCategoryNames :"+e.getMessage(),e);
		} 
		return categoryNamesList;  				
	}	
	
	public Map<Long,String> getSkillTypList() throws DataServiceException{
		Map <Long, String> skillTypeMap = new HashMap <Long, String>();
		try{ 			
			skillTypeMap = (HashMap<Long, String>)getSqlMapClient().queryForMap("getSkillTypList.query",null,"nodeId","skillTypes");
		
		}
		catch(Exception e){
			logger.info("Exception occurred in getSkillTypList"+e);
			throw new DataServiceException("Exception occurred in getSkillTypList :"+e.getMessage(),e);
		} 
		return skillTypeMap;  				
	}

	public List<FirmServiceVO> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws DataServiceException{

		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("categoryIdList", categoryIdList);
		params.put("firmIdList", firmIdList);

		List serviceList = null;
		try {		
			serviceList =  getSqlMapClient().queryForList("getServiceDetailsofFirm.query", params);   

		} catch (Exception e) {
			throw new DataServiceException("Exception in getVendorServiceDetails() due to "	+ e.getMessage(), e);
		}
		return serviceList;
	}

	public List<BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds)throws DataServiceException {
		List<BasicFirmDetailsVO> basicDetailsList = null;
		try{
			basicDetailsList = getSqlMapClient().queryForList("getBasicDetailsofFirm.query", firmIds);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getBasicFirmDetails() of ServiceOrderDaoImpl", e);
		}
		return basicDetailsList;
	}

	public List<String> getAllProvidersForExternalCalenderSync() throws DataServiceException {
		List<String> AllProvidersForExternalCalenderSyncList = null;
		try{
			AllProvidersForExternalCalenderSyncList = getSqlMapClient().queryForList("AllProvidersForExternalCalenderSyncList.query", null);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getAllProvidersForExternalCalenderSync() of ProviderSearchDaoImpl", e);
		}
		return AllProvidersForExternalCalenderSyncList;
	}	
	
	public List<String> getApplicableProvidersForTechTalk(Integer spnId)throws DataServiceException {
		List<String> applicableProviders = null;
		try{
			applicableProviders = getSqlMapClient().queryForList("applicableProvidersForTechTalk.query", spnId);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getApplicableProvidersForTechTalk() of ProviderSearchDaoImpl", e);
		}
		return applicableProviders;
	}	
	
	public List<String> getValidProvidersForTechTalk(Integer spnId)throws DataServiceException {
		List<String> applicableProviders = null;
		try{
			applicableProviders = getSqlMapClient().queryForList("validProvidersForTechTalk.query", spnId);
		}
		catch(Exception e){
			throw new DataServiceException("Exception in getValidProvidersForTechTalk() of ProviderSearchDaoImpl", e);
		}
		return applicableProviders;
	}	
	
}
