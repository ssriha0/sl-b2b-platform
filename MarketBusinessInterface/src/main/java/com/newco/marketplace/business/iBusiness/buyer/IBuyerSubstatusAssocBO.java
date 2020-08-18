package com.newco.marketplace.business.iBusiness.buyer;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;

public interface IBuyerSubstatusAssocBO {
	
	public Map<String, List> getBuyerSubstatus(Integer buyerID, Integer statusId, Integer substatus);
	
	/**
	 * get BuyerSubstatusAssoc records for given buyerId, statusId & substatusId
	 * @param buyerID
	 * @param statusId
	 * @param substatus
	 * @param buyerSubstatus
	 * @return
	 */
	public List<BuyerSubstatusAssocVO> getBuyerSubstatusAssoc(Integer buyerID, Integer statusId, Integer substatus, String buyerStubstatus);

	public boolean getSkipAlertFlag(Integer buyerID, Integer statusId, Integer substatus, String soId); 
	
	public String getIncidentId(String soId, Integer buyerID);

}
