package com.servicelive.routingrulesengine.dao;

import java.util.List;
import java.util.Set;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleSwitches;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;

/**
 * 
 *
 */
public interface RoutingRuleBuyerAssocDao 
{
	/**
	 * 
	 * @param buyerId
	 * @return List<RoutingRuleBuyerAssoc>
	 */
	public RoutingRuleBuyerAssoc findByBuyerId(Integer buyerId);
	
	
	public List<RoutingRuleHdr> findRoutingRuleHdrId(Integer routingRulebuyerAssocId,String jobcode,String zip,String marketId,String sortOrder);
	
	public List<RoutingRuleHdr> findRoutingRuleHdrIdList(Integer routingRuleBuyerAssocId,String jobcode,String zip,String marketId,List<ServiceOrderCustomRefVO> customRefList,String sortOrder);
	
	public List<RoutingRuleHdr> findRoutingRuleHdrIdListRefactored(Integer routingRuleBuyerAssocId,String jobcode,String zip,String marketId,
			List<ServiceOrderCustomRefVO> customRefList,String sortOrder);
	
	public Integer findRoutingRuleBuyerAssocId(Integer buyerId);	
	
	public String findMarketId(String zip);
	
	public String findIfUpdate(String soId);
	
	public List<RoutingRuleSwitches> findSortOrder();
	
	public List<String> findMandatoryCustomRefs(Integer buyerId);
	
	public RoutingRuleHdr findRuleHrIdForSO(String soId);
	
	public Integer findWfStateIdforSO(String soId);
	
	/**
	 * 
	 * @param routingRuleBuyerAssoc
	 * @return RoutingRuleBuyerAssoc
	 * @throws Exception
	 */
	public RoutingRuleBuyerAssoc save(RoutingRuleBuyerAssoc routingRuleBuyerAssoc) throws Exception;
	
	public Integer getRulesHistoryCount(Integer buyerId,boolean archiveIndicator);
		
	public List<RoutingRuleHdr> getRoutingRulesHeaderForFirmId(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public List<RoutingRuleHdr> getRoutingRulesHeaderForRuleName(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public List<RoutingRuleHdr> getRoutingRulesHeaderForFirmName(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public List<RoutingRuleHdr> getRoutingRulesHeaderForUploadFileName(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public List<RoutingRuleHdr> getRoutingRulesHeaderForRuleId(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public List<RoutingRuleHdr> getRoutingRulesHeaderForProcessId(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);

	public Integer searchResultCountForRuleName(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);

	public Integer searchResultCountForFirmName(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);

	public Integer searchResultCountForFirmId(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);

	public Integer searchResultCountForUploadFileName(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);

	public Integer searchResultCountForRuleId(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);

	public Integer searchResultCountForProcessId(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);
	
	//SL 15642 Method to fetch all rules with selected auto accept status
	public Integer searchResultCountForAutoAcceptance(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator);
	
	public List<RoutingRuleHdr> getRoutingRulesHeaderForAutoAcceptanceStatus(
			RoutingRulesSearchVO routingRulesSearchVo,RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator);
	
	/**
	 * See if a buyer has a feature by passing the feature name and buyerID. If the feature is
	 * present this will return true.
	 * @param buyerID
	 * @param feature
	 * @return
	 */
	public Boolean validateFeature(Integer buyerID, String feature);
	
	/**Priority 4 issue changes
	 * Retrieve all active CAR rules matching the SO's jobcode, 
	 * zipcode and ISP_ID for Inhome buyer
	 * @param routingRulebuyerAssocId
	 * @param jobcode
	 * @param zip
	 * @param marketId
	 * @param ispIds
	 * @param sortOrder
	 * @return List<RoutingRuleHdr>
	 */
	public List<RoutingRuleHdr> findRoutingRuleForInhome(Integer routingRulebuyerAssocId,
			String jobcode,String zip,String marketId, Set<String> ispIds, String sortOrder);
}
