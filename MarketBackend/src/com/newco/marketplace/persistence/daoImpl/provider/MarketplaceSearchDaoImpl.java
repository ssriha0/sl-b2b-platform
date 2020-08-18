package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.persistence.iDao.provider.IMarketplaceSearchDao;
import com.newco.marketplace.vo.provider.SearchExpressionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;

/**
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:40:31 $
 */
public class MarketplaceSearchDaoImpl extends SqlMapClientDaoSupport implements
IMarketplaceSearchDao {

	private static final Logger logger = Logger.getLogger(MarketplaceSearchDaoImpl.class.getName());
	public List getChildrenForNode(SkillNodeVO node){
		List childrenlist = new ArrayList();
		try {
			childrenlist =  getSqlMapClient().queryForList("getChildren.query", node);
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return childrenlist;
	}

	public SkillNodeVO getParentForNode(SkillNodeVO node){
		SkillNodeVO skillNodeVO = new SkillNodeVO();
		try {
			skillNodeVO=(SkillNodeVO)getSqlMapClient().queryForObject("getParent.query", node);
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return skillNodeVO;
	}
	
	public List searchNodeByExpression(SearchExpressionVO expression){
		List searchNodeList = new ArrayList();
		try {
			searchNodeList = getSqlMapClient().queryForList("getMatchSearchNodes.query", expression);
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return searchNodeList;
	}
	
	public SkillNodeVO getNodeNameById(SkillNodeVO node){
		SkillNodeVO skillNodeVO = new SkillNodeVO();
		try {
			skillNodeVO = (SkillNodeVO)getSqlMapClient().queryForObject("getNodeNameById.query", node);
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return skillNodeVO;
	}
}
