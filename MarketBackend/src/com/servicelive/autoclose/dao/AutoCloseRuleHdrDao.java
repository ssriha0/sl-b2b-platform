package com.servicelive.autoclose.dao;

import java.util.List;

import com.servicelive.domain.autoclose.AutoCloseRuleCriteria;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteriaHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssoc;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleHdr;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssoc;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssocHistory;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;

/**
 *
 */
public interface AutoCloseRuleHdrDao {
	
	/**
	 * Get a AutoCloseRuleHdr by buyerId
	 * @param buyerId
	 * @return AutoCloseRuleHdr
	 */
	public List<AutoCloseRuleHdr> findByBuyerId(Integer buyerId);

	/**
	 * Get a AutoCloseRuleCriteria by autoCloseRuleHdrId
	 * @param autoCloseRuleHdrId
	 * @return AutoCloseRuleCriteria
	 */
	public AutoCloseRuleCriteria findAutoCloseRuleCriteriaValue(Integer autoCloseRuleHdrId);

	/**
	 * Get a AutoCloseRuleCriteria by criteriaId
	 * @param criteriaId
	 * @return AutoCloseRuleCriteria
	 */
	public AutoCloseRuleCriteria findAutoCloseRuleCriteriaById(Integer criteriaId);

	public void saveAutoCloseRuleCriteria(AutoCloseRuleCriteria autoCloseRuleCriteria);

	public void saveAutoCloseRuleCriteriaHistory(AutoCloseRuleCriteria autoCloseRuleCriteria);

	public List<AutoCloseRuleCriteriaHistory> findAutoCloseRuleCriteriaHistoryByCriteria(Integer autoCloseRuleCriteriaId);

	public List<AutoCloseRuleFirmAssoc> findAutoCloseRuleFirmExclusionList(Integer autoCloseRuleHdrId, boolean activeInd);

	public List<AutoCloseRuleProviderAssoc> findAutoCloseRuleProvExclusionList(Integer autoCloseRuleHdrId, boolean activeInd);

	public AutoCloseRuleFirmAssoc findAutoCloseRuleFirmAssocById(Integer ruleAssocId);

	public AutoCloseRuleFirmAssoc saveAutoCloseRuleFirmAssoc(AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc);

	public AutoCloseRuleProviderAssoc findAutoCloseRuleProviderAssocById(Integer ruleAssocId);

	public AutoCloseRuleProviderAssoc saveAutoCloseRuleProviderAssoc(AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc);

	public List<ProviderFirm> searchByFirmName(String firmName);
	
	public List<ProviderFirm> searchByFirmIds(List<Integer> firmIds);
	
	public List<ServiceProvider> searchByProvName(String provName);
	
	public List<ServiceProvider> searchByProvIds(List<Integer> provIds);

	public List<Integer> getExcludedFirmIds(List<ProviderFirm> firmList  , String buyerId);

	public List<Integer> getExcludedProvIds(List<ServiceProvider> provList , String buyerId);

	public void saveAutoCloseRuleFirmAssocHistory(AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc);

	public void saveAutoCloseRuleProviderAssocHistory(AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc);

	public List<AutoCloseRuleFirmAssocHistory> findAutoCloseRuleFirmExclusionHistory(Integer autoCloseRuleAssocId);

	public List<AutoCloseRuleProviderAssocHistory> findAutoCloseRuleProviderExclusionHistory(Integer autoCloseRuleAssocId);

	public AutoCloseRuleFirmAssoc findAutoCloseRuleFirmAssocByFirmId(Integer exclusionId, Integer id);

	public AutoCloseRuleProviderAssoc findAutoCloseRuleProvAssocByProvId(Integer exclusionId, Integer id);

}
