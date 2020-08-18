package com.servicelive.routingrulesengine.dao.impl;

import com.servicelive.domain.routingrules.RoutingRuleAlert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.servicelive.routingrulesengine.dao.RoutingRuleAlertDao;

/**
 * 
 *
 */
public class RoutingRuleAlertDaoImpl extends AbstractBaseDao implements RoutingRuleAlertDao {
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void saveAndUpdate(RoutingRuleAlert entity) throws Exception {
		save(entity);	
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void delete(RoutingRuleAlert entity) throws Exception {
		getEntityManager().remove(entity);
	}

}
