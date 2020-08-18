/**
 * 
 */
package com.servicelive.spn.services.network;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.detached.ProviderMatchingCountsVO;
import com.servicelive.domain.spn.detached.SPNProviderMatchingCountVO;
import com.servicelive.domain.spn.detached.SPNSummaryFilterVO;
import com.servicelive.domain.spn.detached.SPNSummaryVO;
import com.servicelive.domain.spn.network.ExceptionsAndGracePeriodVO;
import com.servicelive.domain.spn.network.ExclusionsVO;
import com.servicelive.domain.spn.network.SPNComplianceVO;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.ComplianceCriteriaVO;
import com.servicelive.spn.common.detached.NetworkHistoryVO;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;
import com.servicelive.spn.common.detached.counterbucket.BucketFactory;
import com.servicelive.spn.common.detached.counterbucket.BucketTranslator;
import com.servicelive.spn.common.detached.counterbucket.BucketType;
import com.servicelive.spn.common.detached.counterbucket.SpnBucket;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.services.BaseServices;
/**
 * @author hoza
 *
 */
public class NetworkSummaryServices extends BaseServices {
	/**
	 * 
	 * @param buyerId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNSummaryVO> getListOfSPNSummaryForBuyer_old (SPNSummaryFilterVO summaryFilterVO) throws Exception {
		// get list of spn names/ids/campaign count
		Integer buyerId = summaryFilterVO.getBuyerId();
		List<SPNSummaryVO> summaryVOList = getSqlMapClient().queryForList("network.spnsummary.getListOfSPNforBuyer", buyerId);
		//create hashmap
		Map<Integer, SPNSummaryVO> summaryVOMapBySpnId = new HashMap<Integer, SPNSummaryVO>();
		Map<Integer, SPNSummaryVO> summaryVOMapByAliasSpnId = new HashMap<Integer, SPNSummaryVO>();
		for(SPNSummaryVO spnSummaryVO: summaryVOList) {
			summaryVOMapBySpnId.put(spnSummaryVO.getSpnId(), spnSummaryVO);
			if(spnSummaryVO.getIsAliasNetwork().booleanValue()) {
				summaryVOMapByAliasSpnId.put(spnSummaryVO.getSpnId(), spnSummaryVO);//I know its going to have same ref of summary VO thats what i want
			}
		}
		//get counts for all the SPNs for the buyer
		updateCountsForSPNsNew(summaryFilterVO, summaryVOMapBySpnId);
		//consolidate those counts for the alais SPNs
		consolidatedSPNSummaryVO(summaryVOMapBySpnId,summaryVOMapByAliasSpnId);
		return getListofColidatedSummary(summaryVOList,summaryVOMapByAliasSpnId);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNSummaryVO> getListOfSPNSummaryForBuyer (SPNSummaryFilterVO summaryFilterVO, Integer maxRows) throws Exception {
		// get list of spn names/ids/campaign count
		Integer buyerId = summaryFilterVO.getBuyerId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		paramMap.put("maxRows", maxRows);
		List<SPNSummaryVO> summaryVOList = getSqlMapClient().queryForList("network.spnsummary.getListOfSPNforBuyer", paramMap);
		//create hashmap
		List<Integer> spnIds = new ArrayList<Integer>();
		Map<Integer, SPNSummaryVO> summaryVOMapBySpnId = getSummaryMapBySPNID(summaryVOList,spnIds);
		//get counts for all the SPNs for the buyer
		updateCountsForSPNsNew(summaryFilterVO, summaryVOMapBySpnId);
		
		
		Map<Integer, SPNSummaryVO> summaryVOMapByAliasSpnId = getSummaryMapByAliasSPNID(summaryVOList);
		//consolidate those counts for the alais SPNs
		//consolidatedSPNSummaryVO(summaryVOMapBySpnId,summaryVOMapByAliasSpnId);
		return getListofColidatedSummary(summaryVOMapBySpnId,summaryVOMapByAliasSpnId,spnIds);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getCountOfSPNforBuyer (Integer buyerId) throws Exception {
		// get list of spn names/ids/campaign count
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buyerId", buyerId);
		Integer count = (Integer)getSqlMapClient().queryForObject("network.spnsummary.getCountOfSPNforBuyer", paramMap);
		
		return count;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getCountOfCompleteSPNs(Integer buyerId) throws Exception {
		Integer count = (Integer)getSqlMapClient().queryForObject("network.spnsummary.getCountOfCompleteSPNs", buyerId);
		
		return count;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getCountOfIncompleteSPNs (Integer buyerId) throws Exception {
		Integer count = (Integer)getSqlMapClient().queryForObject("network.spnsummary.getCountOfIncompleteSPNs", buyerId);
		
		return count;
	}
	
	private Map<Integer, SPNSummaryVO>  getSummaryMapByAliasSPNID(List<SPNSummaryVO> summaryVOList){
		Map<Integer, SPNSummaryVO> summaryVOMapByAliasSpnId = new HashMap<Integer, SPNSummaryVO>();
		for (SPNSummaryVO spnSummaryVO : summaryVOList) {
			if (spnSummaryVO.getIsAliasNetwork().booleanValue()
					&& spnSummaryVO.getOriginalNetworkIdOfAlias() != null) {
				summaryVOMapByAliasSpnId.put(spnSummaryVO.getSpnId(), spnSummaryVO);
			}
		}
		return summaryVOMapByAliasSpnId;
	}
	/**
	 * @param summaryVOList
	 * @param spnIds 
	 * @return
	 */
	private Map<Integer, SPNSummaryVO> getSummaryMapBySPNID(
			List<SPNSummaryVO> summaryVOList, List<Integer> spnIds) {
		Map<Integer, SPNSummaryVO> summaryVOMapBySpnId = new HashMap<Integer, SPNSummaryVO>();
		
		for(SPNSummaryVO spnSummaryVO: summaryVOList) {
			summaryVOMapBySpnId.put(spnSummaryVO.getSpnId(), spnSummaryVO);
			spnIds.add(spnSummaryVO.getSpnId());
			if(spnSummaryVO.getIsAliasNetwork().booleanValue()) {
				String name =  " Edited with effective date (" + CalendarUtil.formatDate(spnSummaryVO.getAliasEffectiveDate(),"MM-dd-yyyy")+ ")";
				spnSummaryVO.setSpnName(name);
			}
		}
		return summaryVOMapBySpnId;
	}
	
	

