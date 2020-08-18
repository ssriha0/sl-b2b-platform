package com.newco.marketplace.web.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.so.BuyerOrderSku;

public interface ManageScopeService {
	public List<Map<Integer, String>> fetchBuyerSkus(String buyerId);
	public List<Map<Integer, String>> findSKUsForDivision(String primarySku,String buyerID);
	public List<Map<Integer, String>> findSKUsForCategory(final String buyerID,final Integer soMainCatId);
	public BuyerOrderSku fetchSKU(Integer skuId);
	public List<LookupServiceType> fetchSkills(Integer node);
	public BigDecimal getAvailableBalance(Integer buyerId) throws Exception;
	public Integer getSoLevelFundingTypeId(String soId)throws Exception;
	public List<ReasonCode> getScopeChangeReasonCodes(String buyerId);
	public ProcessResponse changeScope(String serviceOrderId, HashMap<Integer, ManageTaskVO> tasks, Integer reason, String comment, SecurityContext securityContext);
}