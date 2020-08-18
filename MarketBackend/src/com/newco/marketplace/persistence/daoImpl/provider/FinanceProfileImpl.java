package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IFinanceProfileDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.FinanceProfile;


public class FinanceProfileImpl extends SqlMapClientDaoSupport implements
		IFinanceProfileDAO {
	private static final Logger localLogger = Logger.getLogger(FinanceProfileImpl.class);

	public void update(FinanceProfile finProfile) throws DBException {

		Integer result = null;
		try {
			localLogger
					.info("updating the FinanceProfile for vendor id ***************"
							+ finProfile.getVendorId());
			result = (Integer) getSqlMapClient().update(
					"FinanceProfile.update", finProfile);
			localLogger.info(" 1 > " + result);

		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @FinanceProfileImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @FinanceProfileImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @FinanceProfileImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @FinanceProfileImpl.update() due to "
							+ ex.getMessage());
		}

	}

	public FinanceProfile query(FinanceProfile finProfile) throws DBException {

		FinanceProfile vo = null;
		try {
			localLogger
					.info("querring for the FinanceProfile for vendor id ***************"
							+ finProfile.getVendorId());
			vo = (FinanceProfile) getSqlMapClient().queryForObject(
					"FinanceProfile.query", finProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @FinanceProfileImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @FinanceProfileImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @FinanceProfileImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @FinanceProfileImpl.query() due to "
							+ ex.getMessage());
		}
		return vo;

	}

	public void insert(FinanceProfile finProfile) throws DBException {
		Integer result = null;

		try {
			localLogger
					.info("inserting the FinanceProfile for vendor id ***************"
							+ finProfile.getVendorId());
			result = (Integer) getSqlMapClient().insert(
					"FinanceProfile.insert", finProfile);

		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @FinanceProfileImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @FinanceProfileImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @FinanceProfileImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @FinanceProfileImpl.insert() due to "
							+ ex.getMessage());
		}

	}

	public List queryList(FinanceProfile userProfile) throws DBException {
		try {
			return getSqlMapClient().queryForList("FinanceProfile.query",
					userProfile);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @FinanceProfileImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @FinanceProfileImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @FinanceProfileImpl.queryList() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @FinanceProfileImpl.queryList() due to "
							+ ex.getMessage());
		}
	}

}
