package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

/**
 * 
 * @author Stephen
 *
 */
public interface RoutingRuleHdrHistDao {

	/**
	 * 
	 * @param buyerId
	 * @return List<RoutingRuleHist>
	 */
	public List<RoutingRuleHdrHist> findByBuyerId(Integer buyerId,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);
	
	/**
	 * To get BuyerResource 
	 * @param userName
	 * @return
	 */
	public BuyerResource getBuyerResource(String userName);

}
