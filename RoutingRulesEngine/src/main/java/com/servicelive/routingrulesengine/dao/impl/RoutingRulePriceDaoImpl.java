package com.servicelive.routingrulesengine.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.routingrules.RoutingRulePrice;
import com.servicelive.routingrulesengine.dao.RoutingRulePriceDao;

public class RoutingRulePriceDaoImpl
	extends AbstractBaseDao
	implements RoutingRulePriceDao
{

	@Transactional(propagation=Propagation.SUPPORTS)
	public void delete(RoutingRulePrice price)
		throws Exception
	{
		super.delete(price);		
	}

}
