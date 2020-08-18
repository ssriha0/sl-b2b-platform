package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.dto.vo.provider.TeamCredentialsLookupVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.ITeamCredentialsDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 00:40:31 $
 */
public class TeamCredentialsDaoImpl extends SqlMapClientDaoSupport implements
		ITeamCredentialsDao {
	private static final Logger localLogger = Logger
			.getLogger(TeamCredentialsDaoImpl.class.getName());

	public List queryAllType() throws DataServiceException {
		try {
			return getSqlMapClient().queryForList(
					"teamCredentialsVO.queryAllType", null);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryAllType - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryAllType - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryAllType - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryAllType - SQLException]"
							+ ex.getMessage());
		}
	}

	public List queryCatByTypeId(int typeId) throws DataServiceException {
		List list = null;

		try {
			list = getSqlMapClient().queryForList(
					"teamCredentialsVO.queryCatByTypeId", typeId);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCatByTypeId - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCatByTypeId - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCatByTypeId - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCatByTypeId - SQLException]"
							+ ex.getMessage());
		}

		return list;
	}

	public List queryCredByResourceId(int typeId) throws DataServiceException {
		List list = null;

		try {
			list = getSqlMapClient().queryForList(
					"teamCredentialsVO.queryCredByResourceId", typeId);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredByResourceId - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredByResourceId - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredByResourceId - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredByResourceId - SQLException]"
							+ ex.getMessage());
		}

		return list;
	}

	public TeamCredentialsVO queryCredById(TeamCredentialsVO teamCredentialsVO)
			throws DataServiceException {

		TeamCredentialsVO vo = null;

		try {
			vo = (TeamCredentialsVO) getSqlMapClient().queryForObject(
					"teamCredentialsVO.queryCredById", teamCredentialsVO);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredById - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredById - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredById - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredById - SQLException]"
							+ ex.getMessage());
		}
		return vo;
	}
	
	public Integer queryForCredExists(TeamCredentialsVO teamCredentialsVO) throws DataServiceException{
		localLogger.info("[TeamCredentialsDaoImpl] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RESOURCE ID "
				+ teamCredentialsVO.getResourceId());
		Integer teamCredID = -1;
		try{
			teamCredID = (Integer) getSqlMapClient().queryForObject(
					"teamCredentialVO.queryForExist", teamCredentialsVO);
		}catch(Exception exp){
			localLogger.error("[TeamCredentialsDaoImpl.queryForExist - Exception]"
					+ StackTraceHelper.getStackTrace(exp));
			throw new DataServiceException("[TeamCredentialsDaoImpl.queryForExist - Exception]"
							+ exp.getMessage());
		}
		return teamCredID;
	}

	public TeamCredentialsVO queryCredByIdSec(
			TeamCredentialsVO teamCredentialsVO) throws DataServiceException {

		TeamCredentialsVO vo = null;

		localLogger
				.info("[TeamCredentialsDaoImpl] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RESOURCE ID "
						+ teamCredentialsVO.getResourceId());

		try {
			vo = (TeamCredentialsVO) getSqlMapClient().queryForObject(
					"teamCredentialsVO.queryCredByIdSec", teamCredentialsVO);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredByIdSec - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredByIdSec - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredByIdSec - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredByIdSec - SQLException]"
							+ ex.getMessage());
		}
		return vo;
	}

	public TeamCredentialsVO insert(TeamCredentialsVO teamCredentialsVO)
			throws DataServiceException {
		Integer result = null;

		try {
			result = (Integer) getSqlMapClient().insert(
					"teamCredentialsVO.insert", teamCredentialsVO);
			teamCredentialsVO.setResourceCredId(result);
		} catch (SQLException sqle) {
			localLogger.info("[TeamCredentialsDaoImpl.insert - SQLException]"
					+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.insert - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger.info("[TeamCredentialsDaoImpl.insert - Exception]"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.insert - Exception]"
							+ ex.getMessage());
		}
		return teamCredentialsVO;
	}

	public TeamCredentialsVO delete(TeamCredentialsVO teamCredentialsVO)
			throws DataServiceException {
		Integer result = null;

		try {
			result = getSqlMapClient().delete(
					"teamCredentialsVO.delete", teamCredentialsVO);
			teamCredentialsVO.setResourceCredId(result);
		} catch (SQLException sqle) {
			localLogger.info("[TeamCredentialsDaoImpl.delete - SQLException]"
					+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.delete - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger.info("[TeamCredentialsDaoImpl.delete - Exception]"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.deletes - Exception]"
							+ ex.getMessage());
		}
		return teamCredentialsVO;
	}

	public void update(TeamCredentialsVO teamCredentialsVO)
			throws DataServiceException {

		Integer result = null;

		try {
			result = (Integer) getSqlMapClient().insert(
					"teamCredentialsVO.update", teamCredentialsVO);
		} catch (SQLException sqle) {
			localLogger.info("[TeamCredentialsDaoImpl.update - SQLException]"
					+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.update - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger.info("[TeamCredentialsDaoImpl.update - Exception]"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.update - Exception]"
							+ ex.getMessage());
		}
	}

	public void updateWfStateId(TeamCredentialsVO teamCredentialsVO)
			throws DataServiceException {

		Integer result = null;

		try {
			result = getSqlMapClient().update(
					"teamCredentialsVO.updateWfStateId", teamCredentialsVO);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.updateWfStateId - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.updateWfStateId - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.updateWfStateId - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.updateWfStateId - Exception]"
							+ ex.getMessage());
		}
	}

	public TeamCredentialsVO getResourceName(TeamCredentialsVO objTeamCredentialsVO)throws DataServiceException{

		TeamCredentialsVO dbTeamCredentialsVO = new TeamCredentialsVO();
		try {
			dbTeamCredentialsVO = (TeamCredentialsVO)getSqlMapClient().queryForObject("getResourceFullName.query", objTeamCredentialsVO);
		} catch (SQLException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return dbTeamCredentialsVO;


	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.ITeamCredentialsDao#queryCredByResourceIdIncludedStates(int, java.util.List)
	 */
	public List queryCredByResourceIdIncludedStates(int resourceId,
			List<Integer> includedStatus) throws DataServiceException {
		List list = null;
		com.newco.marketplace.vo.provider.TeamCredentialsVO vo = new com.newco.marketplace.vo.provider.TeamCredentialsVO();
		vo.setResourceId(resourceId);
		vo.setIncludedStatus(includedStatus);
		try {
			list = getSqlMapClient().queryForList(
					"teamCredentialsVO.queryCredByResourceIdIncludedStates", vo);
		} catch (SQLException sqle) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredByResourceId - SQLException]"
							+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredByResourceId - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger
					.info("[TeamCredentialsDaoImpl.queryCredByResourceId - Exception]"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.queryCredByResourceId - SQLException]"
							+ ex.getMessage());
		}

		return list;
	}
	public Boolean isResourceCredentialIdExist(TeamCredentialsVO teamCredentialsVO) throws DBException  {
        try {
			Object obj = getSqlMapClient().queryForObject("resourceCred.isAvailable", teamCredentialsVO);
			return (Boolean) obj;
			
		}catch (SQLException ex) {
			logger.info("SQL Exception @CredentialDaoImpl.isResourceCredentialIdExist() due to"+ StackTraceHelper.getStackTrace(ex));
			throw new DBException("SQL Exception @CredentialDaoImpl.isResourceCredentialIdExist() due to "+ ex.getMessage());
		}catch (Exception ex) {
			logger.info("General Exception @CredentialDaoImpl.isResourceCredentialIdExist() due to"+ StackTraceHelper.getStackTrace(ex));
			throw new DBException("General Exception @CredentialDaoImpl.isResourceCredentialIdExist() due to",ex);
		}
	}
	
	
	public void updateCredential(TeamCredentialsVO teamCredentialsVO)
			throws DataServiceException {

		Integer result = null;

		try {
			result = getSqlMapClient().update(
					"teamCredentialsVO.updateCredential", teamCredentialsVO);
		} catch (SQLException sqle) {
			localLogger.info("[TeamCredentialsDaoImpl.updateCredential - SQLException]"
					+ StackTraceHelper.getStackTrace(sqle));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.updateCredential - SQLException]"
							+ sqle.getMessage());
		} catch (Exception ex) {
			localLogger.info("[TeamCredentialsDaoImpl.updateCredential - Exception]"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.updateCredential - Exception]"
							+ ex.getMessage());
		}
	}


	public TeamCredentialsLookupVO getTeamCredLookup(String credDesc) throws DataServiceException {
		TeamCredentialsLookupVO vo = new TeamCredentialsLookupVO();
		vo.setTypeDesc(credDesc);
		
		try {
			vo = (TeamCredentialsLookupVO) getSqlMapClient().queryForObject(
					"teamCredentialsVO.queryType", vo);
		} catch (Exception ex) {
			localLogger.info("[TeamCredentialsDaoImpl.getTeamCredLookup - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException(
					"[TeamCredentialsDaoImpl.getTeamCredLookup - SQLException]"
							+ ex.getMessage());
		}
		return vo;
	}
	
	public List<TeamCredentialsLookupVO> queryCatByType(String type) throws DataServiceException {
		List<TeamCredentialsLookupVO> list = null;

		try {
			list = getSqlMapClient().queryForList("teamCredentialsVO.queryCatByType", type);
		} catch (Exception ex) {
			localLogger.info("[TeamCredentialsDaoImpl.queryCatByType - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("[TeamCredentialsDaoImpl.queryCatByType - SQLException]" + ex.getMessage());
		}
		
		return list;
	}

}