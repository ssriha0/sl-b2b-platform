package com.servicelive.routingrulesengine.services;

import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;

public interface RoutingRulesPaginationService 
{
	public static final int DEFAULT_RULES_PAGE_SIZE=30;
	
	public RoutingRulesPaginationVO setSearchResultPagination(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO paginationCriteria,
			boolean archiveIndicator);
	public RoutingRulesPaginationVO setManageRulePagination(Integer buyerId, RoutingRulesPaginationVO paginationCriteria);
	public RoutingRulesPaginationVO setRuleHistoryPagination(Integer buyerId, RoutingRulesPaginationVO paginationCriteria, boolean archiveIndicator );
	
	/**
	 * set the upload pagination criteria
	 * @param paginationCriteria
	 * @return
	 */
	public RoutingRulesPaginationVO setUploadFileCriteria
		(RoutingRulesPaginationVO paginationCriteria,Integer buyerId) throws Exception;
	
}
