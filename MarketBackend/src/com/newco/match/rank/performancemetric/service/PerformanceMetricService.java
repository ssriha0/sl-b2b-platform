package com.newco.match.rank.performancemetric.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.vo.performancematric.VendorPerformanceMatricVo;
import com.newco.marketplace.dto.vo.performancematric.VendorRankingBuyerVo;
import com.newco.marketplace.dto.vo.performancematric.VendorWeightedScoreVo;
import com.newco.marketplace.persistence.match.rank.performancemetric.dao.IPerformanceMetricDao;
import com.newco.match.rank.performancemetric.enums.RankMatchingPerformanceMatricEnum;

public class PerformanceMetricService implements IPerformanceMetricService {
	
	private static final String ADDED_BY_BATCH_JOB_STRING = "BATCH_JOB";
	
	private IPerformanceMetricDao performanceMetricDaoImpl;
	
	public IPerformanceMetricDao getPerformanceMetricDaoImpl() {
		return performanceMetricDaoImpl;
	}

	public void setPerformanceMetricDaoImpl(IPerformanceMetricDao performanceMetricDaoImpl) {
		this.performanceMetricDaoImpl = performanceMetricDaoImpl;
	}
	
	// ----------------------------------------------------------------------------------//
	@Transactional
	public void testDaoImpl() {
		System.out.println("START: PerformanceMetricService...test()");
		getPerformanceMetricDaoImpl().test();
		System.out.println("END: PerformanceMatricService...test()");
	}
	
	// ----------------------------------------------------------------------------------//
	
	// start: SPN
	private List<Integer> fetchSpnIdFromTemplateSkuForBuyerSpecific(Integer buyerId) {
		return getPerformanceMetricDaoImpl().fetchSpnIdFromTemplateSkuForBuyerSpecific(buyerId);
	}
	
	private List<Integer> fetchDistinctFirmIdsFromSpnIdList(Integer buyerId, List<Integer> spnIdList) {
		return getPerformanceMetricDaoImpl().fetchDistinctFirmIdsFromSpnIdList(buyerId, spnIdList);
	}
	// end: SPN
	
	// fetch vendor ranking buyer = master 
	public List<VendorRankingBuyerVo> getVendorRankingBuyerVoPerformanceAttributes(Integer buyerId) {
		return getPerformanceMetricDaoImpl().getPerformanceAttributesAtFirmLevel(buyerId);
	}
	
	// start: calculation
	private Double formulaeToCalculateAppointmentCommitment(Double weightage, Double totalRescheduleCount, Double totalClosedOrdersCount) {
		Double formulaeScore = (double) ((totalRescheduleCount / totalClosedOrdersCount) * 100);
		return calculateFinalWeightedScoreWithRoundOff(formulaeScore, weightage);
	}
	
	private Double formulaeToCalculateWeightedScoreForCustomerRatingCsat(Double weightage, Double avgCsatRating) {
		// avgCsatRating * 100 / 5 = % = Points => (avgCsatRating * 20)
		Double formulaeScore = (double) (avgCsatRating * 20);
		return calculateFinalWeightedScoreWithRoundOff(formulaeScore, weightage);
	}
	
	private Double formulaeToCalculateTimeOnSiteArrival(Double weightage, Double timeOnSiteCountSum, Double totalClosedOrdersCount) {
		// Total closed orders=80; Arrived in window=60 => (60 / 80 * 100) = 75% = 75points
		Double formulaeScore = (double) ((timeOnSiteCountSum / totalClosedOrdersCount) * 100);
		return calculateFinalWeightedScoreWithRoundOff(formulaeScore, weightage);
	}
	
	private Double calculateFinalWeightedScoreWithRoundOff(Double formulaeScore, Double weightage) {
		Double weightedScore = (double) (formulaeScore * (weightage / 100));
		// TODO: need to round-of the value
		return weightedScore;
	}
	// end: calculation
	
