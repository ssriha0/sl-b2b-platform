/**
 * 
 */
package com.newco.marketplace.persistence.iDao.providerSearch;

import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author dmill03
 *
 */
public interface ETMProviderSearchDao  {

	public ETMProviderSearchResults 
			getETMProviderList(CriteriaMap critera, String username) throws DataServiceException;
	
	public ETMProviderSearchResults 
	getETMProviderListApplyFilter(CriteriaMap critera, String username) throws DataServiceException;
	
	public ETMProviderSearchResults 
				findProvidersByCriteria(CriteriaMap critera) throws DataServiceException;
	
	public int loadETMTempTable(ETMProviderSearchResults results) throws DataServiceException;
	
	public int cleanETMTempTable(String searchQueryKey) throws DataServiceException;
	
	public Integer cleanETMTempTableOldRecords() throws Exception;
	
	public Integer getPaginationCount(CriteriaMap critera) throws DataServiceException;
	
	public Integer getDefaultDistance() throws DataServiceException;

	
}
