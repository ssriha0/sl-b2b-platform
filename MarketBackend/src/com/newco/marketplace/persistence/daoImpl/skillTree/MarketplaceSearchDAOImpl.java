package com.newco.marketplace.persistence.daoImpl.skillTree;

import java.util.List;

import com.newco.marketplace.dto.vo.skillTree.SearchExpressionVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.persistence.iDao.skillTree.MarketplaceSearchDAO;
import com.sears.os.dao.impl.ABaseImplDao;



/**
 * @author zizrale
 *
 */
public class MarketplaceSearchDAOImpl extends ABaseImplDao implements MarketplaceSearchDAO {

	public List getChildrenForNode(SkillNodeVO node){
		return queryForList("getChildren.query", node);
	}

	public SkillNodeVO getParentForNode(SkillNodeVO node){
		return (SkillNodeVO)queryForObject("getParent.query", node);
	}
	
	public List searchNodeByExpression(SearchExpressionVO expression){
		return queryForList("getMatchSearchNodes.query", expression);
	}
	
	public SkillNodeVO getNodeNameById(SkillNodeVO node){
		return (SkillNodeVO)queryForObject("getNodeNameById.query", node);
	}
}