	// -------------- START: TIME_ON_SITE_ARRIVAL --------------------
	// case: TIME_ON_SITE_ARRIVAL
	private List<VendorWeightedScoreVo> timeOnSiteSwitchCase(VendorRankingBuyerVo perfAttr, List<Integer> spnFilteredFirmIdList) {
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = null;
		
		// 1. fetch reschedule by prodivder
		List<VendorPerformanceMatricVo> vendorPerfMVoTimeOnSiteList = fetchTimeOnSiteCountSum(perfAttr.getBuyerId());
		
		// 1.A filter it with spn firm id list
		vendorPerfMVoTimeOnSiteList = filterFirmlistWithSPNFirmList(spnFilteredFirmIdList, vendorPerfMVoTimeOnSiteList);
		
		System.out.println("TIME_ON_SITE timeOnSiteSwitchCase.size(): " + vendorPerfMVoTimeOnSiteList.size());
		// print logic
		for (VendorPerformanceMatricVo vendorPerformanceMatricVo : vendorPerfMVoTimeOnSiteList) {
			System.out.println("Before calculation part = " + vendorPerformanceMatricVo.getVendorId());
		}
		
		if (null != vendorPerfMVoTimeOnSiteList) {
			if(vendorPerfMVoTimeOnSiteList.size() > 0) {
				
				// 2.fetch total completed orders by provider
				List<VendorPerformanceMatricVo> vendorPerfMVoTotalClosedOrdersList = fetchTotalClosedOrders(vendorPerfMVoTimeOnSiteList);
				
				// 3. calculate weighted score
				if (null != vendorPerfMVoTotalClosedOrdersList) {
					if(vendorPerfMVoTotalClosedOrdersList.size() > 0) {
						
						vendorWeightedScoreVoList = calculateWeightedScoreForTimeOnSiteArrival(
								perfAttr.getWeightage(),
								vendorPerfMVoTimeOnSiteList,
								vendorPerfMVoTotalClosedOrdersList);
					}
				}
			}
		}
		
		return vendorWeightedScoreVoList;

	}
	// -------------- END: TIME_ON_SITE_ARRIVAL --------------------
	
	// case APPOINTMENT_COMMITMENT
	private List<VendorWeightedScoreVo> appointmentCommitmentSwitchCase(VendorRankingBuyerVo perfAttr, List<Integer> spnFilteredFirmIdList) {
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = null;
		
		// 1. fetch reschedule by prodivder
		List<VendorPerformanceMatricVo> vendorPerfMVoRescheduleByProList = fetchAppointmentCommitmentRescheduleByPro(perfAttr.getBuyerId());
		
		// 1.A filter it with spn firm id list
		vendorPerfMVoRescheduleByProList = filterFirmlistWithSPNFirmList(spnFilteredFirmIdList, vendorPerfMVoRescheduleByProList);

		if(null != vendorPerfMVoRescheduleByProList) {
			if(vendorPerfMVoRescheduleByProList.size() > 0) {
				
				// 2.fetch total completed orders by provider
				List<VendorPerformanceMatricVo> VendorPerfMVoTotalClosedOrdersList = fetchTotalClosedOrders(vendorPerfMVoRescheduleByProList);
				
				// 3. calculate weighted score
				if(null != VendorPerfMVoTotalClosedOrdersList) {
					if (VendorPerfMVoTotalClosedOrdersList.size() > 0) {
						
						vendorWeightedScoreVoList = calculateWeightedScoreForAppointmentCommitment(
								perfAttr.getWeightage(),
								vendorPerfMVoRescheduleByProList,
								VendorPerfMVoTotalClosedOrdersList);
					}
				}
			}
		}
		
		return vendorWeightedScoreVoList;

	}
	
	// case CUSTOMER_RATING_CSAT
	private List<VendorWeightedScoreVo> customerRatingCsatSwitchCase(VendorRankingBuyerVo perfAttr, List<Integer> spnFilteredFirmIdList) {
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = null;
		
		// 1. fetch AVG CUSTOMER RATING CSAT.
		List<VendorPerformanceMatricVo> vendorPerfMVoCustomerRatingCsatList = fetchCustomerRatingCsatAvg(perfAttr.getBuyerId());
		
		// 1.A filter it with spn firm id list
		vendorPerfMVoCustomerRatingCsatList = filterFirmlistWithSPNFirmList(spnFilteredFirmIdList, vendorPerfMVoCustomerRatingCsatList);
		
		if (null != vendorPerfMVoCustomerRatingCsatList) {
			if (vendorPerfMVoCustomerRatingCsatList.size() > 0) {
				// 2. calculate weighted score
				vendorWeightedScoreVoList = calculateWeightedScoreForCustomerRatingCsat(perfAttr.getWeightage(), vendorPerfMVoCustomerRatingCsatList);
			}
		}
		
		return vendorWeightedScoreVoList;
	}
	