	private  void  consolidatedSPNSummaryVO(Map<Integer, SPNSummaryVO> summaryVOMapBySpnIdAllSpns , Map<Integer, SPNSummaryVO> summaryVOMapByAliasSpnId ) throws Exception {
		for(Integer aliaspnId : summaryVOMapByAliasSpnId.keySet() ) {
			SPNSummaryVO aliasSVO = summaryVOMapByAliasSpnId.get(aliaspnId);
				Integer originalSPNid = aliasSVO.getOriginalNetworkIdOfAlias();
				if(summaryVOMapBySpnIdAllSpns.containsKey(originalSPNid)) {
					SPNSummaryVO originalSVO =  summaryVOMapBySpnIdAllSpns.get(originalSPNid);
					updateNameForEditedNetwork(originalSVO);
					updateCounts(originalSVO, aliasSVO);
				}
			}
		
	}
	private void updateNameForEditedNetwork(SPNSummaryVO originalSVO ) throws Exception {
		originalSVO.setSpnName(originalSVO.getSpnName() + "(edited)");
	}

	private void  updateCounts(SPNSummaryVO originalSVO, SPNSummaryVO aliasSVO) throws Exception {
	/* I know I could use reflection.. too much over kill for this small known entity in my view */
		originalSVO.addALL(aliasSVO);
	}
	
	
	private List <SPNSummaryVO> getListofColidatedSummary(Map<Integer, SPNSummaryVO> summaryVOMapBySpnId  , Map<Integer, SPNSummaryVO> summaryVOMapByAliasSpnId, List<Integer> spnIds ) throws Exception{

		for(Integer aliaspnId : summaryVOMapByAliasSpnId.keySet() ) {
			SPNSummaryVO aliasSVO = summaryVOMapByAliasSpnId.get(aliaspnId);
			if(aliasSVO.getOriginalNetworkIdOfAlias() != null ) { 
				SPNSummaryVO svo = summaryVOMapBySpnId.get(aliasSVO.getOriginalNetworkIdOfAlias());
				svo.setAliasSPN(aliasSVO);
				summaryVOMapBySpnId.remove(aliaspnId);
			}
			
		}
		List<SPNSummaryVO> list = new ArrayList<SPNSummaryVO>();
		
		for(Integer spnId : spnIds) {
			if(summaryVOMapBySpnId.get(spnId)!=null){
				list.add(summaryVOMapBySpnId.get(spnId));
			}
		}
		
		return list;
		
	}
	

