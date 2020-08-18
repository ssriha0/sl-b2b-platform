package com.newco.match.rank.performancemetric.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.dto.vo.newsignupbonus.VendorNewSignUpBonusVo;
import com.newco.marketplace.dto.vo.performancematric.VendorPerformanceMatricVo;
import com.newco.marketplace.dto.vo.performancematric.VendorRankingBuyerVo;
import com.newco.marketplace.dto.vo.rank.VendorRankingWeightedScoreVo;
import com.newco.marketplace.dto.vo.rank.VendorSoRankingVo;
import com.newco.marketplace.persistence.match.rank.performancemetric.dao.IRankMatchingDao;
import com.newco.match.rank.performancemetric.enums.RankMatchingPerformanceMatricEnum;

public class RankMatchingServiceImpl implements IRankMatchingService {
	
	private IRankMatchingDao rankMatchingDaoImpl;
	
	public IRankMatchingDao getRankMatchingDaoImpl() {
		return rankMatchingDaoImpl;
	}

	public void setRankMatchingDaoImpl(IRankMatchingDao rankMatchingDaoImpl) {
		this.rankMatchingDaoImpl = rankMatchingDaoImpl;
	}
	// ----------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------//
	@Transactional
	public String getProviderByRankMatchingMetric(List<D2CProviderAPIVO> firmDetailsListVo, String buyerIdString) {
		String corelationId = null;
		if (null != firmDetailsListVo && null != buyerIdString) {
			if (firmDetailsListVo.size() > 0) {
				Integer buyerId = Integer.parseInt(buyerIdString);
				corelationId = calculateSoRanking(firmDetailsListVo, buyerId);
			}
		}
		
		return corelationId;
	}
	// ----------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------//
	
	private List<VendorRankingBuyerVo> getVendorRankingBuyerVoRankAttributesAtFirmLevel(Integer buyerId) {
		return getRankMatchingDaoImpl().getVendorRankingBuyerVoRankAttributesAtFirmLevel(buyerId);
	}
	
	private List<VendorSoRankingVo> performanceMetricSwitch(VendorRankingBuyerVo perfAttr, List<D2CProviderAPIVO> firmDetailsListVo) {
		
		// [firmId, performance_matric_score] = `vendor_performance_matric`
		Map<Integer, Double> perfMetricScoreByFirmIdMap = getVendorPerformanceMatricByFirmAndBuyerIdMap(perfAttr.getBuyerId(), firmDetailsListVo);
		
		// creating object of VendorSoRankingVo iterating over main: firmDetailsListVo list
		// set vendorId and (calculate and set ranking score and default rankingScore  = 0.0)
		List<VendorSoRankingVo> vendorSoRankingVoList = new ArrayList<VendorSoRankingVo>();
		
		Double weightedRankingScore = null;
		// add the firmDetailsListVo(firmId's) and score in the 'vendorSoRankingVoList' list.
		for (D2CProviderAPIVO d2cProviderAPIVO : firmDetailsListVo) {
			
			VendorSoRankingVo vendorSoRankingVo = new VendorSoRankingVo();
			
			// set vendorId
			vendorSoRankingVo.setVendorId(d2cProviderAPIVO.getFirmId());
			
			// calculate and set ranking score and default rankingScore  = 0.0
			if (null != perfMetricScoreByFirmIdMap) {
				if (perfMetricScoreByFirmIdMap.size() > 0) {
					
					if (perfMetricScoreByFirmIdMap.containsKey(vendorSoRankingVo.getVendorId())) {
						
						// calculate ranking score on performance metrics score.
						weightedRankingScore = formulaeToCalculateWeightedScoreBasedOnPerfMatricScore(
								perfAttr.getWeightage(),
								perfMetricScoreByFirmIdMap.get(vendorSoRankingVo.getVendorId()));
						
						vendorSoRankingVo.setRankingScore(weightedRankingScore);
						
					}
				}
			}
			
			vendorSoRankingVoList.add(vendorSoRankingVo);
		}
		
		return vendorSoRankingVoList;
		
	} // END: performanceMetricSwitch()
	
