package com.servicelive.routingrulesengine.dao;

import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.so.SORoutingRuleAssoc;

/**
 * 
 * @author svanloon
 *
 */
public interface SORoutingRuleAssocDao {

	/**
	 * 
	 * @param soId
	 * @return SORoutingRuleAssoc
	 */
	public SORoutingRuleAssoc findBySoId(String soId);
	
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SORoutingRuleAssoc entity) throws Exception;
	
	public void delete(SORoutingRuleAssoc entity) throws Exception;
	 //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
    void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance);
    void setMethodOfRoutingOfSo(String soId,String methodOfRouting);
   // RoutingRuleHdr getProvidersListOfRule(Integer ruleId);
    //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
}
