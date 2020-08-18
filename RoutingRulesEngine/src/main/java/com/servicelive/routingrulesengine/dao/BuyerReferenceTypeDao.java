package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.so.BuyerReferenceType;

/**
 * 
 * @author svanloon
 *
 */
public interface BuyerReferenceTypeDao {

	/**
	 * 
	 * @param buyerId
	 * @return List<BuyerReferenceType>
	 */
	public List<BuyerReferenceType> findByBuyerId(Integer buyerId);

	
	/**
	 * The method is used to validate a list of custom references
	 * 
	 * @param List
	 *            <String> custRefs :custom references to get validated
	 * @param buyerId
	 * @return List<String>
	 */
	public List<String> validateCustRefs(List<String> custRefs,
			Integer buyerId);
	
	public List<String> getBuyerCustomReferences(Integer buyerId);
}
