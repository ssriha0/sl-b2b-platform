package com.newco.batch.tier.performance;
/*
 * The batch calculates the performance score of firms and providers.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.iBusiness.tier.performance.IPerformanceEvaluationFetchService;
import com.newco.marketplace.business.iBusiness.tier.performance.IPerformanceEvaluationService;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.vo.provider.FirmAndResource;
import com.servicelive.domain.tier.performance.vo.PerformanceCriteria;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public class PerformanceProcessor extends ABatchProcess {
	
	private IPerformanceEvaluationService performanceEvaluationService;
	private IPerformanceEvaluationFetchService performanceEvaluationArchiveService;
	
	private static final Logger logger = Logger.getLogger(PerformanceProcessor.class.getName());
	@Override
	public int execute() {
		evaluatePerformance ();
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private int evaluatePerformance () {
		
		try{
			logger.info("Entered evaluatePerformance");
			/*
			 * Fetch the archival flag which indicates if data has to be fetched from archival DB also.
			 * This is based on application property "DB_archival_status"
			 */
			boolean archivalFlag = performanceEvaluationService.checkArchival();
			
			//The number of firms to be processed to be inserted to provider_performance and firm_performance tables
			int numberOfRecordsToProcess = performanceEvaluationService.getProcessTogetherCount
				(Constants.NO_OF_RECORDS_FOR_PERF_SCORE_BATCH);
			
			if(numberOfRecordsToProcess == 0)
				numberOfRecordsToProcess = Constants.DEFAULT_NO_OF_RECORDS_FOR_PERF_SCORE_BATCH;
			
			//Get the list of criteria from the database - 8 different as of now 
			List<PerformanceCriteria> criteriaList =  performanceEvaluationService.lookupPerformaceCriteria();
			
			if(null==criteriaList || 0 == criteriaList.size()){
				// Stop the processing
				
				return 0;
			}
			
			// Fetch all firms and associated provider ids
			List<FirmAndResource> eligibleFirmProviderList = performanceEvaluationService.fetchAllFirmsNew();
			
			
			
			// TODO 
			/* Step 1 : Get the size of the list eligibleFirmProviderList
			 * Step 2 : Get the count of records which can be processed together
			 * 			Preferably from a property ( in table or in properties file)
			 * Step 3 : Get the list of criteria from the database - 8 different as of now 
			 * Step 4 : Iterate for each group of provider firms
			 * Step 5 : Group the providers . 
			 * 			What we have - comma separated provider ids for individual firms
			 * 			What we need - list of providers for all provider firms in the selected group.
			 * Step 6 : For each criteria 
			 * 			Write case statement
			 * 			Get the data and calculate the score
			 * 
			 * Step 7 : Calculate the aggregate score for the firms.
			 * 
			 * Step 8 : Insert the data to the database
			 * 			Note : On duplicate key update will take care of the update to existing records 
			 * 
			 * Step 9 : Create history 
			 * 			Write trigger on the score tables to take care of the history
			 * 
			 * 
			 * Step 10: Update the score for the SPNs
			 * 			Select the SPNs with tiered routing setup and update the score for each of them
			 *  
			 */
			
			
		
			
			// Get the count of the eligible provider firms
			int firmCount = (eligibleFirmProviderList==null?0:eligibleFirmProviderList.size());
			int noOfIter = 0;
			
			if(firmCount>0){
				noOfIter = firmCount/numberOfRecordsToProcess;
				if(noOfIter == 0){
					numberOfRecordsToProcess = firmCount;
					noOfIter =1;
				}
			}
			
			int loopCount = 0;
			while(noOfIter != 0){
				int endIndex = (loopCount+1)*numberOfRecordsToProcess;
				if(noOfIter == 1){
					endIndex = eligibleFirmProviderList.size();
				}
				List<FirmAndResource> firms = eligibleFirmProviderList.subList(loopCount*numberOfRecordsToProcess, endIndex);
				
				loopCount++;
				noOfIter--;
				List<PerformanceVO> providerPerformance = new ArrayList<PerformanceVO>();
				List<PerformanceVO> providerPerformanceList = new ArrayList<PerformanceVO>();
				List<PerformanceVO> firmPerformanceList = new ArrayList<PerformanceVO>();
				List<PerformanceVO> firmPerformance = new ArrayList<PerformanceVO>();
				List<PerformanceVO> acceptedArchivedList = new ArrayList<PerformanceVO>();
				List<PerformanceVO> acceptedFirmArchivedList = new ArrayList<PerformanceVO>();
				List<PerformanceVO> acceptedFirmList = new ArrayList<PerformanceVO>();
				List<PerformanceVO> routedArchivedList = new ArrayList<PerformanceVO>();
				
				HashMap<Integer, HashMap<Integer, PerformanceVO>> acceptedArchivedMap = null;
				HashMap<Integer, HashMap<Integer, PerformanceVO>> routedArchivedMap = null;
				
				List<String> providerIds = new ArrayList<String>();
				List<String> firmIds = new ArrayList<String>();
				
				//Getting firm ids and provider ids
				for(FirmAndResource firmAndResource : firms){
					List<String> ids =  Arrays.asList(firmAndResource.getResourceIdList().split(","));
					firmIds.add(firmAndResource.getVendorId().toString());
					providerIds.addAll(ids);
				}
				
				HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
				HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaFirmMap =  new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
				
				// Get the accepted and routed for all providers in one go
				HashMap<Integer, HashMap<Integer, PerformanceVO>> acceptedMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
				HashMap<Integer, HashMap<Integer, PerformanceVO>> routedMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
				List<PerformanceVO> criteriaValueList = new ArrayList<PerformanceVO>();
				
				for(PerformanceCriteria lookupCriteria: criteriaList){
					
					Integer criteriaId = lookupCriteria.getCriteriaId();
					
					switch (criteriaId){
					case 1: // CSAT Rating
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						criteriaValueList = new ArrayList<PerformanceVO>();
						//Getting the sum of CSAT scores, the total number of SOs for all providers specific for different buyers
						criteriaValueList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.CSAT);
						//Creating a map with key as provider id and value as another map with key as buyer id and value as Performance VO
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							//Get performance values from archive DB
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.CSAT);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							//Combine the values of archive DB with criteria map
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}
						
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						/*
						 *  Forms the list of records to be inserted to provider performance table. Also calculates the 
						 *  performance scores specific to firm in criteriaFirmMap
						 */
						
						providerPerformance = calculatePerformanceValues(criteriaMap, criteriaFirmMap,false, criteriaId);
						//Forms the list of records to be inserted in firm performance table from criteriaFirmMap
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 2: // Accepted Vs Routed
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						HashMap<Integer, HashMap<Integer, PerformanceVO>> acceptedIfRoutedMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						// Getting the accepted count for all providers specific for different buyers
						
						// SL-19607:As per the new requirement we have to consider service orders which were routed and accepted within 90 days instead of 
						// considering only service orders accepted within 90 days. 
						// A new constant ACCEPTED_IF_ROUTED is added and a separate query 'getAcceptedIfRoutedCountForBuyers.query' is included in performanceMap.xml as per new requirements.
						List<PerformanceVO> acceptedIfRoutedList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.ACCEPTED_IF_ROUTED);
						// Getting the routed count for all providers specific for different buyers
						List<PerformanceVO> routedList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.ROUTED);
						/*Creating a map with key as provider id and value as another map with key as buyer id and value as
						Performance VO for accepted count and routed count
						*/
						createProviderMap(acceptedIfRoutedList,acceptedIfRoutedMap);
						createProviderMap(routedList,routedMap);
						//Calculates archival values
						if(archivalFlag){
							// SL-19607:As per the requirement using ACCEPTED_IF_ROUTED instead of ACCEPTED
							acceptedArchivedList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.ACCEPTED_IF_ROUTED);
							routedArchivedList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.ROUTED);
							createProviderMap(acceptedArchivedList,acceptedArchivedMap);
							createProviderMap(routedArchivedList,routedArchivedMap);
							calculateArchivalValues(acceptedIfRoutedMap,acceptedArchivedMap);
							calculateArchivalValues(routedMap,routedArchivedMap);
						
						}
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						//Forms provider performance records and forms firm map
						providerPerformance = calculateCriteriaValues(acceptedIfRoutedMap,routedMap, criteriaFirmMap, criteriaId) ;
						//Get values of the count for Accepted by firm records
						
						// SL-19607:As per the new requirement we have to consider service orders which were routed and accepted within 90 days instead of 
						// considering only service orders accepted within 90 days to find the accepted orders count for a provider for a specific buyer. 
						// A new constant ACCEPTED_BY_FIRM_IF_ROUTED is added and a separate query 'getAcceptedIfRoutedCountForFirmBuyers.query' is included in performanceMap.xml as per new requirements.
						List<PerformanceVO> acceptedFirmIfRoutedList = performanceEvaluationService.getPerformaceValues(firmIds,Constants.ACCEPTED_BY_FIRM_IF_ROUTED);
						if(archivalFlag){
							acceptedFirmArchivedList = performanceEvaluationArchiveService.getPerformaceValues(firmIds,Constants.ACCEPTED_BY_FIRM_IF_ROUTED);
							acceptedFirmIfRoutedList.addAll(acceptedFirmArchivedList);
						}
						/*
						 * Considers accepted by firm records and add to criteriaFirmMap
						 */
						for(PerformanceVO firmVO:acceptedFirmIfRoutedList){
							Integer vendor = firmVO.getVendorId();
							Integer buyer = firmVO.getBuyerId();
							if(criteriaFirmMap.containsKey(vendor)){
								HashMap<Integer, PerformanceVO> buyerMap = criteriaFirmMap.get(vendor);
								if(buyerMap.containsKey(buyer)){
									PerformanceVO vo = buyerMap.get(buyer);
									vo.setPerfValueAll(vo.getPerfValueAll()+firmVO.getPerfValueAll());
									vo.setNinetyPerfVal(vo.getNinetyPerfVal()+firmVO.getNinetyPerfVal());
									buyerMap.put(buyer, vo);
								}else{
									buyerMap.put(buyer, firmVO);
								}
							}else{
								HashMap<Integer, PerformanceVO> buyerMap = new HashMap<Integer, PerformanceVO> ();
								buyerMap.put(buyer, firmVO);
								criteriaFirmMap.put(vendor, buyerMap);
							}
						}
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 3: // Completed Vs Accepted 
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						criteriaValueList = new ArrayList<PerformanceVO>();
						// SL-19607:As per the new requirement we have to consider service orders which were accepted and completed within 90 days instead of 
						// considering only service orders completed within 90 days to find the completed orders count for a provider for a specific buyer. 
						// 'getCompletedRateForBuyers.query' is modified as per the new requirements.
						criteriaValueList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.COMPLETED);
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>>  criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.COMPLETED);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}

						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						providerPerformance = calculatePerformanceValues(criteriaMap, criteriaFirmMap,false, criteriaId);
						// SL-19607
						// Fetching accepted firm list. This list will be reused in 'case 4' and 'case 6' also.
						// Prior to this requirement acceptedFirmList was fetched in 'case 2' and was reused in the subsequent cases.
						acceptedFirmList = performanceEvaluationService.getPerformaceValues(firmIds,Constants.ACCEPTED_BY_FIRM);
						if(archivalFlag){
							acceptedFirmArchivedList = performanceEvaluationArchiveService.getPerformaceValues(firmIds,Constants.ACCEPTED_BY_FIRM);
							acceptedFirmList.addAll(acceptedFirmArchivedList);
						}
						calculateValuesForAcceptedFirm( criteriaFirmMap, acceptedFirmList);
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 4: // Released Vs accepted
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						// SL-19607
						// Fetching accepted list.
						// Prior to this requirement acceptedList was fetched in 'case 2' and was reused in the subsequent cases.
						List<PerformanceVO> acceptedList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.ACCEPTED);
						// acceptedMap will be reused in case 6 also.
						createProviderMap(acceptedList,acceptedMap);
						criteriaValueList = new ArrayList<PerformanceVO>();
						// SL-19607:As per the new requirement we have to consider service orders which were accepted and released within 90 days instead of 
						// considering only service orders released within 90 days to find the released orders count for a provider for a specific buyer. 
						// 'getReleasedCountForBuyers.query' is modified as per the new requirements.
						criteriaValueList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.RELEASED);
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>>  criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.RELEASED);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}
						
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						providerPerformance = calculateCriteriaValues(criteriaMap,acceptedMap, criteriaFirmMap, criteriaId) ;
						if(archivalFlag){
							acceptedFirmArchivedList = performanceEvaluationArchiveService.getPerformaceValues(firmIds,Constants.ACCEPTED_BY_FIRM);
							acceptedFirmList.addAll(acceptedFirmArchivedList);
						}
						calculateValuesForAcceptedFirm( criteriaFirmMap, acceptedFirmList);
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 5: // Rejected Vs Routed
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						criteriaValueList = new ArrayList<PerformanceVO>();
						// SL-19607:As per the new requirement we have to consider service orders which were routed and rejected within 90 days instead of 
						// considering only service orders rejected within 90 days to find the rejected orders count for a provider for a specific buyer. 
						// 'getRejectedCountForBuyers.query' is modified as per the new requirements.
						criteriaValueList = performanceEvaluationService.getPerformaceValues (providerIds,Constants.REJECTED);
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>>  criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues (providerIds,Constants.REJECTED);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}
						
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						providerPerformance = calculateCriteriaValues(criteriaMap,routedMap, criteriaFirmMap, criteriaId) ;
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 6: // Rescheduled Vs Accepted
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						criteriaValueList = new ArrayList<PerformanceVO>();
						// SL-19607:As per the new requirement we have to consider service orders which were accepted and rescheduled within 90 days instead of 
						// considering only service orders rescheduled within 90 days to find the rescheduled orders count for a provider for a specific buyer. 
						// 'getRescheduledCountForBuyers.query' is modified as per the new requirements.
						criteriaValueList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.RESCHEDULED);
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>>  criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.RESCHEDULED);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}
						
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						providerPerformance = calculateCriteriaValues(criteriaMap,acceptedMap, criteriaFirmMap, criteriaId) ;
						if(archivalFlag){
							acceptedFirmArchivedList = performanceEvaluationArchiveService.getPerformaceValues(firmIds,Constants.ACCEPTED_BY_FIRM);
							acceptedFirmList.addAll(acceptedFirmArchivedList);
						}
						calculateValuesForAcceptedFirm( criteriaFirmMap, acceptedFirmList);
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 7: // Response Rate
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						criteriaValueList = new ArrayList<PerformanceVO>();
						criteriaValueList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.RESPONSE);
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>>  criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.RESPONSE);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}
						
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						providerPerformance = calculatePerformanceValues(criteriaMap, criteriaFirmMap,false, criteriaId);
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					case 8: // IVR
						criteriaMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						criteriaValueList = new ArrayList<PerformanceVO>();
						criteriaValueList = performanceEvaluationService.getPerformaceValues(providerIds,Constants.IVR);
						createProviderMap(criteriaValueList,criteriaMap);
						
						if(archivalFlag){
							HashMap<Integer, HashMap<Integer, PerformanceVO>>  criteriaArchiveMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
							List<PerformanceVO> criteriaValueArchiveList = performanceEvaluationArchiveService.getPerformaceValues(providerIds,Constants.IVR);
							createProviderMap(criteriaValueArchiveList,criteriaArchiveMap);
							calculateArchivalValues(criteriaMap,criteriaArchiveMap);
						}
						
						criteriaFirmMap = new HashMap<Integer, HashMap<Integer, PerformanceVO>>();
						firmPerformance = new ArrayList<PerformanceVO>();
						providerPerformance = calculatePerformanceValues(criteriaMap, criteriaFirmMap,false, criteriaId);
						firmPerformance = calculatePerformanceValues(criteriaFirmMap,null, true,criteriaId);
						providerPerformanceList.addAll(providerPerformance);
						firmPerformanceList.addAll(firmPerformance);
						break;
						
					default :
						logger.info("Criteria is not defined::");
						break;
					}
				}
				
				
				
				
				if(null!= providerPerformanceList && providerPerformanceList.size()>0){
					performanceEvaluationService.insertProviderPerformance(providerPerformanceList);
					
				}
				
				if(null!=firmPerformanceList && firmPerformanceList.size()>0){
					performanceEvaluationService.insertFirmPerformance(firmPerformanceList);
				}
				
			}
			
			List<PerformanceVO> providerPerformanceScoreList = performanceEvaluationService.fetchProviderPerformanceScore();
			List<PerformanceVO> firmPerformanceScoreList = performanceEvaluationService.fetchFirmPerformanceScore();
			
			performanceEvaluationService.updateProviderPerformanceScore(providerPerformanceScoreList);
			performanceEvaluationService.updateFirmPerformanceScore(firmPerformanceScoreList);
			
		}catch (Exception e) {
			logger.error("Exception occured:::"+e.getMessage());
		}
		return 1;
	}
	
	/*
	 * The method creates a Map where the key is provider id and value is another Map where key 
	 * is buyer id and value is performance entry(object of PerformanceVO)
	 */
	
	private void createProviderMap(List<PerformanceVO> criteriaValueList, HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaMap){
		for(PerformanceVO criteriaValueForBuyer : criteriaValueList){
			Integer providerId = criteriaValueForBuyer.getResourceId();
			if(criteriaMap.containsKey(providerId)){
				HashMap<Integer, PerformanceVO> buyerMap = criteriaMap.get(providerId);
				Integer buyerId = criteriaValueForBuyer.getBuyerId();
				buyerMap.put(buyerId, criteriaValueForBuyer);
			}else{
				Integer buyerId = criteriaValueForBuyer.getBuyerId();
				HashMap<Integer, PerformanceVO> buyerMap = new HashMap<Integer, PerformanceVO>();
				buyerMap.put(buyerId, criteriaValueForBuyer);
				criteriaMap.put(providerId, buyerMap);
			}
			
		}
	}
	/*
	 * 
	 */
	private List<PerformanceVO> calculateCriteriaValues(
			HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaMap,
			HashMap<Integer, HashMap<Integer, PerformanceVO>> countMap, HashMap<Integer, HashMap<Integer, PerformanceVO>> firmPerformanceMap,
			int criteriaId) {
		List<PerformanceVO> performanceVOList = new ArrayList<PerformanceVO>();
		try {
			
			//for released rate
			if(4 == criteriaId){
				for(Entry<Integer, HashMap<Integer, PerformanceVO>> criteriaEntry : criteriaMap
						.entrySet()) {
					HashMap<Integer, PerformanceVO> buyerMap = new HashMap<Integer, PerformanceVO>();
					Integer providerId = criteriaEntry.getKey();
					
					//get the criteria buyers map
					HashMap<Integer, PerformanceVO> criteriaBuyerMap = criteriaMap.get(providerId);
					
					//if the provider does not have accepted orders
					if(!countMap.containsKey(providerId)){	
						
						//create map for all buyers
						for(Entry<Integer, PerformanceVO> buyer : criteriaBuyerMap.entrySet()){
							PerformanceVO vo = new PerformanceVO();
							vo.setPerfCountAll(0.0);
							vo.setPerfValueAll(0.0);
							vo.setNinetyPerfVal(0.00);
							vo.setNinetyPerfCount(0.00);
							vo.setResourceId(providerId);
							vo.setBuyerId(buyer.getKey());
							vo.setVendorId(buyer.getValue().getVendorId());
							buyerMap.put(buyer.getKey(), vo);
						}
						countMap.put(providerId, buyerMap);
					}
					else{
					
						//get the accepted buyers map
						HashMap<Integer, PerformanceVO> countBuyerMap = countMap.get(providerId);
						
						for(Entry<Integer, PerformanceVO> buyer : criteriaBuyerMap.entrySet()){
							
							//if the provider does not have accepted orders for a buyer
							if(!countBuyerMap.containsKey(buyer.getKey())){
								PerformanceVO vo = new PerformanceVO();
								vo.setPerfCountAll(0.0);
								vo.setPerfValueAll(0.0);
								vo.setNinetyPerfVal(0.00);
								vo.setNinetyPerfCount(0.00);
								vo.setResourceId(providerId);
								vo.setBuyerId(buyer.getKey());
								vo.setVendorId(buyer.getValue().getVendorId());
								//add the buyer in the count buyer map
								countBuyerMap.put(buyer.getKey(), vo);
							}
						}
						countMap.put(providerId, countBuyerMap);
					}
				}
			}
			
			
			
			for (Entry<Integer, HashMap<Integer, PerformanceVO>> criteriaEntry : countMap
					.entrySet()) {
				Integer providerId = criteriaEntry.getKey();
				HashMap<Integer, PerformanceVO> buyerCriteriaMap = criteriaEntry.getValue();
				Double providerValueLifetime = 0.0;
				Double providerCountLifeTime = 0.0;
				Double providerValueNinety = 0.0;
				Double providerCountNinety = 0.0;
				Integer vendorId = 0;
				for (Entry<Integer, PerformanceVO> buyerCriteriaMapEntry : buyerCriteriaMap
						.entrySet()) {
					Integer buyerId = buyerCriteriaMapEntry.getKey();
					PerformanceVO performanceVOCriteria = buyerCriteriaMap.get(buyerId);
					vendorId = performanceVOCriteria.getVendorId();
					PerformanceVO vo = new PerformanceVO();
					if (!criteriaMap.containsKey(providerId)) {
						vo.setBuyerId(buyerId);
						vo.setResourceId(providerId);
						vo.setVendorId(performanceVOCriteria.getVendorId());
						if(criteriaId == 4 ||criteriaId == 5||criteriaId == 6){
							vo.setPerfValue(100.0);
						}else{
							vo.setPerfValue(0.00);
						}
						vo.setPerfCountAll(performanceVOCriteria.getPerfCountAll());
						vo.setPerfValueAll(0.0);
						vo.setNinetyPerfCount(performanceVOCriteria.getNinetyPerfCount());
						vo.setNinetyPerfVal(0.0);
						if((criteriaId == 4 ||criteriaId == 5||criteriaId == 6) &&( null!=performanceVOCriteria.getNinetyPerfCount()
								&& performanceVOCriteria.getNinetyPerfCount()>0)){
							vo.setPerfNinetyValue(100.0);
						}else{
							vo.setPerfNinetyValue(0.0);
						}
						vo.setPerfCriteriaId(criteriaId);
						vo.setPerfCriteriaScope("SINGLE");
						providerCountNinety = providerCountNinety + (null==performanceVOCriteria.getNinetyPerfCount()?0.0:performanceVOCriteria.getNinetyPerfCount());
						providerCountLifeTime = providerCountLifeTime + (null==performanceVOCriteria.getPerfCountAll()?0.0:performanceVOCriteria.getPerfCountAll());
					}else {
						HashMap<Integer, PerformanceVO> buyerMap = criteriaMap
								.get(providerId);
						if (buyerMap.containsKey(buyerId)) {
							PerformanceVO performanceVO = buyerMap
									.get(buyerId);
							Double perfValue = 0.0;
							if(null!=performanceVOCriteria.getPerfCountAll() && performanceVOCriteria.getPerfCountAll().intValue()!=0){
								if(criteriaId == 4){
									perfValue = 100.00*((performanceVOCriteria.getPerfCountAll() + ( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())) -( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll()))/
											(performanceVOCriteria.getPerfCountAll() + ( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll()));
								}else if(criteriaId == 5||criteriaId == 6){
										perfValue = 100.00*(performanceVOCriteria.getPerfCountAll() -( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll()))/
												(performanceVOCriteria.getPerfCountAll());
								}else if(criteriaId==1){
									perfValue = 20.00*(null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())/
											(null==performanceVOCriteria.getPerfCountAll()?0:performanceVOCriteria.getPerfCountAll());
								}else if(criteriaId==7){
									perfValue = 1.00*(null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())/
											(null==performanceVOCriteria.getPerfCountAll()?0:performanceVOCriteria.getPerfCountAll());
								}else{
									perfValue = 100.00*(null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())/
											(null==performanceVOCriteria.getPerfCountAll()?0:performanceVOCriteria.getPerfCountAll());
								}
							}
							providerValueLifetime = providerValueLifetime + (null==performanceVO.getPerfValueAll()?0.0:performanceVO.getPerfValueAll());
							providerCountLifeTime = providerCountLifeTime + (null==performanceVOCriteria.getPerfCountAll()?0:performanceVOCriteria.getPerfCountAll());
							Double NinetyPerfValue = 0.0;
							if(null!=performanceVOCriteria.getNinetyPerfCount() && performanceVOCriteria.getNinetyPerfCount().intValue()!=0){
								if(criteriaId == 4){
									NinetyPerfValue = 100.0*((performanceVOCriteria.getNinetyPerfCount() + ( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())) -( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()))/
											(performanceVOCriteria.getNinetyPerfCount()  + ( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()));
								}else if(criteriaId == 5||criteriaId == 6){
									NinetyPerfValue = 100.0*(performanceVOCriteria.getNinetyPerfCount() -( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()))/
											(performanceVOCriteria.getNinetyPerfCount());
								}else if(criteriaId==1){
									NinetyPerfValue = 20.0*(null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())/
											(null==performanceVOCriteria.getNinetyPerfCount()?0:performanceVOCriteria.getNinetyPerfCount());
								}else if(criteriaId==7){
									NinetyPerfValue =1.0*(null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())/
											(null==performanceVOCriteria.getNinetyPerfCount()?0:performanceVOCriteria.getNinetyPerfCount());
								}else{
									NinetyPerfValue =100.0* (null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())/
											(null==performanceVOCriteria.getNinetyPerfCount()?0:performanceVOCriteria.getNinetyPerfCount());
								}
							}
							providerValueNinety = providerValueNinety + (null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal());
							providerCountNinety = providerCountNinety + (null==performanceVOCriteria.getNinetyPerfCount()?0:performanceVOCriteria.getNinetyPerfCount());
							vo.setBuyerId(buyerId);
							vo.setVendorId(performanceVO.getVendorId());
							vo.setResourceId(providerId);
							vo.setPerfValue(perfValue);
							vo.setPerfNinetyValue(NinetyPerfValue);
							vo.setPerfCriteriaId(criteriaId);
							vo.setPerfCriteriaScope("SINGLE");
							vo.setPerfCountAll(performanceVOCriteria.getPerfCountAll());
							vo.setPerfValueAll(performanceVO.getPerfValueAll());
							vo.setNinetyPerfCount(performanceVOCriteria.getNinetyPerfCount());
							vo.setNinetyPerfVal(performanceVO.getNinetyPerfVal());
						}else {
							vo.setBuyerId(buyerId);
							vo.setResourceId(providerId);
							vo.setVendorId(performanceVOCriteria
									.getVendorId());
							if(criteriaId == 4 ||criteriaId == 5||criteriaId == 6){
								vo.setPerfValue(100.0);
							}
							if((criteriaId == 4 ||criteriaId == 5||criteriaId == 6) &&( null!=performanceVOCriteria.getNinetyPerfCount()
									&& performanceVOCriteria.getNinetyPerfCount()>0)){
								vo.setPerfNinetyValue(100.0);
							}else{
								vo.setPerfNinetyValue(0.0);
							}
							vo.setPerfCriteriaId(criteriaId);
							vo.setPerfCriteriaScope("SINGLE");
							vo.setPerfCountAll(performanceVOCriteria.getPerfCountAll());
							vo.setPerfValueAll(0.0);
							vo.setNinetyPerfCount(performanceVOCriteria.getNinetyPerfCount());
							vo.setNinetyPerfVal(0.0);
							providerCountNinety = providerCountNinety + (null==performanceVOCriteria.getNinetyPerfCount()?0:performanceVOCriteria.getNinetyPerfCount());
							providerCountLifeTime = providerCountLifeTime + (null==performanceVOCriteria.getPerfCountAll()?0:performanceVOCriteria.getPerfCountAll());
						}
						
					}
					if(firmPerformanceMap.containsKey(vendorId)){
						HashMap<Integer, PerformanceVO>  vendorBuyerMap = firmPerformanceMap.get(vendorId);
						if(vendorBuyerMap.containsKey(buyerId)){
							PerformanceVO performanceVOFirm = vendorBuyerMap.get(buyerId);
							performanceVOFirm.setPerfValueAll(null==performanceVOFirm.getPerfValueAll()?
									new Integer(0):performanceVOFirm.getPerfValueAll()+(null==vo.getPerfValueAll()?
											new Integer(0):vo.getPerfValueAll()));
							performanceVOFirm.setPerfCountAll(null==performanceVOFirm.getPerfCountAll()?
									new Integer(0):performanceVOFirm.getPerfCountAll()+(null==vo.getPerfCountAll()?
											new Integer(0):vo.getPerfCountAll()));
							performanceVOFirm.setNinetyPerfVal(null==performanceVOFirm.getNinetyPerfVal()?
									new Integer(0):performanceVOFirm.getNinetyPerfVal()+(null==vo.getNinetyPerfVal()?
											new Integer(0):vo.getNinetyPerfVal()));
							performanceVOFirm.setNinetyPerfCount(null==performanceVOFirm.getNinetyPerfCount()?
									new Integer(0):performanceVOFirm.getNinetyPerfCount()+(null==vo.getNinetyPerfCount()?
											new Integer(0):vo.getNinetyPerfCount()));
							performanceVOFirm.setPerfCriteriaId(criteriaId);
							vendorBuyerMap.put(buyerId, performanceVOFirm);
							firmPerformanceMap.put(vendorId, vendorBuyerMap);
						}else{
							vo.setPerfCriteriaId(criteriaId);
							vendorBuyerMap.put(buyerId, vo);
							firmPerformanceMap.put(vendorId, vendorBuyerMap);
						}
					}else{
						HashMap<Integer, PerformanceVO>  vendorBuyerMap = new HashMap<Integer, PerformanceVO>();
						vo.setPerfCriteriaId(criteriaId);
						vendorBuyerMap.put(buyerId, vo);
						firmPerformanceMap.put(vendorId, vendorBuyerMap);
					}
					performanceVOList.add(vo);
				}
				Double perfValue = 0.0;
				if(null!=providerCountLifeTime && providerCountLifeTime.intValue()!=0){
					if(criteriaId == 4){
						perfValue = 100.0*((providerCountLifeTime + ( null==providerValueLifetime?0:providerValueLifetime)) -( null==providerValueLifetime?0:providerValueLifetime))/
								(providerCountLifeTime + ( null==providerValueLifetime?0:providerValueLifetime));
					}else if(criteriaId == 5||criteriaId == 6){
						perfValue = 100.0*(providerCountLifeTime -( null==providerValueLifetime?0:providerValueLifetime))/
								providerCountLifeTime;
					}else if(criteriaId==1){
						perfValue = 20.0*(null==providerValueLifetime?0:providerValueLifetime)/
								providerCountLifeTime;
					}else if(criteriaId==7){
						perfValue = 1.0*(null==providerValueLifetime?0:providerValueLifetime)/
								providerCountLifeTime;
					}else{
						perfValue = 100.0*(null==providerValueLifetime?0:providerValueLifetime)/
								providerCountLifeTime;
					}
				}
				Double perfValueNinety = 0.0;
				if(null!=providerCountNinety && providerCountNinety.intValue()!=0){
					if(criteriaId == 4){
						perfValueNinety = 100.0*((providerCountNinety + ( null==providerValueNinety?0:providerValueNinety))-( null==providerValueNinety?0:providerValueNinety))/
								(providerCountNinety + ( null==providerValueNinety?0:providerValueNinety));
					}else if(criteriaId == 5||criteriaId == 6){
						perfValueNinety = 100.0*(providerCountNinety -( null==providerValueNinety?0:providerValueNinety))/
								providerCountNinety;
					}else if(criteriaId==1){
						perfValueNinety = 20.0*(null==providerValueNinety?0:providerValueNinety)/
								providerCountNinety;
					}else if(criteriaId==7){
						perfValueNinety = 1.0*(null==providerValueNinety?0:providerValueNinety)/
								providerCountNinety;
					}else{
						perfValueNinety = 100.0*(null==providerValueNinety?0:providerValueNinety)/
								providerCountNinety;
					}
				}
				PerformanceVO vo = new PerformanceVO();
				vo.setBuyerId(-1);
				vo.setResourceId(providerId);
				vo.setVendorId(vendorId);
				vo.setPerfValue(perfValue);
				vo.setPerfNinetyValue(perfValueNinety);
				vo.setPerfCriteriaId(criteriaId);
				vo.setPerfCriteriaScope("ALL");
				performanceVOList.add(vo);
			}
		
		} catch (Exception e) {

		}
		return performanceVOList;
	}
	private void calculateArchivalValues(HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaMap,
			HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaArchiveMap ){
		try{
			if(!criteriaMap.isEmpty()){
				for (Entry<Integer, HashMap<Integer, PerformanceVO>> criteriaEntry : criteriaArchiveMap.entrySet()) {
					Integer providerId = criteriaEntry.getKey();
					HashMap<Integer, PerformanceVO> buyerArchiveMap = criteriaEntry.getValue();
					if(criteriaMap.containsKey(providerId)){
						HashMap<Integer, PerformanceVO> buyerMap = criteriaMap.get(providerId);
						for (Entry<Integer, PerformanceVO> buyerArchiveMapEntry : buyerArchiveMap.entrySet()) {
							Integer buyerId = buyerArchiveMapEntry.getKey();
							if(buyerMap.containsKey(buyerId)){
								PerformanceVO performanceVO = buyerMap.get(buyerId);
								PerformanceVO performanceVOArchive = buyerArchiveMap.get(buyerId);
								performanceVO.setPerfValueAll(null==performanceVO.getPerfValueAll()?
										new Integer(0):performanceVO.getPerfValueAll()+(null==performanceVOArchive.getPerfValueAll()?
												new Integer(0):performanceVOArchive.getPerfValueAll()));
								performanceVO.setPerfCountAll(null==performanceVO.getPerfCountAll()?
										new Integer(0):performanceVO.getPerfCountAll()+(null==performanceVOArchive.getPerfCountAll()?
												new Integer(0):performanceVOArchive.getPerfCountAll()));
								performanceVO.setNinetyPerfVal(null==performanceVO.getNinetyPerfVal()?
										new Integer(0):performanceVO.getNinetyPerfVal()+(null==performanceVOArchive.getNinetyPerfVal()?
												new Integer(0):performanceVOArchive.getNinetyPerfVal()));
								performanceVO.setNinetyPerfCount(null==performanceVO.getNinetyPerfCount()?
										new Integer(0):performanceVO.getNinetyPerfCount()+(null==performanceVOArchive.getNinetyPerfCount()?
												new Integer(0):performanceVOArchive.getNinetyPerfCount()));
								buyerMap.put(buyerId, performanceVO);
							}else{
								buyerMap.put(buyerId, buyerArchiveMap.get(buyerId));
							}
						}
						criteriaMap.put(providerId, buyerMap);
					}else{
						criteriaMap.put(providerId, buyerArchiveMap);
					}
				}
			}else if(!criteriaArchiveMap.isEmpty()){
				criteriaMap.putAll(criteriaArchiveMap);
			}
		}catch(Exception e){
			
		}
	}
	private void calculateValuesForAcceptedFirm(HashMap<Integer, HashMap<Integer, PerformanceVO>> firmPerformanceMap, 
			List<PerformanceVO> performanceVOList){
		for(PerformanceVO firmVO:performanceVOList){
			Integer vendor = firmVO.getVendorId();
			Integer buyer = firmVO.getBuyerId();
			if(firmPerformanceMap.containsKey(vendor)){
				HashMap<Integer, PerformanceVO> buyerMap = firmPerformanceMap.get(vendor);
				if(buyerMap.containsKey(buyer)){
					PerformanceVO vo = buyerMap.get(buyer);
					vo.setPerfCountAll(vo.getPerfCountAll()+firmVO.getPerfCountAll());
					vo.setNinetyPerfCount(vo.getNinetyPerfCount()+firmVO.getNinetyPerfCount());
					buyerMap.put(buyer, vo);
				}else{
					buyerMap.put(buyer, firmVO);
				}
			}else{
				HashMap<Integer, PerformanceVO> buyerMap = new HashMap<Integer, PerformanceVO> ();
				buyerMap.put(buyer, firmVO);
				firmPerformanceMap.put(vendor, buyerMap);
			}
		}
		
	}
	private List<PerformanceVO> calculatePerformanceValues(HashMap<Integer, HashMap<Integer, PerformanceVO>> criteriaMap,
			HashMap<Integer, HashMap<Integer, PerformanceVO>> firmPerformanceMap, boolean calculateFirmValues,  int criteriaId){
		List<PerformanceVO> performanceVOList = new ArrayList<PerformanceVO>();
	
		//Iterate through provider id keys
		for (Entry<Integer, HashMap<Integer, PerformanceVO>> criteriaEntryVal : criteriaMap.entrySet()) {
			HashMap<Integer, PerformanceVO> buyerMap = criteriaEntryVal.getValue();
			Integer providerId = 0;
			if(calculateFirmValues){
				providerId = null;
			}else{
				providerId = criteriaEntryVal.getKey();
			}
			List<PerformanceVO> performanceVOEntries = new ArrayList<PerformanceVO>();
			performanceVOEntries = calculatePerformanceForMap( buyerMap, firmPerformanceMap, calculateFirmValues, criteriaId, providerId);
			performanceVOList.addAll(performanceVOEntries);
		}
		return performanceVOList;
		
	}
	private List<PerformanceVO> calculatePerformanceForMap(HashMap<Integer, PerformanceVO> buyerMap,
			HashMap<Integer, HashMap<Integer, PerformanceVO>> firmPerformanceMap, boolean calculateFirmValues,
			Integer criteriaId, Integer providerId){
		List<PerformanceVO> performanceVOList = new ArrayList<PerformanceVO>();
		Double perfValue = 0.0;
		Double providerValueLifetime = 0.0;
		Double providerCountLifeTime = 0.0;
		Double providerValueNinety = 0.0;
		Double providerCountNinety = 0.0;
		Integer vendorId = 0;
		for (Entry<Integer,PerformanceVO> buyerMapEntryVal : buyerMap.entrySet()) {
			Integer buyerId = buyerMapEntryVal.getKey();
			PerformanceVO performanceVO = buyerMapEntryVal.getValue();
			vendorId = performanceVO.getVendorId();
			PerformanceVO vo = new PerformanceVO();
			if(!calculateFirmValues){
				if(firmPerformanceMap.containsKey(vendorId)){
					HashMap<Integer, PerformanceVO>  vendorBuyerMap = firmPerformanceMap.get(vendorId);
					if(vendorBuyerMap.containsKey(buyerId)){
						PerformanceVO performanceVOFirm = vendorBuyerMap.get(buyerId);
						performanceVOFirm.setPerfValueAll(null==performanceVOFirm.getPerfValueAll()?
								new Integer(0):performanceVOFirm.getPerfValueAll()+(null==performanceVO.getPerfValueAll()?
										new Integer(0):performanceVO.getPerfValueAll()));
						performanceVOFirm.setPerfCountAll(null==performanceVOFirm.getPerfCountAll()?
								new Integer(0):performanceVOFirm.getPerfCountAll()+(null==performanceVO.getPerfCountAll()?
										new Integer(0):performanceVO.getPerfCountAll()));
						performanceVOFirm.setNinetyPerfVal(null==performanceVOFirm.getNinetyPerfVal()?
								new Integer(0):performanceVOFirm.getNinetyPerfVal()+(null==performanceVO.getNinetyPerfVal()?
										new Integer(0):performanceVO.getNinetyPerfVal()));
						performanceVOFirm.setNinetyPerfCount(null==performanceVOFirm.getNinetyPerfCount()?
								new Integer(0):performanceVOFirm.getNinetyPerfCount()+(null==performanceVO.getNinetyPerfCount()?
										new Integer(0):performanceVO.getNinetyPerfCount()));
						performanceVOFirm.setPerfCriteriaId(criteriaId);
						vendorBuyerMap.put(buyerId, performanceVOFirm);
						firmPerformanceMap.put(vendorId, vendorBuyerMap);
					}else{
						performanceVO.setPerfCriteriaId(criteriaId);
						vendorBuyerMap.put(buyerId, performanceVO);
						firmPerformanceMap.put(vendorId, vendorBuyerMap);
					}
				}else{
					HashMap<Integer, PerformanceVO>  vendorBuyerMap = new HashMap<Integer, PerformanceVO>();
					performanceVO.setPerfCriteriaId(criteriaId);
					vendorBuyerMap.put(buyerId, performanceVO);
					firmPerformanceMap.put(vendorId, vendorBuyerMap);
				}
				
			}
			perfValue = 0.0;
			if(null!=performanceVO.getPerfCountAll() && performanceVO.getPerfCountAll().intValue()!=0){
				if(criteriaId == 4){
					perfValue = 100.0*((performanceVO.getPerfCountAll()+ ( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())) -( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll()))/
							(performanceVO.getPerfCountAll() + ( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll()));
				}else if(criteriaId == 5||criteriaId == 6){
					perfValue = 100.0*(performanceVO.getPerfCountAll() -( null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll()))/
							(performanceVO.getPerfCountAll());
				}else if(criteriaId==1){
					perfValue = 20.0*(null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())/
							(null==performanceVO.getPerfCountAll()?0:performanceVO.getPerfCountAll());
				}else if(criteriaId==7){
					perfValue = 1.0*(null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())/
							(null==performanceVO.getPerfCountAll()?0:performanceVO.getPerfCountAll());
				}else{
					perfValue = 100.0*(null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll())/
							(null==performanceVO.getPerfCountAll()?0:performanceVO.getPerfCountAll());
				}
			}
			
			providerValueLifetime = providerValueLifetime + (null==performanceVO.getPerfValueAll()?0:performanceVO.getPerfValueAll());
			providerCountLifeTime = providerCountLifeTime + (null==performanceVO.getPerfCountAll()?0:performanceVO.getPerfCountAll());
			vo.setPerfValue(perfValue);
			vo.setPerfCriteriaScope("SINGLE");
			vo.setPerfCriteriaId(criteriaId);
			
			perfValue = 0.0;
			if(null!=performanceVO.getNinetyPerfCount() && performanceVO.getNinetyPerfCount().intValue()!=0){
				if(criteriaId == 4){
					perfValue = 100.0*((performanceVO.getNinetyPerfCount() + ( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()))-( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()))/
							(performanceVO.getNinetyPerfCount() + ( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()));
				}else if(criteriaId == 5||criteriaId == 6){
					perfValue = 100.0*(performanceVO.getNinetyPerfCount() -( null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal()))/
							(performanceVO.getNinetyPerfCount());
				}else if(criteriaId==1){
					perfValue = 20.0*(null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())/
							(null==performanceVO.getNinetyPerfCount()?0:performanceVO.getNinetyPerfCount());
				}else if(criteriaId==7){
					perfValue = 1.0*(null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())/
							(null==performanceVO.getNinetyPerfCount()?0:performanceVO.getNinetyPerfCount());
				}else{
					perfValue = 100.0*(null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal())/
							(null==performanceVO.getNinetyPerfCount()?0:performanceVO.getNinetyPerfCount());
				}
			}
			providerValueNinety = providerValueNinety + (null==performanceVO.getNinetyPerfVal()?0:performanceVO.getNinetyPerfVal());
			providerCountNinety = providerCountNinety + (null==performanceVO.getNinetyPerfCount()?0:performanceVO.getNinetyPerfCount());
			vo.setPerfNinetyValue(perfValue);
			vo.setBuyerId(buyerId);
			vo.setResourceId(providerId);
			vo.setVendorId(vendorId);
			vo.setNinetyPerfVal(performanceVO.getNinetyPerfVal());
			vo.setNinetyPerfCount(performanceVO.getNinetyPerfCount());
			vo.setPerfValueAll(performanceVO.getPerfValueAll());
			vo.setPerfCountAll(performanceVO.getPerfCountAll());
			performanceVOList.add(vo);
		}
		if(null!=buyerMap && !buyerMap.isEmpty()){
			PerformanceVO performanceVO = new PerformanceVO();
			performanceVO.setBuyerId(-1);
			performanceVO.setResourceId(providerId);
			performanceVO.setVendorId(vendorId);
			performanceVO.setPerfCriteriaScope("ALL");
			perfValue = 0.0;
			if(null!=providerCountLifeTime && providerCountLifeTime.intValue()!=0){
				if(criteriaId == 4){
					perfValue =100.0*(( providerCountLifeTime + ( null==providerValueLifetime?0:providerValueLifetime))-( null==providerValueLifetime?0:providerValueLifetime))/
							(providerCountLifeTime + ( null==providerValueLifetime?0:providerValueLifetime));
				}else if(criteriaId == 5||criteriaId == 6){
					perfValue =100.0*( providerCountLifeTime -( null==providerValueLifetime?0:providerValueLifetime))/
							providerCountLifeTime;
				}else if(criteriaId==1){
					perfValue = 20.0*(null==providerValueLifetime?0:providerValueLifetime)/
							providerCountLifeTime;
				}else if(criteriaId==7){
					perfValue = 1.0*(null==providerValueLifetime?0:providerValueLifetime)/
							providerCountLifeTime;
				}else{
					perfValue = 100.0*(null==providerValueLifetime?0:providerValueLifetime)/
							providerCountLifeTime;
				}
			}
			performanceVO.setPerfValue(perfValue);
			performanceVO.setPerfCriteriaId(criteriaId);
			perfValue = 0.0;
			if(null!=providerCountNinety && providerCountNinety.intValue()!=0){
				if(criteriaId == 4){
					perfValue = 100.0*((providerCountNinety + ( null==providerValueNinety?0:providerValueNinety))-( null==providerValueNinety?0:providerValueNinety))/
							(providerCountNinety + ( null==providerValueNinety?0:providerValueNinety));
				}else if(criteriaId == 5||criteriaId == 6){
					perfValue = 100.0*(providerCountNinety -( null==providerValueNinety?0:providerValueNinety))/
							providerCountNinety;
				}else if(criteriaId==1){
					perfValue = 20.0*(null==providerValueNinety?0:providerValueNinety)/
							providerCountNinety;
				}else if(criteriaId==7){
					perfValue = 1.0*(null==providerValueNinety?0:providerValueNinety)/
							providerCountNinety;
				}else{
					perfValue = 100.0*(null==providerValueNinety?0:providerValueNinety)/
							providerCountNinety;
				}
			}
			performanceVO.setPerfNinetyValue(perfValue);
			performanceVOList.add(performanceVO);
		}
		return performanceVOList;
	}
	@Override
	public void setArgs(String[] args) {
		// do nothing
	}
	public IPerformanceEvaluationFetchService getPerformanceEvaluationArchiveService() {
		return performanceEvaluationArchiveService;
	}
	public void setPerformanceEvaluationArchiveService(
			IPerformanceEvaluationFetchService performanceEvaluationArchiveService) {
		this.performanceEvaluationArchiveService = performanceEvaluationArchiveService;
	}
	public IPerformanceEvaluationService getPerformanceEvaluationService() {
		return performanceEvaluationService;
	}
	public void setPerformanceEvaluationService(
			IPerformanceEvaluationService performanceEvaluationService) {
		this.performanceEvaluationService = performanceEvaluationService;
	}
}
