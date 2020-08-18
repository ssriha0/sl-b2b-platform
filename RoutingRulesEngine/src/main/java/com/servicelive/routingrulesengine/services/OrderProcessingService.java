package com.servicelive.routingrulesengine.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.servicelive.domain.routingrules.RoutingRuleHdr;


/**
 * 
 *
 */
public interface OrderProcessingService {

	/**
	 * @param so - serviceOrder object
	 * @param jobcode
	 * @return rule id
	 * @throws Exception 
	 */
	public Integer checkConditionalRoutingQualification(ServiceOrder so, String jobcode) throws Exception;

    /**
     * @param so - serviceOrder object
     * @param jobcode
     * @return rule
     * @throws Exception
     */
	public RoutingRuleHdr getConditionalRoutingRule(ServiceOrder so, String jobcode) throws Exception;

	/**
	 * 
	 * @param itemsToPrice list of skus that need to be priced
	 * @param ruleId - rule that must be applied
	 * @param specialty - specialty category for the skus
	 * @return sku/price pairs for every rule that has price listed in the rule
	 * @throws Exception 
	 */
	public Map<String, BigDecimal> applyConditionalPriceOverrides(List<String> itemsToPrice, Integer ruleId, String specialty) throws Exception;
	
	/**
	 * 
	 * @param soId 
	 * @param spnId spnId
	 * @param isNewSpn
	 * @return list of provider ids that with current spn approved status
	 */
	public List<Integer> getApprovedProviderList (Integer ruleId, Integer spnId, Boolean isNewSpn);

	/**
	 * 
	 * @param soId service order hdr id
	 * @return routing Rule Name
	 */
	//SL 15642
	public List<Integer> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId);
	
	public String getRoutedRuleName(String soId);

	/**
	 * 
	 * @param soId service order id
	 * @return ruleid or null if none found
	 * @throws Exception 
	 */
	public Integer findRuleIdForSO (String soId);

	/**
	 * 
	 * @param ruleId rule id
	 * @param so service order object
	 * @throws Exception 
	 */
	public void associateRuleIdToSo (Integer ruleId, ServiceOrder so) throws Exception;
	
	/**
	 * This method will be used to create routing rule SO association entries
     * @param ruleId
     * @param service order
	 */
	public void updateRoutingRuleId (RoutingRuleHdr ruleHdr, ServiceOrder so) throws Exception;
	
	//SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
    public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance);
    public void setMethodOfRoutingOfSo(String soId,String methodOfRouting);
    public RoutingRuleHdr getProvidersListOfRule(Integer ruleId);
    //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
}
