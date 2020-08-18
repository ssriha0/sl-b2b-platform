package com.newco.match.rank.performancemetric.service;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;

public interface IPerformanceMetricService {
	
	// start: DAO TEST method -----------
	void testDaoImpl();
	// end: DAO TEST method -------------

	void calculateAndSavePerformanceMatric(Integer buyerId);
	
}
