package com.newco.batch.performancemetric;

import java.util.Date;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.match.rank.performancemetric.service.IPerformanceMetricService;

public class PerformanceMetricProcessor {
	
	private static final Logger logger = Logger.getLogger(PerformanceMetricProcessor.class);
	private IPerformanceMetricService performanceMetricService;

	public IPerformanceMetricService getPerformanceMetricService() {
		return performanceMetricService;
	}

	public void setPerformanceMetricService(IPerformanceMetricService performanceMetricService) {
		this.performanceMetricService = performanceMetricService;
	}
	
	public void process() throws BusinessServiceException {
		logger.info("====== START: PerformanceMetricProcessor: " + new Date());
		Integer buyerId = 3333;
		getPerformanceMetricService().calculateAndSavePerformanceMatric(buyerId);
		logger.info("====== END: PerformanceMetricProcessor: " + new Date());
	}

}
