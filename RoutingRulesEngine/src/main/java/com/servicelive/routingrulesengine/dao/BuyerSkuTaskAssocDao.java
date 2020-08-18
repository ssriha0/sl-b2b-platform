package com.servicelive.routingrulesengine.dao;

import java.util.List;
import com.servicelive.domain.so.BuyerSkuTaskAssoc;

/**
 * 
 * @author svanloon
 *
 */
public interface BuyerSkuTaskAssocDao {

	/**
	 * 
	 * @param buyerId
	 * @param specialtyCode
	 * @return List<String>
	 */	
	public List<String> findDistinctJobCodeByBuyerIdAndSpecialtyCode(Integer buyerId, String specialtyCode);
	
	
	/**
	 * 
	 * @param buyerId
	 * @return list of specialty codes
	 */
	public List<String> findDistinctSpecialtyCodeByBuyerId(Integer buyerId);
	
	/**
	 * 
	 * @param buyerId
	 * @param specialtyCode
	 * @return List<BuyerSkuTaskAssoc>
	 */
	public List<BuyerSkuTaskAssoc> findByBuyerIdAndSpecialtyCode(Integer buyerId, String specialtyCode);
}