	// -------- START: APPOINTMENT_COMMITMENT--------------------
	private List<VendorPerformanceMatricVo> fetchAppointmentCommitmentRescheduleByPro(Integer buyerId) {
		return getPerformanceMetricDaoImpl().fetchAppointmentCommitmentRescheduleByPro(buyerId);
	}
	
	private List<VendorPerformanceMatricVo> fetchTotalClosedOrders(List<VendorPerformanceMatricVo> vendorPerfMVoRescheduleByProList) {
		return getPerformanceMetricDaoImpl().fetchTotalClosedOrders(vendorPerfMVoRescheduleByProList);
	}
	
	
	private List<VendorWeightedScoreVo> calculateWeightedScoreForAppointmentCommitment(
			Double weightage, List<VendorPerformanceMatricVo> vendorPerfMVoRescheduleByProList,
			List<VendorPerformanceMatricVo> vendorPerfMVoTotalClosedOrdersList) {
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = new ArrayList<VendorWeightedScoreVo>();
		
		// total closed orders list
		for (int totalClosedOrders = 0; totalClosedOrders < vendorPerfMVoTotalClosedOrdersList.size(); totalClosedOrders++) {
			
			// total reschedule orders list
			for (int totalReschedule = 0; totalReschedule < vendorPerfMVoRescheduleByProList.size(); totalReschedule++) {
				
				String closedOrderVendorId = (vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getVendorId() + "").trim();
				String rescheduledVendorId = (vendorPerfMVoRescheduleByProList.get(totalReschedule).getVendorId() + "").trim();
				
				if(closedOrderVendorId.equals(rescheduledVendorId)) {
					
					// calculate weighted score
					Double weightedScore = formulaeToCalculateAppointmentCommitment(
							weightage,
							vendorPerfMVoRescheduleByProList.get(totalReschedule).getTotalRescheduleCount(),
							vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getTotalClosedOrdersCount());
					
					VendorWeightedScoreVo vo = new VendorWeightedScoreVo();
					vo.setWeightedScore(weightedScore);
					vo.setBuyerId(vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getBuyerId());
					vo.setVendorId(vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getVendorId());
					
					vendorWeightedScoreVoList.add(vo);
					
				}
			}
		}

		return vendorWeightedScoreVoList;
		
	}
	
	
	// -------- END: APPOINTMENT_COMMITMENT--------------------
	
	// -------- START: CUSTOMER_RATING_CSAT--------------------
	private List<VendorPerformanceMatricVo> fetchCustomerRatingCsatAvg(Integer buyerId) {
		return getPerformanceMetricDaoImpl().fetchCustomerRatingCsatAvg(buyerId);
	}

	private List<VendorWeightedScoreVo> calculateWeightedScoreForCustomerRatingCsat(Double weightage,
			List<VendorPerformanceMatricVo> vendorPerfMVoCustomerRatingCsatList) {
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = new ArrayList<VendorWeightedScoreVo>();
		
		for (VendorPerformanceMatricVo vendorPerformanceMatricVo : vendorPerfMVoCustomerRatingCsatList) {
			Double weightedScore = formulaeToCalculateWeightedScoreForCustomerRatingCsat(weightage, vendorPerformanceMatricVo.getAvgCsatRating());
			
			VendorWeightedScoreVo vo = new VendorWeightedScoreVo();
			vo.setWeightedScore(weightedScore);
			vo.setBuyerId(vendorPerformanceMatricVo.getBuyerId());
			vo.setVendorId(vendorPerformanceMatricVo.getVendorId());
			
			vendorWeightedScoreVoList.add(vo);
		
		}
		
		return vendorWeightedScoreVoList;
	}
	

	
	// -------- END: CUSTOMER_RATING_CSAT--------------------
	
