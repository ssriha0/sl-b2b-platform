package com.newco.marketplace.persistence.iDao.tier.performance;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public interface PerformanceEvaluationFetchDao {
	
	/**
	 * Get the performance values for the selected criteria
	 * @param providerIds
	 * @param criteria
	 * @return
	 * @throws DataServiceException
	 */
	public List<PerformanceVO> getPerformaceValues(List<String> providerIds,String criteria) throws DataServiceException;

}
