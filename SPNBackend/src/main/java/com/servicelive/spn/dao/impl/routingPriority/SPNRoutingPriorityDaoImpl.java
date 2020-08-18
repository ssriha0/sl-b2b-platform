package com.servicelive.spn.dao.impl.routingPriority;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.spn.detached.LookupCriteriaDTO;
import com.servicelive.domain.spn.detached.SPNCoverageDTO;
import com.servicelive.domain.spn.detached.SPNPerformanceCriteria;
import com.servicelive.domain.spn.detached.SPNTierDTO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.dao.routingPriority.SPNRoutingPriorityDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author hoza
 *
 */
public class SPNRoutingPriorityDaoImpl extends BaseServices implements SPNRoutingPriorityDao{
	
	@Override
	protected void handleDates(Object entity) {
		// TODO Auto-generated method stub
		
	}
	
	/**check for routing priority
	 * @param spnId
	 * @return spnHdr
	 * @throws DataServiceException
	 */
	public SPNHeader checkForCriteria(Integer spnId)throws DataServiceException{
		
		SPNHeader spnHdr = null;
		try{
			spnHdr = (SPNHeader)getSqlMapClient().queryForObject("network.routingPriority.check_criteria", spnId);
			
		}
		catch(Exception e){
			logger.debug("Exception in SPNRoutingPriorityDaoImpl.checkForCriteria due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return spnHdr;
	}

	/**fetch look up criteria
	 * @return spnHdr
	 * @throws DataServiceException
	 */
	public List<LookupCriteriaDTO> fetchLookupCriteria()throws DataServiceException{
		
		List<LookupCriteriaDTO> criteriaDTO = new ArrayList<LookupCriteriaDTO>();
		try{
			criteriaDTO = (List<LookupCriteriaDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_lookup_criteria", null);
			
		}
		catch(Exception e){
			logger.debug("Exception in SPNRoutingPriorityDaoImpl.checkForCriteria due to "+e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return criteriaDTO;
	}
	
	/**fetch modified date
	 * @return Date
	 * @throws DataServiceException
	 */
	public Date fetchModifiedDate() throws DataServiceException{
		Date modifiedDate = null;
		try{
			modifiedDate = (Date)getSqlMapClient().queryForObject("network.routingPriority.fetch_modifiedDate", null);
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.fetchModifiedDate() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return modifiedDate;
	}
	
	/**fetch spn performance criteria
	 * @param spnId
	 * @return criteria
	 * @throws BusinessServiceException
	 */
	public List<SPNPerformanceCriteria> fetchCriteria(Integer spnId) throws DataServiceException{
		List<SPNPerformanceCriteria> criteria = new ArrayList<SPNPerformanceCriteria>();
		try{
			criteria = (List<SPNPerformanceCriteria>)getSqlMapClient().queryForList("network.routingPriority.fetch_spn_criteria", spnId);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.fetchCriteria() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return criteria;
	}

	/**fetch spn performance criteria
	 * @param spnId
	 * @return tiers
	 * @throws BusinessServiceException
	 */
	public List<SPNTierDTO> fetchTiers(Integer spnId) throws DataServiceException{
		List<SPNTierDTO> tiers = new ArrayList<SPNTierDTO>();
		try{
			tiers = (List<SPNTierDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_spn_tiers", spnId);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.fetchTiers() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return tiers;
	}

	/**fetch approved SPN providers
	 * @param spnId
	 * @return providerList
	 * @throws BusinessServiceException
	 */
	public List<Integer> fetchSPNProviders(Integer spnId, Integer aliasSpnId) throws DataServiceException{
		List<Integer> providerList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", spnId);
		params.put("aliasSpnId", aliasSpnId);
		try{
			providerList = (List<Integer>)getSqlMapClient().queryForList("network.routingPriority.fetch_spn_providers", params);
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.fetchSPNProviders() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return providerList;
	}
	
	/**fetch approved SPN firms
	 * @param spnId
	 * @return firmList
	 * @throws BusinessServiceException
	 */
	public List<SPNCoverageDTO> fetchSPNFirms(Integer spnId, Integer aliasSpnId) throws DataServiceException{
		List<SPNCoverageDTO> firmList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", spnId);
		params.put("aliasSpnId", aliasSpnId);
		try{
			firmList = (List<SPNCoverageDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_spn_firms", params);
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.fetchSPNFirms() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return firmList;
	}
	
	/**fetch general scores of provider
	 * @param performanceCriteria
	 * @param providerList
	 * @return providerScores
	 * @throws DataServiceException
	 */
	public List<LookupCriteriaDTO> getProviderScores(List<SPNPerformanceCriteria> performanceCriteria, 
			List<Integer> providerList)throws DataServiceException{

		List<LookupCriteriaDTO> providerScores = new ArrayList<LookupCriteriaDTO>();
		try{
			for(SPNPerformanceCriteria criteria : performanceCriteria){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("criteriaId",criteria.getCriteriaId());
				params.put("timeframe",criteria.getTimeFrame());
				params.put("buyerId",criteria.getBuyerId());
				params.put("providers",providerList);
				List<LookupCriteriaDTO> providerScore = (List<LookupCriteriaDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_provider_scores", params);
				providerScores.addAll(providerScore);
			}
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getProviderScores() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return providerScores;
	}
	
	/**fetch general scores of firm
	 * @param performanceCriteria
	 * @param firmList
	 * @return firmScores
	 * @throws DataServiceException
	 */
	public List<LookupCriteriaDTO> getFirmScores(List<SPNPerformanceCriteria> performanceCriteria, 
			List<Integer> firmList)throws DataServiceException{

		List<LookupCriteriaDTO> firmScores = new ArrayList<LookupCriteriaDTO>();
		try{
			for(SPNPerformanceCriteria criteria : performanceCriteria){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("criteriaId",criteria.getCriteriaId());
				params.put("timeframe",criteria.getTimeFrame());
				params.put("buyerId",criteria.getBuyerId());
				params.put("firms",firmList);
				List<LookupCriteriaDTO> firmScore = (List<LookupCriteriaDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_firm_scores", params);
				firmScores.addAll(firmScore);
			}
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getFirmScores() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return firmScores;
	}
	
	/**fetch provider coverage details
	 * @param spnId
	 * @return coverage
	 * @throws DataServiceException
	 */
	public List<SPNCoverageDTO> getProviderCoverage(Integer spnId,Integer aliasSpnId)throws DataServiceException{
		List<SPNCoverageDTO> coverage = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", spnId);
		params.put("aliasSpnId", aliasSpnId);
		try{
			coverage = (List<SPNCoverageDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_provider_coverage", params);
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getProviderCoverage() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return coverage;
	}
	
	/**fetch firm coverage details
	 * @param spnId
	 * @return coverage
	 * @throws DataServiceException
	 */
	public List<SPNCoverageDTO> getFirmCoverage(Integer spnId, Integer aliasSpnId)throws DataServiceException{
		List<SPNCoverageDTO> coverage = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", spnId);
		params.put("aliasSpnId", aliasSpnId);
		try{
			coverage = (List<SPNCoverageDTO>)getSqlMapClient().queryForList("network.routingPriority.fetch_firm_coverage", params);
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getFirmCoverage() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return coverage;
	}
	
	/**fetch eligible prov count for all firms
	 * @param memberList
	 * @param spnId
	 * @return coverage
	 * @throws BusinessServiceException
	 */
	public List<SPNCoverageDTO> getProvCount(Map<Integer, Integer> memberMap, Integer spnId, Integer aliasSpnId)throws DataServiceException{
		List<SPNCoverageDTO> count = new ArrayList<SPNCoverageDTO>();
		List<SPNCoverageDTO> aliasCount = new ArrayList<SPNCoverageDTO>();
		List<Integer> memberList = new ArrayList<Integer>();
		List<Integer> aliasList = new ArrayList<Integer>();
		if(null != memberMap){
			for(Entry<Integer, Integer> member : memberMap.entrySet()){
				if(null != member){
					if(member.getValue().intValue() == spnId.intValue()){
						memberList.add(member.getKey());
					}
					else if(member.getValue().intValue() == aliasSpnId.intValue()){
						aliasList.add(member.getKey());
					}
				}
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		try{
			if(null != memberList && memberList.size() > 0){
				params.put("spnId", spnId);
				params.put("firmIds", memberList);
				count = (List<SPNCoverageDTO>)getSqlMapClient().queryForList("network.routingPriority.get_eligibleProv_count", params);
			}
			if(null != aliasList && aliasList.size() > 0){
				params.clear();
				params.put("spnId", aliasSpnId);
				params.put("firmIds", aliasList);
				aliasCount = (List<SPNCoverageDTO>)getSqlMapClient().queryForList("network.routingPriority.get_eligibleProv_count", params);
			}
			if(null != aliasCount && aliasCount.size() > 0){
				count.addAll(aliasCount);
			}
				
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getProvCount() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return count;
	}
	
	/**save spn hdr criteria
	 * @param spnHdr
	 * @return
	 * @throws DataServiceException
	 */
	public void saveHdrDetails(SPNHeader spnHdr) throws DataServiceException{
		try{
			getSqlMapClient().update("network.routingPriority.save_hdr_details",spnHdr);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveHdrDetails() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save spn criteria
	 * @param performanceCriteria
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveCriteria(List<SPNPerformanceCriteria> performanceCriteria,
			Integer spnId) throws DataServiceException{
		
		try{
			getSqlMapClient().delete("network.routingPriority.delete_criteria", spnId);
			for(SPNPerformanceCriteria criteria : performanceCriteria){
				if(null != criteria){
					criteria.setSpnId(spnId);
					getSqlMapClient().insert("network.routingPriority.save_criteria", criteria);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveCriteria() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save priority tiers
	 * @param spnTiers
	 * @return
	 * @throws DataServiceException
	 */
	public void saveTiers(List<SPNTierDTO> spnTiers, Integer spnId) throws DataServiceException{
		try{
			getSqlMapClient().delete("network.routingPriority.delete_tiers", spnId);
			for(SPNTierDTO tier : spnTiers){
				if(null != tier){
					getSqlMapClient().insert("network.routingPriority.save_tiers", tier);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveTiers() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save provider score
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveProviderScore(List<SPNCoverageDTO> coveragelist, Integer spnId) throws DataServiceException{
		try{
			for(SPNCoverageDTO coverage : coveragelist){
				if(null != coverage){
					getSqlMapClient().update("network.routingPriority.save_prov_score", coverage);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveProviderScore() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save firm score
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveFirmScore(List<SPNCoverageDTO> coveragelist, Integer spnId) throws DataServiceException{
		try{
			for(SPNCoverageDTO coverage : coveragelist){
				if(null != coverage){
					getSqlMapClient().update("network.routingPriority.save_firm_score", coverage);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveFirmScore() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save spnet_hdr criteria history
	 * @param spnHdr
	 * @return
	 * @throws DataServiceException
	 */
	public void saveHdrHistory(SPNHeader spnHdr) throws DataServiceException{
		try{
			getSqlMapClient().insert("network.routingPriority.save_hdr_history",spnHdr);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveHdrHistory() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save network history
	 * @param spnId	 
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	public void saveNetworkHistory(Integer spnId, String userName, String action) throws DataServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", spnId);
		params.put("userName", userName);
		params.put("action", action);
		
		try{
			getSqlMapClient().insert("network.routingPriority.save_network_history",params);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveHdrHistory() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save spn criteria history
	 * @param performanceCriteria
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveCriteriaHistory(List<SPNPerformanceCriteria> performanceCriteria,
			Integer spnId) throws DataServiceException{
		try{
			for(SPNPerformanceCriteria criteria : performanceCriteria){
				if(null != criteria){
					criteria.setSpnId(spnId);
					getSqlMapClient().insert("network.routingPriority.save_criteria_history", criteria);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveCriteriaHistory() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save provider score history
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveProviderScoreHistory(List<SPNCoverageDTO> coveragelist,Integer spnId) throws DataServiceException{
		try{
			for(SPNCoverageDTO coverage : coveragelist){
				if(null != coverage){
					coverage.setSpnId(spnId);
					getSqlMapClient().insert("network.routingPriority.save_provScore_history", coverage);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveProviderScoreHistory() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**save firm score history
	 * @param coveragelist
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveFirmScoreHistory(List<SPNCoverageDTO> coveragelist,Integer spnId) throws DataServiceException{
		try{
			for(SPNCoverageDTO coverage : coveragelist){
				if(null != coverage){
					coverage.setSpnId(spnId);
					getSqlMapClient().insert("network.routingPriority.save_firmScore_history", coverage);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveFirmScoreHistory() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/**fetch last completed date
	 * @param duplicateList
	 * @param criteriaLevel
	 * @throws DataServiceException
	 */	
	public List<SPNCoverageDTO> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId)throws DataServiceException{
		List<SPNCoverageDTO> dateList = new ArrayList<SPNCoverageDTO>();
		Date completedDate = null;
		Date date = new Date(2000, 01, 01);
		try{
			if(criteriaLevel == SPNBackendConstants.FIRM){
				for(Integer firmId : duplicateList){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("firmId", firmId);
					params.put("buyerId", buyerId);
					
					completedDate = (Date)getSqlMapClient().queryForObject("network.routingPriority.fetch_firm_completedDate", params);
					SPNCoverageDTO coverage = new SPNCoverageDTO();
					coverage.setMemberId(firmId);
					if(null != completedDate){
						coverage.setCompletedDate(completedDate);
					}else{
						coverage.setCompletedDate(date);
					}
					dateList.add(coverage);
				}
			}
			else{
				for(Integer provId : duplicateList){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("provId", provId);
					params.put("buyerId", buyerId);
					
					completedDate = (Date)getSqlMapClient().queryForObject("network.routingPriority.fetch_prov_completedDate", params);
					SPNCoverageDTO coverage = new SPNCoverageDTO();
					coverage.setMemberId(provId);
					if(null != completedDate){
						coverage.setCompletedDate(completedDate);
					}else{
						coverage.setCompletedDate(date);
					}
					dateList.add(coverage);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getCompletedDate() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return dateList;
	}
	
	/**check for alias SPN
	 * @param spnId
	 * @throws DataServiceException
	 */
	public Integer checkForAlias(Integer spnId) throws DataServiceException{
		Integer aliasSpnId = null;
		try{
			aliasSpnId = (Integer)getSqlMapClient().queryForObject("network.routingPriority.getAliasSpn", spnId);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.checkForAlias() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
		return aliasSpnId;
	}

	/**save alias hdr details
	 * @param spnHdr
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public void saveHdrDetailsForAlias(SPNHeader spnHdr, Integer spnId) throws DataServiceException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId",spnId);
		params.put("marketPlaceOverFlow",spnHdr.getMarketPlaceOverFlow());
		params.put("criteriaLevel",spnHdr.getCriteriaLevel());
		params.put("criteriaTimeframe",spnHdr.getCriteriaTimeframe());
		params.put("routingPriorityStatus",spnHdr.getRoutingPriorityStatus());
		
		try{
			getSqlMapClient().update("network.routingPriority.save_alias_hdr_details",params);
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.saveHdrDetailsForAlias() due to "+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
}
