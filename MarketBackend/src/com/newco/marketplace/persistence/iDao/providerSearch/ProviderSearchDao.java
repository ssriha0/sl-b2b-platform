package com.newco.marketplace.persistence.iDao.providerSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;


/**
 * @author zizrale
 *
 */
public interface ProviderSearchDao {

	/**
	 * @param skillNodes
	 * @return List<ProviderSkillResultsVO>
	 */
	public List<ProviderSkillResultsVO> getProvidersBySkills(SkillNodeIdsVO skillNodes);
	
	public List<ProviderSkillResultsVO> getProvidersBySkillsAndFirmId(SkillNodeIdsVO skillNodes, ProviderSearchCriteriaVO searchCriteria);
	
	public List<ProviderResultVO> getProvidersByZipLoc(SkillNodeIdsVO skillNodes);
	 
	/**
	 * @param starBean
	 * @return List<ProviderSkillResultsVO>
	 */
	public List<ProviderStarResultsVO> getProviderStarRating(ProviderIdsVO providerIds);
	
	public List<ProviderCredentialResultsVO> getProviderCredentials(ProviderIdsVO providerIds);

	public List<ProviderLanguageResultsVO> getProviderLanguages(ProviderIdsVO providerIds);
	
	public List<ProviderSPNetStateResultsVO> getProviderSPNetStates(ProviderIdsVO providerIds);
	
	public List<ProviderLanguageResultsVO> getProviderLanguagesForOneResource(Integer providerId);
	
	public List<ProviderLocationResultsVO> getProviderLocation(ProviderIdsVO providerIds);
	
	public List<ProviderSkillResultsVO> getAllProvidersInGivenTree(SkillNodeIdsVO skillNodes);
	
	public List<ProviderBackgroundCheckResultsVO> getProviderBackgroundStatus(ProviderIdsVO providerIds);
	
	public List<VendorHdrVO> getVendorHdr(ProviderIdsVO providerIds);
	
	public ArrayList<ProviderResultVO> getSelectedProviders(String serviceOrderID);
	
	public ArrayList<ProviderResultVO> getSelectedProvidersForTier(String serviceOrderID);
	
	public LocationVO getZipLatAndLong(String zip);
	
	public List<ProviderResultForAdminVO> getMatchingProvidersForAdminSearch(ProviderResultForAdminVO providerCriteria) throws DataServiceException;
	
	public List<SearchResultForAdminVO> getMatchingForAdminSearch(SearchResultForAdminVO searchCriteria) throws DataServiceException;
	
	/**
	 * @param providerCriteria
	 * @return
	 * @throws DataServiceException
	 */
	public List<ProviderResultForAdminVO> getMatchingProvidersBySkillForAdminSearch(ProviderResultForAdminVO providerCriteria) throws DataServiceException;
	
	/**
	 * Returns a ProviderResultForAdminVO containing the Provider Admin resource Id and name
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public ProviderResultForAdminVO getProviderAdmin(Integer vendorId) throws DataServiceException;
	
	public List<Integer> getProvidersForServiceSkillType( ProviderSkillRequestVO skillTypeRequest )throws DataServiceException;
	
	/**
	 * @param spnId
	 * @param marketId
	 * @param mainCategory
	 * @param skillNodes
	 * @param skillTypes
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNProviderSearchVO> getNewServiceProsBySkillForSPN (Integer spnId, Integer marketId, Integer mainCategory, 
			List<Integer> skillNodes, List<Integer> skillTypes) throws DataServiceException;
	
	public List<ProviderResultVO> getLockedProviderDetails(SkillNodeIdsVO skillNodes);


	/**
	 * @param skillNodes
	 * @return List<ProviderSkillResultsVO>
	 * get the providers by skills for new SPN
	 */
	public List<ProviderSkillResultsVO> getProvidersBySkillsForNewSpn(
			SkillNodeIdsVO skillNodeIdsVO);
		
	public List<LookupVO> fetchFirmPerfScores(Integer spnId, List<Integer> vendorIds);
	public SPNetHeaderVO fetchSpnDetails(Integer spnId);
	public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers);
	public List<TierRoutedProvider> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores);
	public List<AdditionalInsuranceVO> getAdditionalInsurance(ProviderIdsVO providerIds);
	public List<Integer> getResourcesBasedOnProviderResponse(String soId, List<Integer> resourceIds) throws DataServiceException;
	
	//SL-20760
	//Code added to fetch constant value from application_constants table.
	public String getApplicationConstantValueFromDB(String appkey) throws DataServiceException;
	
	/* R 16_2_0_1: SL-21376:
	 * Fetching categoryIds for the skuList
	 */
	public List<Integer> getSkuCategoryIds(String buyerId, List<String> skuList) throws DataServiceException;

	/* R 16_2_0_1: SL-21376:
	 * Fetching review count for the firms
	 */
	public Map<Integer, Long> getOverallReviewCount(List<String> vendorIdList) throws DataServiceException;
	
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> vendorIdList) throws DataServiceException;
	
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws DataServiceException;

	//SL-21467
	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId,List<Integer> levelThreeCategories,String keyword) throws DataServiceException;
	
	
	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws DataServiceException;
	
	public Map<Long,String> getSkillTypList() throws DataServiceException;
	
	public Map<Long,String> getNodeNames(List<String> nodeIds) throws DataServiceException;


	public List<String> getMainCategoryNames(List<Integer> nodeIds) throws DataServiceException;


	public List<BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds)throws DataServiceException;
	
	public List<FirmServiceVO> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws DataServiceException;
	
	public List<String> getAllProvidersForExternalCalenderSync() throws DataServiceException;
	public List<String> getApplicableProvidersForTechTalk(Integer spnId)throws DataServiceException;
	public List<String> getValidProvidersForTechTalk(Integer spnId)throws DataServiceException;

}
