package com.servicelive.routingrulesengine.dao;

import java.util.List;

public interface BuyerSkuDao {
	/**
	 * 
	 * @param buyerId
	 * @param specialtyCode
	 * @return List<String>
	 */	
	public List<String> findBySpecialtyCodeAndBuyerID(Integer buyerId, String specialtyCode);
	/**
	 * The method is used to validate a list of job Codes
	 * 
	 * @param List
	 *            <String> jobCodes :jobCodes to get validated
	 * @param buyerId
	 * @return List<String>
	 */
	public List<String> validateJobCodes(List<String> jobCodes,
			Integer buyerId);
	
}
