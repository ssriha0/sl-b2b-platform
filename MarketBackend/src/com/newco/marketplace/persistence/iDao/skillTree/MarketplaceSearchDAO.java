package com.newco.marketplace.persistence.iDao.skillTree;

import java.util.List;

import com.newco.marketplace.dto.vo.skillTree.SearchExpressionVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;


/**
 * @author zizrale
 *
 */
public interface MarketplaceSearchDAO {
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