	// -------- START: TIME_ON_SITE_ARRIVAL--------------------
	private List<VendorPerformanceMatricVo> fetchTimeOnSiteCountSum(Integer buyerId) {
		return getPerformanceMetricDaoImpl().fetchTimeOnSiteCountSum(buyerId);
	}
	
	private List<VendorWeightedScoreVo> calculateWeightedScoreForTimeOnSiteArrival(
			Double weightage, List<VendorPerformanceMatricVo> vendorPerfMVoTimeOnSiteList,
			List<VendorPerformanceMatricVo> vendorPerfMVoTotalClosedOrdersList) {
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = new ArrayList<VendorWeightedScoreVo>();
		
		// total closed orders list
		for (int totalClosedOrders = 0; totalClosedOrders < vendorPerfMVoTotalClosedOrdersList.size(); totalClosedOrders++) {
			
			// total reschedule orders list
			for (int totalTimeOnSite = 0; totalTimeOnSite < vendorPerfMVoTimeOnSiteList.size(); totalTimeOnSite++) {
				
				String closedOrderVendorId = (vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getVendorId() + "").trim();
				String rescheduledVendorId = (vendorPerfMVoTimeOnSiteList.get(totalTimeOnSite).getVendorId() + "").trim();
				
				if(closedOrderVendorId.equals(rescheduledVendorId)) {
					
					// calculate weighted score
					Double weightedScore = formulaeToCalculateTimeOnSiteArrival(
							weightage,
							vendorPerfMVoTimeOnSiteList.get(totalTimeOnSite).getTimeOnSiteCountSum(),
							vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getTotalClosedOrdersCount());
					
					// bind the object
					VendorWeightedScoreVo vo = new VendorWeightedScoreVo();
					vo.setWeightedScore(weightedScore);
					vo.setVendorId(vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getVendorId());
					vo.setBuyerId(vendorPerfMVoTotalClosedOrdersList.get(totalClosedOrders).getBuyerId());
					
					vendorWeightedScoreVoList.add(vo);
				}
			}
		}

		return vendorWeightedScoreVoList;
		
	}
	
	// -------- END: TIME_ON_SITE_ARRIVAL--------------------
	
	private List<VendorPerformanceMatricVo> filterFirmlistWithSPNFirmList(
			List<Integer> spnFilteredFirmIdList,
			List<VendorPerformanceMatricVo> vendorPerfMVoList) {
		
		List<VendorPerformanceMatricVo> filteredVendorPerfMVoList = null;
		
		if (null != spnFilteredFirmIdList && null != vendorPerfMVoList) {
			if (spnFilteredFirmIdList.size() > 0 && vendorPerfMVoList.size() > 0) {
				
				// create object
				filteredVendorPerfMVoList = new ArrayList<VendorPerformanceMatricVo>();
				
				// iterate over vendorPerfMVoList
				for (VendorPerformanceMatricVo vendorPerformanceMatricVo : vendorPerfMVoList) {
					
					// check if the vendor id present in spn fetched id list
					if (spnFilteredFirmIdList.contains(vendorPerformanceMatricVo.getVendorId())) {
						System.out.println("TIME_ON_SITE filterFirmlistWithSPNFirmList: " + vendorPerformanceMatricVo.getVendorId());
						filteredVendorPerfMVoList.add(vendorPerformanceMatricVo);
					}
				}
			}
		}
		
		return filteredVendorPerfMVoList;
	}
	

