package com.newco.marketplace.business.iBusiness.providersearch;

import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IETMProviderSearch {

	public ETMProviderSearchResults getETMProviderList(CriteriaMap critera, String username) throws BusinessServiceException;
	
	public ETMProviderSearchResults getETMProviderListPaginateResult(CriteriaMap critera, String username) throws BusinessServiceException;

	public ETMProviderSearchResults getETMProviderListApplyFilter(CriteriaMap critera, String username) throws BusinessServiceException;

	public ETMProviderSearchResults findProvidersByCriteria(CriteriaMap critera, String username,Boolean filterInd) throws BusinessServiceException;
	
	public Integer cleanETMTempTable(String searchQueryKey ) throws BusinessServiceException;
	
	public Integer cleanETMTempTableOldRecords() throws BusinessServiceException;
	
	public Integer getDefaultDistance() throws BusinessServiceException;

	
}

