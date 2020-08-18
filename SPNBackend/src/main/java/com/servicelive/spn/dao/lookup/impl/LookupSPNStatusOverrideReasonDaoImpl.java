package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.spn.network.ProviderFirmNetworkStatusOverride;
import com.servicelive.domain.spn.network.ProviderNetworkStatusOverride;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNStatusOverrideReasonDao;

public class LookupSPNStatusOverrideReasonDaoImpl extends AbstractBaseDao implements LookupSPNStatusOverrideReasonDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<LookupSPNStatusOverrideReason> findAll(int... rowStartIdxAndCount) throws Exception {
		return (List<LookupSPNStatusOverrideReason>) super.findAll("LookupSPNStatusOverrideReason", rowStartIdxAndCount);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<LookupSPNStatusOverrideReason> findByWfState(String wfState, String currentWfState) { 
		String hql = "from LookupSPNStatusOverrideReason r where r.currentlookupSPNWorkflowState.id = :currentWfState and r.newlookupSPNWorkflowState.id = :wfState";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("currentWfState", currentWfState);
		query.setParameter("wfState", wfState);
		return query.getResultList();
	}
	
	// SL-12300 : Method to find the previous network status for a given spn id and provider firm id from spnet_provider_firm_network_override table.
	@Transactional
	public String findLastFirmNetworkStatus(Integer spnId, Integer providerFirmId) { 
		String hql = "from ProviderFirmNetworkStatusOverride p where p.providerFirm.id = :providerFirmId and p.spnHeader.spnId = :spnId and p.activeIndicator = :activeIndicator";
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("spnId", spnId);
		query.setParameter("providerFirmId", providerFirmId);
		query.setParameter("activeIndicator", true);
		List<ProviderFirmNetworkStatusOverride> firmNetworkStatusOverride = query.getResultList();
		if(null != firmNetworkStatusOverride && !firmNetworkStatusOverride.isEmpty()){
			return firmNetworkStatusOverride.get(0).getPreviousNetworkStatus().getId().toString();
		}else{
			return null;
		}
	}
	
	// SL-12300 : Method to find the previous network status for a given spn id and provider resource id from spnet_provider_network_override table.
	@Transactional
	public String findLastProviderNetworkStatus(Integer spnId, Integer providerResourceId) { 
		String hql = "from ProviderNetworkStatusOverride p where p.serviceProvider.id = :serviceProviderId and p.spnHeader.spnId = :spnId and p.activeIndicator = :activeIndicator";
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("spnId", spnId);
		query.setParameter("serviceProviderId", providerResourceId);
		query.setParameter("activeIndicator", true);
		List<ProviderNetworkStatusOverride> providerNetworkStatusOverride = query.getResultList();
		if(null != providerNetworkStatusOverride && !providerNetworkStatusOverride.isEmpty()){
			return providerNetworkStatusOverride.get(0).getPreviousNetworkStatus().getId().toString();
		}else{
			return null;
		}
	}
}
