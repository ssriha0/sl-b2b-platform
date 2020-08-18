package com.servicelive.routingrulesengine.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.routingrulesengine.dao.SPNServiceProviderStateDao;

/**
 * 
 *
 */
public class SPNServiceProviderStateDaoImpl extends AbstractBaseDao implements SPNServiceProviderStateDao {

	@SuppressWarnings("unchecked")
	@Transactional ( propagation = Propagation.REQUIRED)
	public List<ServiceProvider> listBySpnIdSPNWorkflowStatesAndProviderFirmId(Integer spnId, String wfStateId, Integer providerFirmId ) {
		
		List<ServiceProvider> eligibleProList = new ArrayList<ServiceProvider>();
		String hql = 
			"SELECT "+  
			"s.* " +
			"FROM " + 
			"spnet_serviceprovider_state s " +
			"JOIN spnet_hdr sh ON (sh.spn_id =:spnId OR sh.alias_original_spn_id =:spnId) " +
			"JOIN vendor_resource vr ON vr.resource_id = s.service_provider_id " +
			"WHERE " +
			"s.spn_id = sh.spn_id "+
			"AND provider_wf_state = :wfStateId " +
			"AND vr.vendor_id = :providerFirmId " +
			"AND vr.mkt_place_ind = 1 " +
			"AND vr.wf_state_id = 6 ";		
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("wfStateId", wfStateId);
		query.setParameter("providerFirmId", providerFirmId);
		logger.info("Query for provider compliance with CAR SPN"+hql);
		Iterator iterator = query.getResultList().iterator();
		 if(iterator!=null) {
			 while(iterator.hasNext()) {
				 Object[] tuple = (Object[]) iterator.next();
				 ServiceProvider trp = new ServiceProvider();
				 trp.setId((Integer)tuple[1]);
				 eligibleProList.add(trp);					 
			 }
		 }
		 logger.info("Result obtained from the query is of size->>"+query.getResultList().size());
		return eligibleProList;
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)
	public boolean listProviderFirmIdBySpnIdAndSPNWorkflowStates(Integer vendorId,String wfStateId,Integer spnId)
	{
		String hql = 
				"select " +
				"s.* " +
				"from " +
				"spnet_provider_firm_state s " +
				"JOIN spnet_hdr sh ON s.spn_id = sh.spn_id AND (sh.spn_id =:spnId OR sh.alias_original_spn_id =:spnId) " +
				"where " +
				"s.provider_wf_state = :wfStateId " +
				"and s.provider_firm_id = :providerFirmId";
				Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("wfStateId", wfStateId);
		query.setParameter("providerFirmId", vendorId);
		logger.info("Query for firm compliance with CAR SPN"+hql);
		logger.info("Result obtained from the query is of size->>"+query.getResultList().size());
		if(query.getResultList().size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
}


