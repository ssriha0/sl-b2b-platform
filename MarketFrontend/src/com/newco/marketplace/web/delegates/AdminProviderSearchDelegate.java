package com.newco.marketplace.web.delegates;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.web.dto.AdminSearchFormDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsAllDTO;

public interface AdminProviderSearchDelegate {
	
	public AdminSearchResultsAllDTO getProviderSearchResults(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap);
	public AdminSearchResultsAllDTO getSearchResultsForAdmin(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap);
	public AdminSearchResultsAllDTO getProviderSearchResultsBySkill(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap);
	/**
	 * @param vendorId
	 * @return
	 */
	public ProviderResultForAdminVO getProviderAdmin (Integer vendorId);
	
}