package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.ISkillAssignDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.LanguageVO;
import com.newco.marketplace.vo.provider.SearchExpressionVO;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;

/**
 * SkillAssign DAO Implementation
 * 
 * @version
 * @author zizrale
 *
 */
public class SkillAssignDaoImpl extends SqlMapClientDaoSupport implements ISkillAssignDao
{
	
	
	private static final Logger localLogger = Logger.getLogger(SkillAssignDaoImpl.class);
	
	public SkillAssignVO assign(SkillAssignVO skillVO) throws DBException {
		
		 
		Integer id = null;
        try{
            id  = (Integer)getSqlMapClient().insert("skillAssign.insert", skillVO);
        }catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.assign() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.assign() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.assign() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.assign() due to "
							+ ex.getMessage());
		}
        skillVO.setResourceSkillId(id.intValue());
         
        return skillVO;
    }

	public void assignServiceTypes(SkillAssignVO skillVO) throws DBException{
		 
		try {
			getSqlMapClient().insert("servTypeAssign.insert", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.assignServiceTypes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.assignServiceTypes() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.assignServiceTypes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.assignServiceTypes() due to "
							+ ex.getMessage());
		}
		 
	}
	
	public List getResourceSkillIdsList(SkillAssignVO skillVO) throws DBException{
		
		 
		try {
			return getSqlMapClient().queryForList("getResourceSkillIdsList.query", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getResourceSkillIdsList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getResourceSkillIdsList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getResourceSkillIdsList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getResourceSkillIdsList() due to "
							+ ex.getMessage());
		}
		
	}
	
	public List getChildren(SkillNodeVO treeRequestVO) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("getChildrenForNode.query", treeRequestVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		}
	}
	
	public List getServiceTypes(SkillNodeVO requestVO) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("getServiceTypes.query", requestVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		}
	}
	
	public List getGeneralSkills() throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("getGeneralSkills.query", null);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		}
	}
	
	public SkillAssignVO getResourceName(SkillAssignVO skillVO) throws DBException{
	 
		try {
			return (SkillAssignVO)getSqlMapClient().queryForObject("getSkillResourceFullName.query", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		}
	}
	
	public List getCheckedGeneralSkills(CheckedSkillsVO checkedVO) throws DBException{
	 
		try {
			return getSqlMapClient().queryForList("getCheckedGeneralSkills.query", checkedVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.update() due to "
							+ ex.getMessage());
		}
	}
	
	public List getCheckedSkills(CheckedSkillsVO checkedVO) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("getCheckedSkills.query", checkedVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getCheckedSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getCheckedSkills() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getCheckedSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getCheckedSkills() due to "
							+ ex.getMessage());
		}
	}
	
	
	
	public void deleteOldGeneralSkills(SkillAssignVO skillVO) throws DBException {
		
		 	
		try {
			getSqlMapClient().delete("removeOldChecksGeneral.delete", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.deleteOldGeneralSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.deleteOldGeneralSkills() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.deleteOldGeneralSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteOldGeneralSkills() due to "
							+ ex.getMessage());
		}
		 
	}
	
	public void deleteOldSkills(SkillAssignVO skillVO) throws DBException{
		 
		try {
			getSqlMapClient().delete("removeOldChecks.delete", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.deleteOldSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.deleteOldSkills() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.deleteOldSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteOldSkills() due to "
							+ ex.getMessage());
		}
		 
	}
	
	public void deleteOldSkillsNodes(SkillAssignVO skillVO) throws DBException{
		 
	
		try {
			getSqlMapClient().delete("removeOldChecksNodes.delete", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to "
							+ ex.getMessage());
		}
		 
	}
	
	public SkillAssignVO getResourceSkillId(SkillAssignVO skillVO) throws DBException{
		
		 
		try {
			return (SkillAssignVO)getSqlMapClient().queryForObject("getResourceSkillId.query", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getResourceSkillId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getResourceSkillId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getResourceSkillId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getResourceSkillId() due to "
							+ ex.getMessage());
		}
	}
	
	
	public SkillAssignVO getResourceSkillIdByNode(SkillAssignVO skillVO) throws DBException{
		
		 
		try {
			return (SkillAssignVO)getSqlMapClient().queryForObject("getResourceSkillIdByNode.query", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getResourceSkillIdByNode() due to"
					+ StackTraceHelper.getStackTrace(ex));
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getResourceSkillIdByNode() due to"
					+ StackTraceHelper.getStackTrace(ex));
			
		}
		return null; 
	}
	
	public List getSkillsComplete(SkillCompletionVO skillCompleteVO) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("getCompletion.query", skillCompleteVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getSkillsComplete() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getSkillsComplete() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getSkillsComplete() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getSkillsComplete() due to "
							+ ex.getMessage());
		}
	}
	
	public int insertLanguage(LanguageVO  languageVO) throws DBException{
		 
		int  result = 0;
		try {
			 result = (Integer)getSqlMapClient().insert("language.insert", languageVO);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.insertLanguage() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.insertLanguage() due to "
							+ ex.getMessage());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.insertLanguage() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.insertLanguage() due to "
							+ ex.getMessage());
		}
		return  result;
	}
	
	public void deleteOldLanguages(LanguageVO  languageVO) throws DBException{
		 
		try {
			getSqlMapClient().delete("language.delete", languageVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteOldSkillsNodes() due to "
							+ ex.getMessage());
		}
		 
	}
	
	public List getcheckedLanguages(LanguageVO  languageVO) throws DBException{
		 
		List checkedLanguages = new ArrayList();
		
		try {
			checkedLanguages = getSqlMapClient().queryForList("checkedLanguage.query", languageVO);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getcheckedLanguages() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getcheckedLanguages() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getcheckedLanguages() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getcheckedLanguages() due to "
							+ ex.getMessage());
		}
		 
		return checkedLanguages;
	}
	public List getChildrenForNode(SkillNodeVO node){
		 
		List childrenlist = new ArrayList();
		try {
			childrenlist =  getSqlMapClient().queryForList("getSkillChildren.query", node);
		} catch (SQLException e) {
			logger.error("SkillAssignDaoImpl.getChildrenForNode() method "+e.getStackTrace());
		}
		 
		return childrenlist;
	}

	public SkillNodeVO getParentForNode(SkillNodeVO node){
		 
		SkillNodeVO skillNodeVO = new SkillNodeVO();
		try {
			skillNodeVO=(SkillNodeVO)getSqlMapClient().queryForObject("getSkillParent.query", node);
		} catch (SQLException e) {
			logger.error("SkillAssignDaoImpl.getParentForNode() method "+e.getStackTrace());
		}
		 
		return skillNodeVO;
	}
	
	public List searchNodeByExpression(SearchExpressionVO expression){
		 
		List searchNodeList = new ArrayList();
		try {
			searchNodeList = getSqlMapClient().queryForList("getMatchSearchNodes.query", expression);
		} catch (SQLException e) {
			logger.error("SkillAssignDaoImpl.searchNodeByExpression() method "+e.getStackTrace());
		}
		 
		return searchNodeList;
	}
	
	public SkillNodeVO getSkillNodeNameById(SkillNodeVO node) throws DBException{
		
		 
		SkillNodeVO skillNodeVO = new SkillNodeVO();
		try {
			skillNodeVO = (SkillNodeVO)getSqlMapClient().queryForObject("getSkillNodeNameById.query", node);
		} catch (SQLException ex) {
			logger.info("SQL Exception @SkillAssignDAOImpl.getSkillNodeNameById() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getSkillNodeNameById() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getSkillNodeNameById() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getSkillNodeNameById() due to "
							+ ex.getMessage());
		}
		 
		return skillNodeVO;
	}
	/**
	 * 
	 */
	public List getResourceLanguages(Integer resourceId) throws DBException{
		
		 
		List resourceLanguages = new ArrayList();
		
		try {
			resourceLanguages = getSqlMapClient().queryForList("query.getResourceLanguages", resourceId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getResourceLanguages() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getResourceLanguages() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getResourceLanguages() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getResourceLanguages() due to "
							+ ex.getMessage());
		}
		 
		return resourceLanguages;
	}
	
	public List getResourceSkillCat(Integer resourceId) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("query.getResourceSkillCat", resourceId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getResourceSkillCat() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getResourceSkillCat() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.getResourceSkillCat() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getResourceSkillCat() due to "
							+ ex.getMessage());
		}
	}
	public List getSkillNodeName(Integer nodeId) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("query.getSkillNodeName", nodeId);
		} catch (SQLException ex) {
			
			logger.info("SQL Exception @SkillAssignDAOImpl.getSkillNodeName() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getSkillNodeName() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getSkillNodeName() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getSkillNodeName() due to "
							+ ex.getMessage());
		}
	}
	public List<SkillNodeVO> getSkillsForResource(Integer resourceId)	throws DBException {
		try {
			return getSqlMapClient().queryForList("getSkillsForResource.query", resourceId);
		} catch (SQLException ex) {
			
			logger.info("SQL Exception @SkillAssignDAOImpl.getSkillNodeName() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getSkillNodeName() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getSkillNodeName() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getSkillNodeName() due to "
							+ ex.getMessage());
		}
	}
	public List getALLCheckedSkillsForResource(Integer resourceId) throws DBException{
		 
		try {
			return getSqlMapClient().queryForList("getALLCheckedSkills.query", resourceId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getCheckedSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getCheckedSkills() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getCheckedSkills() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getCheckedSkills() due to "
							+ ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ISkillAssignDao#getSkillTree(com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO)
	 */
	public List<SkillNodeVO> getSkillTree(SkillNodeIdsVO nodeIds)
			throws DBException {
		try {
			return getSqlMapClient().queryForList("getSkillTree.query", nodeIds);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getSkillTree() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getSkillTree() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getSkillTree() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getSkillTree() due to "
							+ ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ISkillAssignDao#getServiceTypeTree(com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO)
	 */
	public List<ServiceTypesVO> getServiceTypeTree(SkillNodeIdsVO nodeIds)
			throws DBException {
		try {
			return getSqlMapClient().queryForList("getServiceTypesForRoot.query", nodeIds);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getSkillTree() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getSkillTree() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getSkillTree() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getSkillTree() due to "
							+ ex.getMessage());
		}
	}

	/*
	 * To return root node id when supplied with node Id
	 */
	public Integer getRootNodeId(Integer nodeId)throws DBException {
		try {
			return (Integer)getSqlMapClient().queryForObject("getRootNodeId.query", nodeId);
		} catch (SQLException ex) {
			
			logger.info("SQL Exception @SkillAssignDAOImpl.getRootNodeId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.getRootNodeId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.getRootNodeId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getRootNodeId() due to "
							+ ex.getMessage());
		}
	}
	/**
	 * To load the valid provider ids from the database
	 */
	public Integer loadProviderId(String providerId){
		Integer provider=null;
		try{
			provider=(Integer)getSqlMapClient().queryForObject("getProviderIds.query", providerId);
			
		}
		catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.loadProviderId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			
		}
		return provider;
		
	}
	
	/**
	 * To load the provider ids which have skills associated with it
	 */
	public List loadSkillsOfResource(String providerId) throws DBException{
		List provider=null;
		try{
			provider=getSqlMapClient().queryForList("getResourceSkills.query", providerId);
		}
		catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.loadSkillsOfResource() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.loadSkillsOfResource() due to "
							+ ex.getMessage());
		}
		return provider;
		
	}
	
	/**
	 * To return the child service types when resourceId,skillTypeId and nodeId is given
	 */
	public List<Integer> getChildServiceTypeIdList(SkillAssignVO skillVO) throws DBException{
		List<Integer> childServiceTypeIdList=null;
		try{
			childServiceTypeIdList= getSqlMapClient().queryForList("getChildServiceTypeIdList.query", skillVO);
		}
		catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.getChildServiceTypeIdList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getChildServiceTypeIdList() due to "
							+ ex.getMessage());
		}
		return childServiceTypeIdList;
	}
	
	/**
	 * To return the root service types when resourceId,skillTypeId and nodeId is given
	 */
	public List<Integer> getRootServiceTypeIdList(SkillAssignVO skillVO) throws DBException{
		List<Integer> rootServiceTypeIdList=null;
		try{
			rootServiceTypeIdList= getSqlMapClient().queryForList("getRootServiceTypeIdList.query", skillVO);
		}
		catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.getChildServiceTypeIdList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.getChildServiceTypeIdList() due to "
							+ ex.getMessage());
		}
		return rootServiceTypeIdList;
	}
	/**
	 * To remove the skills associated with the resource details in the parameter list
	 */
	public void deleteRootSkillServiceIdList(List<Integer> rootServiceTypeIdList) throws DBException{
	  
		try {
			getSqlMapClient().delete("deleteRootServiceTypeId.delete", rootServiceTypeIdList);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.deleteRootSkillServiceIdList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteRootSkillServiceIdList() due to "
							+ ex.getMessage());
		}
  }
  
	/**
	 * 
	 * @param skillVO
	 * @return
	 * @throws DBException
	 */
	public SkillAssignVO fetchResourceSkillId(SkillAssignVO skillVO) throws DBException{
		
		try {
			return (SkillAssignVO)getSqlMapClient().queryForObject("fetchResourceSkillId.query", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.fetchResourceSkillId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.fetchResourceSkillId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.fetchResourceSkillId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.fetchResourceSkillId() due to "
							+ ex.getMessage());
		}
	}
	
	public List<Integer> fetchResourceSkillEntry(SkillAssignVO skillVO) throws DBException{
		List<Integer> resourceSkillIdList=null;
		try{
			resourceSkillIdList= getSqlMapClient().queryForList("fetchResourceSkillEntry.query", skillVO);
		}
		catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.fetchResourceSkillEntry() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.fetchResourceSkillEntry() due to "
							+ ex.getMessage());
		}
		return resourceSkillIdList;
	}
	
	public void deleteRootNodeId(SkillAssignVO skillVO) throws DBException {
		
		try {
			getSqlMapClient().delete("deleteRootNodeId.delete", skillVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.deleteRootNodeId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteRootNodeId() due to "
							+ ex.getMessage());
		} 
	}
	/**
	 * @param skillVO
	 * @return
	 * @throws DBException
	 */
	public List<Integer> retrieveResourceSkillEntry(SkillAssignVO skillVO) throws DBException{
		List<Integer> resourceSkillIdList=null;
		try{
			resourceSkillIdList= getSqlMapClient().queryForList("retrieveResourceSkillEntry.query", skillVO);
		}
		catch (Exception ex) {
			
			logger.info("General Exception @SkillAssignDAOImpl.retrieveResourceSkillEntry() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.retrieveResourceSkillEntry() due to "
							+ ex.getMessage());
		}
		return resourceSkillIdList;
	}
	
	public SkillAssignVO getRootNodeResourceSkillId(SkillAssignVO skillVO) throws DBException{
		
		 
		try {
			return (SkillAssignVO)getSqlMapClient().queryForObject("getRootNodeResourceSkillId.query", skillVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @SkillAssignDAOImpl.getRootNodeResourceSkillId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignDAOImpl.getRootNodeResourceSkillId() due to"
					+ StackTraceHelper.getStackTrace(ex));
			
		}
		return null; 
	}
	
	/*Code added as part of R15_1 to delete the 
	skills from resource_skill table*/ 
	public void deleteOtherNodes(SkillAssignVO skillVO) throws DBException{
		 
		
		try {
			getSqlMapClient().delete("deleteOtherNodes.delete", skillVO);
		} catch (SQLException ex) {
			
			logger.info("SQL Exception @SkillAssignDAOImpl.deleteOtherNodes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @SkillAssignDAOImpl.deleteOtherNodes() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
		
			logger.error("General Exception @SkillAssignDAOImpl.deleteOtherNodes() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.deleteOtherNodes() due to "
							+ ex.getMessage());
		}
		 
	}
	

	/*Code added as part of R15_1 to fetch the 
	skills from resource_skill_service_type table based on resource_skill_id*/ 
	public List<Integer> fetchResourceSkillServiceType(SkillAssignVO skillVO) throws DBException{
		List<Integer> resourceSkillIdList=null;
		try{
			resourceSkillIdList= getSqlMapClient().queryForList("fetchResourceSkillServiceType.query", skillVO);
		}
		catch (Exception ex) {
			
			logger.error("General Exception @SkillAssignDAOImpl.fetchResourceSkillServiceType() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @SkillAssignDAOImpl.fetchResourceSkillServiceType() due to "
							+ ex.getMessage());
		}
		return resourceSkillIdList;
	}
	
}