	private Map<Integer, Double> calculateAndSetVendorPerformanceMetricOnWeightedScoreResult(
			final Map<String, List<VendorWeightedScoreVo>> finalMapWeightedScore) {
		
		Map<Integer, Double> consolidatedWeightedScoreByFirmIdMap = new HashMap<Integer, Double>();
		
		Double weightedScore = (double) 0;
		
		// Iterating over map
		for (String key : finalMapWeightedScore.keySet()) {

			// 1:TIME_ON_SITE_ARRIVAL:3333
			// 2:APPOINTMENT_COMMITMENT:3333
			// 4:CUSTOMER_RATING_CSAT:3333
			Integer vendorRankingBuyerId = Integer.parseInt((key.split(":")[0]).trim());
			
			List<VendorWeightedScoreVo> vendorWeightedScoreVoList = finalMapWeightedScore.get(key);
			
			// Iterating over list
			for (VendorWeightedScoreVo vendorWeightedScoreVo : vendorWeightedScoreVoList) {
				
				// set vendorRankingBuyer... Master table id
				vendorWeightedScoreVo.setVendorRankingBuyerId(vendorRankingBuyerId);
				
				if (consolidatedWeightedScoreByFirmIdMap.containsKey(vendorWeightedScoreVo.getVendorId())) {
					
					// get the aleady present value.
					weightedScore = (consolidatedWeightedScoreByFirmIdMap.get(vendorWeightedScoreVo.getVendorId()));
					
					// add it with the next value.
					weightedScore += vendorWeightedScoreVo.getWeightedScore();
					
					// add once again in map with updated value.
					consolidatedWeightedScoreByFirmIdMap.put(vendorWeightedScoreVo.getVendorId(), weightedScore);
					
				} else {
					consolidatedWeightedScoreByFirmIdMap.put(vendorWeightedScoreVo.getVendorId(), vendorWeightedScoreVo.getWeightedScore());
				}
				
			} // END: List iterating logic 
			
		} // END: Map iterating logic.
		
		return consolidatedWeightedScoreByFirmIdMap;
		
	} // END: calculateAndSaveVendorPerformanceMetricOnWeightedScoreResult()
	
	
	private Map<String, List<VendorWeightedScoreVo>> bindVendorPerfMetricIdWithFinalMapWeightedScore(
			Map<String, List<VendorWeightedScoreVo>> finalMapWeightedScore,
			Map<Integer, Integer> vendorIdPerfMetricIdMap) {
		
		// eg: finalMapWeightedScore
		// ["1:TIME_ON_SITE_ARRIVAL:3333", List<VendorWeightedScoreVo>]
		// 3333		10202	38.40
		// 3333		15897	40.00
		
		// ["2:APPOINTMENT_COMMITMENT:3333", List<VendorWeightedScoreVo>]
		// 3333		10105	300.00
		// 3333		10202	28.00
		
		// ["4:CUSTOMER_RATING_CSAT:3333", List<VendorWeightedScoreVo>]
		// 3333		10105	100.00
		// 3333		15897	100.00
		
		// //////////////////////////////////////
		// eg: vendorIdPerfMetricIdMap
		// [10202, 1]
		// [15897, 2]
		// [10105, 3]
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = null;
		Integer vendorPerformanceMetricId = null;
		
		// Iterating over map
		for (String key : finalMapWeightedScore.keySet()) {
			
			vendorWeightedScoreVoList = finalMapWeightedScore.get(key);
			
			// Iterating over list
			for (VendorWeightedScoreVo vendorWeightedScoreVo : vendorWeightedScoreVoList) {
				
				// fetch the id from vendorIdPerfMetricIdMap
				vendorPerformanceMetricId = vendorIdPerfMetricIdMap.get(vendorWeightedScoreVo.getVendorId());
				
				// set the id in main object of finalMapWeightedScore
				vendorWeightedScoreVo.setVendorPerformanceMatricId(vendorPerformanceMetricId);
			}
		}

		return finalMapWeightedScore;

	}

	// retruns map of VendorId with it's performance_metric_id
	private Map<Integer, Integer> saveOrUpdateVendorPerformanceMetrics(Integer buyerId,
			Map<Integer, Double> consolidatedWeightedScoreByFirmIdMap) {
		
		Double consolidatedWeightedScore = null;
		Integer vendorPerfMetricId = null;
		
		Map<Integer, Integer> vendorIdPerfMetricIdMap = new HashMap<Integer, Integer>();
		
		for (Integer vendorId : consolidatedWeightedScoreByFirmIdMap.keySet()) {
			
			consolidatedWeightedScore = consolidatedWeightedScoreByFirmIdMap.get(vendorId);
			
			// on update = vendorPerfMetricId = null
			// on insert = value_will_be_present
			vendorPerfMetricId = saveOrUpdateVendorPerformanceMetrics(buyerId, vendorId, consolidatedWeightedScore, ADDED_BY_BATCH_JOB_STRING);
			
			if (null != vendorPerfMetricId) {
				if (vendorPerfMetricId > 0) {
					vendorIdPerfMetricIdMap.put(vendorId, vendorPerfMetricId);
				}
			}
		}
		
		return vendorIdPerfMetricIdMap;

	} // END: saveOrUpdateVendorPerformanceMetrics

