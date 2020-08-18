package com.newco.marketplace.persistence.iDao.tier.performance;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.FirmAndResource;
import com.newco.marketplace.vo.provider.VendorResource;
import com.servicelive.domain.tier.performance.vo.PerformanceCriteria;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public interface PerformanceEvaluationDao extends PerformanceEvaluationFetchDao{

	public List<FirmAndResource> fetchAllFirmsNew()throws DataServiceException;

	public boolean checkArchival()throws DataServiceException;
	public void insertProviderPerformance(List<PerformanceVO> providerPerformanceList)throws DataServiceException;
	public void insertFirmPerformance(List<PerformanceVO> firmPerformanceList)throws DataServiceException;
	public void insertProviderPerformanceHistory(List<PerformanceVO> providerPerformanceList)throws DataServiceException;
	public void insertFirmPerformanceHistory(List<PerformanceVO> providerPerformanceList)throws DataServiceException;
	public List<PerformanceVO> fetchFirmPerformanceScore()throws DataServiceException;
	public List<PerformanceVO> fetchProviderPerformanceScore()throws DataServiceException;
	public void updateFirmPerformanceScore(List<PerformanceVO> firmPerformanceList)throws DataServiceException;
	public void updateProviderPerformanceScore(List<PerformanceVO> providerPerformanceList)throws DataServiceException;
	
	/**
	 * Get the number of records to process together
	 * @param key
	 * @return
	 * @throws DataServiceException
	 */
	
	public int getProcessTogetherCount(String key) throws DataServiceException;
	
	/**
	 * Fetch the performance criteria look up
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PerformanceCriteria> lookupPerformaceCriteria() throws DataServiceException;
		
	
}
