package com.newco.marketplace.searchInterface;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.search.vo.ProviderListVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.newco.marketplace.search.vo.SkillRequestVO;
import com.newco.marketplace.search.vo.SkillTreeResponseVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;

public interface ISearchProvider {
	public static final String PROPERTY_TOTAL_PROVIDER_FOUND = "TOTAL_PROVIDER_FOUND";
	public static final String PROPERTY_FACETS = "PROPERTY_FACETS";

	public List<ProviderSearchResponseVO> getProvidersById(ProviderSearchRequestVO providerId);
	public ProviderListVO getProviderList(ProviderSearchRequestVO providerList);
	public List<SkillTreeResponseVO> getSkillTree(SkillRequestVO skillTree);
	public int getProvidersCount(String state, String city, List<Integer> categoryIds);
	public Map<String, Long> getCities(String state, List<Integer> categoryIds);
	public Map<Integer, Integer> getProvidersCountBySkills(String state, String city);
	//R 16_2_0_1: SL-21376: for finding categoryIds for the SKU
	public List<Integer> getSkuCategoryIds(String buyerId, List<String> skuList) throws BusinessServiceException;
	//R 16_2_0_1: SL-21376: for finding providers based on search parameters
	public ProviderListVO getProviders(ProviderSearchRequestVO providerSearchRequestVO);
	//R 16_2_0_1: SL-21376: for finding review count for provider firms
	public Map <Integer,Long> getReviewCount(List<String> vendorIdList) throws BusinessServiceException;
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> firmIds) throws BusinessServiceException;
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws BusinessServiceException;
	//SL-21467
	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId, List<Integer> levelThreeCategories, String keyword) throws BusinessServiceException;
	
	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws BusinessServiceException;

	public Map<Long,String> getSkillTypList() throws BusinessServiceException;
	
	public Map<Long,String> getNodeNames(List<String> nodeIds) throws BusinessServiceException;
	
	public List<String> getMainCategoryNames(List<Integer> categoryIdList)  throws BusinessServiceException;

	public Map<Integer,BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds) throws BusinessServiceException;
	
	public Map<String,List<FirmServiceVO>> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws BusinessServiceException;
	

 

	
}
