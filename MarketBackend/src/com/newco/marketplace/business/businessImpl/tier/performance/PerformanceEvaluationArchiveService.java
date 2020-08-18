package com.newco.marketplace.business.businessImpl.tier.performance;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.business.iBusiness.tier.performance.IPerformanceEvaluationFetchService;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.tier.performance.PerformanceEvaluationFetchDao;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public class PerformanceEvaluationArchiveService implements IPerformanceEvaluationFetchService{
	private PerformanceEvaluationFetchDao performanceEvaluationDao;
	
	public PerformanceEvaluationFetchDao getPerformanceEvaluationDao() {
		return performanceEvaluationDao;
	}

	public void setPerformanceEvaluationDao(
			PerformanceEvaluationFetchDao performanceEvaluationDao) {
		this.performanceEvaluationDao = performanceEvaluationDao;
	}

	
	
	
	/**
	 * Get the performance values for the selected criteria
	 * @param providerIds
	 * @param criteria
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PerformanceVO> getPerformaceValues(List<String> providerIds,String criteria) throws BusinessServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			perfList = performanceEvaluationDao.getPerformaceValues(providerIds,criteria);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
		return perfList;
	}

	
}
