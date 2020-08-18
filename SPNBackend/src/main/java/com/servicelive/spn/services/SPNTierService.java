package com.servicelive.spn.services;

import java.util.List;

import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.domain.spn.network.SPNTierPerformanceLevel;

public interface SPNTierService
{
	List<SPNTierMinutes> getAllTierMinutes() throws Exception;

	public void saveTier(Integer spnId, Integer tierId, Integer minutes, List<Integer> performanceLevels, String modifiedBy) throws Exception;
	
	public void deleteTier(Integer spnId, Integer tierId) throws Exception;
	
	public void saveTierMinutes(SPNTierMinutes tierMinutes) throws Exception;

	public void saveTierPerformanceLevel(SPNTierPerformanceLevel performanceLevel) throws Exception;
	
	public int getMinimumDelayMinutes();
	
	
}
