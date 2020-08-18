package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.routingrulesengine.dao.OldSPNNetworkDao;

/**
 * 
 * @author svanloon
 *
 */
public class OldSPNNetworkDaoImpl extends AbstractBaseDao implements OldSPNNetworkDao {

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> findBySpnIdAndRoutingRuleHdrId(Integer spnId, Integer routingRuleHdrId){
		String hql = "select n.serviceProvider.id from OldSPNNetwork n, RoutingRuleVendor rrv where rrv.vendor.id = n.serviceProvider.providerFirm.id and n.spnId = :spnId and rrv.routingRuleHdr.routingRuleHdrId = :routingRuleHdrId and n.spnStatusId = 40 and n.serviceProvider.mktPlaceInd = 1";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("routingRuleHdrId", routingRuleHdrId);
		return query.getResultList();
	}
}