	private Integer saveOrUpdateVendorPerformanceMetrics(Integer buyerId,
			Integer vendorId, Double consolidatedWeightedScore, String modifiedBy) {
		return getPerformanceMetricDaoImpl().saveOrUpdateVendorPerformanceMetrics(buyerId, vendorId, consolidatedWeightedScore, modifiedBy);
	}
	
	private void saveUpdateBackupVendorPerformanceWeightedScore(Map<String, List<VendorWeightedScoreVo>> finalMapWeightedScore) {
		// eg: finalMapWeightedScore
		// ["1:TIME_ON_SITE_ARRIVAL:3333", List<VendorWeightedScoreVo>]
		// 3333		10202	38.40	1(vendorPerformanceMatricId)	1(vendorRankingBuyerId)
		// 3333		15897	40.00	2(vendorPerformanceMatricId)	1(vendorRankingBuyerId)
		
		// ["2:APPOINTMENT_COMMITMENT:3333", List<VendorWeightedScoreVo>]
		// 3333		10105	300.00	3(vendorPerformanceMatricId)	2(vendorRankingBuyerId)
		// 3333		10202	28.00	1(vendorPerformanceMatricId)	2(vendorRankingBuyerId)
		
		// ["4:CUSTOMER_RATING_CSAT:3333", List<VendorWeightedScoreVo>]
		// 3333		10105	100.00	3(vendorPerformanceMatricId)	4(vendorRankingBuyerId)
		// 3333		15897	100.00	2(vendorPerformanceMatricId)	4(vendorRankingBuyerId)
		
		// UNIQUE: vendorRankingBuyerId, vendorPerformanceMatricId, vendorId
		
		for (String key : finalMapWeightedScore.keySet()) {

			List<VendorWeightedScoreVo> vendorWeightedScoreVoList = finalMapWeightedScore.get(key);
			
			for (VendorWeightedScoreVo vendorWeightedScoreVo : vendorWeightedScoreVoList) {
				saveUpdateBackupVendorPerformanceWeightedScore(vendorWeightedScoreVo, ADDED_BY_BATCH_JOB_STRING);
			}
		}
	}

	private void saveUpdateBackupVendorPerformanceWeightedScore(VendorWeightedScoreVo vendorWeightedScoreVo, String modifiedBy) {
		 getPerformanceMetricDaoImpl().saveUpdateBackupVendorPerformanceWeightedScore(vendorWeightedScoreVo, modifiedBy);
	}
	
