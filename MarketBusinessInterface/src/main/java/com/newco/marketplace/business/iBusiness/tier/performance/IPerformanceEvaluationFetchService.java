package com.newco.marketplace.business.iBusiness.tier.performance;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public interface IPerformanceEvaluationFetchService {
	
	
	/**
	 * Get the performance values for the selected criteria
	 * @param providerIds
	 * @param criteria
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PerformanceVO> getPerformaceValues(List<String> providerIds,String criteria) throws BusinessServiceException;

}
