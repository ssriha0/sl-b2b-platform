package com.newco.marketplace.business.iBusiness.tier.performance;

import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.FirmAndResource;
import com.newco.marketplace.vo.provider.VendorResource;
import com.servicelive.domain.tier.performance.vo.PerformanceCriteria;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public interface IPerformanceEvaluationService extends IPerformanceEvaluationFetchService{
	
	public List<FirmAndResource> fetchAllFirmsNew()throws BusinessServiceException;
	public boolean checkArchival() throws BusinessServiceException;
	public void insertProviderPerformance(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException;
	public void insertFirmPerformance(List<PerformanceVO> firmPerformanceList)throws BusinessServiceException;
	public void insertProviderPerformanceHistory(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException;
	public void insertFirmPerformanceHistory(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException;
	public List<PerformanceVO> fetchFirmPerformanceScore()throws BusinessServiceException;
	public List<PerformanceVO> fetchProviderPerformanceScore()throws BusinessServiceException;
	public void updateFirmPerformanceScore(List<PerformanceVO> firmPerformanceList)throws BusinessServiceException;
	public void updateProviderPerformanceScore(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException;
	
	/**
	 * Get the number of records to process together
	 * @param key
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public int getProcessTogetherCount(String key) throws BusinessServiceException;
	
	/**
	 * Fetch the performance criteria look up
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PerformanceCriteria> lookupPerformaceCriteria() throws BusinessServiceException;
}