	private Map<Integer, Double> getVendorPerformanceMatricByFirmAndBuyerIdMap(
			Integer buyerId,
			List<D2CProviderAPIVO> firmDetailsListVo) {

		// `vendor_performance_matric`: buyerId, firmID, score
		List<VendorPerformanceMatricVo> vendorPerformanceMatricVoList = getVendorPerformanceMatricByFirmAndBuyerIdList(
				buyerId, firmDetailsListVo);

		return generateMapVendorIdAndScoreMap(vendorPerformanceMatricVoList);

	}
	
	private List<VendorPerformanceMatricVo> getVendorPerformanceMatricByFirmAndBuyerIdList(
			Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		return getRankMatchingDaoImpl().getVendorPerformanceMatricByFirmAndBuyerId(buyerId,	firmDetailsListVo);
	}

	private Double formulaeToCalculateWeightedScoreBasedOnPerfMatricScore(Double weightage, Double score) {
		Double weightedScore = (score * (weightage / 100));
		return weightedScore;
	}
	
	private Map<Integer, Double> consolidateAndCalculatePerfAttributesMap(
			List<VendorSoRankingVo> vendorSoRankingVoList,
			Map<Integer, Double> consolidatedWeightedScoreByFirmIdMap) {
		
		// add the result to consolidated map
		for (VendorSoRankingVo vendorRankingBuyerVo : vendorSoRankingVoList) {

			if (consolidatedWeightedScoreByFirmIdMap.containsKey(vendorRankingBuyerVo.getVendorId())) {

				// get the aleady present value.
				Double weightedScore = (consolidatedWeightedScoreByFirmIdMap.get(vendorRankingBuyerVo.getVendorId()));

				// add it with the next value.
				weightedScore += vendorRankingBuyerVo.getRankingScore();

				// add once again in map with updated value.
				consolidatedWeightedScoreByFirmIdMap.put(vendorRankingBuyerVo.getVendorId(), weightedScore);

			} else {
				consolidatedWeightedScoreByFirmIdMap.put(vendorRankingBuyerVo.getVendorId(), vendorRankingBuyerVo.getRankingScore());
			}
		}

		return consolidatedWeightedScoreByFirmIdMap;
	}
	
	private List<VendorSoRankingVo> mapToVendorSoRankingVo(List<VendorNewSignUpBonusVo> vendorSoRankingVoListNewSignUps) {
		List<VendorSoRankingVo> vendorSORankingVolist = new ArrayList<VendorSoRankingVo>();
		for (VendorNewSignUpBonusVo vendorNewSignUpBonusVo : vendorSoRankingVoListNewSignUps) {
			VendorSoRankingVo vo = new VendorSoRankingVo();
			vo.setVendorId(vendorNewSignUpBonusVo.getVendorId());
			vo.setRankingScore(vendorNewSignUpBonusVo.getBonusScore());
			vendorSORankingVolist.add(vo);
		}
		return vendorSORankingVolist;
	}
	
