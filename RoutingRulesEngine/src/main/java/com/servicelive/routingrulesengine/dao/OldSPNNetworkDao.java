package com.servicelive.routingrulesengine.dao;

import java.util.List;

/**
 * 
 * @author svanloon
 *
 */
public interface OldSPNNetworkDao {

	/**
	 * 
	 * @param spnId
	 * @param routingRuleHdrId
	 * @return List vendor_resource ids
	 */
	public List<Integer> findBySpnIdAndRoutingRuleHdrId(Integer spnId, Integer routingRuleHdrId);
}
