package com.newco.marketplace.web.delegatesImpl;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.providersearch.IETMProviderSearch;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.IExploreTheMarketplaceDelegate;

public class ExploreTheMarketplaceDelegateImpl implements IExploreTheMarketplaceDelegate
{
	private static final Logger logger = Logger.getLogger(ExploreTheMarketplaceDelegateImpl.class.getName());
	private IETMProviderSearch etmProviderSearchBo;
	
	public IETMProviderSearch getEtmProviderSearchBo() {
		return etmProviderSearchBo;
	}
	public void setEtmProviderSearchBo(IETMProviderSearch etmProviderSearchBo) {
		this.etmProviderSearchBo = etmProviderSearchBo;
	}
		
	public ETMProviderSearchResults getCompleteProviderList(CriteriaMap crtMap, String userName) throws BusinessServiceException{
		logger.debug("----Start of ExploreTheMarketplaceDelegateImpl.getCompleteProviderList----");
		ETMProviderSearchResults etmProviderResults = null;
		etmProviderResults = etmProviderSearchBo.getETMProviderList(crtMap, userName);
		logger.debug("----End of ExploreTheMarketplaceDelegateImpl.getCompleteProviderList----");
		return etmProviderResults;
	}
	
	public ETMProviderSearchResults getCompleteProviderListPaginateResult(CriteriaMap crtMap, String userName) throws BusinessServiceException{
		logger.debug("----Start of ExploreTheMarketplaceDelegateImpl.getCompleteProviderListPaginateResult----");
		ETMProviderSearchResults etmProviderResults = null;
		etmProviderResults = etmProviderSearchBo.getETMProviderListPaginateResult(crtMap, userName); 
		logger.debug("----End of ExploreTheMarketplaceDelegateImpl.getCompleteProviderListPaginateResult----");
		return etmProviderResults;
	}

	
	public ETMProviderSearchResults getCompleteProviderListApplyFilter(CriteriaMap crtMap, String userName) throws BusinessServiceException{
		logger.debug("----Start of ExploreTheMarketplaceDelegateImpl.getCompleteProviderList----");
		ETMProviderSearchResults etmProviderResults = null;
		etmProviderResults = etmProviderSearchBo.getETMProviderListApplyFilter(crtMap, userName);
		logger.debug("----End of ExploreTheMarketplaceDelegateImpl.getCompleteProviderList----");
		return etmProviderResults;
	}

	
	public ETMProviderSearchResults getCriteriaProviderList(CriteriaMap crtMap, String userName,Boolean filterInd) throws BusinessServiceException{
		logger.debug("----Start of ExploreTheMarketplaceDelegateImpl.getCriteriaProviderList----");
		ETMProviderSearchResults etmProviderResults = null;
		etmProviderResults = etmProviderSearchBo.findProvidersByCriteria(crtMap, userName,filterInd);
				logger.debug("----End of ExploreTheMarketplaceDelegateImpl.getCriteriaProviderList----");
		return etmProviderResults;
	}
	
	public Integer cleanETMTempTable(String searchQueryKey) throws BusinessServiceException {
		logger.debug(" Start of ExploreTheMarketplaceDelegateImpl --> cleanETMTempTable() ");
		
		Integer numRowsDeleted = etmProviderSearchBo.cleanETMTempTable(searchQueryKey);
		
		logger.debug(" End of ExploreTheMarketplaceDelegateImpl --> cleanETMTempTable() ");
		return numRowsDeleted;
	}
	
	public Integer getDefaultDistance() throws BusinessServiceException{
		logger.debug(" Start of ExploreTheMarketplaceDelegateImpl --> getDefaultDistance() ");
		Integer distance = etmProviderSearchBo.getDefaultDistance();
		logger.debug(" End of ExploreTheMarketplaceDelegateImpl --> getDefaultDistance() ");
		return distance;
	}
	
}
