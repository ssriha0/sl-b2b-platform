package com.servicelive.routingrulesengine.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.routingrules.RoutingRuleCriteria;
import com.servicelive.routingrulesengine.dao.RoutingRuleCriteriaDao;

public class RoutingRuleCriteriaDaoImpl
	extends AbstractBaseDao
	implements RoutingRuleCriteriaDao
{

	@Transactional(propagation=Propagation.SUPPORTS)
	public void delete(RoutingRuleCriteria criteria)
		throws Exception
	{
		super.delete(criteria);		
	}

}
