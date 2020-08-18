package com.servicelive.inhome.autoclose.dao;

import java.util.List;

import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssoc;
import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.provider.ProviderFirm;

/**
 * 
 *
 */
public interface HSRAutoCloseDao{

	List<InHomeAutoCloseRuleFirmAssoc> getAutoCloseFirmOverrideList(boolean activeInd);

	List<InHomeAutoCloseRuleFirmAssocHistory> getAutoCloseFirmOverrideHistory(Integer inhomeAutoCloseRuleFirmAssocId);

	List<Double> getReimbursementRateList();

	InHomeAutoCloseRuleFirmAssoc getAutoCloseFirmAssocById(Integer ruleAssocId);

	List<ProviderFirm> searchByFirmIds(List<Integer> firmIdList);

	InHomeAutoCloseRuleFirmAssoc findAutoCloseRuleFirmAssocByFirmId(Integer id);

	InHomeAutoCloseRuleFirmAssoc saveAutoCloseFirmAssoc(InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc);

	void saveAutoCloseFirmAssocHistory(InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc);

	List<ProviderFirm> searchByFirmName(String providerFirmName);

	List<Integer> getOverriddenFirmIds(List<ProviderFirm> firmList);

	Double getDefaultReimursementRate(String appKey);


}