	private List<SPNSummaryVO> getListofColidatedSummary(List<SPNSummaryVO> summaryVOList , Map<Integer, SPNSummaryVO> summaryVOMapByAliasSpnId ) throws Exception{
		
		for(Integer aliaspnId : summaryVOMapByAliasSpnId.keySet() ) {
			SPNSummaryVO aliasSVO = summaryVOMapByAliasSpnId.get(aliaspnId);
			if(summaryVOList.contains(aliasSVO)) {
				summaryVOList.remove(aliasSVO);
			}
		}
		return summaryVOList;
	}

	public static List<Integer> getKeysFromValue(Map<Integer, Integer> hm, Integer value) {
		List<Integer> list = new ArrayList<Integer>();
		for (Integer o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				list.add(o);
			}
		}
		return list;
	}
	
	
	private void updateCountsForSPNsNew(SPNSummaryFilterVO summaryFilterVO, Map<Integer, SPNSummaryVO> summaryVOMapBySpnId) throws SQLException {

		List<SpnMonitorCountVO> spnMonitorCountVOList = getSqlMapClient().queryForList("network.spnsummary.getSPNMonitorCount", summaryFilterVO);
		//SL-19025:Added to avoid the duplicates in firm count display of alias spn in frontend(bottom row)-start
		Map<Integer, Set<Integer>> spnFirmIdMap = new HashMap<Integer, Set<Integer>>(); 
		Map<Integer, Set<Integer>> spnAliasFirmIdMap = new HashMap<Integer, Set<Integer>>(); 
		Map<Integer,List<Integer>> firmIdsFinal = new HashMap<Integer, List<Integer>>(); 
		Map<Integer,Integer> aliasOriginalMap = new HashMap<Integer, Integer>(); 
		List<SpnMonitorCountVO> spnMonitorCountVOListToBeRemoved = new ArrayList<SpnMonitorCountVO>();
		Set<Integer> uniqueFirms =   new HashSet<Integer>();
		Set<Integer> uniqueAliasFirms =   new HashSet<Integer>();
		Set<Integer> uniqueOriginalSpns =   new HashSet<Integer>();
		Set<Integer> uniqueSpns =   new HashSet<Integer>();
		Integer lastSpnId = 0;
		//SL-19025--NEW--need to be reviewed
		// build 2 maps - alias spn ids and the firms, original spn ids and the firms
		for(SpnMonitorCountVO spnMonitorCountVO: spnMonitorCountVOList) {
			
			//reset uniquefirms if spn changed: a new new spn identified after n records of a particular spn
			if(uniqueOriginalSpns.size()>0 && !lastSpnId.equals(spnMonitorCountVO.getSpnId())){
				uniqueFirms =   new HashSet<Integer>();
			}
			//reset uniquefirms if spn changed: a new new spn identified after n records of a particular spn
			if(uniqueSpns.size()>0 && !lastSpnId.equals(spnMonitorCountVO.getSpnId())){
				uniqueAliasFirms =   new HashSet<Integer>();
			}
			//identifying firms or original spn
			if(uniqueOriginalSpns.size()>0 && uniqueOriginalSpns.contains(spnMonitorCountVO.getSpnId()) && spnMonitorCountVO.getProviderFirmSpnState().equals(WF_STATUS_PF_SPN_MEMBER)){
				uniqueFirms.add(spnMonitorCountVO.getProviderFirmId());
			}
			//map2-original spn and unique firms
			if(uniqueFirms.size()>0){
				spnFirmIdMap.put(spnMonitorCountVO.getSpnId(), uniqueFirms);
			}
			//if alias spn identify firms
			if(spnMonitorCountVO.getAliasOriginalSpnId()!=null && null!=spnMonitorCountVO.getProviderFirmSpnState() && spnMonitorCountVO.getProviderFirmSpnState().equals(WF_STATUS_PF_SPN_MEMBER)){
				uniqueAliasFirms.add(spnMonitorCountVO.getProviderFirmId());
				uniqueSpns.add(spnMonitorCountVO.getSpnId());
				uniqueOriginalSpns.add(spnMonitorCountVO.getAliasOriginalSpnId());
				aliasOriginalMap.put(spnMonitorCountVO.getSpnId(), spnMonitorCountVO.getAliasOriginalSpnId());
			}
			//map1-alias spn and unique firms
			if(uniqueAliasFirms.size()>0){
				spnAliasFirmIdMap.put(spnMonitorCountVO.getSpnId(), uniqueAliasFirms);
			}
			lastSpnId = spnMonitorCountVO.getSpnId();
			
		}
		//SL-19025--NEW--need to be reviewed
		//identify the firmIds to be retained in alias after removing duplicates 
		Iterator i = aliasOriginalMap.entrySet().iterator();
	   	 while(i.hasNext()) {
	   		 Map.Entry pairs = (Map.Entry)i.next();
	   		List<Integer> firmIdsInOriginal = new ArrayList<Integer>();
	   		List<Integer> firmIdsInAlias = new ArrayList<Integer>();
	   		logger.info("AlisSPN>>>"+pairs.getKey()+" OriginalSPN>>>"+pairs.getValue());
	   		if(null!=spnAliasFirmIdMap.get(pairs.getKey())){
	   		firmIdsInAlias = new ArrayList<Integer>(spnAliasFirmIdMap.get(pairs.getKey()));
	   		}
	   		logger.info("firmIdsInAlias before removal>>"+firmIdsInAlias.size());
	   		 if(null!=spnFirmIdMap.get(pairs.getValue())){
	   			firmIdsInOriginal = new ArrayList<Integer>(spnFirmIdMap.get(pairs.getValue()));
	   			firmIdsInAlias.removeAll(firmIdsInOriginal);//remove duplicates from alias
	   		 }
	   		logger.info("firmIdsInOriginal >>"+firmIdsInOriginal.size());
	   		logger.info("firmIdsInAlias after removal >>"+firmIdsInAlias.size());
				firmIdsFinal.put((Integer)pairs.getKey(),firmIdsInAlias);//build a map holding alias spn id and the list of firms after removing duplicates
	   		  }
		
	   //SL-19025--NEW--need to be reviewed
		//build the list to be removed from parent list
		for(SpnMonitorCountVO spnMonitorCountVO: spnMonitorCountVOList) {
			if(firmIdsFinal.containsKey(spnMonitorCountVO.getSpnId())){
				List<Integer> firmIdstoBeRetained = firmIdsFinal.get(spnMonitorCountVO.getSpnId());
				if(!firmIdstoBeRetained.contains(spnMonitorCountVO.getProviderFirmId()) && null!=spnMonitorCountVO.getProviderFirmSpnState() && spnMonitorCountVO.getProviderFirmSpnState().equals(WF_STATUS_PF_SPN_MEMBER)){
					spnMonitorCountVOListToBeRemoved.add(spnMonitorCountVO);
				}
				if((null==firmIdstoBeRetained || firmIdstoBeRetained.size()==0) && null!=spnMonitorCountVO.getProviderFirmSpnState() && spnMonitorCountVO.getProviderFirmSpnState().equals(WF_STATUS_PF_SPN_MEMBER)){
					spnMonitorCountVOListToBeRemoved.add(spnMonitorCountVO);
				}
				}
		}
		//logger.info("spnMonitorCountVOListToBeRemoved>>"+spnMonitorCountVOListToBeRemoved);
		//SL-19025--NEW--need to be reviewed
		//remove the duplicates from the parent list
		spnMonitorCountVOList.removeAll(spnMonitorCountVOListToBeRemoved);
		//SL-19025:Added to avoid the duplicates in firm count display of alias spn in frontend(bottom row)-end
		Map<Integer, SpnBucket> mapBySpnId = new HashMap<Integer, SpnBucket>();  
		for(SpnMonitorCountVO spnMonitorCountVO: spnMonitorCountVOList) {
			Integer key = spnMonitorCountVO.getSpnId();
			SpnBucket spnBucket = mapBySpnId.get(key);
			if(spnBucket == null) {
				spnBucket = BucketFactory.createBucket(key);
				mapBySpnId.put(key, spnBucket);
			}
			spnBucket.process(spnMonitorCountVO);
		}

		for(Integer spnId: mapBySpnId.keySet()) {
			SpnBucket spnBucket = mapBySpnId.get(spnId);

			SPNSummaryVO spnSummaryVO = summaryVOMapBySpnId.get(spnId);

			Map<BucketType, ProviderMatchingCountsVO> mapByBucketType = BucketTranslator.translate(spnBucket);
			for(BucketType bucketType:mapByBucketType.keySet()) {
				ProviderMatchingCountsVO vo = mapByBucketType.get(bucketType);
				switch(bucketType) {
					case INVITED_COMPOSITE:
						spnSummaryVO.setInvitedPros(vo);
						break;
					case INTERESTED_COMPOSITE:
						spnSummaryVO.setInterestedPros(vo);
						break;
					case NOT_INTERESTED_COMPOSITE:
						spnSummaryVO.setNotInterestedPros(vo);
						break;
					case APPLIED_COMPOSITE:
						spnSummaryVO.setAppliedPros(vo);
						break;
					case DECLINED_COMPOSITE:
						spnSummaryVO.setDeclinePros(vo);
						break;
					case APPLICATION_INCOMPLETE_COMPOSITE:
						spnSummaryVO.setIncompletePros(vo);
						break;
					case INACTIVE_COMPOSITE:
						spnSummaryVO.setFirmOutOfCompliancePros(vo);
						break;
					case ACTIVE_COMPOSITE:
						spnSummaryVO.setActivePros(vo);
						break;
					case REMOVED_COMPOSITE:
						spnSummaryVO.setRemovedPros(vo);
						break;
					case SPN:
					case PROVIDER_FIRM:
					case SERVICE_PROVIDER:
						throw new RuntimeException("This case should never happen!");
					default:
						break;
					
				}
			}
		}
		addActiveRemovedInactiveCount(summaryFilterVO,summaryVOMapBySpnId);

	}
	
	private void addActiveRemovedInactiveCount(SPNSummaryFilterVO summaryFilterVO,
			Map<Integer, SPNSummaryVO> summaryVOMapBySpnId) throws SQLException 
	{
		List<SPNProviderMatchingCountVO> serviceProSPNSpecificMatchingCountList = getSqlMapClient().queryForList("network.spnsummary.getSPNSpecificServiceProCounts", summaryFilterVO);
		
		for(SPNProviderMatchingCountVO providerMatchingCountExtendVO: serviceProSPNSpecificMatchingCountList) {
			Integer spnId = providerMatchingCountExtendVO.getSpnId();
			SPNSummaryVO spnSummaryVO = summaryVOMapBySpnId.get(spnId);
			if(spnSummaryVO == null) {
				logger.info("didn't find an spnSummaryVO for spnId=" + spnId + ".");
				continue;
			}
			String state = providerMatchingCountExtendVO.getState();
			if(state.equals(SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED)) {
				spnSummaryVO.setActiveServiceProCounts(providerMatchingCountExtendVO.getProviderCounts());		
			} else if(state.equals(SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE)) {
				spnSummaryVO.setOocServiceProCounts(providerMatchingCountExtendVO.getProviderCounts());
			} else if(state.equals(SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED)) {
				spnSummaryVO.setRemovedServiceoProCounts(providerMatchingCountExtendVO.getProviderCounts());
			} 
		}
	}

	/**
	 * @param buyerId
	 * @param summaryVOMapBySpnId
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void updateCountsForSPNs(SPNSummaryFilterVO summaryFilterVO,
			Map<Integer, SPNSummaryVO> summaryVOMapBySpnId) throws SQLException {
		// query for additional detail
		List<SPNProviderMatchingCountVO> providerMatchingCountExtendVOList = getSqlMapClient().queryForList("network.spnsummary.getSummaryInfo", summaryFilterVO);
		// combine data
		for(SPNProviderMatchingCountVO providerMatchingCountExtendVO: providerMatchingCountExtendVOList) {
			Integer spnId = providerMatchingCountExtendVO.getSpnId();
			SPNSummaryVO spnSummaryVO = summaryVOMapBySpnId.get(spnId);
			if(spnSummaryVO == null) {
				logger.info("didn't find an spnSummaryVO for spnId=" + spnId + ".");
				continue;
			}
			String state = providerMatchingCountExtendVO.getState();
			if(state.equals(SPNBackendConstants.WF_STATUS_PF_INVITED_TO_SPN)) {
				spnSummaryVO.setInvitedPros(providerMatchingCountExtendVO);				
			} else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_INTERESTED)) {
				spnSummaryVO.setInterestedPros(providerMatchingCountExtendVO);
			} else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_NOT_INTERESTED)) {
				spnSummaryVO.setNotInterestedPros(providerMatchingCountExtendVO);
			} else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT)) {
				spnSummaryVO.setAppliedPros(providerMatchingCountExtendVO);
			}else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT)) {
				spnSummaryVO.setAppliedPros(providerMatchingCountExtendVO);//yes this is not a Typo we consider APPLICANt and REAPPLICANT as APPLIED guys
			}else if(state.equals(SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE)) {
				spnSummaryVO.setFirmOutOfCompliancePros(providerMatchingCountExtendVO);
			} else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER)) {
				spnSummaryVO.setActivePros(providerMatchingCountExtendVO);
			}else if(state.equals(SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE)) {
				spnSummaryVO.setIncompletePros(providerMatchingCountExtendVO);
			}else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_DECLINED)) {
				spnSummaryVO.setDeclinePros(providerMatchingCountExtendVO);
			}else if(state.equals(SPNBackendConstants.WF_STATUS_PF_SPN_REMOVED_FIRM)) {
				spnSummaryVO.setRemovedPros(providerMatchingCountExtendVO);
			}
		}
		
		List<SPNProviderMatchingCountVO> serviceProSPNSpecificMatchingCountList = getSqlMapClient().queryForList("network.spnsummary.getSPNSpecificServiceProCounts", summaryFilterVO);
		
		for(SPNProviderMatchingCountVO providerMatchingCountExtendVO: serviceProSPNSpecificMatchingCountList) {
			Integer spnId = providerMatchingCountExtendVO.getSpnId();
			SPNSummaryVO spnSummaryVO = summaryVOMapBySpnId.get(spnId);
			if(spnSummaryVO == null) {
				logger.info("didn't find an spnSummaryVO for spnId=" + spnId + ".");
				continue;
			}
			String state = providerMatchingCountExtendVO.getState();
			if(state.equals(SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED)) {
				spnSummaryVO.setActiveServiceProCounts(providerMatchingCountExtendVO.getProviderCounts());		
			} else if(state.equals(SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE)) {
				spnSummaryVO.setOocServiceProCounts(providerMatchingCountExtendVO.getProviderCounts());
			} else if(state.equals(SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED)) {
				spnSummaryVO.setRemovedServiceoProCounts(providerMatchingCountExtendVO.getProviderCounts());
			} 
		}
	}

	/**
	 * 
	 * @param spnId
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<NetworkHistoryVO> getNetworkHistory(Integer spnId) throws Exception {
		List<NetworkHistoryVO> list = getSqlMapClient().queryForList("network.history.getListOfNetworkHistory", spnId);
		// Splitting the comments to obtain the credential name and exception details to show details in network history
		if(list!=null && list.size()>0){
			for(NetworkHistoryVO historyVO : list){
				if(historyVO!=null){
					if(historyVO.getStatus()!=null && historyVO.getStatus().contains(SPNBackendConstants.CREDENTIALS)){
						if(historyVO.getComments()!=null && historyVO.getComments().length()>0){
							String [] arr = historyVO.getComments().split(";");
							if(arr !=null && arr.length>0){
								String credential = arr[0];
								historyVO.setCredential(credential);
								historyVO.setComments(arr[1]);
							}
						}
					}
				}
			}
		}
		return list;
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * @return
	 * @throws SQLException
	 * SL-18018: method to fetch exception types.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ExceptionsAndGracePeriodVO> getSpnExceptionTypes()  throws SQLException {
		return getSqlMapClient().queryForList("network.exceptions.getExceptionTypes",null);
	}

	/**
	 * @return
	 * SL-18018: method to fetch gracePeriods.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ExceptionsAndGracePeriodVO> getGracePeriod() throws SQLException {
		return getSqlMapClient().queryForList("network.exceptions.getGracePeriod",null);
	}

	/**
	 * @return
	 * SL-18018: method to fetch exceptionStates.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getStates() throws SQLException  {
		return getSqlMapClient().queryForList("network.exceptions.getStates",null);
	}

	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch spn exceptions.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public ExclusionsVO getSPNExceptions(Integer spnId) throws SQLException {
		ExclusionsVO exclusionsVO = new ExclusionsVO();
		List<SPNExclusionsVO> companyExceptions = getSqlMapClient().queryForList("network.exceptions.getSPNCompanyExceptions",spnId);
		exclusionsVO.setCompanyExclusions(companyExceptions);
		List<SPNExclusionsVO> resourceExceptions = getSqlMapClient().queryForList("network.exceptions.getSPNResourceExceptions",spnId);
		exclusionsVO.setResourceExclusions(resourceExceptions);
		return exclusionsVO;
	}
	/**
	 * @param exclusionsVO
	 * @throws SQLException
	 * 
	 * method to save exceptions
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public void saveException(SPNExclusionsVO exclusionsVO) throws SQLException {
		if(exclusionsVO!=null){
			// to check whether the credential already has a exception applied 
			Integer count = (Integer)getSqlMapClient().queryForObject("network.exceptions.getExceptions", exclusionsVO);
			// to check whether the credential already has same exception applied 
			Integer expCount = (Integer)getSqlMapClient().queryForObject("network.exceptions.getSameExceptions", exclusionsVO);
			if(count==0){
				addNewExceptions(exclusionsVO);
				
			}
			else if(expCount == 0){
				updateExceptions(exclusionsVO);
				
			}
		}


	}
	
	/**
	 * @param exclusionsVO
	 * @throws SQLException
	 * 
	 * to update exceptions
	 * 
	 */
	private void updateExceptions(SPNExclusionsVO exclusionsVO) throws SQLException {
		
		// in case of grace exception
		if(exclusionsVO.getExceptionTypeId()==1){
			String desc = SPNBackendConstants.CRED_GRACE_UPDATE;
			String comments = exclusionsVO.getCredentialType();

			if(exclusionsVO.getCredentialCategory()!=null && StringUtils.isNotBlank(exclusionsVO.getCredentialCategory())){
				comments = comments +SPNBackendConstants.CRED_TYPE_CATEGORY_SEPARATOR+exclusionsVO.getCredentialCategory();
			}
			comments = comments + SPNBackendConstants.CRED_EXCEPTION_SEPARATOR +  SPNBackendConstants.CRED_GRACE_ALLOWED + exclusionsVO.getExceptionValue()+SPNBackendConstants.CRED_GRACE_DAYS;
			exclusionsVO.setDescription(desc);
			exclusionsVO.setComments(comments);
		}
		
		// in case of state exception

		else if(exclusionsVO.getExceptionTypeId()==2){
			String desc = SPNBackendConstants.CRED_STATE_UPDATE;
			String comments = exclusionsVO.getCredentialType();

			if(exclusionsVO.getCredentialCategory()!=null && StringUtils.isNotBlank(exclusionsVO.getCredentialCategory())){
				comments = comments +SPNBackendConstants.CRED_TYPE_CATEGORY_SEPARATOR+exclusionsVO.getCredentialCategory();
			}
			comments = comments + SPNBackendConstants.CRED_EXCEPTION_SEPARATOR +  SPNBackendConstants.CRED_STATE_ALLOWED +exclusionsVO.getExceptionValue();
			exclusionsVO.setDescription(desc);
			exclusionsVO.setComments(comments);
		}
		getSqlMapClient().update("network.exceptions.updateExceptions",exclusionsVO);
		getSqlMapClient().insert("network.exceptions.saveNetworkHistory",exclusionsVO);
	}

	/**
	 * @param exclusionsVO
	 * @throws SQLException
	 * add new exceptions
	 * 
	 */
	private void addNewExceptions(SPNExclusionsVO exclusionsVO) throws SQLException {
		// setting exceptions Ind
		getSqlMapClient().update("network.exceptions.saveExceptionInd",exclusionsVO.getSpnId());
		//adding new exceptions
		getSqlMapClient().insert("network.exceptions.saveExceptions",exclusionsVO);
		
		// in case of grace exception
		if(exclusionsVO.getExceptionTypeId()==1){
			String desc = SPNBackendConstants.CRED_GRACE_ADD;
			String comments = exclusionsVO.getCredentialType();
			if(exclusionsVO.getCredentialCategory()!=null && StringUtils.isNotBlank(exclusionsVO.getCredentialCategory())){
				comments = comments +SPNBackendConstants.CRED_TYPE_CATEGORY_SEPARATOR+exclusionsVO.getCredentialCategory();
			}
			exclusionsVO.setDescription(desc);
			comments = comments+ SPNBackendConstants.CRED_EXCEPTION_SEPARATOR +  SPNBackendConstants.CRED_GRACE_ALLOWED +exclusionsVO.getExceptionValue()+SPNBackendConstants.CRED_GRACE_DAYS;
			exclusionsVO.setComments(comments);
		}
		
		// in case of state exception
		else if(exclusionsVO.getExceptionTypeId()==2){
			String desc = SPNBackendConstants.CRED_STATE_ADD;
			String comments = exclusionsVO.getCredentialType();
			if(exclusionsVO.getCredentialCategory()!=null && StringUtils.isNotBlank(exclusionsVO.getCredentialCategory())){
				comments = comments +SPNBackendConstants.CRED_TYPE_CATEGORY_SEPARATOR+exclusionsVO.getCredentialCategory();
			}
			comments = comments +SPNBackendConstants.CRED_EXCEPTION_SEPARATOR +  SPNBackendConstants.CRED_STATE_ALLOWED +exclusionsVO.getExceptionValue();
			exclusionsVO.setDescription(desc);
			exclusionsVO.setComments(comments);
		}
		getSqlMapClient().insert("network.exceptions.saveNetworkHistory",exclusionsVO);
	}

	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws SQLException {
		List<SPNComplianceVO> complianceList = getSqlMapClient().queryForList("network.compliance.getFirmCompliance",complianceCriteriaVO);
		return complianceList;
		}
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getFirmComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws SQLException {
		Integer count =(Integer) getSqlMapClient().queryForObject("network.compliance.getFirmComplianceCount",complianceCriteriaVO);
		return count;
		}
	
	
	public Date getFirmComplianceDate() throws SQLException {
		Date date= (Date) getSqlMapClient().queryForObject("network.compliance.getFirmComplianceDate",null);
		return date;	
	}
	public Date getProviderComplianceDate() throws SQLException {
		Date date= (Date) getSqlMapClient().queryForObject("network.compliance.getProviderComplianceDate",null);
		return date;	
	}
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws SQLException {
		List<SPNComplianceVO> complianceList = getSqlMapClient().queryForList("network.compliance.getProviderCompliance",complianceCriteriaVO);
		return complianceList;
		}
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getProviderComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws SQLException {
		Integer count =(Integer) getSqlMapClient().queryForObject("network.compliance.getProviderComplianceCount",complianceCriteriaVO);
		return count;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getRequirements(ComplianceCriteriaVO complianceCriteriaVO) throws SQLException {
		List<SPNComplianceVO> complianceList = getSqlMapClient().queryForList("network.compliance.getRequirements",complianceCriteriaVO);
		return complianceList;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<SPNComplianceVO> getRequirementsForProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws SQLException {
		List<SPNComplianceVO> complianceList = getSqlMapClient().queryForList("network.compliance.getRequirementsForProviderCompliance",complianceCriteriaVO);
		return complianceList;
		}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getMarkets() throws SQLException {
		List<String> complianceList = getSqlMapClient().queryForList("network.compliance.getMarkets",null);
		return complianceList;
		}
	
}