	private List<VendorNewSignUpBonusVo> newSignUpsSwitch(
			VendorRankingBuyerVo perfAttr,
			List<D2CProviderAPIVO> firmDetailsListVo) {
		
		// [firmId, performance_matric_score]
		Map<Integer, Double> newSignUpsScoreByFirmIdMap = getVendorBonusScoreMap(perfAttr.getBuyerId(), firmDetailsListVo);
		
		// creating object of VendorNewSignUpBonusVo iterating over main: firmDetailsListVo list
		// set vendorId and (calculate and set weighted score and the default rankingScore  = 0.0)
		List<VendorNewSignUpBonusVo> vendorNewSignUpBonusVoList = new ArrayList<VendorNewSignUpBonusVo>();
		
		Double weightedBonusScore = null;
		// add the firmDetailsListVo(firmIds) and score in the 'vendorSoRankingVoList' list.
		for (D2CProviderAPIVO d2cProviderAPIVO : firmDetailsListVo) {
			
			VendorNewSignUpBonusVo vendorNewSignUpBonusVo = new VendorNewSignUpBonusVo();
			
			// set vendorId
			vendorNewSignUpBonusVo.setVendorId(d2cProviderAPIVO.getFirmId());
			
			// calculate and set ranking score and default rankingScore  = 0.0
			if (null != newSignUpsScoreByFirmIdMap) {
				if (newSignUpsScoreByFirmIdMap.size() > 0) {
					
					if (newSignUpsScoreByFirmIdMap.containsKey(vendorNewSignUpBonusVo.getVendorId())) {
						
						// calculate ranking score on performance metrics score.
						weightedBonusScore = formulaeToCalculateWeightedScoreBasedOnPerfMatricScore(
								perfAttr.getWeightage(),
								newSignUpsScoreByFirmIdMap.get(vendorNewSignUpBonusVo.getVendorId()));
						
						vendorNewSignUpBonusVo.setBonusScore(weightedBonusScore);
						
					}
				}
			}
			
			vendorNewSignUpBonusVoList.add(vendorNewSignUpBonusVo);
		}
		
		return vendorNewSignUpBonusVoList;
		
	}

	private Map<Integer, Double> getVendorBonusScoreMap(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		List<VendorPerformanceMatricVo> vendorPerformanceMatricVoList = getVendorBonusScoreMapList(buyerId, firmDetailsListVo);
		return generateMapVendorIdAndScoreMap(vendorPerformanceMatricVoList);
	}
	
	// this method is used for both([PERFORMANCE_METRIC; MATCHING_PERCENTAGE])
	private Map<Integer, Double> generateMapVendorIdAndScoreMap(List<VendorPerformanceMatricVo> vendorPerformanceMatricVoList) {
		Map<Integer, Double> perfMetricScoreByFirmIdMap = null;
		// create a hashmap for firmId and score from vendorPerformanceMatricVoList
		if (null != vendorPerformanceMatricVoList) {
			if (vendorPerformanceMatricVoList.size() > 0) {
				perfMetricScoreByFirmIdMap = new HashMap<Integer, Double>();
				
				for (VendorPerformanceMatricVo vendorPerformanceMatricVo : vendorPerformanceMatricVoList) {
					perfMetricScoreByFirmIdMap.put(vendorPerformanceMatricVo.getVendorId()
							, vendorPerformanceMatricVo.getVendorPerformanceMatricTableScore());
				}
			}
		}
		
		return perfMetricScoreByFirmIdMap;
	}
	
	private List<VendorPerformanceMatricVo> getVendorBonusScoreMapList(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		return getRankMatchingDaoImpl().getVendorBonusScoreMapList(buyerId,	firmDetailsListVo);
	}
	
	private Map<Double, Integer> rankBasedOnWeightedScore(
			Map<Integer, Double> consolidatedAndSortedFirmIdAndWeightedScoreMap) {
		Map<Double, Integer> rankedMap = new HashMap<Double, Integer>();
		Integer rank = 0;
		for (Integer vendorIdAsKey : consolidatedAndSortedFirmIdAndWeightedScoreMap.keySet()) {
			if (!rankedMap.containsKey((consolidatedAndSortedFirmIdAndWeightedScoreMap.get(vendorIdAsKey)))) {
				rankedMap.put(consolidatedAndSortedFirmIdAndWeightedScoreMap.get(vendorIdAsKey), (++rank));
			}
		}
		return rankedMap;
	}

