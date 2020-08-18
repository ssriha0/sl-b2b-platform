package com.newco.marketplace.business.iBusiness.provider.searchmatchrank;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.exception.core.DataServiceException;
/**
 * D2C provider portal business object
 * 
 * @author aradke0
 *
 */
public interface IProviderSearchMatchRankBO {
	List<D2CProviderAPIVO> getFirmDetailsListByMultipleCriteriaSearchMatchRank(D2CPortalAPIVORequest d2cPortalAPIVO) throws DataServiceException;
	public List<D2CProviderAPIVO> getFirmDetailsWithBuyerPrice(String buyerId, String sku, String zip, List<String> firmList) throws DataServiceException;
}
