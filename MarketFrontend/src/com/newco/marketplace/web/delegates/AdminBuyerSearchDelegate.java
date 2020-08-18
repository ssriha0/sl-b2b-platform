/**
 * 
 */
package com.newco.marketplace.web.delegates;

import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.web.dto.AdminSearchFormDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsAllDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsDTO;
import com.newco.marketplace.web.dto.ProviderWidgetResultsDTO;

/**
 * @author HOZA
 *
 */
public interface AdminBuyerSearchDelegate {
	public AdminSearchResultsAllDTO getSearchResultsForAdmin(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap);
	
	public AdminSearchResultsDTO getSearchResultsForAdminWidget(String soId, Integer buyerId);
	
	public ProviderWidgetResultsDTO getInfoForProviderWidget(String soId,boolean isBuyer,Integer buyerId);
}