	private Map<Integer, Double> sortByValueDescConsolidatedFirmIdAndWeightedScoreMap(
			Map<Integer, Double> consolidatedFirmIdAndWeightedScoreMapUnsortedMap) {

		// 1. Convert Map to List of Map
		List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(consolidatedFirmIdAndWeightedScoreMapUnsortedMap.entrySet());

		// 2. Sort list with Collections.sort(), provide a custom Comparator
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				
				// FOR_ASCENDING_ORDER = return (o1.getValue()).compareTo(o2.getValue());
				
				// FOR_DESCENDING_ORDER 
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Map.Entry<Integer, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	private void saveOrUpdateVendorSoRankingVoList(List<VendorSoRankingVo> vendorSoRankingVoList) {
		for (VendorSoRankingVo vendorSoRankingVo : vendorSoRankingVoList) {
			saveOrUpdateVendorSoRankingVo(vendorSoRankingVo);
		}
	}
	
	private void saveOrUpdateVendorSoRankingVo(VendorSoRankingVo vendorSoRankingVo) {
		getRankMatchingDaoImpl().saveOrUpdateVendorSoRankingVo(vendorSoRankingVo);
	}
	
	private List<VendorSoRankingVo> mapVendorIdWeightedScoreRankAndCorelationIdToVendorSoRankingVo(
			final Map<Integer, Double> consolidatedFirmIdAndWeightedScoreMap,
			final Map<Double, Integer> mapWithWeightedScoreAndRank,
			final String corelationId) {
		
		// consolidatedFirmIdAndWeightedScoreMap[firmId, weightedScore(RankingScore)]
		// 67225, 23.46
		// 10202, 53.84
		// 17339, 0.0
		// 15897, 34.00
		// 10715, 0.0
		
		// mapWithWeightedScoreAndRank[weightedScore, Rank]
		// Key : 53.84 	Value : 1
		// Key : 34.0 	Value : 2
		// Key : 23.46 	Value : 3
		// Key : 0.0 	Value : 4
		
		List<VendorSoRankingVo> vendorSoRankingVoList = new ArrayList<VendorSoRankingVo>();
		
		for (Integer vendorId : consolidatedFirmIdAndWeightedScoreMap.keySet()) {
			
			VendorSoRankingVo vo = new VendorSoRankingVo();
			// eg. 67225
			vo.setVendorId(vendorId);
			// eg. 23.46
			vo.setRankingScore(consolidatedFirmIdAndWeightedScoreMap.get(vendorId));
			// eg. 3
			vo.setRank(mapWithWeightedScoreAndRank.get(vo.getRankingScore()));
			vo.setCorelationId(corelationId);
			
			vendorSoRankingVoList.add(vo);
			
		}

		return vendorSoRankingVoList;
	} // Method: mapVendorIdWeightedScoreAndRankToVendorSoRankingVo(...);
	
	private String generateAndGetCorelationId() {
		return getRankMatchingDaoImpl().generateAndGetCorelationId();
	} // Method: mapVendorIdWeightedScoreAndRankToVendorSoRankingVo(...);
	
	private void saveOrUpdateVendorRankingWeightedScoreVoList(List<VendorRankingWeightedScoreVo> vendorRankingWeightedScoreVoList) {
		for (VendorRankingWeightedScoreVo vendorRankingWeightedScoreVo : vendorRankingWeightedScoreVoList) {
			saveOrUpdateVendorRankingWeightedScoreVo(vendorRankingWeightedScoreVo);
		}
	} // Method: saveOrUpdateVendorRankingWeightedScoreVoList

	private void saveOrUpdateVendorRankingWeightedScoreVo(VendorRankingWeightedScoreVo vendorRankingWeightedScoreVo) {
		// attributeId(vendorRankingBuyerId) = [PERFORMANCE_METRIC || NEW_SIGNUP || MATCHING_PER;] 
		// setVendorId = 67225
		// WeightedScore = 23.46
		// corelationId = 1
		getRankMatchingDaoImpl().saveOrUpdateVendorRankingWeightedScoreVo(vendorRankingWeightedScoreVo);
	} // Method: saveOrUpdateVendorRankingWeightedScoreVo
	
	private void bindFirmDetailsListVoFirmsWithRank(
			List<D2CProviderAPIVO> firmDetailsListVo,
			List<VendorSoRankingVo> vendorSoRankingVoList) {

		for (D2CProviderAPIVO d2cProviderAPIVO : firmDetailsListVo) {
			
			String d2cProviderAPIVOFirmId = Integer.toString(d2cProviderAPIVO.getFirmId());
			
			for (VendorSoRankingVo vendorSoRankingVo : vendorSoRankingVoList) {
				
				String vendorSoRankingVoFirmId = Integer.toString(vendorSoRankingVo.getVendorId());
				
				if (vendorSoRankingVoFirmId.equals(d2cProviderAPIVOFirmId)) {
					d2cProviderAPIVO.setProviderRank(vendorSoRankingVo.getRank());
				}
			}
		}
	}
	
	private List<VendorSoRankingVo> matchingPercentageSwitchCase(VendorRankingBuyerVo perfAttr, List<D2CProviderAPIVO> firmDetailsListVo) {

		// [firmId, matching_score] = `vendor_matching_score`
		Map<Integer, Double> matchingScoreByFirmIdMap = getVendorMatchingPercentageByFirmAndBuyerIdMap(perfAttr.getBuyerId(), firmDetailsListVo);

		// creating object of VendorSoRankingVo iterating over main: firmDetailsListVo list
		// set vendorId and (calculate and set ranking score and default rankingScore  = 0.0)
		List<VendorSoRankingVo> vendorSoRankingVoList = new ArrayList<VendorSoRankingVo>();
		
		Double weightedRankingScore = null;
		// add the firmDetailsListVo(firmId's) and score in the 'vendorSoRankingVoList' list.
		for (D2CProviderAPIVO d2cProviderAPIVO : firmDetailsListVo) {
			
			VendorSoRankingVo vendorSoRankingVo = new VendorSoRankingVo();
			
			// set vendorId
			vendorSoRankingVo.setVendorId(d2cProviderAPIVO.getFirmId());
			
			// calculate and set ranking score and default rankingScore  = 0.0
			if (null != matchingScoreByFirmIdMap) {
				if (matchingScoreByFirmIdMap.size() > 0) {
					
					if (matchingScoreByFirmIdMap.containsKey(vendorSoRankingVo.getVendorId())) {
						
						// calculate ranking score on performance metrics score.
						weightedRankingScore = formulaeToCalculateWeightedScoreBasedOnMatchingScore(
								perfAttr.getWeightage(),
								matchingScoreByFirmIdMap.get(vendorSoRankingVo.getVendorId()));
						
						vendorSoRankingVo.setRankingScore(weightedRankingScore);
						
					}
				}
			}
			
			vendorSoRankingVoList.add(vendorSoRankingVo);
		}
		
		return vendorSoRankingVoList;
	}
	
	private Map<Integer, Double> getVendorMatchingPercentageByFirmAndBuyerIdMap(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		// `vendor_performance_matric`: buyerId, firmID, score
		List<VendorPerformanceMatricVo> vendorMatchingScoreVoList = getVendorMatchingByFirmAndBuyerIdList(buyerId, firmDetailsListVo);
		return generateMapVendorIdAndScoreMap(vendorMatchingScoreVoList);
	}
	
	private List<VendorPerformanceMatricVo> getVendorMatchingByFirmAndBuyerIdList(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		return getRankMatchingDaoImpl().getVendorMatchingByFirmAndBuyerIdList(buyerId,	firmDetailsListVo);
	}
	
	private Double formulaeToCalculateWeightedScoreBasedOnMatchingScore(Double weightage, Double score) {
		Double weightedScore = (score * (weightage / 100));
		return weightedScore;
	}
	
	private String calculateSoRanking(List<D2CProviderAPIVO> firmDetailsListVo, Integer buyerId) {
		
		// generate co-relation Id to be later mapped with so_id
		String corelationId = null;
				
		// 1. Get all performance attributes along with weightage.
		List<VendorRankingBuyerVo> perfAttributes = getVendorRankingBuyerVoRankAttributesAtFirmLevel(buyerId);
		if (perfAttributes.size() == 0) {
			return corelationId;
		}
		
		corelationId = generateAndGetCorelationId();

		// PERFORMANCE_METRIC
		List<VendorSoRankingVo> vendorSoRankingVoListPerfMetric = null;
		
		// NEW_SIGNUP
		List<VendorNewSignUpBonusVo> vendorSoRankingVoListNewSignUps = null;
		
		// MATCHING_PERCENTAGE
		List<VendorSoRankingVo> vendorSoRankingVoMatchingPercentageList = null;
		
		// PERFORMANCE_METRIC + NEW_SIGNUP + MATCHING_PERCENTAGE
		List<VendorRankingWeightedScoreVo> vendorRankingWeightedScoreVoList = new ArrayList<VendorRankingWeightedScoreVo>();
		
		// consolidatedWeightedScoreByFirmIdMap = [firmId, weightedScore(PERFORMANCE_METRIC + NEW_SIGNUP)]
		Map<Integer, Double> consolidatedFirmIdAndWeightedScoreMap = new HashMap<Integer, Double>();
		
		// for populate `vendor_ranking_weighted_score`
		// [PERFORMANCE_METRIC; NEW_SIGNUP; MATCHING_PERCENTAGE] all three weighted score goes in `vendor_ranking_weighted_score`
		for (VendorRankingBuyerVo perfAttr : perfAttributes) {
			
			switch (RankMatchingPerformanceMatricEnum.stringToPerformanceMatricEnum(perfAttr.getName())) {
			case PERFORMANCE_METRIC:
				// vendorSoRankingVoList of [VendorId, RankingScore] based also on firmDetailsListVo
				// calculated from performance_metric score: `vendor_performance_matric`
				vendorSoRankingVoListPerfMetric = performanceMetricSwitch(perfAttr, firmDetailsListVo);
				
				for (VendorSoRankingVo vendorSoRankingVo : vendorSoRankingVoListPerfMetric) {
					VendorRankingWeightedScoreVo vo = new VendorRankingWeightedScoreVo();
					vo.setVendorId(vendorSoRankingVo.getVendorId());
					vo.setWeightedScore(vendorSoRankingVo.getRankingScore());
					vo.setCorelationId(corelationId);
					vo.setVendorRankingBuyerId(perfAttr.getId());
					vendorRankingWeightedScoreVoList.add(vo);
				}
				
				// consolidate and calculate the results of ALL perfAttributes
				consolidatedFirmIdAndWeightedScoreMap = consolidateAndCalculatePerfAttributesMap(
						vendorSoRankingVoListPerfMetric,
						consolidatedFirmIdAndWeightedScoreMap);
				break;
				
			case NEW_SIGNUP:
				// list of [VendorId, BonusScore] calculated from `vendor_bonus_score`
				vendorSoRankingVoListNewSignUps = newSignUpsSwitch(perfAttr, firmDetailsListVo);

				for (VendorNewSignUpBonusVo vendorNewSignUpBonusVo : vendorSoRankingVoListNewSignUps) {
					VendorRankingWeightedScoreVo vo = new VendorRankingWeightedScoreVo();
					vo.setVendorId(vendorNewSignUpBonusVo.getVendorId());
					vo.setWeightedScore(vendorNewSignUpBonusVo.getBonusScore());
					vo.setCorelationId(corelationId);
					vo.setVendorRankingBuyerId(perfAttr.getId());
					vendorRankingWeightedScoreVoList.add(vo);
				}
				
				// consolidate and calculate the results of ALL perfAttributes
				consolidatedFirmIdAndWeightedScoreMap = consolidateAndCalculatePerfAttributesMap(
						mapToVendorSoRankingVo(vendorSoRankingVoListNewSignUps),
						consolidatedFirmIdAndWeightedScoreMap);
				break;
				
			case MATCHING_PERCENTAGE:
				// list of [VendorId, BonusScore] calculated from `vendor_matching_score`
				vendorSoRankingVoMatchingPercentageList = matchingPercentageSwitchCase(perfAttr, firmDetailsListVo);
				
				for (VendorSoRankingVo vendorSoRankingVo : vendorSoRankingVoMatchingPercentageList) {
					VendorRankingWeightedScoreVo vo = new VendorRankingWeightedScoreVo();
					vo.setVendorId(vendorSoRankingVo.getVendorId());
					vo.setWeightedScore(vendorSoRankingVo.getRankingScore());
					vo.setCorelationId(corelationId);
					vo.setVendorRankingBuyerId(perfAttr.getId());
					vendorRankingWeightedScoreVoList.add(vo);
				}
				
				// consolidate and calculate the results of ALL perfAttributes
				consolidatedFirmIdAndWeightedScoreMap = consolidateAndCalculatePerfAttributesMap(
						vendorSoRankingVoMatchingPercentageList,
						consolidatedFirmIdAndWeightedScoreMap);
				
				break;				

			default:
				break;
			}
			
		} // END for and calculation
		
		// consolidatedFirmIdAndWeightedScoreMap
			// 67225, 23.46
			// 10202, 53.84
			// 17339, 0.0
			// 15897, 34.00
			// 10715, 0.0
		
		
		// SORT MAP based on values...
		consolidatedFirmIdAndWeightedScoreMap = sortByValueDescConsolidatedFirmIdAndWeightedScoreMap(consolidatedFirmIdAndWeightedScoreMap);
		// -- AFTER_SORTING-----------------------------------
		// Key : 10202 		Value : 53.84
		// Key : 15897 		Value : 34.0
		// Key : 67225 		Value : 23.46
		// Key : 17339 		Value : 0.0
		// Key : 10715 		Value : 0.0
		
		
		// calculate RANK based on final_consolidated_score(PERFORMANCE_METRIC + NEW_SIGNUP)
		Map<Double, Integer> mapWithWeightedScoreAndRank = rankBasedOnWeightedScore(consolidatedFirmIdAndWeightedScoreMap);
		// -- AFTER_RANKING-----------------------------------
		// Key : 53.84 	Value : 1
		// Key : 34.0 	Value : 2
		// Key : 23.46 	Value : 3
		// Key : 0.0 	Value : 4
		
		
		// Bind :=: vendor_id, score and rank to `VendorSoRankingVo`
		// eg.: setVendorId = 67225; setRankingScore = 23.46; setRank = 3, corelationId = 1
		List<VendorSoRankingVo> vendorSoRankingVoList = mapVendorIdWeightedScoreRankAndCorelationIdToVendorSoRankingVo(
				consolidatedFirmIdAndWeightedScoreMap
				, mapWithWeightedScoreAndRank
				, corelationId);
		
		
		// populate `vendor_so_ranking`
		// eg.: setVendorId = 67225; setRankingScore = 23.46; setRank = 3, corelationId = 1
		saveOrUpdateVendorSoRankingVoList(vendorSoRankingVoList);
		
		
		// -------------------------------------------------------------------------------------------------- //
		// populate `vendor_ranking_weighted_score` PERFORMANCE_METRIC(vendorSoRankingVoListPerfMetric)
		// eg.:all three weighted score goes in `vendor_ranking_weighted_score`
		// attributeId(vendorRankingBuyerId) = [PERFORMANCE_METRIC || NEW_SIGNUP || MATCHING_PER;] 
		// setVendorId = 67225
		// WeightedScore = 23.46
		// corelationId = 1
		saveOrUpdateVendorRankingWeightedScoreVoList(vendorRankingWeightedScoreVoList);
		
		
		// bind the `firmDetailsListVo` with the ranks.
		bindFirmDetailsListVoFirmsWithRank(firmDetailsListVo, vendorSoRankingVoList);
		
		return corelationId;
		
	} // END: calculateSoRanking()

}