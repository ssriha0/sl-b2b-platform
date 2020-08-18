package com.newco.marketplace.business.iBusiness.buyer;

import java.util.List;

public interface IBuyerFeatureSetBO {

	/**
	 * See if a buyer has a feature by passing the feature name and buyerID. If the feature is
	 * present this will return true.
	 * @param buyerID
	 * @param feature
	 * @return
	 */
	public Boolean validateFeature(Integer buyerID, String feature);
	
	/**
	 * Get all the feature sets for a buyer
	 * @param buyerID
	 * @return
	 */
	public List<String> getFeatures(Integer buyerID);
	
	/**
	 * Get all the buyer IDs for the specified feature set
	 * @param String feature
	 * @return A list of Buyer IDs
	 */
	public List<Integer> getBuyerIdsForFeature(String feature);

	public Double getCancelFeeForBuyer(Integer buyerId);
	
}
