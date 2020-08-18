/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IVendorPolicyDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.VendorPolicy;

public class VendorPolicyImpl extends SqlMapClientDaoSupport implements
		IVendorPolicyDao {
	private static final Logger localLogger = Logger.getLogger(VendorPolicyImpl.class);

	public int update(VendorPolicy vendorPolicy) throws DBException {
		Integer result = null;
		try {

			result = (Integer) getSqlMapClient().update("vendorPolicy.update",
					vendorPolicy);
			localLogger.info(" 1 > " + result);

		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorPolicyImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorPolicyImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorPolicyImpl.update() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorPolicyImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public VendorPolicy query(VendorPolicy vendorPolicy) throws DBException {
		VendorPolicy vo = null;
		try {
			localLogger
					.info("querring for the vendor policy for vendor id ***************"
							+ vendorPolicy.getVendorId());
			vo = (VendorPolicy) getSqlMapClient().queryForObject(
					"vendorPolicy.query", vendorPolicy);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorPolicyImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorPolicyImpl.query() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorPolicyImpl.query() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorPolicyImpl.query() due to "
							+ ex.getMessage());
		}
		return vo;
	}

	public void insert(VendorPolicy vendorPolicy) throws DBException {
		Integer result = null;

		try {
			result = (Integer) getSqlMapClient().insert("vendorPolicy.insert",
					vendorPolicy);

		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorPolicyImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorPolicyImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @VendorPolicyImpl.insert() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorPolicyImpl.insert() due to "
							+ ex.getMessage());
		}

	}

	public List queryList(VendorPolicy vendorPolicy) throws DBException {
		try {
			return getSqlMapClient().queryForList("vendorPolicy.query",
					vendorPolicy);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @VendorPolicyImpl.queryList() due to"
					+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"SQL Exception @VendorPolicyImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @VendorPolicyImpl.queryList() due to"
							+ StackTraceHelper.getStackTrace(ex));
			throw new DBException(
					"General Exception @VendorPolicyImpl.queryList() due to "
							+ ex.getMessage());
		}

	}
}
