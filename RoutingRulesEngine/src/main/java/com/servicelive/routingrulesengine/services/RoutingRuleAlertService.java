package com.servicelive.routingrulesengine.services;

import java.util.List;

import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.detached.RoutingRuleEmailVO;

public interface RoutingRuleAlertService {
	
	/**
	 * Method implements business logic for processing routing rule alerts
	 * @throws Exception
	 */
	public void processAlertsForActiveRules() throws Exception;
	
	
	/**
	 * Method returns list of VO object necessary to construct email
	 * @return
	 * @throws Exception
	 */
	public List<RoutingRuleEmailVO> processActiveRulesForEmail() throws Exception;
	
	/**
	 * 
	 * @param routingRuleHdr
	 * @throws Exception
	 */
	public void processAlertsForOneRule(RoutingRuleHdr routingRuleHdr) throws Exception;

}