	@Transactional
	public void calculateAndSavePerformanceMatric(Integer buyerId) {
		
		// check for null
		if (null == buyerId) {
			return;
		}
		
		// 1. check for SPN -> Template -> SKU by that Buyer
		// calculate performance metrics only for "provider firms" present in Template -> SKU(agreed to SKU pricing)
		// hence, this(SPN) criteria added. 
		List<Integer> spnIdList = fetchSpnIdFromTemplateSkuForBuyerSpecific(buyerId);
		
		System.out.println("spnIdList = " + spnIdList);
		
		
		// 2. fetch distinct provider firm list based on spn id's
		List<Integer> spnFilteredFirmIdList = null;
		if (null != spnIdList) {
			if (spnIdList.size() > 0) {
				spnFilteredFirmIdList = fetchDistinctFirmIdsFromSpnIdList(buyerId, spnIdList);
			}
		}
		
		System.out.println("spnFilteredFirmIdList = " + spnFilteredFirmIdList);
		
		// if no firms... return
		if ((null == spnFilteredFirmIdList) || (spnFilteredFirmIdList.size() < 1)) {
			return;
		}
		
		// 2. Get all performance attributes along with weightage.
		List<VendorRankingBuyerVo> perfAttributes = getVendorRankingBuyerVoPerformanceAttributes(buyerId);
		
		List<VendorWeightedScoreVo> vendorWeightedScoreVoList = null;
		
		Map<String, List<VendorWeightedScoreVo>> finalMapWeightedScore = new HashMap<String, List<VendorWeightedScoreVo>>();
		
		for (VendorRankingBuyerVo perfAttr : perfAttributes) {

			switch (RankMatchingPerformanceMatricEnum.stringToPerformanceMatricEnum(perfAttr.getName())) {

			case TIME_ON_SITE_ARRIVAL:
				vendorWeightedScoreVoList = timeOnSiteSwitchCase(perfAttr, spnFilteredFirmIdList);
				if (null != vendorWeightedScoreVoList) {
					if (vendorWeightedScoreVoList.size() > 0) {
						// 4. "1:TIME_ON_SITE_ARRIVAL:3333", List<VendorWeightedScoreVo>
						finalMapWeightedScore.put((perfAttr.getId()) + ":" + (perfAttr.getName()) + ":" + perfAttr.getBuyerId(), vendorWeightedScoreVoList);
					}
				}
				break;
				

			case APPOINTMENT_COMMITMENT:
				vendorWeightedScoreVoList = appointmentCommitmentSwitchCase(perfAttr, spnFilteredFirmIdList);
				if (null != vendorWeightedScoreVoList) {
					if (vendorWeightedScoreVoList.size() > 0) {
						// 4. "2:APPOINTMENT_COMMITMENT:3333", List<VendorWeightedScoreVo>
						finalMapWeightedScore.put((perfAttr.getId()) + ":" + (perfAttr.getName()) + ":" + perfAttr.getBuyerId(), vendorWeightedScoreVoList);
					}
				}
				break;

			case ORDER_RESPONSE:
				// TODO: ORDER_RESPONSE TBD.
				break;

			case CUSTOMER_RATING_CSAT:
				vendorWeightedScoreVoList = customerRatingCsatSwitchCase(perfAttr, spnFilteredFirmIdList);
				if (null != vendorWeightedScoreVoList) {
					if (vendorWeightedScoreVoList.size() > 0) {
						// 3. "4:CUSTOMER_RATING_CSAT:3333", List<VendorWeightedScoreVo>
						finalMapWeightedScore.put((perfAttr.getId()) + ":" + (perfAttr.getName()) + ":" + perfAttr.getBuyerId(), vendorWeightedScoreVoList);
					}
				}
				break;

			default:
				break;
			}
		} // END: for perfAttributes
		
		
		System.out.println("finalMapWeightedScore = " + finalMapWeightedScore);
		
		
		// 3. calculateAndSet
		if (null != finalMapWeightedScore) {
			if (finalMapWeightedScore.size() > 0) {
				
				Map<Integer, Double> consolidatedWeightedScoreByFirmIdMap = calculateAndSetVendorPerformanceMetricOnWeightedScoreResult(finalMapWeightedScore);
				
				System.out.println("consolidatedWeightedScoreByFirmIdMap = " + consolidatedWeightedScoreByFirmIdMap);
				
				// 4. `vendor_performance_matric`... get map of VendorId with it's performance_metric_id
				if (null != consolidatedWeightedScoreByFirmIdMap) {
					if (consolidatedWeightedScoreByFirmIdMap.size() > 0) {
						
						// Map<vendorId, vendorPerfMetricId>
						Map<Integer, Integer> vendorIdPerfMetricIdMap = saveOrUpdateVendorPerformanceMetrics(
								buyerId, consolidatedWeightedScoreByFirmIdMap);
						
						System.out.println("vendorIdPerfMetricIdMap = " + vendorIdPerfMetricIdMap);
						
						// 4.A Bind the newely generated with the firms id present in 'finalMapWeightedScore'
						finalMapWeightedScore = bindVendorPerfMetricIdWithFinalMapWeightedScore(finalMapWeightedScore, vendorIdPerfMetricIdMap);
						
						System.out.println("finalMapWeightedScore after bindVendorPerfMetricIdWithFinalMapWeightedScore = " + finalMapWeightedScore);
						
						// 5. save in `vendor_performance_weighted_score`
						saveUpdateBackupVendorPerformanceWeightedScore(finalMapWeightedScore);
						
					}
				} // END: Save logic
				
			}
		}
		
	} // END: Method

} // END: PerformanceMetricService.java