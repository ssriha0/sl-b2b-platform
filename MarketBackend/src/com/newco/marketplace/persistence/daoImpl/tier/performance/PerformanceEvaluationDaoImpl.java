package com.newco.marketplace.persistence.daoImpl.tier.performance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.tier.performance.PerformanceEvaluationDao;
import com.newco.marketplace.vo.provider.FirmAndResource;
import com.newco.marketplace.vo.provider.VendorResource;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.domain.tier.performance.vo.PerformanceCriteria;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public class PerformanceEvaluationDaoImpl extends ABaseImplDao implements PerformanceEvaluationDao{
	public List<FirmAndResource> fetchAllFirmsNew()throws DataServiceException{
		List<FirmAndResource> firmProviderList = new ArrayList<FirmAndResource>();
		try{
			firmProviderList = (List<FirmAndResource>) queryForList("fetchAllFirmsNew.query");
		}
		catch (Exception e) {
			logger.info("Exception occured in fetching records"+ e.getMessage());
			return firmProviderList;
		}
		return firmProviderList;
	}
	
	public boolean checkArchival()throws DataServiceException{
		boolean result = false;
		String appValue = null;
		try{
			appValue = (String) queryForObject("getAppValue.query", "DB_archival_status");
			if("true".equalsIgnoreCase(appValue)){
				result = true;
			}
		}
		catch (Exception e) {
			throw new DataServiceException("Error occured while checking Archival flag", e);	
		}
		return result;
	}
	
	/**
	 * Get the number of records to process together
	 * @param key
	 * @return
	 * @throws DataServiceException
	 */
	public int getProcessTogetherCount(String key) throws DataServiceException{
		int numberOfRecordsToProcess = 0;		
		try{
			String result = (String) queryForObject("getAppValue.query", key);
			if(null!=result){
				numberOfRecordsToProcess = Integer.parseInt(result);	
			}
				
		}
		catch (Exception e) {
			logger.info("Error occured while getting the app property " + e.getMessage());
			return numberOfRecordsToProcess;
		}
		return numberOfRecordsToProcess;
	}
	
	
	/**
	 * Fetch the performance criteria look up
	 * @return
	 * @throws BusinessServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<PerformanceCriteria> lookupPerformaceCriteria() throws DataServiceException{		
		List<PerformanceCriteria> lookUpList = new ArrayList<PerformanceCriteria>();
		try{
			lookUpList = (List<PerformanceCriteria>) queryForList("fetch_lookup_criteria.query");		
		}catch (Exception e) {
			logger.info("Exception occured in performance criteria look up"+ e.getMessage());
			return lookUpList;
		}
		return lookUpList;
	}
	
	public void insertProviderPerformance(List<PerformanceVO> providerPerformanceList)throws DataServiceException{
		try{
			/*if(providerPerformanceList!=null && providerPerformanceList.size()>0){
				Integer vendorId = providerPerformanceList.get(0).getVendorId();
				if(vendorId!=null){
					delete("providerPerformance.delete",vendorId);
				}
			}*/
			splitInsert("providerPerformance.insert", providerPerformanceList);
		}catch (Exception dse){
			
		}
	}
	public void insertFirmPerformance(List<PerformanceVO> firmPerformanceList)throws DataServiceException{
		try{
			splitInsert("firmPerformance.insert", firmPerformanceList);
		}catch (Exception dse){
			
		}
	}
	public void insertFirmPerformanceHistory(List<PerformanceVO> firmPerformanceList)throws DataServiceException{
		try{
			splitBatchInsert("firmPerformanceHistory.insert", firmPerformanceList);
		}catch (Exception dse){
			
		}
	}
	public void insertProviderPerformanceHistory(List<PerformanceVO> providerPerformanceList)throws DataServiceException{
		try{
			splitBatchInsert("providerPerformanceHistory.insert", providerPerformanceList);
		}catch (Exception dse){
			
		}
	}
	public List<PerformanceVO> fetchFirmPerformanceScore()throws DataServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			perfList = (List<PerformanceVO>) queryForList("getPerfScoreForFirm.query");
		}catch (Exception dse){
			
		}
		return perfList;
	}
	public List<PerformanceVO> fetchProviderPerformanceScore()throws DataServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			perfList = (List<PerformanceVO>) queryForList("getPerfScoreForProvider.query");
		}catch (Exception dse){
			
		}
		return perfList;
	}
	
	public void updateFirmPerformanceScore(List<PerformanceVO> firmPerformanceList)throws DataServiceException{
		try{
			splitBatchUpdate("firmPerformanceScore.update", firmPerformanceList);
		}catch (Exception dse){
			
		}
	}
	public void updateProviderPerformanceScore(List<PerformanceVO> providerPerformanceList)throws DataServiceException{
		try{
			
			splitBatchUpdate("providerPerformanceScore.update", providerPerformanceList);
		}catch (Exception dse){
			
		}
	}
	private void splitBatchUpdate(String query, List<PerformanceVO> performanceList) throws DataServiceException{
		try{
			int noOfRecords = (performanceList==null?0:performanceList.size());
			int noOfIter = 0;
			int count =10000;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = performanceList.size();
				}
				List<PerformanceVO> firms = performanceList.subList(loopCount*count, endIndex);
				batchUpdate(query, firms);
				
				loopCount++;
				noOfIter--;
			}
		}catch (Exception dse){
			
		}
	}
	private void splitBatchInsert(String query, List<PerformanceVO> performanceList) throws DataServiceException{
		try{
			int noOfRecords = (performanceList==null?0:performanceList.size());
			int noOfIter = 0;
			int count =10000;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = performanceList.size();
				}
				List<PerformanceVO> firms = performanceList.subList(loopCount*count, endIndex);
				batchInsert(query, firms);
				
				loopCount++;
				noOfIter--;
			}
		}catch (Exception dse){
			
		}
	}
	private void splitInsert(String query, List<PerformanceVO> performanceList) throws DataServiceException{
		try{
			int noOfRecords = (performanceList==null?0:performanceList.size());
			int noOfIter = 0;
			int count =500;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = performanceList.size();
				}
				List<PerformanceVO> firms = performanceList.subList(loopCount*count, endIndex);
				insert(query, firms);
				
				loopCount++;
				noOfIter--;
			}
		}catch (Exception dse){
			
		}
	}
	

	/**
	 * Get the performance values for the selected criteria
	 * @param providerIds
	 * @param criteriaId
	 * @return
	 * @throws DataServiceException
	 */
	public List<PerformanceVO> getPerformaceValues(List<String> providerIds,String criteria) throws DataServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			if(Constants.CSAT.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getCSATForBuyers.query",providerIds);
			}else if(Constants.ACCEPTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedCountForBuyers.query",providerIds);
			}else if(Constants.ACCEPTED_IF_ROUTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedIfRoutedCountForBuyers.query",providerIds);
			}else if(Constants.ROUTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getRoutedCountForBuyers.query",providerIds);
			}else if (Constants.COMPLETED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getCompletedRateForBuyers.query",providerIds);
			}else if(Constants.RELEASED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getReleasedCountForBuyers.query",providerIds);
			}else if(Constants.REJECTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getRejectedCountForBuyers.query",providerIds);
			}else if(Constants.RESCHEDULED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getRescheduledCountForBuyers.query",providerIds);
			}else if(Constants.RESPONSE.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getResponseRateForBuyers.query",providerIds);
			}else if(Constants.IVR.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getIVRRateForBuyers.query",providerIds);
			}else if(Constants.ACCEPTED_BY_FIRM.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedCountForFirmBuyers.query",providerIds);
			}else if(Constants.ACCEPTED_BY_FIRM_IF_ROUTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedIfRoutedCountForFirmBuyers.query",providerIds);
			}
			
		}
		catch (Exception e) {
			logger.info("Exception occured in fetching records"+ e.getMessage());
			return perfList;
		}
		return perfList;
	}
}

