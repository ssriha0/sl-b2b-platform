package com.servicelive.spn.dao.network.impl;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_APPROVED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SP_SPN_REMOVED;
import static com.servicelive.spn.common.SPNBackendConstants.DATE_FORMAT;
import static com.servicelive.spn.common.SPNBackendConstants.NO_EXPIRATION_DATE;

import java.math.BigInteger;
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

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.network.ProviderNetworkStatusOverride;
import com.servicelive.domain.spn.network.SPNServiceProviderState;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNServiceProviderStateDao;

public class SPNServiceProviderStateDaoImpl extends AbstractBaseDao implements SPNServiceProviderStateDao {

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateServiceProviderStatusPerformanceLevel(Integer spnId, Integer serviceProviderId, String performanceLevelChangeComment, LookupPerformanceLevel performanceLevel, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, String modifiedBy) throws Exception {
		SPNServiceProviderState spnServiceProviderState = findById(spnId, serviceProviderId);
		spnServiceProviderState.setModifiedBy(modifiedBy);
		spnServiceProviderState.setPerformanceLevel(performanceLevel);
		spnServiceProviderState.setPerformanceLevelChangeComments(performanceLevelChangeComment);
		spnServiceProviderState.setLookupSPNStatusActionMapper(lookupSPNStatusActionMapper);
		spnServiceProviderState.setModifiedDate(new Date());
		update(spnServiceProviderState);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateServiceProviderStatusOverride(List<Integer> spnIds, Integer serviceProviderId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, String validityDate) throws Exception {
		List<SPNServiceProviderState> spnServiceProviderStates = findByIds(spnIds, serviceProviderId);
		for(SPNServiceProviderState spnServiceProviderState :spnServiceProviderStates){
			// SL-12300 : As per the new requirement the overridden information for provider resource will be inserted in the new table "spnet_provider_network_override"
			LookupSPNWorkflowState previousNetworkStatus = null;
			// SL-12300 : If the existing and new network status are same, the previous_network_status of spnet_provider_network_override will be saved with the previous_network_status of an active entry
			// for the given spnId and provider resource id.
			// If there is no such entry then the hard coded values will be saved for previous_network_status column
			if(spnServiceProviderState.getWfState().getId().equals(state.getId())){
				String hql1 = "from ProviderNetworkStatusOverride o where o.spnHeader.spnId = :spnId and o.serviceProvider.id = :resourceId and o.activeIndicator = :activeIndicator";
				Query query1 = getEntityManager().createQuery(hql1);
				query1.setParameter("spnId", spnServiceProviderState.getServiceProviderStatePk().getSpnHeader().getSpnId());
				query1.setParameter("resourceId", serviceProviderId);
				query1.setParameter("activeIndicator", true);
				List<ProviderNetworkStatusOverride> providerStatusOverride = query1.getResultList();
				if(null != providerStatusOverride && !providerStatusOverride.isEmpty()){
					previousNetworkStatus = providerStatusOverride.get(0).getPreviousNetworkStatus();
				}else{
					if((WF_STATUS_SP_SPN_APPROVED).equals(state.getId()) || (WF_STATUS_SP_SPN_REMOVED).equals(state.getId())){
						previousNetworkStatus = new LookupSPNWorkflowState(WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
					}else if((WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE).equals(state.getId())){
						previousNetworkStatus = new LookupSPNWorkflowState(WF_STATUS_SP_SPN_APPROVED);
					}
				}
			}else{
				previousNetworkStatus = spnServiceProviderState.getWfState();
			}
			spnServiceProviderState.setModifiedBy(modifiedBy);
			spnServiceProviderState.setStatusOverrideComments(overrideComment);
			spnServiceProviderState.setLookupSPNStatusOverrideReason(reason);
			spnServiceProviderState.setLookupSPNStatusActionMapper(lookupSPNStatusActionMapper);
			spnServiceProviderState.setWfState(state);
			spnServiceProviderState.setModifiedDate(new Date());
			// SL-12300
			spnServiceProviderState.setStatusOverrideInd(true);
			update(spnServiceProviderState);
			
			// SL-12300
			// Updating active indicator of spnet_provider_network_override table with 0 for all entries for the given spnId and provider resource id.
			// This is to deactivate all existing overriden data for the given spnId and provider resource id.
			boolean activeIndicator = false;
			String hql2 = "update ProviderNetworkStatusOverride o set o.activeIndicator = :activeIndicator, o.modifiedDate = :now where o.spnHeader.spnId = :spnId and o.serviceProvider.id = :resourceId";
			Query query2 = super.getEntityManager().createQuery(hql2);
			query2.setParameter("activeIndicator", activeIndicator);
			query2.setParameter("now", CalendarUtil.getNow());
			query2.setParameter("spnId", spnServiceProviderState.getServiceProviderStatePk().getSpnHeader().getSpnId());
			query2.setParameter("resourceId", spnServiceProviderState.getServiceProviderStatePk().getServiceProvider().getId());
			query2.executeUpdate();
			
			activeIndicator = true;
			Date expirationDate = new Date();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			ProviderNetworkStatusOverride providerNetworkStatusOverride = new ProviderNetworkStatusOverride();
			providerNetworkStatusOverride.setSpnHeader(spnServiceProviderState.getServiceProviderStatePk().getSpnHeader());
			providerNetworkStatusOverride.setServiceProvider(spnServiceProviderState.getServiceProviderStatePk().getServiceProvider());
			providerNetworkStatusOverride.setCreatedDate(new Date());
			providerNetworkStatusOverride.setModifiedDate(new Date());
			providerNetworkStatusOverride.setActiveIndicator(activeIndicator);
			providerNetworkStatusOverride.setPreviousNetworkStatus(previousNetworkStatus);
			providerNetworkStatusOverride.setOverridedNetworkStatus(state);
			providerNetworkStatusOverride.setCreatedBy(modifiedBy);
			// SL-12300
			// If value for the validity date is 'No expiration date'. Setting expiration date as null and no_expiration_ind as 1.
			// else Setting the expiration date with the given validity date.
			if((NO_EXPIRATION_DATE).equalsIgnoreCase(validityDate)){
				expirationDate = null;
				providerNetworkStatusOverride.setNoExpirationDateInd(true);
			} else{
				expirationDate = formatter.parse(validityDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(expirationDate);
				cal.set(Calendar.HOUR_OF_DAY, 00);
				cal.set(Calendar.MINUTE, 01);
				cal.set(Calendar.SECOND, 00);
				expirationDate = cal.getTime();
				providerNetworkStatusOverride.setNoExpirationDateInd(false);
			}
			providerNetworkStatusOverride.setValidityDate(expirationDate);
			providerNetworkStatusOverride.setStatusOverrideComments(overrideComment);
			if(null != reason){
				providerNetworkStatusOverride.setStatusOverrideReasonId(reason.getId());
			} else{
				providerNetworkStatusOverride.setStatusOverrideReasonId(null);
			}
			save(providerNetworkStatusOverride);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderState> findServiceProviderState(Integer buyerId, Integer serviceProviderId) {
		List<SPNServiceProviderState> spnServiceProviderStates = new ArrayList<SPNServiceProviderState>();
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNServiceProviderState s ");
		hql.append(", SPNBuyer b ");
		hql.append("where ");
		hql.append("s.serviceProviderStatePk.serviceProvider.id = :serviceProviderId ");
		hql.append("and ");
		hql.append("b.buyerId.buyerId = :buyerId ");
		hql.append("and ");
		hql.append("b.spnId.spnId = s.serviceProviderStatePk.spnHeader.spnId ");
		hql.append("and ");
		hql.append("b = some elements(s.serviceProviderStatePk.spnHeader.buyer) ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("buyerId", buyerId);
		query.setParameter("serviceProviderId", serviceProviderId);
		spnServiceProviderStates = (List<SPNServiceProviderState>)query.getResultList();
		
		// SL-20153 : Fetching expiration date for each spn for the service provider
		String findExpirationDateQuery = StringUtils.EMPTY;
		findExpirationDateQuery = findExpirationDateQuery +"SELECT DISTINCT(pno.spn_id), IF(pno.validity_date IS NULL,'indefinitely',CONCAT('until ',DATE_FORMAT(pno.validity_date, '%m/%d/%Y %h:%i %p'))) "
									+ "FROM spnet_provider_network_override pno "
									+ "JOIN spnet_buyer sb ON (pno.spn_id = sb.spn_id AND sb.buyer_id = :buyerId) "
									+ "WHERE pno.service_provider_id = :serviceProviderId AND pno.active_ind = 1 GROUP BY pno.spn_id";
		Query expirationDateQuery = getEntityManager().createNativeQuery(findExpirationDateQuery);
		expirationDateQuery.setParameter("buyerId", buyerId);
		expirationDateQuery.setParameter("serviceProviderId", serviceProviderId);
		Map<Integer, String> expirationDateMap = new HashMap<Integer, String>();
		List<Object[]> expirationDateList = expirationDateQuery.getResultList();
		if(null != expirationDateList && !expirationDateList.isEmpty()){
			for(Object[] expirationDate : expirationDateList){
				expirationDateMap.put((Integer)expirationDate[0], (String)expirationDate[1]);
			}
			for(SPNServiceProviderState spnServiceProviderState : spnServiceProviderStates){
				spnServiceProviderState.setStatusOverrideEffectiveDate(expirationDateMap.get(spnServiceProviderState.getServiceProviderStatePk().getSpnHeader().getSpnId()));
			}
		}
		return spnServiceProviderStates;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderState> findBySpnId(Integer spnId) {
		String hql = "select s from SPNServiceProviderState s where s.serviceProviderStatePk.spnHeader.spnId = :spnId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderState> findBySpnIdAndSPNWorkflowStates(Integer spnId, List<LookupSPNWorkflowState> states) {
		String hql = "select s from SPNServiceProviderState s where s.serviceProviderStatePk.spnHeader.spnId = :spnId and s.wfState in (:states)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("states", states);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public int update(Integer fromSpnId, Integer toSpnId, List<LookupSPNWorkflowState> states) throws Exception {
		String hql = "update SPNServiceProviderState o set o.modifiedDate = :now, o.serviceProviderStatePk.spnHeader = (select h from SPNHeader h where h.spnId = :toSpnId) where o.wfState in (:states) and o.serviceProviderStatePk.spnHeader.spnId = :fromSpnId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("fromSpnId", fromSpnId);
		query.setParameter("toSpnId", toSpnId);
		query.setParameter("states", states);
		query.setParameter("now", CalendarUtil.getNow());
		return query.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SPNServiceProviderState save(SPNServiceProviderState state) throws Exception {
		super.save(state);
		return findById(state.getServiceProviderStatePk().getSpnHeader().getSpnId(), state.getServiceProviderStatePk().getServiceProvider().getId());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private SPNServiceProviderState findById(Integer spnId, Integer serviceProviderId) {
		String hql = "select s from SPNServiceProviderState s where s.serviceProviderStatePk.spnHeader.spnId = :spnId and s.serviceProviderStatePk.serviceProvider.id = :serviceProviderId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("serviceProviderId", serviceProviderId);
		return (SPNServiceProviderState) query.getSingleResult();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	private List<SPNServiceProviderState> findByIds(List<Integer> spnIds, Integer serviceProviderId) {
		String hql = "select s from SPNServiceProviderState s where s.serviceProviderStatePk.spnHeader.spnId in(:spnIds) and s.serviceProviderStatePk.serviceProvider.id = :serviceProviderId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnIds", spnIds);
		query.setParameter("serviceProviderId", serviceProviderId);
		return (List<SPNServiceProviderState>) query.getResultList();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public SPNServiceProviderState update(SPNServiceProviderState state) throws Exception {
		return (SPNServiceProviderState) super.update(state);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int delete(Integer spnId) throws Exception {
		String hql = "delete from SPNServiceProviderState o where o.serviceProviderStatePk.spnHeader.spnId = :spnId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		return query.executeUpdate();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ProviderNetworkStatusOverride providerNetworkStatusOverride) throws Exception {
		super.save(providerNetworkStatusOverride);
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getValidSearchableStatusList() {
		List<String> list = new ArrayList<String>();
		list.add(WF_STATUS_SP_SPN_APPROVED);
		list.add(WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE);
		list.add(WF_STATUS_SP_SPN_REMOVED);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderState> searchForMemberManage(MemberManageSearchCriteriaVO criteria) throws Exception {
		// I know this is not most elegant way of handling criteria Query. Since
		// we are using hibernate jpa implementation which doesnt support JPA
		// QuryBuilder I had to use way
		StringBuilder hql = new StringBuilder("select s from SPNServiceProviderState s , SPNBuyer b  where b.spnId = s.serviceProviderStatePk.spnHeader.spnId ");
		hql.append(" and s.serviceProviderStatePk.spnHeader.aliasOriginalSpnId is null");
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Map<String, String> queryMap = new LinkedHashMap<String, String>();
		boolean shouldIexecuteQuery = false;
		if (criteria != null) {
			if (criteria.getBuyerId() != null) {
				params.put("buyerId", criteria.getBuyerId());
				queryMap.put("buyerId", " AND b.buyerId.buyerId = :buyerId");
				params.put("validStatus", getValidSearchableStatusList());
				queryMap.put("validStatus", " AND s.wfState.id IN (:validStatus) ");

				if (criteria.getServiceProviderIds() != null && criteria.getServiceProviderIds().size() > 0) {
					params.put("serviceProviderIds", criteria.getServiceProviderIds());
					queryMap.put("serviceProviderIds", " AND s.serviceProviderStatePk.serviceProvider.id IN (:serviceProviderIds) ");
					shouldIexecuteQuery = true;
				}
				if (StringUtils.isNotBlank(criteria.getServiceProviderName())) {
					params.put("serviceProName", "%" + criteria.getServiceProviderName() + "%");
					queryMap.put("serviceProName", " AND ((s.serviceProviderStatePk.serviceProvider.contact.lastName LIKE :serviceProName) OR (s.serviceProviderStatePk.serviceProvider.contact.firstName LIKE :serviceProName) )");
					shouldIexecuteQuery = true;
				}
				if (criteria.getFilterCriteria() != null) {

					if (criteria.getFilterCriteria().getStateCode() != null && !("-1".equals(criteria.getFilterCriteria().getStateCode()))) {
						params.put("stateCdFilter", criteria.getFilterCriteria().getStateCode());
						queryMap.put("stateCdFilter", " AND s.serviceProviderStatePk.serviceProvider.location.lookupStates.id = :stateCdFilter");
					}
					if (criteria.getFilterCriteria().getSpnId() != null && criteria.getFilterCriteria().getSpnId().intValue() > 0) {
						params.put("spnIdFilter", criteria.getFilterCriteria().getSpnId());
						queryMap.put("spnIdFilter", " AND s.serviceProviderStatePk.spnHeader.spnId = :spnIdFilter");
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
		return new ArrayList<SPNServiceProviderState>(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.dao.network.SPNServiceProviderStateDao#findAllServiceProForSpnAndFirm(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderState> findAllServiceProForSpnAndFirm(Integer spnId, Integer providerFirmId) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNServiceProviderState s ");
		hql.append(", ServiceProvider vr ");
		hql.append("where ");
		hql.append("s.serviceProviderStatePk.spnHeader.spnId = :spnId  ");
		hql.append("and ");
		hql.append("s.wfState.id = :serviceproApprovedState ");
		hql.append("and ");
		hql.append("vr.providerFirm.id = :providerFirmId ");
		hql.append("and ");
		hql.append("s.serviceProviderStatePk.serviceProvider.id = vr.id  ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("spnId", spnId);
		query.setParameter("providerFirmId", providerFirmId);
		query.setParameter("serviceproApprovedState", WF_STATUS_SP_SPN_APPROVED);
		return query.getResultList();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.dao.network.SPNServiceProviderStateDao#findAllServiceProForSpnAndFirm(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SPNServiceProviderState> findAllServiceProForFirmAndBuyer(Integer buyer, Integer providerFirmId) throws Exception {
		
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNServiceProviderState s ");
		hql.append(", ServiceProvider vr , SPNBuyer b ");
		hql.append("where ");
		hql.append("b.spnId = s.serviceProviderStatePk.spnHeader.spnId ");
		hql.append("and b.buyerId.buyerId = :buyerId and ");
		hql.append("s.wfState.id = :serviceproApprovedState ");
		hql.append("and ");
		hql.append("vr.providerFirm.id = :providerFirmId ");
		hql.append("and ");
		hql.append("s.serviceProviderStatePk.serviceProvider.id = vr.id  ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("buyerId", buyer);
		query.setParameter("providerFirmId", providerFirmId);
		query.setParameter("serviceproApprovedState", WF_STATUS_SP_SPN_APPROVED);
		return query.getResultList();
	}
	
	// SL-12300 : Method to fetch the count of approved service providers of a particular provider firm for all the spn's of a particular buyer.
	// Providers in edited spn and alias spn will not be considered.
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer countOfServiceProForFirmAndBuyer(Integer buyer, Integer providerFirmId) throws Exception {
		String query = StringUtils.EMPTY;
		query = query +"SELECT COUNT(DISTINCT(sps.service_provider_id)) FROM spnet_serviceprovider_state sps "
				+"JOIN spnet_buyer sb ON (sps.spn_id = sb.spn_id AND sb.buyer_id = :buyerId) "
				+"JOIN vendor_resource vr ON (sps.service_provider_id = vr.resource_id AND vr.vendor_id = :providerFirmId) "
				+"JOIN spnet_hdr sh ON (sps.spn_id = sh.spn_id AND is_alias = 0) "
				+"WHERE sps.provider_wf_state = 'SP SPN APPROVED' AND "
				+"sh.spn_id NOT IN (SELECT alias_original_spn_id FROM spnet_hdr WHERE alias_original_spn_id IS NOT NULL AND is_alias = 1)";
		Query providerCountquery = getEntityManager().createNativeQuery(query);
		providerCountquery.setParameter("buyerId", buyer);
		providerCountquery.setParameter("providerFirmId", providerFirmId);
		BigInteger count = (BigInteger) providerCountquery.getSingleResult();
		return count.intValue();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.spn.dao.network.SPNServiceProviderStateDao#updateAllServiceProStatusOverrideForFirmOverride(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String,
	 *      com.servicelive.domain.lookup.LookupSPNStatusOverrideReason,
	 *      com.servicelive.domain.lookup.LookupSPNWorkflowState,
	 *      java.lang.String,
	 *      com.servicelive.domain.lookup.LookupSPNStatusActionMapper)
	 */
	public int updateAllServiceProStatusOverrideForFirmOverride(Integer providerFirmId, String overrideComment, LookupSPNStatusOverrideReason reason, LookupSPNWorkflowState state, String modifiedBy, LookupSPNStatusActionMapper lookupSPNStatusActionMapper, LookupSPNWorkflowState statusOverrideState, List<Integer> spnIds) throws Exception {
		StringBuilder hql = new StringBuilder("update SPNServiceProviderState o   ");
		hql.append(" set o.modifiedDate = :now,");
		hql.append(" o.lookupSPNStatusOverrideReason = :reason, ");
		hql.append(" o.statusOverrideComments = :overrideComment, ");
		if (statusOverrideState != null) {
			hql.append(" o.statusOverrideState = :statusOverrideState, ");
		}
		hql.append(" o.lookupSPNStatusActionMapper = :lookupSPNStatusActionMapper, ");
		hql.append(" o.wfState = :state, ");
		hql.append(" o.modifiedBy = :modifiedBy ");
		hql.append(" where ");
		hql.append(" o.serviceProviderStatePk.spnHeader.spnId in(:spnIds) ");
		hql.append(" and ");
		hql.append(" o.serviceProviderStatePk.serviceProvider.id IN (Select vr.id  FROM ServiceProvider vr WHERE vr.providerFirm.id   = :providerFirmId )  ");
		hql.append(" and o.wfState.id != 'SP SPN REMOVED'  ");
		Query query = super.getEntityManager().createQuery(hql.toString());
		query.setParameter("spnIds", spnIds);
		query.setParameter("providerFirmId", providerFirmId);
		query.setParameter("reason", reason);
		query.setParameter("now", CalendarUtil.getNow());
		query.setParameter("overrideComment", overrideComment);
		query.setParameter("state", state);
		if (statusOverrideState != null) {
			query.setParameter("statusOverrideState", statusOverrideState);
		}
		query.setParameter("modifiedBy", modifiedBy);
		query.setParameter("lookupSPNStatusActionMapper", lookupSPNStatusActionMapper);

		return query.executeUpdate();

	}
}
