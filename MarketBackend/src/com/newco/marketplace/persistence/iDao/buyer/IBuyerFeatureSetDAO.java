package com.newco.marketplace.persistence.iDao.buyer;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;

public interface IBuyerFeatureSetDAO {
	
	public List<String> getFeatures(Integer buyerID) throws DataServiceException;
	
	public String getFeature(Integer buyerID, String feature) throws DataServiceException;
	
	public List<Integer> getSHCBuyerIds(String feature) throws DataServiceException ;

	public Double getCancelFeeForBuyer(Integer buyerId);
}
