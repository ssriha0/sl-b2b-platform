package com.newco.marketplace.business.businessImpl.tier.performance;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.business.iBusiness.tier.performance.IPerformanceEvaluationService;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.tier.performance.PerformanceEvaluationDao;
import com.newco.marketplace.vo.provider.FirmAndResource;
import com.newco.marketplace.vo.provider.VendorResource;
import com.servicelive.domain.tier.performance.vo.PerformanceCriteria;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public class PerformanceEvaluationService implements IPerformanceEvaluationService{
	private PerformanceEvaluationDao performanceEvaluationDao;
	
	public PerformanceEvaluationDao getPerformanceEvaluationDao() {
		return performanceEvaluationDao;
	}

	public void setPerformanceEvaluationDao(
			PerformanceEvaluationDao performanceEvaluationDao) {
		this.performanceEvaluationDao = performanceEvaluationDao;
	}

	public List<FirmAndResource> fetchAllFirmsNew() throws BusinessServiceException {
		List<FirmAndResource> firmProviderList = null;
		try{
			firmProviderList = performanceEvaluationDao.fetchAllFirmsNew();
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
		return firmProviderList;
	}
	public boolean checkArchival() throws BusinessServiceException {
		try{
			return performanceEvaluationDao.checkArchival();
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	
	
	/**
	 * Get the number of records to process together
	 * @param key
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public int getProcessTogetherCount(String key) throws BusinessServiceException{
		try{
			return performanceEvaluationDao.getProcessTogetherCount(key);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	
	
	/**
	 * Fetch the performance criteria look up
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PerformanceCriteria> lookupPerformaceCriteria() throws BusinessServiceException{
		try{
			return performanceEvaluationDao.lookupPerformaceCriteria();
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	
	
	public void insertProviderPerformance(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException{
		try{
			performanceEvaluationDao.insertProviderPerformance(providerPerformanceList);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void insertFirmPerformance(List<PerformanceVO> firmPerformanceList)throws BusinessServiceException{
		try{
			performanceEvaluationDao.insertFirmPerformance(firmPerformanceList);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void insertProviderPerformanceHistory(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException{
		try{
			performanceEvaluationDao.insertProviderPerformanceHistory(providerPerformanceList);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void insertFirmPerformanceHistory(List<PerformanceVO> firmPerformanceList)throws BusinessServiceException{
		try{
			performanceEvaluationDao.insertFirmPerformanceHistory(firmPerformanceList);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	
	public List<PerformanceVO> fetchFirmPerformanceScore()throws BusinessServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			perfList = performanceEvaluationDao.fetchFirmPerformanceScore();
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
		return perfList;
	}
	public List<PerformanceVO> fetchProviderPerformanceScore()throws BusinessServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			perfList = performanceEvaluationDao.fetchProviderPerformanceScore();
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
		return perfList;
	}
	
	public void updateFirmPerformanceScore(List<PerformanceVO> firmPerformanceList)throws BusinessServiceException{
		try{
			performanceEvaluationDao.updateFirmPerformanceScore(firmPerformanceList);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	public void updateProviderPerformanceScore(List<PerformanceVO> providerPerformanceList)throws BusinessServiceException{
		try{
			performanceEvaluationDao.updateProviderPerformanceScore(providerPerformanceList);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
	}
	

	public List<PerformanceVO> getPerformaceValues(List<String> providerIds,
			String criteria) throws BusinessServiceException {
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			perfList = performanceEvaluationDao.getPerformaceValues(providerIds,criteria);
		}catch (DataServiceException dse){
			throw new BusinessServiceException(dse);
		}
		return perfList;
	}
}
