package com.newco.marketplace.persistence.dao.buyersku;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.so.BuyerOrderSku;

public interface BuyerSkuDao {
	public List<Map<Integer, String>> findSKUsForManageScope(final String buyerID);
	public List<Map<Integer, String>> findSKUsForCategory(final String buyerID,final Integer soMainCatId);
	public String getSkuDivisionException(final String primarySku);
	public List<Map<Integer, String>> findSKUsForDivision(final String division,final String buyerID);
	public BuyerOrderSku findSkuBySkuId(Integer skuId);
	public List<LookupServiceType> fetchSkills(Integer mainCategory);
	public Integer getSoLevelFundingTypeIdForBuyer(String soId);
}
