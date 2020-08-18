package com.servicelive.spn.dao.network.impl;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_REMOVED_FIRM;
import static com.servicelive.spn.common.SPNBackendConstants.DATE_FORMAT;
import static com.servicelive.spn.common.SPNBackendConstants.NO_EXPIRATION_DATE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.network.ProviderFirmNetworkStatusOverride;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.spn.network.SPNProviderFirmStatePk;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNProviderFirmStateDao;

/**
 * 
 * @author svanloon
 * 
 */
public class SPNProviderFirmStateDaoImpl extends AbstractBaseDao implements SPNProviderFirmStateDao {

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState lock(String reviewedByUserName, Integer spnId, Integer providerFirmId, Date originalModifiedDate) throws Exception {
		String hql = "update SPNProviderFirmState o set o.reviewedBy = (select u from User u where u.username = :reviewedByUserName), o.reviewedDate = :now, o.modifiedDate = :now where o.providerFirmStatePk.spnHeader.spnId = :spnId and o.providerFirmStatePk.providerFirm.id = :providerFirmId and o.modifiedDate = :modifiedDate";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("providerFirmId", providerFirmId);
		query.setParameter("modifiedDate", originalModifiedDate);
		query.setParameter("reviewedByUserName", reviewedByUserName);
		// query.setParameter("user", user);
		query.setParameter("now", CalendarUtil.getNow());
		int i = query.executeUpdate();
		if (i == 0) {
			return null; // this record was picked up by someone else.
		} else if (i == 1) {
			return findBySpnIdAndProviderFirmId(spnId, providerFirmId);
		}

		throw new RuntimeException("Code was supposed to only support updating one or zero rows, but it updated " + i + " rows.");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int copyProviderFirmState(Integer fromSpnId, Integer toSpnId, List<LookupSPNWorkflowState> states) throws Exception {
		String hql = "update SPNProviderFirmState o set o.modifiedDate = :now, o.providerFirmStatePk.spnHeader = (select h from SPNHeader h where h.spnId = :toSpnId) where o.wfState in (:states) and o.providerFirmStatePk.spnHeader.spnId = :fromSpnId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("fromSpnId", fromSpnId);
		query.setParameter("toSpnId", toSpnId);
		query.setParameter("states", states);
		query.setParameter("now", CalendarUtil.getNow());
		return query.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderFirmState> findBySpnIdAndProviderFirmId(List<Integer> spnIds, Integer providerFirmId) {
		String hql = "from SPNProviderFirmState o where o.providerFirmStatePk.spnHeader.spnId in (:spnIds) and o.providerFirmStatePk.providerFirm.id = :providerFirmId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnIds", spnIds);
		query.setParameter("providerFirmId", providerFirmId);
		return (List<SPNProviderFirmState>) query.getResultList();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState findBySpnIdAndProviderFirmId(Integer spnId, Integer providerFirmId) {
		String hql = "from SPNProviderFirmState o where o.providerFirmStatePk.spnHeader.spnId = :spnId and o.providerFirmStatePk.providerFirm.id = :providerFirmId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("providerFirmId", providerFirmId);
		return (SPNProviderFirmState) query.getSingleResult();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState save(SPNProviderFirmState spnProviderFirmState) throws Exception {
		super.save(spnProviderFirmState);
		return findBySpnIdAndProviderFirmId(spnProviderFirmState.getProviderFirmStatePk().getSpnHeader().getSpnId(), spnProviderFirmState.getProviderFirmStatePk().getProviderFirm().getId());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNProviderFirmState update(SPNProviderFirmState spnProviderFirmState) throws Exception {
		return (SPNProviderFirmState) super.update(spnProviderFirmState);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ProviderFirmNetworkStatusOverride providerFirmNetworkStatusOverride) throws Exception {
		super.save(providerFirmNetworkStatusOverride);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderFirmState> findBySpnId(Integer spnId) throws Exception {
		String hql = "from SPNProviderFirmState o where o.providerFirmStatePk.spnHeader.spnId = :spnId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderFirmState> findBySpnIdAndSPNWorkFlowStates(Integer spnId, List<LookupSPNWorkflowState> states) throws Exception {
		String hql = "from SPNProviderFirmState o where o.providerFirmStatePk.spnHeader.spnId = :spnId and o.wfState in (:states)";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("states", states);
		return query.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int delete(Integer spnId) throws Exception {
		String hql = "delete from SPNProviderFirmState o where o.providerFirmStatePk.spnHeader.spnId = :spnId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		return query.executeUpdate();

	}

	public SPNProviderFirmState findByPrimaryKey(Integer spnId, Integer providerFirmId) throws Exception {
		SPNProviderFirmStatePk pk = new SPNProviderFirmStatePk(spnId, providerFirmId);
		Object result = super.findById(SPNProviderFirmState.class, pk);
		if (null != result) {
			return (SPNProviderFirmState) result;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<SPNProviderFirmState> findProviderFirmStatus(Integer buyerId, Integer providerFirmId, List<LookupSPNWorkflowState> states) {
		List<SPNProviderFirmState> spnProviderFirmStates = new ArrayList<SPNProviderFirmState>();
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNProviderFirmState s ");
		hql.append(", SPNBuyer b ");
		hql.append("where ");
		hql.append("s.providerFirmStatePk.providerFirm.id = :providerFirmId ");
		hql.append("and ");
		hql.append("b.buyerId.buyerId = :buyerId ");
		hql.append("and ");
		hql.append("b.spnId.spnId = s.providerFirmStatePk.spnHeader.spnId ");
		hql.append("and ");
		hql.append("b = some elements(s.providerFirmStatePk.spnHeader.buyer) ");
		hql.append("and ");
		hql.append("s.wfState in (:states)");
		
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("buyerId", buyerId);
		query.setParameter("providerFirmId", providerFirmId);
		query.setParameter("states", states);
		spnProviderFirmStates = (List<SPNProviderFirmState>)query.getResultList();
		
		// SL-20153 : Fetching expiration date for each spn for the provider firm
		String findExpirationDateQuery = StringUtils.EMPTY;
		findExpirationDateQuery = findExpirationDateQuery +"SELECT DISTINCT(fno.spn_id), IF(fno.validity_date IS NULL,'indefinitely',CONCAT('until ',DATE_FORMAT(fno.validity_date, '%m/%d/%Y %h:%i %p'))) "
									+ "FROM spnet_provider_firm_network_override fno "
									+ "JOIN spnet_buyer sb ON (fno.spn_id = sb.spn_id AND sb.buyer_id = :buyerId) "
									+ "WHERE fno.provider_firm_id = :providerFirmId AND fno.active_ind = 1 GROUP BY fno.spn_id";
		Query expirationDateQuery = getEntityManager().createNativeQuery(findExpirationDateQuery);
		expirationDateQuery.setParameter("buyerId", buyerId);
		expirationDateQuery.setParameter("providerFirmId", providerFirmId);
		Map<Integer, String> expirationDateMap = new HashMap<Integer, String>();
		List<Object[]> expirationDateList = expirationDateQuery.getResultList();
		if(null != expirationDateList && !expirationDateList.isEmpty()){
			for(Object[] expirationDate : expirationDateList){
				expirationDateMap.put((Integer)expirationDate[0], (String)expirationDate[1]);
			}
			for(SPNProviderFirmState spnProviderFirmState : spnProviderFirmStates){
				spnProviderFirmState.setStatusOverrideEffectiveDate(expirationDateMap.get(spnProviderFirmState.getProviderFirmStatePk().getSpnHeader().getSpnId()));
			}
		}
		return spnProviderFirmStates;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProviderFirmStatusOverride(Integer providerFirmId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, List<Integer> spnIds, String validityDate) throws Exception {
		List<SPNProviderFirmState> spnProviderFirmStates = findBySpnIdAndProviderFirmId(spnIds, providerFirmId);
		for(SPNProviderFirmState spnProviderFirmState:spnProviderFirmStates){
			// SL-12300 : As per the new requirement the overriden information for provider resource will be inserted in the new table "spnet_provider_firm_network_override"
			LookupSPNWorkflowState previousNetworkStatus = null;
			// SL-12300 : If the existing and new network status are same, the previous_network_status of spnet_provider_firm_network_override will be saved with the previous_network_status of an active entry
			// for the given spnId and provider firm id.
			// If there is no such entry then the hardcoded values will be saved for previous_network_status column
			if(spnProviderFirmState.getWfState().getId().equals(state.getId())){
				String hql1 = "from ProviderFirmNetworkStatusOverride o where o.spnHeader.spnId = :spnId and o.providerFirm.id = :vendorId and o.activeIndicator = :activeIndicator";
				Query query1 = getEntityManager().createQuery(hql1);
				query1.setParameter("spnId", spnProviderFirmState.getProviderFirmStatePk().getSpnHeader().getSpnId());
				query1.setParameter("vendorId", providerFirmId);
				query1.setParameter("activeIndicator", true);
				List<ProviderFirmNetworkStatusOverride> firmStatusOverride = query1.getResultList();
				if(null != firmStatusOverride && !firmStatusOverride.isEmpty()){
					previousNetworkStatus = firmStatusOverride.get(0).getPreviousNetworkStatus();
				}else{
					if((WF_STATUS_PF_SPN_MEMBER).equals(state.getId()) || (WF_STATUS_PF_SPN_REMOVED_FIRM).equals(state.getId())){
						previousNetworkStatus = new LookupSPNWorkflowState(WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE);
					}else if((WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE).equals(state.getId())){
						previousNetworkStatus = new LookupSPNWorkflowState(WF_STATUS_PF_SPN_MEMBER);
					}
				}	
			}else{
				previousNetworkStatus = spnProviderFirmState.getWfState();
			} 
			spnProviderFirmState.setModifiedBy(modifiedBy);
			// need to correct
			if (reason != null) {
				spnProviderFirmState.setStatusOverrideState(state);
			} else {
				spnProviderFirmState.setStatusOverrideState(null);
			}
			spnProviderFirmState.setStatusOverrideComments(overrideComment);
			spnProviderFirmState.setLookupSPNStatusOverrideReason(reason);
			spnProviderFirmState.setWfState(state);
			spnProviderFirmState.setLookupSPNStatusActionMapper(lookupSPNStatusActionMapper);
			
			// Corrected the change date. 
			spnProviderFirmState.setModifiedDate(new Date());
			// SL-12300
			spnProviderFirmState.setStatusOverrideInd(true);
			update(spnProviderFirmState);
			
			// SL-12300
			// Updating active indicator of spnet_provider_firm_network_override table with 0 for all entries for the given spnId and provider firm id.
			// This is to deactivate all existing overriden data for the given spnId and provider firm id.
			boolean activeIndicator = false;
			String hql2 = "update ProviderFirmNetworkStatusOverride o set o.activeIndicator = :activeIndicator, o.modifiedDate = :now where o.spnHeader.spnId = :spnId and o.providerFirm.id = :vendorId";
			Query query2 = super.getEntityManager().createQuery(hql2);
			query2.setParameter("activeIndicator", activeIndicator);
			query2.setParameter("now", CalendarUtil.getNow());
			query2.setParameter("spnId", spnProviderFirmState.getProviderFirmStatePk().getSpnHeader().getSpnId());
			query2.setParameter("vendorId", spnProviderFirmState.getProviderFirmStatePk().getProviderFirm().getId());
			query2.executeUpdate();
			
			activeIndicator = true;
			Date expirationDate = new Date();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			ProviderFirmNetworkStatusOverride firmNetworkStatusOverride = new ProviderFirmNetworkStatusOverride();
			firmNetworkStatusOverride.setSpnHeader(spnProviderFirmState.getProviderFirmStatePk().getSpnHeader());
			firmNetworkStatusOverride.setProviderFirm(spnProviderFirmState.getProviderFirmStatePk().getProviderFirm());
			firmNetworkStatusOverride.setCreatedDate(new Date());
			firmNetworkStatusOverride.setModifiedDate(new Date());
			firmNetworkStatusOverride.setActiveIndicator(activeIndicator);
			firmNetworkStatusOverride.setPreviousNetworkStatus(previousNetworkStatus);
			firmNetworkStatusOverride.setOverridedNetworkStatus(state);
			firmNetworkStatusOverride.setCreatedBy(modifiedBy);
			// SL-12300
			// If value for the validity date is 'No expiration date'. Setting expiration date as null and no_expiration_ind as 1.
			// else Setting the expiration date with the given validity date.
			if((NO_EXPIRATION_DATE).equalsIgnoreCase(validityDate)){
				expirationDate = null;
				firmNetworkStatusOverride.setNoExpirationDateInd(true);
			} else{
				expirationDate = formatter.parse(validityDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(expirationDate);
				cal.set(Calendar.HOUR_OF_DAY, 00);
				cal.set(Calendar.MINUTE, 01);
				cal.set(Calendar.SECOND, 00);
				expirationDate = cal.getTime();
				firmNetworkStatusOverride.setNoExpirationDateInd(false);
			}
			firmNetworkStatusOverride.setValidityDate(expirationDate);
			firmNetworkStatusOverride.setStatusOverrideComments(overrideComment);
			if(null != reason){
				firmNetworkStatusOverride.setStatusOverrideReasonId(reason.getId());
			} else{
				firmNetworkStatusOverride.setStatusOverrideReasonId(null);
			}
			save(firmNetworkStatusOverride);
		}
	}

	public List<String> getValidSearchableStatusList() {
		List<String> list = new ArrayList<String>();
		list.add(WF_STATUS_PF_SPN_MEMBER);
		list.add(WF_STATUS_PF_SPN_REMOVED_FIRM);
		list.add(WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNProviderFirmState> searchForMemberManage(MemberManageSearchCriteriaVO criteria) throws Exception {
		// I know this is not most elegant way of handling criteria Query. Since
		// we are using hibernate jpa implementation which doesnt support JPA
		// QuryBuilder I had to use way

		StringBuilder hql = new StringBuilder("select s from SPNProviderFirmState s, SPNBuyer b  where b.spnId = s.providerFirmStatePk.spnHeader.spnId");
		hql.append(" and s.providerFirmStatePk.spnHeader.aliasOriginalSpnId is null");
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Map<String, String> queryMap = new LinkedHashMap<String, String>();
		boolean shouldIexecuteQuery = false;
		if (criteria != null) {
			if (criteria.getBuyerId() != null) {
				params.put("buyerId", criteria.getBuyerId());
				queryMap.put("buyerId", " AND b.buyerId.buyerId = :buyerId");
				params.put("validStatus", getValidSearchableStatusList());
				queryMap.put("validStatus", " AND s.wfState.id IN (:validStatus) ");

				if (criteria.getProviderFirmIds() != null && criteria.getProviderFirmIds().size() > 0) {
					params.put("providerFirmIds", criteria.getProviderFirmIds());
					queryMap.put("providerFirmIds", " AND s.providerFirmStatePk.providerFirm.id IN (:providerFirmIds)");
					shouldIexecuteQuery = true;
				}
				if (StringUtils.isNotBlank(criteria.getProviderFirmName())) {
					params.put("proFirmName", "%" + criteria.getProviderFirmName() + "%");
					queryMap.put("proFirmName", " AND s.providerFirmStatePk.providerFirm.businessName LIKE :proFirmName ");
					shouldIexecuteQuery = true;
				}

				if (criteria.getFilterCriteria() != null) {

					if (criteria.getFilterCriteria().getStateCode() != null && !("-1".equals(criteria.getFilterCriteria().getStateCode()))) {

						int startIndex = hql.indexOf("where");
						hql.insert(startIndex, " , ProviderFirmLocation pl ");
						params.put("stateCdFilter", criteria.getFilterCriteria().getStateCode());
						queryMap.put("stateCdFilter", " AND s.providerFirmStatePk.providerFirm = pl.id.providerFirm  AND  pl.id.location.lookupLocationTypes.id = 1 AND pl.id.location.lookupStates.id = :stateCdFilter");
					}

					if (criteria.getFilterCriteria().getSpnId() != null && criteria.getFilterCriteria().getSpnId().intValue() > 0) {
						params.put("spnIdFilter", criteria.getFilterCriteria().getSpnId());
						queryMap.put("spnIdFilter", " AND s.providerFirmStatePk.spnHeader.spnId = :spnIdFilter");
					}

				}

			}
		}

		if (shouldIexecuteQuery) {
			for (String id : queryMap.keySet()) {
				hql.append(queryMap.get(id));
			}
			logger.info("HQL for MMSearch MM#" + hql.toString() + "#MM");
			Query query = getEntityManager().createQuery(hql.toString());
			for (String id : params.keySet()) {
				query.setParameter(id, params.get(id));
			}

			return query.getResultList();
		}
		return new ArrayList<SPNProviderFirmState>(0);
	}
	
	//SL-12300 : Method to fetch SPN edit information (isAlias and effectiveDate)
	public Map<String, Object> getSPNEditInfo(Integer spnId) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNHeader s ");
		hql.append("where ");
		hql.append("s.aliasOriginalSpnId = :spnId ");		
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("spnId", spnId);
		List<SPNHeader> spnHeaderList = (List<SPNHeader>)query.getResultList();
		if(null != spnHeaderList && !spnHeaderList.isEmpty()){
			map.put("isAliasInd", spnHeaderList.get(0).getIsAlias());
			map.put("effectiveDate", spnHeaderList.get(0).getEffectiveDate());
		} else{
			map = null;
		}
		return map;
	}
}
