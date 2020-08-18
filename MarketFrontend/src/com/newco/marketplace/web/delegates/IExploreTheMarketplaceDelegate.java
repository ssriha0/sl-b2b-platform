package com.newco.marketplace.web.delegates;

import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IExploreTheMarketplaceDelegate {

	public ETMProviderSearchResults getCompleteProviderList(CriteriaMap crtMap, String userName) throws BusinessServiceException;
	public ETMProviderSearchResults getCompleteProviderListPaginateResult(CriteriaMap crtMap, String userName) throws BusinessServiceException;
	public ETMProviderSearchResults getCompleteProviderListApplyFilter(CriteriaMap crtMap, String userName) throws BusinessServiceException;
	public ETMProviderSearchResults getCriteriaProviderList(CriteriaMap crtMap, String userName,Boolean filterInd) throws BusinessServiceException;
	public Integer cleanETMTempTable(String searchQueryKey) throws BusinessServiceException;
	public Integer getDefaultDistance() throws BusinessServiceException;

}
