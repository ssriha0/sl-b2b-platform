package com.newco.marketplace.business.iBusiness.providersearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.SearchResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.TierRoutedProvider;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;

public interface IProviderSearchBO {
	
	public ArrayList<ProviderResultVO> getProviderList(ProviderSearchCriteriaVO searchCriteria);
	public List<ProviderResultForAdminVO> getProviderListForAdmin(ProviderResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException;
	public List<ProviderResultForAdminVO> getProviderListBySkillForAdmin(ProviderResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException;
	public List<SearchResultForAdminVO> getSearchListForAdmin(SearchResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException;

	// Required for Solr search	 
	public List <CustomerFeedbackVO> getCustomerFeedbacks(int resourceId, int count) throws BusinessServiceException;
	public List<TeamCredentialsVO> getResourceCredentials(int resourceId);
	public List<com.newco.marketplace.vo.provider.VendorCredentialsVO> getVendorCredentials(int vendorId);
	public Map<String,List> getCheckedSkillsTree(int resourceId);
	
	/**
	 * @param vendorId
	 * @return
	 */
	public ProviderResultForAdminVO getProviderAdmin (Integer vendorId) throws BusinessServiceException;
	public Integer getCustomerFeedbacksCount(int resourceId) throws com.newco.marketplace.exception.BusinessServiceException;
	public VendorResource getVendorFromResourceId(int resourceId) throws BusinessServiceException;
	public List<LookupVO> fetchFirmPerfScores(Integer spnId, List<Integer> vendorIds);
	public SPNetHeaderVO fetchSpnDetails(Integer spnId);
	public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers);
	public List<TierRoutedProvider> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores);
	
	/**
	 * @param resourceIds
	 * @return List<VendorResource>
	 * @throws BusinessServiceException
	 */
	public List<VendorResource> getVendorResourceList(List<Integer> resourceIds) throws BusinessServiceException;
	/**
	 * @param firmIds
	 * @return List<Integers>
	 * @throws BusinessServiceException
	 */
	public List<Integer> getValidVendorList(List<Integer> firmIds)
			throws BusinessServiceException; 

	public List<Integer> getResourcesBasedOnProviderResponse(String soId,
			List<Integer> resourceIds) throws BusinessServiceException;
			
	/* R 16_2_0_1: SL-21376:
	 * Fetching categoryIds for the skuList
	 */
	public List<Integer> getSkuCategoryIds(String buyerId,List<String> skuIdList) throws BusinessServiceException;
	/* R 16_2_0_1: SL-21376:
	 * Fetching review count for the firm
	 */
	public Map <Integer,Long> getReviewCount(List<String> vendorIdList) throws BusinessServiceException;
	
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> vendorIdList) throws BusinessServiceException;
	
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws BusinessServiceException;
	//SL-21467
	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId,List<Integer> levelThreeCategories,String keyword) throws BusinessServiceException;
	
	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws BusinessServiceException;

	public Map<Long,String> getSkillTypList() throws BusinessServiceException;
	
	public Map<Long,String> getNodeNames(List<String> nodeIds) throws BusinessServiceException;
	
	public List<String> getMainCategoryNames(List<Integer> nodeIds) throws BusinessServiceException;

	public Map<Integer,BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds) throws BusinessServiceException;
	
	public Map<String,List<FirmServiceVO>> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws BusinessServiceException;
	


	
}

