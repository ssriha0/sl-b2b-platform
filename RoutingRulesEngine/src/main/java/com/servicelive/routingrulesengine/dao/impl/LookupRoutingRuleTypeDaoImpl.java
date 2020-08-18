package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.servicelive.domain.lookup.LookupRoutingRuleType;
import com.servicelive.routingrulesengine.dao.LookupRoutingRuleTypeDao;

public class LookupRoutingRuleTypeDaoImpl extends AbstractBaseDao implements LookupRoutingRuleTypeDao {

	@SuppressWarnings("unchecked")
	public List<LookupRoutingRuleType> findAll() {
		String hql = "from " + LookupRoutingRuleType.class.getSimpleName();
		Query query = this.getEntityManager().createQuery(hql); 
		return query.getResultList();
	}

	public LookupRoutingRuleType findById(Integer id) {
		return this.getEntityManager().find(LookupRoutingRuleType.class, id);
	}

}
