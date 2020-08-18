package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.provider.ProviderFirm;

/**
 * 
 *
 */
public interface ProviderFirmDao {

	/**
	 * 
	 * @param providerFirmId
	 * @return ProviderFirm
	 */
	public ProviderFirm findById(Integer providerFirmId);
	
	
	/**
	 * method to validate firm ids
	 * 
	 * @param providerFirmIds:List
	 *            of firm ids to be validated
	 * @return List<Integer>:List of valid firm ids
	 */
	public List<Integer> validateFirmIds(List<Integer> providerFirmId);
}
