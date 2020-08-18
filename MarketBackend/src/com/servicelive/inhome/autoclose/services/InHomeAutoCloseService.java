package com.servicelive.inhome.autoclose.services;

import java.math.BigDecimal;
import java.util.List;

import com.newco.marketplace.dto.inhome.autoclose.InHomeAutoCloseDTO;
import com.servicelive.domain.provider.ProviderFirm;






/**
 * 
 *
 */
public interface InHomeAutoCloseService {

	List<InHomeAutoCloseDTO> getOverrideFirmList(boolean activeInd);

	List<Double> getReimbursementRateList();

	void updateOverriddenList(Integer ruleAssocId, boolean activeInd,String[] comment, String modifiedBy);

	void addToFirmOverrideList(List<BigDecimal> reimbursementRate, boolean activeInd,
			String[] comment, String[] firmIdsToAdd, String modifiedBy);

	List<ProviderFirm> getProviderFirms(String providerFirmName,List<Integer> providerFirmIds);

	Double getDefaultReimursementRate();


	
}
