package com.newco.match.rank.service;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.match.rank.performancemetric.service.IRankMatchingService;

public class RankMetricsBoImpl implements IRankMetricsBO {

	// private IPerformanceMetricService performanceMetricService;
	// public IPerformanceMetricService getPerformanceMetricService() {
	// return performanceMetricService;
	// }
	// public void setPerformanceMetricService(IPerformanceMetricService
	// performanceMetricService) {
	// this.performanceMetricService = performanceMetricService;
	// }
	
	private IRankMatchingService rankMatchingService;

	public IRankMatchingService getRankMatchingService() {
		return rankMatchingService;
	}

	public void setRankMatchingService(IRankMatchingService rankMatchingService) {
		this.rankMatchingService = rankMatchingService;
	}

	// ---------------------------------------------- //
	public String getProviderByRankCriteria(List<D2CProviderAPIVO> firmDetailsListVo, final String buyerId) {
		// 1. [PERFORMANCE_METRIC || NEW_SIGNUP || MATCHING_PER]
		String corelationId = null;
		if (null != firmDetailsListVo) {
			if (firmDetailsListVo.size() > 0) {
				corelationId = getRankMatchingService().getProviderByRankMatchingMetric(firmDetailsListVo, buyerId);
			}
		}
		return corelationId;
	}

}
