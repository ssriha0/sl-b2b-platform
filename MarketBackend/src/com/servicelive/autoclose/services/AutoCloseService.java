package com.servicelive.autoclose.services;

import java.util.List;

import com.newco.marketplace.dto.autoclose.AutoCloseRuleExclusionDTO;
import com.newco.marketplace.dto.autoclose.ManageAutoCloseRulesDTO;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteria;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteriaHistory;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;



/**
 * 
 *
 */
public interface AutoCloseService {

	ManageAutoCloseRulesDTO getAutoCloseRulesForBuyer(Integer buyerId);

	AutoCloseRuleCriteria getAutoCloseRuleCriteria(Integer criteriaId);

	void saveAutoCloseRuleCriteria(AutoCloseRuleCriteria autoCloseRuleCriteriaOld, AutoCloseRuleCriteria autoCloseRuleCriteriaChanged);

	List<AutoCloseRuleCriteriaHistory> updateMaxprice(Integer parseInt, String changedPrice, String modifiedBy);

	List<AutoCloseRuleExclusionDTO> getFirmExclusionList(Integer autoCloseRuleHdrId, boolean activeInd);

	List<AutoCloseRuleExclusionDTO> getProviderExclusionList(Integer autoCloseRuleHdrId, boolean activeInd);

	//void updateFirmExclusionList(Integer ruleAssocId, boolean activeInd, String comment);

	void updateProviderExclusionList(Integer ruleAssocId, boolean activeInd, String[] comment, String modifiedBy);

	List<ProviderFirm> getProviderFirms(String firmName, List<Integer> FirmIds  , String buyerId);

	List<ServiceProvider> getServiceProviders(String provName, List<Integer> provIds , String buyerId);

	void updateFirmExclusionList(Integer ruleAssocId, boolean activeInd,String[] comment, String modifiedBy);
	
	void addToFirmExclusionList(Integer exclusionId, boolean activeInd, String[] autoCloseRuleFirmExcludeReasons, String[] firmIds, String modifiedBy);

	void addToProviderExclusionList(Integer exclusionId, boolean activeInd, String[] autoCloseRuleFirmExcludeReasons, String[] firmIds, String modifiedBy);

}
