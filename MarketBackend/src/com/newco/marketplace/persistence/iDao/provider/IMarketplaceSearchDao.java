package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.vo.provider.SearchExpressionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;

public interface IMarketplaceSearchDao 
{
	/**
	 * @param node
	 * @return List of children nodes
	 */
	public List getChildrenForNode(SkillNodeVO node);
	/**
	 * @param node
	 * @return SkillNodeVO
	 */
	public SkillNodeVO getParentForNode(SkillNodeVO node);
	/**
	 * @param expression
	 * @return List of nodes
	 */
	public List searchNodeByExpression(SearchExpressionVO expression);
	/**
	 * @param node
	 * @return SkillNodeVO
	 */
	public SkillNodeVO getNodeNameById(SkillNodeVO node);
}
