package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.ILuActivityDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.LuActivity;

/**
 * @author sahmad7
 *
 */
public class LuActivityDaoImpl extends SqlMapClientDaoSupport implements ILuActivityDao 
{
	public LuActivity query(LuActivity luActivity) throws DBException
    {
		LuActivity dbLuActivity = new LuActivity();
	try {
		dbLuActivity = (LuActivity) getSqlMapClient().queryForObject("lu_activity.query", luActivity);
	} catch (SQLException ex) {
		ex.printStackTrace();
		logger.info("SQL Exception @CredentialDaoImpl.update() due to"
				+ StackTraceHelper.getStackTrace(ex));
		throw new DBException(
				"SQL Exception @CredentialDaoImpl.update() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		ex.printStackTrace();
		logger.info("General Exception @CredentialDaoImpl.update() due to"
				+ StackTraceHelper.getStackTrace(ex));
		throw new DBException(
				"General Exception @CredentialDaoImpl.update() due to "
						+ ex.getMessage());
	}
	return dbLuActivity;
    }
    
	public List queryList(LuActivity luActivity) throws DBException
    {
		List activityList = new ArrayList();
	try {
		activityList = getSqlMapClient().queryForList("lu_activity.query", luActivity);
	} catch (SQLException ex) {
		ex.printStackTrace();
		logger.info("SQL Exception @CredentialDaoImpl.update() due to"
				+ StackTraceHelper.getStackTrace(ex));
		throw new DBException(
				"SQL Exception @CredentialDaoImpl.update() due to "
						+ ex.getMessage());
	} catch (Exception ex) {
		ex.printStackTrace();
		logger.info("General Exception @CredentialDaoImpl.update() due to"
				+ StackTraceHelper.getStackTrace(ex));
		throw new DBException(
				"General Exception @CredentialDaoImpl.update() due to "
						+ ex.getMessage());
	}
	return activityList;
    }
	
}
